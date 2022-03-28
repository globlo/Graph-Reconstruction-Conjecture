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
        int[][] finalMapList = new int[getFactorial(orderOfGraph)][orderOfGraph];
        //Initialize integerList to be used for map generation
        int[] baseMap = new int[orderOfGraph];
          
        int tmpCounter = 1;
        for (int i = 1; i <= baseMap.length; i++) {
            baseMap[i-1] = tmpCounter;
            tmpCounter++;
        }
        //Fill mappingList
        //addAllMaps(baseMap, mappingList);
        helper(baseMap, 0, mappingList);
        //Convert mappingList to int matrix
        for(int i = 0; i < finalMapList.length; i++) {
            for (int j = 0; j < finalMapList[i].length; j++) {
                finalMapList[i][j] = (int)mappingList.get(i)[j];
            }
        }
        return finalMapList;
    }

    public static void addAllMaps(int[] originalMap, ArrayList<int[]> mappingsList) {
        int[] tmpMap = new int[originalMap.length];
        addAllMaps(0, 0, tmpMap, originalMap, mappingsList);
    }

    public static void addAllMaps(int currentIndex, int remainingIndexToAdd, int[] currentValues, int[] originalMap, ArrayList<int[]> mappingsList) {
        if (currentIndex == originalMap.length - 1) {
            currentValues[currentIndex] = originalMap[originalMap.length - 1];
            mappingsList.add(currentValues);
        }else{
            currentValues[currentIndex] = originalMap[remainingIndexToAdd];

            for (int i = remainingIndexToAdd; i < originalMap.length; i++) {
                addAllMaps(currentIndex + 1, i, currentValues, originalMap, mappingsList);
            }
        }
    }

    //Method I got off the internet because I've now spent 3 hours on my own method and I've noticed the constant
    //dripping on my shoulder is actually my melted brain draining out my ear. I'll either clean this up, or fix my own version by wednesday
    private static void helper(int[] array, int pos, ArrayList<int[]> mappingsList){  
        if(pos >= array.length - 1){   
            mappingsList.add(array.clone());
            return;
        }  
  
        for(int i = pos; i < array.length; i++){   
          
            int t = array[pos];  
            array[pos] = array[i];  
            array[i] = t;  
  
        helper(array, pos+1, mappingsList);  
  
            t = array[pos];  
            array[pos] = array[i];  
            array[i] = t;  
        }  
    }  

    //Simple factorial function
    public static int getFactorial(int n){
        int value = 1;
        for (int i = 1; i <= n; i++) {
            value = value * i;
        }
        return value;
    }
}
