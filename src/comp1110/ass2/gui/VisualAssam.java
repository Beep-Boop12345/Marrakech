package comp1110.ass2.gui;
import comp1110.ass2.*;
import javafx.scene.Group;

public class VisualAssam extends Group {
    private static final int sidelength = 80;

    private int x;

    private int y;

    public VisualAssam(Assam assam) {
        this.x = assam.getPosition().getX();
        this.y = assam.getPosition().getY();

    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
