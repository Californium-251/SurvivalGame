package ui;

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
    private final Scanner inputScanner = new Scanner(System.in);

    private Boolean isGameOver = false;

    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String UP = "up";
    private static final String DOWN = "down";
    private static final String ATTACK = "attack";
    private static final String TRAP = "trap";

    private static final List<String> LEGAL_INPUTS = new ArrayList<>();

    private static final int MAX_HEALTH = 3;

    private static final String ENEMY_ICON = "E ";
    private static final String PLAYER_ICON = "P ";
    private static final String EMPTY_TILE = "  ";
    private static final String PLAYER_FUCKED = "F ";
    private static final String TRAP_ICON = "X ";

    private static final String HORIZONTAL_BORDER = "==";
    private static final String VERTICAL_BORDER = "||";


    public TerminalInterface() {
        this.init();
    }


    //MODIFIES: this
    //EFFECTS: initializes world and player. Populates LEGAL_INPUTS with required values
    private void init() {
        world = new World(10, 10);
        player = new Player(world.getCenter()[0], world.getCenter()[1], MAX_HEALTH);

        LEGAL_INPUTS.add(LEFT);
        LEGAL_INPUTS.add(RIGHT);
        LEGAL_INPUTS.add(UP);
        LEGAL_INPUTS.add(DOWN);
        LEGAL_INPUTS.add(ATTACK);
        LEGAL_INPUTS.add(TRAP);
        // make sure this only runs once in the entire program
        // otherwise rework how LEGAL_INPUTS is populated


        displayCurrentState();
    }

    //MODIFIES: this
    //EFFECTS: loops until the game is over; plays the game.
    public void playGame() {
        String in;
        while (true) {
            in = getNextInput();

            try {
                performPlayerAction(in);
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid input leaked regarding performing player action; action ignored");
                //if debugging, make sure to check switch case in performPlayerAction()

            }   //purely as a redundancy in case I change input handling in the future

            world.tickAllEntities(player);

            updatePlayerState();

            displayCurrentState();
            if (isGameOver) {
                System.out.println("~~~~~~~~ GAME OVER ~~~~~~~~");
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
        //could be much better in a gui based approach
        displayHorizontalBorder();

        for (int row = 0; row < world.getHeight(); row++) {
            displayVerticalBorderPiece();

            for (int col = 0; col < world.getWidth(); col++) {

                if (world.containsEnemyAt(col, row) && player.getX() == col && player.getY() == row) {
                    System.out.print(PLAYER_FUCKED);
                } else if (player.getX() == col && player.getY() == row) {
                    System.out.print(PLAYER_ICON);
                } else if (world.containsEnemyAt(col, row)) {
                    System.out.print(ENEMY_ICON);
                } else if (world.containsTrapAt(col, row)) {
                    System.out.print(TRAP_ICON);
                } else {
                    System.out.print(EMPTY_TILE);
                }

            }
            displayVerticalBorderPiece();

            System.out.println(); //Creates newline for next row
        }

        displayHorizontalBorder();
    }

    //EFFECTS: prints out a single piece of the vertical borders of the world
    private void displayVerticalBorderPiece() {
        System.out.print(VERTICAL_BORDER);
    }

    //EFFECTS: prints out an entire horizontal border of the world
    private void displayHorizontalBorder() {
        displayVerticalBorderPiece();

        for (int i = 0; i < world.getWidth(); i++) {
            System.out.print(HORIZONTAL_BORDER);
        }

        displayVerticalBorderPiece();

        System.out.println();
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
    private void performPlayerAction(String in) throws IllegalArgumentException {
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
            } case TRAP: {
                player.placeTrap(world);
                break;
            } default: {
                throw new IllegalArgumentException();
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

