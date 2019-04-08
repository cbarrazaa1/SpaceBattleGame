/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.ui;

import better.core.Game;
import better.core.GameObject;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

/**
 *
 * @author Cesar Barraza
 */
public abstract class UIControl extends GameObject implements MouseEventListener, OnClickListener {
    protected boolean enabled;
    protected boolean visible;
    protected Color color;
    protected MouseEventListener mouseEventListener;
    protected OnClickListener onClickListener;
    private boolean mouseCollided;
    
    public UIControl(float x, float y, float width, float height, Color color) {
        super(x, y, width, height);
        this.color = color;
        this.visible = true;
        this.enabled = true;
        mouseEventListener = this;
        mouseCollided = false;
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
    
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    
    @Override
    public abstract void render(Graphics2D g);
    
    @Override
    public void update() {
        if(!isVisible() || !isEnabled()) {
            return;
        }
        
        if(Game.getMouseManager().intersects(getX(), getY(), getWidth(), getHeight())) {
            mouseCollided = true;
            mouseEventListener.mouseEnter();
            if(Game.getMouseManager().isButtonPressed(MouseEvent.BUTTON1)) {
                mouseEventListener.mouseDown();
            }
            
            if(Game.getMouseManager().isButtonReleased(MouseEvent.BUTTON1)) {
                mouseEventListener.mouseUp();
                
                if(onClickListener != null) {
                    onClickListener.onClick();                    
                }
            }
        } else {
            if(mouseCollided) {
                mouseEventListener.mouseLeave();
                mouseCollided = false;
            }
        }
    }
}
