package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VisualRug extends ImageView {
    private Image rugImage;

    private boolean isVertical;


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

        this.isVertical = rug.isVertical();
    }
}
