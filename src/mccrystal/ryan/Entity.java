package mccrystal.ryan;

import mccrystal.ryan.entities.Ground;

import java.awt.*;
import java.awt.geom.Area;

public class Entity implements Renderable {
    protected float positionX; //Position of entity
    protected float positionY;

    protected float lastGroundFriction = 1;

    public float friction; //Speed at which an object moving on top of this object will decelerate

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

    public Entity(float positionX, float positionY, float width, float height, float friction, Color color) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
        this.friction = friction;
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

    protected void updateYVeloity() {
        if(velocityY >= terminalVelocity) velocityY = terminalVelocity; //Set speed limit
        positionY -= velocityY;

    }

    protected void updateXVelocity() {
        positionX += velocityX;
        if(intersectsGround() != null) {
            Ground g = intersectsGround();
            velocityX *= g.friction;
        }
    }

    protected void updateYPos() {
        updateYVeloity();
        if(intersectsGround() != null) {
            onGround = true;
            Ground g = intersectsGround();
            if(isMovingDirection(Direction.DOWN)) {
                positionY = g.positionY - this.height;
                velocityY = 0;
            } else if (isMovingDirection(Direction.UP)){
                positionY = g.positionY + g.height;
                onGround = false;
                velocityY = 0;
            }
        }
    }

    protected void updateXPos() {
        updateXVelocity();
        if(intersectsGround() != null) {
            Ground g = intersectsGround();
            if(isMovingDirection(Direction.RIGHT)) {
                positionX = g.positionX;

            } else if(isMovingDirection(Direction.LEFT)) {
                positionX = g.positionX + g.width;
            }
        }
    }

    public enum Direction {UP, DOWN, LEFT, RIGHT}
    public boolean isMovingDirection(Direction d) {
        if(velocityY > 0) {
            if(d == Direction.UP) return true;
        }
        if(velocityY < 0) {
            if(d == Direction.DOWN) return true;
        }
        if(velocityX < 0) {
            if(d == Direction.LEFT) return true;
        }
        if(velocityY > 0) {
            if(d == Direction.RIGHT) return true;
        }
        return false;
    }

    protected void updateVelocity() {
        if(hasGravity && !onGround) {
            velocityY -= currentWorld.getGravitiy();
        }
    }

    public void updatePosition() {
        if(!canMove) return;
        updateXPos();
        updateYPos();
        updateOnGround();
        updateVelocity();
    }

    public void render(Graphics2D graphics) {
        graphics.setColor(Color.WHITE);
        graphics.drawString(isMovingDirection(Direction.UP) ? "UP" : "", 300, 300);
        graphics.drawString(isMovingDirection(Direction.DOWN) ? "DOWN" : "", 300, 350);
        graphics.drawString(isMovingDirection(Direction.LEFT) ? "LEFT" : "", 300, 400);
        graphics.drawString(isMovingDirection(Direction.RIGHT) ? "RIGHT" : "", 300, 450);

        graphics.setColor(color);
        graphics.fillRect((int) positionX, (int) positionY, (int) width, (int) height);
    }


}
