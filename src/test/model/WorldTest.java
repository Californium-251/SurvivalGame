//package model;
//
//
//import exceptions.EnemyAlreadyActiveException;
//import exceptions.EnemyOutOfBoundsException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class WorldTest {
//    World world;
//
//    Player player;
//
//    Enemy top;
//    Enemy bot;
//    Enemy left;
//    Enemy right;
//
//    Enemy outOfBounds;
//
//    @BeforeEach
//    public void init() {
//        world = new World();
//
//        int midX = world.getWidth()/2;
//        int midY = world.getHeight()/2;
//
//        player = new Player(midX, midY, 5);
//
//        top = new Enemy(midX, 0);
//        bot = new Enemy(midX, world.getHeight()-1);
//        left = new Enemy(0, midY);
//        right = new Enemy(world.getWidth()-1, midY);
//    }
//
//    @Test
//    public void spawnEnemyInsideBoundsTopTest() {
//        try {
//            world.spawnEnemy(top);
//        } catch (EnemyOutOfBoundsException e) {
//            fail("Enemy incorrectly out of bounds at top");
//        } catch (EnemyAlreadyActiveException e) {
//            fail("Enemy incorrectly stated as already active");
//        }
//
//        assertTrue(world.isActive(top));
//    }
//
//    @Test
//    public void spawnEnemyInsideBoundsBotTest() {
//        try {
//            world.spawnEnemy(bot);
//        } catch (EnemyOutOfBoundsException e) {
//            fail("Enemy incorrectly out of bounds at bottom");
//        } catch (EnemyAlreadyActiveException e) {
//            fail("Enemy incorrectly stated as already active");
//        }
//
//        assertTrue(world.isActive(bot));
//    }
//
//    @Test
//    public void spawnEnemyInsideBoundsLeftTest() {
//        try {
//            world.spawnEnemy(left);
//        } catch (EnemyOutOfBoundsException e) {
//            fail("Enemy incorrectly out of bounds at left");
//        } catch (EnemyAlreadyActiveException e) {
//            fail("Enemy incorrectly stated as already active");
//        }
//
//        assertTrue(world.isActive(left));
//    }
//
//    @Test
//    public void spawnEnemyInsideBoundsRightTest() {
//        try {
//            world.spawnEnemy(right);
//        } catch (EnemyOutOfBoundsException e) {
//            fail("Enemy incorrectly out of bounds at right");
//        } catch (EnemyAlreadyActiveException e) {
//            fail("Enemy incorrectly stated as already active");
//        }
//
//        assertTrue(world.isActive(right));
//    }
//
//    @Test
//    public void spawnEnemyOutOfBoundsTopTest() {
//        outOfBounds = new Enemy(world.getWidth()/2, -1);
//        boolean caught = false;
//
//        try {
//            world.spawnEnemy(outOfBounds);
//        } catch (EnemyOutOfBoundsException e) {
//            caught = true;
//        } catch (EnemyAlreadyActiveException e) {
//            fail("Enemy incorrectly stated as already active");
//        }
//        finally {
//            assertTrue(caught);
//            assertFalse(world.isActive(outOfBounds));
//        }
//    }
//
//    @Test
//    public void spawnEnemyOutOfBoundsBotTest() {
//        outOfBounds = new Enemy(world.getWidth()/2, world.getHeight());
//        boolean caught = false;
//
//        try {
//            world.spawnEnemy(outOfBounds);
//        } catch (EnemyOutOfBoundsException e) {
//            caught = true;
//        } catch (EnemyAlreadyActiveException e) {
//            fail("Enemy incorrectly stated as already active");
//        }
//        finally {
//            assertTrue(caught);
//            assertFalse(world.isActive(outOfBounds));
//        }
//    }
//
//    @Test
//    public void spawnEnemyOutOfBoundsLeftTest() {
//        outOfBounds = new Enemy(-1, world.getHeight()/2);
//        boolean caught = false;
//
//        try {
//            world.spawnEnemy(outOfBounds);
//        } catch (EnemyOutOfBoundsException e) {
//            caught = true;
//        } catch (EnemyAlreadyActiveException e) {
//            fail("Enemy incorrectly stated as already active");
//        }
//        finally {
//            assertTrue(caught);
//            assertFalse(world.isActive(outOfBounds));
//        }
//    }
//
//    @Test
//    public void spawnEnemyOutOfBoundsRightTest() {
//        outOfBounds = new Enemy(world.getWidth(), world.getHeight()/2);
//        boolean caught = false;
//
//        try {
//            world.spawnEnemy(outOfBounds);
//        } catch (EnemyOutOfBoundsException e) {
//            caught = true;
//        } catch (EnemyAlreadyActiveException e) {
//            fail("Enemy incorrectly stated as already active");
//        }
//        finally {
//            assertTrue(caught);
//            assertFalse(world.isActive(outOfBounds));
//        }
//    }
//
//    @Test
//    public void spawnEnemyAddSameEnemyTest() {
//        boolean caught = false;
//
//        try {
//            world.spawnEnemy(top);
//        } catch (EnemyOutOfBoundsException e) {
//            fail("Enemy incorrectly stated as out of bounds");
//        } catch (EnemyAlreadyActiveException e) {
//            caught = true;
//        }
//        finally {
//            assertTrue(caught);
//            assertFalse(world.isActive(top));
//        }
//    }
//
//    @Test
//    public void killEnemyRemovesEnemyTest() {
//        try {
//            world.spawnEnemy(top);
//        } catch (EnemyAlreadyActiveException | EnemyOutOfBoundsException e) {
//            fail("Exception incorrectly thrown");
//        }
//
//        world.killEnemy(top);
//
//        assertFalse(world.isActive(top));
//    }
//
//    @Test
//    public void killEnemyRemovesOnlyEnemyTest() {
//        try {
//            world.spawnEnemy(left);
//            world.spawnEnemy(top);
//        } catch (EnemyAlreadyActiveException | EnemyOutOfBoundsException e) {
//            fail("Exception incorrectly thrown");
//        }
//
//        world.killEnemy(left);
//
//        assertTrue(world.isActive(top));
//        assertFalse(world.isActive(left));
//    }
//
//    @Test
//    public void killEnemyInactiveEnemyTest() {
//        try {
//            world.spawnEnemy(top);
//        } catch (EnemyAlreadyActiveException | EnemyOutOfBoundsException e) {
//            fail("Exception incorrectly thrown");
//        }
//
//        try {
//            world.killEnemy(left);
//        } catch (Exception e) {
//            fail("Method failed");
//        }
//
//        assertTrue(world.isActive(top));
//    }
//
//    @Test
//    public void updateAllEnemiesEmptyListTest() {
//        try {
//            world.updateAllEnemies();
//        } catch (Exception e) {
//            fail("Unexpected exception thrown");
//        }
//    }
//
//    @Test
//    public void updateAllEnemiesSingleEnemyTest() {
//        try {
//            world.spawnEnemy(top);
//
//            world.updateAllEnemies();
//        } catch (Exception e) {
//            fail("Unexpected exception thrown");
//        }
//    }
//
//    @Test
//    public void updateAllEnemiesMultipleEnemyTest() {
//        try {
//            world.spawnEnemy(top);
//            world.spawnEnemy(left);
//
//            world.updateAllEnemies();
//        } catch (Exception e) {
//            fail("Unexpected exception thrown");
//        }
//    }
//}
