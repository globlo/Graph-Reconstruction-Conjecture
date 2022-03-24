public class GRC {
    public static void main(String[] args) {
        int[][] sampleMatrix = {{0,1,0,1},
                                {1,0,1,1},
                                {0,1,0,1},
                                {1,1,1,0}};




        Graph sampleGraph = new Graph(sampleMatrix);
        //Graph sampleGraph = new Graph(AkisScanner("input.txt"));
        System.out.println("Sample Graph:");
        sampleGraph.printGraph();
        System.out.print("Edges in sample graph:");
        System.out.println(sampleGraph.numberOfEdges);

        Deck sampleDeck = createDeck(sampleGraph);
        System.out.println("Sample Deck:");
        sampleDeck.printDeck();

        System.out.print("Edges in sample graph determined by deck:");
        System.out.println(countNumberOfEdgesInOriginalGraph(sampleDeck));

    }

    //Reconstruct a graph after first turning it into a deck, then back into a graph
    public static void reconstructGraph(Graph graphToReconstruct) {
        Deck deckToReconstruct = createDeck(graphToReconstruct);
        Graph reconstructedGraph = reconstructGraph(deckToReconstruct);
        graphToReconstruct.printGraph();
        reconstructedGraph.printGraph();
    }

    //Reconstruct a graph from a given deck
    public static Graph reconstructGraph(Deck deckToReconstructFrom) {
        Graph reconstructedGraph = new Graph(new int[deckToReconstructFrom.numberOfCards][deckToReconstructFrom.numberOfCards]);
        reconstructedGraph.initializeGraphToZeros();
        //Reconstruction stuff goes here
        return reconstructedGraph;
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
}