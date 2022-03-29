//GRC Project
//CombinatoricsTools.java
import java.util.ArrayList;

//Class to contain all of our combinatorics-related methods
public class CombinatoricsTools {
    //Method I got off the internet because I've now spent 3 hours on my own method and I've noticed the constant
    //dripping on my shoulder is actually my melted brain draining out my ear. I'll either clean this up, or fix my own version by wednesday
    public static void permutationGenerator(int[] array, int pos, ArrayList<int[]> mappingsList){  
        if(pos >= array.length - 1){   
            mappingsList.add(array.clone());
            return;
        }  
        for(int i = pos; i < array.length; i++){   
            int tmp = array[pos];  
            array[pos] = array[i];  
            array[i] = tmp;  
            permutationGenerator(array, pos + 1, mappingsList);   
            tmp = array[pos];  
            array[pos] = array[i];  
            array[i] = tmp;  
        }  
    }
}
