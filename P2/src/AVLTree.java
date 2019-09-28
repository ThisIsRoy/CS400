// remember that you are not allowed to add any public methods or fields
// but you can add any private methods or fields

public class AVLTree<K extends Comparable<K>, V> implements TreeADT<K, V> {
	public TreeNode<K, V> root = null;

	/** 
	 * Inner class representing a node of the AVL tree. 
	 * @param <K>
	 * @param <V>
	 */
	private class TreeNode<K, V> {
		private K key;
		private V value;
		private int height;
		private TreeNode<K, V> left, right;

		/**
		 * constructor for node
		 * @param key key of node
		 * @param value value of node
		 */
		private TreeNode(K key, V value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * get key of node
		 * @return key of node
		 */
		private K getKey() {
			return key;
		}

		/**
		 * get value of node
		 * @return value of node
		 */
		private V getValue() {
			return value;
		}

		/**
		 * set height of node
		 * @param height integer height to set
		 */
		private void setHeight(int height) {
			this.height = height;
		}

		/**
		 * get left child of node
		 * @return left child of node
		 */
		private TreeNode<K, V> getLeft() {
			return left;
		}

		/**
		 * set left child of node
		 * @param left new left child of node
		 */
		private void setLeft(TreeNode<K, V> left) {
			this.left = left;
		}

		/**
		 * get right child of node
		 * @return right child of node
		 */
		private TreeNode<K, V> getRight() {
			return right;
		}

		/**
		 * set right child of node
		 * @param right new right child of node
		 */
		private void setRight(TreeNode<K, V> right) {
			this.right = right;
		}

		/**
		 * get height of node
		 * @return height of node
		 */
		private int getHeight() {
			if (this.left == null && this.right == null) {
				return 1;
			} else if (this.left == null) {
				return 1 + this.right.getHeight();
			} else if (this.right == null) {
				return 1 + this.left.getHeight();
			} else {
				return 1 + Math.max(this.left.getHeight(), this.right.getHeight());
			}
		}

		/**
		 * get balance factor of node, left child height minus right child height
		 * @return balance factor of node
		 */
		private int getBalanceFactor() {
			if (this.left == null && this.right == null) {
				return 0;
			} else if (this.left == null) {
				return -1 * this.right.getHeight();
			} else if (this.right == null) {
				return this.left.getHeight();
			} else {
				return this.left.getHeight() - this.right.getHeight();
			}
		}

        private int numOfChildren() {
            int numOfChildren = 0;

            if (getLeft() != null) {
                numOfChildren ++;
            }
            if (getRight() != null) {
                numOfChildren ++;
            }

            return numOfChildren;
        }
	}

   // TODO:  IMPLEMENT ALL INTERFACE METHODS
	/**
	 * Checks for an empty tree.
	 * @return true if tree contains 0 items
	 */
	public boolean isEmpty() {
		return root == null;
	};

	/**
	 * Adds a key, value pair to the tree.
	 * @param key
	 * @param value
	 * @throws DuplicateKeyException if the key has already been inserted into the tree
	 * @throws IllegalKeyException if the key is null
	 */
	public void insert(K key, V value) throws DuplicateKeyException, IllegalKeyException {
        if (key == null) {
            throw new IllegalKeyException();
        }

        this.root = insert(root, key, value);
	};

	/**
	 * helper function for inserting node
	 * @param currNode current node
	 * @param key key to insert
	 * @param value value to insert
	 * @throws DuplicateKeyException null key error
	 */
	private TreeNode<K, V> insert(TreeNode<K, V> currNode, K key, V value) throws DuplicateKeyException {
        // current location is null, add new node here
        if (currNode == null) {
            return new TreeNode<K, V>(key, value);
        }

        // throw duplicate exception if same key is found
        K currKey = currNode.getKey();
        if (currKey.compareTo(key) == 0) {
            throw new DuplicateKeyException();
        }

        // key to insert is less than current key, go left
        if (key.compareTo(currKey) < 0) {
            currNode.setLeft(insert(currNode.getLeft(), key, value));

            // key to insert is greater than current key, go right
        } else if (key.compareTo(currKey) > 0) {
            currNode.setRight(insert(currNode.getRight(), key, value));
        }

        // check balance factor after insertion
        int balanceFactor = currNode.getBalanceFactor();
        if (Math.abs(balanceFactor) > 1) {
            currNode = balance(currNode, balanceFactor);
        }

        return currNode;
	}

