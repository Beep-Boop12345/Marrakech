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
     * Constructor for a new assam object at the beginning of the game
     */
    public Assam() {
        this.position = new IntPair(3,3);
        this.facing = Direction.NORTH;
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
        // Attempt to translate assam based on how many moves and direction it is facing
        IntPair translated = this.position.translate(steps, this.facing);
        // If the translation is within the board it is accepted
        if (translated.withinBoard()) {
            this.position = translated;
        } else {
            /*If the attempted translation is not within the
             board than a shuffle needs to take place  */
            shuffle(steps);
        }
    }


    /**
     * If a translation requires using shuffling on the edges of the board, this method updates assam's direction
     * and IntPair based on the specifications
     */
    public void shuffle(int steps) {
        IntPair currentPosition = this.position;
        int offset = 0;
        IntPair shuffledStart;
        IntPair shuffled;
        switch (this.facing) {
            case NORTH:
                offset = steps - currentPosition.getY() - 1;
                switch (currentPosition.getX()) {
                    case 6:
                        shuffledStart = new IntPair(6,0);
                        shuffled = shuffledStart.translate(offset,Direction.WEST);
                        this.position = shuffled;
                        this.facing = Direction.WEST;
                        break;
                    default:
                        int startX = currentPosition.getX() % 2 == 0 ? currentPosition.getX() + 1 : currentPosition.getX() - 1;
                        shuffledStart = new IntPair(startX,0);
                        shuffled = shuffledStart.translate(offset,Direction.SOUTH);
                        this.position = shuffled;
                        this.facing = Direction.SOUTH;
                        break;
                }
                break;
            case SOUTH:
                offset = steps + currentPosition.getY() - 7;
                switch (currentPosition.getX()) {
                    case 0:
                        shuffledStart = new IntPair(0,6);
                        shuffled = shuffledStart.translate(offset,Direction.EAST);
                        this.position = shuffled;
                        this.facing = Direction.EAST;
                        break;
                    default:
                        int startX = currentPosition.getX() % 2 == 1 ? currentPosition.getX() + 1 : currentPosition.getX() - 1;
                        shuffledStart = new IntPair(startX, 6);
                        shuffled = shuffledStart.translate(offset,Direction.NORTH);
                        this.position = shuffled;
                        this.facing = Direction.NORTH;
                        break;
                }
                break;
            case EAST:
                offset = steps + currentPosition.getX() - 7;
                switch (currentPosition.getY()) {
                    case 0:
                        shuffledStart = new IntPair(6,0);
                        shuffled = shuffledStart.translate(offset,Direction.SOUTH);
                        this.position = shuffled;
                        this.facing = Direction.SOUTH;
                        break;
                    default:
                        int startY = currentPosition.getY() % 2 == 1 ? currentPosition.getY() + 1 : currentPosition.getY() - 1;
                        shuffledStart = new IntPair(6,startY);
                        shuffled = shuffledStart.translate(offset,Direction.WEST);
                        this.position = shuffled;
                        this.facing = Direction.WEST;
                break;
        }
                break;
            case WEST:
                offset = steps - currentPosition.getX() - 1 ;
                switch (currentPosition.getY()) {
                    case 6:
                        shuffledStart = new IntPair(0,6);
                        shuffled = shuffledStart.translate(offset,Direction.NORTH);
                        this.position = shuffled;
                        this.facing = Direction.NORTH;
                        break;
                    default:
                        int startY = currentPosition.getY() % 2 == 0 ? currentPosition.getY() + 1 : currentPosition.getY() - 1;
                        shuffledStart = new IntPair(0,startY);
                        shuffled = shuffledStart.translate(offset,Direction.EAST);
                        this.position = shuffled;
                        this.facing = Direction.EAST;
                        break;
                }
                break;
        }
    }

    public Boolean isAdjacent(IntPair other) {
        int assamX = this.position.getX();
        int assamY = this.position.getY();

        int xDistance = Math.abs(other.getX() - assamX);
        int yDistance = Math.abs(other.getY() - assamY);

        if (xDistance == 1 && yDistance == 1) {
            return false;
        } else if (xDistance == 1 || yDistance == 1) {
            return true;
        }

        return false;
    }

    public IntPair getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "A" + position.toString() + facing.toString();
    }

    public static void main(String[] args) {
        Assam assam = new Assam();
        assam.facing = Direction.EAST;
        assam.position = new IntPair(2,0);
        System.out.println(assam.toString());
    }

}


