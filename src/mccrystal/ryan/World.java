package mccrystal.ryan;

import java.util.LinkedList;

public abstract class World {
    private boolean isActive;

    private LinkedList<Entity> entityList;

    public float gravitiy;

    public float getGravitiy() {
        return gravitiy;
    }

    public boolean isActive() {
        return isActive;
    }

    public LinkedList<Entity> getEntityList() {
        return entityList;
    }

    public void setGravitiy(float gravitiy) {
        this.gravitiy = gravitiy;
    }

    public abstract void render();
}
