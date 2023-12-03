package comp1110.ass2;


public class Assam {
    private Direction facing;

    private IntPair position;


    /**
     * Checks if an assamString is well formed eg. A23N
     * @param assamString
     * @return boolean
     */
    public static boolean isAssamStringWellFormed(String assamString) {
        // Early exit for length check and "A" prefix
        if (assamString.length() != 4 || assamString.charAt(0) != 'A') {
            return false;
        }

        // Check digits in the range [0, 6]
        for (int i = 1; i <= 2; i++) {
            if (!Character.isDigit(assamString.charAt(i))) {
                return false;
            }

            // Check if coordinate is in the range [0, 6]
            int coordinate = Character.getNumericValue(assamString.charAt(i));
            if (coordinate < 0 || coordinate > 6) {
                return false;
            }
        }

        // Check direction
        char direction = assamString.charAt(3);
        switch (direction) {
            case 'N': case 'S': case 'E': case 'W':
                return true;
            default:
                return false;
        }
    }
    public Assam(String assamString) {
        if (isAssamStringWellFormed(assamString)) {
            int x = Integer.parseInt(assamString.substring(1,2));
            int y = Integer.parseInt(assamString.substring(2,3));
            this.position = new IntPair(x,y);
            char direction = assamString.charAt(3);
            switch (direction) {
                case 'N': this.facing = Direction.NORTH; break;
                case 'S': this.facing = Direction.SOUTH; break;
                case 'E': this.facing = Direction.EAST; break;
                case 'W': this.facing = Direction.WEST; break;
            }
        } else {
            throw new IllegalArgumentException("Invalid assamString");
        }

    }

    /**
     * Checks if a given rotation is valid.
     * A rotation is valid if it is a 90 degree rotation left or right but not a 180 degree rotation.
     * @param assamString the current Assam its position and rotation
     * @param direction the specified rotation of the new assam
     * @return a boolean if its valid
     */

    public static boolean isAssamRotationValid(String assamString, int direction) {
        if (!isAssamStringWellFormed(assamString)) {
            return false;
        }
        Assam assam = new Assam(assamString);
        Direction currentDirection = assam.facing;

        int newAngle = currentDirection.getValue() + direction;

        Direction transformedDirection = Direction.getDirection(newAngle);
        if (transformedDirection == null) {
            return false;
        }

        int angleDifference = Math.abs(transformedDirection.getValue() - currentDirection.getValue());
        return angleDifference != 180;
    }


    public Direction getDirection() {
        return facing;
    }

    public void setDirection(Direction direction) {
        this.facing = direction;
    }

    public void moveAssam(int steps) {

    }


    @Override
    public String toString() {
        return "A" + position.toString() + facing.toString();
    }

}


