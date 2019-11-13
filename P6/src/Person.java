//
// Title:           implementation of a graph
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
 * Represents the model of the input json file. A Person object stores the person's name and their friends' names.
 */
public class Person {
	private String name; 
	private String[] friends; 

	public String getName() { return name; }
	public String[] getFriends() { return friends; }
	public void setName(String name) { this.name = name; }
	public void setFriends(String[] friends) { this.friends = friends; }
}
