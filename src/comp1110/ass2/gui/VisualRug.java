package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Set;

public class VisualRug extends ImageView {
    private Image rugImage;

    private Colour colour;

    private static final int sidelength = 80;

    private boolean isVertical;

    private IntPair position;
    private double mouseX;

    private double mouseY;

    private VisualBoard visualBoard;


    public VisualRug(Colour colour, VisualBoard visualBoard) {
        switch (colour) {
            case Red -> {
                rugImage = new Image("a");
            }
            case Purple -> {
                rugImage = new Image("b");
            }
            case Yellow -> {
                rugImage = new Image("c");
            }
            case Cyan -> {
                rugImage = new Image("");
            }

        }
        this.colour = colour;
        this.isVertical = true;
        this.visualBoard = visualBoard;
        if (this.isVertical) this.setRotate(90);

        this.setOnMouseDragged(event -> {
            this.mouseX = event.getSceneX();
            this.mouseY = event.getSceneY();
            setLayoutX(this.mouseX);
            setLayoutX(this.mouseY);
            this.visualBoard.highlightNearestRug(mouseX, mouseY, isVertical, colour);

        });

        this.setOnMousePressed(event -> {
            this.mouseX = event.getSceneX();
            this.mouseY = event.getSceneY();
            snapToPosition();
        });

    }


    /**
     * Rotates the visualRug on the screen.
     */
    public void rotate() {
        if (this.isVertical) {
            this.setRotate(90);
            this.isVertical = false;
        } else {
            this.setRotate(0);
            this.isVertical = true;
        }
    }

    public void snapToPosition() {
        List<VisualBoard.Square> nearestSquares = this.visualBoard.findNearestRug(mouseX,mouseY,isVertical);
        VisualBoard.Square square = nearestSquares.get(0);
        this.setLayoutX(square.getLayoutX());
        this.setLayoutY(square.getLayoutY());
    }




}
