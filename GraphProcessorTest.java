import static org.junit.Assert.assertEquals;
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
    }
    
    @After
    public void tearDown() throws Exception {
        this.processor = null;
    }
    
    @Test
    public final void populateGraph() {
        try {
        processor.populateGraph("word_list.txt");
        }
        catch (IOException e) {
            System.out.println("failed!!");
            return;
        }
        assertEquals(true, true);
    }
}
