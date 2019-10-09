//
// Title:           P2 - implementation of an AVL tree
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

// remember that you are not allowed to add any public methods or fields
// but you can add any private methods or fields

/**
 * Represents an airport. This class is completed for you.
 */
public class Airport {
	private String locid;
	private String city;
	private String name;

	/**
	 * Constructor.
	 * @param locid
	 * @param city
	 * @param airportName
	 */
	public Airport(String locid, String city, String airportName) {
		this.locid = locid;
		this.city = city;
		this.name = airportName;
	}
	
	/**
	 * Gets airport id.
	 * @return
	 */
	public String getId() {
		return locid;
	}
	
	/**
	 * Gets airport city.
	 * @return
	 */
	public String getCity() {
		return city;
	}
	
	/**
	 * Gets airport name.
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Overrides the default toString() method so that an airport object may be printed.
	 * Usage: for an Airport object named airport, System.out.print(airport) will print the following to standard output:
	 * [AIRPORT ID]: [AIRPORT NAME], [AIRPORT CITY]
	 */
	@Override
	public String toString() {
		return locid + ": " + name + ", " + city;
	}
}