import javafx.application.Application;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import java.io.*;
import javafx.scene.image.*;
import javafx.stage.Stage;

import java.util.*;

/**
 * Creates a statistic pane that shows the user relevent information about a boroug
 * 
 *
 * @authors Mayurapiriyan Ramasamy (19017309), Muhammad Ismail Kamdar(19009749)
 *          Muhammad Alyan Qureshi(1928748).
 */
public class StatisticPanel
{
    // instance variables - replace the example below with your own
    private int x;
    Stats stats = new Stats();
    HashMap<String, List<PanelStatistic>> panelStatisticMap;
    List<String> boroughList = new ArrayList<>();
    List<PanelStatistic> currentDisplayList = new ArrayList<>();
    private static int statCount;
    HashMap<VBox, PanelStatistic> displayedPanelMap;

    private FileInputStream input;

    /**
     * Constructor for objects of class kamdarTestingGUI
     */
    public StatisticPanel()
    {
        // initialise instance variables
        panelStatisticMap = new HashMap<>();
        panelStatisticMap = stats.getPanelStatisticMap();
        currentDisplayList = new ArrayList<>();
        boroughList = stats.getBoroughs();
        boroughList.add("London");
        Collections.sort(boroughList);
        displayedPanelMap = new HashMap<>();
    }

    /**
     * The start method is the main entry point for every JavaFX application. 
     * It is called after the init() method has returned and after 
     * the system is ready for the application to begin running.
     * It creates the stats panels and buttons for generating stats
     * It creates a choiceBox for taking in user preference
     * @param  stage the primary stage for this application.
     */

