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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

    public class Graph<T> implements GraphADT<T> {
    private class Node {
        private T value;
        private Node next;

        private Node(T value) {
            this.value = value;
        }
    }

    private HashMap<T, Node> map;

    /**
     * constructor for making empty graph
     */
    public Graph() {
        this.map = new HashMap<T, Node>();
    }

    /**
     * Adds a new vertex to the graph. If the vertex already exists in the graph, returns without throwing an exception
     * or adding a vertex.
     * @param vertex to be added
     * @throws IllegalArgumentException if the vertex is null
     */
    public void addVertex(T vertex) throws IllegalArgumentException {
        if (vertex == null) {
            throw new IllegalArgumentException();
        }

        if (!map.containsKey(vertex)) {
            map.put(vertex, null);
        }
    }

    /**
     * Removes a vertex and all associated edges from the graph. If the vertex does not exist in the graph, returns
     * without throwing an exception or removing a vertex.
     * @param vertex to be removed
     * @throws IllegalArgumentException if the vertex is null
     */
    public void removeVertex(T vertex) throws IllegalArgumentException {
        if (vertex == null) {
            throw new IllegalArgumentException();
        }

        map.remove(vertex);
    }

    /**
     * Adds an undirected edge from vertex1 to vertex2 to the graph. If either vertex does not exist in the graph or if
     * the edge already exists in the graph, returns without throwing an exception or adding an edge.
     * @param vertex1 first vertex
     * @param vertex2 second vertex
     * @throws IllegalArgumentException if either vertex is null
     */
    public void addEdge(T vertex1, T vertex2) throws IllegalArgumentException {
        if (vertex1 == null || vertex2 == null) {
            throw new IllegalArgumentException();
        }

        // check if both keys exist in graph
        if (!map.containsKey(vertex1) || !map.containsKey(vertex2)) {
            System.out.println("error 1");
            return;
        }

        // ignore loops
        if (vertex1.equals(vertex2)) {
            System.out.println("error 2");
            return;
        }

        //add edges to both sides
        addEdgeTo(vertex1, vertex2);
        addEdgeTo(vertex2, vertex1);
    }

    /**
     * helper function that adds vertex2 to the linked list of vertex1 in the map
     * @param vertex1 vertex to add node to
     * @param vertex2 node value to add
     */
    private void addEdgeTo(T vertex1, T vertex2) {
        if (map.get(vertex1) == null) {
            map.put(vertex1, new Node(vertex2));

            // start add algorithm only if value doesn't already exist
        } else if (!listContainsNode(map.get(vertex1), vertex2)) {
            System.out.println("adding " + vertex2 + " to the list of " + vertex1);
            addNodeToList(map.get(vertex1), vertex2);
        }
    }

    /**
     * helper function for checking if value exists in linked list
     * @param node linked list starting node
     * @param vertex2 value to look for
     * @return whether the list contains the vertex2 value
     */
    private boolean listContainsNode(Node node, T vertex2) {
        if (node == null) {
            return false;

            // found node
        } else if (node.value.equals(vertex2)) {
            return true;

            // end of list
        } else if (node.next == null) {
            return false;

            // recursion
        } else {
            return listContainsNode(node.next, vertex2);
        }
    }

    /**
     * helper function for adding node to linked list
     * @param node current node
     * @param vertex2 node value to add
     */
    private void addNodeToList(Node node, T vertex2) {
        if (node.next == null) {
            // System.out.println("added " + vertex2 + " to the node of " + node.value);
            node.next = new Node(vertex2);
            // System.out.println(node.value + "'s next node is " + node.next.value);
        } else {
            // System.out.println("have to add " + vertex2 + "to the next node of " + node.value + " which is " + node.next);
            addNodeToList(node.next, vertex2);
        }
    }

    /**
     * Removes the undirected edge from vertex1 to vertex2 from the graph. If either vertex does not exist in the graph
     * or if the edge does not exist in the graph, returns without throwing an exception or removing an edge.
     * @param vertex1 first vertex
     * @param vertex2 second vertex
     * @throws IllegalArgumentException if either vertex is null
     */
    public void removeEdge(T vertex1, T vertex2) throws IllegalArgumentException {
        if (vertex1 == null || vertex2 == null) {
            throw new IllegalArgumentException();
        }

        // check if both keys exist in graph
        if (!map.containsKey(vertex1) || !map.containsKey(vertex2)) {
            return;
        }

        // perform deletion algorithm
        map.put(vertex1, removeEdgeFrom(map.get(vertex1), vertex2));
        map.put(vertex2, removeEdgeFrom(map.get(vertex2), vertex1));
    }

    /**
     * helper function for deleting node from linked list
     * @param node current node
     * @param vertex2 value of node to delete
     * @return current node
     */
    private Node removeEdgeFrom(Node node, T vertex2) {
        if (node != null) {
            // remove the found node
            if (node.value.equals(vertex2)) {
                return node.next;

                // recursively look through linked list
            } else {
                if (node.next != null) {
                    node.next = removeEdgeFrom(node.next, vertex2);
                }
            }
            return node;
        }

        return null;
    }

    /**
     * Returns a list containing all vertices in the graph.
     * @return List<T> where T is the vertex type
     */
    public List<T> getAllVertices() {
        List<T> list = new ArrayList<>();

        // add all keys
        for (T key : map.keySet()) {
            list.add(key);
        }

        return list;
    }

    /**
     * Get all neighboring (adjacent) vertices of a vertex. If the vertex does not exist in the graph, returns an empty
     * list.
     * @param vertex specified vertex
     * @return List<T> of all adjacent vertices for the specified vertex
     * @throws IllegalArgumentException if the vertex is null
     */
    public List<T> getAdjacentVerticesOf(T vertex) throws IllegalArgumentException {
        if (vertex == null) {
            throw new IllegalArgumentException();
        }

        List<T> list = new ArrayList<>();
        if (!map.containsKey(vertex)) {
            return list;
        }

        Node current = map.get(vertex);

        // iterate through linked list to add values to list
        while (current != null) {
            // System.out.println("current value is " + current.value);
//            if (current.next != null) {
//                System.out.println("next value is " + current.next.value);
//            } else {
//                System.out.println(current.value + " next is null");
//            }
            list.add(current.value);
            current = current.next;
        }

        return list;
    }

    /**
     * Returns the number of edges in the graph.
     * @return number of edges in graph
     */
    public int size() {
        int size = 0;
        for (T key : map.keySet()) {
            size += numOfNodesInList(map.get(key));
        }

        // each edge is double counted
        return size / 2;
    }

    /**
     * helper function for finding number of nodes in linked list
     * @param node current node
     * @return number of nodes including current node
     */
    private int numOfNodesInList(Node node) {
        // base case
        if (node == null) {
            return 0;
        }

        // recursive case
        return 1 + numOfNodesInList(node.next);
    }

    /**
     * Returns the number of vertices in the graph.
     * @return number of vertices in graph
     */
    public int order() {
        return map.keySet().size();
    }

    public static void main(String[] args) {
        Graph<String> graph = new Graph<String>();

        graph.addVertex("a");
        graph.addVertex("b");
        graph.addVertex("c");
        graph.addVertex("d");
        graph.addEdge("a", "b");
        graph.addEdge("a", "c");
        System.out.println(graph.getAdjacentVerticesOf("a").size());
    }
}
