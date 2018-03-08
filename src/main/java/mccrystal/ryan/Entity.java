package mccrystal.ryan;

import mccrystal.ryan.entities.Ground;

import java.awt.*;
import java.awt.geom.Area;

public class Entity implements Renderable { //TODO: Make renderable interface work
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

    protected Ground ground;

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
            		/**
            		 * Daniel:
            		 * This approach makes sense and clearly works, but I think there's a lot of unnecessary overhead here.
            		 * You can do this without instantiating objects.
            		 * 
            		 * But even if you want to keep this logic, there's still room for improvement to make it more readable.
            		 * For example, you cast the entity to a ground object (because you know it is one).
            		 * But then you don't use it until you return it at the end. Look in the instantiation of your
            		 * groundArea object. Where can you use the Ground variable you already created? No need to recast something that you already cast.
            		 */
                Ground ground = (Ground) e;
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

    
    /**
     * Daniel:
     * I think this function is doing more work than you need to.
     * Once you've determined that you're on the ground, you don't need to check again until the player jumps or moves off the edge.
     * As it is right now, every frame, you're checking every single entity to see if it's on top of it.
     * Once you figure out which entity the player is standing on, I would store that in a variable so you know where to look
     * to determine if the player is still on the ground.
     */
    protected void updateOnGround() { //Updates the variable onGround
        this.positionY += 1;                //Moves the object down one pixel and tests if it is in the ground
        Ground ground = intersectsGround();
        positionY -= 1; //Move the object to it's original position
        onGround = ground != null;
        this.ground = ground;
    }
    
    /**
     * Daniel:
     * This method seems poorly named. It seems like you're updating the Y position here, not the velocity.
     */
    protected void updateYVeloity() {
        if(velocityY >= terminalVelocity)
            velocityY = terminalVelocity; //Set speed limit

    }

    protected void updateXVelocity() {
        if(this.ground != null) {
            velocityX *= this.ground.friction;
        } else {
            velocityX *= this.getWorld().getAirResistance();
        }
    }
    
    /**
     * Daniel:
     * Repeated work alert! Think about what's happening when you call intersectsGround. In the worst case,
     * it has to look at every single entity in your scene. You're calling it to see if it is null,
     * but then you call it again inside the if block to assign it to your g variable. This is not so noticeable
     * when you only have a few entities in your scene, but you need to be more disciplined with your function calls
     * if you want this to scale well. Consider calling it once, then setting that result to a variable, and then checking
     * if that variable is null.
     */
    protected void updateYPos() {
        updateYVeloity();
        positionY -= velocityY;
        if(intersectsGround() != null) {
            onGround = true;
            Ground g = intersectsGround();
            this.ground = g;
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
        positionX += velocityX;
        if(intersectsGround() != null) {
            Ground g = intersectsGround();
            updateXVelocity();
            if(isMovingDirection(Direction.RIGHT)) {
                positionX = g.positionX - this.width;
                velocityX = 0;

            } else if(isMovingDirection(Direction.LEFT)) {
                positionX = g.positionX + g.width;
                velocityX = 0;
            }
        } else {
            updateXVelocity();
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
    
    /**
     * Daniel:
     * This is another poorly named method. 
     */
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
        if(Game.DEBUG) {
            graphics.setColor(Color.WHITE);
            graphics.drawString(isMovingDirection(Direction.UP) ? "UP" : "", 300, 300);
            graphics.drawString(isMovingDirection(Direction.DOWN) ? "DOWN" : "", 300, 350);
            graphics.drawString(isMovingDirection(Direction.LEFT) ? "LEFT" : "", 300, 400);
            graphics.drawString(isMovingDirection(Direction.RIGHT) ? "RIGHT" : "", 300, 450);
        }

        graphics.setColor(color);
        graphics.fillRect((int) positionX, (int) positionY, (int) width, (int) height);
    }


}
