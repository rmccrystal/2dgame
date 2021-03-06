package mccrystal.ryan.worlds;

import java.awt.Color;
import mccrystal.ryan.Game;
import mccrystal.ryan.World;
import mccrystal.ryan.entities.Enemy;
import mccrystal.ryan.entities.Ground;
import mccrystal.ryan.entities.Player;

public class TestLevel extends World {
    public TestLevel( Game game, boolean active) {
        super(game, active, 0.5f, 0.99f, new Color(135, 206, 235));
        float friciton = 0.8f;
        this.addEntity(new Ground(0, 900, 600, 60, friciton,  Color.BLACK));
        this.addEntity(new Ground(600, 600, 200, 10, friciton, Color.BLACK));
        generateTerrain();
        for(int i = 0; i<1; i++) {
            Enemy e = new Enemy(i*3, i*3, 50, 50, game.player);
            e.setMoveSpeed(7);
            this.addEntity(e);
        }

    }

    private void generateTerrain() {
        for(int i = 800; i<9000; i+=350) {
            i+=Math.random() * 250;
            this.addEntity(new Ground(i, 600 + (int) (Math.random()*400), 200, 10, 0.8f, Color.BLACK));
        }
    }
}