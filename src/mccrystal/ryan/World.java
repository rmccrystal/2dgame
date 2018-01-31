package mccrystal.ryan;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class World {
    private boolean isActive;

    private LinkedList<Entity> entityList = new LinkedList<Entity>();

    private float gravitiy;

    private Color backgroundColor;

    private Game game;

    public Game getGame() {
        return game;
    }
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
        entity.setWorld(this);
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

    public World(Game g, boolean isActive, float gravitiy, Color backgroundColor) {
        this.isActive = isActive;
        this.gravitiy = gravitiy;
        this.backgroundColor = backgroundColor;
        game = g;
    }

    public void render(Graphics2D g) {
        g.setColor(backgroundColor);
        g.fillRect(0, 0, Game.DEFAULT_WINDOW_LENGTH, Game.DEFAULT_WINDOW_HEIGHT);
        for (Entity e : getEntityList()) {
            e.render(g);
        }
    }
    public void tick() {
        for(Entity e : getEntityList()) {
            e.tick();
        }
    }
}
