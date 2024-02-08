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
}
