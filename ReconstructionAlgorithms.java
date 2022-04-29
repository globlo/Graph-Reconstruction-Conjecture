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
        int originalGraphEdgeCount = GraphLookerAtter.countNumberOfEdgesInOriginalGraph(deckToReconstructFrom);
        //Pick the first card in the deck as the base of our reconstruction
        Graph cardWeReconstructFrom = deckToReconstructFrom.deckArr[0];
        //Determine the number of edges missing from this card
        int chosenCardMissingEdgeCount = originalGraphEdgeCount - cardWeReconstructFrom.numberOfEdges;

        //Try ALL combinations of graphs created by adding one vertex and attaching the missing amount of edges to other vertices
        //First create an array of all combinations of vertices that can be attached to that will satisfy the missing edge requirement
        int[][] possibleVerticesToConnectToList = CombinatoricsTools.generateCombinations(originalGraphVertexCount - 1, chosenCardMissingEdgeCount);
        //Then iterate through that array, creating a graph with corresponding new edges for each combination
        for (int i = 0; i < possibleVerticesToConnectToList.length; i++) {
            Graph attemptAtReconstruction = Graph.createGraphWithNewVertexAndEdges(cardWeReconstructFrom, possibleVerticesToConnectToList[i]);
            //With this new graph, we now create its deck then compare it to the deckToReconstructFrom
            if (Deck.areTheseDecksIdentical(deckToReconstructFrom, Deck.createDeckFromGraph(attemptAtReconstruction)) == true) {
                //If the decks are the same, we've found a reconstruction, add it to our list
                reconstructions.add(attemptAtReconstruction);
            }
        }
        return reconstructions;
    }


    //Check if the passed deck is legitimate. A deck can be considered legitimate if at least one reconstruction exists
    public static boolean isDeckLegitimate(Deck deckToCheck) {
        //First do the easy testing which will instantly rule out the blatantly fake decks
        if (Deck.doesDeckLookLegitimate(deckToCheck) == false) {
            return false;
        }
        //If we get past the easy test, then the only way to truly know if a deck is legitimate is by finding a reconstruction
        if (doesReconstructionExist(deckToCheck) == true) {
            return true;
        }
        return false;
    }

    //Attempts to reconstruct a graph from a given deck. Stops as soon as one is found
    public static boolean doesReconstructionExist(Deck deckToReconstructFrom) {
        //Calculate original graphs Vertex count
        int originalGraphVertexCount = deckToReconstructFrom.numberOfCards;
        //Calculate original graphs Edge count
        int originalGraphEdgeCount = GraphLookerAtter.countNumberOfEdgesInOriginalGraph(deckToReconstructFrom);
        //Pick a good card to reconstruct from
        int bestCardIndex = Deck.pickBestCardForReconstruction(deckToReconstructFrom);
        Graph cardWeReconstructFrom = deckToReconstructFrom.deckArr[bestCardIndex];
        //Determine the number of edges missing from this card
        int chosenCardMissingEdgeCount = originalGraphEdgeCount - cardWeReconstructFrom.numberOfEdges;

        //Try ALL combinations of graphs created by adding one vertex and attaching missingEdgeCount amount of edges to other vertices
        int[][] possibleVerticesToConnectToList = CombinatoricsTools.generateCombinations(originalGraphVertexCount - 1, chosenCardMissingEdgeCount);
        for (int i = 0; i < possibleVerticesToConnectToList.length; i++) {
            Graph attemptAtReconstruction = Graph.createGraphWithNewVertexAndEdges(cardWeReconstructFrom, possibleVerticesToConnectToList[i]);
            if (Deck.areTheseDecksIdentical(deckToReconstructFrom, Deck.createDeckFromGraph(attemptAtReconstruction)) == true) {
                return true;
            }
        }
        return false;
    }
}