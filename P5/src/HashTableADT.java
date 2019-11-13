//
// Title:           implementation of a hash table
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

/*
 * The hash table will be an array of linked lists, which you will write yourself.
 * Each linked list will contain nodes containing key/value pairs.
 * The hash index will be calculated as follows:
 * 		int hashIndex = Math.abs(key.hashCode()) % this.table.length;
 * Your class may not use any import statements
 * May not add any public members to ADT or implementation.
 * @param <K> the key must not be null and must be Comparable
 * @param <V> the data value associated with a given key
 */
public interface HashTableADT<K extends Comparable<K>, V> {
	/**
	 * Adds the key/value pair to the hash table and increases the number of keys.
	 * This method resizes the hash table when load factor >= threshold
	 * Capacity must increase to: 2 * capacity + 1. Once increased, capacity never decreases.
	 * @param key
	 * @param value
	 * @throws DuplicateKeyException if key is already in the data structure
	 * @throws NullKeyException if key is null
	 */
	void insert(K key, V value) throws DuplicateKeyException, NullKeyException;

	/**
	 * Removes the key/value pair from the hash table and decreases the number of keys.
	 * @param key
	 * @return true is key is found, false if key is not found
	 * @throws NullKeyException if key is null
	 */
	boolean remove(K key) throws NullKeyException;

	/**
	 * Returns the value associated with the specified key.
	 * @param key
	 * @return value
	 * @throws KeyNotFoundException if key is not found
	 * @throws NullKeyException if key is null
	 */
	V get(K key) throws KeyNotFoundException, NullKeyException;

	/**
	 * Returns the number of keys in the hash table.
	 * @return number of keys
	 */
	int numKeys();


	/**
	 * Returns the current load factor for this hash table.
	 * load factor = number of items / current table size 
	 * @return load factor (as a double)
	 */
	public double getLoadFactor() ;

	/**
	 * Returns the current capacity (table size) of the hash table array.
	 * The initial capacity must be a positive integer, 1 or greater and is specified in the constructor.
	 * When the load factor >= threshold,  the capacity must increase to: 2 * capacity + 1.
	 * @return capacity
	 */
	public int getCapacity() ;
}
