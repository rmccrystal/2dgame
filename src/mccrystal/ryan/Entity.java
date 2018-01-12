package mccrystal.ryan;

import java.awt.*;

public abstract class Entity {
    protected float positionX; //Position of entity
    protected float positionY;

    protected float velocityX; //velocity of entity
    protected float velocityY;

    protected float width;
    protected float height;

    protected Color color;

    protected boolean hasCollision;
    protected boolean hasGravity;
    protected boolean canMove;
    protected boolean isVisible;

    protected World currentWorld;

    public Entity(float positionX, float positionY, float width, float height, Color color) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void tick() {
        updatePosition();
    }

    public void updatePosition() {
        if(!canMove) return;
        positionY += velocityY;
        positionX += velocityX;
        if(hasGravity) {
            positionY += currentWorld.getGravitiy();
        }
    }
    public void render(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.fillRect((int) positionX, (int) positionY, (int) width, (int) height);
    }


}
