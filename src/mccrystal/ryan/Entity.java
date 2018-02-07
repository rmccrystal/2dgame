package mccrystal.ryan;

import mccrystal.ryan.entities.Ground;

import java.awt.*;
import java.awt.geom.Area;

public class Entity implements Renderable {
    protected float positionX; //Position of entity
    protected float positionY;

    protected float velocityX = 0; //velocity of entity
    protected float velocityY = 0;

    protected int terminalVelocity = 100;

    protected float width;
    protected float height;

    protected Color color;

    protected boolean hasCollision;
    protected boolean hasGravity;
    protected boolean canMove;
    protected boolean isVisible;
    protected boolean onGround;

    protected World currentWorld;

    //protected Rectangle rectangle;

    public Entity(float positionX, float positionY, float width, float height, Color color) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public World getWorld() {
        return currentWorld;
    }

    public void setWorld(World currentWorld) {
        this.currentWorld = currentWorld;
    }

    public void tick() {
        updatePosition();
    }

    protected Ground intersectsGround() {
        for(Entity e : getWorld().getEntityList()) {
            if(e instanceof Ground) {
                Ground ground = (Ground) e;
                Area a = new Area();
                Area thisArea = new Area(new Rectangle((int) positionX, (int) positionY, (int) width, (int) height));
                Area groundArea = new Area(new Rectangle((int) ((Ground) e).positionX, (int) ((Ground) e).positionY, (int) ((Ground) e).width, (int) ((Ground) e).height));
                thisArea.intersect(groundArea);
                if(!thisArea.isEmpty()) {
                    return ground;
                }
            }
        }
        return null;
    }

    protected void updateOnGround() { //Updates the variable onGround
        this.positionY += 1;                //Moves the object down one pixel and tests if it is in the ground
        Ground ground = intersectsGround();
        positionY -= 1; //Move the object to it's original position
        onGround = ground != null;
    }

    protected void updateYPos() {
        if(velocityY >= terminalVelocity) velocityY = terminalVelocity; //Set speed limit
        positionY -= velocityY;
        if(intersectsGround() != null) {
            onGround = true;
            Ground g = intersectsGround();
            positionY = g.positionY - this.height;
            velocityY = 0;
        }
    }

    protected void updateXPos() {
        positionX += velocityX;
        /*
        positionX += 1; //Moves the character one to the right and tests if it is intersecting the ground
        Ground groundLeft = intersectsGround();
        positionX -= 2;
        Ground groundRight = intersectsGround();
        positionX += 1; //Get ground object for both sides
        if(groundLeft != null) {
            velocityX = 0; //If the character is touching the ground after moving to the right, set x velocity to 0 then set location to the left side of object
            positionX = groundLeft.positionX - width;
        }
        if(groundRight != null) {
            velocityX = 0;
            positionX = groundRight.positionX;
        }*/ //TODO: Get this working.
    }

    protected void updateVelocity() {
        if(hasGravity && !onGround) {
            velocityY -= currentWorld.getGravitiy();
        }
    }

    public void updatePosition() {
        if(!canMove) return;
        updateYPos();
        updateXPos();
        updateOnGround();
        updateVelocity();
    }

    public void render(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.fillRect((int) positionX, (int) positionY, (int) width, (int) height);
    }


}
