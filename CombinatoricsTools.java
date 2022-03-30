//GRC Project
//CombinatoricsTools.java
import java.util.ArrayList;

//Class to contain all of our combinatorics-related methods
public class CombinatoricsTools {

    //Method I got off the internet because I've now spent 3 hours on my own method and I've noticed the constant
    //dripping on my shoulder is actually my melted brain draining out my ear. I'll either clean this up, or fix my own version by wednesday
    public static void permutationGenerator(int[] array, int pos, ArrayList<int[]> mappingsList) {
        if(pos >= array.length - 1) {
            mappingsList.add(array.clone());
            return;
        }
        for(int i = pos; i < array.length; i++) {
            int tmp = array[pos];
            array[pos] = array[i];
            array[i] = tmp;
            permutationGenerator(array, pos + 1, mappingsList);
            tmp = array[pos];
            array[pos] = array[i];
            array[i] = tmp;
        }
    }

    //Create ALL possible mappings between two graphs
    public static int[][] generateAllPossibleMaps(int orderOfGraph) {
        
        ArrayList<int[]> mappingList = new ArrayList<int[]>();
        int[][] finalMapList = new int[MiscTools.getFactorial(orderOfGraph)][orderOfGraph];
        //Initialize integerList to be used for map generation
        int[] baseMap = new int[orderOfGraph];
        
        int tmpCounter = 0;
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
    
    //Generate all possible combinations from a set of n numbers of size r
    public static int[][] generateCombinations(int n, int r) {
        ArrayList<int[]> combinations = new ArrayList<>();
        helper(combinations, new int[r], 0, n-1, 0);
        int[][] finalCombinationsArray = new int[combinations.size()][r];
        //Convert combinations list to int matrix
        for(int i = 0; i < finalCombinationsArray.length; i++) {
            for (int j = 0; j < finalCombinationsArray[i].length; j++) {
                finalCombinationsArray[i][j] = (int)combinations.get(i)[j];
            }
        }
        return finalCombinationsArray;
    }

    //Recursive function used by generateCombinations function
    private static void helper(ArrayList<int[]> combinations, int[] data, int start, int end, int index) {
        if (index == data.length) {
            int[] combination = data.clone();
            combinations.add(combination);
        } else if (start <= end) {
            data[index] = start;
            helper(combinations, data, start + 1, end, index + 1);
            helper(combinations, data, start + 1, end, index);
        }
    }
}
