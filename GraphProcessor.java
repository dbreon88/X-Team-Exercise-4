import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * This class adds additional functionality to the graph as a whole.
 * 
 * Contains an instance variable, {@link #graph}, which stores information for all the vertices and edges.
 * @see #populateGraph(String)
 *  - loads a dictionary of words as vertices in the graph.
 *  - finds possible edges between all pairs of vertices and adds these edges in the graph.
 *  - returns number of vertices added as Integer.
 *  - every call to this method will add to the existing graph.
 *  - this method needs to be invoked first for other methods on shortest path computation to work.
 * @see #shortestPathPrecomputation()
 *  - applies a shortest path algorithm to precompute data structures (that store shortest path data)
 *  - the shortest path data structures are used later to 
 *    to quickly find the shortest path and distance between two vertices.
 *  - this method is called after any call to populateGraph.
 *  - It is not called again unless new graph information is added via populateGraph().
 * @see #getShortestPath(String, String)
 *  - returns a list of vertices that constitute the shortest path between two given vertices, 
 *    computed using the precomputed data structures computed as part of {@link #shortestPathPrecomputation()}.
 *  - {@link #shortestPathPrecomputation()} must have been invoked once before invoking this method.
 * @see #getShortestDistance(String, String)
 *  - returns distance (number of edges) as an Integer for the shortest path between two given vertices
 *  - this is computed using the precomputed data structures computed as part of {@link #shortestPathPrecomputation()}.
 *  - {@link #shortestPathPrecomputation()} must have been invoked once before invoking this method.
 *  
 * @author sapan (sapan@cs.wisc.edu)
 * 
 */
public class GraphProcessor {

    /**
     * Graph which stores the dictionary words and their associated connections
     */
    private Graph<String> graph;
    private int graphSize;
    ArrayList<String> words;
    int[][] dist = new int[graphSize][graphSize];  // array of minimum distances
    String[][] next = new String[graphSize][graphSize];  // array of vertex indices
    
    

    /**
     * Constructor for this class. Initializes instances variables to set the starting state of the object
     */
    public GraphProcessor() {
        this.graph = new Graph<>();
        java.util.Arrays.fill(dist, Integer.MAX_VALUE);  // fill with "infinity"
        java.util.Arrays.fill(next, null);  //initial all to null
        this.words = new ArrayList<String>();
    }
        
    /**
     * Builds a graph from the words in a file. Populate an internal graph, by adding words from the dictionary as vertices
     * and finding and adding the corresponding connections (edges) between 
     * existing words.
     * 
     * Reads a word from the file and adds it as a vertex to a graph.
     * Repeat for all words.
     * 
     * For all possible pairs of vertices, finds if the pair of vertices is adjacent {@link WordProcessor#isAdjacent(String, String)}
     * If a pair is adjacent, adds an undirected and unweighted edge between the pair of vertices in the graph.
     * 
     * @param filepath file path to the dictionary
     * @return Integer the number of vertices (words) added
     */
    public Integer populateGraph(String filePath) throws IOException{
        // if these can come in a stream, populate the graph,
        // but also be added to an arrayList of words, then implementation of
        // Floyd-Warshall Path Reconstruction Algorithm should work
        
        Stream<String> wordStream = getWordStream(filePath);
        wordStream.forEach(words::add);
        Integer counter = 0;
        for (String word : this.words) {
            this.graph.addVertex(word);
            counter++;
            if (!this.graph.isEmpty()) {
                for (String element: this.graph.getAllVertices()) {
                    if (graph.isAdjacent(element, word)) {
                        graph.addEdge(element, word);
                    }
                }
                
            }
        }
        
        return counter;
    
    }

    
    /**
     * Gets the list of words that create the shortest path between word1 and word2
     * 
     * Example: Given a dictionary,
     *             cat
     *             rat
     *             hat
     *             neat
     *             wheat
     *             kit
     *  shortest path between cat and wheat is the following list of words:
     *     [cat, hat, heat, wheat]
     * 
     * @param word1 first word
     * @param word2 second word
     * @return List<String> list of the words
     */
    public List<String> getShortestPath(String word1, String word2) {
        String nextWord = "";
        int index1 = words.indexOf(word1);
        int index2 = words.indexOf(word2);
        List<String> path = new ArrayList<String>();
        // if path does not exist
        if (next[index1][index2] == null){
            return path;  // return empty path
        }
        else{
            // add first word to path
            path.add(word1);
            // while next word does not equal final word
            while (!nextWord.equals(word2)){
                // get next word
                nextWord = next[index1][index2];
                // update next index
                index1 = words.indexOf(nextWord);
                // add next word to path
                path.add(nextWord);
            }
            return path;
        }
    }
    
    /**
     * Gets the distance of the shortest path between word1 and word2
     * 
     * Example: Given a dictionary,
     *             cat
     *             rat
     *             hat
     *             neat
     *             wheat
     *             kit
     *  distance of the shortest path between cat and wheat, [cat, hat, heat, wheat]
     *   = 3 (the number of edges in the shortest path)
     * 
     * @param word1 first word
     * @param word2 second word
     * @return Integer distance
     */
    public Integer getShortestDistance(String word1, String word2) {
        int index1 = words.indexOf(word1);
        int index2 = words.indexOf(word2);
        return dist[index1][index2];
    }
    
    /**
     * Computes shortest paths and distances between all possible pairs of vertices.
     * This method is called after every set of updates in the graph to recompute the path information.
     * Any shortest path algorithm can be used (Djikstra's or Floyd-Warshall recommended).
     */
    public void shortestPathPrecomputation() {
        // Floyd-Warshall Path Reconstruction Algorithm
        tempGetGraphSize();  // update size of graph
        Iterator<String> vertices_itr = graph.getAllVertices().iterator();
        // below could be a pretty cool lambda expression if we want
        // populate the arrays dist and next
        // for every vertice in graph
        while (vertices_itr.hasNext()){
            String curVertice = vertices_itr.next();
            int indexCur = words.indexOf(curVertice);
            Iterator<String> edge_itr = graph.getNeighbors(curVertice).iterator();
            // for every edge of this vertice
            while (edge_itr.hasNext()){
                String edgeVertice = edge_itr.next();
                int indexEdge = words.indexOf(edgeVertice);
                // I hope this works:
                dist[indexCur][indexEdge] = 1;
                next[indexCur][indexEdge] = edgeVertice;
            }
            for (int k = 0; k < graphSize; k ++){
                for (int i = 0; i < graphSize; i++){
                    for (int j = 0; j < graphSize; j++){
                        // if a shorter distance between i and j is found 
                        if (dist[i][j] > dist[i][k] + dist[k][j]){
                            // update the distance
                            dist[i][j] = dist[i][k] + dist[k][j];
                            // fix the link
                            next[i][j] = next[i][k];
                        }
                    }
                }
            }
        }
    }

    /**
     * Temporary method to get size of graph
     * Alternate method would be preferred, unable to do graph.getGraphSize
     * because getGraphSize is not of GraphADT
     * Open to suggestions
     * 
     * Update: this could be replaced with words.size();
     * @return size of graph
     */
    private void tempGetGraphSize() {
        Iterator<String> itr = graph.getAllVertices().iterator();
        while (itr.hasNext()){
            graphSize ++;
        }
        return;
    }
    
    private Stream<String> getWordStream(String filePath) throws IOException {
        try { 
        Stream<String> wordStream = 
                        Files.lines(Paths.get(filePath));
        wordStream
        .map(String::trim)
        .filter(x -> x!=null && !x.equals(""))
        .map(String::toUpperCase);
        return wordStream;
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
        
        
    }
}
