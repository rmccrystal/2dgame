package mccrystal.ryan.worlds;

import java.awt.Color;
import mccrystal.ryan.Game;
import mccrystal.ryan.World;

public class TestLevel extends World {
    public TestLevel(Game game, boolean active) {
        super(game, active, 0.5f, 0.99f, Color.BLACK);
    }
}