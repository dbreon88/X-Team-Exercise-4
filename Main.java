
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
       GraphProcessor proc = new GraphProcessor();
       try {
       proc.populateGraph("word_list.txt");
       }
       catch (IOException e) {
           System.out.println(e.getMessage());
       }
       
       proc.shortestPathPrecomputation();
       System.out.println();
<<<<<<< HEAD
       List<String> test = proc.getShortestPath("define", "shinny");
=======
       System.out.println(proc.getShortestDistance("hat", "wheat"));
       System.out.println();
       List<String> test = proc.getShortestPath("hat", "wheat");
>>>>>>> ddd7b45120c492f410443370ffb2e1bd539dda50
       for (String word : test){
    	   System.out.println(word);
       }
       System.out.println(proc.getShortestDistance("define","shinny"));
    }

}
