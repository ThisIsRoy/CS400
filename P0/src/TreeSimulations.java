//
// Title:           tests the binary search tree class by running simulations and gathering statistics
// Files:           Requires: BSTNode.java, BSTNode.java
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
// Persons:         N/A
// Online Sources:  stackoverflow.com: one liner for average, min and max heights
//

import java.util.*;

public class TreeSimulations {
    public static void testManyTrees(int randSeed, int treeSize, int randRange, int numTrees){
        //  initial setup
        BSTTree<Integer> tree = new BSTTree<>();
        int minHeight;
        int maxHeight;
        Double avgHeight;
        List<Integer> heights = new ArrayList<Integer>();
        Random rnd = new Random(randSeed); // Random with seed

        // iterate numTrees times
        for (int i = 0; i < numTrees; i++) {
            tree = new BSTTree<>();

            // add nodes until tree reaches specified size
            while (tree.getSize() < treeSize) {
                tree.insert(rnd.nextInt(randRange));
            }

            // calculate and store height
            int height = tree.getHeight();
            heights.add(height);
            if (height == tree.getSize()) {
                tree.printSideways();
            }
        }

        // update data on max, min, and average height
        avgHeight = heights.stream().mapToInt(val -> val).average().orElse(0.0);
        minHeight = heights.get(heights.indexOf(Collections.min(heights)));
        maxHeight = heights.get(heights.indexOf(Collections.max(heights)));

        ///// after all simulated trees made, output statistics
        System.out.println("min height was : " + minHeight);
        System.out.println("max height was : " + maxHeight);
        System.out.println("avg height was : " + avgHeight);
    } // testManyTrees

    // testing
    public static void main(String[] args) {
        System.out.println("Welcome to the BST Simulator.");
        Scanner scnr = new Scanner(System.in);
        System.out.println("Enter the Random seed number: ");
        int randSeed = scnr.nextInt();
        System.out.println("Enter the number of Integers to be placed in each tree: ");
        int treeSize = scnr.nextInt();
        System.out.println("Enter the maximum random integer to be generated: ");
        int randRange = scnr.nextInt();
        System.out.println("Enter the number of trees to be simulated: ");
        int numTrees = scnr.nextInt();
        testManyTrees(randSeed, treeSize, randRange, numTrees);
        scnr.close();
    }
}


// see sample run below

//    Sample Run:
//
//
//
//        Welcome to the BST Simulator.
//        Enter the Random seed number:
//        13
//        Enter the number of Integers to be placed in each tree:
//        10
//        enter the maximum random integer to be generated:
//        100
//        enter the number of trees to be simulated:
//        10000
//        ------------------------------------------
//        96
//        83
//        79
//        76
//        70
//        68
//        55
//        44
//        33
//        10
//        ------------------------------------------
//        ------------------------------------------
//        86
//        80
//        72
//        53
//        40
//        36
//        33
//        28
//        27
//        5
//        ------------------------------------------
//        min height was : 4
//        max height was : 10
//        avg height was : 5.6456
//
//
