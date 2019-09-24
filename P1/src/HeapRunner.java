//
// Title:           runs string heap to read files
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

public class HeapRunner {
    public static void main(String[] args) {
        // instantiate your heap
        StringHeap heap = new StringHeap(10);

        // TODO: add a try/catch block to make the code below compile

        try {
            Scanner scnr = new Scanner(new File("words1.txt"));

            // keep adding words
            while (scnr.hasNextLine()) {
                String word = scnr.nextLine();
                heap.add(word);
            }

            heap.printHeap();

            System.out.println("------------------------------");

            heap.printLevelOrderTraversal();

        } catch(FileNotFoundException e) {
            System.out.println("No file found");
        }
    }
}



