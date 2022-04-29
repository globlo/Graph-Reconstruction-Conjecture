import java.util.Arrays;
import java.lang.Math;

//GRC Project
//Graph.java

//The Graph object is used to represent graphs throughout our program
//The graphs are represented by adjacency matrices of integers and also seperately store their order (number of vertices), number of edges, and degree sequence
//We do not allow a graph to be changed after it has been created
public class Graph {
    public final int[][] adjMat;
    public final int graphOrder;
    public final int numberOfEdges;
    public final int[] degreeSequence;

    //Constructor for graph. Automatically checks if the passed in adjacency matrix is legitemate
    //If the graph is legitemate, store it, then calculate it's order and edges. If it is not, then instead create the trivial graph to aid with debugging
    public Graph(int[][] adjacencyMatrix) {
        if (isMatrixValidGraph(adjacencyMatrix) == true) {
            adjMat = adjacencyMatrix;
            graphOrder = adjMat[0].length;
            numberOfEdges = countNumerOfEdgesInGraph();
            degreeSequence = calculateGraphDegreeSequence();
        } else {
            //If the graph is invalid, create trivial graph instead to not break things *too much*
            int[][] trivialGraphMatrix = {{0}};
            int[] trivialDegreeSequence = {0};
            adjMat = trivialGraphMatrix;
            graphOrder = 1;
            numberOfEdges = 0;
            degreeSequence = trivialDegreeSequence;
        }
    }

    //If no adjacency matrix is passed to the constructor, create the trivial graph
    public Graph() {
        int[][] trivialGraphMatrix = {{0}};
        int[] trivialDegreeSequence = {0};
        adjMat = trivialGraphMatrix;
        graphOrder = 1;
        numberOfEdges = 0;
        degreeSequence = trivialDegreeSequence;
    }

