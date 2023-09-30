
/**
 * Create a statistic to be shown in the statistics panel for the user to view.
 *
 * @authors Mayurapiriyan Ramasamy (19017309), Muhammad Ismail Kamdar(19009749)
 *          Muhammad Alyan Qureshi(1928748).
 */
public class PanelStatistic
{
    private String statName;
    private String statDescription;
    private String statValue;
    private boolean isShown;

    /**
     * Create a statistics panel with a set information
     * @param statName: The name of the statistic
     * @param statDescription: The description of the statistic
     * @param statValue: The value of the statistic
     */
    public PanelStatistic(String statName, String statDescription, String statValue)
    {
        this.statName = statName;
        this.statDescription = statDescription;
        this.statValue = statValue;
        isShown = false;
    }
    
    /**
     * set isShown to true
     */
    public void setTrue(){
        isShown = true;
    }
    
    /**
     * set isShown to false
     */
    public void setFalse(){
        isShown = false;
    }

    /**
     * @return: the name of the statistic
     */
    public String getStatName(){
        return statName;
    }
    
    /**
     * @return: the description of the statistic
     */
    public String getStatDescription(){
        return statDescription;
    }
    
    /**
     * @return: the value of the statistic
     */
    public String getStatValue(){
        return statValue;
    }
    
    /**
     * @return: isShow
     */
    public boolean getIsShown(){
        return isShown;
    }
}
