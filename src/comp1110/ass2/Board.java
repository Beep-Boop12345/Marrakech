package comp1110.ass2;

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

}


