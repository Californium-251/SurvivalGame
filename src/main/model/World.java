package model;

import model.tickedEntities.Enemy;
import model.tickedEntities.TickedEntity;

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
 * This class also stores the lists of enemies and traps on the map.
 */
public class World {
    private final int height;
    private final int width;

    private final List<TickedEntity> activeEntities = new ArrayList<>();

    // EFFECTS: instantiates a World with a given height and width
    //          in tiles.
    public World(int w, int h) {
        this.height = h;
        this.width = w;
    }

    // MODIFIES: this
    // EFFECTS: updates all enemies in activeEnemies in the order of when they were placed into the list.
    //          If there are no active enemies, spawn a new set of 4, one in each corner of the world.
    public void tickAllEntities(Player p) {
        //current spawning mechanics are mid; will probably change at a later stage
        if (!enemyAlive()) {
            spawnEnemy(new Enemy(0, 0));
            spawnEnemy(new Enemy(width - 1, 0));
            spawnEnemy(new Enemy(0, height - 1));
            spawnEnemy(new Enemy(width - 1, height - 1));
            return;
        }

        for (TickedEntity entity : activeEntities) {
            entity.tick(p, this);

            if (entity instanceof Enemy) {
                enforceWorldBounds((Enemy) entity); //casting is ok because we checked Type
            }
        }
    }

    // EFFECTS: returns true if any element of activeEntities is an Enemy; otherwise false
    private boolean enemyAlive() {
        for (TickedEntity entity : activeEntities) {
            if (entity instanceof Enemy) {
                return true;
            }
        }
        return false;
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
        activeEntities.add(e);
    }

    // MODIFIES: this
    // EFFECTS: removes enemy from the world at the given position,
    //          does nothing if there is no enemy at the given position
    public void killEnemyAt(int x, int y) {
        for (int i = 0; i < activeEntities.size(); i++) {
            if (activeEntities.get(i).getX() == x && activeEntities.get(i).getY() == y) {
                activeEntities.remove(i);
                i--;
            }
        }
    }

    // EFFECTS: returns whether e is active in the world
    public boolean isActive(Enemy e) {
        return activeEntities.contains(e);
    }

    // EFFECTS: returns whether an enemy exists at the given coordinates
    public boolean containsEnemyAt(int x, int y) {
        for (TickedEntity entity : activeEntities) {
            if (entity instanceof Enemy && entity.isAt(x, y)) {
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
