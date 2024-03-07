import bagel.Image;
import bagel.util.*;

/**
 * This is a class for the moving Blue Ghost in Level 1
 */

public class BlueGhost extends Ghost{
    private final Image BLUE_GHOST = new Image("res/ghostBlue.png");
    private static final double NORMAL_SPEED = 2;

    /**
     * Constructor method for Blue Ghost class
     */
    public BlueGhost(int initialX, int initialY) {
        super(initialX, initialY);
       setSpeed(NORMAL_SPEED);
    }

    /**
     * Method that performs state update for Blue Ghost
     */
    @Override
    public void update(ShadowPac gameObject){
        if(isActive()) {

            if (getIsFrenzy()) {

                // draw frenzy ghost
                FRENZY_GHOST.drawFromTopLeft(super.getPosition().x, super.getPosition().y);
            } else {

                // normal mode ghost
                BLUE_GHOST.drawFromTopLeft(super.getPosition().x, super.getPosition().y);
            }

            setSpeed(NORMAL_SPEED);
            gameObject.checkGhostWallCollisions(this);

            // ghost moves horizontally and changes direction when prompted
            move(0, getSpeed()*getDirection());
        }
    }

    /**
     * Method that returns rectangle object for blue ghost
     */
    @Override
    public Rectangle getBoundingBox(){
        return new Rectangle(super.getPosition(), BLUE_GHOST.getWidth(), BLUE_GHOST.getHeight());
    }
}
