public class Graph {
    public int[][] adjMat;
    public int graphOrder = 1;
    public int numberOfEdges = 0;

    //Constructor for graph
    public Graph(int[][] adjacencyMatrix) {
        if (isMatrixValid(adjacencyMatrix) == true) {
            adjMat = adjacencyMatrix;
            graphOrder = adjMat[0].length;
            numberOfEdges = countNumerOfEdgesInGraph();
        } else {
            //If the graph is invalid, create single vertex graph instead to not break things too much
            int[][] singleVertexGraphMatrix = {{0}};
            adjMat = singleVertexGraphMatrix;
        }
    }

    //Checks if a graph's adjacency matrix is valid.
    //This means it's adjacency matrix should be square, mirrored, and it's diaganols should all be zero
    public static boolean isMatrixValid(int[][] matrixToCheck) {
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
                        System.out.println("Attempted to create graph using matrix that isn't mirrored");
                    }
                    //Check that diaganols are zero
                    if (row == col) {
                        if (matrixToCheck[row][col] != 0) {
                            System.out.println("Attempted to create graph using matrix with non-zero diaganol");
                            graphIsLegit = false;
                        }
                    }
                }
            }
        }
        return graphIsLegit;
    }

    //Print contents of adjacency matrix
    public void printGraph() {
        for (int i = 0; i < adjMat[0].length; i++) {
            for (int j = 0; j < adjMat[0].length; j++) {
                System.out.print(adjMat[i][j]);
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

    //Create full deck of subgraphs from given graph    
    public static Deck createDeck(Graph originalGraph) {
        Graph[] deckArr = new Graph[originalGraph.graphOrder];
        Deck deckToReturn;
        for (int i = 0; i < originalGraph.graphOrder; i++) {
            deckArr[i] = Graph.creatSubgraphWithRemovedVertex(originalGraph, i);
        }
        deckToReturn = new Deck(deckArr);
        return deckToReturn;
    }

    //Set an adjacency matrix to all zeros
    public void initializeGraphToZeros() {
        for (int row = 0; row < adjMat.length; row++) {
            for (int col = 0; col < adjMat.length; col++) {
                adjMat[row][col] = 0;
            }
        }
    }

    //Count number of edges in a graph
    public int countNumerOfEdgesInGraph() {
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

    /*
        Purpose: Save the graph object to the text file
    */
    public void printGraphToTextFile() throws IOException {

        File file = new File("output.txt"); //Output file name where graph object will be saved
        PrintWriter outputFile = new PrintWriter(file);

        for (int i = 0; i < adjMat[0].length; i++) {
            for (int j = 0; j < adjMat[0].length; j++) {
                outputFile.print(adjMat[i][j]);
            }
            outputFile.println();
        }
        outputFile.close();
    }

    //Gets the degree sequence of graph
    public int[] getDegreeSequenceOfGraph(int[][] graph) throws IOException {
	    int[] degreeSequence = new int[graph.length];

		for(int i = 0; i < graph[0].length; i++) {
			int counter = 0;
			for (int j = 0; j < graph[0].length; j++) {
				if(graph[i][j] == 1){
					counter++;
				}
			}
			degreeSequence[i] = counter;
		}
		return degreeSequence;
    }

    //Gets the degree sequence of deck
    public int[] getDegreeSequenceOfDeck(Graph deck) throws IOException {
		int[] degreeSequence = new int[deck.adjMat.length];

		for(int i = 0; i < deck.adjMat.length; i++) {
			int counter = 0;
			for (int j = 0; j < deck.adjMat.length; j++) {
				if(deck.adjMat[i][j] == 1){
					counter++;
				}
			}
			degreeSequence[i] = counter;
		}
		return degreeSequence;
    }
}
