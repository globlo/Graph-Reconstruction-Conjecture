public class KocayGraphGenerator {

   public static Graph[] generateKocayGraphs(Graph cubicGraphToGenerateFrom){

		Graph cubicGraph = cubicGraphToGenerateFrom;
		Graph[] kocayGraphsArr = new Graph[cubicGraph.numberOfEdges];
		int[][] cubicGraphMatrixCopy = new int[cubicGraph.graphOrder][cubicGraph.graphOrder];
		MiscTools.copyMatrix(cubicGraph.adjMat, cubicGraphMatrixCopy);
		
		int index = 0;
		// for(int rowCount = 0; rowCount < cubicGraph.graphOrder; rowCount++){
		// 	for(int colCount = 0; colCount < cubicGraph.graphOrder; colCount++){
		// 		kocayGraphCopy.adjMat[rowCount][colCount] = cubicGraph.adjMat[rowCount][colCount];
		// 	}
		// }

		for(int row = 0; row < cubicGraph.graphOrder; row++){
			for(int col = 0; col < cubicGraph.graphOrder; col++){
				System.out.println(row + "," + col);

				//If we have an edge at (row,col)
				if(cubicGraphMatrixCopy[row][col] == 1){
					System.out.println("Edge detected");
					//Then create a new empty matrix
					int[][] matrix = new int[cubicGraph.graphOrder][cubicGraph.graphOrder];
					MiscTools.copyMatrix(cubicGraph.adjMat, matrix);
					matrix[row][col] = 0;
					matrix[col][row] = 0;

					System.out.println("Created Kocay Graph:");
					MiscTools.printMatrix(matrix);
					System.out.println();

					//Then create a graph object from this matrix
					Graph kocayGraph = new Graph(matrix);
					cubicGraphMatrixCopy[row][col] = 0;
					cubicGraphMatrixCopy[col][row] = 0;
					kocayGraphsArr[index] = kocayGraph;

					index++;
				}
			}
		}
		return kocayGraphsArr;
   }
}