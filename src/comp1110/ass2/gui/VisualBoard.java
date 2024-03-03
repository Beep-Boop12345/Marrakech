package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class VisualBoard extends Group {

    private Board board;

    private ArrayList<Square> boardSquares;


    private static final int sidelength = 80;

    class Square extends Rectangle {
        public Square(double side, double x, double y, Color color) {
            super(side,side);
            this.setLayoutX(x);
            this.setLayoutY(y);
            setFill(color);
            setStroke(Color.BLACK);
            setStrokeWidth(2);
        }

    }

    VisualBoard(Board board) {
        this.board = board;
        this.boardSquares = new ArrayList<>();


        // Creates the background boardSquares
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                double xPos = i * sidelength;
                double yPos = j * sidelength;
                Tile tile = board.getSurfaceTiles()[i][j];
                Colour colour = tile.getColour();
                Color squareColor = colourToColor(colour);
                Square square = new Square(sidelength,xPos,yPos,squareColor);
                this.boardSquares.add(square);
            }
        }
        this.getChildren().addAll(boardSquares);
    }

    private Color colourToColor(Colour colour) {
        if (colour == null) {
            return Color.rgb(245,228,176);
        }
        switch (colour) {
            case Yellow -> {
                return Color.YELLOW;
            }
            case Purple -> {
                return Color.PURPLE;
            }
            case Cyan -> {
                return Color.CYAN;
            }
            case Red -> {
                return Color.RED;
            }
            default -> {
                return Color.rgb(245,228,176);
            }
        }
    }

    public void displayAssam(Assam assam) {
        VisualAssam visualAssam = new VisualAssam(assam);

        double offset;
        switch (assam.getDirection()) {
            case NORTH: case SOUTH:
                offset = 6;
                break;
            case WEST: case EAST:
                offset = 4;
                break;
            default:
                offset = 0;
                break;
        }

        double assamX = assam.getPosition().getX() * sidelength + offset;
        double assamY = assam.getPosition().getY() * sidelength;

        visualAssam.setLayoutX(assamX);
        visualAssam.setLayoutY(assamY);


        this.getChildren().add(visualAssam);
    }
}
