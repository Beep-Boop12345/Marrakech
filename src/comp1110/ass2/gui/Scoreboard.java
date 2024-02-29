package comp1110.ass2.gui;
import comp1110.ass2.*;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.NoSuchElementException;

public class Scoreboard extends Group {

    public static final int radius = 20;

    private Colour colour;

    private int dirhams;

    private int rugsRemaining;

    private boolean inGame;

    public Scoreboard(Player player) {
        this.colour = player.getColour();
        this.dirhams = player.getDirhams();
        this.rugsRemaining = player.getDirhams();
        this.inGame = player.isInGame();


        // Create circle icon to indicate player
        Circle circle = new Circle(radius);
        circle.setFill(colourToColor(this.colour));
        circle.setStroke(Color.BLACK);

        // Creates a text to indicate the player
        String playerString = "Player: " + colourToString(this.colour);
        Text playerLabel = new Text(30, 0, playerString);
        Font labelFont = Font.font("Calibri", FontWeight.BOLD, 22);
        playerLabel.setFont(labelFont);
        playerLabel.setFill(Color.BLACK);


        // Creates a text to label all playerInformation
        String dirhamString = String.format("%03d",this.dirhams);
        String rugsLeftString = String.valueOf(this.rugsRemaining);
        String inGameString = this.inGame ? "Yes" : "No";
        String playerInformation = "Dirhams: " + dirhamString + " | " + "Rugs left: " + rugsLeftString + " | "
                                 + "In-game: " + inGameString;
        Text playerInfo = new Text(30,15,playerInformation);
        Font infoFont = Font.font("Calibri",12);
        playerInfo.setFont(infoFont);
        playerInfo.setFill(Color.BLACK);


        // Adds everything to the view
        this.getChildren().addAll(circle,playerLabel,playerInfo);




    }


    private Color colourToColor(Colour colour) {
        switch (colour) {
            case Red -> {
                return Color.RED;
            }
            case Cyan -> {
                return Color.CYAN;
            }
            case Purple -> {
                return Color.PURPLE;
            }
            case Yellow -> {
                return Color.YELLOW;
            }
            default -> throw new NoSuchElementException("No such colour exists");

        }
    }

    private String colourToString(Colour colour) {
        switch (colour) {
            case Red -> {
                return "Red";
            }
            case Cyan -> {
                return "Cyan";
            }
            case Purple -> {
                return "Purple";
            }
            case Yellow -> {
                return "Yellow";
            }
            default -> throw new NoSuchElementException("No such colour exists");

        }
    }
}
