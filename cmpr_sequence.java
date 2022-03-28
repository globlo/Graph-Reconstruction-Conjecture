import java.io.*;
import java.util.Arrays;

public class cmpr_sequence {

    static boolean CompareSequence(int[] Seq1, int[] Seq2) {

        Arrays.sort(Seq1);
        Arrays.sort(Seq2);
        
        return Arrays.equals(Seq1, Seq2);

    }

    public static void main(String[] args){

        int[] a1 = {1, 2, 3};
        int[] a2 = {3, 1, 2};
        System.out.print(CompareSequence(a1,a2));
        
    }
}