/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.game;

import better.core.Game;
import better.core.GameObject;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author Cesar Barraza
 */
public class SelectablePlanet extends GameObject {
    private BufferedImage normalImg;
    private BufferedImage shadowImg;
    private boolean locked;
    
    public SelectablePlanet(float x, float y, float width, float height, BufferedImage normalImg, BufferedImage shadowImg) {
        super(x, y, width, height);
        this.normalImg = normalImg;
        this.shadowImg = shadowImg;
    }
    
    public void setNormalImg(BufferedImage normalImg) {
        this.normalImg = normalImg;
    }
    
    public void setShadowImg(BufferedImage shadowImg) {
        this.shadowImg = shadowImg;
    }
    
    public boolean isLocked() {
        return locked;
    }
    
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    
    @Override
    public void render(Graphics2D g) {
        g.drawImage(normalImg, (int)getX(), (int)getY(), (int)getWidth(), (int)getHeight(), null);
        if(isLocked()) {
            Composite origComposite = g.getComposite();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            g.drawImage(shadowImg, (int)getX(), (int)getY(), (int)getWidth(), (int)getHeight(), null);
            g.setComposite(origComposite);
        }
    }

    @Override
    public void update() {
        if(Game.getMouseManager().intersects((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight())) {
            if(Game.getMouseManager().isButtonReleased(MouseEvent.BUTTON1)) {
                setLocked(!isLocked());
            }
        }
    }
    
}
