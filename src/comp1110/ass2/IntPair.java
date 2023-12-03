package comp1110.ass2;

public class IntPair {
    private int x;
    private int y;

    public IntPair(int x, int y) {
        this.x = x;
        this.y = y;
    }


    // A string of length 2
    public IntPair(String coordinate) {
        this.x = Integer.parseInt(coordinate.substring(0,1));
        this.y = Integer.parseInt(coordinate.substring(1,2));
    }

    @Override
    public String toString() {
        return x + String.valueOf(y);
    }
}
