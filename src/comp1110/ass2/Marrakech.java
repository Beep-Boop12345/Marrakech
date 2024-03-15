package comp1110.ass2;


import java.util.*;
import java.util.concurrent.ThreadLocalRandom;



public class Marrakech {
    private int currentTurn; // This is not 0'th indexed so player 3 would be 3 not 2
    private int numberOfPlayers;
    private Player[] currentPlayers;

    private boolean[] inGamePlayers;

    private Board board;

    private Assam assam;


    private static boolean isStateStringWellFormed(String gameState) {
        final int playerStringLength = 8;
        final int boardStringLength = 3 * 49;
        final int assamStringLength = 4;

        return gameState.length() >= assamStringLength + boardStringLength + 2 * playerStringLength;
    }

    private static String[] stateStringParser(String gameState) {
        final int playerStringLength = 8;
        final int boardStringLength = 3 * 49;
        final int assamStringLength = 4;


        // Extract player strings
        int numPlayers = (gameState.length() - boardStringLength - assamStringLength) / playerStringLength; // Assuming there are at least 2 players
        String[] gameStrings = new String[numPlayers+2];


        for (int i = 0; i < numPlayers; i++) {
            int startIndex = i * playerStringLength;
            int endIndex = startIndex + playerStringLength;
            gameStrings[i] = gameState.substring(startIndex, endIndex);
        }

        // Extract Assam string
        String assamString = gameState.substring(playerStringLength * numPlayers, playerStringLength * numPlayers + assamStringLength);
        gameStrings[numPlayers] = assamString;


        // Extract board string
        String boardString = gameState.substring(numPlayers * playerStringLength + assamStringLength);
        gameStrings[numPlayers+1] = boardString;

        return gameStrings;
    }

    public Marrakech(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        this.currentPlayers = new Player[numberOfPlayers];
        for (int i = 0; i < this.numberOfPlayers ; i++) {
            this.currentPlayers[i] = new Player(i);
        }
        this.inGamePlayers = new boolean[this.numberOfPlayers];
        for (int i = 0; i < this.numberOfPlayers ; i++) {
            inGamePlayers[i] = true;
        }
        this.assam = new Assam();
        this.board = new Board();
        this.currentTurn = 0;
    }

    public Marrakech(String gameState) {
        if (isStateStringWellFormed(gameState)) {
            String[] components = stateStringParser(gameState);
            this.numberOfPlayers = components.length - 2;
            this.currentPlayers = new Player[numberOfPlayers];
            for (int i = 0; i < numberOfPlayers; i++) {
                this.currentPlayers[i] = new Player(components[i]);
            }
            this.inGamePlayers = new boolean[numberOfPlayers];
            for (int i = 0; i < this.numberOfPlayers; i++) {
                inGamePlayers[i] = this.currentPlayers[i].isInGame();
            }
            this.assam = new Assam(components[numberOfPlayers]);
            this.board = new Board(components[numberOfPlayers+1]);
            findCurrentTurn();

        } else {
            throw new IllegalArgumentException("Invalid stateString");
        }
    }

    /**
     * Finds the current turn using logic from the rugCount.
     * The first player with the most rugs (in chronological order
     * from first to the number of players) is the one whose turn it is
     * provided they are still in the game.
     * Eg. If players 1 and 2 have 3 rugs and players 3 and 4 have 4
     *     rugs and all players are in the game it is player 3's turn.
     * If all players have the same rugCount than it is the first
     * player's turn.
     */
    private void findCurrentTurn() {
        // Calculate rugCounts for each player
        int[] rugCounts = new int[this.numberOfPlayers];
        for (int i = 0; i < this.numberOfPlayers; i++) {
            rugCounts[i] = this.currentPlayers[i].getRugCount();
        }

        /* Find the first player in chronological order with the highest rugCount
           If all players have the same rugCount defaults the first player        */
        int turnBasedOfRugs = 0;
        for (int i = 0; i < this.numberOfPlayers; i++) {
            if (rugCounts[i] > rugCounts[turnBasedOfRugs]) {
                turnBasedOfRugs = i;
                break;
            }
        }
        this.currentTurn = turnBasedOfRugs;

        // Account for players that are no longer in the game
        if (!this.inGamePlayers[this.currentTurn]) {
            cycleTurn();
        }

    }


    /**
     * Cycles the turn, use once a placement is made
     * isGameOver() is run before this function so the assumption is
     * made that game is not yet over.
     */
    public void cycleTurn() {
        // Shift the current turn once
        this.currentTurn = (this.currentTurn + 1) % this.numberOfPlayers;

        // Shift again accounting for players that aren't in the game
        while (!inGamePlayers[this.currentTurn]) {
            this.currentTurn = (this.currentTurn + 1) % this.numberOfPlayers;
        }
    }



