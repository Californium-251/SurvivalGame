package model;

import java.util.LinkedList;
import java.util.List;

/*
 * World
 * ------------------
 * This is the class that represents the world with all the enemies in it
 *
 * Although this class does not represent the enemies, it is responsible
 *    for telling each enemy what it can and cannot do
 *
 * This class contains information about the bounds of the playing field for
 *    both the player and enemy classes.
 */
public class World {
    private int height;
    private int width;

    private List<Enemy> activeEnemies = new LinkedList<>();

    public World() {
        this(10, 10);
    }

    // EFFECTS: instantiates a World with a given height and width
    //          in tiles.
    public World(int w, int h) {
        this.height = h;
        this.width = w;
    }

    // MODIFIES: this
    // EFFECTS: updates all enemies in activeEnemies in the order of when they were placed into the list.
    //          If there are no active enemies, spawn a new set of 4, one in each corner of the world.
    public void updateAllEnemies(Player p) {
        //current spawning mechanics are mid; will probably change at a later stage
        if (activeEnemies.isEmpty()) {
            spawnEnemy(new Enemy(0, 0));
            spawnEnemy(new Enemy(width - 1, 0));
            spawnEnemy(new Enemy(0, height - 1));
            spawnEnemy(new Enemy(width - 1, height - 1));
            return;
        }

        for (Enemy enemy : activeEnemies) {
            enemy.updatePos(p, this);
            enforceWorldBounds(enemy);
        }
    }

    // MODIFIES: e
    // EFFECTS: clamps the x and y pos of enemy to conform to height and width of world
    private void enforceWorldBounds(Enemy e) {
        //X position
        if (e.getX() >= this.width) {
            e.setX(this.width - 1);
        } else if (e.getX() < 0) {
            e.setX(0);
        }

        //Y position
        if (e.getY() >= this.height) {
            e.setY(this.height - 1);
        } else if (e.getY() < 0) {
            e.setY(0);
        }
    }


    // MODIFIES: this
    // EFFECTS: adds e to activeEnemies.
    public void spawnEnemy(Enemy e) {
        activeEnemies.add(e);
    }

    // MODIFIES: this
    // EFFECTS: removes enemy from the world at the given position,
    //          does nothing if there is no enemy at the given position
    public void killEnemyAt(int x, int y) {
        for (int i = 0; i < activeEnemies.size(); i++) {
            if (activeEnemies.get(i).getX() == x && activeEnemies.get(i).getY() == y) {
                activeEnemies.remove(i);
                i--;
            }
        }
    }

    // EFFECTS: returns whether e is active in the world
    public boolean isActive(Enemy e) {
        return activeEnemies.contains(e);
    }

    // EFFECTS: returns whether an enemy exists at the given coordinates
    public boolean containsEnemyAt(int x, int y) {
        for (Enemy enemy : activeEnemies) {
            if (enemy.isAt(x, y)) {
                return true;
            }
        }

        return false;
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
