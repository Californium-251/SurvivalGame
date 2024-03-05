package model.tickedEntities;

import model.Player;
import model.World;

/*
 * Enemy
 * --------------------
 * This class represents the abstraction of an enemy
 *
 * It contains some important information about each enemy, such as their position (in superclass) (and potentially
 *  in the future their health)
 *
 * This class also stores the enemies' behaviour (although this may be moved to a specialized AI class in the future)
 */
public class Enemy extends TickedEntity {

    public Enemy(int x, int y) {
        super(x, y);
    }

    // REQUIRES: Player is on screen
    // MODIFIES: this
    // EFFECTS: updates enemy x and y using enemy AI, returns true if the enemy moves
    @Override
    public boolean updatePos(Player player, World world) {
        // Hopefully will be improved at some point
        if (player.getX() > xpos && !(world.containsEnemyAt(xpos + 1, ypos))) {
            xpos++;
            return true;
        } else if (player.getX() < xpos && !world.containsEnemyAt(xpos - 1, ypos)) {
            xpos--;
            return true;
        }

        if (player.getY() > ypos && !(world.containsEnemyAt(xpos, ypos + 1))) {
            ypos++;
            return true;
        } else if (player.getY() < ypos && !world.containsEnemyAt(xpos, ypos - 1)) {
            ypos--;
            return true;
        }
        return false;
    }
}
