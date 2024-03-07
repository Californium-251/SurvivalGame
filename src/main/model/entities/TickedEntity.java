package model.entities;

import model.Player;
import model.World;
import org.json.JSONObject;
import persistence.Writable;

/*
 * TickedEntity
 * -------------------
 * This is the abstract class representing some arbitrary mobile piece of the world that gets updated every game tick
 *
 * This provides some necessary methods and gives abstract methods for others.
 *
 * Also contains universal data such as xpos and ypos
 */
public abstract class TickedEntity implements Writable {
    protected int xpos;
    protected int ypos;

    public TickedEntity(int x, int y) {
        this.xpos = x;
        this.ypos = y;
    }

    public abstract boolean tick(Player player, World world);

    public int getX() {
        return this.xpos;
    }

    public int getY() {
        return this.ypos;
    }

    public void setX(int x) {
        this.xpos = x;
    }

    public void setY(int y) {
        this.ypos = y;
    }

    //EFFECTS: returns whether the enemy is at the specified x and y coordinates
    public boolean isAt(int x, int y) {
        return  xpos == x && ypos == y;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("x", this.getX());
        json.put("y", this.getY());
        json.put("type", this.getClass());

        return json;
    }
}
