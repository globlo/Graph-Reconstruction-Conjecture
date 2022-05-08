public class KocayGraphGenerator {

   public static Graph cubicGraph;

   public KocayGraphGenerator(Graph graph){
	   cubicGraph = graph;
   }

   public static Graph[] generateKocayGraph(){

		Graph[] kocayGraphs = new Graph[cubicGraph.numberOfEdges];
		int[][] mat = new int[cubicGraph.graphOrder][cubicGraph.graphOrder];
		Graph kocayGraphCopy = new Graph(mat);
		int index = 0;

		for(int rowCount = 0; rowCount < cubicGraph.graphOrder; rowCount++){
			for(int colCount = 0; colCount < cubicGraph.graphOrder; colCount++){
				kocayGraphCopy.adjMat[rowCount][colCount] = cubicGraph.adjMat[rowCount][colCount];
			}
		}

		for(int row = 0; row < cubicGraph.graphOrder; row++){
			for(int col = 0; col < cubicGraph.graphOrder; col++){
				if(cubicGraph.adjMat[row][col] == 1){
					int[][] matrix = new int[cubicGraph.graphOrder][cubicGraph.graphOrder];
					Graph kocayGraph = new Graph(matrix);
					for(int rowC = 0; rowC < cubicGraph.graphOrder; rowC++){
						for(int colC = 0; colC < cubicGraph.graphOrder; colC++){
							kocayGraph.adjMat[rowC][colC] = kocayGraphCopy.adjMat[rowC][colC];
						}
					}
					kocayGraph.adjMat[row][col] = 0;
					kocayGraph.adjMat[col][row] = 0;
					cubicGraph.adjMat[row][col] = 0;
					cubicGraph.adjMat[col][row] = 0;
					kocayGraphs[index] = kocayGraph;
					index++;
				}
			}
		}
		return kocayGraphs;
   }
}