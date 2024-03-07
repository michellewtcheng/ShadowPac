import bagel.*;
import bagel.util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * SWEN20003 Project 2B, Semester 1, 2023
 * This is the main class for the ShadowPac game
 *
 * @author Michelle Cheng, using Project 1 Solution code by Tharun Dharmawickrema & Stella Li
 */
public class ShadowPac extends AbstractGame  {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW PAC";
    private final static String WORLD_FILE0 = "res/level0.csv";
    private final static String WORLD_FILE1 =  "res/level1.csv";
    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");

    private final static int TITLE_FONT_SIZE = 64;
    private final static int START_INSTRUCTION_FONT_SIZE = 24;
    private final static int LEVEL1_INSTRUCTION_FONT_SIZE = 40;
    private final static int TITLE_X = 260;
    private final static int TITLE_Y = 250;
    private final static Point START_INSTRUCTION_POINT1 = new Point(320, 440);
    private final static Point START_INSTRUCTION_POINT2 = new Point(300, 470);
    private final static Point LEVEL1_INSTRUCTION_POINT1 = new Point(200, 350);
    private final static Point LEVEL1_INSTRUCTION_POINT2 = new Point(165, 390);
    private final static Point LEVEL1_INSTRUCTION_POINT3 = new Point(133, 450);
    private final static String INSTRUCTION_MESSAGE1 = "PRESS SPACE TO START";
    private final static String INSTRUCTION_MESSAGE2 = "USE ARROW KEYS TO MOVE";
    private final static String INSTRUCTION_MESSAGE3 = "EAT THE PELLET TO ATTACK";
    private final static String END_MESSAGE = "GAME OVER!";
    private final static String WIN_MESSAGE = "WELL DONE!";
    private final static String LEVEL_COMPLETE_MESSAGE = "LEVEL COMPLETE!";
    private final Font TITLE_FONT = new Font("res/FSO8BITR.ttf", TITLE_FONT_SIZE);
    private final Font START_INSTRUCTION_FONT = new Font("res/FSO8BITR.ttf", START_INSTRUCTION_FONT_SIZE);
    private final Font LEVEL1_INSTRUCTION_FONT = new Font("res/FSO8BITR.ttf", LEVEL1_INSTRUCTION_FONT_SIZE);

    private final static int WALL_ARRAY_SIZE = 145;
    private final static int DOT_ARRAY_SIZE = 121;
    private final static int GHOST_ARRAY_SIZE = 4;
    private static Wall[] walls = new Wall[WALL_ARRAY_SIZE];
    private static Dot[] dots = new Dot[DOT_ARRAY_SIZE];
    private static Ghost[] ghosts = new Ghost[GHOST_ARRAY_SIZE];
    private static ArrayList<Cherry> cherries = new ArrayList<Cherry>();
    private static ArrayList<Wall> walls1 = new ArrayList<Wall>();
    private static ArrayList<Dot> dots1 = new ArrayList<Dot>();

    private Player player;
    private boolean hasStarted;
    private boolean gameOver;
    private boolean playerWin;
    private boolean isLevel0;

    private Pellet pellet;
    private boolean isLevel1;
    private boolean levelComplete;
    private boolean isFrenzy;
    private final static int LVL1_WINNING_SCORE = 800;
    private final static int LVL0_WINNING_SCORE = Dot.POINTS * DOT_ARRAY_SIZE;
    private final static int LVL0_WIN_DURATION = 300;
    private final static int FRENZY_DURATION = 1000;
    private int lvlZeroWinMsgCounter = 0;
    private int frenzyCounter = 0;

    /**
     * Constructor for ShadowPac class
     */
    public ShadowPac(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        hasStarted = false;
        gameOver = false;
        playerWin = false;
        isLevel0 = true;
        isLevel1 = false;
        levelComplete = false;
        isFrenzy = false;
        readCSV(WORLD_FILE0);
    }

