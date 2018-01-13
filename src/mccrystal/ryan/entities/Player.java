package mccrystal.ryan.entities;

import mccrystal.ryan.Entity;
import mccrystal.ryan.World;

import java.awt.*;

public class Player extends Entity {
    public Player(float positionX, float positionY, float width, float height, World world) {
        super(positionX, positionY, width, height, Color.CYAN, world);
        canMove = true;
        hasGravity = true;
        hasCollision = true;
        isVisible = true;
    }

    @Override
    public void tick() {
        super.tick();
        System.out.println("Player tick");
    }
}
