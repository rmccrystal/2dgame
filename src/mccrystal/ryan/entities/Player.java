package mccrystal.ryan.entities;

import mccrystal.ryan.Entity;
import mccrystal.ryan.World;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player extends Entity {

    protected int jumpFactor = 10;
    protected int moveSpeed = 5;

    public Player(float positionX, float positionY, float width, float height) {
        super(positionX, positionY, width, height, Color.CYAN);
        canMove = true;
        hasGravity = true;
        hasCollision = true;
        isVisible = true;
    }

    @Override
    public void tick() {
        super.tick();
        if(this.getWorld().getGame().getKeyHandler().isPressed(KeyEvent.VK_SPACE)) {
            this.jump();
        }
    }

    private void jump() {
        this.velocityY = jumpFactor;
        System.out.println("Jumping");
    }

    private void moveLeft() {
        this.positionY += moveSpeed;
    }

    private void moveRight() {
        this.positionX += moveSpeed;
    }
}
