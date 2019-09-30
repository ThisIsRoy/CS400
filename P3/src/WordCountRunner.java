import java.io.File;
import java.util.Scanner;

public class WordCountRunner {
	private static final String INPUT_FILE = "test.txt";

	public static void main(String[] args) {
		RedBlackTree<String, Integer> tree = new RedBlackTree<String, Integer>(root);
		Scanner scnr;

        try {
            scnr = new Scanner(new File(INPUT_FILE));
        } catch (Exception e) {
            System.out.println("No file found");
        }

		// loop through all lines
		   String line = scnr.nextLine();

				// clean a line
				// .replaceAll("[^a-zA-Z ]", "") removes all non-alphabetic characters (replaces them with the
				// empty string) and .split("\\s+") separates out the words using whitespace as a delimiter

				// this code should work without any modification
				String[] words = line.trim().replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");


				// for each word in words that has a length > 0
				   // add word to tree OR if the word already exists increment the count

		tree.printSideways();
		// after all words are read in, print out this message
	   System.out.println("The tree has a size of " + tree.getSize() + " and a height of " + tree.getHeight());

		// prompt user for words until they enter 'qqq'
		// below are lines of code you can use in your program

		Scanner input = new Scanner(System.in);
		String request = "";
		System.out.print("Enter a word to search for in \"" + INPUT_FILE + "\": ");
		request = input.next().replaceAll("[^a-zA-Z ]", "").toLowerCase();
		System.out.println("\"" + request + "\" was found in the file " + tree.getValue(request) + " times.");
		System.out.println("Exiting.");
		System.out.println("\"" + request + "\" was not found in the given file.");

	}
}