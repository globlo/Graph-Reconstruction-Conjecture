//GRC Project
//GRC.java

//GRC is our main method class, experimentation and testing should occur here
public class GRC {
    public static void main(String[] args) {


        long start_program_time = System.nanoTime();

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
        //int n = 3; //the length(# of verteces "i.e. Tirangle: n=3") you want to find
        //System.out.println("Total cycles of length "+n+" are "+Graph.countCycles(sampleMatrix_1, n));

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


        //Testing Deck comparisons
        //Since Sample Graph 1 & 2 are isomorphic, they should be considered identical. Since Sample graph 3 is not isomorphic to 1 or 2, it should return false
        // if (Deck.areTheseDecksIdentical(sampleDeck_1, sampleDeck_2) == true) {
        //     System.out.println("Decks 1 & 2 are identical");
        // }else{
        //     System.out.println("Decks 1 & 2 are NOT identical");
        // }
        // if (Deck.areTheseDecksIdentical(sampleDeck_1, sampleDeck_3) == true) {
        //     System.out.println("Decks 1 & 3 are identical");
        // }else{
        //     System.out.println("Decks 1 & 3 are NOT identical");
        // }

        System.out.println("Attempting to reconstruct graph from following deck:");
        sampleDeck_1.printDeck();
        long start_recunstruct_graph_time = System.nanoTime();
        reconstructGraph(sampleDeck_1);
        long end_recunstruct_graph_time = System.nanoTime();

        long end_program_time = System.nanoTime();
        System.out.println("Program Start Time: "+ start_program_time + " nano sec");
        System.out.println("Program End Time: "+ end_program_time + " nano sec");
        System.out.println("Program Execution Time: "+ (end_program_time - start_program_time)+ " nano sec");
        System.out.println("Graph Reconstruction Time: "+ (end_recunstruct_graph_time - start_recunstruct_graph_time)+ " nano sec");

    }

    //Reconstruct a graph by creating a deck then calling graph reconstruction function with a deck instead
    public static void reconstructGraph(Graph graphToReconstruct) {
        reconstructGraph(Deck.createDeck(graphToReconstruct));
    }

    //Reconstruct a graph from a given deck
    public static Graph reconstructGraph(Deck deckToReconstructFrom) {
        //Calculate original graphs Vertex count
        int calculatedVertexCount = deckToReconstructFrom.numberOfCards;
        //Calculate original graphs Edge count
        int calculatedEdgeCount = GraphLookerAtter.countNumberOfEdgesInOriginalGraph(deckToReconstructFrom);
        //Calculate original graphs degree sequence
        int[] calculatedDegreeSequence = Deck.getDegreeSequenceOfOriginalGraphFromDeck(deckToReconstructFrom);
        //Pick a card to reconstruct from
        Graph cardWeReconstructFrom = deckToReconstructFrom.deckArr[0];
        //Determine the number of edges missing from this card
        int missingEdgeCount = calculatedEdgeCount - cardWeReconstructFrom.numberOfEdges;

        //Try ALL combinations of graphs created by adding one vertex and attaching missingEdgeCount amount of edges to other vertices
        int[][] possibleVerticesToConnectToList = CombinatoricsTools.generateCombinations(calculatedVertexCount - 1, missingEdgeCount);
        for (int i = 0; i < possibleVerticesToConnectToList.length; i++) {
            Graph attemptAtReconstruction = Graph.createGraphWithNewVertex(cardWeReconstructFrom, possibleVerticesToConnectToList[i]);
            if (Deck.areTheseDecksIdentical(deckToReconstructFrom, Deck.createDeck(attemptAtReconstruction)) == true) {
                System.out.println("Graph was reconstructed:");
                attemptAtReconstruction.printGraph();
                return attemptAtReconstruction;
            }
        }
        System.out.println("Graph could not be reconstructed.");
        Graph trivialGraph = new Graph();
        return trivialGraph;
    }
}