package persistence;

import org.json.JSONObject;

/*
 * Writable
 * ---------------
 * This interface represents anything that can be written to a JSON object
 *
 * Inspiration from JSONSerializationDemo
 */
public interface Writable {

    //EFFECTS: returns this as JSON object
    JSONObject toJson();
}
