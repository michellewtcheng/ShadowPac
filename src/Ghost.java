import bagel.Image;
import bagel.util.Rectangle;

// Some code from Project 1 Sample solution provided by teaching team (Tharun Dharmawickrema & Stella Li)

/**
 * This is a class for the stationary Red Ghost in Level 0 and the parent class
 * for all 4 moving ghosts in Level 1
 */

public class Ghost extends MovingGameEntity{
    private final Image GHOST = new Image("res/ghostRed.png");
    private boolean isFrenzy;
    private boolean isActive;
    private boolean hitWall;
    private int direction = 1;
    private double speed;
    private final static int DIRECTION_REVERSE = -1; // used to reverse ghost's direction when it collides with a wall
    private final static double FRENZY_SPEED_OFFSET = 0.5;

    /**
     * This attribute is a constant for the frenzy-mode ghost image
     */
    public final Image FRENZY_GHOST = new Image("res/ghostFrenzy.png");

    /**
     * This attribute is a constant for the amount of points a ghost is worth when eaten during frenzy
     */
    public final static int GHOST_SCORE = 30;


    /**
     * Constructor method for Ghost class
     */
    public Ghost(int initialX, int initialY){
        super(initialX, initialY);
        this.isFrenzy = false;
        this.isActive = true;
        this.hitWall = false;
    }

    /**
     * Method that performs state update
     */
    public void update(ShadowPac gameObject) {
        GHOST.drawFromTopLeft(getPosition().x, getPosition().y);
    }

    /**
     * Method where ghost will reverse direction after hitting a wall (overridden in pink -- get rid of this later)
     */
    public void afterHitWall(){
        direction *= DIRECTION_REVERSE;
        hitWall = false;
    }

    /**
     * Method that sets the ghost's movement direction back to it's original starting direction
     */
    public void resetDirection(){
        direction = Math.abs(direction);
    }

    /**
     * Method that returns a Rectangle object for the ghost
     */
    public Rectangle getBoundingBox(){
        return new Rectangle(getPosition(), GHOST.getWidth(), GHOST.getHeight());
    }

    /**
     * Getter method for isFrenzy variable -- only utilised in Level 1
     */
    public boolean getIsFrenzy(){
        return isFrenzy;
    }

    /**
     * Setter method for isFrenzy variable -- only utilised in Level 1
     */
    public void setIsFrenzy(boolean frenzy) {
        isFrenzy = frenzy;
    }

    /**
     * Getter method for isActive attribute -- only utilised in Level 1
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Setter method for isActive attribute -- only utilised in Level 1
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * Setter method for hitWall attribute -- only utilised in Level 1
     */
    public void setHitWall(boolean hitWall) {
        this.hitWall = hitWall;
    }

    /**
     * Getter method for direction attribute -- only utilised in Level 1
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Getter method for speed attribute -- only used in Level 1
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Setter method for speed attribute, according to whether it's frenzy or not frenzy -- only Level 1
     */
    public void setSpeed(double speed) {
        if (isFrenzy){
            this.speed = speed - FRENZY_SPEED_OFFSET;
        } else {
            this.speed = speed;
        }
    }
}