package mccrystal.ryan;

import mccrystal.ryan.entities.Ground;

import java.awt.*;
import java.awt.geom.Area;

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

    protected boolean intersectsGround() {
        for(Entity e : getWorld().getEntityList()) {
            if(e instanceof Ground) {
                Ground ground = (Ground) e;
                Area a = new Area();
                Area thisArea = new Area(new Rectangle((int) positionX, (int) positionY, (int) width, (int) height));
                Area groundArea = new Area(new Rectangle((int) ((Ground) e).positionX, (int) ((Ground) e).positionY, (int) ((Ground) e).width, (int) ((Ground) e).height));
                thisArea.intersect(groundArea);
                if(!thisArea.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }


    public void updatePosition() {
        if(!canMove) return;
        if(intersectsGround() && !onGround) {
            onGround = true;
            velocityY = 0;
            while(intersectsGround()) {
                this.positionY++;
            }
        }
        if(onGround) {

        }
        positionY -= velocityY;
        positionX += velocityX;
        if(hasGravity && !onGround) {
            velocityY -= currentWorld.getGravitiy();
        }
    }

    public void render(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.fillRect((int) positionX, (int) positionY, (int) width, (int) height); //TODO: Replace this with a rectangle object and use graphics.draw(shape)
    }


}
