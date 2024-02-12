package model;

public class Enemy {
    private int xpos;
    private int ypos;


    public Enemy() {
        this(0, 0);
    }

    public Enemy(int x, int y) {
        this.xpos = x;
        this.ypos = y;
    }

    // REQUIRES: Player is on screen
    // MODIFIES: this
    // EFFECTS: updates enemy x and y using enemy AI
    public void updatePos(Player player, World world) {
        if (player.getX() > xpos && !(world.containsEnemyAt(xpos + 1, ypos))) {
            xpos++;
        } else if (player.getX() < xpos && !world.containsEnemyAt(xpos - 1, ypos)) {
            xpos--;
        }

        if (player.getY() > ypos && !(world.containsEnemyAt(xpos, ypos + 1))) {
            ypos++;
        } else if (player.getY() < ypos && !world.containsEnemyAt(xpos, ypos - 1)) {
            ypos--;
        }
    }

    public int getX() {
        return this.xpos;
    }

    public int getY() {
        return this.ypos;
    }

    public void setX(int x) {
        this.xpos = x;
    }

    public void setY(int y) {
        this.ypos = y;
    }

    //EFFECTS: returns whether the enemy is at the specified x and y coordinates
    public boolean isAt(int x, int y) {
        return  xpos == x && ypos == y;
    }
}
