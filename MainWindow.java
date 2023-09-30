import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.shape.*;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.Tooltip;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.Modality;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.io.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This class is used to run the application, It contains 4 panels for the
 * welcome pane, map pane, statistics pane and booking pane. 
 *
 * @authors Mayurapiriyan Ramasamy (19017309), Muhammad Ismail Kamdar(19009749)
 *          Muhammad Alyan Qureshi(1928748).
 */
public class MainWindow extends Application
{
    // Stores the window stage
    private Stage mainStage;
    private Scene scene;

    // Stores the data of all properties in a new data loader.
    private AirbnbDataLoader airbnbData;
    // Stores a list of booked properties.
    private ObservableList<SimpleAirbnbListing> bookedListings;
    // Stores the statistics pane.
    private StatisticPanel statisticsPane;

    // Stores the min and max price of all properties.
    private int minPrice;
    private int maxPrice;
    // Stores the min and max of the user input price range.
    private int usrMin;
    private int usrMax;
    // Stores the range if the range is valid.
    private int validMin;
    private int validMax;

    // Stores a boolean to show if map is enabled.
    private boolean isMapEnabled;

    // Stores an Array List of listings and prices
    private ArrayList<AirbnbListing> listings;
    private ArrayList<Integer> prices;
    // Stores an Array List of price values for the user to choose.
    private Integer minimumValues[] = {0,100,200,300,400,500,600,700,800,900,1000,1100,1200,
            1300,1400,1500,1600,1700,1800,1900,2000,2100,2200,2300,2400,2500,2600,2700,
            2800,2900,3000,3100,3200,3300,3400,3500,3600,3700,3800,3900,4000,4100,4200,
            4300,4400,4500,4600,4700,4800,4900,5000,5100,5200,5300,5400,5500,5600,5700,
            5800,5900,6000,6100,6200,6300,6400,6500,6600,6700,6800,6900};
    // Stores an Array List of price values for the user to choose.
    private Integer maximumValues[] = {100,200,300,400,500,600,700,800,900,1000,1100,1200,
            1300,1400,1500,1600,1700,1800,1900,2000,2100,2200,2300,2400,2500,2600,2700,
            2800,2900,3000,3100,3200,3300,3400,3500,3600,3700,3800,3900,4000,4100,4200,
            4300,4400,4500,4600,4700,4800,4900,5000,5100,5200,5300,5400,5500,5600,5700,
            5800,5900,6000,6100,6200,6300,6400,6500,6600,6700,6800,6900,7000};

    private Integer months[] = {1,2,3,4,5,6,7,8,9,10,11,12};
    private Integer years[] = {2020,2021,2022,2023};
    // Stores a hashmap of a borough key and its properties as values.
    private HashMap<String, ArrayList<AirbnbListing>> boroughList;
    private ArrayList<AirbnbListing> borough;
    private Stats boroughStat;
    // Stores the textfield the user will put their price range in.
    private ComboBox minimum;
    private ComboBox maximum;
    // Stores the directional buttons the Welcome Pane will use.
    private Button leftWelcomeButton;
    private Button rightWelcomeButton;
    private Button confirmButton;
    // Stores the directional buttons the Map Pane will use.
    private Button leftMapButton;
    private Button rightMapButton;
    // Stores the directional buttons the Payment Pane will use.
    private Button leftPaymentButton;
    private Button rightPaymentButton;
    // Stores the directional buttons the Statistics Pane will use.
    private Button leftStatsButton;
    private Button rightStatsButton;
    // Stores the user input range.
    private String minimumRange;
    private String maximumRange;
    // Create a new input stream to load images
    private FileInputStream input;
    // Create an ArrayList of scenes for the window to travel through.
    // Stores the scene number
    private int sceneCounter;
    // Stores the relevent labels for the window to use.
    private Label selectedPriceLabel;
    private Label helpLabel1;
    private Label helpLabel2;

    private Label pricePaymentLabel;
    private Label totalCostLabel;
    // Stores textfields for the payment pane to use.
    private TextField userName;
    private TextField userSurname;
    private TextField cardNumber;
    private ComboBox expiryMonth;
    private ComboBox expiryYear;
    private TextField cvvNumber;

