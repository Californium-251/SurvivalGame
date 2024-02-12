package model;

import org.junit.jupiter.api.BeforeEach;

public class PlayerTest {
    Player player;

    @BeforeEach
    public void init() {
        player = new Player(0, 0, 3);
    }


}
