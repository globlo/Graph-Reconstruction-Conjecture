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

    //Set an adjacency matrix to all zeros
    public void setMatrixToZeros(int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix.length; col++) {
                matrix[row][col] = 0;
            }
        }
    }

    //Sets all elements of an array to zero
    public static void setArrayToZeros(int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = 0;
        }
    }

    //Determines if we should use the card with the most missing edges or the least missing edges
    //If a graph has a highly connected vertex, using the most missing edges might reduce possible combinations
    public static boolean shouldWeUseCardWithMostMissingEdges(int numberOfCardVertices, int leastMissingEdges, int mostMissingEdges) {
        int differenceBetweenMostAndNumberOfCards = numberOfCardVertices - mostMissingEdges;
        if (differenceBetweenMostAndNumberOfCards < leastMissingEdges) {
            return true;
        }
        return false;
    }

    //Checks if an adjacency matrix represents a valid graph. For this program, valid means: Unweighted, Undirected, and no loops (Vertex connected to itself)
    //This means it's adjacency matrix should be square, mirrored, edge weights of only 1, and it's diaganols should all be zero
    public static boolean isMatrixValidGraph(int[][] matrixToCheck) {
        boolean graphIsLegit = true;
        //First check that the matrix rows and columns are the same length
        if (matrixToCheck.length != matrixToCheck[0].length) {
            System.out.println("Attempted to create graph using matrix with invalid dimensions");
            graphIsLegit = false;
        } else {
            //Iterate over rows
            for (int row = 0; row < matrixToCheck.length; row++) {
                //Iteratore over columns
                for (int col = 0; col < matrixToCheck.length; col++) {
                    //If a graph doesn't have a matching value across it's diaganol, marks as illegitemate
                    if (matrixToCheck[row][col] != matrixToCheck[col][row]) {
                        graphIsLegit = false;
                        System.out.println("Attempted to create a directed graph");
                    }
                    //Check that diaganols are zero
                    if (row == col) {
                        if (matrixToCheck[row][col] != 0) {
                            graphIsLegit = false;
                            System.out.println("Attempted to create graph with self connected vertex");
                        }
                    }
                    //Finally check that no values other than 0 or 1 are present
                    if (matrixToCheck[row][col] != 0 && matrixToCheck[row][col] != 1) {
                        graphIsLegit = false;
                        System.out.println("Attempted to create a weighted graph");
                    }
                }
            }
        }
        return graphIsLegit;
    }
}