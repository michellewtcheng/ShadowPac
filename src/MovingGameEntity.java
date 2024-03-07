import bagel.util.Point;

/**
 * This is an abstract class for Game Entities that move in the game
 */

public abstract class MovingGameEntity {
    private Point position;
    private final Point startingPosition;
    private Point prevPosition;

    /**
     * Constructor for MovingGameEntity abstract class
     */
    public MovingGameEntity(int initialX, int initialY){
        this.position = new Point(initialX, initialY);
        this.startingPosition = position;
    }

    /**
     * Method to move the entity
     */
    public void move(double xMove, double yMove){
        prevPosition = position;
        position = new Point(position.x + xMove, position.y + yMove);
    }

    /**
     * Method to reset the moving entity to go back to its original starting position
     */
    public void resetPosition(){
        position = startingPosition;
    }

    /**
     * Method that moves entity back after colliding with a wall
     */
    public void moveBack(){
        position = prevPosition;
    }

    /**
     * Method to get the private position attribute
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Method to set the private position attribute
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * Getter method for startingPosition attribute
     */
    public Point getStartingPosition() {
        return startingPosition;
    }
}
