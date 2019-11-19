//
// Title:           friend graph algorithms
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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Represents a social network.
 */
public class SocialNetwork implements SocialNetworkADT {
    private Graph<String> graph;
    private static String filename;

    /**
     * Constructs a social network from a json file.
     * @param filename json file
     */
    public SocialNetwork(String filename) {
        SocialNetwork.filename = filename;

        this.graph = new Graph<String>();
        try {
            Person[] persons = parseJSON();
            constructGraph(persons);
            List<String> people = this.graph.getAllVertices();
            Collections.sort(people);
			/* feel free to use this code for testing purposes
			System.out.println("Network: " + people);
			for (String person : people) {
				List<String> friends = this.graph.getAdjacentVerticesOf(person);
				Collections.sort(friends);
				System.out.println(person + "'s friends: " + friends);
			}
			*/
        } catch (Exception e) {
            System.out.println("Error: An unexpected exception occurred");
        }
    }

    /**
     * Parses the input JSON file.
     * @return array of Person objects which stores information about a single person
     * @throws Exception like FileNotFound, JsonParseException
     */
    private static Person[] parseJSON() throws Exception {
        // array storing the Person objects created from the JSON file to be loaded later in the graph
        Person[] people;
        ArrayList<Person> list = new ArrayList<Person>();
        Person person;
        JSONObject jsonObj;
        JSONArray jsonList;
        String[] jsonPeople;

        JSONParser parser = new JSONParser();


        try {
            // grab initial array of people
            Object obj = parser.parse(new FileReader(filename));
            jsonObj = (JSONObject) obj;
            JSONArray peopleArray = (JSONArray) jsonObj.get("socialNetwork");

            for (Object jsonPerson : peopleArray) {
                // create new person
                person = new Person();
                jsonObj = (JSONObject) jsonPerson;

                // fill in name and friends from json format
                person.setName((String) jsonObj.get("name"));
                jsonList = (JSONArray) jsonObj.get("friends");
                jsonPeople = new String[jsonList.size()];
                for (int i = 0; i < jsonList.size(); i++) {
                    jsonPeople[i] = jsonList.get(i).toString();
                }
                person.setFriends(jsonPeople);
                list.add(person);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException io) {
            System.out.println("IO exception encountered");
        } catch (Exception e) {
            System.out.println("Exception encountered");
        }

        people = new Person[list.size()];
        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i).getName());
//            System.out.println(list.get(i).getFriends().length);
            people[i] = list.get(i);
        }
        return people;
    }

    /**
     * Construct an undirected graph from array of Person objects.
     * @param people an array of People objects generated from a json file
     */
    private void constructGraph(Person[] people) {
        for (Person person : people) {
            graph.addVertex(person.getName());
            for (String friend : person.getFriends()) {
                graph.addVertex(friend);
                graph.addEdge(person.getName(), friend);
            }
        }
    }

    // TODO: add graph algorithm methods from SocialNetworkADT
    /**
     * Return the average number of friends people in the social network have. Use the half round up rounding technique
     * (examples: 6.4 rounds to 6, 6.5 rounds to 7, 6.6 rounds to 7). If the network is empty return 0.
     * @return average number of friends per person
     */
    public int averageFriendsPerPerson() {
        // no friends in graph
        if (graph.order() == 0) {
            return 0;
        }

        return (int) Math.rint(2.0 * graph.size() / graph.order());
    }

    /**
     * Given two people, return their mutual friends. If either person parameter is null or does not exist in the
     * network, return an empty Set.
     * @param person1 first person
     * @param person2 second person
     * @return mutual friends
     */
    public Set<String> mutualFriends(String person1, String person2) {
        Set<String> person1Friends = new HashSet<String>(graph.getAdjacentVerticesOf(person1));
        Set<String> person2Friends = new HashSet<String>(graph.getAdjacentVerticesOf(person2));

        // return intersection of two sets
        person1Friends.retainAll(person2Friends);
        return person1Friends;
    }

    /**
     * Return the person in the social network who has the most friends. If there is a tie return the social butterfly
     * who comes first in alphabetical order. If the network is empty return the empty string "".
     * @return the social butterfly
     */
    public String socialButterfly() {
        // empty network
        if (graph.order() == 0) {
            return "";
        }

        List<String> butterflyList = new ArrayList<String>();
        int friendNum;
        int maxFriendNum = 0;

        for (String person : graph.getAllVertices()) {
            friendNum = graph.getAdjacentVerticesOf(person).size();

            // current person has more friends than the max, set to be max
            if (friendNum > maxFriendNum) {
                butterflyList.clear();
                butterflyList.add(person);
                maxFriendNum = friendNum;

                // current person has same # of friends as max, add to list
            } else if (friendNum == maxFriendNum) {
                butterflyList.add(person);
            }
        }

        return Collections.min(butterflyList);
    }

    /**
     * Return the person who has the most friends of friends. Include friends in this count as well as friends of
     * friends, but be careful not to double-count if a friend is also a friend of a friend. If there is a tie return
     * the influencer who comes first in alphabetical order. If the network is empty return the empty string "".
     * @return the influencer
     */
    public String influencer() {
        // empty network
        if (graph.order() == 0) {
            return "";
        }

        List<String> influencerList = new ArrayList<String>();
        int friendNum;
        int maxFriendNum = 0;

        for (String person : graph.getAllVertices()) {
            friendNum = getFriendsFriends(person);

            // current person has more friends than the max, set to be max
            if (friendNum > maxFriendNum) {
                influencerList.clear();
                influencerList.add(person);
                maxFriendNum = friendNum;

                // current person has same # of friends as max, add to list
            } else if (friendNum == maxFriendNum) {
                influencerList.add(person);
            }
        }

        return Collections.min(influencerList);
    }

    /**
     * helper function for influencer
     * get number of friends + friend's friends for a given person
     * @param person person
     * @return number of friends + friend's friends
     */
    private int getFriendsFriends(String person) {
        Set<String> friendsFriends = new HashSet<String>();

        // add each friend and their friends to set
        for (String friend : graph.getAdjacentVerticesOf(person)) {
            friendsFriends.add(friend);
            friendsFriends.addAll(graph.getAdjacentVerticesOf(friend));
        }

        return friendsFriends.size();
    }

    /**
     * Given a person who posts a meme online and assuming that it spreads to all of their friends on the second day,
     * all of their friends' friends on the third day, etc., return the set of all people who have seen the meme
     * (including the meme's creator) after the provided number of days. In other words, if Katie starts the meme and
     * the days parameter is 3, then on Day 1 the set of people who have seen the meme would be Katie. On Day 2, the
     * set of people would be Katie and Katie's friends. On Day 3, the set of people would be Katie, Katie's friends,
     * and Katie's friends' friends. If the person parameter is null or does not exist in the network, or the number of
     * days is less than 1, return an empty Set.
     * @param person person starting meme
     * @param days days since meme was posted
     * @return all people who have seen the meme
     */
    public Set<String> haveSeenMeme(String person, int days) {
        Set<String> seenSet = new HashSet<String>();
        Set<String> addSet = new HashSet<String>();

        // return empty set if conditions don't make sense
        if (person == null || !graph.getAllVertices().contains(person) || days < 1) {
            return seenSet;
        }

        seenSet.add(person);

        // loop through the set of people's friends once for every day
        for (int i = 0; i < days - 1; i++) {
            addSet.clear();
            for (String friend : seenSet) {
                addSet.addAll(graph.getAdjacentVerticesOf(friend));
            }
            seenSet.addAll(addSet);
        }

        return seenSet;
    }

    /**
     * Given a person, return a set of all of the people they might know: these are their friends' friends who are not
     * already their friends. If the person parameter is null or does not exist in the network, return an empty Set.
     * @param person person in question
     * @return set of friends' friends who are not already a friend
     */
    public Set<String> youMayKnow(String person) {
        Set<String> mayKnowSet = new HashSet<String>();

        // return empty set if conditions don't make sense
        if (person == null || !graph.getAllVertices().contains(person)) {
            return mayKnowSet;
        }

        // add all friends of friends into mayKnowSet
        Set<String> friendsSet = new HashSet<String>(graph.getAdjacentVerticesOf(person));
        for (String friend : friendsSet) {
            mayKnowSet.addAll(graph.getAdjacentVerticesOf(friend));
        }

        // mayKnowSet minus friendsSet
        mayKnowSet.removeAll(friendsSet);
        mayKnowSet.remove(person);
        return mayKnowSet;
    }

    /**
     * Given a set of people, find out if they are a friend group, i.e., each person in the group is a friend of every
     * other person in the group. If the people parameter has a size less than 2, return false.
     * @param people
     * @return
     */
    public boolean isFriendGroup(Set<String> people) {
        // people size is less than 2
        if (people.size() < 2) {
            return false;
        }


        // check every combination of friends for strongly connected graph
        for (String person : people) {
            for (String friend : people) {
                if (!person.equals(friend)) {
                    if (!graph.getAdjacentVerticesOf(person).contains(friend)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Six degrees of separation is the idea that all people are six, or fewer, social connections away from each
     * other. In other words, a chain of "a friend of a friend" statements can be made to connect any two people in a
     * maximum of six steps. If the network is empty return true.
     * @return true if the theory holds for this network, otherwise false
     */
    public boolean sixDegreesOfSeparation() {
        HashMap<String, Double> distance = new HashMap<String, Double>();

        //trivial case
        if (graph.getAllVertices().size() < 2) {
            return true;
        }

        for (String person : graph.getAllVertices()) {
            // check the distance from start node using dijkstra's algorithm
            distance = dijkstra(this.graph, person).get(0);
            for (String personDist : distance.keySet()) {
                if (distance.get(personDist) > 6.0) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * helper function for finding shortest path from person to each other person in a social network graph
     * @param startPerson starting person to find distances for
     * @return list of two values, the distance map and the predecessor map
     */
    private List<HashMap> dijkstra(Graph<String> graph, String startPerson) {
        String current = startPerson;
        Double min;
        Set<String> visited = new HashSet<String>();
        Set<String> unvisited = new HashSet<String>(graph.getAllVertices());
        HashMap<String, Double> distance = new HashMap<String, Double>();
        HashMap<String, String> predecessor = new HashMap<String, String>();

        // each unvisited node starts with a distance of infinity and a predecessor of null
        for (String person : graph.getAllVertices()) {
            distance.put(person, Double.POSITIVE_INFINITY);
            predecessor.put(person, null);
        }
        distance.put(startPerson, 0.0);

        while (current != null) {
            visited.add(current);
            unvisited.remove(current);

            for (String friend : graph.getAdjacentVerticesOf(current)) {
                // update distance and predecessor if path from current is shorter
                if (!visited.contains(friend) && distance.get(current) + 1 < distance.get(friend)) {
                    distance.put(friend, distance.get(current) + 1);
                    predecessor.put(friend, current);
                }
            }

            // find connected unvisited node with smallest distance value
            current = null;
            min = Double.POSITIVE_INFINITY;
            for (String neighbor : unvisited) {
                // next reachable node
                if (distance.get(neighbor) < min) {
                    min = distance.get(neighbor);
                    current = neighbor;

                    // traverse alphabetically when ties in distance occur
                } else if (!min.equals(Double.POSITIVE_INFINITY) && distance.get(neighbor).equals(min)) {
                    if (neighbor.compareTo(current) < 0) { // current can't be null because min isn't infinity
                        current = neighbor;
                    }
                }
            }
        }

        // add two maps to a list to return
        List<HashMap> distAndPred = new ArrayList<HashMap>();
        distAndPred.add(distance);
        distAndPred.add(predecessor);
        return distAndPred;
    }

    /**
     * Given two people, the first one wanting to be friends with the second, return a list, in order, of the fewest
     * amount of people the first person can befriend to get to the second person. If there is a tie between two paths
     * choose the path which has the starting person who comes first alphabetically (ex: choose Alex->Jess over
     * Bailey->Jess) If either person parameter is null or does not exist in the network, or if there is no path from
     * person1 to person2, return an empty List.
     * @param person1 the person who is seeking to be friends with person2
     * @param person2 the person with whom person1 is seeking to be friends with
     * @return the ordered list of people in the social ladder
     */
    public List<String> socialLadder(String person1, String person2) {
        List<String> path = new ArrayList<String>();
        if (person1 == null || person2 == null || !graph.getAllVertices().contains(person1) || !graph.getAllVertices().contains(person2)) {
            return path;
        }

        if (person1.equals(person2)) {
            path.add(person1);
            return path;
        }

        // use dijsktra to find mapping of graph
        HashMap<String, String> predecessor = dijkstra(this.graph, person1).get(1);
        String current = predecessor.get(person2);
        if (current == null) {
            return path;
        }

        path.add(person2);
        path.add(current);

        // go backwards using predecessor mapping
        while (predecessor.get(current) != null) {
            current = predecessor.get(current);
            path.add(current);
        }

        if (current.equals(person1)) {
            Collections.reverse(path);
            return path;
        } else {
            path.clear();
            return path;
        }

    }

    /**
     * Given a set of people, find out if there is one friend which, without that person, the sub-network would fall
     * apart (in other words, there would be two separate groups of people with no connection between them). If there
     * are multiple people who could be the "glue", return the one that comes first alphabetically. If there is no
     * single person that is the "glue" of this set of people, return the empty string "". If the set of people has a
     * size less than 3 or is already disconnected, return the empty string "".
     * @param people
     * @return glue of the network, without whom, the network would fall apart; null if there is no glue person
     */
    public String glue(Set<String> people) {
        if (people.size() < 3) {
            return "";
        }

        // construct subgraph and check connection
        Graph<String> subgraph = new Graph<String>();
        for (String person : people) {
            subgraph.addVertex(person);
        }
        for (String person : people) {
            for (String friend : graph.getAdjacentVerticesOf(person)) {
                if (people.contains(friend)) {
                    subgraph.addEdge(person, friend);
                }
            }
        }

        if (!isConnected(subgraph)) {
            return "";
        }

        List<String> glue = new ArrayList<String>();
        List<String> friends = new ArrayList<String>();

        // remove each person and check if graph is connected
        for (String person : people) {
            friends = subgraph.getAdjacentVerticesOf(person);
            subgraph.removeVertex(person);
            if (!isConnected(subgraph)) {
                glue.add(person);
            }

            // add removed person and friends back
            subgraph.addVertex(person);
            for (String friend : friends) {
                subgraph.addEdge(person, friend);
            }
        }

        if (glue.size() > 0) {
            return Collections.min(glue);
        } else {
            return "";
        }
    }

    /**
     * helper function for glue
     * determines if the undirected graph is connected
     * @param graph graph to check
     * @return whether or not the graph is connected
     */
    private boolean isConnected(Graph<String> graph) {
        // trivial connection
        if (graph.getAllVertices().size() < 2) {
            return true;
        }

        List<String> list = new ArrayList<String>(graph.getAllVertices());
        HashMap<String, Double> distance = dijkstra(graph, list.get(0)).get(0);

        // use dijsktra to ensure every person is reachable
        for (String person : graph.getAllVertices()) {
            if (distance.get(person).equals(Double.POSITIVE_INFINITY)) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
//        List<String> test = new ArrayList<>();
//        test.add("Addison");
//        test.add("Lilly");
//        System.out.println(Collections.min(test));
    }
}
