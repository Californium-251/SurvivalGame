package ui;

import model.Player;
import model.World;

/*
 * TerminalInterface
 * ------------------------
 * This is the class that handles I/O and ticking on the terminal based-game
 *
 * Every time the player provides a valid input, the game ticks
 *  - Player Actions are processed
 *  - Enemies move
 *  - Important values change
 *
 * After every input, the screen is reprinted with the current gamestate
 *
 * Player is represented with character 'P'
 * Enemies are represented by 'E'
 *
 */
public class TerminalInterface {
    World world;
    Player player;

    public TerminalInterface() {
        this.init();
    }


    private void init() {
        world = new World();
        player = new Player(world.getCenter()[0], world.getCenter()[1]);
    }
}
