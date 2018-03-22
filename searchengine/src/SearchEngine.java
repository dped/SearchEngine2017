import java.util.*;

/**
 * Created by Martin, and GroupU
 */

public class SearchEngine {

    /**
     * This is the entry to the search engine.
     *
     * Sends the file to the FileHelper class, which creates a list of websites (sites).
     *
     * The Scanner takes the user input from the command line, ie. the query words.
     *
     * An instance of the SimpleIndex (idx) is created, and the list of websites (sites)
     * is sent to its "build()" method.
     *
     *  The program then takes an argument from the user - the query word - and sends that
     * string to the "lookup()" method of idx.
     *
     * The "lookup()" method of idx returns a list of websites containing the query word.
     *
     * The appropriate message is then printed; ie: "Query is found on..."  or
     * "No website contains the query wor½d"
     *
     * @param args The filename- and path for the database file containing the websites.
     */
    public static void main(String[] args) {

        System.out.println("Welcome to the SearchEngine!");
        // first we check that we have a database file containing the websites.
        // If it is 0 we don't have a file, if it is more than one there's too many arguments/filenames
        if (args.length != 1) {
            System.out.println("Error: Filename is missing");
            return;
        }

        Scanner sc = new Scanner(System.in);  // "SystemSystem.in": 'listens ' for input from the command line
        long t1 = System.nanoTime(); // measuring time spent to build the index
        List<Website> sites = FileHelper.parseFile(args[0]); // parses the file from the FileHelper first argument (index = 0)
        Index idx = new HashMapInvertedIndex(); ///////////////////////////////////
        idx.build(sites); // pass a list and call build on the instance of the index class
        long t2 =  System.nanoTime();
        System.out.println("Processing the input data and building the index took " + (t2 - t1) / 10e6 + " milliseconds");

        // We use TfIdfScore
        QueryHandler qh = new QueryHandler(idx, new TfIdfScore());  // swap between different scores
        System.out.println("Please provide a query word");

        while (sc.hasNext()) { // happens when user press return
            String line = sc.nextLine(); //the "nextLine()" method of the Scanner class simply returns a string with what was input by the user
            long ta = System.nanoTime(); // timing how the time the search a query takes
            List<Website> matchingSites = qh.getMatchingWebsites(line);

            if (matchingSites.isEmpty()) {
                long tb = System.nanoTime();
                System.out.println("Search took " + (tb - ta) / 10e3 + " microseconds");
                System.out.println("No website contains the query word.");
            } else {
                long tb = System.nanoTime();
                System.out.println("Search took " + (tb - ta) / 10e3 + " microseconds");
                System.out.println("Query is found on " + matchingSites.size() + " websites:");
                for (Website w : matchingSites) {
                    System.out.println("\t'"+ w.getUrl() + "'");
                }
            }
            System.out.println("Please provide the next query word");

        }
    }
}
