package comp1110.ass2;

/**
 * This class is used to denote a Rug move that is attempted to be played
 */
public class Rug {

    private IntPair tail;
    private IntPair head;

    private int id;

    private Colour colour;

    /**
     * Creates a 'Rug' move from a RugString
     * eg. p014445 Purple, Id: 1, Tail: (4,4), Head (4,5);
     * @param rugString
     */
    public Rug(String rugString) {
        char colourChar = rugString.charAt(0);
        this.colour = Colour.getColour(colourChar);
        this.id = Integer.parseInt(rugString.substring(1,3)); // Range [1,3) i.e [1,2]
        this.tail = new IntPair(rugString.substring(3,5));
        this.head = new IntPair(rugString.substring(5,7));
    }

}
