import java.util.Arrays;
//GRC Project
//GraphExaminer.java

//Class to contain all of our methods that look at one or more graphs
public class GraphExaminer {
    
    //Compares adjacencies for each vertex between two graphs by using the vertex mapping passed for rightGraph
    //For example,
    //LeftGraph 1234    RightGraph 1234   Mapping [2,3,4,1] RightGraphMapped 2341
    //        1 0101             1 0111                                    2 0101
    //        2 1011             2 1010                                    3 1011
    //        3 0101             3 1101                                    4 0101
    //        4 1110             4 1010                                    1 1110
    //Will return true since shifting the ordering of RightGraph's rows and columns to match the mapping will result in the same matrix as LeftGraph
    public static boolean checkAdjacenciesAcrossMap(Graph leftGraph, Graph rightGraph, int[] Mapping) {
        boolean adjacenciesHold = true;
        //Iterate through every vertex
        for (int i = 0; i < Mapping.length; i++) {
            for (int j = 0; j < Mapping.length; j++) {
                if (leftGraph.adjMat[i][j] != rightGraph.adjMat[Mapping[i]][Mapping[j]]) {
                    adjacenciesHold = false;
                }
            }
        }
        return adjacenciesHold;
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

    //Checks if an isomorphism exists between two graphs
    public static boolean areGraphsIsomorphicBruteForce(Graph leftGraph, Graph rightGraph) {
        boolean graphsAreIsomorphic = false;
        //First check all the easy requirements for isomorphism
        if ((leftGraph.graphOrder == rightGraph.graphOrder) && (leftGraph.numberOfEdges == rightGraph.numberOfEdges) && (MiscTools.compareSequence(leftGraph.degreeSequence, rightGraph.degreeSequence) == true)) {
            //Then check every possible vertex mapping between the two graphs (n! possibilities yipee!)
            int[][] mappingList = CombinatoricsTools.generateAllPossibleMaps(leftGraph.graphOrder);
            for (int i = 0; i < mappingList.length; i++) {
                graphsAreIsomorphic = checkAdjacenciesAcrossMap(leftGraph, rightGraph, mappingList[i]);
                //If at any point we find a valid mapping, then return true
                if (graphsAreIsomorphic == true) {
                    return graphsAreIsomorphic;
                }
            }
        }
        return graphsAreIsomorphic;
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

    // public static int count = 0;
    // public static void DFS(int graph[][], boolean marked[], int n, int vert, int start) {
        
    //     int V = graph[0].length;
    //     // mark the vertex vert as visited
    //     marked[vert] = true;

    //     // if the path of length (n-1) is found
    //     if (n == 0) {
    //         marked[vert] = false;   
    //         // Check if vertex vert end
    //         // with vertex start
    //         if (graph[vert][start] == 1) {
    //             count++;
    //             return;
    //         } else
    //             return;
    //     }
         
    //     // searching every possible path of length n-1
    //     for (int i = 0; i < V; i++){
    //         if (!marked[i] && graph[vert][i] == 1){
    //             DFS(graph, marked, n-1, i, start);
    //         }
    //     }    
                
    //     // marking vert as unvisited to make it
    //     // usable again
    //     marked[vert] = false;
    // }
     
    // // Count cycles of length N (# of verteces "i.e. Tirangle: n=3") you want to find
    // public static int countCycles(int graph[][], int n) {
         
    //     int V = graph[0].length;
    //     // all vertex are marked un-visited initially.
    //     boolean marked[] = new boolean[V];
        
    //     // Searching for cycle by using v-n-1 vertices
    //     // n-1 is the every possile path that 
    //     for (int i = 0; i < V - (n - 1); i++) { //n-1 = 4 -> V=6 - 4 = 2
    //         DFS(graph, marked, n-1, i, i);  
    //         // ith vertex is marked as visited and will not be visited again
    //         marked[i] = true;
    //     }
    //     return count / 2; //every vertex has duplicated path, right round or left round cycle
    // }
}