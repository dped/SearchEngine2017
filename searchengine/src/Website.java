import java.util.List;

/**
 * A website is the basic entity of the search engine. It has a url, a title, and a list of words.
 *
 * @author Martin Aumüller
 */

public class Website {

    /**
     * the website's title
     */
    private String title;

    /**
     * the website's url
     */
    private String url;

    /**
     * a list of words storing the words on the website
     */
    private List<String> words;

    /**
     * Constructor
     * Creates a {@code Website} object from a url, a title, and a list of words
     * that are contained on the website.
     *
     * @param url the website's url
     * @param title the website's title
     * @param words the website's list of words
     */
    public Website(String url, String title, List<String> words) {
        this.url = url;
        this.title = title;
        this.words = words;
    }

    /**
     * Returns the website's title.
     *
     * @return the website's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Added a "getWords()" for assignment 3.
     * @return
     */
    public List<String> getWords() {
        return words;
    }

    /**
     * Returns the website's url.
     *
     * @return the website's url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Searches for a query word in the list ("words") of words on the website.
     * @param word The string containing the query word. Passed to the
     *             method by the searchEngine class.
     * @return Returns true if the query string (word) is found on the website.
     */
    public Boolean containsWord(String word) {
        return words.contains(word);

    }

    @Override
    public String toString() {
        return "Website{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", words=" + words +
                '}';
    }
}

