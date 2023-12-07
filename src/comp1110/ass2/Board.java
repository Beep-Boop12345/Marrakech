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
}


