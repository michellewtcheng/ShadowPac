import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

// base code is from the provided Project 1 Solutions by Tharun Dharmawickrema & Stella Li

/**
 * This is a class for the dot entities in the game
 */

public class Dot {
    private final Image DOT = new Image("res/dot.png");
    private final Point position;
    private boolean isActive;

    /**
     * This attribute is a constant for the point value of a dot
     */
    public final static int POINTS = 10;

    /**
     * Constructor method for Dot class
     */
    public Dot(int initialX, int initialY){
        this.isActive = true;
        this.position = new Point(initialX, initialY);
    }

    /**
     * Method that performs state update
     */
    public void update() {
        if (isActive){
            DOT.drawFromTopLeft(position.x, position.y);
        }
    }

    /**
     * Method that returns Rectangle object for the dot
     */
    public Rectangle getBoundingBox(){
        return new Rectangle(position, DOT.getWidth(), DOT.getHeight());
    }

    /**
     * Getter method for isActive variable
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Setter method for isActive variable
     */
    public void setActive(boolean active) {
        isActive = active;
    }

}
