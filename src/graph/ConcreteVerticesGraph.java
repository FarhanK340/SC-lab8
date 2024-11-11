package graph;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConcreteVerticesGraph implements Graph<String> {

    private final Map<String, Vertex> vertices = new HashMap<>();

    @Override
    public boolean add(String vertex) {
        if (vertices.containsKey(vertex)) return false;
        vertices.put(vertex, new Vertex(vertex));
        return true;
    }

    @Override
    public int set(String source, String target, int weight) {
        if (weight < 0) throw new IllegalArgumentException("Weight cannot be negative");

        add(source);
        add(target);

        Vertex sourceVertex = vertices.get(source);
        return sourceVertex.setEdge(target, weight);
    }

    @Override
    public boolean remove(String vertex) {
        if (!vertices.containsKey(vertex)) return false;

        vertices.remove(vertex);
        for (Vertex v : vertices.values()) {
            v.removeEdge(vertex);
        }
        return true;
    }

    @Override
    public Set<String> vertices() {
        return Collections.unmodifiableSet(vertices.keySet());
    }

    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Map.Entry<String, Vertex> entry : vertices.entrySet()) {
            int weight = entry.getValue().getEdge(target);
            if (weight > 0) sources.put(entry.getKey(), weight);
        }
        return sources;
    }

    @Override
    public Map<String, Integer> targets(String source) {
        Vertex vertex = vertices.get(source);
        return vertex != null ? vertex.getTargets() : Collections.emptyMap();
    }

    @Override
    public String toString() {
        return "Graph with vertices: " + vertices.keySet() + " and edges: " + vertices.values();
    }

    private static class Vertex {
        private final String name;
        private final Map<String, Integer> edges = new HashMap<>();

        Vertex(String name) { this.name = name; }

        public int setEdge(String target, int weight) {
            int oldWeight = edges.getOrDefault(target, 0);
            if (weight == 0) edges.remove(target);
            else edges.put(target, weight);
            return oldWeight;
        }

        public void removeEdge(String target) {
            edges.remove(target);
        }

        public int getEdge(String target) {
            return edges.getOrDefault(target, 0);
        }

        public Map<String, Integer> getTargets() {
            return Collections.unmodifiableMap(edges);
        }

        @Override
        public String toString() {
            return name + " -> " + edges;
        }

        @Override
        public int hashCode() {
            return name.hashCode() ^ edges.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Vertex)) return false;
            Vertex other = (Vertex) obj;
            return name.equals(other.name) && edges.equals(other.edges);
        }
    }
}
