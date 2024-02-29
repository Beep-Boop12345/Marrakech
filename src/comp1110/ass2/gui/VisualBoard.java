package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class VisualBoard extends Group {

    private Board board;

    private ArrayList<Square> boardSquares;
    private ArrayList<VisualTile> tiles;


    private static final int sidelength = 80;

    class Square extends Rectangle {
        public Square(double side, double x, double y) {
            super(side,side);
            this.setLayoutX(x);
            this.setLayoutY(y);
            setFill(Color.rgb(245,228,176));
            setStroke(Color.BLACK);
            setStrokeWidth(2);
        }

    }

    VisualBoard(Board board) {
        this.board = board;
        this.boardSquares = new ArrayList<>();
        this.tiles = new ArrayList<>();


        // Creates the background boardSquares
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                double xPos = i * sidelength;
                double yPos = j * sidelength;
                Square square = new Square(sidelength,xPos,yPos);
                this.boardSquares.add(square);
            }
        }
        this.getChildren().addAll(boardSquares);

        // Adds all the rug elements on top of the board
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                Tile tile = this.board.getSurfaceTiles()[i][j];
                VisualTile visualTile = new VisualTile(tile,i,j);
                tiles.add(visualTile);
            }
        }
        this.getChildren().addAll(tiles);
    }

    public void displayAssam(VisualAssam visualAssam) {
        visualAssam.setLayoutX(visualAssam.getX());
        visualAssam.setLayoutY(visualAssam.getY());
    }


}
