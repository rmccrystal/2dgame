package mccrystal.ryan;

import java.awt.*;

public class RenderManager {
    float scale = 1f;

    Game game;
    public RenderManager(float scale, Game game) {
        this.scale = scale;
        this.game = game;
    }

    public void fillBackgroundColor(Color color, Graphics2D g) {
        g.fillRect(0, 0, getGame().frm.getWidth(), getGame().frm.getHeight());
    }

    public void fillRectWithOffset(int x, int y, int width, int height, int offsetX, int offsetY, Graphics2D g) {
        g.fillRect(x, y, width, height);
    }

    protected Game getGame() {
        return game;
    }
}
