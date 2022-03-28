public class Deck {
    public Graph[] deckArr;
    public int numberOfCards;
    
    public Deck(Graph[] deckArray) {
        deckArr = deckArray;
        numberOfCards = deckArr.length;
    }

    public void printDeck() {
        for(int i = 0; i < deckArr.length; i++) {
            deckArr[i].printGraph();
            if (i != (deckArr.length - 1)) {
                System.out.println(",");
            }
        }
    }

    //Create full deck of subgraphs from given graph    
    public static Deck createDeck(Graph originalGraph){
        Graph[] deckArr = new Graph[originalGraph.graphOrder];
        Deck deckToReturn;
        for (int i = 0; i < originalGraph.graphOrder; i++) {
            deckArr[i] = Graph.creatSubgraphWithRemovedVertex(originalGraph, i);
        }
        deckToReturn = new Deck(deckArr);
        return deckToReturn;
    }
}