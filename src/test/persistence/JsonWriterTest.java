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

public class JsonWriterTest extends JsonTest{
    World world;
    Player player;

    JsonWriter writer;
    JsonReader reader;

    @BeforeEach
    void init() {
        world = new World(10, 9);
        player = new Player(5, 2, 3);
    }

    @Test
    void testWriterInvalidFile() {
        try {
            writer = new JsonWriter("./data/highly\0illegal:-file name.json");
            writer.open();
            fail("IOException expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testWriterEmptyWorld() {
        String filepath = "./data/testWriterEmptyWorld.json";

        try {
            writer = new JsonWriter(filepath);
            writer.open();
            writer.write(player, world);
            writer.close();

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
            fail("Exception should not be thrown");
        }
    }

    @Test
    void testWriterGeneralWorld() {
        String filepath = "./data/testWriterGeneralWorld.json";

        String enemyClassString = new Enemy(0, 0).getClass().toString();
        String trapClassString = new Trap(0, 0).getClass().toString();

        world.spawnTrap(new Trap(4, 4));
        world.spawnEnemy(new Enemy(7, 7));
        world.spawnEnemy(new Enemy(2, 4));
        world.spawnTrap(new Trap(4, 5));

        try {
            writer = new JsonWriter(filepath);
            writer.open();
            writer.write(player, world);
            writer.close();

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
            fail("Exception should not have been thrown");
        }
    }
}
