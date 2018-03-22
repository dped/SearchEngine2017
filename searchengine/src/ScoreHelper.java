import java.util.*;

/**
 * Created by groupU  on 16/11/17.
 * v 0.3
 */


public class ScoreHelper
{
    private static double score;

    /**
     * The method, rankWebsites, is called in the QueryHandler
     * @param setOfSites get the sites in evaluateSubQuery from the QueryHandler
     * @param individualSearchTerms Individual searchTerm fx. ["queen", "denmark"] associated with the sites
     * @param idx Index
     * @param score Score of type double in order to do the score
     * @return mapToReturn Returns a Map of key: Websites associated with a value: the score of type double.
     */
    public static Map<Website, Double> rankWebsites(Set<Website> setOfSites, String[] individualSearchTerms, Index idx, Score score) {
        Map<Website,Double> mapToReturn = new HashMap<>();
        // loop over every mathcing website
        for (Website website : setOfSites)
        {
            double scoreOfWebsite = 0;

            // for every website, we loop over every individualSearchTerm of type String fx: 'denmark'
            for (String singleWord: individualSearchTerms)
            {
                // add score of all words in one subquery at a time
                scoreOfWebsite += score.getScore(singleWord,website, idx); // add queens score: 12 + dk score: 4 = 16
            }
            // put it on the map: websites associated to the score of type double
            mapToReturn.put(website, scoreOfWebsite);
        }
        // for every website you can return the map, mapToReturn.
        return mapToReturn; // is passed to queryhandler
    }

}