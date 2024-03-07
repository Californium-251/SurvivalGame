package persistence;

import model.Player;
import model.World;
import model.entities.Enemy;
import model.entities.TickedEntity;
import model.entities.Trap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {
    World world;
    Player player;

    JsonReader reader;

    final int WORLD_WIDTH = 10;
    final int WORLD_HEIGHT = 9;

    @BeforeEach
    void init() {
        world = new World(WORLD_WIDTH, WORLD_HEIGHT);
        player = new Player(5, 4, 3);
    }

    @Test
    void testReadNonExistentFile() {
        String filepath = "./data/nonExistentFile.json";
        try {
            reader = new JsonReader(filepath);

            World w = reader.readWorld();
            fail("Exception should be thrown");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testReadEmptyWorld() {
        String filepath = "./data/testWriterEmptyWorld.json";

        try {
            reader = new JsonReader(filepath);
            world = reader.readWorld();
            player = reader.readPlayer();

            assertEquals(9, world.getHeight());
            assertEquals(10, world.getWidth());
            assertEquals(0, world.numEnemies());
            assertEquals(0, world.numTraps());

            assertEquals(5, player.getX());
            assertEquals(2, player.getY());
            assertEquals(3, player.getHealth());
        } catch (IOException e) {
            fail("File couldn't be read");
        }
    }

    @Test
    void testReadGeneralWorld() {
        String filepath = "./data/testWriterGeneralWorld.json";

        String enemyClassString = new Enemy(0, 0).getClass().toString();
        String trapClassString = new Trap(0, 0).getClass().toString();

        try {
            reader = new JsonReader(filepath);
            world = reader.readWorld();
            player = reader.readPlayer();
            List<TickedEntity> entities = world.getEntities();

            assertEquals(9, world.getHeight());
            assertEquals(10, world.getWidth());
            assertEquals(2, world.numEnemies());
            assertEquals(2, world.numTraps());

            assertEquals(5, player.getX());
            assertEquals(2, player.getY());
            assertEquals(3, player.getHealth());

            //Order of insertion matters only within each subtype. Otherwise, order is Enemy -> Trap

            checkEntity(enemyClassString, 7, 7, entities.get(0));
            checkEntity(enemyClassString, 2, 4, entities.get(1));
            checkEntity(trapClassString, 4, 4, entities.get(2));
            checkEntity(trapClassString, 4, 5, entities.get(3));
        } catch (IOException e) {
            fail("Couldn't read from the file");
        }
    }
}