    //Checks if a graph's adjacency matrix is valid. For this program, valid means: Unweighted, Undirected, and no loops (Vertex connected to itself)
    //This means it's adjacency matrix should be square, mirrored, edge weights of only 1, and it's diaganols should all be zero
    private static boolean isMatrixValidGraph(int[][] matrixToCheck) {
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

    //Print contents of a graphs adjacency matrix
    public void printGraph() {
        for (int row = 0; row < adjMat[0].length; row++) {
            for (int col = 0; col < adjMat[0].length; col++) {
                System.out.print(adjMat[row][col]);
            }
            System.out.println();
        }
    }

    //Creates a graph that is the same as the original graph, less one vertex
    //The first vertex is represented by int Zero, second vertex by int one, etc
    public static Graph creatSubgraphWithRemovedVertex(Graph originalGraph, int vertexNumber) {
        final int orderOfOriginalGraph = originalGraph.graphOrder;
        final int orderOfSubgraph = orderOfOriginalGraph - 1;
        int[][] subAdjMat = new int[orderOfSubgraph][orderOfSubgraph];
        //Used to tell which row/col we're writing to in the subgraph, which can be different from the cell we're reading from
        int subGraphRow = 0;
        int subGraphCol = 0;

        //Iterate over all rows of original graph
        for (int row = 0; row < orderOfOriginalGraph; row++) {
            //If the row corresponds to the removed vertex, skip it
            if (row != vertexNumber) {
                //Otherwise, iterate over all columns of the original graph
                for (int col = 0; col < orderOfOriginalGraph; col++) {
                    //If column corresponds to the removed vertex, skip it
                    if (col != vertexNumber) {
                        //Write contents of original graph cell to currently marked subgraph cell
                        subAdjMat[subGraphRow][subGraphCol] = originalGraph.adjMat[row][col];
                        subGraphCol++;
                    }
                }
                subGraphCol = 0;
                subGraphRow++;
            }
        }
        Graph graphToReturn = new Graph(subAdjMat);
        return graphToReturn;
    }

    //Returns the number of edges in a graph
    //Since this is ran at graph creation, and since graphs can't change, keep it private and just access the static variable numberOfEdges
    private int countNumerOfEdgesInGraph() {
        int edgeCount = 0;
        for (int row = 0; row < graphOrder; row++) {
            for (int col = 0; col < graphOrder; col++) {
                if (adjMat[row][col] != 0) {
                    edgeCount++;
                }
            }
        }
        return edgeCount / 2;
    }

    //Returns the degree sequence of a graph, which is a list of the amount of edges connected to each vertex
    //Since this is ran at graph creation, and since graphs can't change, keep it private and just access the static variable degreeSequence
    private int[] calculateGraphDegreeSequence() {
        int[] degreeSequence = new int[graphOrder];
        int degreeCounter;
        for (int row = 0; row < graphOrder; row++) {
            degreeCounter = 0;
            for (int col = 0; col < graphOrder; col++) {
                if (adjMat[row][col] == 1) {
                    degreeCounter++;
                }
            }
            degreeSequence[row] = degreeCounter;
        }
        return degreeSequence;
    }

    //Creates a new graph object by duplicating the passed in graph, then adding an extra vertex with edges given by verticesToConnect
    //verticesToConnect is defined as an array of vertices that the new vertex should be connected to
    //For example, I want to add a vertex that's connected to original vertices 1 & 3 (Represented in adjMat as row/col 0 & 2), then I'll pass [0,2]
    public static Graph createGraphWithNewVertexAndEdges(Graph originalGraph, int[] verticesToConnect) {
        int[][] originalAdjMat = originalGraph.adjMat;
        int originalGraphOrder = originalGraph.graphOrder;
        //Add new row & column to represent the new vertex
        int[][] newAdjMat = new int[originalGraphOrder + 1][originalGraphOrder + 1];
        //Copy over contents of original adjacency matrix
        for(int row = 0; row < newAdjMat.length; row++){
            for(int col = 0; col < newAdjMat.length; col++){
                //If we're in either the last row or column of our new graph, set it to zero
                if (col == originalGraphOrder || row == originalGraphOrder) {
                    newAdjMat[row][col] = 0;
                } else {
                    //Otherwise simply copy over original matrix contents
                    newAdjMat[row][col] = originalAdjMat[row][col];
                    
                }
            }
        }
        //Connect the new vertex to the given vertices
        for(int i = 0; i < verticesToConnect.length; i++){
            newAdjMat[originalGraphOrder][verticesToConnect[i]] = 1;
            newAdjMat[verticesToConnect[i]][originalGraphOrder] = 1;
        }
        Graph graphToReturn = new Graph(newAdjMat);
        return graphToReturn;
    }
    
    //return binary with 0 or 1 for each index
    public static int[] calculateVerticesThatMightNeedAnEdge(Graph graphInput, int[] expectedDegreeSequence) {
        int[][] graphInputAdjMat = graphInput.adjMat;
        int[] graphInputDegreeSequence = graphInput.degreeSequence.clone();

        int[] tmpSeq = expectedDegreeSequence.clone();
        Arrays.sort(tmpSeq);
        //Since the expected degree sequence copy is now sorted, we can expect the largest degree to be in the last position of tmpSeq
        int maxPossibleDegree = tmpSeq[tmpSeq.length - 1];

        int[] verticesMissedSequence = new int[graphInputAdjMat.length];

        //calculate the current sequence in the graph
        for (int i = 0; i < graphInputAdjMat.length; i++) {
            //find the vertex that misses edges
            if (graphInputDegreeSequence[i] < maxPossibleDegree) {
                verticesMissedSequence[i] = 1;
            } else {
                verticesMissedSequence[i] = 0;
            }
        }
        return verticesMissedSequence;
    }
  
    //return binary with 0 or 1 for each index 
    public static int []CalculateVerticesThatNeedAnEdge(int[][] graph, int[] ExpectedDegreeSequence) {

        int [] current_sequence = new int[graph[0].length];
        int [] vertexes_missed_sequeence = new int[graph[0].length];  //change later
        int i, j, count=0;
        
        //calculate the current sequence in the graph
        for(i=0; i< graph.length; i++){
            for(j=0; j< graph.length; j++){
                current_sequence[i] += graph[i][j];
            }
            System.out.print(current_sequence[i]);
            //find the vertex that misses edges 
            if(current_sequence[i] < ExpectedDegreeSequence[i]) {
                vertexes_missed_sequeence[i] = 1;
            }
        }

        return vertexes_missed_sequeence;
    }


    public static int count = 0;  
    public static void DFS(int graph[][], boolean marked[],
                    int n, int vert, int start) {
        
        int V = graph[0].length;
        // mark the vertex vert as visited
        marked[vert] = true;

        // if the path of length (n-1) is found
        if (n == 0) {
            marked[vert] = false;   
            // Check if vertex vert end
            // with vertex start
            if (graph[vert][start] == 1) {
                count++;
                return;
            } else
                return;
        }
         
        // searching every possible path of length n-1
        for (int i = 0; i < V; i++){
            if (!marked[i] && graph[vert][i] == 1){
                DFS(graph, marked, n-1, i, start);
            }
        }    
                
        // marking vert as unvisited to make it
        // usable again
        marked[vert] = false;
    }
     
    // Count cycles of length N (# of verteces "i.e. Tirangle: n=3") you want to find
    public static int countCycles(int graph[][], int n) {
         
        int V = graph[0].length;
        // all vertex are marked un-visited initially.
        boolean marked[] = new boolean[V];
        
        // Searching for cycle by using v-n-1 vertices
        // n-1 is the every possile path that 
        for (int i = 0; i < V - (n - 1); i++) { //n-1 = 4 -> V=6 - 4 = 2
            DFS(graph, marked, n-1, i, i);  
            // ith vertex is marked as visited and will not be visited again
            marked[i] = true;
        }
        return count / 2; //every vertex has duplicated path, right round or left round cycle
    }

    public static char[] Matrix_to_ASCiiChar(int[][] sampleGraph) {

        int count =0; // Count char[] char to assgin ascii char
        int numb =0; //  Temp # of int to convert ascii char
        int binary_power = 5;
        int extrabits = 0; 

        int graphRow_size = sampleGraph[0].length;

        // Size of half metrix, Formula: n ( n + 1 ) / 2   i.e. n=4 => 4+3+2+1=10
        int half_matrix_size = ((graphRow_size-1) * graphRow_size) / 2;
        // Add extra bits that need to become power of 6 i.e. 10mod6 = 4, 6-4=2, 10+2=12  
        if(half_matrix_size % 6 > 0)
            half_matrix_size += extrabits = 6 - (half_matrix_size % 6);

        // First char represent N size of matrix
        char[] ch = new char[1 + half_matrix_size/6];
        ch[count++] = Character.toString(graphRow_size+63).charAt(0);
        
        // Iterate through half of ajacency matrix
        // Extra bits no need to added since they are all 0's
        for(int row = 0; row < graphRow_size-1; row++)
            for(int col = row+1; col < graphRow_size; col++){

                // i.e. 2^5*1 + 2^4*0 + 2^3*1... 
                numb = numb + (int)Math.pow(2, binary_power--)*sampleGraph[row][col];

                // Convert to ascii standard char when 6 gigits of numbers added into numb
                if(binary_power == -1){

                    ch[count++] = Character.toString(numb+63).charAt(0);

                    numb = 0;
                    binary_power = 5;
                }     
            }
        
        if(extrabits > 0)
            // Last number (binary with extra bits of 0's at last)
            ch[count++] = Character.toString(numb+63).charAt(0);
        
        return ch;
    }

    public static int[][] ASCiiChar_to_Matrix(char[] ch) {

        int ct = 0; // counter for six_digits
        int count = 1; //counter for ch[] array

        int graphRow_size = ch[0] - 63;
        int[][] graph = new int[graphRow_size][graphRow_size];

        //Get first dec representated 6digits in ch[1] & convert it to string
        int six_digits = ch[count++] - 63; 
        String bin = String.format("%6s", Integer.toBinaryString(six_digits)).replace(' ', '0');
        
        // Iterate through half of ajacency matrix and symmetry value to assin value
        for(int row = 0; row < graphRow_size-1; row++){
            for(int col = row+1; col < graphRow_size; col++){
                 
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