    private VBox creditCardPane;
    private Label confirmedPaymentLabel;

    private Button confirmBook;


    /**
     * Contructor that loads in the property data, initialises all the labels
     * buttons and ArrayLists.
     */
    public MainWindow() {
        airbnbData = new AirbnbDataLoader();
        listings = airbnbData.load();
        prices = new ArrayList<>();

        bookedListings = FXCollections.observableArrayList();

        isMapEnabled = false;

        boroughStat = new Stats();
        boroughList = boroughStat.getboroughList();

        getRangeValues();

        sceneCounter = 1;
        minimum = new ComboBox(FXCollections.observableArrayList(minimumValues));
        maximum = new ComboBox(FXCollections.observableArrayList(maximumValues));

        leftWelcomeButton = new Button("<");
        rightWelcomeButton = new Button(">");
        confirmButton = new Button("Confirm");
        leftMapButton = new Button("<");
        rightMapButton = new Button(">");
        leftPaymentButton = new Button("<");
        rightPaymentButton = new Button(">");

        leftStatsButton = new Button("<");
        rightStatsButton = new Button(">");

        selectedPriceLabel = new Label();
        helpLabel1 = new Label();
        helpLabel2 = new Label();
        pricePaymentLabel = new Label();
        totalCostLabel = new Label();
        confirmedPaymentLabel = new Label("Your Payment has been successful! Thank you for using our service! If you wish to book again, please re open the application.");
        confirmedPaymentLabel.setFont(new Font("Arial", 20));
        confirmedPaymentLabel.setPadding(new Insets(50,50,50,50));
        confirmedPaymentLabel.setWrapText(true);
        confirmedPaymentLabel.setVisible(false);

        selectedPriceLabel.setFont(new Font("Arial", 30));
        selectedPriceLabel.setTextFill(Color.WHITE);

        pricePaymentLabel.setFont(new Font("Arial", 30));
        totalCostLabel.setFont(new Font("Arial", 30));

        pricePaymentLabel.setText("Rental price Excluding VAT: £0");
        totalCostLabel.setText("Rental price Including VAT: £0");

        confirmBook = new Button("Confirm Booking");

        userName = new TextField();
        userSurname = new TextField();
        cardNumber = new TextField();

        expiryMonth = new ComboBox(FXCollections.observableArrayList(months));
        expiryYear = new ComboBox(FXCollections.observableArrayList(years));
        cvvNumber = new TextField();
        userName.setPromptText("Enter First Name");
        userSurname.setPromptText("Enter Surname");
        cardNumber.setPromptText("Enter Credit Card Number");
        expiryMonth.setPromptText("Month");
        expiryYear.setPromptText("Year");
        cvvNumber.setPromptText("Enter CVV");

    }

    /**
     * The start method starts the application. It will show the welcome pane
     * to the user.
     *
     * @param  stage the primary stage for this application.
     */
    @Override
    public void start(Stage stage)
    {
        this.mainStage = stage;
        WelcomeScene();
        leftWelcomeButton.setDisable(true);
        rightWelcomeButton.setDisable(true);
        mainStage.setResizable(false);
        mainStage.show();
    }

    /**
     * From the list of properties get the minimum and maximum values of prices.
     */
    public void getRangeValues()
    {
        for (AirbnbListing property : listings) {
            prices.add(property.getPrice());
        }
        minPrice = Collections.min(prices);
        maxPrice = Collections.max(prices);
    }

    /**
     * Print max and min prices of the properties.
     */
    public void printPrices()
    {
        System.out.println("Minimum Price: " + minPrice);
        System.out.println("Maximum Price: " + maxPrice);
    }

