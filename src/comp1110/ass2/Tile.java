package comp1110.ass2;


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

    @Override
    public String toString() {
        if (!this.isOccupied) {
            return "Empty";
        } else {
            return "Colour " + this.colour + " id: " + this.id;
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
