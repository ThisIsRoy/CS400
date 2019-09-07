import java.util.Random;
import java.util.Scanner;

public class TreeSimulations {
    public static void testManyTrees(int randSeed, int treeSize, int randRange, int numTrees){
        //  initial setup
        BSTTree<Integer> tree;
        Random rnd = new Random(randSeed); // Random with seed

        // iterate numTrees times

        // instantiate a new tree each time

        // continue while the size of this tree is less than treeSize

        // use this code to insert a random Integer into a tree
        tree.insert(rnd.nextInt(randRange));



        //  after filling, if the tree tree's height equals its size, print out the tree
        int height = tree.getHeight();
        if (height == tree.getSize()) {
            tree.printSideways();
        }

        // update data on max, min, and average height


        ///// after all simulated trees made, output statistics
        System.out.println("min height was : " + minHeight);
        System.out.println("max height was : " + maxHeight);
        System.out.println("avg height was : " + avgHeight);
    } // testManyTrees

    public static void main(String[] args) {
        System.out.println("Welcome to the BST Simulator.");
        Scanner scnr = new Scanner(System.in);
        System.out.println("Enter the Random seed number: ");
        int randSeed = scnr.nextInt();
        System.out.println("Enter the number of Integers to be placed in each tree: ");
        int treeSize = scnr.nextInt();
        System.out.println("enter the maximum random integer to be generated: ");
        int randRange = scnr.nextInt();
        System.out.println("enter the number of trees to be simulated: ");
        int numTrees = scnr.nextInt();
        testManyTrees(randSeed, treeSize, randRange, numTrees);
        scnr.close();
    } // main
} // class
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
