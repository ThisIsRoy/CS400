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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Represents a social network.
 */
public class SocialNetwork {
	private static Graph<String> graph;
	private static String filename = "social-network.json";

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
		
		// TODO: create People objects by parsing JSON file and add them to array to be returned
        JSONParser parser = new JSONParser();


        try {
            // grab initial array of people
            Object obj = parser.parse(new FileReader("social-network.json"));
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
	private static void constructGraph(Person[] people) {
		for (Person person : people) {
			graph.addVertex(person.getName());
			for (String friend : person.getFriends()) {
				graph.addVertex(friend);
				graph.addEdge(person.getName(), friend);
			}
		}
	}

	public static void main(String[] args) {
		if (args.length > 0) {
			filename = args[0];	// allows alternate filename to be passed in through args
		}

		graph = new Graph<String>();
		try {
			Person[] persons = parseJSON();
			constructGraph(persons);

            List<String> people = graph.getAllVertices();
			Collections.sort(people);

            System.out.println("Network: " + people);
			for (String person : people) {
				List<String> friends = graph.getAdjacentVerticesOf(person);
				Collections.sort(friends);
				System.out.println(person + "'s friends: " + friends);
			}
		} catch (Exception e) {
            e.printStackTrace();
			System.out.println("Error: An unexpected exception occurred");
		}
	}
}
