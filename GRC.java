//GRC Project
//GRC.java

//GRC is our main method class, experimentation and testing should occur here
public class GRC {

    //Display general step-by-step information
    public static boolean debug = true;

    //Use brute force approach for chekcing for isomorphism. If false, instead use tree search approach
    public static boolean useBruteForceIsomorphismCheck = false;
    //Check that two graphs have the same amount of triangle and or squares before checking for isomorphism
    public static boolean checkTrianglesBeforeIsoChecks = false;
    public static boolean checkSquaresBeforeIsoChecks = false;
    //Check for triangle count without using recursion (Yuck!)
    public static boolean checkTrianglesMichaelsWay = false;
    //Relabel graphs before trying to check for isomorphism
    public static boolean relabelBeforeIsoChecks = false;

    //Save the generated non-isomorphic kocay graphs the the output file specified below
    public static boolean saveNonIsoKocaysToFile = true;
    public static String nonIsomorphicG6KocayOutputFileName = "_nonIsoKocayGraphs.g6";

    //Input file information. Given the finite nature of cubic graphs in G6 format, we felt it acceptable to manually enter dimensions of file
    public static String inputCubicGraphFileName = "_cub12FirstTwo.g6";
    public static int numberOfG6InFile = 2;
    public static int lenghtOfG6InFile = 12;

    public static void main(String[] args) {
        long startProgramTime = System.nanoTime();
        

        //Step 1: Read G6 from input file
        if(debug){System.out.println("\n-------------------------------------------------------------------- Step 1 --------------------------------------------------------------------");}
        char[][] arrayOfG6 = FileReader.readG6FromFile(inputCubicGraphFileName, numberOfG6InFile, lenghtOfG6InFile);

        //Step 2: Convert G6 into Graph Objects
        if(debug){System.out.println("\n-------------------------------------------------------------------- Step 2 --------------------------------------------------------------------");}
        Graph[] cubicGraphArr = VerifyGRCTools.convertG6ArrayIntoGraphArray(arrayOfG6);

        //Step 3: Generate Kocay graphs from these graph objects
        //The number of generated kocay graphs for a single cubic graph will be (numOfVertices * 3 / 2)
        //3 edges for each vertex, but since it's undirected, each each is counted twice
        if(debug){System.out.println("\n-------------------------------------------------------------------- Step 3 --------------------------------------------------------------------");}
        long timeToGenerateKocayArrStart = System.nanoTime();
        Graph[] kocayGraphsArr = VerifyGRCTools.generateAllKocayGraphsFromCubicGraphArr(cubicGraphArr);
        long timeToGenerateKocayArrEnd = System.nanoTime();
        
        //Step 4: Prune kocayGraphArray into smaller kocayGraphArray that has no isomorphic graphs
        if(debug){System.out.println("\n-------------------------------------------------------------------- Step 4 --------------------------------------------------------------------");}
        long timeToGenerateNonIsomorphicKocayArrStart = System.nanoTime();
        Graph[] nonIsomorphicKocayGraphs = VerifyGRCTools.removeIsomorphicGraphsFromArray(kocayGraphsArr);
        long timeToGenerateNonIsomorphicKocayArrEnd = System.nanoTime();

        //Optional Steps: Save these non-isomorphic graphs to a file so you don't need to generate them again in the future
        if (saveNonIsoKocaysToFile == true) {
            //Optional Step 1: conver this pruned list into a G6 array
            if(debug){System.out.println("\n---------------------------------------------------------------- OptionalStep 1 ----------------------------------------------------------------");}
            char[][] nonIsoKocayG6Arr = VerifyGRCTools.convertGraphArrayIntoG6Array(nonIsomorphicKocayGraphs);

            //Optional Step 2: write this G6 array to a file
            if(debug){  System.out.println("\n---------------------------------------------------------------- OptionalStep 2 ----------------------------------------------------------------");
                        System.out.println("Writing G6 Array to " + nonIsomorphicG6KocayOutputFileName);}
            FileWriter.writeG6ArrayToFile(nonIsoKocayG6Arr, nonIsomorphicG6KocayOutputFileName);
        }

        //Step 5: Check array of non isomorphic kocay graphs to see if any two graphs share the same deck
        if(debug){System.out.println("\n-------------------------------------------------------------------- Step 5 --------------------------------------------------------------------");}
        long timeToCheckNonIsomorphicKocayArrStart = System.nanoTime();
        boolean grcHolds = VerifyGRCTools.checkTheseNonIsomorphicKocayGraphsForCounterExample(nonIsomorphicKocayGraphs);
        long timeToCheckNonIsomorphicKocayArrEnd = System.nanoTime();
        if (grcHolds == true) {
            System.out.println("No graphs in the non-isomorphic kocay array share the same deck.");
        } else {
            System.out.println("Either we've got an error in our code (Likely) or we've got a counterexample to the GRC!");
        }

        long endProgramTime = System.nanoTime();
        System.out.println("\nHot dog! We did all that in just: " + (endProgramTime - startProgramTime) + " nano-seconds");

        System.out.println("Parameters:\nBruteForceIsoCheck: " + useBruteForceIsomorphismCheck);
        System.out.println("Triangle Checking: " + checkTrianglesBeforeIsoChecks);
        System.out.println("Non-recursive Triangle Checking: " + checkTrianglesMichaelsWay);
        System.out.println("Square Checking: " + checkSquaresBeforeIsoChecks);
        System.out.println("Relabeling used: " + relabelBeforeIsoChecks);
        System.out.println("Time to generate kocay graphs: " + (timeToGenerateKocayArrEnd - timeToGenerateKocayArrStart) + " nano-seconds");
        System.out.println("Time to remove isomorphic kocay graphs: " + (timeToGenerateNonIsomorphicKocayArrEnd - timeToGenerateNonIsomorphicKocayArrStart) + " nano-seconds");
        System.out.println("Time to check non-isomorphic kocay graphs: " + (timeToCheckNonIsomorphicKocayArrEnd - timeToCheckNonIsomorphicKocayArrStart) + " nano-seconds");
    }
}