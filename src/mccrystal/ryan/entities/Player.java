package mccrystal.ryan.entities;

import mccrystal.ryan.Entity;
import mccrystal.ryan.KeyHandler;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Entity {

    protected float jumpFactor = 20;
    protected float moveSpeed = 7;
    protected float acceleration = 3;

    protected boolean jumping = false;

    public Player(float positionX, float positionY, float width, float height) {
        super(positionX, positionY, width, height, 1, Color.CYAN);
        canMove = true;
        hasGravity = true;
        hasCollision = true;
        isVisible = true;
    }

    @Override
    public void tick() {
        handleKeys();
        if(onGround) {
            acceleration = 3;
        } else {
            acceleration = 0.2f;
        }
        super.tick();
    }
    /** handleKeys checks if keys are pressed and does the appropriate action */
    protected void handleKeys() {
        if(getKeyHandler().isPressed(KeyEvent.VK_SPACE)) {
            jump();
        }
        if(getKeyHandler().isPressed(KeyEvent.VK_LEFT)) {
            moveLeft();
        }
        if(getKeyHandler().isPressed(KeyEvent.VK_RIGHT)) {
            moveRight();
        }
    }
/*

    @Override
    protected void updateYPos() {
        if(onGround) return;
        super.updateYPos();
    }
*/

    private KeyHandler getKeyHandler() {
        return this.getWorld().getGame().getKeyHandler();
    }

    private void jump() {
        if(!onGround) return;
        this.velocityY = jumpFactor;
        onGround = false;
        jumping = true;
    }

    private void moveLeft() {
        if(velocityX > -moveSpeed)
            this.velocityX -= acceleration;
    }

    private void moveRight() {
        if(velocityX < moveSpeed)
            this.velocityX += acceleration;
    }
}
