package poet;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.File;
import java.io.IOException;

public class GraphPoetTest {
    
    @Test
    public void testSimplePoemGeneration() throws IOException {
        // Set up a simple corpus
        File corpus = new File("src/poet/simple-corpus.txt");
        GraphPoet poet = new GraphPoet(corpus);
        
        String input = "Seek to explore new and exciting synergies!";
        String expectedPoem = "Seek to explore strange new worlds and new exciting synergies!";
        String generatedPoem = poet.poem(input);
        
        assertEquals("The poem was generated correctly", expectedPoem, generatedPoem);
    }
    
    @Test
    public void testNoBridgeWord() throws IOException {
        // Test where no bridge word should be inserted
        File corpus = new File("src/poet/simple-corpus.txt");
        GraphPoet poet = new GraphPoet(corpus);
        
        String input = "Seek to explore";
        String expectedPoem = "Seek to explore";
        String generatedPoem = poet.poem(input);
        
        assertEquals("No bridge word should be inserted", expectedPoem, generatedPoem);
    }
    
    @Test
    public void testEmptyPoem() throws IOException {
        // Test for empty input
        File corpus = new File("src/poet/simple-corpus.txt");
        GraphPoet poet = new GraphPoet(corpus);
        
        String input = "";
        String expectedPoem = "";
        String generatedPoem = poet.poem(input);
        
        assertEquals("Empty input should return empty poem", expectedPoem, generatedPoem);
    }
    
    @Test(expected = IOException.class)
    public void testInvalidCorpus() throws IOException {
        // Test invalid corpus file path
        File corpus = new File("src/poet/invalid-corpus.txt");
        GraphPoet poet = new GraphPoet(corpus);
    }
    
    @Test
    public void testPoemWithMultipleBridgeWords() throws IOException {
        // Test input that might have multiple options for bridge words
        File corpus = new File("src/poet/complex-corpus.txt");
        GraphPoet poet = new GraphPoet(corpus);
        
        String input = "Seek to explore new frontiers";
        String expectedPoem = "Seek life to seek explore strange new worlds frontiers"; 
        String generatedPoem = poet.poem(input);
        
        assertEquals("The poem with multiple bridge words should be correct", expectedPoem, generatedPoem);
    }
}
