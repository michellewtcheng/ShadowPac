import bagel.Image;
import bagel.util.*;

/**
 * This is a class for the moving Red Ghost in Level 1
 */

public class RedGhost extends Ghost{
    private final Image RED_GHOST = new Image("res/ghostRed.png");
    private static final double NORMAL_SPEED = 1;

    /**
     * Constructor method for RedGhost class
     */
    public RedGhost(int initialX, int initialY) {
        super(initialX, initialY);
        setSpeed(NORMAL_SPEED);
    }

    /**
     * Method that performs state update for red ghost
     */
    @Override
    public void update(ShadowPac gameObject) {
        if(isActive()) {

            if (getIsFrenzy()) {

                // draw frenzy ghost
                FRENZY_GHOST.drawFromTopLeft(super.getPosition().x, super.getPosition().y);
            } else {

                // normal mode ghost
                RED_GHOST.drawFromTopLeft(super.getPosition().x, super.getPosition().y);
            }

            setSpeed(NORMAL_SPEED);
            gameObject.checkGhostWallCollisions(this);

            // ghost moves vertically and changes direction when prompted
            move(getSpeed()*getDirection(), 0);
        }
    }

    /**
     * Method that returns Rectangle object for red ghost
     */
    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(super.getPosition(), RED_GHOST.getWidth(), RED_GHOST.getHeight());
    }
}
