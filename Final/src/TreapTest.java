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

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runners.MethodSorters;

/**
 * JUnit test class to test class Graph that implements GraphADT interface.
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TreapTest {
    private TreapTree<Integer> tree;

    @Rule
    public Timeout globalTimeout = new Timeout(2000, TimeUnit.MILLISECONDS);

    @Before
    public void setUp() throws Exception {
        // you may use any or all of the provided json files for JUnit testing
        this.tree = new TreapTree<Integer>();
    }

    @After
    public void tearDown() throws Exception {
        this.tree = null;
    }

    @Test
    public final void test00_emptyTree() {
        try {
            assertTrue(tree.validatePriority());
            assertTrue(tree.validateTree());
        } catch (Exception e) {
            fail("test00: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test01_insertionSimple() {
        try {
            for (int i = 0; i < 10; i++) {
                tree.insert(i);
            }

            assertTrue(tree.validatePriority());
            assertTrue(tree.validateTree());
        } catch (Exception e) {
            fail("test01: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test02_insertionSimpleWithCustomMax() {
        try {
            tree = new TreapTree<Integer>(10000);
            for (int i = 0; i < 10; i++) {
                tree.insert(i);
            }

            assertTrue(tree.validateTree());
            assertTrue(tree.validatePriority());
        } catch (Exception e) {
            fail("test02: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test03_deletionSimple() {
        try {
            for (int i = 0; i < 10; i++) {
                tree.insert(i);
            }
            tree.delete(4);
            tree.delete(7);
            tree.delete(11);
            tree.delete(65423);
            assertTrue(tree.validatePriority());
            assertTrue(tree.validateTree());

            tree.insert(5);
            tree.delete(5);
            assertFalse(tree.search(5));
        } catch (Exception e) {
            fail("test03: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test04_deletionSimpleWithCustomMax() {
        try {
            tree = new TreapTree<Integer>(10000);
            for (int i = 0; i < 10; i++) {
                tree.insert(i);
            }
            tree.delete(2);
            tree.delete(9);
            tree.delete(13);
            tree.delete(435012);
            assertTrue(tree.validatePriority());
            assertTrue(tree.validateTree());
        } catch (Exception e) {
            fail("test04 failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test05_insertionSimpleString() {
        try {
            TreapTree<String> tree = new TreapTree<String>();
            tree.insert("alex");
            tree.insert("alex");
            tree.insert("bobby");
            tree.insert("AWERWE");
            tree.insert("wefwejrlwe");
            assertTrue(tree.validatePriority());
            assertTrue(tree.validateTree());
        } catch (Exception e) {
            fail("test05: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test06_deletionSimpleString() {
        try {
            TreapTree<String> tree = new TreapTree<String>();
            tree.insert("alex");
            tree.insert("alex");
            tree.insert("bobby");
            tree.insert("AWERWE");
            tree.insert("wefwejrlwe");
            tree.insert("bobby");

            tree.delete("bobby");
            tree.delete("nooo");
            tree.delete("alex");
            tree.validatePriority();
            tree.validateTree();
        } catch (Exception e) {
            fail("test06: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test07_searchSimple() {
        try {
            assertFalse(tree.search(5));
            for (int i = 0; i < 10; i++) {
                tree.insert(i);
            }

            assertTrue(tree.search(0));
            assertTrue(tree.search(6));
            assertFalse(tree.search(11));
        } catch (Exception e) {
            fail("test07: failed - unexpected exception occurred");
        }
    }
}
