import java.io.*;


public class Test {

public static void main(String[] args){


    FileWriter fw = null;
        try {
            File file = new File("test.txt");
            fw = new FileWriter(file);

            for(String s : args){
            //System.out.println(s);
            fw.write(s + '\n');
            }

            fw.flush();
            
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fw != null) {
                    fw.close();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
	
    }
}