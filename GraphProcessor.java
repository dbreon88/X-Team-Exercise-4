import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static java.util.stream.Collectors.toList;

//
// Title: X-Team Exercise #4
// Files: Graph.java, GraphProcessor.java, WordProcessor.java, 
// 			GraphProcessorTest.java
// Course: CS400, Spring 2018
// 
// Authors: Dylan Breon, Steven Berry, Elliot Braem, Adam Bin Mohammed Azmil,
// 				Jesse Brodacz
// Emails: dbreon@wisc.edu, shberry@wisc.edu, ebraem@wisc.edu, Binmohammeda@wisc.edu, brodacz@wisc.edu
// Lecturers name: Deb Deppeler
// 
// Due Date: Monday, April 16th, 10:00 pm
// 
// Known Bugs: NONE
// 

/**
 * This class adds additional functionality to the graph as a whole.
 * 
 * Contains an instance variable, {@link #graph}, which stores information for all the vertices and
 * edges.
 * 
 * @see #populateGraph(String) - loads a dictionary of words as vertices in the graph. - finds
 *      possible edges between all pairs of vertices and adds these edges in the graph. - returns
 *      number of vertices added as Integer. - every call to this method will add to the existing
 *      graph. - this method needs to be invoked first for other methods on shortest path
 *      computation to work.
 * @see #shortestPathPrecomputation() - applies a shortest path algorithm to precompute data
 *      structures (that store shortest path data) - the shortest path data structures are used
 *      later to to quickly find the shortest path and distance between two vertices. - this method
 *      is called after any call to populateGraph. - It is not called again unless new graph
 *      information is added via populateGraph().
 * @see #getShortestPath(String, String) - returns a list of vertices that constitute the shortest
 *      path between two given vertices, computed using the precomputed data structures computed as
 *      part of {@link #shortestPathPrecomputation()}. - {@link #shortestPathPrecomputation()} must
 *      have been invoked once before invoking this method.
 * @see #getShortestDistance(String, String) - returns distance (number of edges) as an Integer for
 *      the shortest path between two given vertices - this is computed using the precomputed data
 *      structures computed as part of {@link #shortestPathPrecomputation()}. -
 *      {@link #shortestPathPrecomputation()} must have been invoked once before invoking this
 *      method.
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
    private List<String> words;
    private int[][] dist;
    private String[][] next;



    /**
     * Constructor for this class. Initializes instances variables to set the starting state of the
     * object
     */
    public GraphProcessor() {
        this.graph = new Graph<>();
        this.words = new ArrayList<String>();
    }

    /**
     * Builds a graph from the words in a file. Populate an internal graph, by adding words from the
     * dictionary as vertices and finding and adding the corresponding connections (edges) between
     * existing words.
     * 
     * Reads a word from the file and adds it as a vertex to a graph. Repeat for all words.
     * 
     * For all possible pairs of vertices, finds if the pair of vertices is adjacent
     * {@link WordProcessor#isAdjacent(String, String)} If a pair is adjacent, adds an undirected
     * and unweighted edge between the pair of vertices in the graph.
     * 
     * @param filepath file path to the dictionary
     * @return Integer the number of vertices (words) added
     */
    public Integer populateGraph(String filePath) throws IOException{
    	try{
	    	// get word stream and save to an array list of words
	        words = WordProcessor.getWordStream(filePath).collect(toList());
	        Integer counter = 0;
	        // add each word to the graph
	        for (String word : words) {
	            this.graph.addVertex(word);
	        	counter++;
	        }
	        // for each word
	        for (String word : words) {
                Iterator<String> itr = this.graph.getAllVertices().iterator();
                // for each neighbor of word
                while(itr.hasNext()){
                	String element = itr.next();
                	// if the words are adjacent
                	if (WordProcessor.isAdjacent(element, word)){
                		// add an edge
                        graph.addEdge(element, word);
                	}
                }
	        }
            return counter;
        } catch (IOException e) {
            System.out.println("IOException in populateGraph()");
            System.out.println(e.getMessage());
            return -1;
        }
        catch (Exception f) {
            System.out.println(f.getMessage());
            return -1;
        }
    }

    /**
     * Gets the list of words that create the shortest path between word1 and word2
     * 
     * Example: Given a dictionary, cat rat hat neat wheat kit shortest path between cat and wheat
     * is the following list of words: [cat, hat, heat, wheat]
     * 
     * @param word1 first word
     * @param word2 second word
     * @return List<String> list of the words
     */
    public List<String> getShortestPath(String word1, String word2) {
        String nextWord = "";
        word1 = word1.toUpperCase().trim();
        word2 = word2.toUpperCase().trim();

        int index1 = words.indexOf(word1);
        int index2 = words.indexOf(word2);
        List<String> path = new ArrayList<String>();
        // if path does not exist
        if (next[index1][index2] == null) {
            return path; // return empty path
        } else {
            // add first word to path
            path.add(word1);
            // while next word does not equal final word
            while (!nextWord.equals(word2)) {
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
     * Example: Given a dictionary, cat rat hat heat wheat kit distance of the shortest path between
     * cat and wheat, [cat, hat, heat, wheat] = 3 (the number of edges in the shortest path)
     * 
     * @param word1 first word
     * @param word2 second word
     * @return Integer distance
     */
    public Integer getShortestDistance(String word1, String word2) {
    	// if the path does not exist (same word or not connected)
    	if (getShortestPath(word1, word2).isEmpty()) {
    		return -1;
    	}
    	word1 = word1.toUpperCase().trim();
    	word2 = word2.toUpperCase().trim();
    	
        int index1 = words.indexOf(word1);
        int index2 = words.indexOf(word2);
        return dist[index1][index2];
    }

    /**
     * Computes shortest paths and distances between all possible pairs of vertices. This method is
     * called after every set of updates in the graph to recompute the path information. Any
     * shortest path algorithm can be used (Djikstra's or Floyd-Warshall recommended).
     */
    public void shortestPathPrecomputation() {
        // Floyd-Warshall Path Reconstruction Algorithm
        graphSize = words.size();
        dist = new int[graphSize][graphSize]; // array of minimum distances
        next = new String[graphSize][graphSize]; // array of vertex indices
        for (int[] row : dist) {
            java.util.Arrays.fill(row, 500000); // fill with "infinity"
        }
        Iterator<String> vertices_itr = graph.getAllVertices().iterator();
        // for every vertice in graph
        while (vertices_itr.hasNext()) {
            String curVertice = vertices_itr.next();
            int indexCur = words.indexOf(curVertice);
            Iterator<String> edge_itr = graph.getNeighbors(curVertice).iterator();
            // for every edge of this vertice
            while (edge_itr.hasNext()) {
                String edgeVertice = edge_itr.next();
                int indexEdge = words.indexOf(edgeVertice);
                // set distance between the two as 1
                dist[indexCur][indexEdge] = 1;
                // set the connection between the two
                next[indexCur][indexEdge] = edgeVertice;
            }
        }
        // Floyd-Warshall Algorithm
        for (int k = 0; k < words.size(); k ++){
            for (int i = 0; i < words.size(); i++){
            	if (next[i][k] == null){  // optimization if path does not exist
            		continue;
            	}
                for (int j = 0; j < words.size(); j++){
                	// if the words are the same
                	if (words.get(i).equals(words.get(j))){
                		// distance is 0
                		dist[i][j] = 0;
                		// connection is null
                		next[i][j] = null;
                	} else if (dist[i][j] > dist[i][k] + dist[k][j]) { 
                	// if a shorter distance between i and j is found 
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
