import java.util.ArrayList;

public class FeatureTesting {

    //Generating simple Graphs and their decks
    public static int[][] sampleMatrix_1 = {{0,1,0,1},
                                            {1,0,1,1},
                                            {0,1,0,1},
                                            {1,1,1,0}};
    public static Graph sampleGraph_1 = new Graph(sampleMatrix_1);
    public static Deck sampleDeck_1 = Deck.createDeckFromGraph(sampleGraph_1);
    public static int[][] sampleMatrix_2 = {{0,1,1,1},
                                            {1,0,1,0},
                                            {1,1,0,1},
                                            {1,0,1,0}};
    public static Graph sampleGraph_2 = new Graph(sampleMatrix_2);
    public static Deck sampleDeck_2 = Deck.createDeckFromGraph(sampleGraph_2);
    public static int[][] sampleMatrix_3 = {{0,1,0,1},
                                            {1,0,1,0},
                                            {0,1,0,1},
                                            {1,0,1,0}};
    public static Graph sampleGraph_3 = new Graph(sampleMatrix_3);
    public static Deck sampleDeck_3 = Deck.createDeckFromGraph(sampleGraph_3);

    //Testing matrix file input
    public static void readMatrixFromFile(String fileName) {
        Graph sampleGraph_4 = FileReader.readGraphFromFile(fileName);
        System.out.println("Sample Graph from file:");
        sampleGraph_4.printGraph();
    }
    
    //Testing edge calculations that are determined when a graph is created
    public static void edgeCountDeterminedAtGraphGeneration(Graph graphToCountEdgesIn) {
        System.out.print("Edges in sample graph:");
        System.out.println(graphToCountEdgesIn.numberOfEdges);
    }

    //Testing edge counting based off a deck
    public static void edgeCalculationFromDeck(Deck deckToCountEdgesFrom) {
        System.out.println("There are " + DeckExaminer.countNumberOfEdgesInOriginalGraph(deckToCountEdgesFrom) + " edges in the graph that the following deck was generated from:");
        deckToCountEdgesFrom.printDeck();
    }

    //Testing creation of graph from card
    public static void addingVertexWithEdgesToGraph(int[] vertexListToAttachTo, Graph graphToBuildFrom) {
        System.out.println("Graph we're going to attach edges to:");
        graphToBuildFrom.printGraph();
        Graph sampleGraph_4 = GraphExaminer.createGraphWithNewVertexAndEdges(graphToBuildFrom, vertexListToAttachTo);
        System.out.print("Sample Graph with new vertex connected to vertices");
        for (int i = 0; i < vertexListToAttachTo.length; i++) {
            System.out.print(" " + vertexListToAttachTo[i]);
        }
        System.out.println();
        sampleGraph_4.printGraph();
    }

    //Testing generation of all possible vertex mappings (Used in brute force isomorphism checking)
    public static void generatingAllPermutationsForAGraphsVertices(int numberOfVertices) {
        int[][] permutationList = CombinatoricsTools.generateAllPossibleMaps(numberOfVertices);
        System.out.println("Generated " + permutationList.length + " permutations:");
        for (int i = 0; i < permutationList.length; i++) {
            for (int j = 0; j < permutationList[i].length; j++) {
                System.out.print(permutationList[i][j]);
            }
            System.out.println();
        }
    }

    //Testing generation of combinations n choose r
    public static void generatingCombinationOfNOfSizeR(int n, int r) {
        int[][] combinationsList = CombinatoricsTools.generateCombinations(n, r);
        System.out.println("Generated " + combinationsList.length + " combinations:");
        for (int i = 0; i < combinationsList.length; i++) {
            for (int j = 0; j < combinationsList[i].length; j++) {
                System.out.print(combinationsList[i][j]);
            }
            System.out.println();
        }
    }

    //Testing isomorphism checking by brute force
    public static void graphIsomorphismTestingByBruteForce(Graph leftGraph, Graph rightGraph) {
        System.out.println("Testing if the following two graphs are isomorphic by brute force.");
        System.out.println("Graph 1:");
        leftGraph.printGraph();
        System.out.println("Graph 2:");
        rightGraph.printGraph();
        if (GraphExaminer.areGraphsIsomorphicBruteForce(leftGraph, rightGraph) == true) {
            System.out.println("Graphs 1 & 2 are isomorphic");
        }else{
            System.out.println("Graphs 1 & 2 are NOT isomorphic");
        }
    }

