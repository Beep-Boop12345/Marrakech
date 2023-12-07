package comp1110.ass2;

public enum Colour {
    Cyan('c'),
    Yellow('y'),
    Red('r'),
    Purple('p');

    private char colourChar;


    Colour(char colourChar) {
        this.colourChar = colourChar;
    }


    public static Colour getColour(char colourChar) {
        for (Colour colour : Colour.values()) {
            if (colour.colourChar == colourChar) {
                return colour;
            }
        }
        // Return null if an invalid character is given
        return null;
    }



    @Override
    public String toString() {
        if (this == null) {
            return "";
        }
        return String.valueOf(colourChar);
    }



}
