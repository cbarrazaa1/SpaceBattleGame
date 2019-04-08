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
public class UIShadowedLabel extends UILabel {
    public UIShadowedLabel(float x, float y, String text, Color color, Font font) {
        super(x, y, text, color, font);
    }
    
    @Override
    public void render(Graphics2D g) {        
        // render shadows
        g.setColor(new Color(0, 0, 0, 0.5f));
        g.setFont(getFont());
        g.drawString(getText(), getX() - 1, getY() + getHeight() - 6 - 1);
        g.drawString(getText(), getX() - 1, getY() + getHeight() - 6 + 1);
        g.drawString(getText(), getX() + 1, getY() + getHeight() - 6 - 1);
        g.drawString(getText(), getX() + 1, getY() + getHeight() - 6 + 1);
        
        // render actual text
        super.render(g);
    }
}
