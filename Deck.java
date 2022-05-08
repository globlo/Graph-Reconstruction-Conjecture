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
    //Decks created in this manner are guaranteed legitimate or your money back*       *terms and conditions apply
    public static Deck createDeckFromGraph(Graph originalGraph){
        Graph[] deckArr = new Graph[originalGraph.graphOrder];
        for (int i = 0; i < originalGraph.graphOrder; i++) {
            deckArr[i] = GraphExaminer.creatSubgraphWithRemovedVertex(originalGraph, i);
        }
        Deck deckToReturn = new Deck(deckArr);
        return deckToReturn;
    }
}
