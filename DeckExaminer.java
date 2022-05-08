//GRC Project
//DeckExaminer.java

//Class to contain all of our methods that look at one or more decks
public class DeckExaminer {

    //Return the number of edges in the original graph by using Kelly's lemma
    //Original graph edge count = Total number of edges in deck divided by (number of cards - 2)
    public static int countNumberOfEdgesInOriginalGraph(Deck deckToCountFrom) {
        int edgeCount = 0;
        for (int i = 0; i < deckToCountFrom.numberOfCards; i++) {
            edgeCount += deckToCountFrom.deckArr[i].numberOfEdges;
        }
        edgeCount = edgeCount / (deckToCountFrom.numberOfCards - 2);
        return edgeCount;
    }

    //Gets the degree sequence of deck
    public static int[] getDegreeSequenceOfOriginalGraphFromDeck(Deck deckToRead) {
        int[] degreeSequence = new int[deckToRead.numberOfCards];
        int originalGraphNumberOfEdges = DeckExaminer.countNumberOfEdgesInOriginalGraph(deckToRead);
        for (int i = 0; i < degreeSequence.length; i++) {
            degreeSequence[i] = originalGraphNumberOfEdges - deckToRead.deckArr[i].numberOfEdges;
        }
        return degreeSequence;
    }

    //Performs easy checks to see if the passed deck comes from a legitemate graph
    //Failing this test means the deck is certainly illegitemate
    //Passing this test doesn't mean the deck IS legitemate, just that it might be
    public static boolean doesDeckLookLegitimate(Deck deckToTest) {
        for (int i = 0; i < deckToTest.numberOfCards; i++) {
            //Each card in a deck must have a vertex count of numberOfCards - 1
            if (deckToTest.deckArr[i].graphOrder != (deckToTest.numberOfCards - 1)) {
                return false;
            }
        }
        return true;
    }

    //Determines if two decks contain the same set of cards
    public static boolean areTheseDecksIdentical(Deck leftDeck, Deck rightDeck) {
        boolean decksAreIdentical = true;
        boolean leftCardMatchFound = false;
        int[] usedRightDeckCards = new int[rightDeck.numberOfCards];
        MiscTools.setArrayToZeros(usedRightDeckCards);
        //First do the easy check, are the decks the same size
        if (leftDeck.numberOfCards == rightDeck.numberOfCards) {
            //Then iterate through the left deck card by card, looking for an identical card in the right deck
            for (int leftDeckCardIndex = 0; leftDeckCardIndex < leftDeck.numberOfCards; leftDeckCardIndex++) {
                leftCardMatchFound = false;
                for (int rightDeckCardIndex = 0; rightDeckCardIndex < rightDeck.numberOfCards; rightDeckCardIndex++) {
                    //If we haven't found a match for our current left deck card & we haven't used our current right deck card
                    if (leftCardMatchFound != true && usedRightDeckCards[rightDeckCardIndex] == 0) {
                        //Then check for a match at the current index of our right deck
                        if (GraphExaminer.areGraphsIsomorphicBruteForceV1(leftDeck.deckArr[leftDeckCardIndex], rightDeck.deckArr[rightDeckCardIndex]) == true) {
                            leftCardMatchFound = true;
                            usedRightDeckCards[rightDeckCardIndex] = 1;
                        }
                    }
                }
                //If we iterated through all right cards and couldn't find a match for the left card, return false
                if (leftCardMatchFound == false) {
                    decksAreIdentical = false;
                    return decksAreIdentical;
                }
            }
        }
        return decksAreIdentical;
    }

    //Find the card which has the smallest amount of missing edges
    public static int pickCardWithLeastMissingEdges(Deck deckToSearch) {
        int currentMaximumCardIndex = 0;
        int currentMaximumEdgeCount = deckToSearch.deckArr[0].numberOfEdges;
        for (int i = 1; i < deckToSearch.numberOfCards; i++) {
            if (deckToSearch.deckArr[i].numberOfEdges > currentMaximumEdgeCount) {
                currentMaximumCardIndex = i;
                currentMaximumEdgeCount = deckToSearch.deckArr[i].numberOfEdges;
            }
        }
        return currentMaximumCardIndex;
    }

