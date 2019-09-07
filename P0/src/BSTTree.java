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
        if (current == null) {
            current = new BSTNode<T>(element);
        } else if (element.compareTo(current.getData()) < 0 ) {
            current.setLeft(insert(current.getLeft(), element));
        } else if (element.compareTo(current.getData()) > 0) {
            current.setRight(insert(current.getRight(), element));
        } else {
            System.out.println("Element " + element + " already exists in the tree!");
        }
        return current;
    }

    public void remove(T element) {

    }

    public boolean contains(T element) {
        return contains(this.root, element);
    }

    private boolean contains(BSTNode<T> current, T element) {
        if (current == null) {
            return false;
        } else if (current.getData() == element) {
            return true;
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
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
    }


}
