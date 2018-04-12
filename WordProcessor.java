import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * This class contains some utility helper methods
 * 
 * @author sapan (sapan@cs.wisc.edu)
 */
public class WordProcessor {
    
    /**
     * Gets a Stream of words from the filepath.
     * 
     * The Stream should only contain trimmed, non-empty and UPPERCASE words.
     * 
     * @see <a href="http://www.oracle.com/technetwork/articles/java/ma14-java-se-8-streams-2177646.html">java8 stream blog</a>
     * 
     * @param filepath file path to the dictionary file
     * @return Stream<String> stream of words read from the filepath
     * @throws IOException exception resulting from accessing the filepath
     */
    public static Stream<String> getWordStream(String filepath) throws IOException {
        /**
         * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html">java.nio.file.Files</a>
         * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/nio/file/Paths.html">java.nio.file.Paths</a>
         * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html">java.nio.file.Path</a>
         * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html">java.util.stream.Stream</a>
         * 
         * class Files has a method lines() which accepts an interface Path object and 
         * produces a Stream<String> object via which one can read all the lines from a file as a Stream.
         * 
         * class Paths has a method get() which accepts one or more strings (filepath),  
         * joins them if required and produces a interface Path object
         * 
         * Combining these two methods:
         *     Files.lines(Paths.get(<string filepath>))
         *     produces
         *         a Stream of lines read from the filepath
         * 
         * Once this Stream of lines is available, you can use the powerful operations available for Stream objects to combine 
         * multiple pre-processing operations of each line in a single statement.
         * 
         * Few of these features:
         *      1. map( )      [changes a line to the result of the applied function. Mathematically, line = operation(line)]
         *          -  trim all the lines
         *          -  convert all the lines to UpperCase
         *          -  example takes each of the lines one by one and apply the function toString on them as line.toString() 
         *             and returns the Stream:
         *                  streamOfLines = streamOfLines.map(String::toString) 
         * 
         *      2. filter( )   [keeps only lines which satisfy the provided condition]  
         *          -  can be used to only keep non-empty lines and drop empty lines
         *          -  example below removes all the lines from the Stream which do not equal the string "apple" 
         *                 and returns the Stream:
         *                  streamOfLines = streamOfLines.filter(x -> x != "apple");
         *                   
         *      3. collect( )  [collects all the lines into a java.util.List object]
         *          -  can be used in the function which will invoke this method to convert Stream<String> of lines to List<String> of lines
         *          -  example below collects all the elements of the Stream into a List and returns the List:
         *              List<String> listOfLines = streamOfLines.collect(Collectors::toList); 
         * 
         * Note: since map and filter return the updated Stream objects, they can chained together as:
         *      streamOfLines.map(...).filter(a -> ...).map(...) and so on
         */

            try { 
            Stream<String> wordStream = 
                            Files.lines(Paths.get(filepath));
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
    
    /**
     * Adjacency between word1 and word2 is defined by:
     * if the difference between word1 and word2 is of
     *  1 char replacement
     *  1 char addition
     *  1 char deletion
     * then 
     *  word1 and word2 are adjacent
     * else
     *  word1 and word2 are not adjacent
     *  
     * Note: if word1 is equal to word2, they are not adjacent
     * 
     * @param word1 first word
     * @param word2 second word
     * @return true if word1 and word2 are adjacent else false
     */
    public static boolean isAdjacent(String word1, String word2) {
        if (findEdge(word1, word2) == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * figures out what kind of edge there should be between two strings
     * @param a is the first string to be compared
     * @param b is the second string to be compared
     * @return 0 for no edge or duplicate
     * @return 1 for add edge
     * @return 2 for subtract edge
     * @return 3 for change edge
     */
    private static int findEdge(String a, String b) {
        if (a.equals(b)) return 0; // duplicate
        if (a.length() < b.length()+1 || a.length() > b.length()-1) { //if the length of a is more than 1 character different than the length of b, there can't be an edge.
            if (a.length() == b.length()) { //either a change edge or no edge
                //break a and b into an array of characters
                char[] aArray = a.toCharArray();
                char[] bArray = b.toCharArray();
                for (int n = 0; n < a.length(); n++) {
                    //replace the nth letter in bArray with the nth letter in aArray
                    char temp = bArray[n];
                    bArray[n] = aArray[n];
                    //if the arrays are the same, this is a change edge. If not, reset bArray, and try the next letter.
                    if (new String(aArray).equals(new String(bArray))) {
                        return 3;
                    } else {
                        bArray[n] = temp;
                    }
                }
            } else { //either add edge, subtract edge, or no edge
                //set l to the longer string and s to the shorter string
                String l = (a.length() > b.length())? a : b;
                char[] lArray = l.toCharArray();
                String s = (a.length() > b.length())? b : a;
                for (int n = 0; n < l.length(); n++) { // try deleting each of the letters in the longer string
                    if (new String(ignore(n, lArray)).equals(s)) { // if they match, return 1 or 2
                        return (a.length() > b.length())? 2 : 1; // return 1 if b is longer (an add edge) and 2 if a is longer (a subtract edge)
                    }
                }
            }
        }
        //no edges detected
        return 0;
    }

    /**
     * return a char array without the letter at index
     * @param index is the index to be ignored
     * @param l the char array
     * @return a char array without the letter at index
     */
    private static char[] ignore(int index, char[] l) {
        char[] returnArray = new char[l.length - 1];
        int newIndex = 0;
        for (int i = 0; i < l.length; i++) {
            if (i != index) {
                returnArray[newIndex] = l[i];
                newIndex++;
            }
        }
        return returnArray;
    }
    
}
