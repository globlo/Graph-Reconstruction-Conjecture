import java.io.*;

public class TextFileReader{

    public static void main(String args[])throws FileNotFoundException  {

		try{
			BufferedReader br = new BufferedReader(new FileReader("input.txt"));
			String currentLine;

			while((currentLine = br.readLine()) != null)
			{
				String[] data = currentLine.split(" ");
				for(int i = 0; i < data.length; i++)
				{
					System.out.print(data[i] + " ");
				}
				System.out.println();
		   }
		   br.close();
        }
   	    catch (IOException e) {
   		      e.printStackTrace();
        }
   }
}
