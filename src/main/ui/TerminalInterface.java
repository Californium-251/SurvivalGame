package ui;

import exceptions.InvalidInputException;
import model.Player;
import model.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * TerminalInterface
 * ------------------------
 * This is the class that handles I/O and ticking on the terminal based-game
 *
 * Every time the player provides a valid input, the game ticks
 *  - Player Actions are processed
 *  - Enemies move
 *  - Important values change
 *
 * After every input, the screen is reprinted with the current gamestate
 *
 * Player is represented with character 'P'
 * Enemies are represented by 'E'
 *
 */
public class TerminalInterface {
    private World world;
    private Player player;
    private Scanner inputScanner = new Scanner(System.in);

    private Boolean isGameOver = false;

    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String UP = "up";
    private static final String DOWN = "down";
    private static final String ATTACK = "attack";

    private static final List<String> LEGAL_INPUTS = new ArrayList<>();

    private static final int MAX_HEALTH = 3;

    public TerminalInterface() {
        this.init();
    }


    //MODIFIES: this
    //EFFECTS: initializes world and player. Populates LEGAL_INPUTS with required values
    private void init() {
        world = new World();
        player = new Player(world.getCenter()[0], world.getCenter()[1], MAX_HEALTH);

        LEGAL_INPUTS.add(LEFT);
        LEGAL_INPUTS.add(RIGHT);
        LEGAL_INPUTS.add(UP);
        LEGAL_INPUTS.add(DOWN);
        LEGAL_INPUTS.add(ATTACK);
        // make sure this only runs once in the entire program
        // otherwise rework how LEGAL_INPUTS is populated
    }

    //MODIFIES: this
    //EFFECTS: loops until the game is over; plays the game.
    public void playGame() {
        String in;
        while (true) {
            in = getNextInput();

            try {
                performPlayerAction(in);
            } catch (InvalidInputException e) {
                System.err.println("Invalid input leaked regarding performing player action; action ignored");
                //if debugging, make sure to check switch case in performPlayerAction()

            }   //purely as a redundancy in case I change input handling in the future

            world.updateAllEnemies();

            updatePlayerState();

            displayCurrentState();
            if (isGameOver) {
                break;
            }
        }
    }

    //EFFECTS: Displays the current world state on the terminal
    private void displayCurrentState() {
        displayWorld();
        System.out.println("Health: " + player.getHealth());
    }

    //EFFECTS: displays the simulation box with player and enemies
    private void displayWorld() {
        //TODO: implement world display
    }

    //MODIFIES: this
    //EFFECTS: decreases the player's health if colliding with enemy and sets
    private void updatePlayerState() {
        if (world.containsEnemyAt(player.getX(), player.getY())) {
            player.takeDamage();
        }
        checkGameOver();
    }

    //MODIFIES: this
    //EFFECTS: sets the gameOver state to true if the player is dead
    private void checkGameOver() {
        if (player.isDead()) {
            isGameOver = true;
        }
    }

    //MODIFIES: this
    //EFFECTS: performs the specified player action
    private void performPlayerAction(String in) throws InvalidInputException {
        switch (in) {
            case LEFT: {
                player.moveLeft(world);
                break;
            } case RIGHT: {
                player.moveRight(world);
                break;
            } case UP: {
                player.moveUp(world);
                break;
            } case DOWN: {
                player.moveDown(world);
                break;
            } case ATTACK: {
                player.attack(world);
                break;
            } default: {
                throw new InvalidInputException();
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: retrieves the next input from the user
    private String getNextInput() {
        String in = inputScanner.nextLine();

        if (LEGAL_INPUTS.contains(in)) {
            return in;
        } else {
            System.out.println("Invalid Input! Please try again:");
            return getNextInput();
        }
    }



}