    /**
     * Determine whether a rug String is valid.
     * For this method, you need to determine whether the rug String is valid, but do not need to determine whether it
     * can be placed on the board (you will determine that in Task 10 ). A rug is valid if and only if all the following
     * conditions apply:
     *  - The String is 7 characters long
     *  - The first character in the String corresponds to the colour character of a player present in the game
     *  - The next two characters represent a 2-digit ID number
     *  - The next 4 characters represent coordinates that are on the board
     *  - The combination of that ID number and colour is unique
     * To clarify this last point, if a rug has the same ID as a rug on the board, but a different colour to that rug,
     * then it may still be valid. Obviously multiple rugs are allowed to have the same colour as well so long as they
     * do not share an ID. So, if we already have the rug c013343 on the board, then we can have the following rugs
     *  - c023343 (Shares the colour but not the ID)
     *  - y013343 (Shares the ID but not the colour)
     * But you cannot have c014445, because this has the same colour and ID as a rug on the board already.
     * @param gameString A String representing the current state of the game as per the README
     * @param rugString A String representing the rug you are checking
     * @return true if the rug is valid, and false otherwise.
     */
    public static boolean isRugValid(String gameString, String rugString) {
        // Check if the rugString is well formatted
        if (!Rug.isRugStringWellFormed(rugString)) {
            return false;
        }
        Marrakech marrakech = new Marrakech(gameString);
        Rug rug = new Rug(rugString);
        return marrakech.isRugValid(rug);
    }

    public boolean isRugValid(Rug rug) {
        Board currentBoard = this.board;
        Tile[][] surfaceTiles = currentBoard.getSurfaceTiles();

        // If either of the rug positions aren't within board
        if (!rug.getHead().withinBoard() || !rug.getTail().withinBoard()) {
            return false;
        }

        int rugID = rug.getId();
        Colour rugColour = rug.getColour();

        // Check if that rug already exists within the current board
        for (int x = 0; x < 7 ; x++) {
            for (int y = 0; y < 7 ; y++) {
                Tile tile = surfaceTiles[x][y];
                if ((tile.getId() == rugID) && (tile.getColour() == rugColour)) {
                    return false;
                }
            }
        }

        return true;
    }



        /**
         * Roll the special Marrakech die and return the result.
         * Note that the die in Marrakech is not a regular 6-sided die, since there
         * are no faces that show 5 or 6, and instead 2 faces that show 2 and 3. That
         * is, of the 6 faces
         *  - One shows 1
         *  - Two show 2
         *  - Two show 3
         *  - One shows 4
         * As such, in order to get full marks for this task, you will need to implement
         * a die where the distribution of results from 1 to 4 is not even, with a 2 or 3
         * being twice as likely to be returned as a 1 or 4.
         * @return The result of the roll of the die meeting the criteria above
         */
    public static int rollDie() {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 7);
        int dieRoll;
        switch (randomNum) {
            case 1: dieRoll = 1; break;
            case 2: case 3: dieRoll = 2; break;
            case 4: case 5: dieRoll = 3; break;
            case 6: dieRoll = 4; break;
            default: return -1;
        }

