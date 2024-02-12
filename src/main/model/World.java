package model;

import exceptions.EnemyAlreadyActiveException;
import exceptions.EnemyOutOfBoundsException;

import java.util.LinkedList;
import java.util.List;

public class World {
    private int height;
    private int width;

    private List<Enemy> activeEnemies = new LinkedList<>();

    public World() {
        this(20, 20);
    }

    // EFFECTS: instantiates a World with a given height and width
    //          in tiles.
    public World(int h, int w) {
        this.height = h;
        this.width = w;
    }

    // MODIFIES: instanceOf Enemy
    // EFFECTS: updates all enemies in activeEnemies
    public void updateAllEnemies() {
        //TODO: COMPLETE IMPLEMENTATION
    }

    // MODIFIES: e
    // EFFECTS: clamps the x and y pos of enemy to conform to height and width of world
    private void enforceWorldBounds(Enemy e) {
        //TODO: COMPLETE IMPLEMENTATION
    }


    // MODIFIES: this
    // EFFECTS: throws EnemyOutOfBoundsException if enemy is outside world bounds and does nothing,
    //          throws EnemyAlreadyActiveException if enemy is already in activeEnemies and does nothing,
    //          otherwise, adds new enemy to world
    public void spawnEnemy(Enemy e) throws
            EnemyAlreadyActiveException, EnemyOutOfBoundsException {
        //TODO: COMPLETE IMPLEMENTATION
    }

    // MODIFIES: this
    // EFFECTS: removes enemy from the world,
    //          does nothing if enemy is not currently active
    public void killEnemy(Enemy e) {
        //TODO: COMPLETE IMPLEMENTATION
    }

    // EFFECTS: returns whether e is active in the world
    public boolean isActive(Enemy e) {
        return activeEnemies.contains(e);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    // EFFECTS: returns the center of the current world {x, y}
    public int[] getCenter() {
        return new int[] {this.width / 2, this.height / 2};
    }
}
