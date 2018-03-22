import java.util.*;
import java.util.stream.Collectors;

/**
 * This class answers queries to our search engine.
 * It contains all the logic behind handling/splitting complex search strings, and returning the result.
 *
 * Created by GroupU
 */
public class QueryHandler {
    /**
     * The field "idx" is the index used by the QH to find the matching website objects.
     * The field 'Score' is the score used to store the score.
     */
    private Index idx;
    private Score sc;

    /**
     * The constructor. Assigns the index passed to/through the constructor, to the field "idx".
     *
     * @param idx The index passed to the constructor. Passed by (from) the SearchEngine.
     * @param sc The score type passed to the constructor. Passed from SearchEngine.
     */
    public QueryHandler(Index idx, Score sc) { // passed from SearchEngine’s qh o
        this.idx = idx;
        this.sc = sc;
    }


    /**
     * getMachingWebsites answers queries of the type
     * "subquery1 OR subquery2 OR subquery3 ...". A "subquery"
     * has the form "word1 word2 word3 ...". A website matches
     * a subquery if all the words occur on the website. A website
     * matches the whole query, if it matches at least one subquery.
     *
     * @param fullQuery the query string
     * @return the list of websites that matches the query
     */
    public List<Website> getMatchingWebsites(String fullQuery) { // lets use line instead??
        List<Website> results = evaluateFullQuery(fullQuery.split(" OR ")); // after split by OR: subQuery { "Queen dk" }
        return results; // return to SearchEngine
    }

    /**
     * It takes a subquery and use a split to seperate each individual search term.
     * @param subQuery An array of subQueries, of type String, seperated already.
     * ["word1 word2", "word1 word3"]
     * @return List of Website objects. Each Website object matches
     * at least one subQuery from the input array.
     */
    private List<Website> evaluateFullQuery(String[] subQuery) { // actual method: "Queen dk", "pres USA", ...
        // Empty map will hold the keys (Website), and values (score) of type Double for a subQuery.
        Map<Website, Double> sites = new HashMap<>();


        // loop through the words in each subquery
        for (String str : subQuery) { //  "Queen dk"
            // Assign tempMap to hold values returned wsebsites-> scores from ScoreHelper line 108
            Map<Website, Double> tempMap = evaluateSubQuery(str.split(" "));// take one subquery at a time
            for (Website w : tempMap.keySet()) { //if w = website

                if (!sites.containsKey(w)) {
                    sites.put(w, tempMap.get(w));
                }
                else{
                    // If the website is already in the sites map,
                    if(tempMap.get(w) > sites.get(w)){ // if two subqueries exist take the highes score
                        sites.put(w, tempMap.get(w)); // update the sites map, if it holds the lowest score
                    }
                }
            }
        }
        // Return a List of websites, sorted by score
        return sites.entrySet()
                .stream()
                .sorted((x,y) -> y.getValue().compareTo(x.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     *
     * Given a string of search words, ret urns the list of websites that contains
     * each individual search word.
     **
     * @param individualSearchTerm Array of strings containing the individual words of the subquery.
     * ["word1", "word2"]
     * @return Returns a Map of websites and their associated scores.
     */
    private Map<Website, Double> evaluateSubQuery(String[] individualSearchTerm) { // "queen", "dk"
        Set<Website> setOfSites = new HashSet<>();                  // faster than ArrayList

        for (int i = 0; i < individualSearchTerm.length; i++) {

            // add all websites that matches the first string inside the individualSearchTerm
            if (i == 0) {
                setOfSites.addAll(idx.lookup(individualSearchTerm[i])); // method call to the index
            }
            else {
                // retain only the matching sites that matches both the first string
                // and the following string inside the individualSearchTerm
                setOfSites.retainAll(idx.lookup(individualSearchTerm[i]));
            }
        }
        // rankWebsites
        // Returns a Map of websites and their associated scores, received from ScoreHelper.
        // parameters: sites, individualSearchTerms, index, and type of score.

        return ScoreHelper.rankWebsites(setOfSites, individualSearchTerm, idx, sc);
    }
}