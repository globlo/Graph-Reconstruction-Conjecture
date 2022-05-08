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
        if (MiscTools.isMatrixValidGraph(adjacencyMatrix) == true) {
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

    //Print contents of a graphs adjacency matrix
    public void printGraph() {
        for (int row = 0; row < adjMat[0].length; row++) {
            for (int col = 0; col < adjMat[0].length; col++) {
                System.out.print(adjMat[row][col]);
            }
            System.out.println();
        }
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

    //Creates a new graph with an adjacency matrix such that each row has equal to or more 1's than the previous row
    public Graph createGraphWithSortedLabels() {
        Graph sortedGraph;
        int[][] sortedMatrix = new int[graphOrder][graphOrder];
        MiscTools.copyMatrix(adjMat, sortedMatrix);
        int[] tmpRow;
        int[] tmpCol = new int[adjMat.length];
        int currentSortedRow = 0;
        //Check in order of increasing vertex degrees
        //Start with i = 1 since every vertex should be connected
        for (int i = 1; i < graphOrder; i++) {
            //Iterate through every row, looking for a row with an edge count of i
            for (int row = currentSortedRow; row < adjMat.length; row++) {
                
                if (degreeSequence[row] == i) {
                    //If we're not swapping with ourself
                    if (currentSortedRow != row) {
                        //Swap current sorted row index with row where degree sequence = i was found
                        tmpRow = sortedMatrix[currentSortedRow];
                        sortedMatrix[currentSortedRow] = sortedMatrix[row];
                        //MiscTools.printMatrix(sortedMatrix);System.out.println();
                        sortedMatrix[row] = tmpRow;
                        
                        for (int j = 0; j < tmpCol.length; j++) {
                            tmpCol[j] = sortedMatrix[j][currentSortedRow];
                        }
                        for (int j = 0; j < tmpCol.length; j++) {
                            sortedMatrix[j][currentSortedRow] = sortedMatrix[j][row];
                        }
                        for (int j = 0; j < tmpCol.length; j++) {
                            sortedMatrix[j][row] = tmpCol[j];
                        }
                    }
                    currentSortedRow++;
                }
            }
        }

        sortedGraph = new Graph(sortedMatrix);
        return sortedGraph;
    }


}
