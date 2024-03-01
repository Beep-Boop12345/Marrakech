package comp1110.ass2.gui;
import comp1110.ass2.*;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VisualAssam extends ImageView {
    private static final int sidelength = 80;

    private Direction direction;

    private Image assamImage;

    public VisualAssam(Assam assam) {
        this.direction = assam.getDirection();
        setAssamImage();
        this.setPreserveRatio(true);
        this.setSmooth(true);
        this.setFitHeight(sidelength);
        this.setFitWidth(sidelength);
    }

    public void setAssamImage() {
        switch (this.direction) {
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


}
