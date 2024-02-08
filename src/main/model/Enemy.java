package model;

public class Enemy {
    private int xpos;
    private int ypos;


    public Enemy() {
        xpos = 0;
        ypos = 0;
    }

    // REQUIRES: Player is on screen
    // MODIFIES: this
    // EFFECTS: updates enemy x and y using enemy AI
    public void updatePos() {
        //stub
    }

    public void setX(int x) {
        this.xpos = x;
    }

    public void setY(int y) {
        this.ypos = y;
    }
}
