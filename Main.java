
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
       List<String> test = proc.getShortestPath("define", "shinny");
       for (String word : test){
    	   System.out.println(word);
       }
       System.out.println(proc.getShortestDistance("define","shinny"));
    }

}
