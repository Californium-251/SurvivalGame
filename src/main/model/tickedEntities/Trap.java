package model.tickedEntities;

import model.Player;
import model.World;

/*
 * Trap
 * -----------------
 * This class represented the abstraction of the traps the player places down.
 *
 * When updated, these check if an enemy is standing on them and activate if there is.
 * If a trap is activated, it instantly kills whichever enemy was standing on it.
 *
 * At least for now, there can be more than one trap per tile without issue
 */
public class Trap extends TickedEntity {

    public Trap(int x, int y) {
        super(x, y);
    }

    // MODIFIES: world
    // EFFECTS: if there is an enemy standing on trap, kill enemy and consume this trap
    @Override
    public boolean updatePos(Player player, World world) {
        return false; //TODO: IMPLEMENT TRAP AI
    }
}
