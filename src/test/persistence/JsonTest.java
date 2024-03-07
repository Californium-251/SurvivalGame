package persistence;

import model.World;
import model.entities.TickedEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkEntity(String type, int x, int y, TickedEntity entity) {
        assertEquals(type, entity.getClass().toString());
        assertEquals(x, entity.getX());
        assertEquals(y, entity.getY());
    }
}
