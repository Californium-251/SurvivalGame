package ui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
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

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LanternaInterface {
    private static final String JSON_FILEPATH = "./data/savedGame.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private World world;
    private Player player;

    private boolean isGameOver = false;

    private static final int MAX_HEALTH = 100;
    private static final long TICKRATE = 1; // ticks/s

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
    private static final KeyStroke SAVE = new KeyStroke(new Character('s'), true, false);

    private Set<KeyStroke> legalInputs = new HashSet<>();



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

        addInputs();
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
            Thread.sleep(100L / TICKRATE);
        }

        System.exit(0);
    }

    private void tick() throws IOException {
        handleUserInput();

        world.tickAllEntities(player);
        updatePlayerState();

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
        //TODO: currently inputs get queued. This would preferably not happen but that's for a later fix
        KeyStroke stroke = screen.pollInput();

        if (stroke == null) {
            return;
        }

        if (!legalInputs.contains(stroke)) {
            return;
        }

        performPlayerAction(stroke);
    }

    // MODIFIES: player
    // EFFECTS: performs player action, throws illegalArgumentException if not a valid action
    @SuppressWarnings("methodlength")
    private void performPlayerAction(KeyStroke action) throws IllegalArgumentException {
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
        } else {
            //TODO: implement save key functionality
            //saveGame();  throw new IllegalArgumentException();
        }
    }

    private void renderScene() {
        //TODO: Implement game over scene

        drawHealth();
        drawEntities();
        drawPlayer();
    }

    private void drawHealth() {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.GREEN);
        text.putString(1, 0, "Health Remaining: ");

        text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.WHITE);
        text.putString(19, 0, String.valueOf(player.getHealth()));
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
                drawPosition(activeEntity, TextColor.ANSI.RED, 'E');
            } else if (activeEntity instanceof Trap) {
                drawPosition(activeEntity, TextColor.ANSI.YELLOW, 'X');
            }
        }
    }

    private void drawPosition(TickedEntity activeEntity, TextColor color, char c) {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(color);
        text.putString(activeEntity.getX(), activeEntity.getY(), String.valueOf(c));
    }


}
