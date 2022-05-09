import java.util.ArrayList;
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

    public static boolean areGraphsIsomorphic(Graph leftGraph, Graph rightGraph) {
        //First check all the easy requirements for isomorphism
        if ((leftGraph.graphOrder == rightGraph.graphOrder) && (leftGraph.numberOfEdges == rightGraph.numberOfEdges) && (MiscTools.compareSequence(leftGraph.degreeSequence, rightGraph.degreeSequence) == true)) {
            
            //Next check that the triangle count is the same if enabled
            if (GRC.checkTrianglesBeforeIsoChecks == true) {
                //Are we counting triangles Michael's way? (The cool way)
                if (GRC.checkTrianglesNonrecurisveWay == true) {
                    int numberOfTrianglesInLeftGraph = countNumberOfTriangles(leftGraph);
                    int numberOfTrianglesInrightGraph = countNumberOfTriangles(rightGraph);
                    if (numberOfTrianglesInLeftGraph != numberOfTrianglesInrightGraph) {
                        return false;
                    }
                } else {
                    int numberOfTrianglesInLeftGraph = countCycles(leftGraph.adjMat, 3);
                    int numberOfTrianglesInrightGraph = countCycles(rightGraph.adjMat, 3);
                    if (numberOfTrianglesInLeftGraph != numberOfTrianglesInrightGraph) {
                        return false;
                    }
                }
            }

            //Next check that the Square count is the same if enabled
            if (GRC.checkSquaresBeforeIsoChecks == true) {
                int numberOfSquaresInLeftGraph = countCycles(leftGraph.adjMat, 4);
                int numberOfSquaresInrightGraph = countCycles(rightGraph.adjMat, 4);
                if (numberOfSquaresInLeftGraph != numberOfSquaresInrightGraph) {
                    return false;
                }
            }

            //Now check for isomorphisms the hard way. Must find a valid vertex mapping such that adjacencies are held
            if (GRC.useBruteForceIsomorphismCheck == true) {

                //Are we using vertex relabeling to sort degree sequence?
                if (GRC.relabelBeforeIsoChecks == true) {
                    Graph sortedLeftGraph = leftGraph.createGraphWithSortedLabels();
                    Graph sortedRightGraph = rightGraph.createGraphWithSortedLabels();
                    return areGraphsIsomorphicBruteForce(sortedLeftGraph, sortedRightGraph);
                } else {
                    return areGraphsIsomorphicBruteForce(leftGraph, rightGraph);
                }
                
            } else {

                //Are we using vertex relabeling to sort degree sequence?
                if (GRC.relabelBeforeIsoChecks == true) {
                    Graph sortedLeftGraph = leftGraph.createGraphWithSortedLabels();
                    Graph sortedRightGraph = rightGraph.createGraphWithSortedLabels();
                    return areGraphsIsomorphicTreeSearch(sortedLeftGraph, sortedRightGraph);
                } else {
                    return areGraphsIsomorphicTreeSearch(leftGraph, rightGraph);
                }

            }

        } else {
            return false;
        }
    }

    //Checks if an isomorphism exists between two graphs by brute forcing all possible vertex mappings
    public static boolean areGraphsIsomorphicBruteForce(Graph leftGraph, Graph rightGraph) {
        boolean graphsAreIsomorphic = false;        
        //Then check every possible vertex mapping between the two graphs (n! possibilities yipee!)
        int[][] mappingList = CombinatoricsTools.generateAllPossibleMaps(leftGraph.graphOrder);
        for (int i = 0; i < mappingList.length; i++) {
            graphsAreIsomorphic = checkAdjacenciesAcrossMap(leftGraph, rightGraph, mappingList[i]);
            //If at any point we find a valid mapping, then return true
            if (graphsAreIsomorphic == true) {
                return graphsAreIsomorphic;
            }
        }
        return graphsAreIsomorphic;
    }


    //Checks if an isomorphism exists between two graphs by using a search tree to narrow the possible vertex mappings
    public static boolean areGraphsIsomorphicTreeSearch(Graph leftGraph, Graph rightGraph) {
        boolean isomorphismFound = false;
        
        //First check all the easy requirements for isomorphism
        if ((leftGraph.graphOrder == rightGraph.graphOrder) && (leftGraph.numberOfEdges == rightGraph.numberOfEdges) && (MiscTools.compareSequence(leftGraph.degreeSequence, rightGraph.degreeSequence) == true)) {
            //Create root and first level of tree. For easier indexing, root is at level -1 with children at level 0
            IsomorphimTreeNode root = IsomorphimTreeNode.createInitialTree(leftGraph.graphOrder);
            int currentLevel = 0;
            ArrayList<Integer> currentSequence = new ArrayList<Integer>();
            //Check each branch of this first level
            for (int i = 0; i < leftGraph.graphOrder; i++) {
                //Get vertexIndex from child node
                int currentVertexIndex = root.children.get(i).vertexIndex;
                
                //Compare if degree of left graph's vertex at given index matches degree of right graph's vertex at index equal to current tree level
                if (leftGraph.degreeSequence[currentVertexIndex] == rightGraph.degreeSequence[currentLevel]) {
                    //This vertex is a possible match since their degrees are the same. Now recursively check children for matches
                    currentSequence.add(Integer.valueOf(currentVertexIndex));
                    isomorphismFound = recursivelyCheckChildren(root.children.get(i), currentSequence, currentLevel + 1, leftGraph.graphOrder, leftGraph, rightGraph);
                    if (isomorphismFound == true) {
                        return true;
                    }
                    currentSequence.remove(currentSequence.size() - 1);
                }
            }
        }
        return false;
    }

    public static boolean recursivelyCheckChildren(IsomorphimTreeNode currentNode, ArrayList<Integer> currentSequence, int currentLevel, int totalVertexCount, Graph leftGraph, Graph rightGraph) {
        boolean isomorphismFound = false;
        //If our currentSequence size is equal to our total vertex count minus one, then we have a possible isomorphism
        if (currentSequence.size() == totalVertexCount) {
            //Check adjacencies for the sequence from the root to this leaf
            // System.out.print("Possible isomorphism found with: ");
            // for (int i = 0; i < currentSequence.size(); i++) {
            //     System.out.print(currentSequence.get(i));
            // }
            // System.out.println();
            int[] possibleSequence = new int[currentSequence.size()];
            for (int i = 0; i < possibleSequence.length; i++) {
                possibleSequence[i] = currentSequence.get(i);
            }
            if (checkAdjacenciesAcrossMap(leftGraph, rightGraph, possibleSequence) == true) {
                //System.out.println("Isomorphism confirmed.");
                return true;
            } else {
                return false;
            }
            
        }
        //First generate the child sequence
        int[] childSequence = generateChildSequence(currentSequence, totalVertexCount);

        IsomorphimTreeNode.generateChildren(currentNode, childSequence);

        for (int i = 0; i < childSequence.length; i++) {
            int currentVertexIndex = currentNode.children.get(i).vertexIndex;

            if (leftGraph.degreeSequence[currentVertexIndex] == rightGraph.degreeSequence[currentLevel]) {
                currentSequence.add(Integer.valueOf(currentVertexIndex));
                isomorphismFound = recursivelyCheckChildren(currentNode.children.get(i), currentSequence, currentLevel + 1, totalVertexCount, leftGraph, rightGraph);
                if (isomorphismFound == true) {
                    return true;
                }
                currentSequence.remove(currentSequence.size() - 1);
            }
        }
        
        return false;
    }

    public static int[] generateChildSequence(ArrayList<Integer> currentlyUsedSequence, int vertexCount) {
        //Create sequence array of appropriate length
        int[] childSequence = new int[vertexCount - currentlyUsedSequence.size()];
        int childSequenceIndex = 0;
        boolean skipThisI;
        //Try to fill in sequence array
        for (int i = 0; i < vertexCount; i++) {
            skipThisI = false;
            //Check if the current i value is in currentlyUsedSequence
            for (int j = 0; j < currentlyUsedSequence.size(); j++) {
                if (i == currentlyUsedSequence.get(j).intValue()) {
                    skipThisI = true;
                    break;
                }
            }
            if (skipThisI == false) {
                childSequence[childSequenceIndex] = i;
                childSequenceIndex++;
            }
        }
        return childSequence;
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

    public static int count = 0;
    public static void DFS(int graph[][], boolean marked[], int n, int vert, int start) {
        
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
        count = 0;
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

    //Count the number of triangles in a graph. This method might be faster than the cycle counting method since this is much more specific
    public static int countNumberOfTriangles(Graph graphToCountFrom) {
        int numberOfTriangles = 0;
        int[] verticesAdjacentToVertexI = new int[graphToCountFrom.graphOrder - 1];
        int currentAdjacentVerticesCount = 0;

        //Check each vertex
        for (int i = 0; i < graphToCountFrom.graphOrder; i++) {
            //Check for vertices adjacent to vertex i
            for (int j = 0; j < graphToCountFrom.graphOrder; j++) {
                if (graphToCountFrom.adjMat[i][j] == 1) {
                    //If vertex is adjacent to vertex i, add it to adjacentVerticesArray
                    verticesAdjacentToVertexI[currentAdjacentVerticesCount] = j;
                    currentAdjacentVerticesCount++;
                }
            }

            //Now that we have all vertices adjacent to i, check if any are adjacent to eachother
            for (int j = 0; j < currentAdjacentVerticesCount; j++) {
                for (int k = j; k < currentAdjacentVerticesCount; k++) {
                   if (graphToCountFrom.adjMat[verticesAdjacentToVertexI[j]][verticesAdjacentToVertexI[k]] == 1) {
                        numberOfTriangles++;
                    }
                }
            }

            //Reset adjacent vertices count before beggining check for next node
            currentAdjacentVerticesCount = 0;
        }

        numberOfTriangles = numberOfTriangles / 3;
        return numberOfTriangles;
    }
}