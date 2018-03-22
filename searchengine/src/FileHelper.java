import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * FileHelper contains all methods that help reading a database of websites from a file.
 *
 * Created by GroupU
 */

public class FileHelper {
    /**
     * Parses a file and extracts all the websites that are contained in the file.
     *
     * Each file lists a number of websites including their URL, their title,
     * and the words that appear on a website. In particular, a website starts with a line “*PAGE:” that
     * is followed by the URL of the website. The next line represents the title of the website in natural language.
     * This line is followed by a list of words that occur on the page.
     *
     * @param filename The filename of the file that we want to load. Needs to include the directory path as well.
     * @return The list of websites that contain all websites that were found in the file.
     */
    public static List<Website> parseFile(String filename) {
        // Will contain all the websites that we have found in the file
        List<Website> sites = new ArrayList<Website>();

        // We use these variables to store the url, title, and the
        // words that we find for a website in the file
        String url = null, title = null; List<String> listOfWords = null;

        // foundFirstPage is true as soon as we found the first "*PAGE:" line
        // and is used to skip any erroneous lines at the beginning of the file.
        boolean foundFirstPage = false;
        // Idea: isNextLineTitle distinguishes if the line is the title or if the line is a word
        // set it to true after reading a line starting with *PAGE:, set it to false in the next line.
        boolean isNextLineTitle = false;

        try {
            // load the file, will throw a FileNotFoundException if the
            // filename doesn't point to an existing file.
            Scanner sc = new Scanner(new File(filename), "UTF-8");
            // as long as we are not done with reading the file
            while (sc.hasNext()) {
                // get the next line from the file
                String line = sc.nextLine();

                // Check status and the content of the line to figure out if this line is
                // the url, the title, or a word.
                if (line.startsWith("*PAGE:")) {
                    // new website entry starts, so create PREVIOUS website from data gathered BEFORE reaching this "*PAGE:" entry.
                    // (if data is correct we create the (previous) website
                    if ((url != null) && (title != null) && (listOfWords != null)) {
                        sites.add(new Website(url, title, listOfWords));
                    }

                    // clear all variables to start new website entry
                    url = line.substring(6); // line starts with *PAGE:, so get substring from position 6 (after "*PAGE:" to capture the url)
                    title = null; // title not known, to reset
                    listOfWords = null; // no words are known

                    foundFirstPage = true;
                    isNextLineTitle = true;
                } else if (foundFirstPage && isNextLineTitle) {
                    // this is the title of the website
                    title = line;
                    isNextLineTitle = false; // then the subsequent lines are the words of the website
                } else if (foundFirstPage && !isNextLineTitle){
                    // if this is the first word on the website, we have to initialize listOfWords
                    if (listOfWords == null) {
                        listOfWords = new ArrayList<String>(); // initialize
                    }
                    listOfWords.add(line); // add
                }
            }
            // When we have read the whole file, we have to create the very last website manually.
            if ((url != null) && (title != null) && (listOfWords != null)) {
                sites.add(new Website(url, title, listOfWords));
            }


        } catch (FileNotFoundException e) {
            System.out.println("Couldn't load the given file");
            e.printStackTrace();
        }

        return sites;
    }
}