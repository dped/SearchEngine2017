package searchengine;

import java.util.List;

/**
 * This class TfScore implements Score Interface.
 * It counts the number of times the query word exists on a website,
 * which is the TF-Score.
 *
 * Created by GroupU
 */
public class TfScore implements Score {


    /**
     *
     * @param individualSearchTerm
     * @param website
     * @param idx
     * @return wordcounter which is the TF-Score of type Double:
     * term frequency is the number of times a word occurs on a website
     */

        /*
    * In the end this class implements the TF-Score
    * TF: the number of times the word exists on one website */
    @Override
    public double getScore(String individualSearchTerm, Website website, Index idx) {

        double wordCounter = 0;


        List<String> allWordsPerWebsite = website.getWords(); //website.getWords = samtlige ord på et site


        for (String word : allWordsPerWebsite)
        {
            if (individualSearchTerm.equals(word)) // if searchterm = current word
            {
                wordCounter++; // counts number of time a word exists on a site
            }
        }

        return wordCounter;  // is returned to ScoreHelper class

    }
}
