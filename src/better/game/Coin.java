/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.game;

import better.assets.Assets;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author Cesar Barraza
 */
public class Coin extends GameObject {
    private double theta;
    private BufferedImage img;
    private Player player;
    
    public Coin(float x, float y, float width, float height, Player player) {
        super(x, y, width, height);
        this.player = player;
        img = Assets.images.get("coin");
        theta = 0.0d;
    }
    /**
     * renders the coin
     * @param g 
     */
    @Override
    public void render(Graphics2D g) {
        AffineTransform orig = g.getTransform();
        g.translate(getX(), getY());
        g.drawImage(img, 0, 0, (int)getWidth(), (int)getHeight(), null);
        g.setTransform(orig);
    }
    /**
     * updates the coin
     */
    @Override
    public void update() {
        double distX = getDistXTo(player);
        double distY = getDistYTo(player);
        float dist = (float)Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
        float speed =  1f / (dist / 600f) * 2.5f;
        theta = (float)Math.atan2(distY, distX);
        
        if(speed >= 11f) {
            speed = 11f;
        }
        setX(getX() + ((float)(Math.cos(theta)) * speed));
        setY(getY() + ((float)(Math.sin(theta)) * speed));
    }
    
    @Override
    public void onClick() {
    }

    @Override
    public void mouseEnter() {
    }

    @Override
    public void mouseLeave() {
    }

    @Override
    public void mouseDown() {
    }

    @Override
    public void mouseUp() {
    }    
}
