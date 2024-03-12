package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VisualRug extends ImageView {
    private Image rugImage;

    private static final int sidelength = 80;

    private boolean isVertical;

    private IntPair position;


    public VisualRug(Rug rug) {
        switch (rug.getColour()) {
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
        this.position = rug.getHead();
        this.isVertical = rug.isVertical();
        if (this.isVertical) this.setRotate(90);
    }

    private double distance(double x, double y) {
        double deltaX = this.getLayoutX() - x;
        double deltaY = this.getLayoutY() - y;
        return Math.sqrt(deltaX*deltaX+deltaY*deltaY);
    }


    public class DraggableRug extends VisualRug {

        private double mouseX;
        private double mouseY;

        /* Fields to store information about nearestMove and board position */

        private Marrakech marrakech;

        private VisualBoard visualBoard;




        public DraggableRug(Rug rug) {
            super(rug);

            this.setOnMousePressed(event -> {
                this.mouseX = event.getSceneX();
                this.mouseY = event.getSceneY();
                toFront();
            });

            this.setOnMouseDragged(event -> {
                double deltaX = event.getSceneX() - this.mouseX;
                double deltaY = event.getSceneY() - this.mouseY;
                setLayoutX(getLayoutX() + deltaX);
                setLayoutY(getLayoutY() + deltaY);
                this.mouseX = event.getSceneX();
                this.mouseY = event.getSceneY();
//                board.highlightNearestTriangle(this.getLayoutX(), this.getLayoutY());
            });

            this.setOnMouseReleased(event -> {
//                Triangle nearestTriangle = this.board.findNearestTriangle(this.getLayoutX(), this.getLayoutY());
//                this.setRotate(nearestTriangle.getRotate());
//                this.setLayoutX(nearestTriangle.getLayoutX());
//                this.setLayoutY(nearestTriangle.getLayoutY());
            });
        }
    }
}
