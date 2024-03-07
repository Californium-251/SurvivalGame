package persistence;

import model.Player;
import model.World;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/*
 * JsonWriter
 * ----------------
 * Represents a writer that writes the JSON representation
 * of the world to a file
 *
 * Huge inspiration taken from JSONSerializationDemo project
 */
public class JsonWriter {
    private static final int INDENT = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: stores the JSON representations of the world and player in a file
    public void write(Player p, World w) {
        JSONObject worldJson = w.toJson();
        JSONObject playerJson = p.toJson();

        saveToFile(worldJson.toString(INDENT), playerJson.toString(INDENT));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String worldData, String playerData) {
        writer.print(appendJsonStrings(worldData, playerData));
    }

    private String appendJsonStrings(String a, String b) {
        String result = a.substring(0, a.length() - 2) + ",";
        result += b.substring(1);
        return result;
    }
}
