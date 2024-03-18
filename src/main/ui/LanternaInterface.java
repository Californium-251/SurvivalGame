package ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import model.Player;
import model.World;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LanternaInterface {
    private static final String JSON_FILEPATH = "./data/savedGame.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private World world;
    private Player player;

    private Boolean isGameOver = false;

    private static final int MAX_HEALTH = 3;
    private static final long TICKRATE = 1; // ticks/s

    private static final String ENEMY_ICON = "E ";
    private static final String PLAYER_ICON = "P ";
    private static final String EMPTY_TILE = "  ";
    private static final String PLAYER_FUCKED = "F ";
    private static final String TRAP_ICON = "X ";

    private static final String HORIZONTAL_BORDER = "==";
    private static final String VERTICAL_BORDER = "||";

    private Screen screen;

    //
    public void start() throws IOException, InterruptedException {
        initJson();
        initScreen();
        //TODO: implement saving/loading logic
        initGame();

        beginTicking();
    }

    //EFFECTS: creates a new world and player at default size and location
    private void initGame() {
        TerminalSize terminalSize = screen.getTerminalSize();

        //TODO: implement saving/loading world & player
        world = new World(terminalSize.getColumns(), terminalSize.getRows());
        player = new Player(terminalSize.getColumns() / 2,
                terminalSize.getRows() / 2,
                MAX_HEALTH);
    }

    //EFFECTS: initializes screen
    private void initScreen() throws IOException {
        screen = new DefaultTerminalFactory().createScreen();
        screen.startScreen();
    }

    //EFFECTS: initializes json reader and writer objects
    private void initJson() {
        jsonWriter = new JsonWriter(JSON_FILEPATH);
        jsonReader = new JsonReader(JSON_FILEPATH);
    }


    //EFFECTS: begins the game tick cycle which will not stop until the game ends
    private void beginTicking() throws InterruptedException {
        while (!isGameOver) {
            tick();
            Thread.sleep(1000L / TICKRATE);
        }

        System.exit(0);
    }

    private void tick() {
        //stub
    }


}
