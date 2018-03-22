import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Dorte Pedersen on 21/11/17.
 */
public class WebsiteTest {
    Website website1;
    Website website2;
    Website website3;
    Website website4;
    List<String> listOfWordsW1 = new ArrayList<>();


    @Before
    public void build() {
        listOfWordsW1.add("word1");
        listOfWordsW1.add("word2");

        website1 = new Website("https://www.dr.dk", "DR", listOfWordsW1);
        //website2 = new Website("https://www.itu.dk", "ITU", listOfWordsW2);


        // website instance
        website1 = new Website("https://www.dr.dk", "DR", listOfWordsW1);

    }

    @Test
    public void getTitle() throws Exception {
        assertEquals("DR", website1.getTitle());

    }

    @Test
    public void getWords() throws Exception {

        assertEquals(listOfWordsW1, website1.getWords());
        assertEquals(2, website1.getWords().size());

    }

    @Test
    public void getUrl() throws Exception {
        assertEquals("https://www.dr.dk", website1.getUrl());

    }

    @Test
    public void containsWord() throws Exception {
        assertTrue(website1.containsWord("word1"));
        assertFalse(website1.containsWord("hello"));
    }

}