    //Testing isomorphism checking via tree search
    public static void graphIsomorphismTestingByTreeSearch(Graph leftGraph, Graph rightGraph) {
        System.out.println("Testing if the following two graphs are isomorphic by tree search.");
        System.out.println("Graph 1:");
        leftGraph.printGraph();
        System.out.println("Graph 2:");
        rightGraph.printGraph();
        if (GraphExaminer.areGraphsIsomorphicTreeSearch(leftGraph, rightGraph) == true) {
            System.out.println("Graphs 1 & 2 are isomorphic");
        }else{
            System.out.println("Graphs 1 & 2 are NOT isomorphic");
        }
    }

    //Testing Deck comparisons
    public static void deckEquivalencyChecking(Deck leftDeck, Deck rightDeck) {
        if (GRC.useBruteForceIsomorphismCheck == true) {
            System.out.println("Testing if the following two decks are equivalent by brute force.");
        } else {
            System.out.println("Testing if the following two decks are equivalent by tree search.");
        }
        System.out.println("Deck 1:");
        leftDeck.printDeck();
        System.out.println("Graph 2:");
        rightDeck.printDeck();
        if (DeckExaminer.areTheseDecksIdentical(leftDeck, rightDeck) == true) {
            System.out.println("Decks 1 & 2 are identical");
        }else{
            System.out.println("Decks 1 & 2 are NOT identical");
        }
    }

    //Testing for illegitemate deck
    public static void illegitemateDeckChecking() {
        //We have to manually make a fake deck since our deck generator is FABULOUS
        Graph[] fakeGraphArr = {sampleGraph_3, sampleGraph_2, sampleGraph_1, sampleGraph_3};
        Deck fakeDeck = new Deck(fakeGraphArr);
        System.out.println("Checking if the following deck is legitemate:");
        fakeDeck.printDeck();
        if (DeckExaminer.isDeckLegitimate(fakeDeck) == true) {
            System.out.println("Deck is legitemate");
        } else {
            System.out.println("Deck is illegitemate");
        }
    }

    //Testing ability to find ALL possible reconstructions as well as time tracking
    public static void findingAllReconstructionsFromADeck(Deck deckToCheck) {
        System.out.println("Attempting to find all reconstructions from following deck:");
        deckToCheck.printDeck();
        ArrayList<Graph> foundReconstructions;
        foundReconstructions = ReconstructionAlgorithms.findAllReconstructionsByForce(deckToCheck);
        System.out.println("Found " + foundReconstructions.size() + " possible reconstructions.");
        for (int i = 0; i < foundReconstructions.size(); i++) {
            System.out.println("Reconstruction " + i + ":");
            foundReconstructions.get(i).printGraph();
            System.out.println();
        }
    }
    
    //Testing integer matrix to Graph6 translation
    public static void convertingGraphMatrixToGraph6(Graph graphToConvert) {
        System.out.println("\nConverting the following adjacency matrix to it's graph6 equivalent: ");
        graphToConvert.printGraph();
        char[] converted_Chars = MiscTools.matrixToGraph6(graphToConvert.adjMat);
        System.out.print("Graph6 equivalent: ");
        for(int i = 0; i < converted_Chars.length; i++) {
            System.out.print(converted_Chars[i]);
        }
        System.out.println();
    }

    //Testing graph6 to integer matrix translation
    public static void convertingGraph6ToMatrix(char[] graph6ToConvert) {
        System.out.print("\nConverting the following graph6 value to it adjacency matrix equivalent: ");
        for (int i = 0; i < graph6ToConvert.length; i++) {
            System.out.print(graph6ToConvert[i]);
        }
        System.out.println();
        int[][] sampleMatrix = MiscTools.graph6ToMatrix(graph6ToConvert);
        System.out.println("Matrix equivalent: ");
        for(int i = 0; i < sampleMatrix[0].length; i++){
            for(int j = 0; j < sampleMatrix[0].length; j++){
                System.out.print(sampleMatrix[i][j]);
            }
            System.out.println();
        }
    }

