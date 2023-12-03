package comp1110.ass2;

public class Player {

    private int dirhams;


    private int rugCount;

    private boolean inGame;

    private Colour colour;


    // Something to store colour probably an enum

    /**
     * Constructor for a player object given a player string
     * eg. Pr00803i, Colour Red, 8 Dirhams, 3 Rugs remaining
     * @param playerString
     */
    public Player (String playerString) {
        assert isPlayerStringValid(playerString);
        char colourChar = playerString.charAt(1);
        this.colour = Colour.getColour(colourChar);
        this.dirhams = Integer.parseInt(playerString.substring(2,5)); // Range [2,5)
        this.rugCount = Integer.parseInt(playerString.substring(5,7)); // Range [5,7)
        this.inGame = playerString.charAt(7) == 'i';
    }

    private static boolean isPlayerStringValid(String playerString) {
        // Check if the playerString has the correct length
        if (playerString.length() != 8) {
            return false;
        }

        // Check if the playerString starts with 'P'
        if (playerString.charAt(0) != 'P') {
            return false;
        }

        // Check if the color is valid
        char colorChar = playerString.charAt(1);
        if (Colour.getColour(colorChar) == null) {
            return false;
        }

        // Check if dirhams is a 3-digit number
        String dirhamsStr = playerString.substring(2, 5);
        try {
            int dirhams = Integer.parseInt(dirhamsStr);
            if (dirhams < 0 || dirhams > 999) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        // Check if rugs remaining is a 2-digit number
        String rugsRemainingStr = playerString.substring(5, 7);
        try {
            int rugsRemaining = Integer.parseInt(rugsRemainingStr);
            if (rugsRemaining < 0 || rugsRemaining > 99) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        // Check if the last character is 'i' or 'o'
        char inGameChar = playerString.charAt(7);
        if (inGameChar != 'i' && inGameChar != 'o') {
            return false;
        }

        return true;
    }

}
