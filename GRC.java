//GRC Project
//GRC.java
import java.util.ArrayList;

//GRC is our main method class, experimentation and testing should occur here
public class GRC {
    public static void main(String[] args) {
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
        Deck sampleDeck_1 = Deck.createDeckFromGraph(sampleGraph_1);
        Deck sampleDeck_2 = Deck.createDeckFromGraph(sampleGraph_2);
        Deck sampleDeck_3 = Deck.createDeckFromGraph(sampleGraph_3);

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

/* 
        //Testing for illegitemate deck
        Graph[] fakeDeck = {sampleGraph_3, sampleGraph_2, sampleGraph_1, sampleGraph_3};
        Deck sampleDeck_4 = new Deck(fakeDeck);
        if (isDeckLegitemate(sampleDeck_4) == true) {
            System.out.println("Deck is legitemate");
        } else {
            System.out.println("Deck is illegitemate");
        }
 */
        //ArrayList<Graph> foundReconstructions;
        //foundReconstructions = findAllReconstructionsByForce(sampleDeck_1);
        // for (int i = 0; i < foundReconstructions.size(); i++) {
        //     foundReconstructions.get(i).printGraph();
        // }
    }

    //Reconstruct a graph by creating a deck then calling graph reconstruction function with a deck instead
    public static void reconstructGraph(Graph graphToReconstruct) {
        reconstructGraph(Deck.createDeckFromGraph(graphToReconstruct));
    }

    //Reconstruct a graph from a given deck
    public static Graph reconstructGraph(Deck deckToReconstructFrom) {
        if (Deck.doesDeckLookLegitimate(deckToReconstructFrom) == true) {
            System.out.println("Deck is illegitemate.");
        } else {

            //Calculate original graphs Vertex count
            int calculatedVertexCount = deckToReconstructFrom.numberOfCards;
            //Calculate original graphs Edge count
            int calculatedEdgeCount = GraphLookerAtter.countNumberOfEdgesInOriginalGraph(deckToReconstructFrom);
            //Calculate original graphs degree sequence
            //int[] calculatedDegreeSequence = Deck.getDegreeSequenceOfOriginalGraphFromDeck(deckToReconstructFrom);
            //Pick a card to reconstruct from
            Graph cardWeReconstructFrom = deckToReconstructFrom.deckArr[0];
            //Determine the number of edges missing from this card
            int missingEdgeCount = calculatedEdgeCount - cardWeReconstructFrom.numberOfEdges;

            //Try ALL combinations of graphs created by adding one vertex and attaching missingEdgeCount amount of edges to other vertices
            int[][] possibleVerticesToConnectToList = CombinatoricsTools.generateCombinations(calculatedVertexCount - 1, missingEdgeCount);
            for (int i = 0; i < possibleVerticesToConnectToList.length; i++) {
                Graph attemptAtReconstruction = Graph.createGraphWithNewVertex(cardWeReconstructFrom, possibleVerticesToConnectToList[i]);
                if (Deck.areTheseDecksIdentical(deckToReconstructFrom, Deck.createDeckFromGraph(attemptAtReconstruction)) == true) {
                    System.out.println("Graph was reconstructed:");
                    attemptAtReconstruction.printGraph();
                    return attemptAtReconstruction;
                }
            }
            System.out.println("Deck is illegitemate.");
        }
        Graph trivialGraph = new Graph();
        return trivialGraph;
    }

    //Reconstruct all possible graphs from a given deck
    public static ArrayList<Graph> findAllReconstructionsByForce(Deck deckToReconstructFrom) {
        ArrayList<Graph> reconstructions = new ArrayList<>();
        if (Deck.doesDeckLookLegitimate(deckToReconstructFrom) == true) {
            System.out.println("Deck is illegitemate.");
        } else {
            //Calculate original graphs Vertex count
            int calculatedVertexCount = deckToReconstructFrom.numberOfCards;
            //Calculate original graphs Edge count
            int calculatedEdgeCount = GraphLookerAtter.countNumberOfEdgesInOriginalGraph(deckToReconstructFrom);
            //Pick a good card to reconstruct from
            int bestCardIndex = Deck.pickCardWithLeastMissingEdges(deckToReconstructFrom);
            Graph cardWeReconstructFrom = deckToReconstructFrom.deckArr[bestCardIndex];
            //Determine the number of edges missing from this card
            int missingEdgeCount = calculatedEdgeCount - cardWeReconstructFrom.numberOfEdges;

            //Try ALL combinations of graphs created by adding one vertex and attaching missingEdgeCount amount of edges to other vertices
            int[][] possibleVerticesToConnectToList = CombinatoricsTools.generateCombinations(calculatedVertexCount - 1, missingEdgeCount);
            for (int i = 0; i < possibleVerticesToConnectToList.length; i++) {
                Graph attemptAtReconstruction = Graph.createGraphWithNewVertex(cardWeReconstructFrom, possibleVerticesToConnectToList[i]);
                if (Deck.areTheseDecksIdentical(deckToReconstructFrom, Deck.createDeckFromGraph(attemptAtReconstruction)) == true) {
                    System.out.println("Reconstruction Found:");
                    attemptAtReconstruction.printGraph();
                    reconstructions.add(attemptAtReconstruction);
                }
            }
        }
        if (reconstructions.size() == 0) {
            System.out.println("Deck is illegitemate.");
        }
        return reconstructions;
    }


    //Reconstruct a graph from a given deck
    public static boolean isDeckLegitimate(Deck deckToReconstructFrom) {
        if (Deck.doesDeckLookLegitimate(deckToReconstructFrom) == false) {
            return false;
        } else {
            //Calculate original graphs Vertex count
            int calculatedVertexCount = deckToReconstructFrom.numberOfCards;
            //Calculate original graphs Edge count
            int calculatedEdgeCount = GraphLookerAtter.countNumberOfEdgesInOriginalGraph(deckToReconstructFrom);
            //Pick a card to reconstruct from
            Graph cardWeReconstructFrom = deckToReconstructFrom.deckArr[0];
            //Determine the number of edges missing from this card
            int missingEdgeCount = calculatedEdgeCount - cardWeReconstructFrom.numberOfEdges;

            

            //Try ALL combinations of graphs created by adding one vertex and attaching missingEdgeCount amount of edges to other vertices
            int[][] possibleVerticesToConnectToList = CombinatoricsTools.generateCombinations(calculatedVertexCount - 1, missingEdgeCount);
            for (int i = 0; i < possibleVerticesToConnectToList.length; i++) {
                Graph attemptAtReconstruction = Graph.createGraphWithNewVertex(cardWeReconstructFrom, possibleVerticesToConnectToList[i]);
                if (Deck.areTheseDecksIdentical(deckToReconstructFrom, Deck.createDeckFromGraph(attemptAtReconstruction)) == true) {
                    return true;
                }
            }
        }
        return false;
    }


}