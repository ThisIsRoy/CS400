//
// Title:           implementation of a red black tree
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
// Online Sources:  NONE
//

/**
 * Interface for a search tree.
 */
public interface SearchTreeADT<K extends Comparable<K>, V>  {

	/**
	 * Checks for an empty tree.
	 * @return true if tree contains 0 items
	 */
	public boolean isEmpty();

	/**
	 * Returns the number of nodes in the tree.
	 * An empty tree has a size of 0.
	 * @return tree size
	 */
	public int getSize();

	/**
	 * Returns the height of the tree.
	 * A empty tree has height =  0. A tree with only a root has height = 1.
	 * @return tree height
	 */
	public int getHeight();

	/**
	 * Adds a key, value pair to the tree. 
	 * If key already exists in tree, do not make a new node
	 *    but instead replace old value with new value.
	 * @param key
	 * @param value
	 * @throws IllegalKeyException if the key is null
	 */
	public void insert(K key, V value) throws IllegalKeyException;

	/**
	 * Get the value in the tree associated with the given key.
	 * If key is not found, return null.
	 * @param key
	 * @return value
	 * @throws IllegalArgumentException if the key is null
	 */
	public V getValue(K key) throws IllegalKeyException;

	/**
	 * Returns the keys in this tree in pre-order traversal. 
	 * @return a String with each key separated by a space.  Example: "a b c d".
	 */
	public String preOrderTraversal();

	/**
	 * Prints AVL tree sideways to show structure.
	 */
	public void printSideways();
}
