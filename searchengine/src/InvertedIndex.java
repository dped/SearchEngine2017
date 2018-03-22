import java.util.*;

/**
 *  Inverted Index is one of the major functionality of the search engine.
 *  It is used to indexing a list of websites, providing a particular map.
 *  which stores a mapping
 *  between a query word and a list of websites that contains the word.
 *  An inverted index provides a fast way to access websites.
 *
 *  The Map Interface makes it possible to switch between HashMap/TreeMap, assigned to the queryMap.
 *  The InvertedIndex class is an abstract superclass. Its two subclasses are: HasMapInvertedIndex and
 *  TreeMapInvertedIndex.
 *  ,
 *  Which InvertedIndex map to use (Hash or Tree) is specified inside the SearchEngine class.
 *
 *  The InvertedIndex class uses two methods, implemented via Index Interface:
 *  the build method which processes a list of website objects,
 *  and the lookup method which is a search that returns a list of websites containing the query word.
 *  @author groupU
 *  @version 2.0
 */

abstract public class InvertedIndex implements Index {

    /**
     * The build method processes a list of websites into the index data structure.
     *
     * @param sites The list of websites that should be indexed
     */
    protected Map<String, List<Website>> queryMap; ////////////////////////// protected
    int numberOfAllWebsites;
    //int numberOfMatchingWebsites;

    /**
     * The build method processes a list of websites into the index data structure.
     *
     * @param sites The list of websites that should be indexed
     */
    public void build(List<Website> sites) {
        // The first foreach loops through the collection of websites
        // The second foreach loops through all the words on one website at a time
        numberOfAllWebsites = sites.size();
        for (Website s : sites) {
            for (String word : s.getWords()) { // the two for-loops says: "For ALL words in ALL websites"

                // The if statement checks if a mapping (key-value pair) of the particular word
                // is not stored in the queryMap already

                if (!queryMap.containsKey(word)) {

                    // create a new empty ArrayList to store the List of websites that contains the query word
                    List<Website> sitesContainingTheWord = new ArrayList<>();

                    // Add the list of websites to the list: sitesContainingTheWord
                    sitesContainingTheWord.add(s);

                    /* put: Associates the specified value with the specified key in this map.
                     previously contained a mapping for the key, the old value is replaced.
                     word: is the key, a potential query word
                     sitesContainingTheWord List of websites associated to the word. */

                    queryMap.put(word, sitesContainingTheWord);
                }

                // checks if the websites that contains a query word is not in the queryMap already
                // get(word)  Returns the value to which the specified key is mapped, or null if this
                // map contains no mapping for the key that is why we check it
                if (!queryMap.get(word).contains(s)){

                    /* add(s)    Add the specified website , to the end of this list that is associated with:
                      get(word)  Returns the value to which the specified key is mapped, or null if this map
                      contains no mapping for the key.
                      @return the value to which the specified key is mapped, or null if this
                      map contains no mapping for the key  */

                    queryMap.get(word).add(s);

                }
            }
        }
    }

    /**
     * Given a query string, returns a list of all websites that contain the query.
     *
     * @param query The query
     * @return the list of websites that contains the query word.
     */
    public List<Website> lookup(String query) {

        // returns an empty list if the query word is not found in the index
        if (queryMap.get(query) == null){
        //     numberOfMatchingWebsites = 0;
            return new ArrayList<>();
        }
        // numberOfMatchingWebsites = queryMap.values().size();
        return queryMap.get(query);// return value = a List of matching Websites
    }

    @Override
    public int getNumberOfAllWebsites() {
        return numberOfAllWebsites;
    }


    @Override
    public String toString() {
        return "InvertedIndex{" +
                "queryMap=" + queryMap +
                ", numberOfAllWebsites=" + numberOfAllWebsites +
                '}';
    }
}


