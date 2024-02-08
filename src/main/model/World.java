package model;

import java.util.LinkedList;
import java.util.List;

public class World {
    private int height;
    private int width;

    private List<Enemy> activeEnemies = new LinkedList<>();

    public World() {
        this.height = 10;
        this.width = 10;
    }

    // EFFECTS: instantiates a World with a given height and width
    //          in tiles.
    public World(int h, int w) {
        this.height = h;
        this.width = w;
    }

    // EFFECTS: updates all enemies in activeEnemies
    public void updateAllEnemies() {
        //stub
    }

    // REQUIRES: e has been instantiated
    // MODIFIES: e
    // EFFECTS: clamps the x and y pos of enemy to conform to height and width of world
    private void enforceWorldBounds(Enemy e) {
        //stub
    }

}
