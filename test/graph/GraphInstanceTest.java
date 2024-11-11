package graph;

import static org.junit.Assert.*;
import java.util.Collections;
import org.junit.Test;

public abstract class GraphInstanceTest {

    // Each subclass will provide a specific graph implementation instance
    public abstract Graph<String> emptyInstance();

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // Ensure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testInitialVerticesEmpty() {
        assertEquals("Expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }

    @Test
    public void testAddVertex() {
        Graph<String> graph = emptyInstance();
        assertTrue("Adding new vertex should return true", graph.add("A"));
        assertFalse("Adding existing vertex should return false", graph.add("A"));
    }

    @Test
    public void testSetEdge() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        assertEquals("Initial set should return 0", 0, graph.set("A", "B", 10));
        assertEquals("Updating should return old weight", 10, graph.set("A", "B", 20));
    }

    @Test
    public void testRemoveVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertTrue("Removing existing vertex should return true", graph.remove("A"));
        assertFalse("Removing non-existing vertex should return false", graph.remove("A"));
    }

    @Test
    public void testSources() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 10);
        assertEquals("Expected source A with weight 10", 
                     Collections.singletonMap("A", 10), graph.sources("B"));
    }

    @Test
    public void testTargets() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 10);
        assertEquals("Expected target B with weight 10", 
                     Collections.singletonMap("B", 10), graph.targets("A"));
    }
}
