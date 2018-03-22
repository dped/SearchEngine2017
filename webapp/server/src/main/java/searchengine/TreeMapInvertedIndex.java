package searchengine;

import java.util.TreeMap;

/**
 * Created by GroupU on 07/12/17
 * v 0.1
 */

public class TreeMapInvertedIndex extends InvertedIndex
{
    public TreeMapInvertedIndex()
    {
        super.queryMap = new TreeMap();
    }
}




