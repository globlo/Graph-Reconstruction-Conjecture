public class GRC {
    public static void main(String[] args) {
<<<<<<< Updated upstream
        int[][] sampleMatrix = {{0,1,0,1},
                                {1,0,1,1},
                                {0,1,0,1},
                                {1,1,1,0}};
=======
        //Generating simple Graphs and their decks
        int[][] sampleMatrix_1 = {  {0,1,0,1},
                                    {1,0,1,1},
                                    {0,1,0,1},
                                    {1,1,1,0}};
        int[][] sampleMatrix_2 = {  {0,1,1,1},
                                    {1,0,1,0},
                                    {1,1,0,1},
                                    {1,0,1,0}};
        int[][] sampleMatrix_3 = {  {0,1,0,1},
                                    {1,0,1,0},
                                    {0,1,0,1},
                                    {1,0,1,0}};
        Graph sampleGraph_1 = new Graph(sampleMatrix_1);
        Graph sampleGraph_2 = new Graph(sampleMatrix_2);
        Graph sampleGraph_3 = new Graph(sampleMatrix_3);
        Deck sampleDeck_1 = Deck.createDeck(sampleGraph_1);
        Deck sampleDeck_2 = Deck.createDeck(sampleGraph_2);
        Deck sampleDeck_3 = Deck.createDeck(sampleGraph_3);

        //Testing Count # of cycle in Graph
        int n = 3; //the length(# of verteces "i.e. Tirangle: n=3") you want to find
        System.out.println("Total cycles of length "+n+" are "+Graph.countCycles(sampleMatrix_1, n));

        //Testing file input
        // sampleGraph_1 = FileReader.readGraphFromFile("sampleGraphWSpaces.txt");
        // System.out.println("Sample Graph from file:");
        // sampleGraph_1.printGraph();

        //Testing edge calculations
        // System.out.print("Edges in sample graph:");
        // System.out.println(sampleGraph_1.numberOfEdges);
        // System.out.print("Edges in sample graph determined by deck: ");
        // System.out.println(GraphLookerAtter.countNumberOfEdgesInOriginalGraph(sampleDeck));

        //Testing creation of graph from card
        // int[] vertexListToAttachTo = {0,2};
        // Graph sampleGraph_4 = Graph.createGraphWithNewVertex(sampleGraph_1, vertexListToAttachTo);
        // System.out.println("Sample Graph with new vertex connected to vertices 0 and 2:");
        // sampleGraph_4.printGraph();
>>>>>>> Stashed changes

        Graph sampleGraph = new Graph(sampleMatrix);
        System.out.println("Sample Graph:");
        sampleGraph.printGraph();
        System.out.print("Edges in sample graph:");
        System.out.println(sampleGraph.numberOfEdges);

        Deck sampleDeck = Graph.createDeck(sampleGraph);
        System.out.println("Sample Deck:");
        sampleDeck.printDeck();

        System.out.print("Edges in sample graph determined by deck:");
        System.out.println(countNumberOfEdgesInOriginalGraph(sampleDeck));

    }

    //Reconstruct a graph after first turning it into a deck, then back into a graph
    public static void reconstructGraph(Graph graphToReconstruct) {
        Deck deckToReconstruct = Graph.createDeck(graphToReconstruct);
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