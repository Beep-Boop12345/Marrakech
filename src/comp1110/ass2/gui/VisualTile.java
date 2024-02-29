package comp1110.ass2.gui;

import comp1110.ass2.Tile;
import javafx.scene.Group;

public class VisualTile extends Group {

    public VisualTile(Tile tile, int x, int y) {
        this.setLayoutX(x);
        this.setLayoutY(y);
    }
}
