package searchengine;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * The main class of our search engine program.
 *
 * @author Martin Aumüller
 * @author Leonid Rusnac
 * @author Modified by GroupU
 */
@Configuration
@EnableAutoConfiguration
@Path("/")
public class SearchEngine extends ResourceConfig {
    private static QueryHandler qh;

    public SearchEngine() {
        packages("searchengine");
    }

    /**
     * The main method of our search engine program.
     * Expects exactly one argument being provided. This
     * argument is the filename of the file containing the
     * websites.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the SearchEngine!");

        if (args.length != 1) {
            System.out.println("Error: Filename is missing");
            return;
        }

        // Index idx = new SimpleIndex();
        Index idx = new HashMapInvertedIndex(); // assign chosen data structure
        qh = new QueryHandler(idx, new TfIdfScore());  // swap between different scores


        long t1 = System.nanoTime();
        List<Website> sites = FileHelper.parseFile(args[0]);
        idx.build(sites);

        long t2 = System.nanoTime();

        System.out.println("Processing the data set and building the index took " +
                (t2 - t1) / 10e6 + " milliseconds.");
        // run the search engine
        SpringApplication.run(SearchEngine.class);
    }

    /**
     * This methods handles requests to GET requests at search.
     * It assumes that a GET request of the form "search?query=word" is made.
     *
     * @param response Http response object
     * @param query the query string
     * @return the list of websites matching the query
     */

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("search")
    public List<Website> search(@Context HttpServletResponse response, @QueryParam("query") String query) {
        // Set crossdomain access. Otherwise your browser will complain that it does not want
        // to load code from a different location.
        response.setHeader("Access-Control-Allow-Origin", "*");


        if (query == null) {
            return new ArrayList<Website>();
        }

        String line = query;



        System.out.println("Handling request for query word \"" + query + "\"");
        long ta = System.nanoTime(); // timing how the time the search a query takes
        List<Website> matchingSites =  new ArrayList<>();  //qh.getMatchingWebsites(line);
        matchingSites = qh.getMatchingWebsites(line);

        if (matchingSites.isEmpty()) {
            long tb = System.nanoTime();
            System.out.println("Search took " + (tb - ta) / 10e3 + " microseconds");
            System.out.println("No website contains the query word.");
        } else {
            System.out.println("Found " + matchingSites.size() + " websites.");

            long tb = System.nanoTime();
            System.out.println("Search took " + (tb - ta) / 10e3 + " microseconds");
            System.out.println("Query is found on " + matchingSites.size() + " websites:");
            for (Website w : matchingSites) {
                System.out.println("\t'"+ w.getUrl() + "'");
            }
        }

        return matchingSites;

    }
}
