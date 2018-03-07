package mccrystal.ryan.entities;

import mccrystal.ryan.Entity;

import java.awt.*;

public class Ground extends Entity {
    public Ground(float positionX, float positionY, float width, float height, float friction, Color color) {
        super(positionX, positionY, width, height, friction,  color);
        this.canMove = false;
        this.hasGravity = false;
        this.hasCollision = true; //This might be set to false
        this.isVisible = true;
        this.friction = friction;
    }
}
