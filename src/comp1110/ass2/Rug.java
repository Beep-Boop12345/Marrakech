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
     * @param rugString String representation of a rug move.
     */
    public Rug(String rugString) {
        char colourChar = rugString.charAt(0);
        this.colour = Colour.getColour(colourChar);
        this.id = Integer.parseInt(rugString.substring(1,3)); // Range [1,3) i.e [1,2]
        this.tail = new IntPair(rugString.substring(3,5));
        this.head = new IntPair(rugString.substring(5,7));
    }

    /**
     * Creates a 'Rug' from the closest square position, a boolean checking
     * if the rug is vertical and the colour of the rug to place.
     */
    public Rug(IntPair head, boolean isVertical, Player player) {
        this.head = head;
        if (isVertical) {
            this.tail = new IntPair(head.getX(), head.getY()+1);
        } else {
            this.tail = new IntPair(head.getX()+1,head.getY());
        }
        this.colour = player.getColour();
        this.id = player.getRugCount();
    }

    /**
     * Check if a rugString is well-formed in terms of formatting
     * 1. Starts with a colour char
     * 2. The rest of the string are numbers that can be parsed
     * @return true if its well formatted
     */
    public static boolean isRugStringWellFormed(String rugString) {
        if (rugString.length() != 7) {
            return false;
        }

        char colourChar = rugString.charAt(0);
        if (colourChar != 'c' && colourChar != 'y' && colourChar != 'r' && colourChar != 'p') {
            return false;
        }

        String numbersFromRug = rugString.substring(1);
        for (int i = 0; i < numbersFromRug.length(); i++) {
            char num = numbersFromRug.charAt(i);
            if (!Character.isDigit(num)) {
                return false;
            }
        }

        return true;
    }

    public IntPair getTail() {
        return tail;
    }

    public IntPair getHead() {
        return head;
    }

    public int getId() {
        return id;
    }

    public Colour getColour() {
        return colour;
    }
}
