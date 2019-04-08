/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author Cesar Barraza
 */
public class UILabel extends UIControl {
    public static Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 16);
    
    private String text;
    private Font font;
    
    public UILabel(float x, float y, String text, Color color, Font font) {        
        super(x, y, 0, 0, color);
        this.text = text;
        this.font = font;
    }

    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
        setWidth(0);
        setHeight(0);
    }
    
    public Font getFont() {
        return font;
    }
    
    public void setFont(Font font) {
        this.font = font;
    }
    
    public void setFontName(String name) {
        this.font = new Font(name, font.getStyle(), font.getSize());
    }
    
    public void setFontSize(int size) {
        this.font = new Font(font.getName(), font.getStyle(), size);
    }
    
    public void setFontStyle(int style) {
        this.font = new Font(font.getName(), style, font.getSize());
    }
    
    public boolean hasDimensions() {
        return !(getWidth() == 0 || getHeight() == 0);
    }
    
    public void calculateDimensions(Graphics2D g) {
        // calculate string dimensions
        setWidth(g.getFontMetrics(getFont()).stringWidth(getText()));
        setHeight(g.getFontMetrics(getFont()).getHeight());
    }
    
    @Override
    public void render(Graphics2D g) {
        // if not visible, don't render
        if(!isVisible()) {
            return;
        }
        
        // calculate string dimensions
        if(!hasDimensions()) {
            calculateDimensions(g);
            return;
        }
        
        // draw the string
        g.setColor(getColor());
        g.setFont(getFont());
        g.drawString(getText(), getX(), getY() + getHeight() - 6);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void mouseEnter() { }

    @Override
    public void mouseLeave() { }
    
    @Override
    public void mouseDown() { }

    @Override
    public void mouseUp() { }

    @Override
    public void onClick() { }
}
