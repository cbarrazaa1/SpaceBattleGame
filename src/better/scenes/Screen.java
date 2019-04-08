/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.scenes;

import better.ui.UIControl;
import java.awt.Graphics2D;
import java.util.HashMap;

/**
 *
 * @author Cesar Barraza
 */
public abstract class Screen {
    protected HashMap<String, UIControl> uiControls;
    
    public Screen() {
        uiControls = new HashMap<>(); 
    }
    
    public abstract void init();
    public abstract void render(Graphics2D g);
    public abstract void update();
}