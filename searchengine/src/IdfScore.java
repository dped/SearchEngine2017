/**
 * This class calculates the IdfScore.
 *
 * Created by GroupU
 */
public class IdfScore implements Score{

    double numberOfAllWebsites = 0;
    double numberOfMatchingWebsites = 0;

    /**
     * @param singleWord A query containing all the words.
     * @param website website object
     * @param idx index received from ScoreHelper
     * @return idfScore of type Double.
     */
    @Override
    public double getScore(String singleWord, Website website, Index idx) {


        numberOfAllWebsites = idx.getNumberOfAllWebsites();
        numberOfMatchingWebsites = idx.lookup(singleWord).size();

        double idfScore = Math.log (numberOfAllWebsites / numberOfMatchingWebsites);  //log10
//        System.out.println(idfScore);

        idfScore  =  idfScore / Math.log(2);  //log2

        if (idx.lookup(singleWord).size() < 1){ // if word matches zerro websites
            idfScore = 0.0;                           // assign the score to be 0.0
        }
        return idfScore;
    }
}
