import java.util.ArrayList;



public class VerifyGRCTools {

    public static boolean debug = true;

    public static Graph[] convertG6ArrayIntoGraphArray(char[][] g6Array) {
        Graph[] arrayOfCubicGraphs = new Graph[g6Array.length];
        for (int i = 0; i < arrayOfCubicGraphs.length; i++) {
            arrayOfCubicGraphs[i] = new Graph(MiscTools.graph6ToMatrix(g6Array[i]));
        }

        if (debug == true) {
            System.out.println("Converted G6 graphs:");
            for (int i = 0; i < g6Array.length; i++) {
                for (int j = 0; j < g6Array[0].length; j++) {
                    System.out.print(g6Array[i][j]);
                }
                System.out.println();
            }
            System.out.println("\ninto the following Graph Objects:");
            for (int i = 0; i < arrayOfCubicGraphs.length; i++) {
                arrayOfCubicGraphs[i].printGraph();
                System.out.println();
            }
        }

        return arrayOfCubicGraphs;
    }

    public static char[][] convertGraphArrayIntoG6Array(Graph[] graphArray) {
        int lengthOfG6Element = MiscTools.matrixToGraph6(graphArray[0].adjMat).length;
        char[][] arrayOfG6 = new char[graphArray.length][lengthOfG6Element];
        for (int i = 0; i < arrayOfG6.length; i++) {
            arrayOfG6[i] = MiscTools.matrixToGraph6(graphArray[i].adjMat);
        }

        if (debug == true) {
            System.out.println("Converted Graphs:");
            for (int i = 0; i < graphArray.length; i++) {
                graphArray[i].printGraph();
                System.out.println();
            }
            System.out.println("into the following G6 array:");
            for (int i = 0; i < arrayOfG6.length; i++) {
                for (int j = 0; j < arrayOfG6[0].length; j++) {
                    System.out.print(arrayOfG6[i][j]);
                }
                System.out.println();
            }
        }

        return arrayOfG6;
    }


    public static Graph[] generateAllKocayGraphsFromCubicGraphArr(Graph[] cubicGraphArr) {
        int numberOfKocayGraphsPerCubicGraph = ((cubicGraphArr[0].graphOrder * 3) / 2);
        Graph[][] kocayGraphMatrix = new Graph[cubicGraphArr.length][numberOfKocayGraphsPerCubicGraph];
        for (int i = 0; i < kocayGraphMatrix.length; i++) {
            kocayGraphMatrix[i] = KocayGraphGenerator.generateKocayGraphs(cubicGraphArr[i]);
        }
        Graph[] kocayGraphArr = new Graph[numberOfKocayGraphsPerCubicGraph * cubicGraphArr.length];
        int kocayGraphIndex = 0;
        for (int i = 0; i < kocayGraphMatrix.length; i++) {
            for (int j = 0; j < kocayGraphMatrix[i].length; j++) {
                kocayGraphArr[kocayGraphIndex] = kocayGraphMatrix[i][j];
                kocayGraphIndex++;
            }
        }

        if (debug == true) {
            System.out.println("Kocay graphs generated: " + kocayGraphArr.length);
            // for (int i = 0; i < kocayGraphArr.length; i++) {
            //     kocayGraphArr[i].printGraph();
            //     System.out.println();
            // }
        }

        return kocayGraphArr;
    }

    public static boolean checkTheseNonIsomorphicKocayGraphsForCounterExample(Graph[] nonIsoKocayGraphArr) {
        Boolean GRCHolds = true;
        Deck outerDeck;
        Deck innerDeck;
        for (int i = 0; i < nonIsoKocayGraphArr.length; i++) {
            outerDeck = Deck.createDeckFromGraph(nonIsoKocayGraphArr[i]);
            for (int j = i + 1; j < nonIsoKocayGraphArr.length; j++) {
                if(GRC.debug){System.out.print("Checking if Graph " + (i + 1) + " shares the same deck as Graph " + (j + 1) + "... ");}
                innerDeck = Deck.createDeckFromGraph(nonIsoKocayGraphArr[j]);
                if (DeckExaminer.areTheseDecksIdentical(outerDeck, innerDeck)) {
                    System.out.println("Possible counter example found:");
                    System.out.println("Graph A:");
                    nonIsoKocayGraphArr[i].printGraph();
                    System.out.println("Graph B:");
                    nonIsoKocayGraphArr[j].printGraph();
                    System.out.println("Share the same deck:");
                    outerDeck.printDeck();
                    GRCHolds = false;
                    return GRCHolds;
                } else {
                    System.out.println("They do not.");
                }
            }
        }
        return GRCHolds;
    }

    //Returns the subset of passed parameter graphs such that no two graphs are isomorphic
    public static Graph[] removeIsomorphicGraphsFromArray(Graph[] graphsToCheck) {
        //Used to keep track of which graphs from graphsToCheck are not isomorphic
        ArrayList<Graph> nonIsomorphicGraphsArrList = new ArrayList<Graph>();
        //We can safely add the first graph since it's impossible for it to be isomorphic to anything in our empty arrayList
        nonIsomorphicGraphsArrList.add(graphsToCheck[0]);
        System.out.println("Adding graph 1/" + graphsToCheck.length + " to non isomorphic list.");

        //Iterate over all remaining graphs in graphsToCheck
        for (int i = 1; i < graphsToCheck.length; i++) {
            //Compare the graph at graphsToCheck[i] with every graph currently in our non-isomorphic graph arraylist
            for (int j = 0; j < nonIsomorphicGraphsArrList.size(); j++) {
                System.out.print("\nComparing graph " + (i + 1) + "/" + graphsToCheck.length + " to " + (j + 1) + "/" + nonIsomorphicGraphsArrList.size() + " in our non isomorphic list. ");
                //If the graph at graphsToCheck[i] is isomorphic to ANY graph in our non-isomorphic graph arrayList, then we know it will not be added
                if (GraphExaminer.areGraphsIsomorphic(graphsToCheck[i], nonIsomorphicGraphsArrList.get(j)) == true) {
                    //Isomorphism found, exist inner loop and begin checking next graph in graphsToCheck
                    System.out.println("Graph " + (i + 1) + "/" + graphsToCheck.length + " is isomorphic to " + (j + 1) + "/" + nonIsomorphicGraphsArrList.size() + " in our non isomorphic list.");
                    break;
                } else {
                    //If we've reached the last graph in our non-isomorphic list, and never triggered our break for isomorphism found, then we can add
                    //the graph at graphsToCheck[i] to our non-isomorphic arraylist
                    if (j == nonIsomorphicGraphsArrList.size() - 1) {
                        //GraphsToCheck[i] is not isomorphic to any graph in nonIsomorphicGraphsArrList, so add it to the list and break from the innter loop
                        nonIsomorphicGraphsArrList.add(graphsToCheck[i]);
                        System.out.println("Graph " + (i + 1) + "/" + graphsToCheck.length + " is NOT isomorphic to anything in our non isomorphic list, so add it to the list. New list size is: " + nonIsomorphicGraphsArrList.size());
                        break;
                    }
                }
            }
        }
        Graph[] nonIsomorphicGraphs = nonIsomorphicGraphsArrList.toArray(new Graph[nonIsomorphicGraphsArrList.size()]);
        return nonIsomorphicGraphs;
    }
}