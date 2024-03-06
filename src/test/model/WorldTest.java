package model;

import model.entities.Enemy;
import model.entities.Trap;
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

    Trap ctrTrap;

    @BeforeEach
    public void init() {
        world = new World(10, 10);

        int midX = world.getWidth()/2;
        int midY = world.getHeight()/2;

        player = new Player(midX, midY, 5);

        top = new Enemy(midX, 0);
        bot = new Enemy(midX, world.getHeight()-1);
        left = new Enemy(0, midY);
        right = new Enemy(world.getWidth()-1, midY);

        ctrTrap = new Trap(midX, midY);
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
        assertFalse(world.containsEnemyAt(0, 0));
        assertFalse(world.containsEnemyAt(world.getWidth()-1, 0));
        assertFalse(world.containsEnemyAt(0, world.getHeight()-1));
        assertFalse(world.containsEnemyAt(world.getWidth()-1, world.getHeight()-1));

        world.tickAllEntities(player);

        assertTrue(world.containsEnemyAt(0, 0));
        assertTrue(world.containsEnemyAt(world.getWidth()-1, 0));
        assertTrue(world.containsEnemyAt(0, world.getHeight()-1));
        assertTrue(world.containsEnemyAt(world.getWidth()-1, world.getHeight()-1));
    }

    @Test
    public void updateAllEnemiesSingleEnemyTest() {
        Enemy topClone = new Enemy(top.getX(), top.getY());
        topClone.tick(player, world);

        int expectedX = topClone.getX();
        int expectedY = topClone.getY();

        world.spawnEnemy(top);

        //method
        world.tickAllEntities(player);

        //value check

        assertEquals(expectedX, top.getX());
        assertEquals(expectedY, top.getY());
        assertTrue(world.isActive(top));
    }

    @Test
    public void updateAllEnemiesMultipleEnemyTest() {
        Enemy topClone = new Enemy(top.getX(), top.getY());
        Enemy leftClone = new Enemy(left.getX(), left.getY());

        topClone.tick(player, world);
        leftClone.tick(player, world);

        int expectedXTop = topClone.getX();
        int expectedYTop = topClone.getY();

        int expectedXLeft = leftClone.getX();
        int expectedYLeft = leftClone.getY();


        world.spawnEnemy(top);
        world.spawnEnemy(left);

        //method
        world.tickAllEntities(player);

        //test values
        assertEquals(expectedXTop, top.getX());
        assertEquals(expectedYTop, top.getY());

        assertEquals(expectedXLeft, left.getX());
        assertEquals(expectedYLeft, left.getY());
    }

    @Test
    public void updateAllEnemiesRespectsTopBoundaryTest() {
        Player outOfBoundsPlayer = new Player(world.getWidth() / 2, -10, 3);
        world.spawnEnemy(top);

        //Method
        world.tickAllEntities(outOfBoundsPlayer);

        //Values
        assertEquals(0, top.getY());
    }

    @Test
    public void updateAllEnemiesRespectsBottomBoundaryTest() {
        Player outOfBoundsPlayer = new Player(world.getWidth() / 2, world.getHeight()+10, 3);
        world.spawnEnemy(bot);

        //Method
        world.tickAllEntities(outOfBoundsPlayer);

        //Values
        assertEquals(world.getHeight()-1, bot.getY());
    }

    @Test
    public void updateAllEnemiesRespectsLeftBoundaryTest() {
        Player outOfBoundsPlayer = new Player(-10, world.getHeight() /2, 3);
        world.spawnEnemy(left);

        //Method
        world.tickAllEntities(outOfBoundsPlayer);

        //Values
        assertEquals(0, left.getX());
    }

    @Test
    public void updateAllEnemiesRespectsRightBoundaryTest() {
        Player outOfBoundsPlayer = new Player(world.getWidth() +10, world.getHeight()/2, 3);
        world.spawnEnemy(right);

        //Method
        world.tickAllEntities(outOfBoundsPlayer);

        //Values
        assertEquals(world.getHeight()-1, right.getX());
    }

    @Test
    public void isActiveActiveTest() {
        world.spawnEnemy(top);

        assertTrue(world.isActive(top));
    }

    @Test
    public void isActiveInactiveTest() {
        world.spawnEnemy(top);

        assertFalse(world.isActive(left));
    }

    @Test
    public void containsEnemyAtSingleEnemyCorrectPosTest() {
        world.spawnEnemy(top);

        assertTrue(world.containsEnemyAt(top.getX(), top.getY()));
    }

    @Test
    public void containsEnemyAtMultiEnemyCorrectPosTest() {
        world.spawnEnemy(left);
        world.spawnEnemy(right);
        world.spawnEnemy(bot);

        assertTrue(world.containsEnemyAt(right.getX(), right.getY()));
    }

    @Test
    public void containsEnemyAtSingleEnemyIncorrectPosTest() {
        world.spawnEnemy(top);

        assertFalse(world.containsEnemyAt(top.getX()+1, top.getY()+1));
    }

    @Test
    public void containsEnemyAtNoEnemiesAtPosTest() {
        world.spawnEnemy(top);
        world.spawnEnemy(bot);
        world.spawnEnemy(left);

        assertFalse(world.containsEnemyAt(top.getX()+1, top.getY()+1));
    }

    @Test
    public void containsEnemyAtNoEnemiesInListTest() {
        assertFalse(world.containsEnemyAt(top.getX()+1, top.getY()+1));
    }

    @Test
    public void getCenterAccuracyTest() {
        World EvenWEvenH = new World(10, 12);
        World EvenWOddH = new World(8, 11);
        World OddWEvenH = new World(9, 6);
        World OddWOddH = new World(7, 13);

        //Test Values
        assertEquals(5, EvenWEvenH.getCenter()[0]);
        assertEquals(6, EvenWEvenH.getCenter()[1]);

        assertEquals(4, EvenWOddH.getCenter()[0]);
        assertEquals(5, EvenWOddH.getCenter()[1]);

        assertEquals(4, OddWEvenH.getCenter()[0]);
        assertEquals(3, OddWEvenH.getCenter()[1]);

        assertEquals(3, OddWOddH.getCenter()[0]);
        assertEquals(6, OddWOddH.getCenter()[1]);
    }

    @Test
    void testContainsTrapAt() {
        assertFalse(world.containsTrapAt(ctrTrap.getX(), ctrTrap.getY()));

        world.spawnTrap(ctrTrap);

        assertTrue(world.containsTrapAt(ctrTrap.getX(), ctrTrap.getY()));

        assertFalse(world.containsTrapAt(ctrTrap.getX()+1, ctrTrap.getY()));
        assertFalse(world.containsTrapAt(ctrTrap.getX()-1, ctrTrap.getY()));
        assertFalse(world.containsTrapAt(ctrTrap.getX(), ctrTrap.getY()-1));
        assertFalse(world.containsTrapAt(ctrTrap.getX(), ctrTrap.getY()+1));
    }

    @Test
    void testSpawnTrap() {
        assertFalse(world.containsTrapAt(world.getCenter()[0], world.getCenter()[1]));

        world.spawnTrap(ctrTrap);

        assertTrue(world.containsTrapAt(world.getCenter()[0], world.getCenter()[1]));
    }

    // REQUIRES: no enemy is at center pos on map
    @Test
    void testTrapPersistence() {
        world.spawnTrap(ctrTrap);

        world.tickAllEntities(player);

        assertTrue(world.containsTrapAt(world.getCenter()[0], world.getCenter()[1]));
    }

    @Test
    void testConsumeTrap() {
        world.spawnTrap(ctrTrap);

        assertTrue(world.containsTrapAt(world.getCenter()[0], world.getCenter()[1]));

        world.consumeTrap(ctrTrap);

        assertFalse(world.containsTrapAt(world.getCenter()[0], world.getCenter()[1]));
    }

    @Test
    void testTickAllEntitiesConsumesTraps() {
        Trap topCatcher = new Trap(top.getX(), top.getY()+1); //Make sure trap is in path of enemy

        world.spawnTrap(topCatcher);
        world.tickAllEntities(player);

        assertFalse(world.isActive(top));
        assertFalse(world.containsTrapAt(topCatcher.getX(), topCatcher.getY()));
    }

    @Test
    void testTickAllEntitiesConsumesCorrectTrap() {
        Trap topCatcher = new Trap(top.getX(), top.getY()+1); //Make sure trap is in path of enemy

        world.spawnTrap(topCatcher);
        world.spawnTrap(ctrTrap);
        world.tickAllEntities(player);

        assertFalse(world.isActive(top));
        assertFalse(world.containsTrapAt(topCatcher.getX(), topCatcher.getY()));
        assertTrue(world.containsTrapAt(ctrTrap.getX(), ctrTrap.getY()));
    }
}
