package searchengine;

import java.util.ArrayList;
import java.util.List;

/**
 * SimpleIndex implements the Index interface.
 * Creates a list of all websites, goes through all websites,
 * and returns a list of websites that contain the query word.
 */

public class SimpleIndex implements Index {

    /**
     * Field "sites" stores the list of all website objects created by the "build()" method.
     */
    private List<Website> sites;

    /**
     * Preprocesses a list of website objects.
     * The "build()" method creates the list of all website objects from the database file.
     * Updates the value of the field "sites" to hold all websites.
     */
    @Override
    public void build(List<Website> sites) { // receive list of sites from the SearchEngine
        this.sites = sites;
    }

    /**
     * Takes a string (line) containing the query word as argument.
     * Checks all websites and returns a list of website objects matching a query
     *
     * @param line The query word received from the "main()" method.
     * @return The list "matchingWebsites" of all sites containing the query word.
     */
    @Override
    public List<Website> lookup(String line) {
        List<Website> matchingWebsites = new ArrayList<>();
        // Go through all websites and check if word is present
        for (Website w : sites) {
            if(w.containsWord(line)){
                matchingWebsites.add(w);
            }
        }
        return matchingWebsites;
    }

    @Override
    public int getNumberOfAllWebsites() {
        return 0;
    }


    @Override
    public String toString() {
        return "SimpleIndex{" +
                "sites=" + sites +
                '}';
    }
}
