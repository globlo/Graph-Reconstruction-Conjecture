public class GRC {
    public static void main(String[] args) {
        // int[][] sampleMatrix_1 = {  {0,1,0,1},
        //                             {1,0,1,1},
        //                             {0,1,0,1},
        //                             {1,1,1,0}};
        // int[][] sampleMatrix_2 = {  {0,1,1,1},
        //                             {1,0,1,0},
        //                             {1,1,0,1},
        //                             {1,0,1,0}};
        // int[][] sampleMatrix_3 = {  {0,1,0,1},
        //                             {1,0,1,0},
        //                             {0,1,0,1},
        //                             {1,0,1,0}};


        // Graph sampleGraph_1 = new Graph(sampleMatrix_1);
        // Graph sampleGraph_2 = new Graph(sampleMatrix_2);
        // Graph sampleGraph_3 = new Graph(sampleMatrix_3);
        //Graph sampleGraph = new Graph(AkisScanner("input.txt"));
        //System.out.println("Sample Graph:");
        //sampleGraph_1.printGraph();
        //System.out.print("Edges in sample graph:");
        //System.out.println(sampleGraph_1.numberOfEdges);

        //Deck sampleDeck = createDeck(sampleGraph_1);
        //System.out.println("Sample Deck:");
        //sampleDeck.printDeck();

        //System.out.print("Edges in sample graph determined by deck: ");
        //System.out.println(GraphLookerAtter.countNumberOfEdgesInOriginalGraph(sampleDeck));

        // int[] sampleMap = {2,3,4,1};
        // if (GraphLookerAtter.checkAdjacenciesAcrossMap(sampleGraph_1, sampleGraph_2, sampleMap)) {
        //     System.out.println("Sample Graphs 1 & 2 are isomorphic");
        // }else{
        //     System.out.println("Sample Graphs 1 & 2 are NOT isomorphic");
        // }
        // if (GraphLookerAtter.checkAdjacenciesAcrossMap(sampleGraph_1, sampleGraph_3, sampleMap)) {
        //     System.out.println("Sample Graphs 1 & 3 are isomorphic");
        // }else{
        //     System.out.println("Sample Graphs 1 & 3 are NOT isomorphic");
        // }
        int[][] list = GraphLookerAtter.generateAllPossibleMaps(4);
        System.out.println("Generated " + list.length + " permutations.");
        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < list[i].length; j++) {
                System.out.print(list[i][j]);        
            }
            System.out.println();
        }
    }

    //Reconstruct a graph after first turning it into a deck, then back into a graph
    public static void reconstructGraph(Graph graphToReconstruct) {
        Deck deckToReconstruct = Deck.createDeck(graphToReconstruct);
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
}