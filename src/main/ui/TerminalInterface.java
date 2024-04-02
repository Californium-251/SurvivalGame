package ui;

import model.Player;
import model.World;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Deprecated
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
    private static final String JSON_FILEPATH = "./data/savedGame.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

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
    private static final String SAVE = "save";

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
        jsonWriter = new JsonWriter(JSON_FILEPATH);
        jsonReader = new JsonReader(JSON_FILEPATH);

        if (playerWantsNewGame()) {
            world = new World(10, 10);
            player = new Player(5, 5, MAX_HEALTH);
        } else {
            loadGame();
        }

        this.init();

        this.displayCurrentState();
    }


    // EFFECTS: returns true if the player chooses to start a new game
    private boolean playerWantsNewGame() {
        System.out.println("Would you like a new game or to load the saved game?");
        System.out.println("   new game -> Make a new game");
        System.out.println("   load     -> Load previous save");
        String input = inputScanner.nextLine();

        if (input.equals("new game")) {
            return true;
        } else if (input.equals("load")) {
            return false;
        } else {
            System.out.println("Invalid Input, try again!");
            return playerWantsNewGame();
        }
    }

    //MODIFIES: this
    //EFFECTS: loads a previously saved game from file
    private void loadGame() {
        try {
            world = jsonReader.readWorld();
            player = jsonReader.readPlayer();
            System.out.println("Successfully loaded game from: " + JSON_FILEPATH);
        } catch (IOException e) {
            System.err.println("Error reading file: " + JSON_FILEPATH);
        }
    }

    //EFFECTS: saves the current game to file
    private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(player, world);
            jsonWriter.close();
            System.out.println("Saved Game!");
        } catch (FileNotFoundException e) {
            System.err.println("Unable to write to file: " + JSON_FILEPATH);
        }
    }


    //MODIFIES: this
    //EFFECTS: Populates LEGAL_INPUTS with required values
    private void init() {
        LEGAL_INPUTS.add(LEFT);
        LEGAL_INPUTS.add(RIGHT);
        LEGAL_INPUTS.add(UP);
        LEGAL_INPUTS.add(DOWN);
        LEGAL_INPUTS.add(ATTACK);
        LEGAL_INPUTS.add(TRAP);
        LEGAL_INPUTS.add(SAVE);
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
                if (performPlayerAction(in) == false) {
                    continue; // we do not want to update anything according to effects clause
                }
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
    //EFFECTS: performs the specified player action; returns true if game should update
    @SuppressWarnings("methodlength")
    private boolean performPlayerAction(String in) throws IllegalArgumentException {
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
            } case SAVE: {
                saveGame();
                return false; //we don't want game to update on save
            } default: {
                throw new IllegalArgumentException();
            }
        }
        return true; //default method behaviour
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

