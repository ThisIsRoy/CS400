//
// Title:           implementation of a binary search tree class
// Files:           Requires: BSTNode.java
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
// Online Sources:  stackoverflow.com: proper javadoc formatting, ternary operator
//

public class BSTTree<T extends Comparable<T>> implements BinarySearchTreeADT<T> {
    private BSTNode<T> root;

    public BSTTree() {
        this.root = null;
    }

    // *********************************
    // --- TREE OPERATION FUNCTIONS ---
    // *********************************

    /**
     * insert element into correct binary position
     * @param element element to be inserted
     */
    public void insert (T element) {
        this.root = this.insert(this.root, element);
    }

    /**
     * helper function for {@link #insert(T)}
     * @param current current node
     * @param element element to be inserted
     * @return current node
     */
    private BSTNode<T> insert(BSTNode<T> current, T element) {
        // insert node once we reach end of tree
        if (current == null) {
            current = new BSTNode<T>(element);

            // continue to look for end of tree via recursion
        } else if (element.compareTo(current.getData()) < 0 ) {
            current.setLeft(insert(current.getLeft(), element));
        } else if (element.compareTo(current.getData()) > 0) {
            current.setRight(insert(current.getRight(), element));
        }

        return current;
    }

    /**
     * remove element from tree
     * @param element element to be removed
     */
    public void remove(T element) {
        if (root != null) {
            // root node is element to remove
            if (root.getData() == element) {
                remove(null, root, 0);

                // start recursion helper function to look for node
            } else {
               findNode(element, null, root, 0);
            }
        }
    }

    /**
     * helper function for {@link #remove(T)}
     * finds the node to be removed in the BST
     * @param element element to be removed
     * @param parent parent of current node
     * @param current current node
     * @param parentDirection 1 - current is left child of parent,
     *                        2 - current is right child of parent
     */
    private void findNode(T element, BSTNode<T> parent, BSTNode<T> current, int parentDirection) {
        if (current != null) {
            // found node to remove
            if (current.getData() == element) {
                remove(parent, current, parentDirection);

                // keep looking recursively
            } else {
                findNode(element, current, current.getLeft(), 1);
                findNode(element, current, current.getRight(), 2);
            }
        }
    }

    /**
     * helper function for {@link #remove(T)}
     * finds the parent of the smallest node larger than the current node via the left subtree
     * @param parent
     * @param current
     * @return
     */
    private BSTNode<T> findNextSmallestNodeParent(BSTNode<T> parent, BSTNode<T> current) {
        // found smallest node
        if (current.getLeft() == null) {
            return parent;

            // recursively loop through left subtree
        } else {
            return findNextSmallestNodeParent(current, current.getLeft());
        }
    }

    // current, replacementNodeParent is only used when 2 children

    /**
     * helper function for {@link #remove(T)}
     * switches the current node with the replacementNode
     * @param parent parent of the current node
     * @param replacementNode replacement node
     * @param parentDirection 1 - current node is left child of parent node
     *                        2 - current node is right child of parent node
     * @param numOfChildren number of children the current node has
     * @param replacementNodeParent parent of the replacement node
     * @param current current node
     */
    private void replaceNode(BSTNode<T> parent, BSTNode<T> replacementNode, int parentDirection, int numOfChildren, BSTNode<T> replacementNodeParent, BSTNode<T> current) {
        // attaches children from old node to replacement node if old node had two children
        if (numOfChildren == 2) {
            int replaceDirection = replacementNodeParent.getData() == current.getData() ? 2 : 1;

            // removes replacement node from its original position
            replaceNode(replacementNodeParent, replacementNode.getRight(), replaceDirection, replacementNode.numOfChildren(), null, null);
            replacementNode.setLeft(current.getLeft());
            replacementNode.setRight(current.getRight());
        }

        // add replacement node to its new position
        if (parent == null) {
            root = replacementNode;
        } else if (parentDirection == 1) {
            parent.setLeft(replacementNode);
        } else if (parentDirection == 2) {
            parent.setRight(replacementNode);
        }


    }

    /**
     * helper function for {@link #remove(T)}
     * calls the other helper functions to remove the current node
     * @param parent parent of current node
     * @param current current node
     * @param parentDirection 1 - current node is left child of parent node
     *                        2 - current node is right child of parent node
     */
    private void remove(BSTNode<T> parent, BSTNode<T> current, int parentDirection) {
        int numOfChildren = current.numOfChildren();

        if (numOfChildren == 0) {
            // no child, simply remove node
            replaceNode(parent,null, parentDirection, numOfChildren, null, null);


            // one child, attach child to parent
        } else if (numOfChildren == 1) {
            BSTNode<T> child = current.getChildren().get(0);
            replaceNode(parent, child, parentDirection, numOfChildren, null, null);


            // two children, find next largest of right subtree to replace
        } else if (numOfChildren == 2) {
            BSTNode<T> replaceNodeParent = findNextSmallestNodeParent(current, current.getRight());
            BSTNode<T> replaceNode = replaceNodeParent.getData() == current.getData() ? replaceNodeParent.getRight() : replaceNodeParent.getLeft();
            replaceNode(parent, replaceNode, parentDirection, numOfChildren, replaceNodeParent, current);
        }
    }

