import bagel.Image;
import bagel.util.*;
import java.util.Random;

/**
 * This is a class for the moving Green Ghost in Level 1
 */

public class GreenGhost extends Ghost{
    private final Image GREEN_GHOST = new Image("res/ghostGreen.png");
    private static final double NORMAL_SPEED = 4;
    private Random random = new Random();
    private final int randomDirection; // stores the green ghosts chosen movement (horizontal/vertical) for the game
    private static final int DIRECTION_OPTIONS = 2;
    private static final int HORIZONTAL = 0; // outcome of random generator for horizontal movement
    private static final int VERTICAL = 1; // outcome of random generator for vertical movement

    /**
     * Constructor method for the GreenGhost class
     */
    public GreenGhost(int initialX, int initialY) {
        super(initialX, initialY);
        setSpeed(NORMAL_SPEED);
        this.randomDirection = random.nextInt(DIRECTION_OPTIONS);
    }

    /**
     * Method that performs state update for green ghost
     */
    @Override
    public void update(ShadowPac gameObject) {
        if(isActive()) {
            if (getIsFrenzy()) {

                // draw frenzy ghost
                FRENZY_GHOST.drawFromTopLeft(super.getPosition().x, super.getPosition().y);
            } else {

                // normal mode ghost
                GREEN_GHOST.drawFromTopLeft(super.getPosition().x, super.getPosition().y);
            }

            setSpeed(NORMAL_SPEED);
            gameObject.checkGhostWallCollisions(this);

            // green ghost will move in the randomly selected direction that was chosen when it was created
            if (randomDirection == VERTICAL){
                move(0, getSpeed()*getDirection());
            } else if (randomDirection == HORIZONTAL){
                move(getSpeed()*getDirection(), 0);
            }
        }
    }

    /**
     * Method that returns a Rectangle object for the green ghost
     */
    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(super.getPosition(), GREEN_GHOST.getWidth(), GREEN_GHOST.getHeight());
    }
}
