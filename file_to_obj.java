import java.io.File;
import java.util.Scanner;

public class file_to_obj {

public static void main(String[] args)throws Exception{

    // File path is passed as parameter
 

        // pass the path to the file as a parameter
        File file = new File("file_to_obj.txt");
        Scanner sc = new Scanner(file);
        
        //declare
        int array[][];
        int i, j, size;
        String[] st;

        //get first line and get the size of matrixes
        String line = sc.nextLine();	
        st = line.split(" ");
        size = st.length;
        array = new int[size][size];

        //assign the matirx values in the array
        for (i=0; i < size; i++) {  
            for (j=0; j < size; j++) {
                array[i][j] = Integer.parseInt(st[j]);
            }
            if(sc.hasNextLine()){
                line = sc.nextLine();	
                st = line.split(" ");
            }
            
        }

        
        //print out the array 
        System.out.println('\n'+"");
        for(i = 0; i < 4; i++){
            for(j = 0; j < 4; j++){

                    System.out.print(array[i][j]);
            }
            System.out.println('\n');
        }

        //Graph sampleGraph = new Graph(array);

    }
}