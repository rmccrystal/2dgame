package mccrystal.ryan;

import java.awt.*;

public class RenderManager {
    public float getScale() {
        return scale;
    }
    public void setScale(float scale) {
        this.scale = scale;
        if(scale > 500) {
            assert false;
        }
    }

    private float scale = 1f;

    public float getZoomModifier() {
        return zoomModifier;
    }

    public void setZoomModifier(float zoomModifier) {
        this.zoomModifier = zoomModifier;
    }

    private float zoomModifier = 1f; //Modifier for the scale changed when the plus and minus keys are pressed

    Game game;
    public RenderManager(float scale, Game game) {
        this.scale = scale;
        this.game = game;
    }

    public void fillBackgroundColor(Color color, Graphics2D g) {
        g.setColor(color);
        g.fillRect(0, 0, getGame().frm.getWidth(), getGame().frm.getHeight());
    }

//    public void fillRectWithOffset(int x, int y, int width, int height, int offsetX, int offsetY, Graphics2D g) {
//        g.fillRect(x, y, width, height);
//    }

    public void fillRectWithCamera(int x, int y, int width, int height, int cameraX, int cameraY, Color color, Graphics2D g) {
        int centerOfFrameX = getGame().frm.getWidth()/2; //Get the center of the frame using the getHeight and getWidth method
        int centerOfFrameY = getGame().frm.getHeight()/2;
        g.setColor(color);
        float finalScale = scale; //The final scale to use after multiplying zoomModifier
        finalScale *= zoomModifier;
        width *= finalScale;
        height *= finalScale;
        cameraX *= finalScale;
        cameraY *= finalScale;
        x *= finalScale;
        y *= finalScale;
        g.fillRect(
                (int) (-cameraX + (centerOfFrameX + x)),
                (int) (-cameraY + (centerOfFrameY + y)),
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