    //Find the card which has the most amount of missing edges
    public static int pickCardWithMostMissingEdges(Deck deckToSearch) {
        int currentMinimumCardIndex = 0;
        int currentMinimumEdgeCount = deckToSearch.deckArr[0].numberOfEdges;
        for (int i = 1; i < deckToSearch.numberOfCards; i++) {
            if (deckToSearch.deckArr[i].numberOfEdges < currentMinimumEdgeCount) {
                currentMinimumCardIndex = i;
                currentMinimumEdgeCount = deckToSearch.deckArr[i].numberOfEdges;
            }
        }
        return currentMinimumCardIndex;
    }

    //Find the card which will have the smallest amount of possible combinations
    public static int pickBestCardForReconstruction(Deck deckToSearch) {
        int bestCardIndex = -1;
        //Get original edge count of graph
        int originalEdgeCount = DeckExaminer.countNumberOfEdgesInOriginalGraph(deckToSearch);

        //Find indexes of card with least and most missing edges
        int leastMissingIndex = pickCardWithLeastMissingEdges(deckToSearch);
        int mostMissingIndex = pickCardWithMostMissingEdges(deckToSearch);

        //Get actual amount of edges missing from each of the previous two cards
        int LeastMissingEdges = originalEdgeCount - deckToSearch.deckArr[leastMissingIndex].numberOfEdges;
        int mostMissingEdges = originalEdgeCount - deckToSearch.deckArr[mostMissingIndex].numberOfEdges;

        if (MiscTools.shouldWeUseCardWithMostMissingEdges(deckToSearch.deckArr[0].graphOrder, LeastMissingEdges, mostMissingEdges)) {
            bestCardIndex = mostMissingIndex;
        } else {
            bestCardIndex = leastMissingIndex;
        }
        return bestCardIndex;
    }

    //Attempts to reconstruct a graph from a given deck. Stops as soon as one is found
    public static boolean doesReconstructionExist(Deck deckToReconstructFrom) {
        //Calculate original graphs Vertex count
        int originalGraphVertexCount = deckToReconstructFrom.numberOfCards;
        //Calculate original graphs Edge count
        int originalGraphEdgeCount = DeckExaminer.countNumberOfEdgesInOriginalGraph(deckToReconstructFrom);
        //Pick a good card to reconstruct from
        int bestCardIndex = pickBestCardForReconstruction(deckToReconstructFrom);
        Graph cardWeReconstructFrom = deckToReconstructFrom.deckArr[bestCardIndex];
        //Determine the number of edges missing from this card
        int chosenCardMissingEdgeCount = originalGraphEdgeCount - cardWeReconstructFrom.numberOfEdges;

        //Try ALL combinations of graphs created by adding one vertex and attaching missingEdgeCount amount of edges to other vertices
        int[][] possibleVerticesToConnectToList = CombinatoricsTools.generateCombinations(originalGraphVertexCount - 1, chosenCardMissingEdgeCount);
        for (int i = 0; i < possibleVerticesToConnectToList.length; i++) {
            Graph attemptAtReconstruction = GraphExaminer.createGraphWithNewVertexAndEdges(cardWeReconstructFrom, possibleVerticesToConnectToList[i]);
            if (areTheseDecksIdentical(deckToReconstructFrom, Deck.createDeckFromGraph(attemptAtReconstruction)) == true) {
                return true;
            }
        }
        return false;
    }

    //Check if the passed deck is legitimate. A deck can be considered legitimate if at least one reconstruction exists
    public static boolean isDeckLegitimate(Deck deckToCheck) {
        //First do the easy testing which will instantly rule out the blatantly fake decks
        if (doesDeckLookLegitimate(deckToCheck) == false) {
            return false;
        }
        //If we get past the easy test, then the only way to truly know if a deck is legitimate is by finding a reconstruction
        if (doesReconstructionExist(deckToCheck) == true) {
            return true;
        }
        return false;
    }
}