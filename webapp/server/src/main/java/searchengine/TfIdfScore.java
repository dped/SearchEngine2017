package searchengine;

/**
 * This class calculates
 * Created by GroupU
 */
public class TfIdfScore implements Score  {

    private TfScore tfscore = new TfScore();
    private IdfScore idfScore = new IdfScore();

    //     public double getScore(String fullQuery, Website website, Index idx) {

    @Override
    public double getScore(String singleWord, Website website, Index idx) {

        double tf = tfscore.getScore(singleWord, website, idx);
        double idf = idfScore.getScore(singleWord, website, idx);

        return tf * idf;
    }
}