        return dieRoll;
    }

    /**
     * Determine whether a game of Marrakech is over
     * Recall from the README that a game of Marrakech is over if a Player is about to enter the rotation phase of their
     * turn, but no longer has any rugs. Note that we do not encode in the game state String whose turn it is, so you
     * will have to think about how to use the information we do encode to determine whether a game is over or not.
     * @param currentGame A String representation of the current state of the game.
     * @return true if the game is over, or false otherwise.
     */
    public static boolean isGameOver(String currentGame) {
        Marrakech marrakech = new Marrakech(currentGame);
        return marrakech.isGameOver();
    }

    public boolean isGameOver() {
        Player player = currentPlayers[this.currentTurn];
        return player.getRugCount() == 0;
    }

    /**
     * Implement Assam's rotation.
     * Recall that Assam may only be rotated left or right, or left alone -- he cannot be rotated a full 180 degrees.
     * For example, if he is currently facing North (towards the top of the board), then he could be rotated to face
     * East or West, but not South. Assam can also only be rotated in 90 degree increments.
     * If the requested rotation is illegal, you should return Assam's current state unchanged.
     * @param currentAssam A String representing Assam's current state
     * @param rotation The requested rotation, in degrees. This degree reading is relative to the direction Assam
     *                 is currently facing, so a value of 0 for this argument will keep Assam facing in his
     *                 current orientation, 90 would be turning him to the right, etc.
     * @return A String representing Assam's state after the rotation, or the input currentAssam if the requested
     * rotation is illegal.
     */
    public static String rotateAssam(String currentAssam, int rotation) {
        if (!Assam.isAssamRotationValid(currentAssam,rotation)) {
            return currentAssam;
        }
        Assam assam = new Assam(currentAssam);
        assam.rotateAssam(rotation);
        return assam.toString();
    }

    /**
     * Determine whether a potential new placement is valid (i.e that it describes a legal way to place a rug).
     * There are a number of rules which apply to potential new placements, which are detailed in the README but to
     * reiterate here:
     *   1. A new rug must have one edge adjacent to Assam (not counting diagonals)
     *   2. A new rug must not completely cover another rug. It is legal to partially cover an already placed rug, but
     *      the new rug must not cover the entirety of another rug that's already on the board.
     * @param gameState A game string representing the current state of the game
     * @param rug A rug string representing the candidate rug which you must check the validity of.
     * @return true if the placement is valid, and false otherwise.
     */
    public static boolean isPlacementValid(String gameState, String rug) {
        Marrakech marrakech = new Marrakech(gameState);
        Rug rugMove = new Rug(rug);
        return marrakech.isPlacementValid(rugMove);
    }

    public boolean isPlacementValid(Rug rug) {
        if (!isRugValid(rug)) {
            return false;
        }
        Board currentBoard = this.board;
        Tile[][] surfaceTiles = currentBoard.getSurfaceTiles();
        Assam assam = this.assam;
        IntPair head = rug.getHead();
        IntPair tail = rug.getTail();

        // 1. A new rug must have one edge adjacent to Assam (not counting diagonals)
        if (!(assam.isAdjacent(head) || assam.isAdjacent(tail))) {
            return false;
        }

        // 2. A new rug must not completely cover another rug.
        Tile headTile = surfaceTiles[head.getX()][head.getY()];
        Tile tailTile = surfaceTiles[tail.getX()][tail.getY()];

        if (headTile.isOccupied() || tailTile.isOccupied()) {
            if ((headTile.getId() == tailTile.getId()) && (headTile.getColour() == tailTile.getColour())) {
                return false;
            }
        }


        // 3. A rug cannnot overlap with assam's position
        IntPair assamPos = assam.getPosition();
        if (assamPos.equals(head) || assamPos.equals(tail)) {
            return false;
        }

        return true;
    }




    /**
     * Determine the amount of payment required should another player land on a square.
     * For this method, you may assume that Assam has just landed on the square he is currently placed on, and that
     * the player who last moved Assam is not the player who owns the rug landed on (if there is a rug on his current
     * square). Recall that the payment owed to the owner of the rug is equal to the number of connected squares showing
     * on the board that are of that colour. Similarly to the placement rules, two squares are only connected if they
     * share an entire edge -- diagonals do not count.
     * @param gameString A String representation of the current state of the game.
     * @return The amount of payment due, as an integer.
     */
    public static int getPaymentAmount(String gameString) {
        Marrakech marrakech = new Marrakech(gameString);
        return marrakech.getPaymentAmount();
    }

    private int getPaymentAmount() {
        IntPair assamPos = this.assam.getPosition();
        return this.board.findLargestGroup(assamPos);
    }


    /**
     * Updates the marrakech object to correctly represent the payment after assam lands on a rug.
     * This method is used after a rug is placed and assam is moved, and it updates the player information
     * to correctly represent payment. Finally, the method also cycles turn to indicate it is the next player's turn.
     */
    public void makePayment() {
        IntPair assamPosition = this.assam.getPosition();
        Tile assamTile = this.board.getSurfaceTiles()[assamPosition.getX()][assamPosition.getY()];
        Player playerToPay = null;
        for (Player player : this.currentPlayers) {
            if (player.getColour() == assamTile.getColour()) {
                playerToPay = player;
                break;
            }
        }
        int paymentAmount = getPaymentAmount();
        Player playerToMakePayment = this.currentPlayers[currentTurn];
        if (playerToPay != null) {
            playerToMakePayment.makePayment(paymentAmount,playerToPay);
        }

    }


    /**
     * Determine the winner of a game of Marrakech.
     * For this task, you will be provided with a game state string and have to return a char representing the colour
     * of the winner of the game. So for example if the cyan player is the winner, then you return 'c', if the red
     * player is the winner return 'r', etc...
     * If the game is not yet over, then you should return 'n'.
     * If the game is over, but is a tie, then you should return 't'.
     * Recall that a player's total score is the sum of their number of dirhams and the number of squares showing on the
     * board that are of their colour, and that a player who is out of the game cannot win. If multiple players have the
     * same total score, the player with the largest number of dirhams wins. If multiple players have the same total
     * score and number of dirhams, then the game is a tie.
     * @param gameState A String representation of the current state of the game
     * @return A char representing the winner of the game as described above.
     */
    public static char getWinner(String gameState) {
        Marrakech marrakech = new Marrakech(gameState);
        return marrakech.getWinner();
    }

    public char getWinner() {
        // If game isn't over
        if (!this.isGameOver()) {
            return 'n';
        }


        // Calculate each player scores and the highest score
        calculateScores();
        int highestScore = 0;

        int[] playerScores = new int[this.numberOfPlayers];
        for (int i = 0; i < this.numberOfPlayers; i++) {
            playerScores[i] = this.currentPlayers[i].getScore();
            if ((playerScores[i] > highestScore) && inGamePlayers[i]) {
                highestScore = playerScores[i];
            }
        }

        // Find players with the highest scores
        List<Player> playersWithHighestScore = new ArrayList<>();
        for (int i = 0; i < this.numberOfPlayers; i++) {
            if (inGamePlayers[i]) {
                Player player = this.currentPlayers[i];
                if (playerScores[i] == highestScore) {
                    playersWithHighestScore.add(player);
                }
            }
        }

        // Calculate the highest dirhams amongst the highest scoring players
        int highestDirhamsAmongHighestScore = 0;
        for (Player player : playersWithHighestScore) {
            if (player.getDirhams() > highestDirhamsAmongHighestScore) {
                highestDirhamsAmongHighestScore = player.getDirhams();
            }
        }


        List<Player> winners = new ArrayList<>();
        for (Player player : playersWithHighestScore) {
            if (player.getDirhams() == highestDirhamsAmongHighestScore) {
                winners.add(player);
            }
        }

        if (winners.size() > 1) {
            return 't';
        } else {
            Colour winnerCol = winners.get(0).getColour();
            return winnerCol.toString().charAt(0);
        }
    }

    public void calculateScores() {
        for (Player player : this.currentPlayers) {
            player.calculateScore(this.board);
        }
    }



    /**
     * Implement Assam's movement.
     * Assam moves a number of squares equal to the die result, provided to you by the argument dieResult. Assam moves
     * in the direction he is currently facing. If part of Assam's movement results in him leaving the board, he moves
     * according to the tracks diagrammed in the assignment README, which should be studied carefully before attempting
     * this task. For this task, you are not required to do any checking that the die result is sensible, nor whether
     * the current Assam string is sensible either -- you may assume that both of these are valid.
     * @param currentAssam A string representation of Assam's current state.
     * @param dieResult The result of the die, which determines the number of squares Assam will move.
     * @return A String representing Assam's state after the movement.
     */
    public static String moveAssam(String currentAssam, int dieResult){
        Assam assam = new Assam(currentAssam);
        assam.moveAssam(dieResult);
        return assam.toString();
    }

    /**
     * Place a rug on the board
     * This method can be assumed to be called after Assam has been rotated and moved, i.e in the placement phase of
     * a turn. A rug may only be placed if it meets the conditions listed in the isPlacementValid task. If the rug
     * placement is valid, then you should return a new game string representing the board after the placement has
     * been completed. If the placement is invalid, then you should return the existing game unchanged.
     * @param currentGame A String representation of the current state of the game.
     * @param rugString A String representation of the rug that is to be placed.
     * @return A new game string representing the game following the successful placement of this rug if it is valid,
     * or the input currentGame unchanged otherwise.
     */
    public static String makePlacement(String currentGame, String rugString) {
        if (isRugValid(currentGame,rugString) && isPlacementValid(currentGame,rugString)) {
            Marrakech marrakech = new Marrakech(currentGame);
            Rug rug = new Rug(rugString);
            marrakech.makePlacement(rug);
            return marrakech.toString();
        } else {
            return currentGame;
        }
    }




    public void makePlacement(Rug rug) {
        if (isRugValid(rug) && isPlacementValid(rug)) {
            this.board.placeTile(rug);
            Colour rugColour = rug.getColour();
            for (Player player : this.currentPlayers) {
                Colour playerColour = player.getColour();
                if (rugColour.equals(playerColour)) {
                    player.updateRugCount();
                    break;
                }
            }
        }
    }



    @Override
    public String toString() {
       StringBuilder stateString = new StringBuilder();
       for (Player player : this.currentPlayers) {
           stateString.append(player.toString());
       }
       stateString.append(this.assam.toString());
       stateString.append(this.board.toString());
       return stateString.toString();
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public Player[] getCurrentPlayers() {
        return currentPlayers;
    }


    public Board getBoard() {
        return board;
    }

    public Assam getAssam() {
        return assam;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }


}
