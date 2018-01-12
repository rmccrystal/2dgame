package mccrystal.ryan;

import java.awt.*;

public abstract class Entity {
    float positionX; //Position of entity
    float positionY;

    float velocityX; //velocity of entity
    float velocityY;

    float width;
    float height;

    boolean hasCollision;
    boolean hasGravity;
    boolean canMove;

    float gravity

    public void tick() {
        if(!canMove) return;
        velocityY
        if(hasGravity) {
            positionY += velocityY;
        }
    }
    public abstract void render();
}
