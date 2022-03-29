import java.util.ArrayList;

public class GraphLookerAtter {
    
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
        //Create a temporary copy of Mapping with all values subtracted by 1 so they can be easily used for indexing
        int[] tmpMap = new int[Mapping.length];
        for (int i = 0; i < tmpMap.length; i++) {
            tmpMap[i] = Mapping[i] - 1;
        }
        //Iterate through every vertex
        for (int i = 0; i < Mapping.length; i++) {
            for (int j = 0; j < Mapping.length; j++) {
                if (leftGraph.adjMat[i][j] != rightGraph.adjMat[tmpMap[i]][tmpMap[j]]) {
                    adjacenciesHold = false;
                }
            }
        }
        return adjacenciesHold;
    }

    //Return the number of edges in the original graph by using Kelly's lemma
    //Original graph edge count = Total number of edges in deck divided by (number of cards - 2)
    public static int countNumberOfEdgesInOriginalGraph(Deck deckToCountFrom) {
        int edgeCount = 0;
        for (int i = 0; i < deckToCountFrom.numberOfCards; i++) {
            edgeCount += deckToCountFrom.deckArr[i].numberOfEdges;
        }
        edgeCount = edgeCount / (deckToCountFrom.numberOfCards - 2);
        return edgeCount;
    }

    

    //Create ALL possible mappings between two graphs
    public static int[][] generateAllPossibleMaps(int orderOfGraph) {
        
        ArrayList<int[]> mappingList = new ArrayList<int[]>();
        int[][] finalMapList = new int[MiscTools.getFactorial(orderOfGraph)][orderOfGraph];
        //Initialize integerList to be used for map generation
        int[] baseMap = new int[orderOfGraph];
          
        int tmpCounter = 1;
        for (int i = 0; i < baseMap.length; i++) {
            baseMap[i] = tmpCounter;
            tmpCounter++;
        }

        //Fill mappingList with all permutations
        CombinatoricsTools.permutationGenerator(baseMap, 0, mappingList);
        //Convert mappingList to int matrix
        for(int i = 0; i < finalMapList.length; i++) {
            for (int j = 0; j < finalMapList[i].length; j++) {
                finalMapList[i][j] = (int)mappingList.get(i)[j];
            }
        }
        mappingList.clear();
        return finalMapList;
    }



    
}