public class Deck {
    public Graph[] deckArr;
    
    public Deck(Graph[] deckArray) {
        deckArr = deckArray;
    }

    public void printDeck() {
        for(int i = 0; i < deckArr.length; i++) {
            deckArr[i].printGraph();
            if (i != (deckArr.length - 1)) {
                System.out.println(",");
            }
        }
    }
}