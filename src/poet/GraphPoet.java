/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {
    
    private final Map<String, Map<String, Integer>> graph = new HashMap<>();
    
    // Abstraction function:
    //   - The graph is a mapping from each word (vertex) to a map of words it is followed by
    //     (edges), with the number of times that adjacency occurs (edge weight).
    
    // Representation invariant:
    //   - The graph should never be null and should not have any empty words or edges.
    
    // Safety from rep exposure:
    //   - We only expose copies of the graph to the outside world, and not the original map.
    
    /**
     * Create a new poet with the graph from corpus (as described above).
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        List<String> lines = Files.readAllLines(corpus.toPath());
        for (String line : lines) {
            String[] words = line.split("[\\s\\n\\r]+");
            for (int i = 0; i < words.length - 1; i++) {
                String w1 = words[i].toLowerCase();
                String w2 = words[i + 1].toLowerCase();
                addEdge(w1, w2);
            }
        }
    }
    
    // Helper method to add edges to the graph
    private void addEdge(String w1, String w2) {
        graph.putIfAbsent(w1, new HashMap<>());
        graph.get(w1).put(w2, graph.get(w1).getOrDefault(w2, 0) + 1);
    }
    
    /**
     * Generate a poem.
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        String[] words = input.split(" ");
        StringBuilder poem = new StringBuilder(words[0]);

        for (int i = 0; i < words.length - 1; i++) {
            String w1 = words[i].toLowerCase();
            String w2 = words[i + 1].toLowerCase();
            String bridgeWord = findBridgeWord(w1, w2);
            if (bridgeWord != null) {
                poem.append(" ").append(bridgeWord);
            }
            poem.append(" ").append(words[i + 1]);
        }
        
        return poem.toString();
    }
    
    // Helper method to find the bridge word
    private String findBridgeWord(String w1, String w2) {
        Map<String, Integer> adjacencies = graph.getOrDefault(w1, new HashMap<>());
        int maxWeight = 0;
        String bridgeWord = null;
        
        for (Map.Entry<String, Integer> entry : adjacencies.entrySet()) {
            if (entry.getKey().equals(w2)) continue; // skip the direct neighbor
            int weight = entry.getValue();
            if (weight > maxWeight) {
                maxWeight = weight;
                bridgeWord = entry.getKey();
            }
        }
        
        return bridgeWord;
    }
    
    @Override
    public String toString() {
        return graph.toString();
    }
}