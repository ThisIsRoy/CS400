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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Enum representing user input command options.
 * I: Insert
 * G: Get
 * R: Remove
 * Q: Quit
 */
enum UserCommand {
	I, G, R, P, Q
}

/**
 * Reads in airport data from a CSV file, constructs an AVL tree, and prompts user to look up an airport.
 */
public class AirportDataReader {
	private static HashTable<String, Airport> hashTable = new HashTable<String, Airport>();
	private static String filename = "airportdatareduced.csv";

	/**
	 * Parses a csv file, creates new Airport objects from each row, and inserts each Airport object into the hash
	 * table.
	 */
	private static void parseCSV() {
        String data;
        String[] values;
        String locid;
        String city;
        String name;
        String rank;
        Airport airport;
        File file = new File(filename);

        try {
            Scanner scanner = new Scanner(file);
            data = scanner.nextLine();

            // initialize variables
            while (scanner.hasNext()) {
                data = scanner.nextLine();
                values = data.split(",");
                locid = values[3];
                city = values[4];
                name = values[5];
                rank = values[0];
                airport = new Airport(locid, city, name, Integer.parseInt(rank));

                // try adding airport
                try {
                    hashTable.insert(locid, airport);
                } catch (DuplicateKeyException dup) {
                    System.out.println("Duplicate key");
                } catch (NullKeyException nullKey) {
                    System.out.println("Null key");
                }
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            // print statement for when file is not found
            System.out.println("File not found.");
        }
	}

	/**
	 * Prompts the user for commands until the command 'Q' is entered.
	 */
	private static void promptUserForCommands() {
		// prompt user for commands
		Scanner scnr = new Scanner(System.in);

		while (true) {
			System.out.print("Enter command ([I]:insert  [G]:get  [P]:print  [R]:remove  [Q]:quit): ");
			try {
				// switch on value of enum
				switch (UserCommand.valueOf(scnr.nextLine().toUpperCase())) {
				case I:
					System.out.println("New Airport:");
					System.out.print("\tID (3 letters): ");
					String id = scnr.nextLine().toUpperCase();
					if (!id.matches("([A-Z][A-Z][A-Z])")) {
						System.out.println("Invalid ID.");
						break;
					}
					System.out.print("\tCity: ");
					String city = scnr.nextLine();
					System.out.print("\tName: ");
					String name = scnr.nextLine();
					System.out.print("\tRank (number): ");
					if (!scnr.hasNextInt()) {
						System.out.println("Invalid rank.");
						scnr.nextLine();	// clear scanner
						break;
					}
					int rank = scnr.nextInt();
					scnr.nextLine();	// clear scanner

					handleInsert(id, city, name, rank);
					break;
				case G:
					System.out.print("Enter airport id: ");
					handleGet(scnr.nextLine().toUpperCase());
					break;
				case P:
					hashTable.printHashTable();
					break;
				case R:
					System.out.print("Enter airport id: ");
					handleRemove(scnr.nextLine().toUpperCase());
					break;
				case Q:
					System.out.println("Goodbye!");
					scnr.close();
					System.exit(0);
					break;
				default:
					break;
				}
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid command. Please try again.");
			}
		}
	}

	/**
	 * Handles the insert user input command.
	 * @param id airport id
	 * @param city airport city
	 * @param name airport name
	 */
	private static void handleInsert(String id, String city, String name, int rank) {
		try {
			Airport airport = new Airport(id, city, name, rank);
			hashTable.insert(id, airport);
			System.out.println();
		} catch (DuplicateKeyException e) {
			System.out.println("\t" + id + " is already in the airport database.");
		} catch (NullKeyException e) {
			System.out.println("\tAirport ID cannot be null.");
		}
	}

	/**
	 * Handles the get user input command.
	 * @param id airport id
	 */
	private static void handleGet(String id) {
        try {
            Airport airport = hashTable.get(id);
            // print statement for displaying the airport to the user
            System.out.println("\t" + airport);
        } catch (KeyNotFoundException noKey) {
            // print statement for when the key is not found
            System.out.println("\t" + id + " was not found in the airport database.");
        } catch (NullKeyException nullKey) {
            // print statement for when a null key is passed in
            System.out.println("\tAirport ID cannot be null.");
        }






	}

	/**
	 * Handles the remove user input command.
	 * @param id airport id
	 */
	private static void handleRemove(String id) {
        try {
            boolean removed = hashTable.remove(id);

            if (removed) {
                // print statement for when an airport was successfully removed
                System.out.println("\t" + id + " was removed from the database.");
            } else {
                // print statement for when the airport was not found
                System.out.println("\t" + id + " was not found in the database.");
            }
        } catch (NullKeyException nullKey) {
            // print statement for when a null key is passed in
            System.out.println("\tAirport ID cannot be null.");
        }
	}

	/**
	 * Main method.  You should not need to modify this in any way.
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length > 0) {
			filename = args[0];	// allows alternate filename to be passed in through args
		}
		parseCSV();
		hashTable.printHashTable();
		promptUserForCommands();
	}
}
