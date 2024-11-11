package graph;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

public class ConcreteEdgesGraphTest extends GraphInstanceTest {

    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }

    @Test
    public void testAddEdge() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");
        assertEquals("Edge A -> B with weight 10 should return previous weight 0", 
                     0, graph.set("A", "B", 10));
        assertTrue("Graph should contain vertex A", graph.vertices().contains("A"));
        assertTrue("Graph should contain vertex B", graph.vertices().contains("B"));
    }

    @Test
    public void testUpdateEdge() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 10);
        assertEquals("Updating edge A -> B to 20 should return previous weight 10", 
                     10, graph.set("A", "B", 20));
    }

    @Test
    public void testRemoveEdge() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 10);
        graph.set("A", "B", 0);
        assertEquals("Expected sources map to be empty after edge removal", 
                     Collections.emptyMap(), graph.sources("B"));
    }

    @Test
    public void testToString() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 10);
        String expectedString = "Graph with vertices: [A, B] and edges: [A -> B (10)]";
        assertEquals("Graph toString output should match expected format", 
                     expectedString, graph.toString());
    }
}
