/**
 * Represents an airport. This class is completed for you.
 */
public class Airport {
	private String locid;
	private String city;
	private String airportName;

	/**
	 * Constructor.
	 * @param locid
	 * @param city
	 * @param airportName
	 */
	public Airport(String locid, String city, String airportName) {
		this.locid = locid;
		this.city = city;
		this.airportName = airportName;
	}

	/**
	 * Overrides the default toString() method so that an airport object may be printed.
	 * Usage: for an Airport object named airport, System.out.print(airport) will print the following to standard output:
	 * [AIRPORT ID]: [AIRPORT NAME], [AIRPORT CITY]
	 */
	@Override
	public String toString() {
		return locid + ": " + airportName + ", " + city;
	}
}