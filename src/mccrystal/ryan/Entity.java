package mccrystal.ryan;

import mccrystal.ryan.entities.Ground;

import java.awt.*;
import java.awt.geom.Area;

public class Entity {
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

    protected void updateGround() {
        if(intersectsGround() != null) {
            onGround = true;
            Ground g = intersectsGround();
            positionY = g.positionY - this.height;
            velocityY = 0;
        }
    }

    protected void updateOnGround() {
        this.positionY += 1;
        Ground ground = intersectsGround();
        positionY -= 1;
        if(ground == null) {
            onGround = false;
        } else {
            onGround = true;
        }
    }

    protected void updateYPos() {
        positionY -= velocityY;

    }

    protected void updateXPos() {
        positionX += velocityX;
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
        updateGround();
        updateVelocity();
    }

    public void render(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.fillRect((int) positionX, (int) positionY, (int) width, (int) height); //TODO: Replace this with a rectangle object and use graphics.draw(shape)
    }


}
