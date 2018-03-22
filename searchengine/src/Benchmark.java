import java.util.*;

/**
 * Here we can run timed experiments to assess the performance of our system using three different implementations,
 * in order to choose the fastest implementation.
 *
 * Created by GroupU
 */
public class Benchmark {

    /**
     * The main methods sets up all necessary data and objects needed to run the experiment.
     * @param args The database ".txt" file containing the websites we want to search through.
     */
    public static void main(String[] args){
        List<Website> sites = FileHelper.parseFile(args[0]);

        Index indexSimple = new SimpleIndex();
        Index invIndHashMap = new HashMapInvertedIndex();
        Index invIndTreeMap = new TreeMapInvertedIndex();

        List<String> queryWords = new ArrayList<>();
        indexSimple.build(sites);
        invIndHashMap.build(sites);
        invIndTreeMap.build(sites);

        queryWords.add("denmark");
        queryWords.add("america");
//        queryWords.add("the");
        queryWords.add("japan");
        queryWords.add("copenhagen");
        queryWords.add("university");
        queryWords.add("war");
        queryWords.add("government");
        queryWords.add("english");
        queryWords.add("president");
        queryWords.add("queen");

        // which index the runBenchmark method shuold benchmark on
        // method runBenchmark is being run three times:
        // arguemtns: queryWords, index, implementation:
        runBenchmark(queryWords, indexSimple, "simple index");
        runBenchmark(queryWords, invIndHashMap, "HashMap");
        runBenchmark(queryWords, invIndTreeMap, "TreeMap");
    }

    // String implementation: is called from teh main method, therefore static
    /**
     * Here we run the experiment i itself and print a view of the results to the console.
     * The number of searches and warm up runs can be altered by changing the code.
     * @param queryWords The list of words we search for on the websites.
     * @param index The specific index we are testing the performance for.
     * @param implementation A string containing the name of the index tested.
     *                       Used when printing the results of the experiments.
     */
    public static void runBenchmark(List<String> queryWords, Index index, String implementation){

        int websitesFound = 0; // number of websites it finds
//        // Warmup:
//        int i = 100;
//        while (i > 0) {
//            for (String word : queryWords) {
//                websitesFound += index.lookup(word).size();
//            }
//            i = i - 1;
//        }
//        websitesFound = 0;

        // Actual benchmarking with timing:
        int indexNumber = 0;  // index bruges til at styre loop’et
        int iterations = 500; // hvor mange gange jeg kører eksperimentet
        long averageTime = 0;

        // search time per query word:
        // for ( int indexNumber = 0; indexNumber < iterations; indexNumber++)
        while (indexNumber < iterations) {  // while number of declared iterations is valid
            for (String word : queryWords) {
                long startTime = System.nanoTime(); // before new search begins
                websitesFound += index.lookup(word).size(); // use the result to count websites found
                long endTime = System.nanoTime();
                long totalTime = endTime - startTime;
                averageTime += totalTime;
            }

            indexNumber++;

            //long elapsedTime = System.nanoTime() - startTime;
        }

        averageTime = averageTime / iterations;
        System.out.println("Using a " + implementation + ", searching through all websites took on average "
                + (averageTime/10e3) + " milliseconds." + "\nThe search was run " + indexNumber + " times.");
        System.out.println("Number of websites found containing the search terms: " + websitesFound + "\n");
    }
}

