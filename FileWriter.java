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
}