package searchengine;

/**
 * Created by GroupU on 16/11/17.
 */
public interface Score {

    /* Add an interface Score to your program. It should have a method getScore that takes
    a string, a Website object, and an Index object, and returns a floating point number.*/


    public double getScore(String individualSearchTerm, Website website, Index idx);
}




