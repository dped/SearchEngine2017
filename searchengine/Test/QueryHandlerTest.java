import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class QueryHandlerTest {

    private QueryHandler qh = null;

    @Before
    public void setUp() throws Exception {

        Index idx = new HashMapInvertedIndex();
        Score sc = new TfIdfScore();

        List<Website> sites = new ArrayList<>();
        sites.add(new Website("1.com", "example1", Arrays.asList("word1", "word2")));
        sites.add(new Website("2.com", "example2", Arrays.asList("word2", "word3")));
        sites.add(new Website("3.com", "example3", Arrays.asList("word3", "word4")));
        sites.add(new Website("4.com", "example4", Arrays.asList("word2", "word5")));
        sites.add(new Website("5.com", "example5", Arrays.asList("word5", "word6", "word7")));
        sites.add(new Website("6.com", "example6", Arrays.asList("word10", "word11")));
        sites.add(new Website("7.com", "example7", Arrays.asList("word11", "word10")));

        idx.build(sites);
        qh = new QueryHandler(idx, sc);
    }

    @Test
    public void testSingleWord(){ //in the screencast the test method has no access modifier. Here I'm told this HAS to be public. Why?
        assertEquals("example1", qh.getMatchingWebsites("word1").get(0).getTitle()); // 1 - The title of the website object returned is indeed the one we expect.
        assertEquals(1, qh.getMatchingWebsites("word1").size()); // 2 - The word appears on only one website object.
        assertEquals(3, qh.getMatchingWebsites("word2").size()); // 3 - The word appears on more than one website object.
        assertEquals(0, qh.getMatchingWebsites("word8").size()); // 4 - The word appears on NO website objects.

    }

    @Test
    public void testMultipleWords(){
        assertEquals(1, qh.getMatchingWebsites("word1 word2").size()); // 1 - The words are contained on one site only.
        assertEquals(2, qh.getMatchingWebsites("word11 word10").size()); // 2 - The words are contained on more than one site.
        assertEquals(0, qh.getMatchingWebsites("word2 word8").size()); // 3 - Only one of the words is found on a  website
        assertEquals(0, qh.getMatchingWebsites("word2 word4").size()); // 4 - Each word is found on a website, just not on the same
        assertEquals(0, qh.getMatchingWebsites("word8 word9").size()); // 5 - None of the words are found on a website.
    }

    @Test
    public void testORQueries(){
        assertEquals(1, qh.getMatchingWebsites("word6 OR word7").size()); // 1 - The words are contained on only one site.
        assertEquals(4, qh.getMatchingWebsites("word2 OR word3").size()); // 2 - The words are contained on multiple sites.
        assertEquals(1, qh.getMatchingWebsites("word1 OR word8").size()); // 3 - Only one of the words is found on a website.
        assertEquals(3, qh.getMatchingWebsites("word2 OR word8").size()); // 4 - Only one of the words is found on multiple websites.
        assertEquals(2, qh.getMatchingWebsites("word1 OR word4").size()); // 5 - Each word is found on a website, just not on the same
        assertEquals(0, qh.getMatchingWebsites("word8 OR word9").size()); // 6 - None of the words are found on a website.

    }

    @Test
    public void testLongerORQueries(){
        assertEquals(2, qh.getMatchingWebsites("word1 word2 OR word3 word4").size()); // 1 - All words from all subqueries are found (on the same website).
        assertEquals(1, qh.getMatchingWebsites("word1 word2 OR word3 word8").size()); // 2 - All words from one subquery are found (on the same website) but some words from another subquery are found.
        assertEquals(0, qh.getMatchingWebsites("word2 word6 OR word1 word4").size()); // 3 - Only some of the words from each subquery are found (on the same website).
        assertEquals(0, qh.getMatchingWebsites("word2 word6 OR word8 word9").size()); // 4 - Only some words from one of the subqueries are found.
        assertEquals(0, qh.getMatchingWebsites("Bjørn Martin OR Dorte Troels").size()); // 5 - NO words from any of the subqueries are found ANYWHERE.
        assertEquals(1, qh.getMatchingWebsites("Bjørn Martin OR Dorte Troels OR word1 word2").size()); // 6 - Test that the program understands searchstrings with several ‘OR’s.
        assertEquals(1, qh.getMatchingWebsites("Bjørn Martin OR ORBIT FLOOR OR word1 word2").size()); // 7 - Tests for words containing ‘or’, such as ‘floor’ & ‘orbit’.

    }
// test for problematic input
// test that the program only counts a website ONCE when a website contains the search-term several times.
    @Test
    public void testCornerCases(){
//        Does the code remove duplicates?
        assertEquals(1, qh.getMatchingWebsites("word1 word1").size()); // 1 - searching for multiple words without ‘OR’.
        assertEquals(1, qh.getMatchingWebsites("word1 OR word1").size()); // 2 - searching for single word subqueries separated ‘OR’.
        assertEquals(1, qh.getMatchingWebsites("word1 word2 OR word2 word1").size()); // 3 - searching for subqueries of multiple words, separated ‘OR’.


    }

}