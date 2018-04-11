package mccrystal.ryan;

import java.awt.*;
import java.util.LinkedList;

import mccrystal.ryan.entities.Player;

public class World {
    private boolean isActive;

    private int cameraX;
    private int cameraY;

    private LinkedList<Entity> entityList = new LinkedList<Entity>();

    private float gravitiy;
    private float airResistance;

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
    public float getAirResistance() {
        return airResistance;
    }
    public void setAirResistance(float airResistance) {
        this.airResistance = airResistance;
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

    public RenderManager getRenderManager() {
        return getGame().getRenderManager();
    }

    public World(Game g, boolean isActive, float gravitiy, float airResistance, Color backgroundColor) {
        this.isActive = isActive;
        this.gravitiy = gravitiy;
        this.backgroundColor = backgroundColor;
        this.airResistance = airResistance;
        game = g;
    }

    public void render(Graphics2D g) {
        getRenderManager().fillBackgroundColor(this.backgroundColor, g);
        for (Entity e : getEntityList()) {

            getRenderManager().fillRectWithCamera(e.positionX, e.positionY, e.width, e.height, cameraX, cameraY, e.color, g);
        }
    }
    public void tick() {
        for(Entity e : getEntityList()) {
            e.tick();
            if(e instanceof Player) {
                Player p = (Player) e;
                this.cameraX = (int) e.positionX + (int) e.width/2;
                this.cameraY = (int) e.positionY + (int) e.height/2;
            }
        }
    }
}
