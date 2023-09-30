
/**
 * This class is used to load information about the number of facilities in each
 * borough, such as amount of restaurants, pubs etc.
 *
 * @authors Mayurapiriyan Ramasamy (19017309), Muhammad Ismail Kamdar(19009749)
 *          Muhammad Alyan Qureshi(1928748).
 */
public class LondonStatistic
{
    // instance variables - replace the example below with your own
    private String borough;
    private int resturants;
    private int pubs;
    private int underground;
    private double crimeRate;
    private int toiletPaperProbability;
    private int parks;
    
    /**
     * Constructor for objects of class LondonStatistic.
     */
    public LondonStatistic(String borough, int resturants, int pubs, int underground
    , double crimeRate, int toiletPaperProbability, int parks)
    {
        this.borough = borough;
        this.resturants = resturants;
        this.pubs = pubs;
        this.underground = underground;
        this.crimeRate = crimeRate;
        this.toiletPaperProbability = toiletPaperProbability;
        this.parks = parks;

    }

    /**
     * @return name of borough
     */
    public String getBorough(){
        return borough;
    }

    /**
     * @return number of resturants
     */
    public int getResturants(){
        return resturants;
    }

    /**
     * @return number of pubs
     */
    public int getPubs(){
        return pubs;
    }

    /**
     * @return number of undergrounds
     */
    public int getUnderground(){
        return underground;
    }

    /**
     * @return crimes per 1000 people
     */
    public double getCrimeRate(){
        return crimeRate;
    }
    
    /**
     * @return the propbability of having toilet paper
     */
    public int getToiletPaperProbability(){
        return  toiletPaperProbability;
    }

    /**
     * @return the the number of parks
     */
    public int getParks(){
        return parks;
    }
}

