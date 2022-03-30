//GRC Project
//GRC.java

//GRC is our main method class, experimentation and testing should occur here
public class GRC {
    public static void main(String[] args) {
        //Simple Graph example
        int[][] sampleMatrix_1 = {  {0,1,0,1},
                                    {1,0,1,1},
                                    {0,1,0,1},
                                    {1,1,1,0}};
        Graph sampleGraph_1 = new Graph(sampleMatrix_1);
        System.out.println("Sample Graph:");
        sampleGraph_1.printGraph();

        //Testing file input
        // sampleGraph_1 = FileReader.readGraphFromFile("sampleGraphWSpaces.txt");
        // System.out.println("Sample Graph from file:");
        // sampleGraph_1.printGraph();

        //Testing Deck Generation
        // Deck sampleDeck = Deck.createDeck(sampleGraph_1);
        // System.out.println("Sample Deck:");
        // sampleDeck.printDeck();

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

        //Testing generation of all possible vertex mappings
        // int[][] list = GraphLookerAtter.generateAllPossibleMaps(4);
        // System.out.println("Generated " + list.length + " permutations.");
        // for (int i = 0; i < list.length; i++) {
        //     for (int j = 0; j < list[i].length; j++) {
        //         System.out.print(list[i][j]);
        //     }
        //     System.out.println();
        // }

        //Testing generation of combinations n choose r
        // int[][] combinationsList = CombinatoricsTools.generateCombinations(5,2);
        // System.out.println("Generated " + combinationsList.length + " combinations.");
        // for (int i = 0; i < combinationsList.length; i++) {
        //     for (int j = 0; j < combinationsList[i].length; j++) {
        //         System.out.print(combinationsList[i][j]);
        //     }
        //     System.out.println();
        // }

        //Testing isomorphism checking. sampleMatrix_1 and sampleMatrix_2 should be isomorphic to eachother, while sampleMatrix_3 is not isomorphic to any other
        // int[][] sampleMatrix_2 = {  {0,1,1,1},
        //                             {1,0,1,0},
        //                             {1,1,0,1},
        //                             {1,0,1,0}};
        // Graph sampleGraph_2 = new Graph(sampleMatrix_2);
        // int[][] sampleMatrix_3 = {  {0,1,0,1},
        //                             {1,0,1,0},
        //                             {0,1,0,1},
        //                             {1,0,1,0}};
        // Graph sampleGraph_3 = new Graph(sampleMatrix_3);
        // if (GraphLookerAtter.areGraphsIsomorphic(sampleGraph_1, sampleGraph_2) == true) {
        //     System.out.println("Sample Graphs 1 & 2 are isomorphic");
        // }else{
        //     System.out.println("Sample Graphs 1 & 2 are NOT isomorphic");
        // }
        // if (GraphLookerAtter.areGraphsIsomorphic(sampleGraph_1, sampleGraph_3)) {
        //     System.out.println("Sample Graphs 1 & 3 are isomorphic");
        // }else{
        //     System.out.println("Sample Graphs 1 & 3 are NOT isomorphic");
        // }
    }

    //Reconstruct a graph by creating a deck then calling graph reconstruction function with a deck instead
    public static void reconstructGraph(Graph graphToReconstruct) {
        reconstructGraph(Deck.createDeck(graphToReconstruct));
    }

    //Reconstruct a graph from a given deck
    public static Graph reconstructGraph(Deck deckToReconstructFrom) {
        int calculatedVertexCount = deckToReconstructFrom.numberOfCards;
        int calculatedEdgeCount = GraphLookerAtter.countNumberOfEdgesInOriginalGraph(deckToReconstructFrom);
        //int[] calculatedDegreeSequence = 
        Graph cardWeReconstructFrom = deckToReconstructFrom.deckArr[0];
        
        
        Graph reconstructedGraph = new Graph(new int[deckToReconstructFrom.numberOfCards][deckToReconstructFrom.numberOfCards]);
        //Reconstruction stuff goes here
        
        return reconstructedGraph;
    }
}