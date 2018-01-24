package mccrystal.ryan;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;

public class KeyHandler implements KeyListener {
    private ArrayList<Integer> keysDown = new ArrayList<Integer>();

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysDown.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysDown.removeAll(Collections.singleton(e.getKeyCode()));
    }

    public ArrayList<Integer> getKeysDown() {
        return keysDown;
    }

    public boolean isPressed(int keyCode) {
        for(Integer e : keysDown) {
            return e == keyCode;
        }
        return false;
    }
}
