package mccrystal.ryan.entities;

import mccrystal.ryan.Entity;
import mccrystal.ryan.KeyHandler;
import mccrystal.ryan.World;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class Player extends Entity {

    protected int jumpFactor = 25;
    protected int moveSpeed = 10;



    protected boolean jumping = false;

    public Player(float positionX, float positionY, float width, float height) {
        super(positionX, positionY, width, height, Color.CYAN);
        canMove = true;
        hasGravity = true;
        hasCollision = true;
        isVisible = true;
    }

    @Override
    public void tick() {
        handleKeys();
        super.tick();
    }
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

    @Override
    protected void updateYPos() {
        if(onGround) return;
        super.updateYPos();
    }

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
        this.positionX -= moveSpeed;
    }

    private void moveRight() {
        this.positionX += moveSpeed;
    }
}
