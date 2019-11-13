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
 * Checked exception thrown when a user attempts to insert or get key that is not found.
 */
@SuppressWarnings("serial")
public class KeyNotFoundException extends Exception {
    /**
     * constructor that requires no arguments
     */
    public KeyNotFoundException() {

    }

    /**
     * constructor that takes in an error message
     * @param message error message
     */
    public KeyNotFoundException(String message) {
        System.out.println(message);
    }
}
