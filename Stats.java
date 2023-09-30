import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * This class is used to calculate and return various useful information about
 * the boroughs.
 *
 * @authors Mayurapiriyan Ramasamy (19017309), Muhammad Ismail Kamdar(19009749)
 *          Muhammad Alyan Qureshi(1928748).
 */
public class Stats {
    // instance variables - replace the example below with your own
    private List<AirbnbListing> airbnbList = new ArrayList();
    private List<LondonStatistic> londonStatisticsList = new ArrayList<>();
    private List<PanelStatistic> panelLondonStats = new ArrayList<>();
    private List<String> boroughList = new ArrayList<>();
    private HashMap<String, List<PanelStatistic>> panelStatisticMap;
    private AirbnbDataLoader dataLoader = new AirbnbDataLoader();
    private HashMap<String, ArrayList<AirbnbListing>> propertyMap;
    private HashMap<String, LondonStatistic> boroughMap;
    private int totalReviews = 0;
    private double averageReviews = 0;
    private int availableProperties = 0;
    private int entireHomes = 0;
    private String expensiveBorough = "";
    private String affordableBorough = "";
    
    /**
     * Creates an object that holds stats on every borough
     */
    public Stats() {
        airbnbList = dataLoader.load();
        // list of data from additional dataset London Statistics
        londonStatisticsList = dataLoader.loadNewData();
        propertyMap =  new HashMap<>();
        boroughMap =  new HashMap<>();
        panelStatisticMap = new HashMap<>();
        createStats();
        calculateAverageReviews();
        calculateMostExpensiveBorough();
        calculateMostAffordableBorough();
        createPanelStatisticsMap();
        createLondonStats();
    }

    /**
     * Goes through the airbnbList and creates a dictionary HashMap for properties
     * if a neighborhood key does not exist in the hashmap then a new entry is created along with a new list
     * corresponding airbnbListings are added into suitable lists
     */
    private void createStats(){
        int x = 0;
        while (x < airbnbList.size()) {
            AirbnbListing property = airbnbList.get(x);
            String neighborhood = property.getNeighbourhood();
            if (!propertyMap.containsKey(neighborhood)) {
                propertyMap.put(neighborhood, new ArrayList<>());
                boroughList.add(neighborhood);
            } else {
                propertyMap.get(neighborhood).add(property);
            }
            if (property.getAvailability365() > 0) {
                availableProperties++;
            }
            if (!property.getRoom_type().equals("Private room")) {
                entireHomes++;
            }
            totalReviews += property.getNumberOfReviews();

            x++;
        }

        x = 0;
        while (x < londonStatisticsList.size()){
            LondonStatistic londonStatistic = londonStatisticsList.get(x);
            String boroughName = londonStatistic.getBorough();
            if (! boroughMap.containsKey(boroughName)){
                boroughMap.put(boroughName, londonStatistic);
            }
            x++;
        }
    }

    /**
     * generates stats for list of properties passed as parameter
     * @param boroughProperties
     * @param boroughName
     * creates a list of PanelStatistics and add stats.
     * @return panelStatList
     */
    private ArrayList<PanelStatistic> createBoroughStats(List<AirbnbListing> boroughProperties, String boroughName){
        int x = 0;
        int propertiesAvailable = 0;
        int completeHomes = 0;
        int avgReviews = 0;
        ArrayList<PanelStatistic> panelStatList = new ArrayList<>();
        while ( x < boroughProperties.size()){
            AirbnbListing property =  boroughProperties.get(x);
            if (property.getAvailability365() > 0){
                propertiesAvailable++;
            }
            if (! property.getRoom_type().equals("Private room")){
                completeHomes++;
            }
            avgReviews += property.getNumberOfReviews();
            x++;
        }
        avgReviews = avgReviews/boroughProperties.size();
        panelStatList.add(new PanelStatistic("Properties Available","Properties available in " + boroughName + " are :", String.valueOf(propertiesAvailable)));
        panelStatList.add(new PanelStatistic("Entire Homes Available","Entire Homes or Apartments available n \n" + boroughName + " are :", String.valueOf(completeHomes)));
        panelStatList.add(new PanelStatistic("Average Reviews","Average number of reviews for properties\nin " + boroughName + " is :", String.valueOf(avgReviews)));
        panelStatList.add(new PanelStatistic("Most Expensive Property","Most expensive property in " + boroughName + " is :", getMostExpensiveProperty(boroughProperties)));
        panelStatList.add(new PanelStatistic("Most Affordable Property","Most affordable property in " + boroughName + " is :", getMostAffordableProperty(boroughProperties)));
        panelStatList.add(new PanelStatistic("Crime rate", "The crime rate per 1000 people,\n in " + boroughName + " is :",String.valueOf(boroughMap.get(boroughName).getCrimeRate())));
        panelStatList.add(new PanelStatistic("Restaurants", "The number of restaurants in " + boroughName + " is :", String.valueOf(boroughMap.get(boroughName).getResturants())));
        panelStatList.add(new PanelStatistic("Pubs","The number of Pubs in " + boroughName + " is :", String.valueOf(boroughMap.get(boroughName).getPubs())));
        panelStatList.add(new PanelStatistic("Toilet Paper", "Probability of toilet paper availability\nduring a pandemic in " + boroughName + " is :", String.valueOf(boroughMap.get(boroughName).getToiletPaperProbability()) + "%"));
        return panelStatList;
    }

