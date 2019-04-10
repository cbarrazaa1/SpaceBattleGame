/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.core;

import java.awt.Graphics2D;
import better.assets.Assets;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;
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
        double deltaX = Game.getMouseManager().getX()-(x+getHeight()/2);
        double deltaY = Game.getMouseManager().getY()-(y+getWidth()/2);
        theta = Math.atan2(deltaY, deltaX);
        theta += Math.PI/2;
        
        if (Game.getKeyManager().isKeyDown(VK_UP)){
            setY(getY()-3);
        }
        if (Game.getKeyManager().isKeyDown(VK_DOWN)){
            setY(getY()+3);
        }
        if (Game.getKeyManager().isKeyDown(VK_LEFT)){
            setX(getX()-3);
        }
        if (Game.getKeyManager().isKeyDown(VK_RIGHT)){
            setX(getX()+3);
        }
        System.out.println("theta: " + theta*(180/Math.PI));
        System.out.println("x: " + deltaX);
        System.out.println("y: " + deltaY);
    }
    
}
