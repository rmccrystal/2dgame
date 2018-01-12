package mccrystal.ryan;

import java.awt.*;
import java.util.LinkedList;

public abstract class World {
    private boolean isActive;

    private LinkedList<Entity> entityList;

    private float gravitiy;

    private Color backgroundColor;

    public float getGravitiy() {
        return gravitiy;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void addEntity(Entity entity) {
        entityList.add(entity);
    }

    public void removeEntity(Entity entity) {
        entityList.remove(entity);
    }

    public LinkedList<Entity> getEntityList() {
        return entityList;
    }

    public void setGravitiy(float gravitiy) {
        this.gravitiy = gravitiy;
    }

    public World(boolean isActive, float gravitiy, Color backgroundColor) {
        this.isActive = isActive;
        this.gravitiy = gravitiy;
        this.backgroundColor = backgroundColor;
    }

    public void render(Graphics2D g) {
        g.setColor(backgroundColor);
        g.fillRect(0, 0, Game.DEFAULT_WINDOW_LENGTH, Game.DEFAULT_WINDOW_HEIGHT);
    }
    public void tick() {}
}
