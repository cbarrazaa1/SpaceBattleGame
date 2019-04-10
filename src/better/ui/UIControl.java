/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.ui;

import better.game.OnClickListener;
import better.game.MouseEventListener;
import better.game.GameObject;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Cesar Barraza
 */
public abstract class UIControl extends GameObject implements MouseEventListener, OnClickListener {
    protected boolean enabled;
    protected boolean visible;
    protected Color color;
    
    public UIControl(float x, float y, float width, float height, Color color) {
        super(x, y, width, height);
        this.color = color;
        this.visible = true;
        this.enabled = true;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean isVisible() {
        return visible;
    }
    
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    public Color getColor() {
        return color;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
    
    @Override
    public abstract void render(Graphics2D g);
    
    @Override
    public void update() {
        if(!isVisible() || !isEnabled()) {
            return;
        }
        super.update();
    }
}
