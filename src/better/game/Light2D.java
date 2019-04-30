/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Cesar Barraza
 */
public class Light2D extends GameObject {
    private float attenuation;
    private int radius;
    private BufferedImage lightMap;
    private int r;
    private int g;
    private int b;
    
    public Light2D(float x, float y, float attenuation, int radius, int r, int g, int b) {
        super(x, y, radius * 2, radius * 2);
        lightMap = new BufferedImage(radius * 2, radius * 2, BufferedImage.TYPE_INT_ARGB);
        this.r = r;
        this.g = g;
        this.b = b;
        
        // create lightmap
        Graphics2D g2d = (Graphics2D)lightMap.getGraphics();
        for(int i = 0; i < radius; i++) {
            float luminosity = 1.0f - ((i + 0.000001f) / radius);
            int alpha = Math.min((int)(255.0f * luminosity * attenuation), 255);
            g2d.setColor(new Color(r, g, b, alpha));
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(radius - i, radius - i, i * 2, i * 2);
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(lightMap, (int)x - lightMap.getWidth() / 2, (int)y - lightMap.getHeight() / 2, lightMap.getWidth(), lightMap.getHeight(), null);
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
