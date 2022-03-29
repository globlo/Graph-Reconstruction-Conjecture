//GRC Project
//MiscTools.java
import java.util.Arrays;

//Contains miscellaneous functions that don't fall into combinatorics, graph, or deck operations
public class MiscTools {
    //Simple factorial function
    public static int getFactorial(int n){
        int value = 1;
        for (int i = 1; i <= n; i++) {
            value = value * i;
        }
        return value;
    }

    //Compares if two sequences are equivalent without modifying them
    public static boolean compareSequence(int[] seq1, int[] seq2) {
        int[] tmpSeq1 = seq1.clone();
        int[] tmpSeq2 = seq2.clone();
        Arrays.sort(tmpSeq1);
        Arrays.sort(tmpSeq2);
        return Arrays.equals(tmpSeq1, tmpSeq2);
    }
}