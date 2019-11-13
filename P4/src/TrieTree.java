//
// Title:           implementation of a trie tree
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

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
// no other import statements are allowed

/**
 * Trie data structure.
 */
public class TrieTree implements PrefixTreeADT {
	private final char ROOT = '@';
	private TNode root;
	private int size;	

	// provide the  inner class header and instance variables to students
	/**
	 * Private inner node class.
	 */
	private class TNode {
		private char letter;
		private boolean isEndOfWord;
		private int endWordCount;  
		private ArrayList<TNode> childList;

		private TNode(char letter) {
			this.letter = letter;
			this.endWordCount = 0;
			this.childList = new ArrayList<TNode>();
		}
	
	}// inner class
	
	
	/**
	 * Trie constructor.
	 */
	public TrieTree() {
		root = new TNode(ROOT); // stands for root node
		this.size = 0;
	}

	/**
	 * Prints the tree to the console.
	 */
	public void printTrie() {
		printTrieRecursive(root, "");
	}

	/**
	 * Helper method for printTrie().
	 * @param current the current node in the recursive call
	 * @param indent the amount of indent to print this level
	 */
	private void printTrieRecursive(TNode current, String indent) {
		if (current != null) {
			if (current.isEndOfWord) {
				System.out.println(indent + current.letter + ":" + current.endWordCount);
			} else {
				System.out.println(indent + current.letter);
			}
			for (TNode child : current.childList) {
				printTrieRecursive(child, indent + "  ");
			}
		}
	}
	
	// TODO: IMPLEMENT THE METHODS REQUIRED BY THE INTERFACE
	/**
	 * Gets the size ....An empty trie returns 0.
	 * @return size (number of unique words) in the trie.
	 */
	public int getSize() {
        return this.size;
	}

	/**
	 * private helper function for recursively finding height
	 * @param node current node
	 * @return size of the current node
	 */
	private int getSize(TNode node) {
		int size = 1;
		for (TNode childNode : node.childList) {
			size += getSize(node);
		}
		return size;
	}

	/**
	 * Inserts a string into the trie and increases the count of the word.
	 * New nodes must be inserted in alphabetical order.
	 * @param word can be upper or lower case, but must be stored as lower case
	 * @throws IllegalArgumentException if input string is null or length is 0
	 */
	public void insert(String word) throws IllegalArgumentException {
		if (word == null || word.length() == 0) {
			throw new IllegalArgumentException();
		}

		insert(word, root);
	}

	/**
	 * private helper function for inserting
	 * @param word word to insert
	 * @param current current node in the tree
	 */
	private void insert(String word, TNode current) {
		char firstLetter = word.charAt(0);
		TNode node = null;

		// if node exists, no need to create new node
		for (TNode child : current.childList) {
			if (child.letter == firstLetter) {
				node = child;
			}
		}

		// create node when letter doesn't exist
		if (node == null) {
			node = new TNode(firstLetter);
            addAlphabetically(current.childList, node);
            // this.size++;
		}

		// base case, end of word
		if (word.length() == 1) {
            if (!node.isEndOfWord) {
                this.size++;
            }

			node.isEndOfWord = true;
			node.endWordCount++;
		} else {
			insert(word.substring(1), node);
		}

	}

	/**
	 * helper function to insert the character in the list alphabetically
	 * @param childList array list of child nodes
	 * @param node node to insert
	 */
	private void addAlphabetically(ArrayList<TNode> childList, TNode node) {
		boolean added = false;

		// loop through child list to find position alphabetically
		for (int i = 0; i < childList.size(); i++) {
			if (childList.get(i).letter > node.letter) {
				childList.add(i, node);
				added = true;
				break;
			}
		}

		// empty list or the new node is last alphabetically
		if (!added) {
			childList.add(node);
		}
	}

	/**
	 * Returns the number of times the word appears in the trie.
	 * @param word can be upper or lower case, but is stored as lower case
	 * @return the number of occurrences of word (returns 0 if word not present)
	 * @throws IllegalArgumentException if input string is null or length is 0
	 */
	public int getFrequency(String word) throws IllegalArgumentException {
		if (word == null || word.length() == 0) {
			throw new IllegalArgumentException();
		}

		return getFrequency(word, this.root);
	}

	/**
	 * helper function for finding word in tree
	 * @param word word to look for
	 * @param current current node
	 * @return occurrence of word
	 */
	private int getFrequency(String word, TNode current) {
		// loop through child list to find node with matching letter
		for (int i = 0; i < current.childList.size(); i++) {
			TNode listNode = current.childList.get(i);

			if (word.charAt(0) == listNode.letter) {
				return word.length() == 1 ? listNode.endWordCount : getFrequency(word.substring(1), listNode);
			}
		}

		// if no node with matching letter is found, word doesn't exist in tree
		return 0;
	}

	/**
	 * Returns an ArrayList<String> of all words in the trie that have the given prefix.
	 * If no words match the prefix, return an empty ArrayList<String>.
	 * If an empty string is input, returns all words
	 * Must have Order(tree height) efficiency. In other words, you must traverse your trie :)
	 * NOTE: if your trie is made correctly, your traversal will produce a sorted list
	 *       so you should not need to perform a sorting algorithm on this list
	 * @param prefix (if an empty string is entered, returns all words)
	 * @return an ArrayList<String>
	 * @throws IllegalArgumentException if the prefix is null
	 */
	public ArrayList<String> getWordsWithPrefix(String prefix) throws IllegalArgumentException {
		if (prefix == null) {
			throw new IllegalArgumentException();
		}

		// return empty list if empty tree
		if (this.root == null) {
            return new ArrayList<String>();
        }

        // add words
        ArrayList<String> wordList = new ArrayList<String>();
        if (prefix.equals("")) {
            getWordsWithPrefix("", this.root, wordList);
        } else {
            findPrefix(prefix, prefix, this.root, wordList);
        }

        return wordList;
	}

    private void findPrefix(String prefix, String currPrefix, TNode current, ArrayList<String> wordList) {
        char firstLetter = currPrefix.charAt(0);

        for (TNode currentChild : current.childList) {
            if (currentChild.letter == firstLetter) {

                // prefix is found, start adding words
                if (currPrefix.length() == 1) {
                    getWordsWithPrefix(prefix, currentChild, wordList);

                    // recursively look for prefix
                } else {
                    findPrefix(prefix, currPrefix.substring(1), currentChild, wordList);
                }

                break;
            }
        }
    }

    private void getWordsWithPrefix(String prefix, TNode current, ArrayList<String> wordList) {
        if (current.isEndOfWord) {
            wordList.add(prefix);
        }

        String appendedPrefix;

        for (TNode currentChild : current.childList) {
//            System.out.println("current node is " + current.letter);
//            System.out.println("current prefix is " + prefix);
//            System.out.println("current child is " + currentChild.letter);
//            System.out.println("----------------------------");
            appendedPrefix = prefix + currentChild.letter;
            getWordsWithPrefix(appendedPrefix, currentChild, wordList);
        }
    }

    ;

	public static void main(String[] args) {
		TrieTree tree = new TrieTree();
		tree.insert("testing");
		tree.insert("lmao");
		tree.insert("lmbo");
		tree.insert("lmdo");
		tree.printTrie();
        System.out.println(tree.getWordsWithPrefix(""));
	}
}
