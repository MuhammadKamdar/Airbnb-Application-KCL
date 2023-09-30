import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.collections.ObservableList;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
/**
 * This is a simplified version of the AirbnbListing class, which is used to store
 * user relevent information.
 *
 * @authors Mayurapiriyan Ramasamy (19017309), Muhammad Ismail Kamdar(19009749)
 *          Muhammad Alyan Qureshi(1928748).
 */
public class SimpleAirbnbListing
{
    private String hostName;
    private int price;
    private int reviews;
    private int nights;
    private String id;
    
    private Button mapButton;
    
    private Button bookButton;
    
    private Button unbookButton;
    
    private double latitude;
    private double longitude;
    
    private DecimalFormat df;
    /**
     * Creates a simple listing that has the ability to book and unbook itself
     * from a list of bookings.
     * @param id: the id of the property
     * @param hostName: the host name of the property
     * @param price: the price to rent the property
     * @param nights: the minimum nights to rent the property
     * @param latitude: the latitude of the property
     * @param longitude: the longitude of the property
     * @param bookButton: button used to add the property to a list of bookings
     * @param unbookButton: button used to remove property from list of bookings
     * @param bookedListings: an observable array list used to store booked properties
     * @param pricePaymentLabel: label used to show the total price of booked properties
     * @param totalCostLabel: label used to show the total price with VAT of booked properties
     */
    public SimpleAirbnbListing(String id, String hostName, int price, int reviews, int nights, double latitude, double longitude, Button bookButton, Button unbookButton, ObservableList<SimpleAirbnbListing> bookedListings, Label pricePaymentLabel, Label totalCostLabel)
    {
        df = new DecimalFormat("#.##");
        this.id = id;
        this.hostName = hostName;
        this.price = price;
        this.reviews = reviews;
        this.nights = nights;
        this.bookButton = bookButton;
        this.unbookButton = unbookButton;
        // Check to see if the property is booked
        for(SimpleAirbnbListing property : bookedListings)
        {
            if(property.getId().contains(this.id))
            {
                bookButton.setDisable(true);
            }
        }
        //When clicked, add the property to the booking list and add the price to the labels
        bookButton.setOnAction((ActionEvent) -> {
            bookedListings.add(this);
            bookButton.setDisable(true);
            float i = 0;
            for(SimpleAirbnbListing property : bookedListings){
                i = i + property.getPrice();
            }
            pricePaymentLabel.setText("Rental price Excluding VAT: £" + df.format(i));
            totalCostLabel.setText("Rental price Including VAT: £" + df.format((i * 1.2)));
        });
        // View the location of the property
        mapButton = new Button("View Property");
        mapButton.setOnAction((ActionEvent) -> viewMap(latitude, longitude));
        //When clicked, remove the property from the booked listing and subtract the price from the labels
        unbookButton.setOnAction((ActionEvent) -> {
            bookedListings.remove(this);
            float i = 0;
            for(SimpleAirbnbListing property : bookedListings){
                i = i + property.getPrice();
            }
            pricePaymentLabel.setText("Price Excluding VAT: £" + df.format(i));
            totalCostLabel.setText("Price Including VAT: £" + df.format((i * 1.2)));
        });
    }

    /**
     * @return: the id of the property
     */
    public String getId()
    {
        return id;
    }
    
    /**
     * @return: the host name of the property
     */
    public String getHostName()
    {
        return hostName;
    }
    
    /**
     * @return: the price of the property
     */
    public int getPrice()
    {
        return price;
    }
    
    /**
     * @return: the amount of reviews on the property
     */
    public int getReviews()
    {
        return reviews;
    }
    
    /**
     * @return: the minimum nights of the property
     */
    public int getNights()
    {
        return nights;
    }
    
    /**
     * @return: the map button
     */
    public Button getMapButton()
    {
        return mapButton;
    }
    
    /**
     * @return: the book button
     */
    public Button getBookButton()
    {
        return bookButton;
    }
    
    /**
     * @return: the unbook button
     */
    public Button getUnbookButton()
    {
        return unbookButton;
    }
    
    /**
     * Open the browser and view the location of the property on google maps
     * @param latitude: the latitiude of the property
     * @param longitude: the longitude of the property
     */
    public void viewMap(double latitude, double longitude)
    {
         try{
            URI uri = new URI("https://www.google.com/maps/place/" + latitude + "," + longitude);
            java.awt.Desktop.getDesktop().browse(uri);
        }
        catch(Exception e){
        }
    }
}
