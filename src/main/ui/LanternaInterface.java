package ui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import model.Player;
import model.World;
import model.entities.Enemy;
import model.entities.TickedEntity;
import model.entities.Trap;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * GUI Class.
 *
 * Heavily inspired by the Snake Console Game provided to us
 */
public class LanternaInterface {
    private static final String JSON_FILEPATH = "./data/savedGame.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private World world;
    private Player player;

    private boolean isGameOver = false;
    private boolean paused = false;

    private static final int MAX_HEALTH = 100;
    private static final long TICKRATE = 10; // ticks/s

    private static final String ENEMY_ICON = "E ";
    private static final String PLAYER_ICON = "P ";
    private static final String EMPTY_TILE = "  ";
    private static final String PLAYER_FUCKED = "F ";
    private static final String TRAP_ICON = "X ";

    private static final String HORIZONTAL_BORDER = "==";
    private static final String VERTICAL_BORDER = "||";

    private Screen screen;

    /**
     * Input key variables
     *
     * In an ideal world, this will be moved to a .json file
     */
    private static final KeyStroke UP = new KeyStroke(KeyType.ArrowUp);
    private static final KeyStroke DOWN = new KeyStroke(KeyType.ArrowDown);
    private static final KeyStroke LEFT = new KeyStroke(KeyType.ArrowLeft);
    private static final KeyStroke RIGHT = new KeyStroke(KeyType.ArrowRight);
    private static final KeyStroke TRAP = new KeyStroke(new Character('t'), false, false);
    private static final KeyStroke ATTACK = new KeyStroke(new Character(' '), false, false);
    private static final KeyStroke SAVE = new KeyStroke(new Character('s'), false, false);
    private static final KeyStroke PAUSE = new KeyStroke(KeyType.Escape);

    private Set<KeyStroke> legalInputs = new HashSet<>();

    public void start() throws IOException, InterruptedException {
        initJson();
        initScreen();

        boolean load = displayStartScreen();

        if (load) {
            loadGame();
        } else {
            initNewGame();
        }
        addInputs();

        beginTicking();
    }

    // MODIFIES: this
    // EFFECTS: loads the game stored in file
    private void loadGame() {
        try {
            world = jsonReader.readWorld();
            player = jsonReader.readPlayer();
        } catch (IOException e) {
            System.err.println("Error reading file: " + JSON_FILEPATH);
        }
        paused = true;
    }

