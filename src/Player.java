import bagel.*;

// code builds on provided Project 1 Solutions by Tharun Dharmawickrema & Stella Li

/**
 * This is a class for the PacMan player
 */

public class Player extends MovingGameEntity {
    private final static String PAC = "res/pac.png";
    private final static String PAC_OPEN = "res/pacOpen.png";
    private final Image HEART = new Image("res/heart.png");
    private final static int MOVE_SIZE = 3;
    private final static int FRENZY_MOVE_SIZE_OFFSET = 1;
    private final static int MAX_LIVES = 3;
    private final static int SWITCH_FRAME = 15;
    private final static int FONT_SIZE = 20;
    private final static String SCORE_STRING = "SCORE ";
    private final static int SCORE_X = 25;
    private final static int SCORE_Y = 25;
    private final static int LIVES_X = 900;
    private final static int LIVES_Y = 10;
    private final static int LIVES_OFFSET = 30;
    private final Font FONT = new Font("res/FSO8BITR.ttf", FONT_SIZE);

    private DrawOptions rotator = new DrawOptions();
    private int counter;
    private int score;
    private Image currentImage;
    private int lives;
    private boolean isOpen = false;

    /**
     * Constructor method for Player class
     */
    public Player(int initialX, int initialY){
        super(initialX, initialY);
        this.currentImage = new Image(PAC);
        this.counter = SWITCH_FRAME;
        this.lives = MAX_LIVES;
        this.score = 0;
    }

    /**
     * Method that performs state update
     */
    public void update(Input input, ShadowPac gameObject){
        int speed;
        // sets the speed at which player moves
        if (gameObject.getIsFrenzy()){
            // player will move at frenzy speed
            speed = MOVE_SIZE + FRENZY_MOVE_SIZE_OFFSET;
        } else {
            speed = MOVE_SIZE;
        }

        counter--;

        if (input.isDown(Keys.UP)){
            move(0, -speed);
            rotator.setRotation(-Math.PI/2);
        } else if (input.isDown(Keys.DOWN)){
            move(0, speed);
            rotator.setRotation(Math.PI/2);
        } else if (input.isDown(Keys.LEFT)){
            move(-speed,0);
            rotator.setRotation(Math.PI);
        } else if (input.isDown(Keys.RIGHT)) {
            move(speed,0);
            rotator.setRotation(0);
        }
        if (counter == 0) {
            // switching the image being rendered
            if (isOpen) {
                currentImage = new Image(PAC);
                isOpen = false;
            } else {
                currentImage = new Image(PAC_OPEN);
                isOpen = true;
            }
            counter = SWITCH_FRAME;
        }
        currentImage.drawFromTopLeft(getPosition().x, getPosition().y, rotator);
        gameObject.checkCollisions(this);
        renderScore();
        renderLives();
    }

    /**
     * Method that renders the player's score
     */
    private void renderScore(){
        FONT.drawString(SCORE_STRING + score, SCORE_X, SCORE_Y);
    }

    /**
     * Method that renders the player's lives
     */
    private void renderLives(){
        for (int i = 0; i < lives; i++){
            HEART.drawFromTopLeft(LIVES_X + (LIVES_OFFSET*i), LIVES_Y);
        }
    }

    /**
     * Method that resets the player's position to the starting location
     */
    @Override
    public void resetPosition(){
        setPosition(getStartingPosition());
        currentImage = new Image(PAC);
        rotator.setRotation(0);
    }

    /**
     * Method that checks if the player has 0 lives
     */
    public boolean isDead() {
        return lives == 0;
    }

    /**
     * Method that checks if the player has reached the target score
     */
    public boolean reachedScore(int target){
        return score >= target;
    }

    /**
     * Method that increases the score by the number of points being passed in
     */
    public void incrementScore(int points) {
        score += points;
    }

    /**
     * Decrements the lives attribute by 1
     */
    public void reduceLives() {
        lives--;
    }

    /**
     * Getter method for the currentImage attribute
     */
    public Image getCurrentImage() {
        return currentImage;
    }

}
