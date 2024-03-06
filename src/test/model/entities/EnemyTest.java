package model.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnemyTest extends TickedEntityTest {

    @BeforeEach
    public void init() {
        super.init();
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
}
