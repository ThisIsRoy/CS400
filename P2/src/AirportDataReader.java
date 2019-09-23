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

					// TODO: extract airport data: ID, city, name
					// TODO: create airport object
					// TODO: insert airport into tree
					// TODO: catch exceptions

			}
			csvScnr.close();
		} catch (FileNotFoundException e1) {
			System.out.println("File not found.");
		}

		// TODO: prompt user to look up airport
		// TODO: accept user input of 3-digit code
		// TODO: get airport object, print to standard output
		// TODO: exit when a user enters an airport code that is not found
	}
}