public class BSTTree<T extends Comparable<T>> implements BinarySearchTreeADT<T> {
    private BSTNode<T> root;

    public BSTTree() {
        this.root = null;
    }

    // *********************************
    // --- TREE OPERATION FUNCTIONS ---
    // *********************************
    public void insert (T element) {
        this.root = this.insert(this.root, element);
    }

    private BSTNode<T> insert(BSTNode<T> current, T element) {
        // insert node once we reach end of tree
        if (current == null) {
            current = new BSTNode<T>(element);
            System.out.println("Node " + element + " added successfully!");

            // continue to look for end of tree via recursion
        } else if (element.compareTo(current.getData()) < 0 ) {
            current.setLeft(insert(current.getLeft(), element));
        } else if (element.compareTo(current.getData()) > 0) {
            current.setRight(insert(current.getRight(), element));

            // catch error
        } else {
            System.out.println("Element " + element + " already exists in the tree!");
        }

        return current;
    }

    public void remove(T element) {
        if (root != null) {
            // root node is element to remove
            if (root.getData() == element) {
                remove(null, root, 0);
                System.out.println("Node " + element + " removed successfully!");

                // start recursion helper function to look for node
            } else {
               findNode(element, null, root, 0);
            }
        }
    }

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
    private void replaceNode(BSTNode<T> parent, BSTNode<T> replacementNode, int parentDirection, int numOfChildren, BSTNode<T> replacementNodeParent, BSTNode<T> current) {
        // attaches children from old node to replacement node if old node had two children
        if (numOfChildren == 2) {
            int replaceDirection = replacementNodeParent.getData() == root.getData() ? 2 : 1;
            System.out.println("Current is " + current.getRight().getData());

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

    // parent direction: 0 no parent, 1 node is parent's left child, 2 node is parent's right child
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
            BSTNode<T> replaceNode = replaceNodeParent.getData() == root.getData() ? replaceNodeParent.getRight() : replaceNodeParent.getLeft();
            replaceNode(parent, replaceNode, parentDirection, numOfChildren, replaceNodeParent, current);
        }
    }

    public boolean contains(T element) {
        return contains(this.root, element);
    }

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

    public int getHeight() {
        if (root == null) {
            return 0;
        } else {
            return root.getHeight();
        }
    }

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
    public void printSideways() {
        System.out.println("------------------------------------------");
        printSideways(root, "");
        System.out.println("------------------------------------------");
    }

    // private recursive helper method for printSideways above
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
    public void postOrderTraversal() {
        postOrderTraversal(this.root);
    }

    private void postOrderTraversal(BSTNode<T> current) {
        if (current != null) {
            postOrderTraversal(current.getLeft());
            postOrderTraversal(current.getRight());
            System.out.println(current.getData());
        }
    }

    public String preOrderTraversal() {
        String preOrder = "";
        preOrderTraversal(this.root, preOrder);
        return preOrder;
    }

    private void preOrderTraversal(BSTNode<T> current, String preOrder) {
        if (current != null) {
            preOrder += current.getData();
            preOrderTraversal(current.getLeft(), preOrder);
            preOrderTraversal(current.getRight(), preOrder);
        }
    }

    public String inOrderTraversal() {
        String inOrder = "";
        preOrderTraversal(this.root, inOrder);
        return inOrder;
    }

    private void inOrderTraversal(BSTNode<T> current, String inOrder) {
        if (current != null) {
            inOrderTraversal(current.getLeft(), inOrder);
            inOrder += current.getData();
            inOrderTraversal(current.getRight(), inOrder);
        }
    }

    public static void main(String[] args) {
        BSTTree<Integer> tree = new BSTTree<Integer>();
        tree.insert(5);
        tree.insert(7);
        tree.insert(10);
        tree.insert(4);
        tree.insert(6);

        tree.printSideways();

        tree.remove(7);

        tree.printSideways();
        // System.out.println(tree.preOrderTraversal());
    }


}
