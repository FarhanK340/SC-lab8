package graph;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ConcreteEdgesGraph implements Graph<String> {
    
    private final Set<String> vertices = new HashSet<>();
    private final Set<Edge> edges = new HashSet<>();
    
    @Override
    public boolean add(String vertex) {
        return vertices.add(vertex);
    }

    @Override
    public int set(String source, String target, int weight) {
        if (weight < 0) throw new IllegalArgumentException("Weight cannot be negative");

        add(source);
        add(target);

        for (Edge edge : edges) {
            if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                int oldWeight = edge.getWeight();
                if (weight == 0) {
                    edges.remove(edge);
                } else {
                    edge.setWeight(weight);
                }
                return oldWeight;
            }
        }

        if (weight > 0) edges.add(new Edge(source, target, weight));
        return 0;
    }

    @Override
    public boolean remove(String vertex) {
        if (!vertices.contains(vertex)) return false;
        
        vertices.remove(vertex);
        edges.removeIf(edge -> edge.getSource().equals(vertex) || edge.getTarget().equals(vertex));
        return true;
    }

    @Override
    public Set<String> vertices() {
        return Collections.unmodifiableSet(vertices);
    }

    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getTarget().equals(target)) {
                sources.put(edge.getSource(), edge.getWeight());
            }
        }
        return sources;
    }

    @Override
    public Map<String, Integer> targets(String source) {
        Map<String, Integer> targets = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(source)) {
                targets.put(edge.getTarget(), edge.getWeight());
            }
        }
        return targets;
    }

    @Override
    public String toString() {
        return "Graph with vertices: " + vertices + " and edges: " + edges;
    }
    
    private static class Edge {
        private final String source;
        private final String target;
        private int weight;
        
        Edge(String source, String target, int weight) {
            this.source = source;
            this.target = target;
            this.weight = weight;
        }

        public String getSource() { return source; }
        public String getTarget() { return target; }
        public int getWeight() { return weight; }

        public void setWeight(int weight) { this.weight = weight; }

        @Override
        public String toString() {
            return source + " -> " + target + " (" + weight + ")";
        }

        @Override
        public int hashCode() {
            return source.hashCode() ^ target.hashCode() ^ Integer.hashCode(weight);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Edge)) return false;
            Edge other = (Edge) obj;
            return source.equals(other.source) && target.equals(other.target) && weight == other.weight;
        }
    }
}
