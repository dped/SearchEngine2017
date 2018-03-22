package searchengine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreHelperTest {


    private Website w1;
    private Website w2;
    private Website w3;
    private Website w4;
    private Website w5;

    private Index idx = new HashMapInvertedIndex();


    @BeforeEach
    void setUp() {
        List<Website> sites = new ArrayList<>();

        w1 = new Website("1.com", "title1", Arrays.asList("word1", "word2", "word1", "word3", "word1", "word4",
                "word5", "word6", "word7", "word3", "word8", "word8",
                "word9", "word20", "word20","word30"));
        w2 = new Website("2.com", "title2", Arrays.asList("word1", "word10", "word8", "word11", "word1", "word4",
                "word12", "word13", "word10", "word12", "word8", "word11",
                "word9", "word20", "word20", "word20", "word30"));
        w3 = new Website("3.com", "title3", Arrays.asList("word8", "word2", "word14", "word15", "word16", "word17",
                "word14", "word8", "word18", "word8", "word9", "word20"));
        w4 = new Website("4.com", "title4", Arrays.asList("word19", "word20", "word8", "word21", "word12", "word21",
                "word20", "word19", "word8", "word1", "word20", "word20", "word30"));
        w5 = new Website("5.com", "title5", Arrays.asList("word22", "word1", "word23", "word1", "word24", "word23", "word8"));

        sites.add(w1);
        sites.add(w2);
        sites.add(w3);
        sites.add(w4);
        sites.add(w5);
        idx.build(sites);
    }

    @Test
    public void tfScoreTest()  {
        TfScore tf = new TfScore();
        QueryHandler qh = new QueryHandler(idx, tf);
        // Testing the sorting: qh.getMatchingWebsites("word20") returns a List.
        // A List is an ordered data structure, so the order of elements matters by design: "...two lists are defined to be equal if they contain the same elements in the same order." - Java API
        // Therefore
        List<Website> expectedOutcome = new ArrayList<>();
        expectedOutcome.add(w4);
        expectedOutcome.add(w2);
        expectedOutcome.add(w1);
        expectedOutcome.add(w3);

        List<Website> expectedOutcomeMultipleWords = new ArrayList<>();
        expectedOutcomeMultipleWords.add(w2);
        expectedOutcomeMultipleWords.add(w1);


        assertEquals(1.00, tf.getScore("word2", w1, idx), 0.001); // word found once on the site.
        assertEquals(3.00, tf.getScore("word1", w1, idx), 0.001); // word found more than once on the site.
        assertEquals(0.00, tf.getScore("word10", w1, idx), 0.001); // word NOT found on the site.
        assertEquals(4, idx.lookup("word1").size()); // SORTING: Here we test that a search (this time on the whole list of websites) returns the expected number of websites
        assertEquals(expectedOutcome, qh.getMatchingWebsites("word20")); // SORTING: Here we test that a search returns the expected number of websites IN THE EXPECTED ORDER.
        assertEquals(expectedOutcomeMultipleWords, qh.getMatchingWebsites("word20 word30 word9")); // SORTING: multiple words, please see our report for details.

    }

    @Test
    public void idfScoreTest()  {
        IdfScore idf = new IdfScore();


        assertEquals(2.321928, idf.getScore("word22", w5, idx), 0.001); // word found ONCE on only ONE website.
        assertEquals(2.321928, idf.getScore("word23", w5, idx), 0.001); // word found SEVERAL times on only ONE website.
        assertEquals(0.736965, idf.getScore("word9", w3, idx), 0.001); // word found ONCE  on several websites.
        assertEquals(0.321928, idf.getScore("word1", w1, idx), 0.001); // word found SEVERAL times on ALL BUT ONE website.
        assertEquals(1.321928, idf.getScore("word4", w2, idx), 0.001); // word found on two websites.
        assertEquals(0.000000, idf.getScore("bjorn", w2, idx), 0.001); // word NOT found on ANY websites.

    }

    @Test
    public void tfIdfScoreTest()  {
        TfIdfScore tfidf = new TfIdfScore();

        QueryHandler qh = new QueryHandler(idx, tfidf);
        List<Website> expectedOutcome = new ArrayList<>();
        expectedOutcome.add(w4);
        expectedOutcome.add(w2);
        expectedOutcome.add(w1);
        expectedOutcome.add(w3);

        List<Website> expectedOutcomeMultipleWords = new ArrayList<>();

        expectedOutcomeMultipleWords.add(w2);
        expectedOutcomeMultipleWords.add(w1);

        assertEquals(2.321928, tfidf.getScore("word22", w5, idx), 0.001); // word found ONCE on ONE website
        assertEquals(4.643856, tfidf.getScore("word23", w5, idx), 0.001); // word found TWICE times on ONE website
        assertEquals(0.965784, tfidf.getScore("word1", w1, idx), 0.001); // word found SEVERAL times on ONE website
        assertEquals(0.000000, tfidf.getScore("bjorn", w1, idx), 0.001); // word NOT on ANY websites
        assertEquals(expectedOutcome, qh.getMatchingWebsites("word20")); // SORTING: Here we test that a search returns the expected number of websites IN THE EXPECTED ORDER.
        assertEquals(expectedOutcomeMultipleWords, qh.getMatchingWebsites("word20 word30 word9"));
    }
}