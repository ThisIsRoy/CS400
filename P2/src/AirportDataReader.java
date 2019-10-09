//
// Title:           P2 - implementation of an AVL tree
// Files:           Requires: AVLTree.java, Airport.java
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

// remember that you are not allowed to add any public methods or fields
// but you can add any private methods or fields

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Reads in airport data from a CSV file, constructs an AVL tree, and prompts user to look up an airport.
 */
public class AirportDataReader {
	/**
	 * Main method.
	 * @param args
	 */
	public static void main(String[] args) {
		AVLTree<String, Airport> tree = new AVLTree<String, Airport>();

		// parse csv
		try {
			Scanner csvScnr = new Scanner(new File("airportdata.csv"));
			while (csvScnr.hasNextLine()) {
				String row = csvScnr.nextLine();
				String[] data = row.split(",");
                Airport airport = new Airport(data[3], data[4], data[5]);

                // insert airports from file
                try {
                    tree.insert(data[3], airport);
                } catch (IllegalKeyException e) {
                    System.out.println("Illegal key");
                } catch (DuplicateKeyException f) {
                    System.out.println("Duplicate key");
                }
			}
			csvScnr.close();
		} catch (FileNotFoundException e1) {
			System.out.println("File not found.");
		}

        String input;
        Airport output = new Airport("", "", "");
        Scanner scanner = new Scanner(System.in);

		while (output != null) {
		    // grab user input
            System.out.print("Enter 3-digit airport id: ");
            input = scanner.nextLine();

            // try to find airport in AVL tree
            if (input.length() != 3) {
                output = null;

            } else {
                try {
                    output = tree.get(input);

                    if (output != null) {
                        System.out.println(output);
                    }

                } catch (Exception e) {
                    System.out.println("Illegal key");
                }
            }
        }

//        try {
//            System.out.println(tree.get("MKE"));
//
//        } catch (Exception exception) {
//            System.out.println("lmaoooo");
//        }
	}
}