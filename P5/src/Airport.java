/**
 * Represents an airport. This class is completed for you.
 */
public class Airport {
	private String locid;
	private String city;
	private String name;
	private int rank;

	/**
	 * Constructor.
	 * @param locid
	 * @param city
	 * @param name
	 * @param rank
	 */
	public Airport(String locid, String city, String name, int rank) {
		this.locid = locid;
		this.city = city;
		this.name = name;
		this.rank = rank;
	}

	/**
	 * Gets airport id.
	 * @return
	 */
	public String getId() { return locid; }

	/**
	 * Gets airport city.
	 * @return
	 */
	public String getCity() { return city; }

	/**
	 * Gets airport name.
	 * @return
	 */
	public String getName() { return name; }
	
	/**
	 * Gets airport rank.
	 * @return
	 */
	public int getRank() { return rank; }

	/**
	 * Overrides the default toString() method so that an airport object may be printed.
	 * Usage: for an Airport object named airport, System.out.print(airport) will print the following to standard output:
	 *  [AIRPORT RANK] [AIRPORT ID]: [AIRPORT NAME], [AIRPORT CITY]
	 */
	@Override
	public String toString() {
		return "[" + rank + "] " + locid + ": " + name + ", " + city;
	}
}
