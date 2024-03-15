package comp1110.ass2.gui;
import comp1110.ass2.*;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VisualAssam extends ImageView {
    private static final int sidelength = 80;

    private Assam assam;

    private Image assamImage;


    private static final double boardLayoutX = 319.0;

    private static final double boardLayoutY = 69;

    public VisualAssam(Assam assam) {
        this.assam = assam;
        setAssamImage();
        this.setPreserveRatio(true);
        this.setSmooth(true);
        this.setFitHeight(sidelength);
        this.setFitWidth(sidelength);
        this.toFront();
    }

    public void setAssamImage() {
        switch (this.assam.getDirection()) {
            case NORTH -> {
                assamImage = new Image("file:assets/Images/assamsprites/North.png");
                this.setImage(assamImage);
            }
            case SOUTH -> {
                assamImage = new Image("file:assets/Images/assamsprites/South.png");
                this.setImage(assamImage);
            }
            case EAST -> {
                assamImage = new Image("file:assets/Images/assamsprites/East.png");
                this.setImage(assamImage);
            }
            case WEST -> {
                assamImage = new Image("file:assets/Images/assamsprites/West.png");
                this.setImage(assamImage);
            }
        }
    }




    public void setLayoutPosition() {
        double offset;
        switch (this.assam.getDirection()) {
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

        double assamX = this.assam.getPosition().getX() * sidelength + offset + boardLayoutX;
        double assamY = this.assam.getPosition().getY() * sidelength + boardLayoutY;
        this.setLayoutX(assamX);
        this.setLayoutY(assamY);
        setAssamImage();
        this.toFront();
    }



}
