/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author cesar
 */
public class StatusBar extends GameObject {
    private BufferedImage backImg;
    private BufferedImage img;
    private int value;
    private int maxValue;
    private float factor;
    
    public StatusBar(float x, float y, float width, float height, BufferedImage img, int value, int maxValue, float factor) {
        super(x, y, width, height);
        this.value = value;
        this.maxValue = maxValue;
        this.factor = factor;
        this.width = maxValue * factor;
        this.img = img.getSubimage(0, 0, (int)width, (int)height);
        this.backImg = img.getSubimage((int)width, 0, (int)width, (int)height);
    }

    public int getValue() {
        return value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
    
    public int getMaxValue() {
        return maxValue;
    }
    
    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
    
    public float getFactor() {
        return factor;
    }
    
    public void setFactor(float factor) {
        this.factor = factor;
    }
    
    @Override
    public void render(Graphics2D g) { 
        // Bar Background //
        // render left side
        g.drawImage(backImg, (int)getX(), (int)getY(), (int)getX() + 2, (int)getY() + (int)getHeight(), 0, 0, 2, 11, null);
        
        // render middle
        g.drawImage(backImg, (int)getX() + 2, (int)getY(), (int)getX() + (int)getWidth(), (int)getY() + (int)getHeight(), 2, 0, 4, 11, null);
        
        // render right side
        g.drawImage(backImg, (int)getX() + (int)getWidth(), (int)getY(), (int)getX() + (int)getWidth() + 2, (int)getY() + (int)getHeight(), 4, 0, 6, 11, null);
        
        // Actual Bar //
        float w = (value * getWidth()) / maxValue;
        
        // render left side
        g.drawImage(img, (int)getX(), (int)getY(), (int)getX() + 2, (int)getY() + (int)getHeight(), 0, 0, 2, 11, null);
        
        // render middle
        g.drawImage(img, (int)getX() + 2, (int)getY(), (int)getX() + (int)w, (int)getY() + (int)getHeight(), 2, 0, 4, 11, null);
        
        // render right side
        g.drawImage(img, (int)getX() + (int)w, (int)getY(), (int)getX() + (int)w + 2, (int)getY() + (int)getHeight(), 4, 0, 6, 11, null);
    }

    @Override
    public void onClick() { }

    @Override
    public void mouseEnter() { }

    @Override
    public void mouseLeave() { }

    @Override
    public void mouseDown() { }

    @Override
    public void mouseUp() { }
}
