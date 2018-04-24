package mccrystal.ryan.entities;

import mccrystal.ryan.Entity;

import java.awt.*;

public class Enemy extends Entity {
    protected int jumpFactor = 20;
    protected float moveSpeed = 6;
    protected float acceleration = 0.5f;

    protected boolean jumping = false;

    protected boolean canForceJump = false;

    protected Player player;

    public Enemy(float positionX, float positionY, float width, float height, Player player) {
        super(positionX, positionY, width, height, 1, Color.RED);
        this.player = player;
        this.canMove = true;
        this.hasGravity = true;
    }

    @Override
    public void tick() {
        super.tick();
        if(onGround) {
            jumping = false;
        }
        if(positionX < player.getPositionX()) {
            moveRight();
        }
        if(positionX > player.getPositionX()) {
            moveLeft();
        }
        if(positionY - 400 > player.getPositionY()) {
            jump();
        }
        if(onGround == false && canForceJump) {
            canForceJump = false;
            forceJump();
        }
        if(onGround) canForceJump = true; //This code makes the enemy jump when it reaches an edge
        restartPositionIfNeeded();

    }

    private void restartPositionIfNeeded() {
        if(positionY > 2500) {
            positionX = 0;
            positionY = 0;
        }
    }

    private void jump() {
        if(!onGround) return;
        this.velocityY = jumpFactor;
        onGround = false;
        jumping = true;
    }

    private void forceJump() {
        this.velocityY = jumpFactor;
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
