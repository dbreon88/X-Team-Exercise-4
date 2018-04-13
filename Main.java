import java.io.IOException;

public class Main {

    public static void main(String[] args) {
       GraphProcessor proc = new GraphProcessor();
       try {
       proc.populateGraph("/Users/administrator/eclipse-workspace/p4/src/word_list.txt");
       }
       catch (IOException e) {
           System.out.println(e.getMessage());
       }
       System.out.println(proc.toString());
    }

}
