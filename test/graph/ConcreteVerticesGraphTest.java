package graph;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

public class ConcreteVerticesGraphTest extends GraphInstanceTest {

    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }

    @Test
    public void testAddVertex() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        assertTrue("Adding new vertex should return true", graph.add("A"));
        assertFalse("Adding existing vertex should return false", graph.add("A"));
    }

    @Test
    public void testSetEdge() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");
        assertEquals("Setting new edge A -> B with weight 10 should return 0", 
                     0, graph.set("A", "B", 10));
        assertEquals("Updating edge A -> B with weight 20 should return previous weight 10", 
                     10, graph.set("A", "B", 20));
    }

    @Test
    public void testRemoveVertexAndEdges() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 10);
        assertTrue("Removing vertex A should return true", graph.remove("A"));
        assertFalse("Removing non-existing vertex should return false", graph.remove("A"));
        assertTrue("Edge from A to B should be removed along with A",
                   graph.sources("B").isEmpty());
    }

    @Test
    public void testSourcesAndTargets() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 10);
        graph.set("B", "A", 15);
        
        assertEquals("Sources of B should contain A with weight 10", 
                     Collections.singletonMap("A", 10), graph.sources("B"));
        assertEquals("Targets of A should contain B with weight 10", 
                     Collections.singletonMap("B", 10), graph.targets("A"));
    }

    @Test
    public void testToString() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 10);
        
        String expectedString = "Graph with vertices: [A, B] and edges: [A -> {B=10}, B -> {}]";
        String actualString = graph.toString();
        
        assertEquals("Graph toString output should match expected format", 
                     expectedString, actualString);
    }
}
