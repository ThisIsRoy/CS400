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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Builds a trie with words from a .txt file, then uses the tree to predict
 * words given a prefix.
 */
public class TextPredictor {
	private static final String FILE_NAME = "harry_potter.txt";
	
	/**
	 * Main method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		TrieTree testTree = new TrieTree();
		System.out.println("testing printTrie on a tree with these inserts: yip, yes, pi, pie, yes, ye");
		testTree.insert("yip");
		testTree.insert("yes");
		testTree.insert("pi");
		testTree.insert("pie");
		testTree.insert("yes");
		testTree.insert("ye");
		testTree.printTrie();
		System.out.println("\n\n");
		
		TrieTree tree = new TrieTree();

		System.out.println("Welcome to the Text Predictor!");

		try {
			
			// scan words in file
			Scanner input = new Scanner(new File(FILE_NAME));

			while (input.hasNextLine()) {
				String line = input.nextLine();

				// clean file
				// .replaceAll("[^a-z ]", "") removes all non-alphabetic characters (replaces
				// them with the
				// empty string) and .split("\\s+") separates out the words using whitespace as
				// a delimiter
				String[] words = line.toLowerCase().replaceAll("[^a-z ]", "").split("\\s+");
				for (String word : words) {
					if (!word.equals("")) {
						tree.insert(word);
					}

				}
			}

			System.out.println("A tree of size " + tree.getSize() + " was created from file \"" + FILE_NAME + "\".");

			if (tree.getSize() < 7)
				tree.printTrie();


			input = new Scanner (System.in);
			String request = "";
			do {
				System.out.println("\nEnter a word to auto-complete from the file \"" + FILE_NAME + "\" : ");

				request = input.next().toLowerCase().replaceAll("[^a-z ]", "");

				if (request.equals("qqq")) {
					System.out.println("Exiting.");
					break;
				}

				ArrayList<String> predictedWords = tree.getWordsWithPrefix(request);

				if (predictedWords.isEmpty()) {
					System.out.println("There are no matches for the prefix \"" + request + "\"");
				} else {
					int maxFrequency = 0;
					String maxWord = "FIX_ME";
					for (String word : predictedWords) {
						// TODO: find the word with the given prefix that occurs the most in the text.
						int frequency = tree.getFrequency(word);
						if (frequency > maxFrequency) {
							maxFrequency = frequency;
							maxWord = word;
						}
						if (predictedWords.size() < 7) {
							System.out.println(word + ": " + frequency);
						}
					}
					System.out.println("The predicted word is: " + maxWord);
				}

			} while (request != "qqq");

		} catch (FileNotFoundException e) {
			System.out.println("The input file \"" + FILE_NAME + "\" was not found.");
		}  
		


		
		System.out.println("Thanks for using the Text Predictor!");

	}
}