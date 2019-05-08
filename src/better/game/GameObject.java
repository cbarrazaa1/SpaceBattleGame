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
 * @author Rogelio Martinez
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
    /**
     * the x position
     * @return x
     */
    public float getX() {
        return x;
    }
    /**
     * the new x
     * @param x 
     */
    public void setX(float x) {
        this.x = x;
    }
    /**
     * the y position
     * @return y
     */
    public float getY() {
        return y;
    }
    /**
     * the new y
     * @param y 
     */
    public void setY(float y) {
        this.y = y;
    }
    /**
     * the width
     * @return width
     */
    public float getWidth() {
        return width;
    }
    /**
     * new width
     * @param width 
     */
    public void setWidth(float width) {
        this.width = width;
    }
    /**
     * the height
     * @return height
     */
    public float getHeight() {
        return height;
    }
    /**
     * new height
     * @param height 
     */
    public void setHeight(float height) {
        this.height = height;
    }
    /**
     * sets on click listener
     * @param onClickListener 
     */
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    /**
     * renders the object
     * @param g 
     */
    public abstract void render(Graphics2D g);
    /**
     * updates the object
     */
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
    /**
     * creates hitbox
     * @return rect
     */
    public Rectangle2D.Float getRect() {
        return new Rectangle2D.Float(x, y, width, height);
    }
    /**
     * checks for intersection
     * @param obj
     * @return if intersected with obj
     */
    public boolean intersects(GameObject obj) {
        return getRect().intersects(obj.getRect());
    }
    /**
     * if out of bounds
     * @param tolerance
     * @return 
     */
    public boolean isOutOfBounds(int tolerance) {
        return (x + width + tolerance < 0 || x > Game.getDisplay().getWidth() + tolerance) || 
               (y + height + tolerance < 0 || y > Game.getDisplay().getHeight() + tolerance);
    }
    /**
     * gets x distance to
     * @param obj
     * @return distance
     */
    public double getDistXTo(GameObject obj) {
        return (obj.getX() + obj.getWidth() / 2) - (getX() + getWidth() / 2);
    }
    /**
     * gets y distance to
     * @param obj
     * @return distance
     */
    public double getDistYTo(GameObject obj) {
        return (obj.getY() + obj.getHeight() / 2) - (getY() + getHeight() / 2);
    }
    /**
     * gets theta relative to obj
     * @param obj
     * @return theta
     */
    public double getThetaTo(GameObject obj) {
        return Math.atan2(getDistYTo(obj), getDistXTo(obj));
    }
}
