package comp1110.ass2;

public class Player {

    private int dirhams;

    private int score;

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

    /**
     * Constructor for a player object at the start of the game
     */

    public Player(int n) {
        switch (n) {
            case 0: this.colour = Colour.Cyan; break;
            case 1: this.colour = Colour.Yellow; break;
            case 2: this.colour = Colour.Red; break;
            case 3: this.colour = Colour.Purple; break;
            default: throw new IllegalArgumentException("There is a maximum of 4 players");
        }
        this.dirhams = 30;
        this.rugCount = 15;
        this.inGame = true;
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

    public int getDirhams() {
        return dirhams;
    }


    public int getRugCount() {
        return rugCount;
    }

    /**
     * Method to update rugCount after a rug is placed by that player
     * automatically updates inGame status based on rugCount
     */
    public void updateRugCount() {
        this.rugCount--;
        if (rugCount == 0) {
            this.rugCount = 0;
            this.inGame = false;
        }
    }

    public boolean isInGame() {
        return inGame;
    }

    public void calculateScore(Board board) {
        Tile[][] surfaceTiles = board.getSurfaceTiles();
        int surfaceTilesForPlayer = 0;
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                Tile tile = surfaceTiles[x][y];

                if (tile.isOccupied()) {
                    if (tile.getColour().equals(this.colour)) {
                        surfaceTilesForPlayer++;
                    }
                }
            }
        }
        this.score = surfaceTilesForPlayer + this.dirhams;
    }

    public int getScore() {
        return score;
    }

    public Colour getColour() {
        return colour;
    }

    @Override
    public String toString() {
        StringBuilder playerString = new StringBuilder();
        playerString.append("P");
        playerString.append(this.colour.toString());

        String dirhamString = String.format("%03d", this.dirhams);
        playerString.append(dirhamString);

        String rugCountString = String.format("%02d", this.rugCount);
        playerString.append(rugCountString);

        String inGameString = this.inGame ? "i" : "o";
        playerString.append(inGameString);

        return playerString.toString();
    }


}
