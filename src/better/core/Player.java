/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.core;

import java.awt.Graphics2D;
import better.assets.Assets;
import java.awt.geom.AffineTransform;
/**
 *
 * @author rogel
 */

public class Player extends GameObject{
    
    private double theta;
    
    public Player(float x, float y, float width, float height) {
        super(x, y, width, height);
        double theta = 0;
    }

    @Override
    public void render(Graphics2D g) {
        AffineTransform orig = g.getTransform();
        g.translate(x, y);
        g.rotate(theta, getWidth()/2, getHeight()/2);
        g.drawImage(Assets.mainPlayer, 0, 0, 100, 100, null);
        g.setTransform(orig);
    }

    @Override
    public void update() {
        double deltaX = (y+getHeight()/2)-Game.getMouseManager().getX();
        double deltaY = (x+getWidth()/2)-Game.getMouseManager().getY();
        theta = Math.atan2(deltaY, deltaX);
        theta -= Math.PI/2;
        
        //System.out.println("theta: " + theta);
        //System.out.println("x: " + Game.getMouseManager().getX());
        //System.out.println("y: " + Game.getMouseManager().getY());
    }
    
}
