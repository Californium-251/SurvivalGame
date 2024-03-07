package persistence;

import model.Player;
import model.World;
import model.entities.Enemy;
import model.entities.TickedEntity;
import model.entities.Trap;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/*
 * JsonReader
 * --------------------
 * A class that represents a reader that reads a saved gamestate stored in file
 *
 * Huge inspiration from JsonSerializerDemo
 */
public class JsonReader {
    String fileSource;

    // EFFECTS: constructs reader to read from source
    public JsonReader(String source) {
        this.fileSource = source;
    }

    //EFFECTS: reads player from file and returns it;
    // IOException is thrown if error occurs in reading data
    public Player readPlayer() throws IOException {
        String jsonData = readFile(fileSource);
        JSONObject jsonObject = new JSONObject(jsonData);

        return parsePlayer(jsonObject);
    }

    //EFFECTS: parses player from JSON object and returns it
    private Player parsePlayer(JSONObject jsonObject) {
        int health = jsonObject.getInt("health");
        int x = jsonObject.getInt("x");
        int y = jsonObject.getInt("y");

        return new Player(x, y, health);
    }

    //EFFECTS: reads world from file and returns it;
    // IOException is thrown if error occurs in reading data
    public World readWorld() throws IOException {
        String jsonData = readFile(fileSource);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorld(jsonObject);
    }

    // EFFECTS: parses world data from jsonObject and returns the world
    private World parseWorld(JSONObject jsonObject) {
        int height = jsonObject.getInt("height");
        int width = jsonObject.getInt("width");

        World w = new World(width, height);
        spawnEntities(w, jsonObject);

        return w;
    }

    //MODIFIES: w
    //EFFECTS: spawns all entities stored in jsonObject
    private void spawnEntities(World w, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("entities");

        for (Object obj : jsonArray) {
            JSONObject nextEntity = (JSONObject) obj;
            int xpos = nextEntity.getInt("x");
            int ypos = nextEntity.getInt("y");

            String type = nextEntity.getString("type");

            if (type.equals("class model.entities.Enemy")) {
                w.spawnEnemy(new Enemy(xpos, ypos));
            } else {
                w.spawnTrap(new Trap(xpos, ypos));
            }
        }
    }


    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }
}
