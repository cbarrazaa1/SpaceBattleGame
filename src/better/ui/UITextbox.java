/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.ui;

import better.assets.Assets;
import better.core.Game;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 *
 * @author Cesar Barraza
 */
public class UITextbox extends UIControl {
    private UILabel lblText;
    private String text;
    private boolean active;
    private String sep = "|";
    
    public UITextbox(float x, float y) {
        super(x, y, 170, 30, Color.WHITE);
        text = "";
        lblText = new UILabel(x, y, text, Color.BLACK, UILabel.DEFAULT_FONT);
    }

    public String getText() {
        return text;
    }
    
    @Override
    public void update() {
        if(text.length() <= 12) {
            for(int i = 0; i < Game.getKeyManager().chars.length; i++) {
                int c = Game.getKeyManager().chars[i];
                if(Game.getKeyManager().isKeyReleased(c)) {
                    String val = "";
                    if(c == KeyEvent.VK_SPACE) {
                        val = " ";
                    } else {
                        val = KeyEvent.getKeyText(c);
                    }
                    text += val;
                }
            }
        }
        
        if(Game.getKeyManager().isKeyPressed(KeyEvent.VK_BACK_SPACE)) {
            if(text.length() > 0) {
                text = text.substring(0, text.length() - 1);
            }
        }
        
        if(text.length() != 0) {
            lblText.setText(text + sep);
        } else {
            lblText.setText(sep);
        }
    }
    
    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.images.get("textbox"), (int)x, (int)y, (int)width, (int)height, null);
        lblText.setX(x + 6);
        lblText.setY(y + height - 4);
        lblText.render(g);
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