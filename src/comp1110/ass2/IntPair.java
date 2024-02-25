package comp1110.ass2;

import java.util.Objects;

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

    public IntPair translate(int steps, Direction direction) {
        switch (direction) {
            case NORTH -> { return new IntPair(this.x,this.y - steps); }
            case SOUTH -> { return new IntPair(this.x,this.y + steps); }
            case EAST -> { return new IntPair(this.x + steps,this.y); }
            case WEST -> { return new IntPair(this.x - steps, this.y); }
            default -> throw new IllegalArgumentException("This is not a valid direction");
        }
    }

    public IntPair add(IntPair other) {
        int sumX = this.x + other.getX();
        int sumY = this.y + other.getY();
        return new IntPair(sumX, sumY);
    }


    public boolean withinBoard() {
        if (this.x < 0 || this.x > 6) {
            return false;
        }

        if (this.y < 0 || this.y > 6) {
            return false;
        }

        return true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.valueOf(x) + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntPair intPair = (IntPair) o;
        return x == intPair.x && y == intPair.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
