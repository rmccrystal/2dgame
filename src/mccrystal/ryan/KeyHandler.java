package mccrystal.ryan;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyHandler implements KeyListener {
    private ArrayList<KeyEvent> keysDown = new ArrayList<KeyEvent>();

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysDown.add(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
          keysDown.remove(e);
    }

    public ArrayList<KeyEvent> getKeysDown() {
        return keysDown;
    }
}
