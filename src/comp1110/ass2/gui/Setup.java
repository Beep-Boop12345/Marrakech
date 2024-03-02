package comp1110.ass2.gui;
import comp1110.ass2.*;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import java.util.Arrays;

public class Setup {
    private static final Stage primaryStage = new Stage();
    private static final int SETUP_WIDTH = 1200*3/4;
    private static final int SETUP_HEIGHT = 750*3/4;
    private static int playerCount = 2;

    private static int aiCount = 0;


    //private static boolean aiVariant = false;
    private static final AnchorPane background = new AnchorPane();

    private static final Group controls = new Group();

    private static final StackPane root = new StackPane();

    private static Label playerCountDisplay = new Label("2");
    private static Label aiCountDisplay = new Label("0");

    static Button incrementPlayersButton;
    static Button decrementPlayersButton;

    static Button incrementAIButton;
    static Button decrementAIButton;
    static Button playButton;

    /**
     * Initializes/formats the controls
     * @author u7330006
     **/
    public static void makeControls() {
        // Label for displaying "Number of Players" text
        Label playerCountLabel = new Label("Number of Players");
        playerCountLabel.setTextFill(Color.WHITE);
        playerCountLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

        // Create a label for displaying the player count
        playerCountDisplay.setStyle("-fx-font-weight: bold;");

        // Buttons to adjust the number of players
        incrementPlayersButton = createButton("+", "-fx-background-color: lightblue; -fx-font-weight: bold;");
        decrementPlayersButton = createButton("-", "-fx-background-color: lightgray; -fx-font-weight: bold;");


        // Label for displaying "Number of AIs" text
        Label aiCountLabel = new Label("Number of AIs");
        aiCountLabel.setTextFill(Color.WHITE);
        aiCountLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

        // Create a label for displaying the ai count
        aiCountDisplay.setStyle("-fx-font-weight: bold;");

        // Buttons to adjust the number of ai
        incrementAIButton = createButton("+", "-fx-background-color: lightblue; -fx-font-weight: bold;");
        decrementAIButton = createButton("-", "-fx-background-color: lightgray; -fx-font-weight: bold;");


        // Create an HBox for the increment and decrement buttons, along with the player count
        HBox playerCountHBox = new HBox(50);
        playerCountHBox.setAlignment(Pos.CENTER);
        playerCountHBox.getChildren().addAll(decrementPlayersButton, playerCountDisplay, incrementPlayersButton);

        // Create an HBox for the increment and decrement buttons, along with the ai count
        HBox aiCountHBox = new HBox(50);
        aiCountHBox.setAlignment(Pos.CENTER);
        aiCountHBox.getChildren().addAll(decrementAIButton, aiCountDisplay, incrementAIButton);

        // Create an HBox for the "Play" button
        playButton = createButton("Play", "-fx-background-color: lightgoldenrodyellow; -fx-text-fill: black;" +
                " -fx-font-weight: bold;");
        playButton.setMinWidth(80);
        HBox playButtonHBox = new HBox(playButton);
        playButtonHBox.setAlignment(Pos.CENTER);

        // Create a VBox to organize the controls vertically
        VBox controlVBox = new VBox(10);
        controlVBox.getChildren().addAll(playerCountLabel, playerCountHBox, aiCountLabel, aiCountHBox,playButtonHBox);
        controlVBox.setStyle("-fx-background-color: " + Color.LIGHTGRAY.interpolate(Color.TRANSPARENT, 0.5)
                .toString().replace("0x", "#") + ";");
        controlVBox.setAlignment(Pos.CENTER);
        controlVBox.setPadding(new Insets(10,10,10,10));

        // Event Handling
        setupEventHandlers();


        // Set the root to the StackPane
        controls.getChildren().add(controlVBox);
    }

    /** Helper method to create buttons (of all the same size)
     * @author u7330006
     *
     * @param text the text the button displays
     * @param style the String representing standard javaFX style parameters*/
    private static Button createButton(String text, String style) {
        Button button = new Button(text);
        button.setStyle(style);
        button.setPrefWidth(25);
        button.setPrefHeight(25);
        return button;
    }


    // Method for event handling
    private static void setupEventHandlers() {
        incrementPlayersButton.setOnAction(event -> {
            if ((playerCount + aiCount) < 4) {
                playerCount++;
                playerCountDisplay.setText(Integer.toString(playerCount));
            }
        });

        decrementPlayersButton.setOnAction(event -> {
            if ((playerCount) > 1) {
                playerCount--;
                playerCountDisplay.setText(Integer.toString(playerCount));
            }
        });

        incrementAIButton.setOnAction(event -> {
            if ((playerCount + aiCount) < 4) {
                aiCount++;
                aiCountDisplay.setText(Integer.toString(aiCount));
            }
        });

        decrementAIButton.setOnAction(event -> {
            if (aiCount > 0) {
                aiCount--;
                aiCountDisplay.setText(Integer.toString(aiCount));
            }
        });


        /* If the playButton is hit the game will start, the play button generates
           the first game instance that will start the game                     */
        playButton.setOnAction(e -> {
            Marrakech initialGame = new Marrakech(playerCount);
            new Viewer(initialGame);
            primaryStage.close();
        });
    }

    // Method to make background
    public static void makeBackground() {
        // Create background image
        Image backgroundImage = new Image("file:assets/Images/marrakeshcover.jpg", SETUP_HEIGHT, SETUP_WIDTH, true, true, true);
        ImageView backgroundImageView = new ImageView(backgroundImage);
        root.getChildren().add(backgroundImageView);

        AnchorPane.setBottomAnchor(backgroundImageView,0.0);
        AnchorPane.setLeftAnchor(backgroundImageView,0.0);
        AnchorPane.setRightAnchor(backgroundImageView,0.0);
        AnchorPane.setTopAnchor(backgroundImageView,0.0);

        // Fit to fullscreen if window size changed
        backgroundImageView.fitWidthProperty().bind(root.widthProperty());
        backgroundImageView.fitHeightProperty().bind(root.heightProperty());

    }

    public static void display() {
        primaryStage.setTitle("Setup");
        makeBackground();
        makeControls();
        controls.setTranslateY(100);

        root.getChildren().addAll(background, controls);

        Scene scene = new Scene(root, SETUP_WIDTH, SETUP_HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();
    }


}



