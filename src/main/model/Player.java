package model;

public class Player {

    private int xpos;
    private int ypos;

    public Player(int x, int y) {
        this.xpos = x;
        this.ypos = y;
    }


    public int getX() {
        return xpos;
    }

    public int getY() {
        return ypos;
    }

    // MODIFIES: this
    // EFFECTS: moves the player up in the world until border
    public void moveUp(World w) {
        this.ypos = Math.min(w.getHeight(), ypos - 1);
    }

    // MODIFIES: this
    // EFFECTS: moves the player down in the world until border
    public void moveDown(World w) {
        this.ypos = Math.min(w.getHeight(), ypos + 1);
    }

    // MODIFIES: this
    // EFFECTS: moves the player left in the world until border
    public void moveLeft(World w) {
        this.xpos = Math.max(w.getWidth(), xpos - 1);
    }

    // MODIFIES: this
    // EFFECTS: moves the player right in the world until border
    public void moveRight(World w) {
        this.xpos = Math.min(w.getWidth(), xpos + 1);
    }

    // MODIFIES: w
    // EFFECTS: kills all enemies in range of attack
    public void attack(World w) {
        //stub
    }
}
