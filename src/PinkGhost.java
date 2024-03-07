import bagel.Image;
import bagel.util.*;
import java.util.Random;

/**
 * This is a class for the moving Pink Ghost in Level 1
 */

public class PinkGhost extends Ghost{
    private final Image PINK_GHOST = new Image("res/ghostPink.png");
    private static final double NORMAL_SPEED = 3;
    private Random random = new Random();
    private int randomDirection; // keeps track of what direction the pink ghost is currently moving in
    private static final int DIRECTION_OPTIONS = 4;
    private static final int LEFT = 0; // outcome of random generator for left movement
    private static final int RIGHT = 1; // outcome of random generator for right movement
    private static final int UP = 2; // outcome of random generator for up movement
    private static final int DOWN = 3; // outcome of random generator for down movement

    /**
     * Constructor method for Pink Ghost class
     */
    public PinkGhost(int initialX, int initialY) {
        super(initialX, initialY);
        setSpeed(NORMAL_SPEED);
        this.randomDirection = random.nextInt(DIRECTION_OPTIONS);
    }

    /**
     * Method that performs state update for Pink Ghost
     */
    @Override
    public void update(ShadowPac gameObject) {
        if (isActive()) {
            if (getIsFrenzy()) {

                // draw frenzy ghost
                FRENZY_GHOST.drawFromTopLeft(super.getPosition().x, super.getPosition().y);
            } else {

                // normal mode ghost
                PINK_GHOST.drawFromTopLeft(super.getPosition().x, super.getPosition().y);
            }

            setSpeed(NORMAL_SPEED);
            gameObject.checkGhostWallCollisions(this);

            // pink ghost will move in whichever random direction that gets generated for it
            if (randomDirection == LEFT){
                move(-getSpeed(), 0);
            } else if (randomDirection == RIGHT){
                move(getSpeed(), 0);
            } else if (randomDirection == UP){
                move(0, -getSpeed());
            } else if (randomDirection == DOWN){
                move(0, getSpeed());
            }
        }
    }

    /**
     * Method that returns a Rectangle object for the pink ghost
     */
    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(super.getPosition(), PINK_GHOST.getWidth(), PINK_GHOST.getHeight());
    }

    /**
     * Method that changes the direction the pink ghost moves in when it hits a wall
     */
    @Override
    public void afterHitWall(){
        //picks another random direction out of the four (up, down, left, right)
        randomDirection = random.nextInt(DIRECTION_OPTIONS);
        setHitWall(false);
    }
}
