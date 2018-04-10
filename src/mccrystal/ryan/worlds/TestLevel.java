package mccrystal.ryan.worlds;

import java.awt.Color;
import mccrystal.ryan.Game;
import mccrystal.ryan.World;
import mccrystal.ryan.entities.Enemy;
import mccrystal.ryan.entities.Ground;
import mccrystal.ryan.entities.Player;

public class TestLevel extends World {
    public TestLevel(Game game, boolean active) {
        super(game, active, 0.5f, 0.99f, Color.BLACK);
        float friciton = 0.8f;
        this.addEntity(new Ground(0, 900, 2000, 60, friciton,  Color.GREEN));
        this.addEntity(new Ground(1200, 700, 200, 20, friciton, Color.MAGENTA));
        this.addEntity(new Ground(600, 500, 200, 10, friciton, Color.YELLOW));
        this.addEntity(new Ground(100, 800, 310, 5, friciton, Color.ORANGE));
        this.addEntity(new Enemy(500, 20, 50, 50, game.player));
    }
}