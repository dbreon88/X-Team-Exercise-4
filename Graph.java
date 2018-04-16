import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
//
// Title: X-Team Exercise #4
// Files: Graph.java, GraphProcessor.java, WordProcessor.java, 
//			GraphProcessorTest.java
// Course: CS400, Spring 2018
//
// Authors: Dylan Breon, Steven Berry, Elliot Braem, Adam Bin Mohammed Azmil,
//				Jesse Brodacz
// Emails: dbreon@wisc.edu, shberry@wisc.edu, ebraem@wisc.edu, Binmohammeda@wisc.edu, brodacz@wisc.edu
// Lecturers name: Deb Deppeler
//
// Due Date: Monday, April 16th, 10:00 pm
//
// Known Bugs: NONE
//


/**
 * Undirected and unweighted graph implementation using a HashMap
 * function. A Hashmap implementation is used to drastically reduce 
 * time complexity and effectively find and use locations in our graph
 * for manipulation. We use a inner class Vertex<T> which acts as our vertices
 * for our graph. 
 * 
 * @param <E> type of a vertex
 * 
 */
public class Graph<E> implements GraphADT<E> {
    
	/**
	 * Vertex class, holds the data and 
	 * an ArrayList of neighbors
	 *
	 * @param <T>
	 */
    class Vertex<T> {
        T data;
        ArrayList<E> neighbors; 
        
        public Vertex(T data){
            this.data = data; 
            this.neighbors = new ArrayList<E>();
        }
        public T getData() {
            return this.data;
        }
    }
    
    private HashMap<E, Vertex<E>> vertices;
    
    /**
     * Constructor, creates hash map
     */
    public Graph() {
        this.vertices = new HashMap<E, Vertex<E>>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E addVertex(E vertex) {
        if(vertex == null || vertices.containsKey(vertex)) {
            return null;
        }
        this.vertices.put(vertex, new Vertex<E>(vertex));
        return vertex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E removeVertex(E vertex) {
        if (vertex == null || !vertices.containsKey(vertex)) {
        	return null;
        }
        boolean removed = false;
        Vertex<E> removeVertex = vertices.get(vertex);  // vertex to be removed        
        // for each neighbor of this vertex
        Iterator<E> itr = vertices.get(vertex).neighbors.iterator();
        while(itr.hasNext()) {
            Vertex<E> tempNeighbor = vertices.get(itr.next());
            // remove vertex from their list of neighbors
            removed = tempNeighbor.neighbors.remove(vertex);
            // if vertex cannot be removed from neighbor's list
            if (!removed){
                return null;
            }
        }
        removed = vertices.remove(vertex, removeVertex);
        if (!removed) {  // if vertex cannot be removed
            return null;
        } else {
            return vertex;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addEdge(E vertex1, E vertex2) {
        boolean added1 = false;
        boolean added2 = false;
        if (vertex1.equals(vertex2) || !vertices.containsKey(vertex1) || !vertices.containsKey(vertex2)) {
            return false;
        } else {
        	// if the two are not already connected
        	if (!vertices.get(vertex1).neighbors.contains(vertex2)) {
        		// add vertex2 to vertex1's neighbors
                added1 = vertices.get(vertex1).neighbors.add(vertex2);
                // add vertex1 to vertex2's neighbors
                added2 = vertices.get(vertex2).neighbors.add(vertex1);
        	}
            // check that edges were added
            if (added1 && added2) {  // if both were added
                return true;
            } else {
                return false;
            }
        }
    }    

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeEdge(E vertex1, E vertex2) {
        boolean removed1, removed2 = false;
        if (vertex1.equals(vertex2) || !vertices.containsKey(vertex1) || !vertices.containsKey(vertex2)) {
            return false;
        } else {
            // remove vertex2 from vertex1's neighbors
            removed1 = vertices.get(vertex1).neighbors.remove(vertex2);
            // remove vertex1 from vertex2's neighbors
            removed2 = vertices.get(vertex2).neighbors.remove(vertex1);
            // check that edges were removed
            if (removed1 && removed2) {  // if both were removed
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAdjacent(E vertex1, E vertex2) {
        boolean adjacent1 = false, adjacent2 = false;
        if (vertex1.equals(vertex2) || !vertices.containsKey(vertex1) || !vertices.containsKey(vertex2)) {
            return false;
        } else {
            // check vertex2 if one of vertex1's neighbors
            Iterator<E> itr = this.getNeighbors(vertex1).iterator();
            while (itr.hasNext()){
                if (itr.next().equals(vertex2)){
                    adjacent1 = true;
                }
            }
            // check vertex 1 if one of vertex2's neighbors
            Iterator<E> itr2 = this.getNeighbors(vertex2).iterator();
            while (itr2.hasNext()) {
                if (itr2.next().equals(vertex1)) {
                    adjacent2 = true;
                }
            }
            if (adjacent1 && adjacent2){  // if both were adjacent
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getNeighbors(E vertex) {
        return vertices.get(vertex).neighbors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getAllVertices() {
        return vertices.keySet();
    }
}
