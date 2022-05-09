//GRC Project
//FileReader.java
import java.io.File;
import java.util.Scanner;

//FileReader stores all of our functions related to reading in a graph or deck from a .txt file
public class FileReader {


    public static String [] ifNextLineHasSpace(String line) {

        if(line.contains(" ")){
            return line.split(" ");
        }
        else{
            return line.split("");    
        }

    }
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
            //String line = ;	
            st = ifNextLineHasSpace(sc.nextLine());
            size = st.length;
            adjacencyMatrix = new int[size][size];
        
            //assign the matirx values in the array
            for (int i = 0; i < size; i++) {  
                for (int j = 0; j < size; j++) {
                    adjacencyMatrix[i][j] = Integer.parseInt(st[j]);
                }
                if (sc.hasNextLine()) {
                    st = ifNextLineHasSpace(sc.nextLine()); 
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


    //Since we know we're dealing with a fixed amount of graphs of equivalent order, we can pass the known size ahead of time
    public static char[][] readG6FromFile(String inputFile, int numOfG6InFile, int numOfCharsInG6) {
        char[][] arrayOfG6 = new char[numOfG6InFile][numOfCharsInG6];
        try {
            File file = new File(inputFile);
            Scanner scanner = new Scanner(file);
            String[] stringArr = new String[numOfG6InFile];
            int counter = 0;

            while (scanner.hasNextLine() == true) {
                stringArr[counter] = scanner.nextLine();
                counter++;
            }
            scanner.close();

            for (int i = 0; i < arrayOfG6.length; i++) {
                arrayOfG6[i] = stringArr[i].toCharArray();
            }
            
            //Debug for step 1
            if (GRC.debug == true) {
                System.out.println("G6 items read: ");
                for (int i = 0; i < arrayOfG6.length; i++) {
                    for (int j = 0; j < arrayOfG6[0].length; j++) {
                        System.out.print(arrayOfG6[i][j]);
                    }
                    System.out.println();
                }
            }
            
        } catch (Exception e) {
            System.out.println("File not found.");
        }

        return arrayOfG6;
    }

}