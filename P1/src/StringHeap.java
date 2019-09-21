//
// Title:           implementation of a string heap
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
// Online Sources:  log2 in java: https://www.techiedelight.com/calculate-log-base-2-in-java/
//

import java.util.ArrayList;

public class StringHeap {
    // instance variables; you may add additional private fields
    private String[] heap;
    private int size;

    /*
     * constructor (this code is done for you)
     *
     * @param the initial heap capacity
     */
    public StringHeap(int capacity) {
        heap = new String[capacity];
        size = 0;
    }

    /*
     * determines which String has higher priority, using rules in this order:
     *     0) convert each String to lower case first, since case does not matter
     *     1) if lengths are different, the largest length String has priority
     *     2) else if numVowels (a,e,i,o,u) are different, the String with the most vowels has priority
     *     3) else, alphabetical order (since already in lower case, use .compareTo() in the String class)
     *
     * @param first the first String
     * @param second the second String
     * @return a positive integer if first has higher priority; a negative integer if
     * second has higher priority; 0 if the priorities are equal
     */
    public static int prioritize(String first, String second) {

        // check for null values
        if (first == null && second == null) {
            return 0;
        } else if (first == null) {
            return -1;
        } else if (second == null) {
            return 1;
        }

        first = first.toLowerCase();
        second = second.toLowerCase();
        int compare;

        compare = Integer.compare(first.length(), second.length());

        // strings have different length
        if (compare != 0) {
            return compare;

            // strings have same length, compare number of vowels
        } else {
            int firstNumOfVowels = numOfVowels(first);
            int secondNumofVowels = numOfVowels(second);
            compare = Integer.compare(firstNumOfVowels, secondNumofVowels);

            if (compare != 0) {
                return compare;

                // strings have same number of vowels, compare alphabetical order
            } else {
                return -1 * first.compareTo(second);
            }
        }
    }

    /**
     * check for number of vowels in input
     * @param input input string
     * @return integer for number of vowels in input string
     */
    private static int numOfVowels(String input) {
        int numOfVowels = 0;
        String vowels = "aeiou";

        // loop through each character in input
        for (int i = 0; i < input.length(); i++) {
            String character = Character.toString(input.charAt(i));
            if (vowels.contains(character)) {
                numOfVowels ++;
            }
        }

        return numOfVowels;
    }

    /*
     * adds a String to the heap and prioritizes using the 'prioritize' method
     *
     * @param s the string to be added (duplicate values should be added again)
     * @throws IllegalArgumentException if the value is null or an empty String
     */
    public void add(String value) throws IllegalArgumentException {
        if (value == null || value.equals("")) {
            throw  new IllegalArgumentException("Invalid input");
        }

         // increase heap capacity if heap is at max
        if (size == heap.length) {
            String[] newHeap = new String[heap.length * 2];
            for (int i = 0; i < heap.length; i ++) {
                newHeap[i] = heap[i];
            }
            heap = newHeap;
        }

        size++;
        heap[size - 1] = value;
        bubbleUp(size - 1);
    }

    /**
     * assumes valid input
     * helper function for "bubbling up" a node in heap insertions
     * @param currIndex index of the current node in the heap
     */
    private void bubbleUp(int currIndex) {
        if (currIndex == 0) {
            return;
        }

        while (currIndex != 0) {
            int parentIndex = getParentIndex(currIndex);

            // bubbles child up if child has higher priority
            if (prioritize(heap[currIndex], heap[parentIndex]) > 0) {
                switchNodes(currIndex, parentIndex);
                currIndex = parentIndex;

                // end search if child doesn't have higher priority than parent
            } else {
                currIndex = 0;
            }
        }
    }

    /**
     * helper function for switching two nodes at the indicated index
     * @param firstIndex index of first node
     * @param secondIndex index of second node
     */
    private void switchNodes(int firstIndex, int secondIndex) {
        String currStr = heap[firstIndex];
        heap[firstIndex] = heap[secondIndex];

        heap[secondIndex] = currStr;
    }

    /*
     * removes the String with the highest priority from the queue and adjusts the heap
     * to maintain priority rules
     *
     * @return the String with the highest priority; null if the heap is empty
     */
    public String remove() {
        if (size == 0) {
            return null;
        }

        String removedStr = heap[0];

        // replaces top node with last node and bubble down
        replaceTopNode();
        bubbleDown(0);

        return removedStr;
    }

    /**
     * replaces top node in heap with the last node
     */
    private void replaceTopNode() {
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
    }

    /**
     * helper function for bubbling down at a given node to ensure all nodes maintain heap property
     * @param currIndex index of the node to start the bubble down process
     */
    private void bubbleDown(int currIndex) {
        int higherPriorityChildIndex = getHigherPriorityChildIndex(currIndex);

        // keeps looking for child with higher priority and swapping until heap property is maintained
        while (higherPriorityChildIndex > 0) {
            switchNodes(currIndex, higherPriorityChildIndex);
            currIndex = higherPriorityChildIndex;
            higherPriorityChildIndex = getHigherPriorityChildIndex(currIndex);
        }
    }

