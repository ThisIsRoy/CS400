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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
public class GraphTest {
	private Graph<String> graph;

	@Rule
	public Timeout globalTimeout = new Timeout(2000, TimeUnit.MILLISECONDS);

	@Before
	public void setUp() throws Exception {
		this.graph = new Graph<String>();
	}

	@After
	public void tearDown() throws Exception {
		this.graph = null;
	}

	@Test
	public final void test00_addNullVertex() {
		try {
			graph.addVertex(null);
			fail("test00: failed - should throw an IllegalArgumentException upon adding a null vertex");
		} catch (IllegalArgumentException e) {
			// test passed
		} catch (Exception e) {
			fail("test00: failed - unexpected exception occurred");
		}
	}

	@Test
	public final void test01_removeNonexistentVertex() {
		try {
			graph.removeVertex("a");
		} catch (Exception e) {
			fail("test01: failed - unexpected exception occurred");
		}
	}

	@Test
	public final void test02_addEdge() {
		try {
			graph.addVertex("a");
			assertEquals("test02: failed - vertex insertion error", true, graph.getAllVertices().contains("a"));
			graph.addVertex("b");
			assertEquals("test02: failed - vertex insertion error", true, graph.getAllVertices().contains("b"));
			graph.addEdge("a", "b");
			assertEquals("test02: failed - edge insertion error", true, graph.getAdjacentVerticesOf("a").contains("b"));
		} catch (Exception e) {
			fail("test02: failed - unexpected exception occurred");
		}
	}
	
	// TODO: add additional JUnit tests here
	@Test
	public final void test03_addInvalidEdge() {
		try {
		    // add edges of non-existent vertices
			graph.addEdge("a", "b");
			graph.addEdge("c", "d");
			assertEquals("test03: failed - void insertion error", true, graph.size() == 0);

			// add edge where only one vertex exists
			graph.addVertex("a");
			graph.addEdge("a", "b");
			assertEquals("test03: failed - void insertion error", true, graph.size() == 0);
		} catch (Exception e) {
			fail("test03: failed - unexpected exception occurred");
		}
	}

	@Test
	public final void test04_graphOrder() {
		try {
		    // add and remove vertex while checking correct order
			graph.addVertex("a");
			graph.addVertex("a");
			graph.addVertex("b");
			graph.removeVertex("c");
			assertEquals("test04: failed - incorrect order", true, graph.order() == 2);
			graph.addVertex("c");
			graph.addVertex("d");
			graph.removeVertex("a");

			// actually remove vertex to check order
            assertEquals("test04: failed - incorrect order", true, graph.order() == 3);
            graph.removeVertex("a");
			assertEquals("test04: failed - incorrect order", true, graph.order() == 3);
		} catch (Exception e) {
            fail("test04: failed - unexpected exception occurred");
        }
	}

    @Test
    public final void test05_adjacentVertex() {
        try {
            // add vertex and edges
            graph.addVertex("a");
            graph.addVertex("b");
            graph.addVertex("c");
            graph.addVertex("d");
            graph.addEdge("a", "b");
            graph.addEdge("a", "c");
            graph.addEdge("a", "a");

            // check adjacent vertex
            assertEquals("test05: failed - incorrect adjacent vertex", true, graph.getAdjacentVerticesOf("a").contains("b"));
            assertEquals("test05: failed - incorrect adjacent vertex", true, graph.getAdjacentVerticesOf("a").contains("c"));
            assertEquals("test05: failed - incorrect adjacent vertex", false, graph.getAdjacentVerticesOf("a").contains("a"));
            assertEquals("test05: failed - incorrect adjacent vertex", false, graph.getAdjacentVerticesOf("a").contains("d"));
            assertEquals("test05: failed - incorrect adjacent vertex", true, graph.size() == 2);
        }catch (Exception e) {
            fail("test05: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test06_inputExceptions() {
        try {
            graph.addEdge(null, "b");
            fail("test06: failed - no exception given");
        } catch (IllegalArgumentException ill) {
        } catch (Exception e) {
            fail("test06: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test07_removeEdge() {
	    // add vertex and edge
        graph.addVertex("a");
        graph.addVertex("b");
        graph.addVertex("c");
        graph.addVertex("d");
        graph.addEdge("a", "b");
        graph.addEdge("c", "d");
        graph.addEdge("c", "a");
        graph.removeEdge("a", "c");

        // check that remove edge works
        assertEquals("test07: failed - incorrect remove edge", false, graph.getAdjacentVerticesOf("a").contains("c"));
        assertEquals("test07: failed - incorrect remove edge", false, graph.getAdjacentVerticesOf("c").contains("a"));
        assertEquals("test07: failed - incorrect remove edge", true, graph.size() == 2);
        graph.removeEdge("d", "c");
        graph.removeEdge("b", "a");
        assertEquals("test07: failed - incorrect remove edge", true, graph.size() == 0);
    }
}
