package mccrystal.ryan;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class KeyHandler implements KeyListener {
    private ArrayList<Integer> keysDown = new ArrayList<Integer>();
    private Map<Integer, Runnable> keyEvents = new HashMap<>();

    public void keyTyped(KeyEvent e) {
        
    }

    public void keyPressed(KeyEvent e) {
        if(keysDown.contains(e.getKeyCode())) return;
        keysDown.add(e.getKeyCode());
        for(Map.Entry<Integer, Runnable> entry : keyEvents.entrySet()) {
            if(entry.getKey() == e.getKeyCode()) {
                entry.getValue().run();
            }
        }
    }

    /**
     * Runs code from an anonymous runnable class when a certain keycode is pressed
     * @param keyCode The keycode that needs to be pressed to run the code
     * @param codeToRun The code
     */
    public void addEvent(int keyCode, Runnable codeToRun) {
        keyEvents.put(keyCode, codeToRun);
    }

    /**
     * Removes all keycode events from a certain keycode
     * @param keyCode
     */
    public void removeAllEvents(int keyCode) { //TODO

    }

    public void keyReleased(KeyEvent e) {
        keysDown.removeAll(Collections.singleton(e.getKeyCode()));
    }

    public ArrayList<Integer> getKeysDown() {
        return keysDown;
    }

    public boolean isPressed(int keyCode) {
        return keysDown.contains(keyCode);
    }
}
