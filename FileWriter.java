import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class FileWriter {
    
    //Purpose: Save the graph object to the text file
    public void printGraphToTextFile(Graph graphToWrite) throws IOException {

        File file = new File("graphOutput.txt"); //Output file name where graph object will be saved
        PrintWriter outputFile = new PrintWriter(file);

        for (int i = 0; i < graphToWrite.adjMat.length; i++) {
            for (int j = 0; j < graphToWrite.adjMat[i].length; j++) {
                outputFile.print(graphToWrite.adjMat[i][j]);
            }
            outputFile.println();
        }
        outputFile.close();
    }

    //Print contents of Deck object to a text file
    public void printDeckToTextFile(Deck deckToWrite) throws IOException {
        File file = new File("deckOutput.txt");
        PrintWriter outputFile = new PrintWriter(file);
        for (int i = 0; i < deckToWrite.numberOfCards; i++) {
            outputFile.println("C" + (i + 1));
            for (int j = 0; j < deckToWrite.deckArr[i].adjMat.length; j++) {
                for (int k = 0; k < deckToWrite.deckArr[i].adjMat[j].length; k++) {
                    outputFile.print(deckToWrite.deckArr[i].adjMat[j][k]);
                }
                outputFile.println();
            }
        }
        outputFile.close();
    }
}
