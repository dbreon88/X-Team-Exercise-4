import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class GraphProcessorTest {

    private GraphProcessor processor;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

    }


    @AfterClass
    public static void tearDownAfterClass() throws Exception {

    }

    @Before
    public void setUp() throws Exception {
        this.processor = new GraphProcessor();
        processor.populateGraph("word_list.txt");
        processor.shortestPathPrecomputation();
    }

    @After
    public void tearDown() throws Exception {
        this.processor = null;
    }

    @Test
    public void testIsAdjacentIfWordsAreOneLetterOffByAdding() throws Exception {
        String[] strings = {"test", "tests"};
        assertEquals("words are one character off by adding", true,
                        WordProcessor.isAdjacent(strings[0], strings[1]));
    }

    @Test
    public void testIsAdjacentIfWordsAreOneLetterOffBySubtracting() throws Exception {
        String[] strings = {"tests", "test"};
        assertEquals("words are one character off by subtracting", true,
                        WordProcessor.isAdjacent(strings[0], strings[1]));
    }

    @Test
    public void testIsAdjacentIfWordsAreOneLetterOffByChanging() throws Exception {
        String[] strings = {"test", "tost"};
        assertEquals("words are one character off by changing", true,
                        WordProcessor.isAdjacent(strings[0], strings[1]));
    }

    @Test
    public void testIsAdjacentIfWordsAreMoreThanOneCharacterOffByAdding() throws Exception {
        String[] strings = {"test", "testin"};
        assertEquals("words are more than one character off by adding", false,
                        WordProcessor.isAdjacent(strings[0], strings[1]));
    }

    @Test
    public void testIsAdjacentIfWordsAreMoreThanOneCharacterOffBySubtracting() throws Exception {
        String[] strings = {"testin", "test"};
        assertEquals("words are more than one character off by adding", false,
                        WordProcessor.isAdjacent(strings[0], strings[1]));
    }

    @Test
    public void testIsAdjacentIfWordsAreMoreThanOneCharacterOffByChanging() throws Exception {
        String[] strings = {"tell", "test"};
        assertEquals("words are more than one character off by adding", false,
                        WordProcessor.isAdjacent(strings[0], strings[1]));
    }

    @Test
    public void testIsAdjacentIfWordsAreMoreThanOneCharacterOffByAddingAndChanging()
                    throws Exception {
        String[] strings = {"test", "tasty"};
        assertEquals("words are more than one character off by adding and changing", false,
                        WordProcessor.isAdjacent(strings[0], strings[1]));
    }

    @Test
    public void testIsAdjacentIfWordsAreMoreThanOneCharacterOffBySubtractingAndChanging()
                    throws Exception {
        String[] strings = {"test", "tat"};
        assertEquals("words are more than one character off by subtracting and changing", false,
                        WordProcessor.isAdjacent(strings[0], strings[1]));
    }

    @Test
    public void testIsAdjacentIfWordsAreMoreThanOneCharacterOffByChangingTwice() throws Exception {
        String[] strings = {"test", "toot"};
        assertEquals("words are more than one character off by changing twice", false,
                        WordProcessor.isAdjacent(strings[0], strings[1]));
    }

    @Test
    public void testIsAdjacentIfWordsAreDuplicates() throws Exception {
        String[] strings = {"test", "test"};
        assertEquals("words are more than one character off by adding", false,
                        WordProcessor.isAdjacent(strings[0], strings[1]));
    }

    @Test
    public void testGetShortestPath() throws Exception {
        List<String> path = processor.getShortestPath("Carrier", "Weather");
        boolean passed = true;
        for (int i = 0; i < path.size(); i++) {
            if (i < path.size() - 1) {
                if (!WordProcessor.isAdjacent(path.get(i), path.get(i + 1))) {
                    passed = false;
                }
            }
        }
        assertEquals(passed, true);
    }
    
    @Test
    public void testGetShortestPathSameArguments() throws Exception {
        List<String> path = processor.getShortestPath("Carrier", "Carrier");
        assertTrue(path.isEmpty());
    }

    @Test
    public void testGetShortestDistanceIfSameWords() throws Exception{
        int actual=-1;
        int distance =processor.getShortestDistance("rapine", "rapine");
        assertEquals(actual,distance);
    }
    
    
    


    public void testGetShortestDistanceIfWordsExists() throws Exception {
        int actual = 78;
        int distance = processor.getShortestDistance("charge", "gimlets");
        assertEquals(actual, distance);
    }


    @Test
    public void testGetShortestDistanceIfNoPathExists() throws Exception {
        int actual = -1;
        int distance = processor.getShortestDistance("supreme", "airfoil");
        assertEquals(actual, distance);
    }
    
    @Test
    public void testGetShortestPathIfNoPathExists() throws Exception {
        processor.shortestPathPrecomputation();
        boolean passed = true;
        List<String> path = processor.getShortestPath("supreme", "airfoil");
        for (String word : path) {
            passed = false;
        }
        assertEquals(passed, true);
    }

}

    
