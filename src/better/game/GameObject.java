/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.game;

import better.core.Game;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Cesar Barraza
 */
public abstract class GameObject implements OnClickListener, MouseEventListener {
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected OnClickListener onClickListener;
    protected MouseEventListener mouseEventListener;
    private boolean mouseCollided;
    
    public GameObject(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        mouseEventListener = this;
    }
    
    public float getX() {
        return x;
    }
    
    public void setX(float x) {
        this.x = x;
    }
    
    public float getY() {
        return y;
    }
    
    public void setY(float y) {
        this.y = y;
    }
    
    public float getWidth() {
        return width;
    }
    
    public void setWidth(float width) {
        this.width = width;
    }
    
    public float getHeight() {
        return height;
    }
    
    public void setHeight(float height) {
        this.height = height;
    }
    
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    
    public abstract void render(Graphics2D g);
    public void update() {
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
    
    public Rectangle2D.Float getRect() {
        return new Rectangle2D.Float(x, y, width, height);
    }
    public boolean intersects(GameObject obj) {
        return getRect().intersects(obj.getRect());
    }
    
    public boolean isOutOfBounds(int tolerance) {
        return (x + width + tolerance < 0 || x + tolerance > Game.getDisplay().getWidth()) || 
               (y + height + tolerance < 0 || y + tolerance > Game.getDisplay().getHeight());
    }
}
