interface BinarySearchTreeADT<T extends Comparable<T>> {
    // for p0, assume that no element values are null

    // adds element to tree maintaining BST rules
// if element is already in tree, do nothing
    void insert(T element);

    // if element exists, remove from tree & replace with inorder predecessor
    // if not, or if tree is empty, do nothing
    void remove(T element);

    // returns true if element is in the tree
// in all other cases returns false
    boolean contains(T element);

    // returns the height of this tree (empty = 0, single node = 1, etc)
    int getHeight();

    // returns the number of elements in this tree, empty = 0
    int getSize();

    // returns a String representing the pre-order traversal of this tree
    // each element in the String should be followed by a space
    // an empty tree returns an empty String
    String preOrderTraversal();

    // returns a String representing the in-order traversal of this tree
    // each element in the String should be followed by a space
    // an empty tree returns an empty String
    String inOrderTraversal();

    // prints out this tree sideways...use the version given in notes
    void printSideways();

}
