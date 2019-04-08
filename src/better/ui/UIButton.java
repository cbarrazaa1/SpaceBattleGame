/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.ui;

import better.assets.Assets;
import better.core.Game;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author Cesar Barraza
 */

enum ButtonState {
    NORMAL,
    HOVER,
    CLICKED
};

public class UIButton extends UIControl {
    private UIShadowedLabel title;
    private BufferedImage preRenderedImage;
    private ButtonState state;
    
    public UIButton(float x, float y, float width, float height, Color color, String text) {
        super(x, y, width, height, color);
        this.title = new UIShadowedLabel(x, y, text, Color.WHITE, UILabel.DEFAULT_FONT);
        title.setFontSize(18);
        title.setFontStyle(Font.BOLD);
        state = ButtonState.NORMAL;
        preRender();
    }

    public UILabel getTitle() {
        return title;
    }
    
    public void setText(String text) {
        getTitle().setText(text);
    }
    
    private void preRender() {
        BufferedImage img = Assets.defaultUIButton;
        BufferedImage res = new BufferedImage((int)getWidth(), (int)getHeight(), img.getType());
        Graphics2D g = (Graphics2D)res.createGraphics();
        
        // set up size variables
        int w = (int)getWidth();
        int h = (int)getHeight();
        
        // top-left
        g.drawImage(img, 0, 0, 3, 3, 0, 0, 3, 3, null);
        
        // top
        g.drawImage(img, 3, 0, w - 3, 3, 3, 0, 2, 3, null);
        
        // top-right
        g.drawImage(img, w - 3, 0, w, 3, 5, 0, 8, 3, null);
        
        // right
        g.drawImage(img, w - 3, 3, w, h - 3, 5, 3, 8, 5, null);
        
        // bottom-right
        g.drawImage(img, w - 3, h - 3, w, h, 5, 5, 8, 8, null);
        
        // bottom
        g.drawImage(img, 3, h - 3, w - 3, h, 3, 5, 5, 8, null);
        
        // bottom-left
        g.drawImage(img, 0, h - 3, 3, h, 0, 5, 3, 8, null);
        
        // left
        g.drawImage(img, 0, 3, 3, h - 3, 0, 3, 3, 5, null);
        
        // gradient
        g.drawImage(Assets.UIButtonGradient, 2, 2, w - 4, h - 4, null);
        
        // draw foreground color
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f));
        g.setColor(getColor());
        g.fillRect(0, 0, w, h);
        
        // set final result
        preRenderedImage = res;
        g.dispose();
    }
    
    @Override
    public void render(Graphics2D g) { 
        // set uilabel position
        if(getTitle().getWidth() == 0 || getTitle().getHeight() == 0) {
            getTitle().calculateDimensions(g);
            
            float newX = ((getX() + getWidth() / 2) - (getTitle().getWidth() / 2));
            float newY = ((getY() + getHeight() / 2) - (getTitle().getHeight() / 2));
            getTitle().setX(newX);
            getTitle().setY(newY);
        }
        
        // get alpha value
        float alpha = 0.85f;
        switch(state) {
            case CLICKED:
                alpha = 0.75f;
                break;
            case HOVER:
                alpha = 1.0f;
                break;
        }
        
        // render pre-rendered image
        Composite origComposite = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g.drawImage(preRenderedImage, (int)getX(), (int)getY(), (int)getWidth(), (int)getHeight(), null);
        
        // render label
        getTitle().render(g);
        
        // set original composite
        g.setComposite(origComposite);
    }

    @Override
    public void mouseEnter() {
        if(!Game.getMouseManager().isButtonDown(MouseEvent.BUTTON1)) {
            state = ButtonState.HOVER;
        }
    }

    @Override
    public void mouseLeave() {
        state = ButtonState.NORMAL;
    }
    
    @Override
    public void mouseDown() { 
        state = ButtonState.CLICKED;
    }

    @Override
    public void mouseUp() { }

    @Override
    public void onClick() { }   
}
