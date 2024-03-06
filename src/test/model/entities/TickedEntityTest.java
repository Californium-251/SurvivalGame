package model.entities;

import model.Player;
import model.World;
import model.entities.Enemy;
import model.entities.TickedEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TickedEntityTest {
    protected World testWorld;

    protected int centerX;
    protected int centerY;

    protected Player pCenter;

    Player pTop;
    Player pBot;
    Player pLef;
    Player pRig;

    protected TickedEntity eCTR;
    protected TickedEntity eTL;
    protected TickedEntity eTC;
    protected TickedEntity eTR;
    protected TickedEntity eBC;

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
    public void isAtAccuracyTest() {
        assertTrue(eCTR.isAt(centerX, centerY));
        assertTrue(eTL.isAt(0, 0));

        assertFalse(eBC.isAt(0, 0));
        assertFalse(eTR.isAt(centerX, 0));
    }
}
