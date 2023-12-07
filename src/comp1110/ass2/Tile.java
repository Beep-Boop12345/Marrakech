package comp1110.ass2;


import java.util.Objects;

/**
 * This class is used for Rug Tiles on the board
 */
public class Tile {
    private Colour colour;

    private int id;

    private boolean isOccupied;


    /**
     * Makes a Tile on the board given an abbreviatedRugString
     * eg. n00 for Empty c12 for Cyan With ID: 12
     * Note: Not safety checking that the boardString is valid
     * @param abbreviatedRugString
     */
    public Tile(String abbreviatedRugString) {
        if (!(abbreviatedRugString.equals("n00"))) {
            char colourChar = abbreviatedRugString.charAt(0);
            this.colour = Colour.getColour(colourChar);
            this.id = Integer.parseInt(abbreviatedRugString.substring(1));
            this.isOccupied = true;
        } else {
            this.isOccupied = false;
        }
    }

    public Tile(Colour colour, int id, boolean isOccupied) {
        this.colour = colour;
        this.id = id;
        this.isOccupied = isOccupied;
    }

    @Override
    public String toString() {
        if (!this.isOccupied) {
            return "n00";
        } else {
            return colour.toString() + String.format("%02d", this.id);
        }
    }

    public Colour getColour() {
        return this.colour;
    }

    public int getId() {
        return this.id;
    }

    public boolean isOccupied() {
        return this.isOccupied;
    }


}
