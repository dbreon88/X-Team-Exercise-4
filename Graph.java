import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Undirected and unweighted graph implementation
 * 
 * @param <E> type of a vertex
 * 
 * @author sapan (sapan@cs.wisc.edu)
 * 
 */
public class Graph<E> implements GraphADT<E> {
    
    class Vertex<T> {
        T data;
        ArrayList<Vertex<T>> neighbors; 
        ArrayList<ArrayList<T>> shortestPaths;
        ArrayList<Integer> shortestDistances; 
        
        public Vertex(T data){
            this.data = data; 
            this.neighbors = new ArrayList<Vertex<T>>();
            this.shortestPaths = new ArrayList<ArrayList<T>>();
            this.shortestDistances = new ArrayList<Integer>();
  
        }
    }
    
    ArrayList<Vertex<E>> vertices;
    
    public Graph() {
        this.vertices = new ArrayList<Vertex<E>>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E addVertex(E vertex) {
        this.vertices.add(new Vertex<E>(vertex));
        return vertex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E removeVertex(E vertex) {
        
    }

  /**
     * {@inheritDoc}
     */
    @Override
    public boolean addEdge(E vertex1, E vertex2) {
    	if(vertex1.equals(null) || vertex2.equals(null) || vertex1.equals(vertex2))
    		return false;
    	else
    		//TODO: add and edge (add the vertex2 neighbor to the vertex 1 neighbor array, 
    		// and add the vertex1 neighbor to the vertex2 neighbor array).
    		return false;
    }    

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeEdge(E vertex1, E vertex2) {
        if(vertex1.equals(null) || vertex2.equals(null) || vertex1.equals(vertex2))
        	return false; 
        else
    //TODO Finish this    	
        	return true;
    
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAdjacent(E vertex1, E vertex2) {
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getNeighbors(E vertex) {
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getAllVertices() {
        
    }

}
