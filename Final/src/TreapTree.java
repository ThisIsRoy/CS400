//
// Title:           NBA Statistics
// Files:           Requires: N/A
// Course:          CS 400 Fall 19 2019
//
// Author:          Roy Sun
// Email:           rsun65@wisc.edu
// Lecturer's Name: Andrew Kuemmel
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here.  Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do.  If you received no outside help from either type
//  of source, then please explicitly indicate NONE.
//
// Persons:         NONE
// Online Sources:  thenewboston Youtube
//                  stackoverflow
//                  oracle docs
//                  geeksforgeeks
//

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TreapTree<T extends Comparable<T>> {
    private TreapNode root;
    private int maxPriority;

    private class TreapNode {
        private T value;
        private int priority;
        private TreapNode left;
        private TreapNode right;

        private TreapNode(T value) {
            this.value = value;

            // generate priority as random number up to 1000
            Random rand = new Random();
            this.priority = rand.nextInt(maxPriority);
        }

        /**
         * finds number of children for the current node
         * @return number of children node
         */
        private int numOfChildren() {
            int childNum = 0;

            if (left != null) {
                childNum ++;
            }

            if (right != null) {
                childNum ++;
            }

            return  childNum;
        }
    }

    public TreapTree() {
        this.root = null;
        this.maxPriority = 100;
    }

    public TreapTree(int maxPriority) {
        this.root = null;
        this.maxPriority = maxPriority;
    }

    /**
     * Returns the number of nodes in the tree.
     * An empty tree has a size of 0.
     * @return tree size
     */
    public int getSize() {
        return getSize(root);
    }

    /**
     * helper function for recursively finding the size of the tree
     * @param currNode current node
     * @return size of the subtree
     */
    private int getSize(TreapNode currNode) {
        if (currNode == null) {
            return 0;
        }

        return 1 + getSize(currNode.left) + getSize(currNode.right);
    }

    /**
     * Returns the height of the tree.
     * A empty tree has height =  0. A tree with only a root has height = 1.
     * @return tree height
     */
    public int getHeight() {
        return getHeight(root);
    }

    /**
     * helper function for recursively finding the height of the tree
     * @param currNode current node
     * @return height of the subtree
     */
    private int getHeight(TreapNode currNode) {
        if (currNode == null) {
            return 0;
        }

        return 1 + Math.max(getHeight(currNode.left), getHeight(currNode.right));
    }

    /**
     * checks if tree contains a certain element
     * @param value element to check for in tree
     * @return boolean on whether element is in tree
     */
    public boolean search(T value) {
        return contains(this.root, value);
    }

    /**
     * helper function for {@link #search(T)}
     * recursively looks through the tree for element
     * @param current current node
     * @param value element to check for in tree
     * @return whether the current node contains the value
     */
    private boolean contains(TreapNode current, T value) {
        // return false if we reach end of tree
        if (current == null) {
            return false;

            // return true if we find node
        } else if (current.value == value) {
            return true;

            // continue searching via recursion
        } else if (value.compareTo(current.value) < 0) {
            return contains(current.left, value);
        } else {
            return contains(current.right, value);
        }
    }

    /**
     * insert element into correct binary position
     * @param element element to be inserted
     */
    public void insert (T element) {
        this.root = insert(this.root, element);
    }

    /**
     * helper function for {@link #insert(T)}
     * @param current current node
     * @param element element to be inserted
     * @return current node
     */
    private TreapNode insert(TreapNode current, T element) {
        // insert node once we reach end of tree
        if (current == null) {
            current = new TreapNode(element);

            // continue to look for end of tree via recursion, performs rotation to ensure max heap property
        } else if (element.compareTo(current.value) < 0 ) {
            current.left = insert(current.left, element);

            if (current.left.priority > current.priority) {
                current = rotateRight(current);
            }
        } else if (element.compareTo(current.value) > 0) {
            current.right = insert(current.right, element);

            if (current.right.priority > current.priority) {
                current = rotateLeft(current);
            }
        }

        return current;
    }

    /**
     * AVLTree rotate left.
     * Assumes root has a right child
     * @param root an imbalance node
     * @return the node for which balance has been modified
     */
    private TreapNode rotateLeft(TreapNode root) {
        TreapNode rightChild = root.right;
        // store root's right child's left child if it exists
        TreapNode rightLeftChild = rightChild.left;

        root.right = rightLeftChild;
        rightChild.left = root;

        return rightChild;
    }

    /**
     * AVLTree rotate right.
     * Assumes root has a left child
     * @param root an imbalance node
     * @return the node for which balance has been modified
     */
    private TreapNode rotateRight(TreapNode root) {
        TreapNode leftChild = root.left;
        // store root's left child's right child if it exists
        TreapNode leftRightChild = leftChild.right;

        root.left = leftRightChild;
        leftChild.right = root;

        return leftChild;
    }

    /**
     * deletes element from tree
     * does nothing if element does not exist in treap
     */
    public void delete(T element) {
        this.root = delete(this.root, element);
    }

    private TreapNode delete(TreapNode current, T element) {
        // element does not exist in tree
        if (current == null) {
            return null;

            // continue to look for end of tree via recursion, performs rotation to ensure max heap property
        } else if (element.compareTo(current.value) < 0 ) {
            current.left = delete(current.left, element);
        } else if (element.compareTo(current.value) > 0) {
            current.right = delete(current.right, element);

            // found node to delete, check three cases
        } else if (element.equals(current.value)) {
            int numOfChildren = current.numOfChildren();

            // node has no children
            if (numOfChildren == 0) {
                return null;
                // node has one child
            } else if (numOfChildren == 1) {
                return current.left != null ? current.left : current.right;
                // node has two children
            } else {
                int leftMaxPriority = getMaxPriority(current.left);
                int rightMaxPriority = getMaxPriority(current.right);

                // perform rotation until case 1 or 2 can be performed
                if (leftMaxPriority > rightMaxPriority) {
                    current = rotateRight(current);
                    current.right = delete(current.right, element);
                } else {
                    current = rotateLeft(current);
                    current.left = delete(current.left, element);
                }
            }
        }

        return current;
    }

    /**
     * helper function for getting the maximum priority
     * @param current root of the subtree to find the max priority of
     * @return
     */
    private int getMaxPriority(TreapNode current) {
        if (current == null) {
            return 0;
        }

        // get priority of current vs left and right side
        int maxPriority = current.priority;
        int leftMaxPriority = getMaxPriority(current.left);
        int rightMaxPriority = getMaxPriority(current.right);

        // compare and replace priority
        if (leftMaxPriority > maxPriority) {
            maxPriority = leftMaxPriority;
        }

        if (rightMaxPriority > maxPriority) {
            maxPriority = rightMaxPriority;
        }

        return maxPriority;
    }

    /**
     * function for testing if the priority of the tree is valid
     * @return whether the tree has valid priority
     */
    public boolean validatePriority() {
        return validatePriority(this.root, maxPriority);
    }

    /**
     * helper function for recursively checking tree priority
     * @param current current node
     * @param parentPriority priority of parent node
     * @return whether the node maintains priority
     */
    private boolean validatePriority(TreapNode current, int parentPriority) {
        if (current == null) {
            return true;
        } else if (current.priority > parentPriority) {
//            System.out.println("current node is " + current.value);
//            System.out.println("parent priority is " + parentPriority);
            return false;
        } else {
            return validatePriority(current.left, current.priority) && validatePriority(current.right, current.priority);
        }
    }

    /**
     * validates if the values in the BST maintain BST property
     * @return whether tree is BST
     */
    public boolean validateTree() {
        List<T> inOrder = new ArrayList<T>();
        inOrderTraversal(this.root, inOrder);
        List<T> sorted = new ArrayList<T>(inOrder);
        Collections.sort(sorted);
        return sorted.equals(inOrder);
    }

    /**
     * returns the in-order traversal of the tree
     * @return list containing the in-order traversal
     */
    public List<T> inOrderTraversal() {
        List<T> inOrder = new ArrayList<T>();
        inOrderTraversal(this.root, inOrder);
        return inOrder;
    }

    /**
     * helper function for {@link #validateTree()}
     * @param current current node
     * @param inOrder string value for the traversal
     * @return string value for the traversal
     */
    private List<T> inOrderTraversal(TreapNode current, List<T> inOrder) {
        if (current != null) {
            inOrder = inOrderTraversal(current.left, inOrder);
            inOrder.add(current.value);
            inOrder = inOrderTraversal(current.right, inOrder);
        }
        return inOrder;
    }

    /**
     * prints tree structure
     */
    public void printSideways() {
        System.out.println("------------------------------------------");
        printSideways(root, "");
        System.out.println("------------------------------------------");
    }

    /**
     * helper function for {@link #printSideways()}
     * @param current current node
     * @param indent space between nodes
     */
    private void printSideways(TreapNode current, String indent) {
        if (current != null) {
            printSideways(current.right, indent + "    ");
            System.out.println(indent + current.value + " (" + current.priority + ")");
            printSideways(current.left, indent + "    ");
        }
    }

    public static void main(String[] args) {
//        TreapTree<Integer> tree = new TreapTree<Integer>();
//        tree.insert(4);
//        tree.insert(7);
//        tree.insert(10);
//        tree.insert(12);
//        tree.insert(3);
//        tree.delete(10);
//        tree.printSideways();
//        System.out.println(tree.validatePriority());
//        System.out.println(tree.validateTree());
    }
}
