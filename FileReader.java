//GRC Project
//FileReader.java
import java.io.File;
import java.util.Scanner;

//FileReader stores all of our functions related to reading in a graph or deck from a .txt file
public class FileReader {
    //Reads the adjacency matrix of a given file, then returns the Graph object represented by that matrix
    public static Graph readGraphFromFile(String filename) {
        Graph graphToReturn;
        try {
            File file = new File(filename);
            Scanner sc = new Scanner(file);
            int adjacencyMatrix[][];
            int size;
            String[] st;
        
            //Get the first line to get the size of matrixes
            String line = sc.nextLine();	
            st = line.split(" ");
            size = st.length;
            adjacencyMatrix = new int[size][size];
        
            //assign the matirx values in the array
            for (int i = 0; i < size; i++) {  
                for (int j = 0; j < size; j++) {
                    adjacencyMatrix[i][j] = Integer.parseInt(st[j]);
                }
                if (sc.hasNextLine()) {
                    line = sc.nextLine();	
                    st = line.split(" ");
                }
            }
            sc.close();
            graphToReturn = new Graph(adjacencyMatrix);
            
        } catch (Exception e) {
            System.out.println("File not found.");
            int[][] trivialGraphMatrix = {{0}};
            graphToReturn = new Graph(trivialGraphMatrix);
        }
        return graphToReturn;
    }
}