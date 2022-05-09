import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class FileWriter {
    
    //Purpose: Save the graph object to the text file
    public void printGraphToTextFile(Graph graphToWrite) throws IOException {
        File file = new File("graphOutput.txt"); //Output file name where graph object will be saved
        PrintWriter outputFile = new PrintWriter(file);
        for (int row = 0; row < graphToWrite.adjMat.length; row++) {
            for (int col = 0; col < graphToWrite.adjMat[row].length; col++) {
                outputFile.print(graphToWrite.adjMat[row][col]);
            }
            outputFile.println();
        }
        outputFile.close();
    }

    //Print contents of Deck object to a text file
    public void printDeckToTextFile(Deck deckToWrite) throws IOException {
        File file = new File("deckOutput.txt");
        PrintWriter outputFile = new PrintWriter(file);
        for (int card = 0; card < deckToWrite.numberOfCards; card++) {
            outputFile.println("C" + (card + 1));
            for (int row = 0; row < deckToWrite.deckArr[card].adjMat.length; row++) {
                for (int col = 0; col < deckToWrite.deckArr[card].adjMat[row].length; col++) {
                    outputFile.print(deckToWrite.deckArr[card].adjMat[row][col]);
                }
                outputFile.println();
            }
        }
        outputFile.close();
    }
    
   //Save the graph object to the text file in a Sage output format
   //{0:[1,2,3], 4:[0,2], 6:[1,2,3,4,5]} This is a sample Sage output format
   public static void writeSageFormatToFile(Graph graphToWrite){

		try{
			    StringBuilder str = new StringBuilder();
				PrintWriter outputFile = new PrintWriter("SageFormatOutput.txt");
				str.append("{");
				for(int row = 0; row < graphToWrite.graphOrder; row++){
					str.append(row + ":[");
					for(int col = 0; col < graphToWrite.adjMat[row].length; col++){
						if(graphToWrite.adjMat[row][col] == 1){
							str.append(col + ",");
						}
					}
					str.append("], ");
				}
				str.append("}");
				String strFirst = str.toString().replace(",]", "]");
				String strFinal = strFirst.replace(", }", "}");
				outputFile.print(strFinal);
				outputFile.close();
			}
		catch (IOException e) {
			e.printStackTrace();
		}
   }

   public static void writeG6ArrayToFile(char[][] g6Graphs, String fileName){
        try{
            PrintWriter outputFile = new PrintWriter(fileName);
            for (int i = 0; i < g6Graphs.length; i++) {
                for (int j = 0; j < g6Graphs[i].length; j++) {
                    outputFile.print(g6Graphs[i][j]);
                }
                if (i != g6Graphs.length - 1) {
                    outputFile.print('\n');
                }
            }
            outputFile.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
