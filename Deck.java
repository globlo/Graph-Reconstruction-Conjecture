//GRC Project
//Deck.java

//A Deck is an object to represent the collection of subgraphs created when a graph has each vertex removed once
//This object stores a deck as an array of Graph objects, and also stores the number of cards in the deck seperately for readability
//We do not allow a Deck to be manipulated after it has been created
public class Deck {
    public final Graph[] deckArr;
    public final int numberOfCards;
    
    //Constructor for a Deck. 
    public Deck(Graph[] deckArray) {
        deckArr = deckArray;
        numberOfCards = deckArr.length;
    }

    //Prints the contents of each graph within the deck
    public void printDeck() {
        for(int i = 0; i < deckArr.length; i++) {
            deckArr[i].printGraph();
            if (i != (deckArr.length - 1)) {
                System.out.println(",");
            }
        }
    }

    //Creates an array of Graphs made by removing each vertex from the original graph once, then passes that array to the deck constructor
    public static Deck createDeckFromGraph(Graph originalGraph){
        Graph[] deckArr = new Graph[originalGraph.graphOrder];
        for (int i = 0; i < originalGraph.graphOrder; i++) {
            deckArr[i] = Graph.creatSubgraphWithRemovedVertex(originalGraph, i);
        }
        Deck deckToReturn = new Deck(deckArr);
        return deckToReturn;
    }

    //Gets the degree sequence of deck
    public static int[] getDegreeSequenceOfOriginalGraphFromDeck(Deck deckToRead) {
        int[] degreeSequence = new int[deckToRead.numberOfCards];
        int originalGraphNumberOfEdges = GraphLookerAtter.countNumberOfEdgesInOriginalGraph(deckToRead);
        for (int i = 0; i < degreeSequence.length; i++) {
            degreeSequence[i] = originalGraphNumberOfEdges - deckToRead.deckArr[i].numberOfEdges;
        }
        return degreeSequence;
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
                        if (GraphLookerAtter.areGraphsIsomorphic(leftDeck.deckArr[leftDeckCardIndex], rightDeck.deckArr[rightDeckCardIndex]) == true) {
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

    //Performs easy checks to see if the passed deck comes from a legitemate graph
    //Failing this test means the deck is certainly illegitemate
    //Passing this test doesn't mean the deck IS legitemate, just that it might be
    public static boolean doesDeckLookLegitemate(Deck deckToTest) {
        for (int i = 0; i < deckToTest.numberOfCards; i++) {
            //Each card in a deck must have a vertex count of numberOfCards - 1
            if (deckToTest.deckArr[i].graphOrder != (deckToTest.numberOfCards - 1)) {
                return false;
            }
        }
        return true;
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
    
    //Determines if we should use the card with the most missing edges or the least missing edges
    //If a graph has a highly connected vertex, using the most missing edges might reduce possible combinations
    public static boolean shouldWeUseCardWithMostMissingEdges(int numberOfCardVertices, int leastMissingEdges, int mostMissingEdges) {
        int differenceBetweenMostAndNumberOfCards = numberOfCardVertices - mostMissingEdges;
        if (differenceBetweenMostAndNumberOfCards < leastMissingEdges) {
            return true;
        }
        return false;
    }
}
