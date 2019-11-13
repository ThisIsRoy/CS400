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

/**
 * Hash table data structure.
 * @param <K> key
 * @param <V> value
 */
@SuppressWarnings("unchecked")
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {

	/**
	 * Hash node inner class storing a key, value, and pointer to the next node in the chain.
	 */
	@SuppressWarnings("hiding")
	private class HashNode<K, V> {
		private K key;
		private V value;
		private HashNode<K, V> next;

		private HashNode(K key, V value, HashNode<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		private String hashNodeToString() {
			return key + " : " + value;
		}
	}

	// instance variables for hash table
	private double loadFactorThreshold;
	private int numKeys;
	private HashNode<K, V>[] table; // array of header nodes

	/**
	 * Default no-arg constructor.
	 */
	public HashTable() {
		this(5, 0.75);
	}

	/**
	 * Constructor with initial capacity and load factor threshold provided.
	 * @param initialCapacity
	 * @param loadFactorThreshold
	 */
	public HashTable(int initialCapacity, double loadFactorThreshold) {
		this.loadFactorThreshold = loadFactorThreshold;
		this.numKeys = 0;
		// initialize buckets
		table = (HashNode<K, V>[]) new HashNode[initialCapacity];
	}

	/**
	 * Prints the HashTable to standard output.
	 */
	public void printHashTable() {
		System.out.println("HASH TABLE:");
		// print out an array of BucketList<KeyValuePair>
		for (int i = 0; i < table.length; i++) {
			System.out.print(i + ": ");
			HashNode<K, V> currNode = table[i];
			while (currNode != null) {
				System.out.print("(" + currNode.hashNodeToString() + "), ");
				currNode = currNode.next;
			}
			System.out.println();
		}
		System.out.println("numKeys: " + numKeys + "\tload factor: " + 1.0 * this.numKeys/this.table.length);
		System.out.println("--------------------------------------------------");
	}

	/**
	 * Adds the key/value pair to the hash table and increases the number of keys.
	 * This method resizes the hash table when load factor >= threshold
	 * Capacity must increase to: 2 * capacity + 1. Once increased, capacity never decreases.
	 * @param key
	 * @param value
	 * @throws DuplicateKeyException if key is already in the data structure
	 * @throws NullKeyException if key is null
	 */
	public void insert(K key, V value) throws DuplicateKeyException, NullKeyException {
		if (key == null) {
			throw new NullKeyException("Key is null");
		}

		// insert key value
		int hashIndex = computeHashIndex(key);
		insert(key, value, hashIndex);

		// check if table needs to be re-sized
		if (getLoadFactor() > this.loadFactorThreshold) {
			resize();
		}
	}

	/**
	 * computes hash index of the key value
	 * @param key key
	 * @return hash index
	 */
	private int computeHashIndex(K key) {
		return Math.abs(key.hashCode()) % this.table.length;
	}

	/**
	 * helper function for inserting node into bucket
	 * @param key key
	 * @param value value
	 * @param hashIndex index to insert at
	 * @throws DuplicateKeyException
	 */
	private void insert(K key, V value, int hashIndex) throws DuplicateKeyException {
		// no key exists at index
		if (table[hashIndex] == null) {
			table[hashIndex] = new HashNode<>(key, value, null);
			this.numKeys++;
		} else {
			addKeyValue(key, value, table[hashIndex]);
		}
	}

	/**
	 * recursive function for finding location in linked list to add node
	 * @param key key
	 * @param value value
	 * @param node current node
	 * @throws DuplicateKeyException
	 */
	private void addKeyValue(K key, V value, HashNode<K,V> node) throws DuplicateKeyException {
		// key already exists
		if (node.key == key) {
			throw new DuplicateKeyException();
		}

		// create node at end of the linked list
		if (node.next != null) {
			addKeyValue(key, value, node.next);
		} else {
			node.next = new HashNode<>(key, value, null);
			this.numKeys++;
		}
	}

	/**
	 * helper function to resize capacity to 2 * capacity + 1
	 */
	private void resize() {
		HashNode<K, V>[] oldTable = this.table;
		this.table = (HashNode<K, V>[]) new HashNode[getCapacity() * 2 + 1];
		this.numKeys = 0;
		for (HashNode<K, V> node : oldTable) {
			if (node != null) {
				copyNodesToNewTable(node);
			}
		}
	}

	/**
	 * copies all nodes in linked list to new table
	 * assumes node is NOT null
	 * @param node node in linked list
	 */
	private void copyNodesToNewTable(HashNode<K,V> node) {
		// insert node into resized table using the correct hash index
		try {
			insert(node.key, node.value, computeHashIndex(node.key));
		} catch (DuplicateKeyException duplicate) {
			System.out.println("Error in resizing, duplicate key");
		}

		// keep going for all nodes in linked list
		if (node.next != null) {
			copyNodesToNewTable(node.next);
		}
	}

	/**
	 * Removes the key/value pair from the hash table and decreases the number of keys.
	 * @param key
	 * @return true is key is found, false if key is not found
	 * @throws NullKeyException if key is null
	 */
	public boolean remove(K key) throws NullKeyException {
		if (key == null) {
			throw new NullKeyException();
		}

		int hashIndex = computeHashIndex(key);
		int beforeNumKey = this.numKeys;

		// remove key and check if number of keys stayed the same
//        System.out.println(this.table[hashIndex].key + " is the key of the first node");
//        System.out.println(key + " is the key to remove");
		this.table[hashIndex] = removeKey(this.table[hashIndex], key);
        return beforeNumKey != this.numKeys;
	}

	/**
	 * helper function for finding key in linked list
	 * @param node
	 * @param key
	 * @return whether the key is in the linked list
	 */
	private HashNode<K, V> removeKey(HashNode<K,V> node, K key) {
		if (node == null) {
			return null;
		}

//        System.out.println("From removeKey, node key is " + node.key);
//        System.out.println("From removeKey, key is " + key);
//        if (node.key == key) {
//            System.out.println(node.key + " is equal to " + key);
//        } else {
//            System.out.println("|" + node.key + "|" + " is not equal to " + "|" + key + "|");
//        }

		// found key, remove
		if (node.key.equals(key)) {
			this.numKeys--;
			return node.next;

			// recursively keep finding
		} else {
			node.next = removeKey(node.next, key);
			return node;
		}
	}


	/**
	 * Returns the value associated with the specified key.
	 * @param key
	 * @return value
	 * @throws KeyNotFoundException if key is not found
	 * @throws NullKeyException if key is null
	 */
	public V get(K key) throws KeyNotFoundException, NullKeyException {
		if (key == null) {
			throw new NullKeyException();
		}

		int hashIndex = computeHashIndex(key);
		return findKey(this.table[hashIndex], key);
	}

	/**
	 * helper function for finding the key in the linked list
	 * @param node
	 * @param key
	 * @return
	 * @throws KeyNotFoundException
	 */
	private V findKey(HashNode<K,V> node, K key) throws KeyNotFoundException {
		if (node == null) {
			throw new KeyNotFoundException();
		}

		// return key if found
		if (node.key == key) {
			return node.value;

			// otherwise recursively search through list
		} else {
			return findKey(node.next, key);
		}
	}

	/**
	 * Returns the number of keys in the hash table.
	 * @return number of keys
	 */
	public int numKeys() {
		return this.numKeys;
	}

	/**
	 * Returns the current load factor for this hash table.
	 * load factor = number of items / current table size
	 * @return load factor (as a double)
	 */
	public double getLoadFactor() {
        return (double) this.numKeys / this.table.length;
	}

	/**
	 * returns table capacity
	 * @return table capacity
	 */
	public int getCapacity() {
		return this.table.length;
	}

	/**
	 * Main method.
	 * @param args
	 */
	public static void main(String[] args) {
		HashTable<String, String> table = new HashTable<String, String>(2, 0.5);

        try {
            table.insert("test1", "lmao");
            table.insert("test2", "lmaooo");
            System.out.println(table.numKeys());
            System.out.println(table.get("test2"));
            Boolean testBool = table.remove("test1");
        } catch (DuplicateKeyException duplicate) {
            System.out.println("duplicate");
        } catch (NullKeyException nullKey) {
            System.out.println("Null key");
        } catch (KeyNotFoundException notFound) {
            System.out.println("key not found");
        }

	}
}