    /**
     * Executes if the users range is valid, or opens a new window saying its 
     * invalid and a reason why.
     */
    private void confirmButtonClick(ActionEvent ev)
    {
        Label label1 = new Label();
        Label label2 = new Label();
        String text ="";
        if(usrMin > usrMax || usrMin == usrMax)
        {
            label1.setText("You have entered an invalid range.");
            label2.setText("Minumum is larger than maximum price.");
            text = "Invalid Range";
        }

        else if(usrMin > maxPrice || usrMax < minPrice)
        {
            label1.setText("There are no properties available");
            label2.setText("within the given price range.");
            text = "No Properties Available";
        }
        else { 
            validMin = usrMin;
            validMax = usrMax;
            selectedPriceLabel.setText("From: £" + usrMin + " to £" + usrMax);
            helpLabel1.setText("Click on the right arrow at the bottom to view the map of the boroughs, statistics and booking facilities.");
            helpLabel1.setFont(new Font("Arial", 20));
            helpLabel1.setTextFill(Color.WHITE);
            helpLabel2.setText("If you change your price range while properties are booked. Those properties will be removed.");
            helpLabel2.setFont(new Font("Arial", 15));
            leftWelcomeButton.setDisable(false);
            rightWelcomeButton.setDisable(false);
            minimum.setEditable(false);
            maximum.setEditable(false);
            return;
        }

        VBox pane = new VBox(label1, label2);
        pane.setAlignment(Pos.BASELINE_CENTER);
        pane.setPadding(new Insets(20,20,20,20));
        Scene scene = new Scene(pane);
        Stage labelStage = new Stage();
        labelStage.setTitle(text);
        labelStage.setScene(scene);
        labelStage.setResizable(false);
        labelStage.setX(mainStage.getX() + (mainStage.getWidth() / 2));
        labelStage.setY(mainStage.getY() + (mainStage.getHeight() / 2));
        labelStage.initModality(Modality.APPLICATION_MODAL);
        labelStage.show();
    }

    /**
     * Executes if the credit card information, or opens a new window saying its 
     * invalid and a reason why.
     */
    private void confirmBookingClick(ActionEvent ev)
    {
        Label label1 = new Label();
        Label label2 = new Label();

        String text ="";

        // This checks if all the fields have the correct information.
        if (cardNumber.getText().length() == 0 || cvvNumber.getText().length() == 0
        || userName.getText().length() == 0 || userSurname.getText().length() == 0)
        {
            label1.setText("You have left a field blank.");
            label2.setText("Please fill in the field before proceeding.");
            text = "Blank Field";
        }
        else if (!(userName.getText().matches("[ a-zA-Z]+") && userName.getText().length() > 0)
        || !(userSurname.getText().matches("[ a-zA-Z]+") && userSurname.getText().length() > 0))
        {
            label1.setText("You have entered numbers into the name details.");
            label2.setText("Please write your name with only letters.");
            text = "Numbers Found";
        }
        else if(!(cardNumber.getText().matches("[0-9]+") && cardNumber.getText().length() > 0)
        || !(cvvNumber.getText().matches("[0-9]+") && cvvNumber.getText().length() > 0))
        {
            label1.setText("You have entered letters into the card details.");
            label2.setText("Please write your card number with only numbers.");
            text = "Letters Found";
        }

        else if(cardNumber.getText().length() != 16)
        {
            label1.setText("The card number you have entered is invalid.");
            label2.setText("Please type in your 16 digit card number.");
            text = "Invalid Card Number";
        }
        else if (expiryMonth.getSelectionModel().isEmpty() || 
        expiryYear.getSelectionModel().isEmpty())
        {
            label1.setText("You have not selected your expiry date");
            label2.setText("Please select your expiry date");
            text = "No Expiry Date Selected";
        }
        else if(cvvNumber.getText().length() != 3)
        {
            label1.setText("The CVV number is invalid");
            label2.setText("Please type in your correct CVV.");
            text = "Invalid CVV";
        }
        else if(bookedListings.size() == 0){
            label1.setText("You have not booked any properties");
            label2.setText("Please book a property before paying.");
            text = "No Bookings";
        }
        else {
            for(SimpleAirbnbListing listing : bookedListings)
            {
                listing.getUnbookButton().setDisable(true);
            }

            creditCardPane.setVisible(false);
            confirmedPaymentLabel.setVisible(true);
            leftPaymentButton.setDisable(true);
            rightPaymentButton.setDisable(true);
            return;
        }

        VBox pane = new VBox(label1, label2);
        pane.setAlignment(Pos.BASELINE_CENTER);
        pane.setPadding(new Insets(20,20,20,20));
        Scene scene = new Scene(pane);
        Stage labelStage = new Stage();
        labelStage.setTitle(text);
        labelStage.setScene(scene);
        labelStage.setResizable(false);
        labelStage.setX(mainStage.getX() + (mainStage.getWidth() / 2));
        labelStage.setY(mainStage.getY() + (mainStage.getHeight() / 2));
        labelStage.initModality(Modality.APPLICATION_MODAL);
        labelStage.show();
    }

