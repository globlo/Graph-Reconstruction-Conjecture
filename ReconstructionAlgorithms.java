//GRC Project
//ReconstructionAlgorithms.java
import java.util.ArrayList;

//Reconstruction Algorithms is where we store the actual step by step methods required to reconstruct graphs
public class ReconstructionAlgorithms {
    
    //Reconstruct all possible graphs from a given deck using brute force
    //Assumes deck is legitemate
    public static ArrayList<Graph> findAllReconstructionsByForce(Deck deckToReconstructFrom) {
        //Used to store all found reconstructions
        ArrayList<Graph> reconstructions = new ArrayList<>();
        //Calculate original graphs Vertex count
        int originalGraphVertexCount = deckToReconstructFrom.numberOfCards;
        //Calculate original graphs Edge count
        int originalGraphEdgeCount = DeckExaminer.countNumberOfEdgesInOriginalGraph(deckToReconstructFrom);
        //Pick the first card in the deck as the base of our reconstruction
        Graph cardWeReconstructFrom = deckToReconstructFrom.deckArr[0];
        //Determine the number of edges missing from this card
        int chosenCardMissingEdgeCount = originalGraphEdgeCount - cardWeReconstructFrom.numberOfEdges;

        //Try ALL combinations of graphs created by adding one vertex and attaching the missing amount of edges to other vertices
        //First create an array of all combinations of vertices that can be attached to that will satisfy the missing edge requirement
        int[][] possibleVerticesToConnectToList = CombinatoricsTools.generateCombinations(originalGraphVertexCount - 1, chosenCardMissingEdgeCount);
        //Then iterate through that array, creating a graph with corresponding new edges for each combination
        for (int i = 0; i < possibleVerticesToConnectToList.length; i++) {
            Graph attemptAtReconstruction = GraphExaminer.createGraphWithNewVertexAndEdges(cardWeReconstructFrom, possibleVerticesToConnectToList[i]);
            //With this new graph, we now create its deck then compare it to the deckToReconstructFrom
            if (DeckExaminer.areTheseDecksIdentical(deckToReconstructFrom, Deck.createDeckFromGraph(attemptAtReconstruction)) == true) {
                //If the decks are the same, we've found a reconstruction, add it to our list
                reconstructions.add(attemptAtReconstruction);
            }
        }
        return reconstructions;
    }
    
}