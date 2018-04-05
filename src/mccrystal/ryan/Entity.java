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

    /** The Entity class can be anytning that appears on the screen.
     * @param positionX inital X position of entity
     * @param positionY inital Y position of entity
     * @param width Width of the entity
     * @param height Heigh tof the entity
     * @param friciton Amount that will be multiplied to the speed on an entity that is moving on top of it. For example, friction of 0.9 will set velocity to 90% of its current velocity every tick.
     * @param color Color of the object //TODO: add textures
     */
    public Entity(float positionX, float positionY, float width, float height, float friction, Color color) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
        this.friction = friction;
        this.color = color;
    }
    
    /** @return the current world the entity is in */
    public World getWorld() {
        return currentWorld;
    }

    public void setWorld(World currentWorld) {
        this.currentWorld = currentWorld;
    }

    /** Runs every tick of the game as long as it is in an active world */
    public void tick() {
        runEvents();
        updatePosition();
    }

    /** Checks if the current entity is intersecting a ground object
     * @return If the entity is intersecting a ground, it will return the ground object it is intersecting. If it is not intersecting a ground, it will return null
     */
    protected Ground intersectsGround() { //TODO: This code isn't necessary to run every tick
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
    
    protected void updateYVeloity() {
        if(velocityY >= terminalVelocity)
            velocityY = terminalVelocity; //Set speed limit
    }

    protected void updateXVelocity() { //Updaets X velocity based on the friction on the ground
        if(this.ground != null) {
            velocityX *= this.ground.friction;
        } else {
            velocityX *= this.getWorld().getAirResistance();
        }
    }
    
    protected void updateYPos() {
        updateYVeloity();
        positionY -= velocityY;
        Ground ground = intersectsGround();
        if(ground != null) {
            onGround = true;
            this.ground = ground;
            if(isMovingDirection(Direction.DOWN)) {
                positionY = ground.positionY - this.height;
                velocityY = 0;
            } else if (isMovingDirection(Direction.UP)){
                positionY = ground.positionY + ground.height;
                onGround = false;
                velocityY = 0;
            }
        }
    }

    protected void updateXPos() { //FIXME: Collision doesn't work while moing right or jumping and moving left
        if(hasGravity && !onGround) {
            velocityY -= currentWorld.getGravitiy(); //Gravity
        }
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

    protected void runEvents() {
        runOnIntsersectGround();
    }

    protected void runOnIntsersectGround() {
        Ground g = intersectsGround();
        if(g !=null) {
            onIntersectGround(g);
        }
    }

    /////////////////////////////////////////EVENTS//////////////////////////////////////////
    /**
     * This code runs whenever the entity collides with another entity
     */
    protected void onIntersectGround(Ground e) {
        if(!canMove) return;
        Game.debugPrint("Collided with the ground");
    }
    /////////////////////////////////////////////////////////////////////////////////////////
    public enum Direction {UP, DOWN, LEFT, RIGHT}
    /**
     * isMovingDirection will return true if the player is moving the specified direction and false if it is not.
     * @param d This is the direciton that will be checked
     * @return boolean This returns if the player is moving that direction specified
     */
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

    public void updatePosition() {
        if(!canMove) return;
        updateXPos();
        updateYPos();
        updateOnGround();
    }

    public void render(Graphics2D graphics) {

    }


}
