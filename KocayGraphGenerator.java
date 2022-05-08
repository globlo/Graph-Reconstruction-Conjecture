public class KocayGraphGenerator {

   public static Graph[] generateKocayGraphs(Graph cubicGraphToGenerateFrom){

		Graph cubicGraph = cubicGraphToGenerateFrom;
		Graph[] kocayGraphsArr = new Graph[cubicGraph.numberOfEdges];
		int[][] cubicGraphMatrixCopy = new int[cubicGraph.graphOrder][cubicGraph.graphOrder];
		MiscTools.copyMatrix(cubicGraph.adjMat, cubicGraphMatrixCopy);
		int edgeIndex = 0;

		for(int row = 0; row < cubicGraph.graphOrder; row++){
			for(int col = 0; col < cubicGraph.graphOrder; col++){

				//If we have an edge at (row,col)
				if(cubicGraphMatrixCopy[row][col] == 1){
					//Then create a new empty matrix
					int[][] kocayMatrix = new int[cubicGraph.graphOrder][cubicGraph.graphOrder];
					//Copy over the original cubic graph into this matrix
					MiscTools.copyMatrix(cubicGraph.adjMat, kocayMatrix);
					//Then remove that edge from this matrix
					kocayMatrix[row][col] = 0;
					kocayMatrix[col][row] = 0;

					//Next create a graph object from that matrix and add it to our kocay array
					Graph kocayGraph = new Graph(kocayMatrix);
					kocayGraphsArr[edgeIndex] = kocayGraph;
					edgeIndex++;

					//And finally remove the detected edge from our cubic graph matrix copy so we don't detect it again on the opposite side of the diaganol
					cubicGraphMatrixCopy[row][col] = 0;
					cubicGraphMatrixCopy[col][row] = 0;
				}
			}
		}
		return kocayGraphsArr;
   }
}