    /**
     * Method used to read file and create objects
     */
    private void readCSV(String worldFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(worldFile))){

            String line;
            int currentWallCount = 0;
            int currentDotCount = 0;
            int currentGhostCount = 0;

            while ((line = reader.readLine()) != null) {
                String[] sections = line.split(",");

                String type = sections[0];
                if (type.equals("Player")){
                    player = new Player(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));

                } else if (type.equals("Ghost")){
                    ghosts[currentGhostCount] = new Ghost(Integer.parseInt(sections[1]),
                            Integer.parseInt(sections[2]));
                    currentGhostCount++;

                } else if (type.equals("GhostRed")){
                    ghosts[currentGhostCount] = new RedGhost(Integer.parseInt(sections[1]),
                            Integer.parseInt(sections[2]));
                    currentGhostCount++;

                } else if (type.equals("GhostBlue")){
                    ghosts[currentGhostCount] = new BlueGhost(Integer.parseInt(sections[1]),
                            Integer.parseInt(sections[2]));
                    currentGhostCount++;

                } else if (type.equals("GhostGreen")){
                    ghosts[currentGhostCount] = new GreenGhost(Integer.parseInt(sections[1]),
                            Integer.parseInt(sections[2]));
                    currentGhostCount++;

                } else if (type.equals("GhostPink")){
                    ghosts[currentGhostCount] = new PinkGhost(Integer.parseInt(sections[1]),
                            Integer.parseInt(sections[2]));
                    currentGhostCount++;

                } else if (type.equals("Dot")){
                    if (isLevel0) {
                        dots[currentDotCount] = new Dot(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]));
                        currentDotCount++;

                    } else if (isLevel1) {
                        dots1.add(new Dot(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                    }

                } else if (type.equals("Wall")){
                    if (isLevel0) {
                        walls[currentWallCount] = new Wall(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]));
                        currentWallCount++;

                    } else if (isLevel1) {
                        walls1.add(new Wall(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                    }

                } else if (type.equals("Cherry")){
                    cherries.add(new Cherry(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));

                } else if (type.equals("Pellet")){
                    pellet = new Pellet(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                }
            }

        } catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * This is the main method
     */
    public static void main(String[] args) {
        ShadowPac game = new ShadowPac();
        game.run();
    }

    /**
     * Method that performs state update
     */
    @Override
    protected void update(Input input) {

        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

        // just for me to skip ahead to level 1 -- testing
        if (input.wasPressed(Keys.W)){
            hasStarted = true;
            levelComplete = true;
        }

        if(!hasStarted){
            if (isLevel0) {
                drawStartScreen();
                if (input.wasPressed(Keys.SPACE)) {
                    hasStarted = true;
                }
            } else {
                // this is for the Level 1 instructions page
                drawMessage(INSTRUCTION_MESSAGE1);
                if (input.wasPressed(Keys.SPACE)){
                    readCSV(WORLD_FILE1);
                    hasStarted = true;
                }
            }
        } else if (gameOver){
            drawMessage(END_MESSAGE);

        } else if (playerWin) {
            drawMessage(WIN_MESSAGE);

        } else if (levelComplete){
            drawLevelComplete();

        } else {
            player.update(input, this);

            // update level 0 exclusive entities
            if (isLevel0) {
                for (Wall current : walls) {
                    current.update();
                }
                for (Dot current : dots) {
                    current.update();
                }
            }

            // update level 1 entities
            if (isLevel1){
                pellet.update();

                for(Cherry current: cherries){
                    current.update();
                }

                for(Wall current: walls1){
                    current.update();
                }

                for(Dot current: dots1){
                    current.update();
                }
            }

            for (Ghost current: ghosts){
                current.update(this);
            }

            if (isFrenzy) {
                // keeps frenzy status up to date for ghosts
                updateFrenzy(isFrenzy);
            }

            if (player.isDead()){
                gameOver = true;
            }
            if (player.reachedScore(LVL0_WINNING_SCORE) && isLevel0){
                // winning condition for level 0
                levelComplete = true;
            }
            if (player.reachedScore(LVL1_WINNING_SCORE) && isLevel1){
                //winning conditions for level 1
                playerWin = true;
            }
        }
    }

    /**
     * Method that checks for collisions between the player and the other entities, and performs
     * corresponding actions.
     */
    public void checkCollisions(Player player){
        Rectangle playerBox = new Rectangle(player.getPosition(), player.getCurrentImage().getWidth(),
                player.getCurrentImage().getHeight());

        for (Ghost current: ghosts){
            Rectangle ghostBox = current.getBoundingBox();
            if (playerBox.intersects(ghostBox)){
                if (current.isActive() && isFrenzy){

                    //checking collisions with ghosts during frenzy mode
                    current.setActive(false);
                    player.incrementScore(Ghost.GHOST_SCORE);

                    // invisible/eaten ghost goes back to original position, waiting to appear once frenzy is over
                    current.resetPosition();
                    current.resetDirection();

                } else if (current.isActive()){

                    // player goes back to starting position if colliding with a ghost in normal mode
                    player.reduceLives();
                    player.resetPosition();

                    // in Level 1, upon collision, the ghost will go back to it's starting position
                    current.resetPosition();
                    current.resetDirection();
                }
            }
        }

        if (isLevel0) {

            for (Dot current : dots) {
                Rectangle dotBox = current.getBoundingBox();
                if (current.isActive() && playerBox.intersects(dotBox)) {
                    player.incrementScore(Dot.POINTS);
                    current.setActive(false);
                }
            }

            for (Wall current : walls) {
                Rectangle wallBox = current.getBoundingBox();
                if (playerBox.intersects(wallBox)) {
                    player.moveBack();
                }
            }

        } else if (isLevel1) {

            // checking collisions for level 1 exclusive entities when it's level 1
            for (Dot current: dots1){
                Rectangle dotBox = current.getBoundingBox();
                if (current.isActive() && playerBox.intersects(dotBox)) {
                    player.incrementScore(Dot.POINTS);
                    current.setActive(false);
                }
            }

            for (Wall current : walls1) {
                Rectangle wallBox = current.getBoundingBox();
                if (playerBox.intersects(wallBox)){
                    player.moveBack();
                }
            }

            for (Cherry current: cherries){
                Rectangle cherryBox = current.getBoundingBox();
                if (current.isActive() && playerBox.intersects(cherryBox)){
                    player.incrementScore(Cherry.CHERRY_SCORE);
                    current.setActive(false);
                }
            }

            Rectangle pelletBox = pellet.getBoundingBox();
            if (pellet.isActive() && playerBox.intersects(pelletBox)){
                isFrenzy = true;
                pellet.setActive(false);
            }
        }
    }

    /**
     * Method that checks for collisions between the moving ghost in level 1 and walls. Prevents the
     * ghost from going through the wall
     */
    public void checkGhostWallCollisions(Ghost ghost){
        Rectangle ghostBox = ghost.getBoundingBox();

        for (Wall current : walls1){
            Rectangle wallBox = current.getBoundingBox();
            if (ghostBox.intersects(wallBox)){
                ghost.moveBack();
                ghost.setHitWall(true);
                ghost.afterHitWall();
            }
        }
    }

    /**
     * Method used to draw the start screen title and instructions
     */
    private void drawStartScreen(){
        TITLE_FONT.drawString(GAME_TITLE, TITLE_X, TITLE_Y);
        START_INSTRUCTION_FONT.drawString(INSTRUCTION_MESSAGE1, START_INSTRUCTION_POINT1.x, START_INSTRUCTION_POINT1.y);
        START_INSTRUCTION_FONT.drawString(INSTRUCTION_MESSAGE2, START_INSTRUCTION_POINT2.x, START_INSTRUCTION_POINT2.y);
    }

    /**
     * Method used to draw end screen messages and level 1 instruction message
     */
    private void drawMessage(String message){
        if (playerWin || levelComplete) {

            TITLE_FONT.drawString(message, (Window.getWidth() / 2.0 - (TITLE_FONT.getWidth(message) / 2.0)),
                    (Window.getHeight() / 2.0 + (TITLE_FONT_SIZE / 2.0)));

        } else if (gameOver){

            // all lives have been lost, so game over screen gets drawn
            TITLE_FONT.drawString(message, (Window.getWidth() / 2.0 - (TITLE_FONT.getWidth(message) / 2.0)),
                    (Window.getHeight() / 2.0 + (TITLE_FONT_SIZE / 2.0)));

        } else if (isLevel1) {

            // draws level 1 instruction message screen per line
            LEVEL1_INSTRUCTION_FONT.drawString(message, LEVEL1_INSTRUCTION_POINT1.x,
                    LEVEL1_INSTRUCTION_POINT1.y);
            LEVEL1_INSTRUCTION_FONT.drawString(INSTRUCTION_MESSAGE2, LEVEL1_INSTRUCTION_POINT2.x,
                    LEVEL1_INSTRUCTION_POINT2.y);
            LEVEL1_INSTRUCTION_FONT.drawString(INSTRUCTION_MESSAGE3, LEVEL1_INSTRUCTION_POINT3.x,
                    LEVEL1_INSTRUCTION_POINT3.y);
        }
    }

    /**
     *  This method draws the level 0 win screen for appropriate counts
     *  then initiates beginning of level 1 events once the duration is over
     */
    private void drawLevelComplete(){
        drawMessage(LEVEL_COMPLETE_MESSAGE);
        if (lvlZeroWinMsgCounter != LVL0_WIN_DURATION) {

            drawMessage(LEVEL_COMPLETE_MESSAGE);
            lvlZeroWinMsgCounter++;
        }
        if (lvlZeroWinMsgCounter == LVL0_WIN_DURATION){

            // level complete screen has appeared for 300 frames
            hasStarted = false;
            levelComplete = false;
            isLevel0 = false;
            isLevel1 = true;
        }
    }

    private void updateFrenzy(boolean frenzy){
        if (frenzyCounter < FRENZY_DURATION) {

            // frenzy begins
            for (Ghost current : ghosts) {
                current.setIsFrenzy(frenzy);
            }
            frenzyCounter++;

        } else if (frenzyCounter == FRENZY_DURATION){

            // frenzy is now over!
            isFrenzy = false;
            for (Ghost current : ghosts){

                //ghosts go back to normal and reappear if they were eaten
                current.setIsFrenzy(false);
                current.setActive(true);
            }
        }
    }

    /**
     * Getter method for isFrenzy attribute
     */
    public boolean getIsFrenzy(){
        return isFrenzy;
    }
}