	/**
	 * helper function for balancing node, assuming current node is not null
	 * @param currNode current node
	 * @param balanceFactor balance factor of current node
	 */
	private TreeNode<K, V> balance(TreeNode<K, V> currNode, int balanceFactor) {
	    // check balance factor
		if (balanceFactor >= 2) {
			int leftBalanceFactor = currNode.getLeft().getBalanceFactor();

			// left left case
			if (leftBalanceFactor > 0) {
				currNode = rotateRight(currNode);

				// left right case
			} else {
                currNode.setLeft(rotateLeft(currNode.getLeft()));
                currNode = rotateRight(currNode);
			}
		} else if (balanceFactor <= -2) {
			int rightBalanceFactor = currNode.getRight().getBalanceFactor();

			// right left case
			if (rightBalanceFactor > 0) {
			    currNode.setRight(rotateRight(currNode.getRight()));
                currNode = rotateLeft(currNode);

				// right right case
			} else {
                currNode = rotateLeft(currNode);
			}
		}

		return currNode;
	}

	/**
	 * Removes a key from the AVL tree. Returns nothing if the key is not found.
	 * If a node has 2 children, replace that node with the inorder predecessor
	 * @param key
	 * @throws IllegalKeyException if attempt to delete null
	 */
	public void delete(K key) throws IllegalKeyException {
		if (key == null) {
			throw new IllegalKeyException();
		}

		if (root == null) {
            return;
        }

        root = delete(root, key, true);
	};

    /**
     * helper function that uses recursion to find the node to delete
     * @param currNode current node
     * @param key key to delete
     * @param rebalance whether or not to rebalance after deletion
     * @return value of the current node after the possible deletion
     */
	private TreeNode<K, V> delete(TreeNode<K, V> currNode, K key, boolean rebalance) {
	    // leaf node, stop recursion
	    if (currNode == null) {
            return null;
        }

        K currKey = currNode.getKey();
        // call deletion algorithm if current node is to be deleted
        if (key.compareTo(currKey) == 0) {
            currNode = deleteNode(currNode);
        } else {
            // key to insert is less than current key, go left
            if (key.compareTo(currKey) < 0) {
                currNode.setLeft(delete(currNode.getLeft(), key, true));

                // key to insert is greater than current key, go right
            } else if (key.compareTo(currKey) > 0) {
                currNode.setRight(delete(currNode.getRight(), key, true));
            }
        }

        // check balance factor if current node is not null
        if (currNode != null && rebalance) {
            currNode = balance(currNode, currNode.getBalanceFactor());
        }

        return currNode;
    }

    /**
     * helper function to perform the actual deletion algorithm on the current node
     * assumes current node is not null
     * @param currNode current node
     * @return current node after deletion
     */
    private TreeNode<K, V> deleteNode(TreeNode<K, V> currNode) {
        int numOfChildren = currNode.numOfChildren();

        // no children, simply delete node
        if (numOfChildren == 0) {
            return null;

            // one child, attach child to parent of current node
        } else if (numOfChildren == 1) {
            return currNode.getLeft() != null ? currNode.getLeft() : currNode.getRight();

            // two children, find in-order predecessor and replace current node with it
        } else if (numOfChildren == 2) {
            TreeNode<K, V> inOrderPredecessor = getInOrderPredecessor(currNode);

            // replace current node with in-order predecessor
            inOrderPredecessor.setLeft(currNode.getLeft());
            inOrderPredecessor.setRight(currNode.getRight());
            return inOrderPredecessor;
        }

        return null;
    }