    /**
     * return index of child with higher priority than current node, if tie return left child index
     * @param currIndex index of current node
     * @return index of child node with higher priority than current node
     *         0 if no such child exists
     */
    private int getHigherPriorityChildIndex(int currIndex) {
        int leftChildIndex = getLeftChildIndex(currIndex);
        int rightChildIndex = getRightChildIndex(currIndex);

        // get value for child if not out of bounds
        String leftChildValue = leftChildIndex > size - 1 ? null : heap[leftChildIndex];
        String rightChildValue = rightChildIndex > size - 1 ? null : heap[rightChildIndex];

        // no children
        String currentValue = heap[currIndex];
        if (leftChildValue == null && rightChildValue == null) {
            return 0;
        }

        // returns no child if parent has higher or equal priority than both children
        if (prioritize(currentValue, leftChildValue) >= 0 && prioritize(currentValue, rightChildValue) >= 0 ) {
            return 0;
        }

        // return the child with the higher priority
        return prioritize(leftChildValue, rightChildValue) >= 0 ? leftChildIndex : rightChildIndex;
    }

    /**
     * get parent index assuming valid input
     * @param currIndex index of current value
     * @return index of parent value
     */
    private int getParentIndex(int currIndex) {
        return (int) Math.floor((currIndex - 1) / 2);
    }

    /**
     * get left child index assuming valid input
     * @param currIndex index of current value
     * @return index of left child value
     */
    private int getLeftChildIndex(int currIndex) {
        return 2 * currIndex + 1;
    }

    /**
     * get right child index assuming valid input
     * @param currIndex index of current value
     * @return index of right child value
     */
    private int getRightChildIndex(int currIndex) {
        return 2 * currIndex + 2;
    }

    /*
     * @return true if the String has no elements, false otherwise
     */
    public boolean isEmpty() {
        return heap.length == 0;
    }

    /*
     * @return the number of Strings stored in the heap
     */
    public int getSize() {
        return size;
    }

    /*
     * @return the element with the highest priority but do not remove it
     */
    public String peek() {
        return isEmpty() ? null : heap[0];
    }

    /*
     * calculates the height (the number of levels past the root); an empty heap has a
     * height of 0 a heap with just a root has a height of 1
     *
     * @return the height of the heap
     */
    public int getHeight() {
        return isEmpty() ? 0 : heapHeightMath(getSize());
    }

    /**
     * uses formula Ceiling(log2(size + 1)) to find height of heap
     * @param size number of values in heap
     * @return integer for height of tree
     */
    private int heapHeightMath(int size) {
        return (int) Math.ceil(Math.log(size + 1) / Math.log(2));
    }

    /*
     * a new ArrayList containing all Strings at this level
     *     - may not contain null values
     *     - an empty heap will result in returning an empty ArrayList
     *     - throws an IndexOutOfBoundsException if level is not appropriate for this heap
     *
     * @return a new String ArrayList that contains only the Strings at this level
     */
    public ArrayList<String> getLevel(int level) throws IndexOutOfBoundsException {
        if (level < 1 || level > getHeight()) {
            throw new IndexOutOfBoundsException("No such level exists in heap");
        }

        int startIndex = (int) Math.pow(2, level - 1) - 1;
        ArrayList<String> levelArr = new ArrayList<String>();

        for (int i = startIndex; i <= startIndex * 2; i ++) {
            if (heap.length > i) {
                levelArr.add(heap[i]);

            }
        }
        return levelArr;
    }

    /*
     * returns a deep copy of the heap array, but not the actual heap object so that
     * the original heap remains unchangeable outside this class
     *     - will be used by the autograder
     *     - an empty heap will return null
     *
     * @return a new array of length heap.length with copied Strings
     *
     */
    public String[] getHeap() {
        String[] newHeap = new String[heap.length];
        for (int i = 0; i < size; i++) {
            newHeap[i] = heap[i];
        }
        return newHeap;
    }

    /*
     * prints out heap size, then prints out the heap elements
     *     - one per line following the indexing of the heap array
     */
    public void printHeap() {
        System.out.println(getSize());
        for (int i = 0; i < heap.length; i++) {
            System.out.println(heap[i]);
        }
    }

    /*
     * prints out level-order traversal of the heap
     *     - one level per line with nodes delimited by a single whitespace
     */
    public void printLevelOrderTraversal() {
        ArrayList<String> level;
        String levelStr;
        for (int i = 1; i < getHeight()+ 1; i++) {
            level = getLevel(i);

            // grab existing values
            levelStr = "";
            for (String value : level) {
                levelStr = levelStr + value + " ";
            }

            System.out.println(levelStr);
        }
    }

    // you are welcome to add private methods

    public static void main(String[] args) {
        // you do not need a main method, but you can use it to test your code
        StringHeap test = new StringHeap(2);
        test.add("R");
        test.add("O");
        System.out.println(test.getHeight());
        System.out.println(test.getLevel(1));
        System.out.println(test.getLevel(2));
        test.add("Y");
        System.out.println("---------");
        System.out.println(test.getHeight());
        System.out.println(test.getLevel(1));
        System.out.println(test.getLevel(2));
        // test.printLevelOrderTraversal();
//        test.remove();
//        System.out.println(test.getHeight());
//        System.out.println(test.getLevel(2));
//        System.out.println(test.peek());
//        test.printLevelOrderTraversal();
}
}


