package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import javafx.scene.media.AudioClip;
import javafx.stage.Stage;


import java.util.ArrayList;


public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;

    private final Group root = new Group();
    private final Group controls = new Group();

    private Group currentView = new Group();
    private TextField boardTextField;

    // Static fields for easy access

    private static ImageView dieImageV;

    private static Button rollButton = new Button();

    private static Button rotateButton = new Button();
    
    private static Roller clock;

    /* Static Fields for easy access */
    private static Marrakech currentMarrakech;

    private static VisualBoard currentBoard;

    private static final AudioClip dieSound = new AudioClip("file:assets/Sounds/rollsound.mp3");

    private static final AudioClip assamMoveSound = new AudioClip("file:assets/Sounds/assamslide.mp3");


    /* Code used to animate dice rolls, roll button event handler included */
    private class Roller extends AnimationTimer {

        private long FRAMES_PER_SEC = 50L;
        private long INTERVAL = 1000000000L / FRAMES_PER_SEC;
        private int MAX_ROLLS = 30;

        private long last = 0;
        private int count = 0;

        @Override
        public void handle(long now) {
            if (now - last > INTERVAL) {
                int rollInitial = Marrakech.rollDie();
                setDieImage(rollInitial);
                last = now;
                count++;
                if (count > MAX_ROLLS) {
                    clock.stop();
                    int rollFinal = Marrakech.rollDie();
                    setDieImage(rollFinal);
                    assamMoveSound.play();
                    rollButton.setDisable(false);
                    count = 0;
                }
            }
        }
    }


    private void setupRollEventHandler() {
        rollButton.setOnAction(event -> {
            clock.start();
            rollButton.setDisable(true);
            dieSound.play();
        });
    }

    private void initialiseRoller() {
        clock = new Roller();
    }



    /**
     * Draw a placement in the window, removing any previously drawn placements
     *
     * @param state an array of two strings, representing the current game state
     */
    void displayState(String state) {
        Marrakech marrakech = new Marrakech(state);
        displayGame(marrakech);
        // FIXME Task 5: implement the simple state viewer
    }


    // Test String
    // Pc03015iPy03015iA33NBn00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00
    // Pc05700iPy01700iPp03900iPr00700iA35WBp10y41y11y11n00n00p40p10y41c01p36r19y32p40p25p25r04p36p09y32c43c13r11c14c33y47r22c43c13p34r32c33y47c47c47y13p34r32r17p38r46p29p20y37c39c39p38r46p29

    void displayGame(Marrakech marrakech) {

        // Reset the current view
        root.getChildren().remove(currentView);
        currentMarrakech = marrakech;
        Group newView  = new Group();


        // Constructs the board and adds it to the viewer, (assam is included)
        currentBoard = new VisualBoard(marrakech.getBoard());
        currentBoard.displayAssam(marrakech.getAssam());

        // Calculate the position to center the board
        double boardWidth = currentBoard.getLayoutBounds().getWidth();
        double boardHeight = currentBoard.getLayoutBounds().getHeight();
        double boardX = (VIEWER_WIDTH - boardWidth) / 2;
        double boardY = (VIEWER_HEIGHT - boardHeight) / 2;
        currentBoard.setLayoutX(boardX);
        currentBoard.setLayoutY(boardY);
        newView.getChildren().add(currentBoard);

        // Scoreboard for all players
        ArrayList<Scoreboard> scoreboards = new ArrayList<>();
        double scoreBoardX = boardX + boardWidth + 50;
        int radius = 20;
        Player[] currentPlayers = marrakech.getCurrentPlayers();
        for (int i = 0; i < currentPlayers.length; i++) {
            Scoreboard scoreboard = new Scoreboard(currentPlayers[i]);
            double scoreboardHeight = scoreboard.getLayoutBounds().getHeight();
            double scoreBoardY = (boardY + radius + 150) + (1.5 * scoreboardHeight * i);
            scoreboard.setLayoutX(scoreBoardX);
            scoreboard.setLayoutY(scoreBoardY);
            if (i == marrakech.getCurrentTurn()) {
                scoreboard.setOpacity(1);
            } else {
                scoreboard.setOpacity(0.3);
            }
            scoreboards.add(scoreboard);

        }
        newView.getChildren().addAll(scoreboards);

        // Roll Button
        Image rollIcon = new Image("file:assets/Images/roll-icon.png",100,100,true,true);
        ImageView rollIconV = new ImageView(rollIcon);
        rollButton.setGraphic(rollIconV);
        rollButton.setMinSize(100,100);
        rollButton.setMaxSize(100,100);

        double rollX = boardX - 250;
        double rollY = boardY + boardHeight / 2;
        rollButton.setLayoutX(rollX);
        rollButton.setLayoutY(rollY);
        newView.getChildren().add(rollButton);

        // Dice Image & Animation (Defaults to showing 1)
        Image dieImage = new Image("file:assets/Images/dice-six-faces-1.png",100,100,true,true);
        dieImageV = new ImageView(dieImage);
        double dieX = rollX;
        double dieY = rollY - 105;
        dieImageV.setLayoutX(dieX);
        dieImageV.setLayoutY(dieY);
        newView.getChildren().add(dieImageV);


        // Rotate Assam
        Image rotateIcon = new Image("file:assets/Images/rotate-icon.png", 100, 100, true, true);
        ImageView rotateIconV = new ImageView(rotateIcon);
        rotateButton.setGraphic(rotateIconV);
        rotateButton.setMinSize(100,100);
        rotateButton.setMaxSize(100,100);

        double rotateX = dieX;
        double rotateY = dieY - 105;
        rotateButton.setLayoutX(rotateX);
        rotateButton.setLayoutY(rotateY);
        newView.getChildren().add(rotateButton);



        // Updates to the new view
        root.getChildren().add(newView);
        currentView = newView;

    }




    /**
     * Given a dice roll update the die's image to the number rolled.
     * Used in animating the dice rolls.
     */
    public void setDieImage(int roll) {
        String imgURL  = "file:assets/Images/dice-six-faces-" +roll+".png";
        Image dieImage = new Image(imgURL,100,100,true,true);
        dieImageV.setImage(dieImage);
    }

    /**
     * Setups the event handler for the rotate button.
     */
    private void setupRotateEventHandler() {
        rotateButton.setOnAction(event -> {
            if (currentMarrakech != null) {
                currentMarrakech.getAssam().rotateAssam(90);
                if (currentBoard != null){
                    currentBoard.getChildren().removeIf(node -> node instanceof VisualAssam);
                    currentBoard.displayAssam(currentMarrakech.getAssam());
                }
            }


        });
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label boardLabel = new Label("Game State:");
        boardTextField = new TextField();
        boardTextField.setPrefWidth(800);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                displayState(boardTextField.getText());
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(boardLabel,
                boardTextField, button);
        hb.setSpacing(10);
        hb.setLayoutX(50);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Marrakech Viewer");

        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(controls);

        makeControls();
        setupRollEventHandler();
        setupRotateEventHandler();
        initialiseRoller();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