    /**
     * creates a list of PanelStatistics for London as a whole suing methods described below
     */
    private void createLondonStats(){
        panelLondonStats.add(new PanelStatistic("Properties Available","Properties available in London are :",String.valueOf(getAvailableProperties())));
        panelLondonStats.add(new PanelStatistic("Entire Homes Available","Entire Homes or Apartments available\nin London are :",String.valueOf(getEntireHomes())));
        panelLondonStats.add(new PanelStatistic("Average Reviews","Average number of reviews for properties in London is :",String.valueOf(getAverageReviews())));
        panelLondonStats.add(new PanelStatistic("Most Expensive Property","Most expensive borough in London is :",getMostExpensiveBorough()));
        panelLondonStats.add(new PanelStatistic("Most Affordable Borough", "Most affordable borough in London is :", getMostAffordableBorough()));
        panelLondonStats.add(new PanelStatistic("Most Underground", "The most convenient borough with\nhighest number of TFL stations is :", getMostUndergroundBorough()));
        panelLondonStats.add(new PanelStatistic("Toilet Paper Availability", "Borough with the highest probability\nof toilet paper availability during a pandemic is: ", getHighestToiletPaperAvailability()));
        panelLondonStats.add(new PanelStatistic("Most Parks", "The greenest borough in London\nwith most number of parks is :", getMostParksBorough()));
        panelLondonStats.add(new PanelStatistic("Highest Crime Rate", "The borough with the highest level of\ncrime rate, per 1000 people is :", getUnsafeBorough()));
    }

    /**
     *
     * @return panelLondonStats
     */
    public List<PanelStatistic> getPanelLondonStats(){
        return panelLondonStats;
    }

    /**
     * calculates the most expensive borough by going through the the list of properties for each
     * borough and then calculating avg price per borough and assigning the highest to mostExoensive and expensiveBorough
     */
    private void calculateMostExpensiveBorough(){
        List<AirbnbListing> borough;
        int mostExpensive = 0;
        for (String s : boroughList){
            borough = propertyMap.get(s);
            int x = 0;
            int totalCost = 0;
            while (x < borough.size()){
                totalCost += borough.get(x).getPrice() * borough.get(x).getMinimumNights();
                x++;
            }
            totalCost = totalCost/borough.size();
            if (totalCost > mostExpensive){
                mostExpensive = totalCost;
                expensiveBorough = s;
            }
        }
    }
    /**
     * calculates the most expensive borough by going through the the list of properties for each
     * borough and then calculating avg price per borough and assigning the lowest to mostAffordable and affordableBorough
     */
    private void calculateMostAffordableBorough(){
        List<AirbnbListing> borough;
        int mostAffordable = 100000;
        for (String s : boroughList){
            borough = propertyMap.get(s);
            int x = 0;
            int totalCost = 0;
            while (x < borough.size()){
                totalCost += borough.get(x).getPrice() * borough.get(x).getMinimumNights();
                x++;
            }
            totalCost = totalCost/borough.size();
            if (totalCost < mostAffordable){
                mostAffordable = totalCost;
                affordableBorough = s;
            }
        }
    }

    /**
     *
     * @param propertyList goes through the list and calculates most expensive property form the given list
     * @return name of expensive property
     */
    private String getMostExpensiveProperty(List<AirbnbListing> propertyList){
        int x = 0;
        int mostExpensive = 0;
        String expensiveProperty = "";
        while (x < propertyList.size()){
            int roomCost = propertyList.get(x).getMinimumNights() *  propertyList.get(x).getPrice();
            if (roomCost > mostExpensive){
                mostExpensive = roomCost;
                expensiveProperty = propertyList.get(x).getName();
            }
            x++;
        }
        return expensiveProperty;
    }

    /**
     * @param propertyList goes through the list and calculates most affordable property form the given list
     * @return name of affordable property
     */
    private String getMostAffordableProperty(List<AirbnbListing> propertyList){
        int x = 0;
        int mostAffordable = 100000;
        String cheapProperty = "";
        while (x < propertyList.size()){
            int roomCost = propertyList.get(x).getMinimumNights() *  propertyList.get(x).getPrice();
            if (roomCost < mostAffordable){
                mostAffordable = roomCost;
                cheapProperty = propertyList.get(x).getName();
            }
            x++;
        }
        return cheapProperty;
    }

