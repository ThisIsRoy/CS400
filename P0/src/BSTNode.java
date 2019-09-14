//
// Title:           implementation of a binary search tree node
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

import java.util.List;
import java.util.ArrayList;

public class BSTNode<T extends Comparable<T>> {

    private T data;
    private BSTNode<T> left;        // a reference to the left child
    private BSTNode<T> right;        // a reference to the right child

    public BSTNode(T data) {
        this.data = data;
    }

    /**
     * get data in node
     * @return data in node
     */
    public T getData() {
        return data;
    }

    /**
     * set data in node
     * @param data data to place in node
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * get left child of node
     * @return left child of node
     */
    public BSTNode<T> getLeft() {
        return left;
    }

    /**
     * set left child of node
     * @param left new left child of node
     */
    public void setLeft(BSTNode<T> left) {
        this.left = left;
    }

    /**
     * get right child of node
     * @return right child of node
     */
    public BSTNode<T> getRight() {
        return right;
    }

    /**
     * set right child of node
     * @param right new right child of node
     */
    public void setRight(BSTNode<T> right) {
        this.right = right;
    }

    /**
     * get height of current node to the leaf
     * @return height in integer value
     */
    public int getHeight() {
        // base case leaf node
        if (this.getLeft() == null & this.getRight() == null) {
            return 1;

            // recursive case
        } else if (this.getLeft() == null) {
            return 1 + this.getRight().getHeight();
        } else if (this.getRight() == null) {
            return 1 + this.getLeft().getHeight();
        } else {
            return 1 + Math.max(this.getLeft().getHeight(), this.getRight().getHeight());
        }
    }

    /**
     * get total number of elements in current node and its children
     * @return number of elements in integer value
     */
    public int getSize() {
        // base case leaf node
        if (this.getLeft() == null & this.getRight() == null) {
            return 1;

            // recursive case
        } else if (this.getLeft() == null) {
            return 1 + this.getRight().getSize();
        } else if (this.getRight() == null) {
            return 1 + this.getLeft().getSize();
        } else {
            return 1 + this.getLeft().getSize() + this.getRight().getSize();
        }
    }

    /**
     * get number of children for current node
     * @return number of children in integer value
     */
    public int numOfChildren() {
        int numOfChildren = 0;

        if (getLeft() != null) {
            numOfChildren ++;
        }
        if (getRight() != null) {
            numOfChildren ++;
        }

        return numOfChildren;
    }

    /**
     * get the children of the current node
     * @return child node in an list
     */
    public List<BSTNode<T>> getChildren() {
        List<BSTNode<T>> children = new ArrayList<BSTNode<T>>();
        if (getLeft() != null) {
            children.add(getLeft());
        }
        if (getRight() != null) {
            children.add(getRight());
        }

        return children;
    }

// add a main method to test the code
    public static void main(String[] args) {
        BSTNode<String> node1 = new BSTNode<>("R");
        BSTNode<String> node2 = new BSTNode<>("O");
        BSTNode<String> node3 = new BSTNode<>("Y");
        BSTNode<String> node4 = new BSTNode<>("!");

        node1.setLeft(node2);
        // node1.setRight(node3);
        node2.setRight(node4);

        System.out.println(node1.getHeight());
    }
}