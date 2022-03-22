public class GRC {
    public static void main(String[] args) {
        int[][] adjRep = {  {0,1,1,0,0,0},
                            {1,0,1,0,1,0},
                            {1,1,0,1,0,0},
                            {0,0,1,0,1,1},
                            {0,1,0,1,0,1},
                            {0,0,0,1,1,0}   };
        int[][] adjRep2 = { {0,1,1,0,0,0},
                            {1,0,1,0,1,0},
                            {1,1,0,1,0,0},
                            {0,0,1,0,1,1},
                            {0,1,0,1,0,1},
                            {0,0,0,1,1,0}   };
        Graph testGraph = new Graph(adjRep);
        Graph testGraph2 = new Graph(adjRep2);
        Graph[] testDeckArr = {testGraph, testGraph2};
        Deck testDeck = new Deck(testDeckArr);
        testDeck.printDeck();
    }
}