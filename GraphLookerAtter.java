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

    //Checks if an isomorphism exists between two graphs
    public static boolean areGraphsIsomorphic(Graph leftGraph, Graph rightGraph) {
        boolean graphsAreIsomorphic = false;
        //First check all the easy requirements for isomorphism
        if ((leftGraph.graphOrder == rightGraph.graphOrder) && (leftGraph.numberOfEdges == rightGraph.numberOfEdges) && (MiscTools.compareSequence(leftGraph.degreeSequence, rightGraph.degreeSequence) == true)) {
            //Then check every possible vertex mapping between the two graphs (n! possibilities yipee!)
            int[][] mappingList = CombinatoricsTools.generateAllPossibleMaps(leftGraph.graphOrder);
            for (int i = 0; i < mappingList.length; i++) {
                graphsAreIsomorphic = checkAdjacenciesAcrossMap(leftGraph, rightGraph, mappingList[i]);
                //If at any point we find a valid mapping, then return true
                if (graphsAreIsomorphic == true) {
                    System.out.println("Isomorphism found between:");
                    leftGraph.printGraph();
                    System.out.println("and:");
                    rightGraph.printGraph();
                    System.out.print("With vertex mapping: [");
                    for (int j = 0; j < mappingList[i].length; j++) {
                        if (j == mappingList[i].length - 1) {
                            System.out.println(mappingList[i][j] + "]");
                        } else {
                            System.out.print(mappingList[i][j] + ",");
                        }
                    }
                    return graphsAreIsomorphic;
                }
            }
        }
        return graphsAreIsomorphic;
    }
}