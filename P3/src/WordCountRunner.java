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

import java.io.File;
import java.util.Scanner;

public class WordCountRunner {
    private static final String INPUT_FILE = "test.txt";

    public static void main(String[] args) {
        RedBlackTree<String, Integer> tree = new RedBlackTree<String, Integer>(); //root input???
        Scanner scnr;
        Integer wordCount;

        try {
            scnr = new Scanner(new File(INPUT_FILE));

            // loop through all lines
            String line;

            while (scnr.hasNext()) {
                line = scnr.nextLine();

                // clean a line
                // .replaceAll("[^a-zA-Z ]", "") removes all non-alphabetic characters (replaces them with the
                // empty string) and .split("\\s+") separates out the words using whitespace as a delimiter

                // this code should work without any modification
                String[] words = line.trim().replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");


                // for each word in words that has a length > 0
                // add word to tree OR if the word already exists increment the count

                for (String word : words) {
                    if (!word.equals("")) {
                        wordCount = tree.getValue(word);


                        if (wordCount != null) {
                            tree.insert(word, wordCount + 1);
                        } else {
                            tree.insert(word, 1);
                        }
                    }
                }
            }



        } catch (Exception e) {
            System.out.println("No file found");
        }

        tree.printSideways();
        // after all words are read in, print out this message
        System.out.println("The tree has a size of " + tree.getSize() + " and a height of " + tree.getHeight());

        // prompt user for words until they enter 'qqq'
        // below are lines of code you can use in your program

        Scanner input = new Scanner(System.in);
        String request = "";

        wordCount = 1;
        while (wordCount != null) {
            System.out.print("Enter a word to search for in \"" + INPUT_FILE + "\": ");
            request = input.next().replaceAll("[^a-zA-Z ]", "").toLowerCase();
            try {
                if (request.equals("qqq")) {
                    wordCount = null;
                } else {
                    wordCount = tree.getValue(request);
                    if (wordCount == null) {
                        wordCount = 0;
                        System.out.println("\"" + request + "\" was not found in the given file.");
                    } else {
                        System.out.println("\"" + request + "\" was found in the file " + wordCount + " times.");
                    }
                }

            } catch (Exception e) {
                System.out.println("Illegal key exception");
            }
        }


        System.out.println("Exiting.");
    }
}