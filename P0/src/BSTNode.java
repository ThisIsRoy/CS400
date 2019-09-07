public class BSTNode<T extends Comparable<T>> {

    private T data;
    private BSTNode<T> left;        // a reference to the left child
    private BSTNode<T> right;        // a reference to the right child

    public BSTNode(T data) {
        this.data = data;
    }

// add getters and setters
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BSTNode<T> getLeft() {
        return left;
    }

    public void setLeft(BSTNode<T> left) {
        this.left = left;
    }

    public BSTNode<T> getRight() {
        return right;
    }

    public void setRight(BSTNode<T> right) {
        this.right = right;
    }

    public int getHeight() {
        // base case leaf node
        if (this.getLeft() == null & this.getRight() == null) {
            return 1;

            // recursive case
        } else if (this.getLeft() == null) {
            return 1 + this.getRight().getHeight();
        } else if (this.getRight() == null) {
            return 1 + this.getLeft().getHeight();
        } else {
            return 1 + Math.max(this.getLeft().getHeight(), this.getRight().getHeight());
        }
    }

    public int getSize() {
        // base case leaf node
        if (this.getLeft() == null & this.getRight() == null) {
            return 1;

            // recursive case
        } else if (this.getLeft() == null) {
            return 1 + this.getRight().getSize();
        } else if (this.getRight() == null) {
            return 1 + this.getLeft().getSize();
        } else {
            return 1 + this.getLeft().getSize() + this.getRight().getSize();
        }
    }

    public int numOfChildren() {
        int numOfChildren = 0;

        if (getLeft() != null) {
            numOfChildren ++;
        }
        if (getRight() != null) {
            numOfChildren ++;
        }

        return numOfChildren;
    }

// add a main method to test the code
    public static void main(String[] args) {
        BSTNode<String> node1 = new BSTNode<>("R");
        BSTNode<String> node2 = new BSTNode<>("O");
        BSTNode<String> node3 = new BSTNode<>("Y");
        BSTNode<String> node4 = new BSTNode<>("!");

        node1.setLeft(node2);
        // node1.setRight(node3);
        node2.setRight(node4);

        System.out.println(node1.getHeight());
    }
}