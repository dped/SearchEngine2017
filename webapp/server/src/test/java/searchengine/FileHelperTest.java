package searchengine;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileHelperTest {

    @Test
    public void parseFile()  {
        List<Website> sites = FileHelper.parseFile("test-resources/test.txt");
        assertEquals(5, sites.size()); // test that the List of websites has the expected number of elements.
        // tests that the List contains the elements we expect, and at the expected indices.
        assertEquals("Title5", sites.get(4).getTitle());
        assertEquals("Title1", sites.get(0).getTitle());
        assertEquals("website1", sites.get(0).getUrl());
        assertEquals("website4", sites.get(3).getUrl());
    }

    @Test
    public void parseFileWithErrors()  {
        List<Website> sites = FileHelper.parseFile("test-resources/test_errors.txt");
        // 1 - Tests that the program discards entries in the input file with invalid/incomplete website info.
        assertEquals(3, sites.size());
        // 2 - Tests that the program has in fact discarded the (invalid) entries for the first two website objects,
        //     and that the first object in the list is actually website three.
        assertEquals("Title3", sites.get(0).getTitle());
    }

}