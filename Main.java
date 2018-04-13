
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
       GraphProcessor proc = new GraphProcessor();
       try {
       proc.populateGraph("/Users/stevenberry/Downloads/word_list.txt");
       }
       catch (IOException e) {
           System.out.println(e.getMessage());
       }
       
       proc.shortestPathPrecomputation();
       System.out.println();
       List<String> test = proc.getShortestPath("cat", "wheat");
       for (String word : test){
    	   System.out.println(word);
       }
    }

}
