//GRC Project
//Deck.java

//A Deck is an object to represent the collection of subgraphs created when a graph has each vertex removed once
//This object stores a deck as an array of Graph objects, and also stores the number of cards in the deck seperately for readability
//We do not allow a Deck to be manipulated after it has been created
public class Deck {
    public final Graph[] deckArr;
    public final int numberOfCards;
    
    //Constructor for a Deck. To ensure validity, this should only be called via createDeck function
    private Deck(Graph[] deckArray) {
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
    public static Deck createDeck(Graph originalGraph){
        Graph[] deckArr = new Graph[originalGraph.graphOrder];
        for (int i = 0; i < originalGraph.graphOrder; i++) {
            deckArr[i] = Graph.creatSubgraphWithRemovedVertex(originalGraph, i);
        }
        Deck deckToReturn = new Deck(deckArr);
        return deckToReturn;
    }

    //Gets the degree sequence of deck
    // public int[] getDegreeSequenceOfDeck(Deck deckToRead) {
    //     int[] degreeSequence = 
    //     return degreeSequence;
    // }
}