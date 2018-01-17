package mccrystal.ryan;

import mccrystal.ryan.entities.Ground;

import java.awt.*;

public abstract class Entity {
    protected float positionX = 0; //Position of entity
    protected float positionY = 0;

    protected float velocityX = 0; //velocity of entity
    protected float velocityY = 0;

    protected float width;
    protected float height;

    protected Color color;

    protected boolean hasCollision;
    protected boolean hasGravity;
    protected boolean canMove;
    protected boolean isVisible;
    protected boolean onGround = false;

    protected World currentWorld;

    protected Rectangle rectangle;

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

    public boolean intersectsGround() {
        for(Entity e : getWorld().getEntityList()) {
            if(e instanceof Ground) {
                Ground ground = (Ground) e;
                Rectangle hisRectangle = new Rectangle((int) positionX, (int) positionY, (int) width, (int) height
                if(new Rectangle((int) positionX, (int) positionY, (int) width, (int) height).contains(ground.positionX, ground.positionY, ground.width, ground.height)) { //TODO: This checks if the ground COMPLETELY contains the object. You need to create your own function to check if two rectangles are touching
                    return true;
                }
            }
        }
        return false;
    }

    public void updatePosition() {
        if(!canMove) return;
        if(intersectsGround()) {
            velocityY = 0;
            onGround = true;
            System.out.println("Intersects Ground");
        }
        positionY += velocityY;
        positionX += velocityX;
        if(hasGravity && !onGround) {
            velocityY += currentWorld.getGravitiy();
        }
    }

    public void render(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.fillRect((int) positionX, (int) positionY, (int) width, (int) height); //TODO: Replace this with a rectangle object and use graphics.draw(shape)
    }


}