    public Pane returnStatsPane()
    {
        BorderPane mainPane = new BorderPane();
        List<Button> buttonList = new ArrayList<>();
        Button generateButton = new Button("Generate Stats");
        GridPane gridPane = new GridPane();
        mainPane.setId("mainPane");
        generateButton.setId("generateButton");

        try {
            input = new FileInputStream("ppaStatsBackground.jpg");
        }
        catch (Exception e) {
            System.out.println("File not available");
        }
        Image image = new Image(input);

        Background background = new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT));
        gridPane.setBackground(background);

        // pane1
        BorderPane pane1 = new BorderPane();
        pane1.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.6), CornerRadii.EMPTY, Insets.EMPTY)));
        VBox vBox = new VBox();
        Button next = new Button("next");
        Button prev = new Button("prev");
        BorderPane nextButtonPane1 = new BorderPane(next);
        BorderPane prevButtonPane1 = new BorderPane(prev);

        pane1.setCenter(new BorderPane(vBox));
        pane1.setRight(nextButtonPane1);
        pane1.setLeft(prevButtonPane1);
        pane1.setStyle("-fx-border-style: solid inside;" + "-fx-border-color: grey;");

        pane1.setId("pane1");
        //pane2
        BorderPane pane2 = new BorderPane();
        pane2.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.6), CornerRadii.EMPTY, Insets.EMPTY)));
        VBox vBox2 = new VBox();
        Button next2 = new Button("next");
        Button prev2 = new Button("prev");
        BorderPane nextButtonPane2 = new BorderPane(next2);
        BorderPane prevButtonPane2 = new BorderPane(prev2);

        pane2.setCenter(new BorderPane(vBox2));
        pane2.setRight(nextButtonPane2);
        pane2.setLeft(prevButtonPane2);
        pane2.setStyle("-fx-border-style: solid inside;" + "-fx-border-color: grey;");

        pane2.setId("pane2");
        // pane 3
        BorderPane pane3 = new BorderPane();
        pane3.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.6), CornerRadii.EMPTY, Insets.EMPTY)));
        VBox vBox3 = new VBox();
        Button next3 = new Button("next");
        Button prev3 = new Button("prev");
        BorderPane nextButtonPane3 = new BorderPane(next3);
        BorderPane prevButtonPane3 = new BorderPane(prev3);

        pane3.setCenter(new BorderPane(vBox3));
        pane3.setRight(nextButtonPane3);
        pane3.setLeft(prevButtonPane3);
        pane3.setStyle("-fx-border-style: solid inside;" + "-fx-border-color: grey;");

        pane3.setId("pane3");

        //pane4
        BorderPane pane4 = new BorderPane();
        pane4.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.6), CornerRadii.EMPTY, Insets.EMPTY)));
        VBox vBox4 = new VBox();
        Button next4 = new Button("next");
        Button prev4= new Button("prev");
        BorderPane nextButtonPane4 = new BorderPane(next4);
        BorderPane prevButtonPane4 = new BorderPane(prev4);

        pane4.setCenter(new BorderPane(vBox4));
        pane4.setRight(nextButtonPane4);
        pane4.setLeft(prevButtonPane4);
        pane4.setStyle("-fx-border-style: solid inside;" + "-fx-border-color: grey;");

        pane4.setId("pane4");

        Label label3 = new Label(boroughList.get(x));

        ArrayList<VBox> vBoxes = new ArrayList<>();
        vBoxes.add(vBox);
        vBoxes.add(vBox2);
        vBoxes.add(vBox3);
        vBoxes.add(vBox4);

        //button events
        ChoiceBox choiceBox = createChoiceBox();

        // gets the list of panel stats for the borough from hashmap thorugh key selected on choicebox
        currentDisplayList = panelStatisticMap.get(choiceBox.getValue());
        displayStats(vBoxes);
        generateButton.setOnAction(e -> getChoice(choiceBox, vBoxes));

        next.setOnAction(e -> nextStat(vBox));
        next2.setOnAction(e -> nextStat(vBox2));
        next3.setOnAction(e -> nextStat(vBox3));
        next4.setOnAction(e -> nextStat(vBox4));

        prev.setOnAction(e -> prevStat(vBox));
        prev2.setOnAction(e -> prevStat(vBox2));
        prev3.setOnAction(e -> prevStat(vBox3));
        prev4.setOnAction(e -> prevStat(vBox4));


        gridPane.setHgap(120);
        gridPane.setVgap(50);

        gridPane.add(pane1,0,0);
        gridPane.add(pane2,0,1);
        gridPane.add(pane3,1,0);
        gridPane.add(pane4,1,1);

        BorderPane selectionPane = new BorderPane();
        selectionPane.setLeft(choiceBox);
        selectionPane.setRight(generateButton);

        //AnchorPane anchorPane = new AnchorPane(generateButton, choiceBox);
        //anchorPane.setLeftAnchor(choiceBox, 5.0);
        //anchorPane.setRightAnchor(generateButton, 5.0);

        mainPane.setCenter(gridPane);
        mainPane.setTop(selectionPane);
        mainPane.setPrefSize(1000,675);
        

        return mainPane;
    }

    /**
     *
     * @param choiceBox, takes in values from choiceBox
     *                   gets the ArrayList from panelStatisticMap using choiceBox value
     *                   and assigns it to currentDisplayList
     * @param vBoxes  calls displayStats passing vBoxes as a parameter
     */
    private void getChoice(ChoiceBox<String> choiceBox, List<VBox> vBoxes){
        currentDisplayList = panelStatisticMap.get(choiceBox.getValue());
        displayStats(vBoxes);
    }

    /**
     *
     * @param vBoxes
     * this methods sets the initial display for the stats
     * it takes panel stats from the display when generateButton is clicked
     * it clears prev vboxes and panel statistic displayed before
     * it gets new panel  statistics from the displayList and displays the stats while setting their boolean to true
     * adds paddings to stats
     */
    private void displayStats(List<VBox> vBoxes){

        for (VBox box : vBoxes) {
            if(displayedPanelMap.get(box) != null) {
                displayedPanelMap.get(box).setFalse();
            }
        }
        statCount = 0;
        displayedPanelMap.clear();
        for (VBox box : vBoxes){
            box.getChildren().clear();
            box.setPrefSize(350,300);
            PanelStatistic displayStat = currentDisplayList.get(statCount);
            displayStat.setTrue();
            displayedPanelMap.put(box, displayStat);
            Label description = new Label(displayStat.getStatDescription());
            Label value = new Label((displayStat.getStatValue()));

            description.setWrapText(true);
            value.setWrapText(true);

            description.setTextFill(Color.WHITE);
            value.setTextFill(Color.WHITE);

            description.setFont(new Font("Forte", 14));
            value.setFont(new Font("Times New Roman", 15));
            value.setPadding(new Insets(10,0,10,0));

            box.getChildren().add(description);
            box.getChildren().add(value);
            box.setSpacing(15);
            box.setPadding(new Insets(50, 15,50,15));
            statCount++;
        }
    }

    /**
     *
     * @param box clears box
     * increments statCount and gets the respective stat from list if its isShown is false else it increments and repeats
     *
     */
    private void nextStat(VBox box){
        box.getChildren().clear();
        PanelStatistic displayStat = currentDisplayList.get(statCount);
        while (displayStat.getIsShown() == true) {
            statCount++;
            if (statCount == currentDisplayList.size()) {
                statCount = 0;
                //roll back statCount
            }
            displayStat = currentDisplayList.get(statCount);
        }

        displayedPanelMap.get(box).setFalse();
        // sets itShown true
        displayStat.setTrue();
        displayedPanelMap.replace(box, displayStat);
        Label description = new Label(displayStat.getStatDescription());
        Label value = new Label((displayStat.getStatValue()));

        description.setTextFill(Color.WHITE);
        value.setTextFill(Color.WHITE);

        description.setFont(new Font("Forte", 14));
        value.setFont(new Font("Times New Roman", 15));
        value.setPadding(new Insets(0,50,0,25));

        box.getChildren().add(description);
        box.getChildren().add(value);
        box.setSpacing(15);
        box.setPadding(new Insets(50, 15,50,15));
    }

    /**
     *
     * @param box clears box
     * decrements statCount and gets the respective stat from list if its isShown is false else it decrements and repeats
     *
     */
    private void prevStat(VBox box){
        PanelStatistic displayStat = currentDisplayList.get(statCount);
        box.getChildren().clear();
        while (displayStat.getIsShown() == true) {
            statCount--;
            if (statCount < 0) {
                statCount = (currentDisplayList.size() - 1);
                // roll statCount forward
            }
            displayStat = currentDisplayList.get(statCount);
        }

        displayedPanelMap.get(box).setFalse();
        displayStat.setTrue();
        displayedPanelMap.replace(box, displayStat);
        Label description = new Label(displayStat.getStatDescription());
        Label value = new Label((displayStat.getStatValue()));

        description.setTextFill(Color.WHITE);
        value.setTextFill(Color.WHITE);

        description.setFont(new Font("Forte", 14));
        value.setFont(new Font("Times New Roman", 15));
        value.setPadding(new Insets(0,50,0,25));

        box.getChildren().add(description);
        box.getChildren().add(value);
        box.setSpacing(15);
        box.setPadding(new Insets(50, 15,50,15));
    }

    /**
     * creates a choicebox using boroughName from boroughList
     * @return choiceBox
     */
    private ChoiceBox<String> createChoiceBox(){
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        for (String s : boroughList){
            choiceBox.getItems().add(s);
        }
        // set a default value
        choiceBox.setValue("London");
        return choiceBox;
    }
}
