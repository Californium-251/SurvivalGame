package model;

import model.entities.Enemy;
import model.entities.Trap;

import java.util.ArrayList;
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
 *
 * This class is also where each enemy and trap is stored.
 */
public class World {
    private final int height;
    private final int width;

    private final List<Enemy> activeEnemies = new ArrayList<>();
    private final List<Trap> activeTraps = new ArrayList<>();

    // EFFECTS: instantiates a World with a given height and width
    //          in tiles.
    public World(int w, int h) {
        this.height = h;
        this.width = w;
    }

    // MODIFIES: this
    // EFFECTS: updates all non-player objects on screen
    public void tickAllEntities(Player p) {
        updateAllEnemies(p);
        updateAllTraps(p);
        updateEnemySpawnCycle();
    }

    // MODIFIES: this
    // EFFECTS: If there are no active enemies, spawn a new set of 4, one in each corner of the world.
    private void updateEnemySpawnCycle() {
        //current spawning mechanics are mid; will probably change at a later stage
        if (activeEnemies.isEmpty()) {
            spawnEnemy(new Enemy(0, 0));
            spawnEnemy(new Enemy(width - 1, 0));
            spawnEnemy(new Enemy(0, height - 1));
            spawnEnemy(new Enemy(width - 1, height - 1));
        }
    }

    // MODIFIES: this
    // EFFECTS: updates all enemies in activeEnemies in the order of when they were placed into the list.
    private void updateAllEnemies(Player p) {
        for (Enemy enemy : activeEnemies) {
            enemy.tick(p, this);
            enforceWorldBounds(enemy);
        }
    }

    // MODIFIES: this
    // EFFECTS: updates all traps in activeTraps in the order of when they were placed.
    private void updateAllTraps(Player p) {
        Trap curTrap;
        for (int i = 0; i < activeTraps.size(); i++) {
            curTrap = activeTraps.get(i);
            boolean caughtEnemy = curTrap.tick(p, this);

            if (caughtEnemy) {
                i--;
            }
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

    // MODIFIES: this
    // EFFECTS: spawns a trap at the given x and y coordinates
    public void spawnTrap(Trap trap) {
        activeTraps.add(trap);
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

    // EFFECTS: returns whether a trap exists at the given coordinates
    public boolean containsTrapAt(int x, int y) {
        for (Trap t : activeTraps) {
            if (t.isAt(x, y)) {
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

    // MODIFIES: this
    // EFFECTS: removes the given trap from the world
    public void consumeTrap(Trap trap) {
        activeTraps.remove(trap); //MAKE SURE THIS IS BASED ON OBJECT ID AND NOT ANY EQUALS BULLSHIT
    }
}
