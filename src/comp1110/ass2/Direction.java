package comp1110.ass2;

public enum Direction {
    NORTH(0),
    SOUTH(180),
    EAST(90),
    WEST(270);

    private final int value;
    Direction(int angle) {
        this.value = angle;
    }

    /**
     * Convert an integer value back into a Direction
     *
     * @param angle in degrees
     * @return rotation
     */
    public static Direction getDirection(int angle) {
        if (angle < 0) {
            return getDirection(angle + 360);
        }
        angle = angle % 360;
        for (Direction direction : Direction.values()) {
            if (direction.getValue() == angle) {
                return direction;
            }
        }
        return null;
    }


    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        switch (this) {
            case NORTH:
                return "N";
            case SOUTH:
                return "S";
            case EAST:
                return "E";
            case WEST:
                return "W";
            default:
                return "";
        }
    }
}
