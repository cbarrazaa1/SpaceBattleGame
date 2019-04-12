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
    private ButtonState state;
    private BufferedImage img;
    private BufferedImage hoverImg;
    private BufferedImage clickImg;
    
    public UIButton(float x, float y, float width, float height, BufferedImage img) {
        super(x, y, width, height, Color.WHITE);
        state = ButtonState.NORMAL;
                
        // crop all image states
        this.img = img.getSubimage(0, 0, (int)width, (int)height);
        this.hoverImg = img.getSubimage((int)width, 0, (int)width, (int)height);
        this.clickImg = img.getSubimage((int)(width * 2), 0, (int)width, (int)height);
    }
    
    @Override
    public void render(Graphics2D g) { 
        BufferedImage toRender = img;
        switch(state) {
            case HOVER:
                toRender = hoverImg;
                break;
            case CLICKED:
                toRender = clickImg;
                break;
        }
        
        g.drawImage(toRender, (int)getX(), (int)getY(), (int)getWidth(), (int)getHeight(), null);
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
