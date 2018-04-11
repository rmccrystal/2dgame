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

//    public void fillRectWithOffset(int x, int y, int width, int height, int offsetX, int offsetY, Graphics2D g) {
//        g.fillRect(x, y, width, height);
//    }

    public void fillRectWithCamera(int x, int y, int width, int height, int cameraX, int cameraY, Color color, Graphics2D g) {
        int centerOfFrameX = getGame().frm.getWidth()/2; //Get the center of the frame using the getHeight and getWidth method
        int centerOfFrameY = getGame().frm.getHeight()/2;
        g.setColor(color);
        g.fillRect(
                -cameraX + centerOfFrameX + x,
                -cameraY + centerOfFrameY + y,
                width,
                height
        );

    }

    public void fillRectWithCamera(float x, float y, float width, float height, int cameraX, int cameraY, Color color, Graphics2D g) {
        fillRectWithCamera((int) x, (int) y, (int) width, (int) height, cameraX, cameraY, color, g);
    }


        protected Game getGame() {
        return game;
    }
}
