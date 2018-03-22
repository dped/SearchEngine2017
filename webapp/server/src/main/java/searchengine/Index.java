package searchengine;

import java.util.List;

/**
 * The index data structure provides a way to build an index from
 * a list of websites. It allows to lookuo the websites that contain a query word.
 */
public interface Index {


    /**
     * The build method processes a list of websites into the index data structure.
     * @param sites The list of websites that should be indexed.
     */
    public void build(List<Website> sites);


    /**
     * Given query, returns a list of websites with query in it.
     * @param query The query string
     * @return the list of websites containing query.
     */
    public List<Website> lookup(String query);

    /**
     * This method is used to calculate the idf-score.
     * @return The size of the database. Ie. how many websites there are in the map.
     */
    int getNumberOfAllWebsites();

}
