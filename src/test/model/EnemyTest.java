package model;

import model.entities.Enemy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnemyTest {
    public World testWorld;

    public int centerX;
    public int centerY;

    public Player pCenter;

    Player pTop;
    Player pBot;
    Player pLef;
    Player pRig;

    public Enemy eCTR;
    public Enemy eTL;
    public Enemy eTC;
    public Enemy eTR;
    public Enemy eBC;

    @BeforeEach
    public void init() {
        testWorld = new World(10, 10); //Define size manually to avoid issues in tests where enemies may overlap

        centerX = testWorld.getCenter()[0];
        centerY = testWorld.getCenter()[1];

        pCenter = new Player(testWorld.getCenter()[0], testWorld.getCenter()[1], 3);
        pTop = new Player(centerX, 0, 3);
        pBot = new Player(centerX, testWorld.getHeight()-1, 3);
        pLef = new Player(0, centerY, 3);
        pRig = new Player(testWorld.getWidth()-1, centerY, 3);

        eCTR = new Enemy(centerX,centerY);
        eTL = new Enemy(0, 0);
        eTC = new Enemy(centerX, 0);
        eTR = new Enemy(testWorld.getWidth()-1, 0);
        eBC = new Enemy(centerX, testWorld.getHeight()-1);
    }

    @Test
    public void updatePosMoveLeftTest() {
        eTR.tick(pCenter, testWorld);

        assertEquals(testWorld.getWidth()-2, eTR.getX());
    }

    @Test
    public void updatePosMoveRightTest() {
        eTL.tick(pCenter, testWorld);

        assertEquals(1, eTL.getX());
    }

    @Test
    public void updatePosMoveDownTest() {
        eTC.tick(pCenter, testWorld);

        assertEquals(1, eTC.getY());
    }

    @Test
    public void updatePosMoveUpTest() {
        eBC.tick(pCenter, testWorld);

        assertEquals(testWorld.getHeight()-2, eBC.getY());
    }

    @Test
    public void updatePosPrioritizesXTest() {
        eTL.tick(pCenter, testWorld);
        eTC.tick(pCenter, testWorld);
        eTR.tick(pCenter, testWorld);
        eBC.tick(pCenter, testWorld);

        assertEquals(1, eTL.getX());
        assertEquals(0, eTL.getY());

        assertEquals(centerX, eTC.getX());
        assertEquals(1, eTC.getY());

        assertEquals(testWorld.getWidth()-2, eTR.getX());
        assertEquals(0, eTR.getY());

        assertEquals(centerX, eBC.getX());
        assertEquals(testWorld.getHeight()-2, eBC.getY());
    }



    @Test
    public void updatePosDoesNotTrampleEnemyUpTest() {
        Enemy eBlockingTop = new Enemy(centerX, centerY-1);
        testWorld.spawnEnemy(eBlockingTop);

        //Run method
        assertFalse(eCTR.tick(pTop, testWorld));

        //Test values
        assertEquals(centerX, eCTR.getX());
        assertEquals(centerY, eCTR.getY());
    }

    @Test
    public void updatePosDoesNotTrampleEnemyDownTest() {
        Enemy eBlockingBot = new Enemy(centerX, centerY+1);
        testWorld.spawnEnemy(eBlockingBot);

        //Run method
        assertFalse(eCTR.tick(pBot, testWorld));

        //Test values
        assertEquals(centerX, eCTR.getX());
        assertEquals(centerY, eCTR.getY());
    }

    @Test
    public void updatePosDoesNotTrampleEnemyRightTest() {
        Enemy eBlockingRig = new Enemy(centerX+1, centerY);
        testWorld.spawnEnemy(eBlockingRig);

        //Run method
        assertFalse(eCTR.tick(pRig, testWorld));

        //Test values
        assertEquals(centerX, eCTR.getX());
        assertEquals(centerY, eCTR.getY());
    }

    @Test
    public void updatePosDoesNotTrampleEnemyLeftTest() {
        Enemy eBlockingLef = new Enemy(centerX-1, centerY);
        testWorld.spawnEnemy(eBlockingLef);

        //Run method
        assertFalse(eCTR.tick(pLef, testWorld));

        //Test values
        assertEquals(centerX, eCTR.getX());
        assertEquals(centerY, eCTR.getY());
    }

    @Test
    public void isAtAccuracyTest() {
        assertTrue(eCTR.isAt(centerX, centerY));
        assertTrue(eTL.isAt(0, 0));

        assertFalse(eBC.isAt(0, 0));
        assertFalse(eTR.isAt(centerX, 0));
    }

}