    /**
     * This method builds the welcome pane for the main stage to use. Then it will
     * set the main stage's current scene to this.
     */
    public void WelcomeScene()
    {
        helpLabel1.setTextFill(Color.WHITE);
        helpLabel2.setTextFill(Color.WHITE);
        Label infoLabel = new Label("Helpful tips will be displayed at the bottom, such as this one");
        // Load in the background image used by the welcome pane.
        try {
            input = new FileInputStream("PPAWelcomeBackground.jpg");
        }
        catch (Exception e) {
            System.out.println("File not available");
        }
        Image image = new Image(input);
        // Create event handlers for the combo boxes to use.
        // If the user changes the range, delete all properties they have booked
        // before.
        EventHandler<ActionEvent> minEvent = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    usrMin = (Integer) minimum.getValue();
                    leftWelcomeButton.setDisable(true);
                    rightWelcomeButton.setDisable(true);
                    bookedListings = FXCollections.observableArrayList();
                }
            };
        minimum.setOnAction(minEvent);

        EventHandler<ActionEvent> maxEvent = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    usrMax = (Integer) maximum.getValue();
                    leftWelcomeButton.setDisable(true);
                    rightWelcomeButton.setDisable(true);
                    bookedListings = FXCollections.observableArrayList();
                }
            };
        maximum.setOnAction(maxEvent);

        Background background = new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT));
        // Set promp text for the text fields.
        minimum.setPromptText("Minimum Price");
        maximum.setPromptText("Maximum Price");
        // Create the sub panes and add relevent components.
        HBox rangeSetter = new HBox(minimum, maximum, confirmButton);
        VBox welcomePane = new VBox(selectedPriceLabel, helpLabel1, helpLabel2);
        welcomePane.setAlignment(Pos.BASELINE_CENTER);
        welcomePane.setPadding(new Insets(300,0,0,0));

        BorderPane topPane = new BorderPane(null, null, rangeSetter, null, null);
        BorderPane bottomPane = new BorderPane(infoLabel, null, rightWelcomeButton, null, leftWelcomeButton);
        // Set styling of each pane
        topPane.setStyle("-fx-border-style: solid inside;" + "-fx-border-color: grey;");
        welcomePane.setStyle("-fx-border-style: solid inside;" + "-fx-border-color: grey;");
        bottomPane.setStyle("-fx-border-style: solid inside;" + "-fx-border-color: grey;");
        BorderPane mainPane = new BorderPane(welcomePane, topPane, null, bottomPane, null);
        mainPane.setPrefSize(1000,700);
        // Give methods to each button.
        confirmButton.setOnAction(this::confirmButtonClick);
        rightWelcomeButton.setOnAction((ActionEvent) -> MapScene());
        leftWelcomeButton.setOnAction((ActionEvent) -> PaymentScene());

        welcomePane.setBackground(background);

        Scene mainScene = new Scene(mainPane);
        mainStage.setScene(mainScene);
    }

    /**
     * Creates the map scene and returns it, calculates the placement of
     * borough buttons and the corresponding booking table.
     * 
     * @param: the window stage.
     */
    public void MapScene()
    {
        // Create a new pane
        Pane mapPane = new Pane();
        mapPane.setPadding(new Insets(10, 10, 10, 10));
        try {
            input = new FileInputStream("ppaMapBackground.jpg");
        }
        catch (Exception e) {
            System.out.println("File not available");
        }
        Image image = new Image(input);

        Background background = new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT));

        mapPane.setBackground(background);
        rightMapButton.setOnAction((ActionEvent) -> StatsScene());

        leftMapButton.setOnAction((ActionEvent) -> WelcomeScene());

        // For each borough, create a button and place it on the map using
        // its lattitude and longitude.
        for (String boroughName: boroughList.keySet())
        {
            int count = calculateInRange(boroughName, usrMin, usrMax);
            Button displayButton = new Button("");
            displayButton.setShape(new Circle(5));
            displayButton.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            displayButton.setPadding(new Insets(0, 0, 0, 0));
            displayButton.setPrefSize(20, 20);
            displayButton.setTooltip(new Tooltip(boroughName + "\n" + count));
            setShadow(displayButton);
            double opacity = count;
            if(count < 100) {
                opacity = 0.05;
            }
            else{
                opacity = opacity/6000.0;
            }
            displayButton.setBackground(new Background(new BackgroundFill(Color.rgb(34, 139, 34, opacity), CornerRadii.EMPTY, Insets.EMPTY)));
            displayButton.setOnAction((ActionEvent) -> { 
                    displayProperties(boroughName);
                });
            ArrayList<AirbnbListing> borough = boroughStat.getBoroughList(boroughName);
            double y = 500 - (Math.round((borough.get(0).getLatitude() - 51.3) * 51143)/40) * 1.8 + 280;
            double x = 590 +(Math.round(borough.get(0).getLongitude()*5004)/4) + 80;
            displayButton.setLayoutX(x);
            displayButton.setLayoutY(y);
            mapPane.getChildren().add(displayButton);
        }
        Label mapInfoLabel = new Label("<- (Start) Hover over a borough to see the name and amount of properties it has. Click on a borough to see properties and to book (Stats) ->");
        BorderPane bottomMapPane = new BorderPane(mapInfoLabel, null, rightMapButton, null, leftMapButton);
        BorderPane superMapPane = new BorderPane(mapPane, null, null, bottomMapPane, null);
        bottomMapPane.setStyle("-fx-border-style: solid inside;" + "-fx-border-color: grey;");
        superMapPane.setPrefSize(1000,700);
        // JavaFX must have a Scene (window content) inside a Stage (window)
        Scene mapScene = new Scene(superMapPane);
        mainStage.setScene(mapScene);
    }

    /**
     * This method returns the statistics scene for the stage to use. 
     */
    public void StatsScene()
    {
        statisticsPane = new StatisticPanel();
        Pane pane = statisticsPane.returnStatsPane();
        rightStatsButton.setOnAction((ActionEvent) -> PaymentScene());
        leftStatsButton.setOnAction((ActionEvent) -> MapScene());
        Label infoLabel = new Label("<- (Map) Here are stats for each borough. Select a borough from the drop down and click on generate stats (Payment) ->");
        BorderPane centerPane = new BorderPane(pane, null, null, null, null);
        BorderPane bottomPane = new BorderPane(infoLabel, null, rightStatsButton, null, leftStatsButton);
        BorderPane mainPane = new BorderPane(centerPane, null, null, bottomPane, null);
        mainPane.setPrefSize(1000,700);

        Scene statsScene = new Scene(mainPane);
        mainStage.setScene(statsScene);
    }

    /**
     * Returns the payment scene for the stage to use. Automatically updates
     * when a user books or unbooks a property and shows the total prices
     * for the properties to be booked.
     */
    public void PaymentScene()
    {
        GridPane mainGridPane = new GridPane();
        mainGridPane.setStyle("-fx-border-style: solid inside;" + "-fx-border-color: grey;");
        // Apartment Interior Pane
        // Gets the image for the appartment interior background
        try {
            input = new FileInputStream("PPAAppartmentInterior.jpg");
        }
        catch (Exception e) {
            System.out.println("File not available");
        }
        Image image = new Image(input);

        Background background = new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT));
        StackPane interiorPane = new StackPane();
        interiorPane.setStyle("-fx-border-style: solid inside;" + "-fx-border-color: grey;");
        interiorPane.setMinSize(500,336);
        interiorPane.setPadding(new Insets(50,50,50,50));
        interiorPane.setBackground(background);

        Label infoLabel = new Label("<- (Stats) Here you can pay for your booked properties, see the total price and unbook properties (Start) ->");
        //Total Cost Pane
        ToggleGroup group = new ToggleGroup();

        RadioButton mr = new RadioButton("Mr.");
        mr.setToggleGroup(group);
        mr.setSelected(true);

        RadioButton mrs = new RadioButton("Miss/Mrs/Ms");
        mrs.setToggleGroup(group);

        VBox userGender = new VBox(mr,mrs);
        VBox userFullName = new VBox(userName, userSurname);
        userFullName.setPadding(new Insets(0,0,0,50));

        HBox userInfo = new HBox(userGender, userFullName);
        userInfo.setPadding(new Insets(0,0,50,0));

        Label vatLabel = new Label("The VAT is at 20%");
        vatLabel.setFont(new Font("Arial", 18));
        VBox labels = new VBox(pricePaymentLabel, totalCostLabel, vatLabel);
        interiorPane.setStyle("-fx-border-style: solid inside;" + "-fx-border-color: grey;");
        labels.setMinSize(500,336);
        labels.setPadding(new Insets(100,0,0,0));
        labels.setAlignment(Pos.BASELINE_CENTER);
        //Booked Property Table
        TableView<SimpleAirbnbListing> table;

        TableColumn<SimpleAirbnbListing, String> nameColumn = new TableColumn<>("Host Name");
        nameColumn.setMinWidth(150);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("hostName"));

        TableColumn<SimpleAirbnbListing, Integer> priceColumn = new TableColumn<>("Price");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<SimpleAirbnbListing, Integer> reviewColumn = new TableColumn<>("Reviews");
        reviewColumn.setMinWidth(80);
        reviewColumn.setCellValueFactory(new PropertyValueFactory<>("reviews"));

        TableColumn<SimpleAirbnbListing, Integer> nightsColumn = new TableColumn<>("Nights");
        nightsColumn.setMinWidth(75);
        nightsColumn.setCellValueFactory(new PropertyValueFactory<>("nights"));

        TableColumn<SimpleAirbnbListing, Button> unbookColumn = new TableColumn<>("");
        unbookColumn.setMinWidth(75);
        unbookColumn.setCellValueFactory(new PropertyValueFactory<>("unbookButton"));

        table = new TableView<>();
        table.setPlaceholder(new Label("No properties Booked"));
        table.setItems(bookedListings);
        table.getColumns().addAll(nameColumn, priceColumn, reviewColumn, nightsColumn, unbookColumn);

        table.setStyle("-fx-border-style: solid inside;" + "-fx-border-color: grey;");
        table.setMinSize(500,336);
        //Credit Card Pane

        confirmBook.setOnAction(this::confirmBookingClick);

        HBox expiryDate = new HBox(expiryMonth, expiryYear);

        creditCardPane = new VBox(userInfo,cardNumber, expiryDate, cvvNumber, confirmBook);
        creditCardPane.setPadding(new Insets(50,50,50,50));

        StackPane pane = new StackPane(creditCardPane, confirmedPaymentLabel);
        pane.setStyle("-fx-border-style: solid inside;" + "-fx-border-color: grey;");

        creditCardPane.setStyle("-fx-border-style: solid inside;" + "-fx-border-color: grey;");
        creditCardPane.setMinSize(500,336);
        rightPaymentButton.setOnAction((ActionEvent) -> WelcomeScene());
        leftPaymentButton.setOnAction((ActionEvent) -> StatsScene());

        //Add panes to the main grid.
        mainGridPane.add(interiorPane,0,0);
        mainGridPane.add(table,0,1);
        mainGridPane.add(labels,1,0);
        mainGridPane.add(pane,1,1);

        BorderPane bottomPane = new BorderPane(infoLabel, null, rightPaymentButton, null, leftPaymentButton);
        bottomPane.setStyle("-fx-border-style: solid inside;" + "-fx-border-color: grey;");
        BorderPane mainPane = new BorderPane(mainGridPane, null, null, bottomPane, null);
        Scene paymentScene = new Scene(mainPane, 1000,700);
        mainStage.setScene(paymentScene);
    }    

    /**
     * Calculates the amount of properties in a given borough and if it is
     * in between the range fiven by the user.
     * @param borough: The name of the borough
     * @param min: The minimum range the user has set
     * @param max: The maximum range the user has set
     */
    public int calculateInRange(String borough, int min, int max)
    {
        int i = 0;
        for (AirbnbListing property : listings){
            if(property.getNeighbourhood().contains(borough) 
            && property.getPrice() >= min && property.getPrice() <= max ){
                i++;
            }
        }
        return i;
    }

    /**
     * this method creates a table view for the properties in a borough
     * and gives the ability for the user to book properties.
     * @param boroughName: The name of the borough.
     */
    public void displayProperties(String boroughName)
    {
        ObservableList<SimpleAirbnbListing> properties = FXCollections.observableArrayList();
        for (AirbnbListing property : listings) {
            if (property.getNeighbourhood().contains(boroughName)
            && property.getPrice() >= usrMin && property.getPrice() <= usrMax ) {
                Button bookButton = new Button("Book");
                Button unbookButton = new Button("Unbook");
                properties.add(new SimpleAirbnbListing(property.getId(), property.getHost_name(), property.getPrice(), property.getNumberOfReviews(), property.getMinimumNights(), property.getLatitude(), property.getLongitude(), bookButton, unbookButton, bookedListings, pricePaymentLabel, totalCostLabel));
            }
        } 

        //Create a TableView of all properties in a borough within the price range
        TableView<SimpleAirbnbListing> table;

        TableColumn<SimpleAirbnbListing, String> nameColumn = new TableColumn<>("Host Name");
        nameColumn.setMinWidth(150);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("hostName"));

        TableColumn<SimpleAirbnbListing, Integer> priceColumn = new TableColumn<>("Price");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<SimpleAirbnbListing, Integer> reviewColumn = new TableColumn<>("Reviews");
        reviewColumn.setMinWidth(80);
        reviewColumn.setCellValueFactory(new PropertyValueFactory<>("reviews"));

        TableColumn<SimpleAirbnbListing, Integer> nightsColumn = new TableColumn<>("Nights");
        nightsColumn.setMinWidth(75);
        nightsColumn.setCellValueFactory(new PropertyValueFactory<>("nights"));

        TableColumn<SimpleAirbnbListing, Button> viewMapButtonColumn = new TableColumn<>("");
        viewMapButtonColumn.setMinWidth(140);
        viewMapButtonColumn.setCellValueFactory(new PropertyValueFactory<>("mapButton"));

        TableColumn<SimpleAirbnbListing, Button> bookColumn = new TableColumn<>("");
        bookColumn.setMinWidth(75);
        bookColumn.setCellValueFactory(new PropertyValueFactory<>("bookButton"));

        table = new TableView<>();
        table.setPlaceholder(new Label("No properties available in given range"));
        table.setItems(properties);
        table.getColumns().addAll(nameColumn, priceColumn, reviewColumn, nightsColumn, viewMapButtonColumn, bookColumn);

        VBox tablePane = new VBox(table);

        Scene boroughScene = new Scene(tablePane);                      

        Stage boroughStage = new Stage();
        boroughStage.setMaxHeight(300);
        boroughStage.setTitle(boroughName);
        boroughStage.setScene(boroughScene);
        boroughStage.setResizable(true);

        boroughStage.setX(mainStage.getX() + (mainStage.getWidth() / 2));
        boroughStage.setY(mainStage.getY() + (mainStage.getHeight() / 2));

        boroughStage.initModality(Modality.APPLICATION_MODAL);

        boroughStage.show();
    }

    /**
     * This method adds a shadow effect to a relevent button.
     * @param A button.
     */
    public void setShadow(Button button)
    {
        DropShadow shadow = new DropShadow();
        button.addEventHandler(MouseEvent.MOUSE_ENTERED,new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    button.setEffect(shadow);
                }
            });
        button.addEventHandler(MouseEvent.MOUSE_EXITED,new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    button.setEffect(null);
                }
            });
    }
}