    //EFFECTS: saves the current game to file
    private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(player, world);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.err.println("Unable to write to file: " + JSON_FILEPATH);
        }
    }

    // EFFECTS: gets the player input on whether or not they want to load
    //          the game stored in file.
    private boolean displayStartScreen() {
        WindowBasedTextGUI startGUI = new MultiWindowTextGUI(screen);

        MessageDialogButton response = new MessageDialogBuilder()
                .setTitle("Welcome!")
                .setText("Would you like to load the game from file?")
                .addButton(MessageDialogButton.Yes)
                .addButton(MessageDialogButton.No)
                .build()
                .showDialog(startGUI);

        if (response == MessageDialogButton.Yes) {
            return true;
        }
        return false;
    }

    //EFFECTS: creates a new world and player at default size and location
    private void initNewGame() {
        TerminalSize terminalSize = screen.getTerminalSize();

        //TODO: implement saving/loading world & player
        world = new World(terminalSize.getColumns(), terminalSize.getRows());
        player = new Player(terminalSize.getColumns() / 2,
                terminalSize.getRows() / 2,
                MAX_HEALTH);
    }

    // MODIFIES: this
    // EFFECTS: adds all the declared inputs to the legalInputs list
    private void addInputs() {
        legalInputs.add(UP);
        legalInputs.add(DOWN);
        legalInputs.add(LEFT);
        legalInputs.add(RIGHT);
        legalInputs.add(TRAP);
        legalInputs.add(ATTACK);
        legalInputs.add(SAVE);
        legalInputs.add(PAUSE);
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
    private void beginTicking() throws InterruptedException, IOException {
        while (!isGameOver) {
            tick();
            Thread.sleep(1000L / TICKRATE);
        }

        System.exit(0);
    }

    private void tick() throws IOException {
        handleUserInput();

        if (!paused) {
            world.tickAllEntities(player);
            updatePlayerState();
        }

        screen.setCursorPosition(new TerminalPosition(0, 0));
        screen.clear();
        renderScene();
        screen.refresh();

        screen.setCursorPosition(new TerminalPosition(screen.getTerminalSize().getColumns() - 1, 0));
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
    //EFFECTS: if the player has died, update isGameOver
    private void checkGameOver() {
        if (player.isDead()) {
            isGameOver = true;
        }
    }


    //EFFECTS: grabs the next input and performs it if it is valid
    private void handleUserInput() throws IOException {
        KeyStroke stroke = screen.pollInput();

        if (stroke == null || !legalInputs.contains(stroke)) {
            return;
        }

        if (stroke.equals(PAUSE)) {
            togglePause();
            return;
        }

        if (paused) {
            performPausedPlayerAction(stroke);
        } else {
            performUnpausedPlayerAction(stroke);
        }
        emptyInputQueue();
    }

    // MODIFIES: this
    // EFFECTS: performs player pause menu action, does nothing if not a valid action
    private void performPausedPlayerAction(KeyStroke action) {
        if (action.equals(SAVE)) {
            saveGame();
        } else if (action.equals(PAUSE)) {
            togglePause();
        }
    }

    // MODIFIES: player
    // EFFECTS: performs player world action, does nothing if not a valid action
    @SuppressWarnings("methodlength")
    private void performUnpausedPlayerAction(KeyStroke action) {
        if (action.equals(LEFT)) {
            player.moveLeft(world);
        } else if (action.equals(RIGHT)) {
            player.moveRight(world);
        } else if (action.equals(UP)) {
            player.moveUp(world);
        } else if (action.equals(DOWN)) {
            player.moveDown(world);
        } else if (action.equals(ATTACK)) {
            player.attack(world);
        } else if (action.equals(TRAP)) {
            player.placeTrap(world);
        }
    }

    //EFFECTS: burns through any remaining inputs made sub-tick to prevent annoying buffers
    private void emptyInputQueue() throws IOException {
        KeyStroke input = screen.pollInput();

        while (input != null) {
            input = screen.pollInput();
        }
    }



    //EFFECTS: pauses game if unpaused, unpauses game if paused
    private void togglePause() {
        if (paused) {
            paused = false;
        } else {
            paused = true;
        }
    }

    private void renderScene() {
        if (isGameOver) {
            drawEndScreen();
        }

        drawHealth();
        drawEntities();
        drawPlayer();

        if (paused) {
            drawPauseDialog();
        }
    }

    //EFFECTS: draws the pause menu to the screen
    private void drawPauseDialog() {
        int centerX = world.getWidth() / 2;
        int centerY = world.getHeight() / 2;

        String pauseString = "GAME PAUSED!";
        String saveString = "Press S to save";

        drawText(centerX - (pauseString.length() / 2), centerY, pauseString, TextColor.ANSI.CYAN);
        drawText(centerX - (saveString.length() / 2), centerY + 1, saveString, TextColor.ANSI.CYAN);

    }

    private void drawEndScreen() {
        WindowBasedTextGUI endGUI = new MultiWindowTextGUI(screen);

        new MessageDialogBuilder()
                .setTitle("Game over!")
                .setText("You Died!")
                .addButton(MessageDialogButton.Close)
                .build()
                .showDialog(endGUI);
    }

    private void drawHealth() {
        drawText(1, 0, "Health Remaining: ", TextColor.ANSI.GREEN);
        drawText(19, 0, String.valueOf(player.getHealth()), TextColor.ANSI.WHITE);
    }

    private void drawPlayer() {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.GREEN);
        if (world.containsEnemyAt(player.getX(), player.getY())) {
            text.putString(player.getX(), player.getY(), PLAYER_FUCKED);
        } else {
            text.putString(player.getX(), player.getY(), PLAYER_ICON);
        }
    }

    private void drawEntities() {
        List<TickedEntity> activeEntities = world.getEntities();

        for (TickedEntity activeEntity : activeEntities) {
            if (activeEntity instanceof Enemy) {
                drawEntity(activeEntity, TextColor.ANSI.RED, 'E');
            } else if (activeEntity instanceof Trap) {
                drawEntity(activeEntity, TextColor.ANSI.YELLOW, 'X');
            }
        }
    }

    private void drawEntity(TickedEntity activeEntity, TextColor color, char c) {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(color);
        text.putString(activeEntity.getX(), activeEntity.getY(), String.valueOf(c));
    }

    private void drawText(int x, int y, String text, TextColor color) {
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(color);
        textGraphics.putString(x, y, text);
    }
}
