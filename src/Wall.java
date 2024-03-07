import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

// base code is from the provided Project 1 Solutions by Tharun Dharmawickrema & Stella Li

/**
 * This is a class for the wall entities in the game
 */

public class Wall {
    private final Image WALL = new Image("res/wall.png");
    private final Point position;

    /**
     * Constructor method for Wall class
     */
    public Wall(int initialX, int initialY){
        this.position = new Point(initialX, initialY);
    }

    /**
     * Method that performs state update
     */
    public void update() {
        WALL.drawFromTopLeft(this.position.x, this.position.y);
    }

    /**
     * Method that returns Rectangle object for the wall
     */
    public Rectangle getBoundingBox(){
        return new Rectangle(position, WALL.getWidth(), WALL.getHeight());
    }
}
