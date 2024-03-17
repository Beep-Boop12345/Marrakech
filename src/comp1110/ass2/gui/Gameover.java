package comp1110.ass2.gui;

import comp1110.ass2.Marrakech;
import comp1110.ass2.Player;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Gameover {
    private static Stage primaryStage;
    private static final int SETUP_WIDTH = 1200 * 3 / 4;
    private static final int SETUP_HEIGHT = 750 * 3 / 4;

    private static final StackPane root = new StackPane();

    private static Group controls;

    static Button exitButton;
    static HBox exitHBox;
    static Label gameOverLabel = new Label();
    static Label winnerLabel = new Label();

    /**
     * Displays the gameOver screen
     * @author u7330006
     *
     * @param marrakech represents the state of the finished game*/
    public static void display(Marrakech marrakech) {

        primaryStage = new Stage();
        primaryStage.setTitle("Game Over");

        // Create a Scoreboard to display the scores
        ArrayList<Scoreboard> scoreboards = new ArrayList<>();
        for (int i = 0; i < marrakech.getNumberOfPlayers() ; i++) {
            Player player = marrakech.getCurrentPlayers()[i];
            Scoreboard scoreboard = new Scoreboard(player, true, marrakech.getBoard());
            scoreboard.setLayoutY(i*scoreboard.getLayoutBounds().getHeight()+10);
            scoreboards.add(scoreboard);
        }


        // Create the Exit Button and Game Over Label
        makeControls(marrakech);

        // Organize everything into a vertical box with 30 pixel spacing
        VBox controlsVBox = new VBox(30);
        controlsVBox.setAlignment(Pos.CENTER);
        gameOverLabel.setAlignment(Pos.CENTER);
        winnerLabel.setAlignment(Pos.CENTER);
        exitHBox.setAlignment(Pos.CENTER);

        controlsVBox.getChildren().add(gameOverLabel);
        controlsVBox.getChildren().add(winnerLabel);
        controlsVBox.getChildren().addAll(scoreboards);
        controlsVBox.getChildren().add(exitHBox);


        controls = new Group(controlsVBox);
        root.getChildren().add(controls);

        Scene scene = new Scene(root, SETUP_WIDTH, SETUP_HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * Initialize and format control objects
     * @author u7330006
     *
     * @param marrakech object representing the finished game*/
    public static void makeControls(Marrakech marrakech) {

        // Generate the "Game over" text
        gameOverLabel.setText("Game Over");
        gameOverLabel.setTextFill(Color.BLACK);
        gameOverLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 36;");



        // Finding the winners
        char winnerChar = marrakech.getWinner();
        String gameOverMessage;
        switch (winnerChar) {
            case 't' -> gameOverMessage = "The Game is a Tie!";
            case 'c' -> gameOverMessage = "The Winner is Cyan!";
            case 'y' -> gameOverMessage = "The Winner is Yellow!";
            case 'p' -> gameOverMessage = "The Winner is Purple!";
            case 'r' -> gameOverMessage = "The Winner is Red!";
            case 'n' -> gameOverMessage = "The Game has not finished.";
            default -> gameOverMessage = "Error";

         }




        // Set text for the winner label
        winnerLabel.setText(gameOverMessage);
        winnerLabel.setTextFill(Color.BLACK);
        winnerLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 36;");

        // Create an HBox for the "Exit" button
        exitButton = new Button("Exit");
        // #ff6666 is hexadecimal for light red
        exitButton.setStyle("-fx-background-color: #ff6666; -fx-text-fill: black; -fx-font-weight: bold;");
        exitButton.setMinWidth(120);
        exitButton.setOnAction(e -> {primaryStage.close();});

        exitHBox = new HBox(exitButton);


    }
}
