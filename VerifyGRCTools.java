import java.util.ArrayList;

public class VerifyGRCTools {

    public static void verifyTheKocayGraphsFromTheCubicGraphsInThisFileForGRC(String inputFile, int numOfG6InFile, int sizeOfG6InFile) {
        //Step 1, Read G6 from input file
        System.out.println("Step 1 ------------------------------------------------------------------------------------------------------ ");
        char[][] arrayOfG6 = FileReader.readG6FromFile(inputFile, numOfG6InFile, sizeOfG6InFile);
        //Debug for step 1
        System.out.println("G6 items read: ");
        for (int i = 0; i < arrayOfG6.length; i++) {
            for (int j = 0; j < arrayOfG6[0].length; j++) {
                System.out.print(arrayOfG6[i][j]);
            }
            System.out.println();
        }


        //Step 2, convert G6 into Graph Objects
        System.out.println("Step 2 ------------------------------------------------------------------------------------------------------ ");
        Graph[] arrayOfCubicGraphs = new Graph[numOfG6InFile];
        for (int i = 0; i < arrayOfCubicGraphs.length; i++) {
            arrayOfCubicGraphs[i] = new Graph(MiscTools.graph6_to_matrix(arrayOfG6[i]));
        }
        //Debug for step 2
        System.out.println("Converted G6 into the following graphs:");
        for (int i = 0; i < arrayOfCubicGraphs.length; i++) {
            arrayOfCubicGraphs[i].printGraph();
            System.out.println();
        }

        //Step 3, Generate Kocay graphs from these graph objects
        //Number of generated kocay graphs for a single cubic graph will be (numOfVertices * 3 / 2) 
        //3 edges for each vertex, but since it's undirected, each each is counted twice
        System.out.println("Step 3 ------------------------------------------------------------------------------------------------------ ");
        int numberOfKocayGraphsPerCubicGraph = ((arrayOfCubicGraphs[0].graphOrder * 3) / 2);
        Graph[][] kocayGraphMatrix = new Graph[arrayOfCubicGraphs.length][numberOfKocayGraphsPerCubicGraph];
        for (int i = 0; i < kocayGraphMatrix.length; i++) {
            kocayGraphMatrix[i] = KocayGraphGenerator.generateKocayGraphs(arrayOfCubicGraphs[i]);
        }
        Graph[] kocayGraphArr = new Graph[numberOfKocayGraphsPerCubicGraph * arrayOfCubicGraphs.length];
        int kocayGraphIndex = 0;
        for (int i = 0; i < kocayGraphMatrix.length; i++) {
            for (int j = 0; j < kocayGraphMatrix[i].length; j++) {
                kocayGraphArr[kocayGraphIndex] = kocayGraphMatrix[i][j];
                kocayGraphIndex++;
            }
        }
        //Debug for step 3
        System.out.println("Kocay graphs generated: " + kocayGraphArr.length);
        for (int i = 0; i < kocayGraphArr.length; i++) {
            kocayGraphArr[i].printGraph();
            System.out.println();
        }

        //Step 4, Prune kocayGraphArray into smaller kocayGraphArray that has no isomorphic graphs
        System.out.println("Step 4 ------------------------------------------------------------------------------------------------------ ");
        Graph[] nonIsomorphicKocayGraphs = removeIsomorphicGraphsFromArray(kocayGraphArr);
        //Debug for step 4
        System.out.println("Kocay graphs left over: " + nonIsomorphicKocayGraphs.length);
        for (int i = 0; i < nonIsomorphicKocayGraphs.length; i++) {
            nonIsomorphicKocayGraphs[i].printGraph();
            System.out.println();
        }

        //Step 5, check array of non isomorphic kocay graphs to see if any two graphs share the same deck
        Boolean GRCHolds = checkTheseNonIsomorphicKocayGraphsForCounterExample(nonIsomorphicKocayGraphs);

    }



    public static boolean checkTheseNonIsomorphicKocayGraphsForCounterExample(Graph[] nonIsoKocayGraphArr) {
        Boolean GRCHolds = true;
        Deck outerDeck;
        Deck innerDeck;
        for (int i = 0; i < nonIsoKocayGraphArr.length; i++) {
            outerDeck = Deck.createDeckFromGraph(nonIsoKocayGraphArr[i]);
            for (int j = i + 1; j < nonIsoKocayGraphArr.length; j++) {
                innerDeck = Deck.createDeckFromGraph(nonIsoKocayGraphArr[j]);
                if (DeckExaminer.areTheseDecksIdentical(outerDeck, innerDeck)) {
                    System.out.println("Either we've got an error in our code (Likely) or we've got a counterexample to the GRC:");
                    System.out.println("Graph A:");
                    nonIsoKocayGraphArr[i].printGraph();
                    System.out.println("Graph B:");
                    nonIsoKocayGraphArr[j].printGraph();
                    System.out.println("Share the same deck:");
                    outerDeck.printDeck();
                    GRCHolds = false;
                    return GRCHolds;
                }
            }
        }
        System.out.println("No graphs in this nonIsomorphic kocay array share the same deck.");
        return GRCHolds;
    }

    //Returns the subset of passed parameter graphs such that no two graphs are isomorphic
    public static Graph[] removeIsomorphicGraphsFromArray(Graph[] graphsToCheck) {
        //Used to keep track of which graphs from graphsToCheck are not isomorphic
        ArrayList<Graph> nonIsomorphicGraphsArrList = new ArrayList<Graph>();
        //We can safely add the first graph since it's impossible for it to be isomorphic to anything in our empty arrayList
        nonIsomorphicGraphsArrList.add(graphsToCheck[0]);

        //Iterate over all remaining graphs in graphsToCheck
        for (int i = 1; i < graphsToCheck.length; i++) {
            //Compare the graph at graphsToCheck[i] with every graph currently in our non-isomorphic graph arraylist
            for (int j = 0; j < nonIsomorphicGraphsArrList.size(); j++) {
                System.out.println(i + "/" + graphsToCheck.length + " , " + j + "/" + nonIsomorphicGraphsArrList.size());
                //If the graph at graphsToCheck[i] is isomorphic to ANY graph in our non-isomorphic graph arrayList, then we know it will not be added
                if (GraphExaminer.areGraphsIsomorphicBruteForceV1(graphsToCheck[i], nonIsomorphicGraphsArrList.get(j)) == true) {
                    //Isomorphism found, exist inner loop and begin checking next graph in graphsToCheck
                    break;
                } else {
                    //If we've reached the last graph in our non-isomorphic list, and never triggered our break for isomorphism found, then we can add
                    //the graph at graphsToCheck[i] to our non-isomorphic arraylist
                    if (j == nonIsomorphicGraphsArrList.size() - 1) {
                        //GraphsToCheck[i] is not isomorphic to any graph in nonIsomorphicGraphsArrList, so add it to the list and break from the innter loop
                        nonIsomorphicGraphsArrList.add(graphsToCheck[i]);
                        break;
                    }
                }
            }
        }

        Graph[] nonIsomorphicGraphs = nonIsomorphicGraphsArrList.toArray(new Graph[nonIsomorphicGraphsArrList.size()]);
        return nonIsomorphicGraphs;
    }
}
