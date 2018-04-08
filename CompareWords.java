/**
     * figures out what kind of edge there should be between two strings
     * @param a is the first string to be compared
     * @param b is the second string to be compared
     * @return 0 for no edge or duplicate
     * @return 1 for add edge
     * @return 2 for subtract edge
     * @return 3 for change edge
     */
    public int findEdge(String a, String b) {
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
    private char[] ignore(int index, char[] l) {
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
