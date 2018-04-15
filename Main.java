
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
       System.out.println(proc.getShortestDistance("hat", "wheat"));
       System.out.println();
       List<String> test = proc.getShortestPath("hat", "wheat");
       for (String word : test){
    	   System.out.println(word);
       }
    }

}
