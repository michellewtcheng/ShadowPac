import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

// the structure of the below code borrows from the Project 1 Sample Solution provided by teaching team

/**
 * This is a class for the Cherry entities in Level 1
 */

public class Cherry {
    private final Image CHERRY = new Image("res/cherry.png");
    private final Point position;
    private boolean isActive;

    /**
     * This attribute is a constant for the point value of a cherry
     */
    public final static int CHERRY_SCORE = 20;

    /**
     * Constructor method for Cherry class
     */
    public Cherry(int initialX, int initialY){
        this.isActive = true;
        this.position = new Point(initialX, initialY);
    }

    /**
     * Method that performs state update
     */
    public void update(){
        if(isActive){
            CHERRY.drawFromTopLeft(position.x, position.y);
        }
    }

    /**
     * Method that returns a Rectangle object for the cherry object
     */
    public Rectangle getBoundingBox() {
        return new Rectangle(position, CHERRY.getWidth(), CHERRY.getHeight());
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
    public void setActive(boolean active){
        isActive = active;
    }
}
