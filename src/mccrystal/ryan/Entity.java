package mccrystal.ryan;

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

    protected World currentWorld;

    public Entity(float positionX, float positionY, float width, float height, Color color, World world) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
        this.color = color;
        this.currentWorld = world;
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

    public void updatePosition() {
        if(!canMove) return;
        positionY += velocityY;
        positionX += velocityX;
        if(hasGravity) {
            velocityY += currentWorld.getGravitiy(); //TODO: Get this working
        }
    }
    public void render(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.fillRect((int) positionX, (int) positionY, (int) width, (int) height);
    }


}