    //Testing Tree generation
    public static void generatingTreeNodesSequences(int[] usedValues, int totalValues) {
        System.out.print("A node with the values:");
        for (int i = 0; i < usedValues.length; i++) {
            System.out.print(" " + usedValues[i]);
        }
        ArrayList<Integer> usedValuesArrList = new ArrayList<Integer>();
        for (int i = 0; i < usedValues.length; i++) {
            usedValuesArrList.add(usedValues[i]);
        }
        int[] childSeq = GraphExaminer.generateChildSequence(usedValuesArrList, totalValues);
        System.out.print(" between itself (including itself) and the root of the tree, will have " + childSeq.length + " child nodes containing the values:");
        for (int i = 0; i < childSeq.length; i++) {
            System.out.print(" " + childSeq[i]);
        }
        System.out.println(" (Assuming the number of vertices in the graph being matched is " + totalValues + ")");
    }

    //Testing graph relabeling
    public static void relabelingGraphSuchThatDegreeSequenceIsSorted(Graph graphToRelabel) {
        System.out.println("Relabeling the following graph such that it's degree sequence will be sorted:");
        Graph sortedGraph = graphToRelabel.createGraphWithSortedLabels();
        graphToRelabel.printGraph();
        System.out.println("After relabeling:");
        sortedGraph.printGraph();
    }

    //Testing Kocay Graph Generation
    public static void generatingKocayGraphs(Graph cubicGraphToGenerateFrom) {
        System.out.println("Generating " + cubicGraphToGenerateFrom.numberOfEdges + " cubic graphs from the following graph:");
        cubicGraphToGenerateFrom.printGraph();
        Graph[] kocayArray = KocayGraphGenerator.generateKocayGraphs(cubicGraphToGenerateFrom);
        for (int i = 0; i < kocayArray.length; i++) {
            System.out.println("Kocay Graph " + i + ":");
            kocayArray[i].printGraph();
        }
    }

    //Testing for isomorphism pruning
    public static void pruningGraphArrByIsomorphisms() {
        int[][] sampleMatrix_4 = {  {0,1,1,1},
                                    {1,0,1,1},
                                    {1,1,0,1},
                                    {1,1,1,0}};
        Graph sampleGraph_4 = new Graph(sampleMatrix_4);
        Graph sampleGraph_5 = new Graph(sampleMatrix_2);
        Graph sampleGraph_6 = new Graph(sampleMatrix_1);
        Graph[] graphArr = {sampleGraph_3, sampleGraph_2, sampleGraph_1, sampleGraph_4, sampleGraph_5, sampleGraph_6};
        System.out.println("Pruning the following graphs such that the remaining graphs are all non-isomorphic:");
        MiscTools.printGraphArray(graphArr);
        Graph[] nonIsoGraphArr = VerifyGRCTools.removeIsomorphicGraphsFromArray(graphArr);
        System.out.println("Only " + nonIsoGraphArr.length + "graphs are left after pruning:");
        MiscTools.printGraphArray(nonIsoGraphArr);
    }

    //Testing reading in G6 file
    public static void readingMultipleG6FromFile(String fileName, int numberOfG6InFile, int lenghtOfG6InFile) {
        char[][] g6Array = FileReader.readG6FromFile(fileName, numberOfG6InFile, numberOfG6InFile);
        System.out.println("G6 items read: ");
        for (int i = 0; i < g6Array.length; i++) {
            for (int j = 0; j < g6Array[0].length; j++) {
                System.out.print(g6Array[i][j]);
            }
            System.out.println();
        }
    }

    //Testing Count # of cycle in Graph
    // public static void cycleCounting(Graph graphToLookAt, int lengthOfCyclesToFind) {
    //     System.out.println("There are " + GraphExaminer.countCycles(graphToLookAt.adjMat, lengthOfCyclesToFind) + " cycles of length " + lengthOfCyclesToFind + " in the following Graph: ");
    //     graphToLookAt.printGraph();
    // }
}
