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
     * Characters that can be typed.
     */
    public final int[] chars = {KeyEvent.VK_Q, KeyEvent.VK_W, KeyEvent.VK_E, KeyEvent.VK_R, KeyEvent.VK_T, KeyEvent.VK_Y, KeyEvent.VK_U, KeyEvent.VK_I, KeyEvent.VK_O,
                                KeyEvent.VK_P, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_F, KeyEvent.VK_G, KeyEvent.VK_H, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L, 
                                KeyEvent.VK_Z, KeyEvent.VK_X, KeyEvent.VK_C, KeyEvent.VK_V, KeyEvent.VK_B, KeyEvent.VK_N, KeyEvent.VK_M, KeyEvent.VK_SPACE, KeyEvent.VK_0, 
                                KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9};
    
    /**
     * Initializes the size of the keys array.
     */
    public KeyManager() {
        keysInUse = new ArrayList<>();
        keys = new boolean[256];
        pressedKeys = new boolean[256];
        
        // add keys that will be used by the game
        keysInUse.add(KeyEvent.VK_W);
        keysInUse.add(KeyEvent.VK_A);
        keysInUse.add(KeyEvent.VK_S);
        keysInUse.add(KeyEvent.VK_D);
        keysInUse.add(KeyEvent.VK_SPACE);
        keysInUse.add(KeyEvent.VK_ESCAPE);
        keysInUse.add(KeyEvent.VK_BACK_SPACE);
        
        for(int i = 0; i < chars.length; i++) {
            keysInUse.add(chars[i]);
        }
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
