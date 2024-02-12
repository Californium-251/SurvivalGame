package model;

public class Player {
    private int health;

    private int xpos;
    private int ypos;

    public Player(int x, int y, int health) {
        this.xpos = x;
        this.ypos = y;
        this.health = health;
    }


    public int getX() {
        return xpos;
    }

    public int getY() {
        return ypos;
    }

    public int getHealth() {
        return health;
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

    //MODIFIES: this
    // EFFECTS: decreases health by 1
    public void takeDamage() {
        this.health--;
    }

    //EFFECTS: returns whether or not the player has died
    public boolean isDead() {
        return this.health <= 0;
    }
}
