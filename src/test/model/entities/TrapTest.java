package model.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrapTest extends TickedEntityTest {
    Trap tCTR;
    Trap tCTROffsetUp;
    Trap tCTROffsetLeft;
    Trap tCTROffsetRight;
    Trap tCTROffsetDown;

    @BeforeEach
    public void init() {
        super.init();

        tCTR = new Trap(eCTR.getX(), eCTR.getY());
        tCTROffsetUp = new Trap(eCTR.getX(), eCTR.getY()-1);
        tCTROffsetDown = new Trap(eCTR.getX(), eCTR.getY()+1);
        tCTROffsetLeft = new Trap(eCTR.getX()-1, eCTR.getY());
        tCTROffsetRight = new Trap(eCTR.getX()+1, eCTR.getY());

        testWorld.spawnEnemy((Enemy) eCTR);
    }

    @Test
    void testTrapKillsEnemy() {
        tCTR.tick(pTop, testWorld);

        assertFalse(testWorld.containsEnemyAt(tCTR.getX(), tCTR.getY()));
    }

    @Test
    void testTrapGetsConsumed() {
        tCTR.tick(pTop, testWorld);

        assertFalse(testWorld.containsTrapAt(tCTR.getX(), tCTR.getY()));
    }

    @Test
    void testTrapsDoNotKillAdjacentTiles() {
        assertTrue(testWorld.containsEnemyAt(eCTR.getX(), eCTR.getY()));

        tCTROffsetUp.tick(pTop, testWorld);
        tCTROffsetDown.tick(pTop, testWorld);
        tCTROffsetLeft.tick(pTop, testWorld);
        tCTROffsetRight.tick(pTop, testWorld);

        assertTrue(testWorld.containsEnemyAt(eCTR.getX(), eCTR.getY()));
    }
}
