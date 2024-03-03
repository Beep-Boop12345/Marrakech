package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VisualRug extends ImageView {
    private Image rugImage;

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
}
