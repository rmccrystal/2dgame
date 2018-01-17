package mccrystal.ryan.entities;

import mccrystal.ryan.Entity;

import java.awt.*;

public class Ground extends Entity {
    public Ground(float positionX, float positionY, float width, float height, Color color) {
        super(positionX, positionY, width, height, color);
        this.canMove = false;
        this.hasGravity = false;
        this.hasCollision = true; //This might be set to false
        this.isVisible = true;
    }
}
