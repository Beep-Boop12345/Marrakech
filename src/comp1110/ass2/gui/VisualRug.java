package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import static comp1110.ass2.gui.Viewer.getRollButton;
import static comp1110.ass2.gui.Viewer.getVisualAssam;


public class VisualRug extends ImageView {
    private Image rugImage;

    private Colour colour;

    private static final int sidelength = 80;

    private boolean isVertical;

    private boolean placed;
    private double mouseX;

    private double mouseY;

    private VisualBoard visualBoard;

    private Marrakech marrakech;


    private static final double boardLayoutX = 319.0;

    private static final double boardLayoutY = 69;



    public VisualRug(Colour colour, VisualBoard visualBoard, Marrakech marrakech) {
        this.colour = colour;
        setRugImage();
        this.isVertical = false;
        this.visualBoard = visualBoard;
        this.placed = false;
        this.marrakech = marrakech;

        // Position to default
        resetToDefaultPosition();

        // Event Handling
        setupMouseHandler();
    }

    private void setRugImage() {
        switch (this.colour) {
            case Red -> {
                rugImage = new Image("file:assets/Images/rugs/red.jpg",80,160,false,true);
            }
            case Purple -> {
                rugImage = new Image("file:assets/Images/rugs/purple.jpg",80,160,false,true);
            }
            case Yellow -> {
                rugImage = new Image("file:assets/Images/rugs/yellow.jpg",80,160,false,true);
            }
            case Cyan -> {
                rugImage = new Image("file:assets/Images/rugs/cyan.jpg",80,160,false,true);
            }
        }
        this.setImage(rugImage);
    }

    private void setupMouseHandler() {
        this.setOnMousePressed(event -> {
            this.mouseX = event.getSceneX();
            this.mouseY = event.getSceneY();
        });

        this.setOnMouseDragged(event -> {

            if (!placed) {
                double deltaX = event.getSceneX() - this.mouseX;
                double deltaY = event.getSceneY() - this.mouseY;
                this.setLayoutX(getLayoutX() + deltaX);
                this.setLayoutY(getLayoutY() + deltaY);
                this.mouseX = event.getSceneX();
                this.mouseY = event.getSceneY();
                IntPair closestPosition = findClosestIntPair();
                this.visualBoard.highlightNearestRug(closestPosition, isVertical, colour);
            }
        });

        this.setOnMouseReleased(event -> {
            if (!placed) {
                this.visualBoard.dehighlightAllSquares();
                Player currentPlayer = marrakech.getCurrentPlayers()[marrakech.getCurrentTurn()];
                IntPair closestPosition = findClosestIntPair();
                Rug rugMove = new Rug(closestPosition, isVertical, currentPlayer);
                //  Is Rug Valid IF YES
                if (marrakech.isPlacementValid(rugMove)) {
                    this.placed = true;
                    this.marrakech.cycleTurn();
                    this.marrakech.makePlacement(rugMove);
                    getRollButton().setDisable(false);
                    snapToPosition();
                } else {
                    resetToDefaultPosition();
                }
            }
        });

        this.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.R) {
                if (!placed) {
                    rotate();
                }
            }
        });





    }


    /**
     * Finds the closest IntPair from the current Rug position on the screen.
     * @return closest IntPair to Rug.
     */
    private IntPair findClosestIntPair() {
        int closestX = (int) Math.round((this.getLayoutX()- boardLayoutX) / 80);
        int closestY = (int) Math.round((this.getLayoutY() - boardLayoutY) / 80);

        return new IntPair(closestX, closestY);
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


    /**
     * Changes position to default.
     */
    public void resetToDefaultPosition() {
        this.setLayoutX(79);
        this.setLayoutY(430);
        this.isVertical = false;
        this.setRotate(90);
    }

    public void snapToPosition() {
        VisualBoard.Square nearestSquare = this.visualBoard.findNearestSquare(findClosestIntPair());
        double layoutX;
        double layoutY;
        if (isVertical) {
            layoutX = nearestSquare.getLayoutX()+boardLayoutX;
            layoutY = nearestSquare.getLayoutY()+boardLayoutY;
        } else {;
            layoutX = nearestSquare.getLayoutX()+boardLayoutX + sidelength/2;
            layoutY = nearestSquare.getLayoutY()+boardLayoutY - sidelength/2;

        }
        this.setLayoutX(layoutX);
        this.setLayoutY(layoutY);
    }



}