    /**
     * helper function for finding the in-order predecessor node
     * @param currNode current node
     * @return node towards the in-order predecessor
     */
    private TreeNode<K,V> getInOrderPredecessor(TreeNode<K,V> currNode) {
        // current node is root, go left
        if (currNode == root) {
            return getInOrderPredecessor(currNode.getLeft());

            // current node has right child, go right
        } else if (currNode.getRight() != null) {
            return getInOrderPredecessor(currNode.getRight());

            // current node has no right child, current node is in-order predecessor
        } else {
            // delete the in-order predecessor
            try {
                delete(root, currNode.getKey(), false);

            } catch(Exception e) {
                System.out.println("Error in getting in order predecessor");
            }

            return currNode;
        }
    }

    /**
	 * AVLTree rotate left.
     * Assumes root has a right child
	 * @param root an imbalance node
	 * @return TreeNode<K, V> the node for which balance has been modified 
	 */
	private TreeNode<K, V> rotateLeft(TreeNode<K, V> root) {
		TreeNode<K, V> rightChild = root.getRight();
        // store root's right child's left child if it exists
		TreeNode<K, V> rightLeftChild = rightChild.getLeft();

		root.setRight(rightLeftChild);
		rightChild.setLeft(root);

		return rightChild;
	}

	/**
	 * AVLTree rotate right.
     * Assumes root has a left child
	 * @param root an imbalance node
	 * @return the node for which balance has been modified 
	 */
	private TreeNode<K, V> rotateRight(TreeNode<K, V> root) {
        TreeNode<K, V> leftChild = root.getLeft();
        // store root's left child's right child if it exists
        TreeNode<K, V> leftRightChild = leftChild.getRight();

        root.setLeft(leftRightChild);
        leftChild.setRight(root);

		return leftChild;
	}

	/**
	 * Get a value in the tree given the key.
     * returns null if key not found
	 * @param key
	 * @return value the object associated with this key
	 * @throws IllegalArgumentException if the key is null
	 */
	public V get(K key) throws IllegalKeyException {
		if (key == null) {
			throw new IllegalKeyException();
		}

		return get(root, key);
	};

    /**
     * helper function for finding the node with the specified key
     * @param currNode current node
     * @param key key to look for
     * @return the value of the key to look for
     */
	private V get(TreeNode<K, V> currNode, K key) {
        if (currNode == null) {
            return null;
        }

		K currKey = currNode.getKey();

		if (currKey.compareTo(key) == 0) {
			return currNode.getValue();

			// key is smaller, look left
		}  else if (key.compareTo(currKey) < 0) {
		    return get(currNode.getLeft(), key);

			// key is larger, look right
		} else {
		    return get(currNode.getRight(), key);
		}
	}

    /**
     * gets pre-order traversal of tree
     * @return string of pre-order traversal
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
    private String preOrderTraversal(TreeNode<K, V> current, String preOrder) {
        if (current != null) {
            preOrder = preOrder + " " + current.getKey();
            preOrder = preOrderTraversal(current.getLeft(), preOrder);
            preOrder = preOrderTraversal(current.getRight(), preOrder);
        }
        return preOrder;
    }

	/**
	 * Print a tree sideways to show structure. This code is completed for you.
	 */
	public void printSideways() {
		System.out.println("------------------------------------------");
		recursivePrintSideways(root, "");
		System.out.println("------------------------------------------");
	}

	/**
	 * Print nodes in a tree. This code is completed for you. 
	 * You are allowed to modify this code to include balance factors or heights
	 * @param current
	 * @param indent
	 */
	private void recursivePrintSideways(TreeNode<K, V> current, String indent) {
		if (current != null) {
			recursivePrintSideways(current.right, indent + "    ");
			System.out.println(indent + current.key);
			recursivePrintSideways(current.left, indent + "    ");
		}
	}

    public static void main(String[] args) {
        AVLTree<Integer, String> test = new AVLTree<Integer, String>();
        try {
            test.insert(3,"lmao");
            test.insert(2, "ljw");
            test.insert(1, "wefwe");
            test.insert(6, "werw");
            test.insert(8, "werwe");
            test.insert(10, "werwertge");
            test.insert(9, "werwer");
            test.delete(6);
            test.printSideways();
        } catch (IllegalKeyException e) {
            System.out.println("lmaooo");
        } catch (DuplicateKeyException f) {
            System.out.println("lmaooooo");
        }

    }
}