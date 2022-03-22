public class Graph {
    public int[][] adjMat;
    
    public Graph(int[][] adjacencyMatrix) {
        adjMat = adjacencyMatrix;
    }

    public int vertexCount() {
        return adjMat[0].length;
    }

    public void printGraph() {
        for(int i = 0; i < adjMat[0].length; i++) {
            for (int j = 0; j < adjMat[0].length; j++) {
                System.out.print(adjMat[i][j]);
            }
            System.out.println();
        }
    }
}