package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        private double distance(double x, double y) {
            double deltaX = this.getLayoutY() - x;
            double deltaY = this.getLayoutY() - y;
            return Math.sqrt(deltaX*deltaX + deltaY*deltaY);
        }


        public void highlight(Colour colour) {
            Color color = colourToColor(colour);
            this.setFill(color);
        }

        public void dehighlight() {
            this.setFill(Color.rgb(245,228,176));
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

    public void highlightNearestRug(double x, double y, boolean isVertical, Colour colour) {
        List<Square> squaresToHighlight = findNearestRug(x, y, isVertical);
        for (Square square : squaresToHighlight) {
            square.highlight(colour);
        }
    }

    public void dehighlightAllSquares() {
        for (Square square : this.boardSquares) {
            square.dehighlight();
        }
    }

    public List<Square> findNearestRug(double x, double y, boolean isVertical) {
        List<Square> rugSquares = new ArrayList<>();
        Square nearestSquare = this.boardSquares.get(0);
        for (Square square : this.boardSquares) {
            if (square.distance(x,y) < nearestSquare.distance(x,y)) {
                nearestSquare = square;
            }
        }

        double nearestSquareX = nearestSquare.getLayoutX();
        double nearestSquareY = nearestSquare.getLayoutY();
        double otherSquareX;
        double otherSquareY;
        if (isVertical) {
            otherSquareX = nearestSquareX;
            otherSquareY = nearestSquareY + sidelength;
        } else {
            otherSquareX = nearestSquareX + sidelength;
            otherSquareY = nearestSquareY;
        }
        Square otherSquare = null;
        for (Square square : this.boardSquares) {
            if (square.getLayoutX() == otherSquareX && square.getLayoutY() == otherSquareY) {
                otherSquare = square;
                break;
            }
        }
        rugSquares.add(nearestSquare);
        rugSquares.add(otherSquare);
        return rugSquares;
    }
}
