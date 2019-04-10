package better.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * KeyManager
 * 
 * Listens for keyboard events such as released and pressed keys.
 * @author César Barraza A01176786
 * Date 30/Jan/2019
 * @version 1.0
 */
public class KeyManager implements KeyListener {

    /**
     * Members that tell state of their respective key.
     */
    private final ArrayList<Integer> keysInUse;

    /**
     * Arrays that hold the state of all possible keys.
     */
    private final boolean keys[];
    private final boolean pressedKeys[];

    /**
     * Initializes the size of the keys array.
     */
    public KeyManager() {
        keysInUse = new ArrayList<>();
        keys = new boolean[256];
        pressedKeys = new boolean[256];
        
        // add keys that will be used by the game
        keysInUse.add(KeyEvent.VK_LEFT);
        keysInUse.add(KeyEvent.VK_RIGHT);
        keysInUse.add(KeyEvent.VK_UP);
        keysInUse.add(KeyEvent.VK_DOWN);
        keysInUse.add(KeyEvent.VK_P);
        keysInUse.add(KeyEvent.VK_R);
        keysInUse.add(KeyEvent.VK_C);
        keysInUse.add(KeyEvent.VK_G);
        keysInUse.add(KeyEvent.VK_SPACE);
    }

    /**
     * Fires when the user types a character.
     *
     * @param e holds the information about the typed key
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Fires when a key is pressed.
     *
     * @param e holds the information about the pressed key
     */
    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    /**
     * Fires when a key is released.
     *
     * @param e holds the information about the released key
     */
    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
    
    /**
     * Determines the state of a key.
     * 
     * @param keyCode the key's code
     * @return whether key is down or not
     */
    public boolean isKeyDown(int keyCode) {
        return keys[keyCode];
    }
    
    /**
     * Determines if a key was just pressed.
     * 
     * @param keyCode the key's code
     * @return whether the key was just pressed or not
     */
    public boolean isKeyPressed(int keyCode) {
        return keys[keyCode] && !pressedKeys[keyCode];
    }
    
    /**
     * Determines if a key was just released.
     * 
     * @param keyCode the key's code
     * @return whether the key was just released or not
     */
    public boolean isKeyReleased(int keyCode) {
        return !keys[keyCode] && pressedKeys[keyCode];
    }
    
    /**
     * Updates the states of the keys that are being managed.
     */
    public void update() {
        for(int key : keysInUse) {
            pressedKeys[key] = keys[key];
        }
    }
}
