package model;

import model.entities.Enemy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private final int WORLD_HEIGHT = 10;
    private final int WORLD_WIDTH = 10;

    private final int PLAYER_X = WORLD_WIDTH/2;
    private final int PLAYER_Y = WORLD_HEIGHT/2;
    private final int PLAYER_HEALTH = 3;


    Player player;
    Player tinyPlayer; //used in tinyWorld

    World emptyWorld;
    World enemiesWorld;
    World tinyWorld;

    @BeforeEach
    public void init() {
        player = new Player(PLAYER_X, PLAYER_Y, PLAYER_HEALTH);
        tinyPlayer = new Player(0, 0, 3);

        emptyWorld = new World(WORLD_HEIGHT, WORLD_WIDTH);

        enemiesWorld = new World(WORLD_HEIGHT, WORLD_WIDTH);
        enemiesWorld.spawnEnemy(new Enemy(PLAYER_X+1, PLAYER_Y));
        enemiesWorld.spawnEnemy(new Enemy(PLAYER_X-1, PLAYER_Y));
        enemiesWorld.spawnEnemy(new Enemy(PLAYER_X, PLAYER_Y-1));
        enemiesWorld.spawnEnemy(new Enemy(PLAYER_X, PLAYER_Y+1));
        enemiesWorld.spawnEnemy(new Enemy(PLAYER_X, PLAYER_Y));


        tinyWorld = new World(1, 1);
    }

    @Test
    public void constructorTest() {
        assertEquals(PLAYER_X, player.getX());
        assertEquals(PLAYER_Y, player.getY());
        assertEquals(PLAYER_HEALTH, player.getHealth());
    }

    @Test
    public void moveUpTest() {
        player.moveUp(emptyWorld);

        assertEquals(PLAYER_X, player.getX());
        assertEquals(PLAYER_Y-1, player.getY());
    }

    @Test
    public void moveUpAgainstBorderTest() {
        tinyPlayer.moveUp(tinyWorld);

        assertEquals(0, tinyPlayer.getX());
        assertEquals(0, tinyPlayer.getY());
    }

    @Test
    public void moveDownTest() {
        player.moveDown(emptyWorld);

        assertEquals(PLAYER_X, player.getX());
        assertEquals(PLAYER_Y+1, player.getY());
    }

    @Test
    public void moveDownAgainstBorderTest() {
        tinyPlayer.moveDown(tinyWorld);

        assertEquals(0, tinyPlayer.getX());
        assertEquals(0, tinyPlayer.getY());
    }

    @Test
    public void moveLeftTest() {
        player.moveLeft(emptyWorld);

        assertEquals(PLAYER_X-1, player.getX());
        assertEquals(PLAYER_Y, player.getY());
    }

    @Test
    public void moveLeftAgainstBorderTest() {
        tinyPlayer.moveLeft(tinyWorld);

        assertEquals(0, tinyPlayer.getX());
        assertEquals(0, tinyPlayer.getY());
    }

    @Test
    public void moveRightTest() {
        player.moveRight(emptyWorld);

        assertEquals(PLAYER_X+1, player.getX());
        assertEquals(PLAYER_Y, player.getY());
    }

    @Test
    public void moveRightAgainstBorderTest() {
        tinyPlayer.moveRight(tinyWorld);

        assertEquals(0, tinyPlayer.getX());
        assertEquals(0, tinyPlayer.getY());
    }

    @Test
    public void attackKillsAboveTest() {
        player.attack(enemiesWorld);

        assertFalse(enemiesWorld.containsEnemyAt(PLAYER_X, PLAYER_Y-1));
    }

    @Test
    public void attackKillsBelowTest() {
        player.attack(enemiesWorld);

        assertFalse(enemiesWorld.containsEnemyAt(PLAYER_X, PLAYER_Y+1));
    }

    @Test
    public void attackKillsLeftTest() {
        player.attack(enemiesWorld);

        assertFalse(enemiesWorld.containsEnemyAt(PLAYER_X-1, PLAYER_Y));
    }

    @Test
    public void attackKillsRightTest() {
        player.attack(enemiesWorld);

        assertFalse(enemiesWorld.containsEnemyAt(PLAYER_X+1, PLAYER_Y));
    }

    @Test
    public void attackKillsOnPlayerTest() {
        player.attack(enemiesWorld);

        assertFalse(enemiesWorld.containsEnemyAt(PLAYER_X, PLAYER_Y));
    }

    @Test
    public void takeDamageOneTest() {
        player.takeDamage();

        assertEquals(PLAYER_HEALTH-1, player.getHealth());
    }

    @Test
    public void takeDamageTwiceTest() {
        player.takeDamage();
        player.takeDamage();

        assertEquals(PLAYER_HEALTH-2, player.getHealth());
    }

    @Test
    public void isDeadPlayerNotDeadTest() {
        assertFalse(player.isDead());
    }

    @Test
    public void isDeadPlayer1HPTest() {
        for (int i = PLAYER_HEALTH-1; i > 0; i--) {
            player.takeDamage();
        }

        assertFalse(player.isDead());
    }

    @Test
    public void isDeadPlayer0HPTest() {
        for (int i = PLAYER_HEALTH; i > 0; i--) {
            player.takeDamage();
        }

        assertTrue(player.isDead());
    }

    @Test
    public void isDeadPlayerNegativeHPTest() {
        for (int i = PLAYER_HEALTH+1; i > 0; i--) {
            player.takeDamage();
        }

        assertTrue(player.isDead());
    }

    @Test
    void testPlaceTrap() {
        player.placeTrap(emptyWorld);

        assertTrue(emptyWorld.containsTrapAt(player.getX(), player.getY()));
    }
}