    /**
     * checks if tree contains a certain element
     * @param element element to check for in tree
     * @return boolean on whether element is in tree
     */
    public boolean contains(T element) {
        return contains(this.root, element);
    }

    /**
     * helper function for {@link #contains(T)}
     * recursively looks through the tree for element
     * @param current current node
     * @param element element to check for in tree
     * @return
     */
    private boolean contains(BSTNode<T> current, T element) {
        // return false if we reach end of tree
        if (current == null) {
            return false;

            // return true if we find node
        } else if (current.getData() == element) {
            return true;

            // continue searching via recursion
        } else if (element.compareTo(current.getData()) < 0) {
            return contains(current.getLeft(), element);
        } else if (element.compareTo(current.getData()) > 0) {
            return contains(current.getRight(), element);
        }

        System.out.println("Error in contain at element " + current.getData()); // catch error
        return false;
    }

    /**
     * finds height of tree
     * @return integer value for height of tree
     */
    public int getHeight() {
        if (root == null) {
            return 0;
        } else {
            return root.getHeight();
        }
    }

    /**
     * finds number of elements in tree
     * @return integer value for number of elements in tree
     */
    public int getSize() {
        if (root == null) {
            return 0;
        } else {
            return root.getSize();
        }
    }

    // *******************************
    // --- TREE PRINTING FUNCTIONS ---
    // *******************************

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
    private void printSideways(BSTNode<T> current, String indent) {
        if (current != null) {
            printSideways(current.getRight(), indent + "    ");
            System.out.println(indent + current.getData());
            printSideways(current.getLeft(), indent + "    ");
        }
    }

    // ********************************
    // --- TREE TRAVERSAL FUNCTIONS ---
    // ********************************

    /**
     * finds post order traversal of tree
     * @return string value of post order traversal
     */
    public String postOrderTraversal() {
        String postOrder = "";
        return postOrderTraversal(this.root, postOrder);
    }

    /**
     * helper function for {@link #postOrderTraversal()}
     * @param current current node
     * @param postOrder string value for the traversal
     * @return string value for the traversal
     */
    private String postOrderTraversal(BSTNode<T> current, String postOrder) {
        if (current != null) {
            postOrder = postOrderTraversal(current.getLeft(), postOrder);
            postOrder = postOrderTraversal(current.getRight(), postOrder);
            postOrder = postOrder + " " + current.getData();
        }
        return postOrder;
    }

    /**
     * finds pre order traversal of tree
     * @return string value of pre order traversal
     */
    public String preOrderTraversal() {
        String preOrder = "";
        return preOrderTraversal(this.root, preOrder);
    }

    /**
     * helper function for {@link #preOrderTraversal()}
     * @param current current node
     * @param preOrder string value for the traversal
     * @return string value for the traversal
     */
    private String preOrderTraversal(BSTNode<T> current, String preOrder) {
        if (current != null) {
            preOrder = preOrder + " " + current.getData();
            preOrder = preOrderTraversal(current.getLeft(), preOrder);
            preOrder = preOrderTraversal(current.getRight(), preOrder);
        }
        return preOrder;
    }

    /**
     * finds in order traversal of tree
     * @return string value of in order traversal
     */
    public String inOrderTraversal() {
        String inOrder = "";
        return inOrderTraversal(this.root, inOrder);
    }

    /**
     * helper function for {@link #inOrderTraversal()}
     * @param current current node
     * @param inOrder string value for the traversal
     * @return string value for the traversal
     */
    private String inOrderTraversal(BSTNode<T> current, String inOrder) {
        if (current != null) {
            inOrder = inOrderTraversal(current.getLeft(), inOrder);
            inOrder = inOrder + " " + current.getData();
            inOrder = inOrderTraversal(current.getRight(), inOrder);
        }
        return inOrder;
    }

    /**
     * test tree
     */
    public static void main(String[] args) {
        BSTTree<Integer> tree = new BSTTree<Integer>();
        tree.insert(50);tree.insert(40); tree.insert(90);

        tree.insert(10);tree.insert(22);tree.insert(5);

        tree.insert(60); tree.insert(45); tree.insert(55);

        tree.insert(1);
        tree.remove(10);
        tree.printSideways();
        System.out.println(tree.preOrderTraversal());
        System.out.println(tree.getHeight());

    }


}
