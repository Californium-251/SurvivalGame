package model;


import exceptions.EnemyAlreadyActiveException;
import exceptions.EnemyOutOfBoundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WorldTest {
    World world;

    Player player;

    Enemy top;
    Enemy bot;
    Enemy left;
    Enemy right;

    Enemy outOfBounds;

    @BeforeEach
    public void init() {
        world = new World();

        int midX = world.getWidth()/2;
        int midY = world.getHeight()/2;

        player = new Player(midX, midY, 5);

        top = new Enemy(midX, 0);
        bot = new Enemy(midX, world.getHeight()-1);
        left = new Enemy(0, midY);
        right = new Enemy(world.getWidth()-1, midY);
    }

    @Test
    public void spawnEnemyInsideBoundsTopTest() {
        world.spawnEnemy(top);

        assertTrue(world.isActive(top));
    }

    @Test
    public void spawnEnemyInsideBoundsBotTest() {
        world.spawnEnemy(bot);

        assertTrue(world.isActive(bot));
    }

    @Test
    public void spawnEnemyInsideBoundsLeftTest() {
        world.spawnEnemy(left);

        assertTrue(world.isActive(left));
    }

    @Test
    public void spawnEnemyInsideBoundsRightTest() {
        world.spawnEnemy(right);

        assertTrue(world.isActive(right));
    }


    @Test
    public void killEnemyRemovesEnemyTest() {
        world.spawnEnemy(top);

        world.killEnemyAt(top.getX(), top.getY());

        assertFalse(world.isActive(top));
    }

    @Test
    public void killEnemyRemovesOnlyEnemyTest() {
        world.spawnEnemy(left);
        world.spawnEnemy(top);


        world.killEnemyAt(left.getX(), left.getY());

        assertTrue(world.isActive(top));
        assertFalse(world.isActive(left));
    }

    @Test
    public void killEnemyInactiveEnemyTest() {
        world.spawnEnemy(top);

        try {
            world.killEnemyAt(left.getX(), left.getY());
        } catch (Exception e) {
            fail("Method threw unexpected Exception");
        }

        assertTrue(world.isActive(top));
    }

    @Test
    public void updateAllEnemiesEmptyListSpawnsNewEnemiesTest() {
        world.updateAllEnemies(player);

        assertTrue(world.containsEnemyAt(0, 0));
        assertTrue(world.containsEnemyAt(world.getWidth()-1, 0));
        assertTrue(world.containsEnemyAt(0, world.getHeight()-1));
        assertTrue(world.containsEnemyAt(world.getWidth()-1, world.getHeight()-1));
    }

    @Test
    public void updateAllEnemiesSingleEnemyTest() {
        Enemy topClone = new Enemy(top.getX(), top.getY());
        topClone.updatePos(player, world);

        int expectedX = topClone.getX();
        int expectedY = topClone.getY();

        world.spawnEnemy(top);

        //method
        world.updateAllEnemies(player);

        //value check

        assertEquals(expectedX, top.getX());
        assertEquals(expectedY, top.getY());
        assertTrue(world.isActive(top));
    }

    @Test
    public void updateAllEnemiesMultipleEnemyTest() {
        try {
            world.spawnEnemy(top);
            world.spawnEnemy(left);

            world.updateAllEnemies();
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }
    }
}