    /**
     * goes through lists of properties of all boroughs and finds the one with the highest number of underground stations
     * @return String Name of borough with most underground stations
     */
    public String getMostUndergroundBorough(){
        String mostUndergroundBorough = "";
        int x = 0;
        int mostUnderground = 0;
        while (x < londonStatisticsList.size()) {
            LondonStatistic stat = londonStatisticsList.get(x);
            if (stat.getUnderground() > mostUnderground) {
                mostUnderground = stat.getUnderground();
                mostUndergroundBorough = stat.getBorough();
            }
            x++;
        }
        return (mostUndergroundBorough + " : " + mostUnderground + " stations");
    }
    /**
     * goes through lists of properties of all boroughs and finds the one with the highest number of resturants
     * @return String Name of borough with most resturants
     */
    public String getMostResturantBorough(){
        String mostResturantBorough = "";
        int x = 0;
        int mostResturants = 0;
        while (x < londonStatisticsList.size()) {
            LondonStatistic stat = londonStatisticsList.get(x);
            if (stat.getResturants() > mostResturants) {
                mostResturants = stat.getResturants();
                mostResturantBorough = stat.getBorough();
            }
            x++;
        }
        return mostResturantBorough;
    }
    /**
     * goes through lists of properties of all boroughs and finds the one with the highest number of parks
     * @return String Name of borough with most parks
     */
    public String getMostParksBorough(){
        String mostParkBorough = "";
        int x = 0;
        int mostParks = 0;
        while (x < londonStatisticsList.size()) {
            LondonStatistic stat = londonStatisticsList.get(x);
            if (stat.getParks() > mostParks) {
                mostParks = stat.getParks();
                mostParkBorough = stat.getBorough();
            }
            x++;
        }
        return (mostParkBorough + " : " + mostParks + " parks");
    }
    /**
     * goes through lists of properties of all boroughs and finds the one with the
     * highest probability of toilet paper availability
     * @return String Name of borough with highest probability of toilet paper availability
     */
    public String getHighestToiletPaperAvailability(){
        String mostToiletPaperBorough = "";
        int x = 0;
        int mostToiletPapers = 0;
        while (x < londonStatisticsList.size()) {
            LondonStatistic stat = londonStatisticsList.get(x);
            if (stat.getToiletPaperProbability() > mostToiletPapers) {
                mostToiletPapers = stat.getToiletPaperProbability();
                mostToiletPaperBorough = stat.getBorough();
            }
            x++;
        }
        return (mostToiletPaperBorough +" : " + mostToiletPapers + "%");
    }

    /**
     * goes through lists of properties of all boroughs and finds the one with the
     * highest crime rate
     * @return String Name of borough with highest crime rate
     */
    public String getUnsafeBorough(){
        String mostUnsafeBorough = "";
        int x = 0;
        double highestCrimeRate = 0;
        while (x < londonStatisticsList.size()) {
            LondonStatistic stat = londonStatisticsList.get(x);
            if (stat.getCrimeRate() > highestCrimeRate) {
                highestCrimeRate = stat.getCrimeRate();
                mostUnsafeBorough = stat.getBorough();
            }
            x++;
        }
        return mostUnsafeBorough;
    }

    /**
     * creates a hashmap with borough name as key and a list of panel Statistics
     */
    public void createPanelStatisticsMap(){
        panelStatisticMap.put("London", getPanelLondonStats());
        for (String s : boroughList){
            panelStatisticMap.put(s, createBoroughStats(propertyMap.get(s), s));
        }
    }

    /**
     *
     * @return panelStatisticMap
     */
    public HashMap<String, List<PanelStatistic>> getPanelStatisticMap(){
        return panelStatisticMap;
    }

    /**
     *
     * @return expensiveBorough
     */
    public String getMostExpensiveBorough(){
        return expensiveBorough;
    }

    /**
     *
     * @return affordableBorough
     */
    public String getMostAffordableBorough(){
        return affordableBorough;
    }

    /**
     *
     * @param boroughName takes in as key for propertyMap
     * @return return List of properties for said borough
     */
    public ArrayList<AirbnbListing> getBoroughList(String boroughName){
        return propertyMap.get(boroughName);
    }
    /**
     * Goes through airbnbList and calculates the average number of reviews
     */
    private void calculateAverageReviews(){
        averageReviews = totalReviews / airbnbList.size();
    }
    
    /**
     *
     * @return entireHomes
     */
    public int getEntireHomes(){
        return entireHomes;
    }

    /**
     * @return availableProperties
     */
    public int getAvailableProperties() {
        return availableProperties;
    }
    
    /**
     * @return averageReviews
     */
    public double getAverageReviews(){
        return averageReviews;
    }

    /**
     *
     * @return propertyMap
     */
    public HashMap<String, ArrayList<AirbnbListing>> getboroughList() 
    {
        return propertyMap;
    }

    /**
     *
     * @return boroughList (list of boroughName)
     */
    public List<String> getBoroughs(){
        return boroughList;
    }

}
