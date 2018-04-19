package mccrystal.ryan.entities;

import mccrystal.ryan.Entity;

import java.awt.*;

public class Enemy extends Entity {
    protected int jumpFactor = 20;
    protected int moveSpeed = 6;
    protected int acceleration = 2;

    protected boolean jumping = false;

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

        if(positionX < player.getPositionX()) {
            moveRight();
        }
        if(positionX > player.getPositionX()) {
            moveLeft();
        }
        if(positionY - 400 > player.getPositionY()) {
            jump();
        }
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
