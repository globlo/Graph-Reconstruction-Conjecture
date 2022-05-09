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
    public static void setMatrixToZeros(int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix.length; col++) {
                matrix[row][col] = 0;
            }
        }
    }

    //Set an adjacency matrix to all zeros
    public static void printMatrix(int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix.length; col++) {
                System.out.print(matrix[row][col]);
            }
            System.out.println();
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

    public static void copyMatrix(int[][] sourceMatrix, int[][] destinationMatrix) {
        for (int i = 0; i < sourceMatrix.length; i++) {
            for (int j = 0; j < sourceMatrix[i].length; j++) {
                destinationMatrix[i][j] = sourceMatrix[i][j];
            }
        }
    }

    public static void setIntArrayToNegativeOnes(int[] arrayToSet) {
        for (int i = 0; i < arrayToSet.length; i++) {
            arrayToSet[i] = -1;
        }
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

    public static void printGraphArray(Graph[] graphsToPrint) {
        for (int i = 0; i < graphsToPrint.length; i++) {
            System.out.println("Graph: " + i);
            graphsToPrint[i].printGraph();
        }
    } 


    public static char[] matrixToGraph6(int[][] sampleGraph) {

        int count =0; // Count char[] char to assgin ascii char
        int numb =0; //  Temp # of int to convert ascii char
        int binary_power = 5;
        int extrabits = 0; 

        int graphRow_size = sampleGraph[0].length;

        // Size of half metrix, Formula: n ( n + 1 ) / 2   i.e. n=4 => 4+3+2+1=10
        int half_matrix_size = ((graphRow_size-1) * graphRow_size) / 2;
        // Add extra bits that need to become power of 6 i.e. 10mod6 = 4, 6-4=2, 10+2=12  
        if(half_matrix_size % 6 > 0)
            half_matrix_size = extrabits = half_matrix_size + 6 - (half_matrix_size % 6);

        // First char represent N size of matrix
        char[] ch = new char[1 + half_matrix_size/6];
        ch[count++] = Character.toString(graphRow_size+63).charAt(0);
        
        // Iterate through half of ajacency matrix
        // Extra bits no need to added since they are all 0's
        for(int col = 1; col < graphRow_size; col++){

            for(int row = 0; row < col; row++){

                // i.e. 2^5*1 + 2^4*0 + 2^3*1... 
                numb = numb + (int)Math.pow(2, binary_power--)*sampleGraph[row][col];

                // Convert to ascii standard char when 6 gigits of numbers added into numb
                if(binary_power == -1){

                    ch[count++] = Character.toString(numb+63).charAt(0);

                    numb = 0;
                    binary_power = 5;
                }     
            }
        }
        if(extrabits > 0)
            // Last number (binary with extra bits of 0's at last)
            ch[count++] = Character.toString(numb+63).charAt(0);
        
        return ch;
    }

    public static int[][] graph6ToMatrix(char[] ch) {

        int ct = 0; // counter for six_digits
        int count = 1; //counter for ch[] array

        int graphRow_size = ch[0] - 63;
        int[][] graph = new int[graphRow_size][graphRow_size];

        //Get first dec representated 6digits in ch[1] & convert it to string
        int six_digits = ch[count++] - 63; 
        String bin = String.format("%6s", Integer.toBinaryString(six_digits)).replace(' ', '0');
        
        // Iterate through half of ajacency matrix and symmetry value to assin value
        for(int col = 1; col < graphRow_size; col++){

            for(int row = 0; row < col; row++){
                 
                graph[row][col] = Integer.parseInt(String.valueOf(bin.charAt(ct)));
                graph[col][row] = Integer.parseInt(String.valueOf(bin.charAt(ct++)));

                if(ct >= bin.length() && count < ch.length){

                    ct = 0;

                    six_digits = ch[count++] - 63;
                    bin = String.format("%6s", Integer.toBinaryString(six_digits)).replace(' ', '0');

                }    
            }
        }
            
        return graph;
    }
}