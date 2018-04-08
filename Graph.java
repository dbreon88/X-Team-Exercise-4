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
        if (vertex == null) return null;
        for (int i = vertices.size() - 1; i >= 0; i--) {
            if (vertices.get(i).data == vertex) {
                E returnValue = vertices.get(i).data;
                vertices.remove(i);
                return returnValue;
            }
        }
        return null;
    }

  /**
     * {@inheritDoc}
     */
    @Override
    public boolean addEdge(E vertex1, E vertex2) {
    	int vertex1index = 0;
    	int vertex2index = 0;
    	if(vertex1.equals(null) || vertex2.equals(null) || vertex1.equals(vertex2))
    		return false;
    	else
    		for(int i = 0; i < vertices.size(); i++) { //iterates through the graph
    			if(vertices.get(i).equals(vertex1)) { //finds vertex1
    				vertex1index = i; 
    			}
    			if(vertices.get(i).equals(vertex2)) { //finds vertex2
    				vertex2index = i;
    			}		
    		}
    		vertices.get(vertex1index).neighbors.add(vertices.get(vertex2index)); //adds vertex2 to the neighbor arrayList of vertex1
    		vertices.get(vertex2index).neighbors.add(vertices.get(vertex1index)); //adds vertex1 to the neighbor arrayList of vertex2
    		
    	return true;
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
