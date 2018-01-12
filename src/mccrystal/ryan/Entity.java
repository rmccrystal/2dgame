package mccrystal.ryan;

import java.awt.*;

public abstract class Entity {
    private float positionX; //Position of entity
    private float positionY;

    private float velocityX; //velocity of entity
    private float velocityY;

    private float width;
    private float height;

    private boolean hasCollision;
    private boolean hasGravity;
    private boolean canMove;
    private boolean isVisible;

    private World currentWorld;

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
    public abstract void render();


}
