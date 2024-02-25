package comp1110.ass2;

import java.util.Arrays;

public class Board {

    private Tile[][] surfaceTiles = new Tile[7][7];

    /**
     * Constructs a board from a boardString
     *
     * @param boardString
     */
    public Board(String boardString) {
        assert boardString.startsWith("B");
        String tiles = boardString.substring(1);
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                String abbreviatedRugString = tiles.substring(21 * x + 3 * y, 21 * x + 3 * (y + 1));
                Tile tile = new Tile(abbreviatedRugString);
                surfaceTiles[x][y] = tile;
            }
        }
    }

    /**
     * Constructs the starting board for the game
     */
    public Board() {
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                String abbreviatedRugString = "n00";
                Tile tile = new Tile(abbreviatedRugString);
                surfaceTiles[x][y] = tile;
            }
        }
    }

    public Tile[][] getSurfaceTiles() {
        return this.surfaceTiles;
    }

    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder();
        boardString.append("B");
        for (int x = 0; x < 7 ; x++) {
            for (int y = 0; y < 7; y++) {
                Tile tile = this.surfaceTiles[x][y];
                boardString.append(tile.toString());
            }
        }
        return boardString.toString();
    }

    public void placeTile(Rug rug) {
        IntPair head = rug.getHead();
        IntPair tail = rug.getTail();
        int id = rug.getId();
        Colour colour  = rug.getColour();
        Tile rugTile = new Tile(colour,id,true);
        this.surfaceTiles[head.getX()][head.getY()] = rugTile;
        this.surfaceTiles[tail.getX()][tail.getY()] = rugTile;
    }


    public int findLargestGroup(IntPair position) {
        Tile original = this.surfaceTiles[position.getX()][position.getY()];
        if (!original.isOccupied()) {
            return 0;
        } else {
            boolean[][] path = new boolean[7][7];
            for (int x = 0; x < 7 ; x++) {
                for (int y = 0; y < 7 ; y++) {
                    path[x][y] = false;
                }
            }
            path[position.getX()][position.getY()] = true;
            return dfs(path,position,original);
        }
    }


    private boolean isTileValid(boolean[][] pathSoFar, IntPair tilePos, Tile original) {
        // Not a valid spot on the board
        if (!tilePos.withinBoard()) {
            return false;
        }

        // If the other tile is not the same colour don't count it towards the group
        Tile other = this.surfaceTiles[tilePos.getX()][tilePos.getY()];
        if (other.getColour() != original.getColour()) {
            return false;
        }

        // Prevent double counting
        if (pathSoFar[tilePos.getX()][tilePos.getY()]) {
            return false;
        }

        return true;
    }


    // Search North South East West
    static final IntPair[] offsets = new IntPair[]{
            new IntPair(0,1),
            new IntPair(0,-1),
            new IntPair(1,0),
            new IntPair(-1,0)
    };


    private int dfs(boolean[][] pathSoFar, IntPair position, Tile original) {
        int count = 1; // Count the current position as part of the group

        for (IntPair offset : offsets) {
            IntPair translatedPosition = position.add(offset);
            if (isTileValid(pathSoFar, translatedPosition, original)) {
                pathSoFar[translatedPosition.getX()][translatedPosition.getY()] = true;
                count += dfs(pathSoFar,translatedPosition,original);
            }
        }

        return count;
    }
}


