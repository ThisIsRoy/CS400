public class BinarySearchTree<T extends Comparable <T>> {
    private BSTNode<T> root;

    public BinarySearchTree() {
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

    public void postOrderTraversal(BSTNode<T> current) {
        if (current != null) {
            postOrderTraversal(current.getLeft());
            postOrderTraversal(current.getRight());
            System.out.println(current.getData());
        }
    }


    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
    }


}
