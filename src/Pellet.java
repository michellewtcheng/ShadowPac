import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;


// the structure of the below code borrows from the Project 1 Sample Solution provided by teaching team

/**
 * This is a class for the pellet entity in Level 1 of the game
 */

public class Pellet {
    private final Image PELLET = new Image("res/pellet.png");
    private final Point position;
    private boolean isActive;

    /**
     * Constructor for Pellet class
     */
    public Pellet(int initialX, int initialY){
        this.isActive = true;
        this.position = new Point(initialX, initialY);
    }

    /**
     * Method that performs state update
     */
    public void update(){
        if(isActive){
            PELLET.drawFromTopLeft(position.x, position.y);
        }
    }

    /**
     * Method that returns a Rectangle object for the Pellet
     */
    public Rectangle getBoundingBox() {
        return new Rectangle(position, PELLET.getWidth(), PELLET.getHeight());
    }

    /**
     * Getter method for the isActive variable
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
