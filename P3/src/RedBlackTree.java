public class RedBlackTree<K extends Comparable<K>, V> implements SearchTreeADT<K, V> {

// Note:  your RedBlackTree implementation must be consistent with the class notes and specifications.
// When in doubt, check your results with https://www.cs.usfca.edu/~galles/visualization/RedBlack.html

	private class RBNode {
		private K key;
		private V value;
		private boolean isRed;
		private RBNode left;
		private RBNode right;

		// we assume that all new nodes are red to start
		public RBNode(K key, V value) {
			this.key = key;
			this.value = value;
			this.isRed = true;
		}
	}

	private RBNode root;

	// RBTree constructor
	public RedBlackTree() {
		this.root = null;
	}

	// RBTree methods
	/**
	 * Checks for an empty tree.
	 * @return true if tree contains 0 items
	 */
	public boolean isEmpty() {
		return root == null;
	};

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
	private int getSize(RBNode currNode) {
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
	private int getHeight(RBNode currNode) {
		if (currNode == null) {
			return 0;
		}

		return 1 + Math.max(getHeight(currNode.left), getHeight(currNode.right));
	}

	/**
	 * Adds a key, value pair to the tree.
	 * If key already exists in tree, do not make a new node
	 *    but instead replace old value with new value.
	 * @param key key to insert
	 * @param value value to insert
	 * @throws IllegalKeyException if the key is null
	 */
	public void insert(K key, V value) throws IllegalKeyException {
		if (key == null) {
			throw new IllegalKeyException("Illegal key for insertion");
		}

        this.root = insert(root, key, value);
        this.root.isRed = false;
	}

    /**
     * helper function for inserting node
     * @param currNode current node
     * @param key key to insert
     * @param value value to insert
     */
    private RBNode insert(RBNode currNode, K key, V value) {
        if (currNode == null) {
            return new RBNode(key, value);
        }

        // key already exists, replace value
        K currKey = currNode.key;
        if (currKey.compareTo(key) == 0) {
            currNode.value = value;
        }

        // key to insert is less than current key, go left
        if (key.compareTo(currKey) < 0) {
            currNode.left = insert(currNode.left, key, value);

            // key to insert is greater than current key, go right
        } else if (key.compareTo(currKey) > 0) {
            currNode.right = insert(currNode.right, key, value);
        }

        // find red red violation and balance if necessary
        currNode = checkRedRed(currNode);

        return currNode;
    }

    /**
     * helper function to check if any child + grandchild of current node violates the red-red property
     * if so, call balance algorithm
     * @param currNode current node
     * @return new current node
     */
    private RBNode checkRedRed(RBNode currNode) {
        // direction of the red-red violation, 1-left and 2-right
        int childDirection = 0;
        int grandChildDirection = 0;

        // check left subtree
        if (currNode.left != null && currNode.left.isRed) {

            // check if left child + left grandchild are both red
            if (currNode.left.left != null && currNode.left.left.isRed) {
                childDirection = 1;
                grandChildDirection = 1;

                // check if left child + right grandchild are both red
            } else if (currNode.left.right != null && currNode.left.right.isRed) {
                childDirection = 1;
                grandChildDirection = 2;
            }
        }

        // check right subtree
        if (currNode.right != null && currNode.right.isRed) {

            // check if right child + left grandchild are both red
            if (currNode.right.left != null && currNode.right.left.isRed) {
                childDirection = 2;
                grandChildDirection = 1;

                // check if right child + right grandchild are both red
            } else if (currNode.right.right != null && currNode.right.right.isRed) {
                childDirection = 2;
                grandChildDirection = 2;
            }
        }

        return recolor(currNode, childDirection, grandChildDirection);
    }

    /**
     * helper function for recoloring nodes to correct red-red violation
     * @param currNode current node
     * @param childDirection child direction of red-red violation
     * @param grandChildDirection grandchild direction of red-red violation
     * @return new current node
     */
    private RBNode recolor(RBNode currNode, int childDirection, int grandChildDirection) {
        // no violation detected
        if (childDirection == 0) {
            return currNode;
        }

        // set uncle, child, grandchild such that child and grandchild are the red red violations
        RBNode uncle;
        RBNode child;
        RBNode grandchild;
        if (childDirection == 1) {
            uncle = currNode.right;
            child = currNode.left;
            grandchild = grandChildDirection == 1 ? child.left : child.right;
        } else {
            uncle = currNode.left;
            child = currNode.right;
            grandchild = grandChildDirection == 1 ? child.left : child.right;
        }

        // uncle is red, recolor child + uncle to be black and current node to be red
        if (uncle != null && uncle.isRed) {
            uncle.isRed = false;
            child.isRed = false;
            currNode.isRed = true;
            return currNode;

            // uncle is black, check for four cases
        } else {

            // left left case
            if (childDirection == 1 && grandChildDirection == 1) {
                currNode = leftLeftRecolor(currNode);

                // left right case
            } else if (childDirection == 1 && grandChildDirection == 2) {
                currNode = leftRightRecolor(currNode);

                // right left case
            } else if (childDirection == 2 && grandChildDirection == 1) {
                currNode = rightLeftRecolor(currNode);

                // right right case
            } else {
                currNode = rightRightRecolor(currNode);
            }
        }

        return currNode;
    }

    /**
     * helper function for recoloring in left left violations
     * @param currNode current node
     * @return new current node
     */
    private RBNode leftLeftRecolor(RBNode currNode) {
        currNode = rotateRight(currNode);
        currNode.isRed = false;
        currNode.right.isRed = true;
        return currNode;
    }

    /**
     * helper function for recoloring in left right violations
     * @param currNode current node
     * @return new current node
     */
    private RBNode leftRightRecolor(RBNode currNode) {
        currNode.left = rotateLeft(currNode.left);
        return leftLeftRecolor(currNode);

    }

    /**
     * helper function for recoloring in right right violations
     * @param currNode current node
     * @return new current node
     */
    private RBNode rightRightRecolor(RBNode currNode) {
        currNode = rotateLeft(currNode);
        currNode.isRed = false;
        currNode.left.isRed = true;
        return currNode;
    }

    /**
     * helper function for recoloring in right left violations
     * @param currNode current node
     * @return new current node
     */
    private RBNode rightLeftRecolor(RBNode currNode) {
        currNode.right = rotateRight(currNode.right);
        return rightRightRecolor(currNode);
    }

    /**
     * AVLTree rotate left.
     * Assumes root has a right child
     * @param root an imbalance node
     * @return TreeNode<K, V> the node for which balance has been modified
     */
    private RBNode rotateLeft(RBNode root) {
        RBNode rightChild = root.right;
        // store root's right child's left child if it exists
        RBNode rightLeftChild = rightChild.left;

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
    private RBNode rotateRight(RBNode root) {
        RBNode leftChild = root.left;
        // store root's left child's right child if it exists
        RBNode leftRightChild = leftChild.right;

        root.left = leftRightChild;
        leftChild.right = root;

        return leftChild;
    }

	/**
	 * Get the value in the tree associated with the given key.
	 * If key is not found, return null.
	 * @param key key to find
	 * @return value
	 * @throws IllegalArgumentException if the key is null
	 */
	public V getValue(K key) throws IllegalKeyException {
		if (key == null) {
			throw new IllegalKeyException();
		}

		return get(root, key);
	}

	/**
	 * helper function for finding the node with the specified key
	 * @param currNode current node
	 * @param key key to look for
	 * @return the value of the key to look for
	 */
	private V get(RBNode currNode, K key) {
		if (currNode == null) {
			return null;
		}

		K currKey = currNode.key;

		if (currKey.compareTo(key) == 0) {
			return currNode.value;

			// key is smaller, look left
		}  else if (key.compareTo(currKey) < 0) {
			return get(currNode.left, key);

			// key is larger, look right
		} else {
			return get(currNode.right, key);
		}
	}

	/**
	 * Returns the keys in this tree in pre-order traversal.
	 * @return a String with each key separated by a space.  Example: "a b c d".
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
	private String preOrderTraversal(RBNode current, String preOrder) {
		if (current != null) {
			preOrder = preOrder + " " + current.key;
			preOrder = preOrderTraversal(current.left, preOrder);
			preOrder = preOrderTraversal(current.right, preOrder);
		}
		return preOrder;
	}

	/*
	 * prints a tree sideways on your screen source: Building Java Programs, 4th
	 * Ed., by Reges and Stepp, Ch. 17
	 */
	public void printSideways() {
		System.out.println("------------------------------------------");
		printSideways(root, "");
		System.out.println("------------------------------------------");
	}

	// private recursive helper method for printSideways above
	private void printSideways(RBNode current, String indent) {
		if (current != null) {
			printSideways(current.right, indent + "    ");
			if (current.isRed) {
				System.out.println(indent + "[" + current.key + "]");
			} else {
				System.out.println(indent + "(" + current.key + ")");
			}
			printSideways(current.left, indent + "    ");
		}
	}

    public static void main(String[] args) {
        RedBlackTree<Integer, String> tree = new RedBlackTree<Integer, String>();
        try {
            tree.insert(4, "wfw");
            tree.insert(3, "wer");
            tree.insert(7, "werw");
            tree.insert(2, "wefwe");
            tree.insert(1, "wefwe");
            tree.insert(6, "werwe");
            tree.insert(5, "werwe");
            tree.insert(10,"");

            System.out.println(tree.getValue(3));
            System.out.println(tree.getValue(5));

        } catch (Exception e) {
            System.out.println("illegal key lmao");
        }

        tree.printSideways();

    }
}
