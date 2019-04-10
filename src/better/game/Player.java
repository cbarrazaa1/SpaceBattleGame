/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.game;

import better.game.GameObject;
import java.awt.Graphics2D;
import better.assets.Assets;
import better.core.Game;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;
import java.awt.geom.AffineTransform;
/**
 *
 * @author rogel
 */

public class Player extends GameObject {
    private double theta;
    
    public Player(float x, float y, float width, float height) {
        super(x, y, width, height);
        theta = 0;
    }

    @Override
    public void render(Graphics2D g) {
        AffineTransform orig = g.getTransform();
        g.translate(getX(), getY());
        g.rotate(theta, getWidth() / 2, getHeight() / 2);
        g.drawImage(Assets.mainPlayer, 0, 0, (int)(getWidth()), (int)(getHeight()), null);
        g.setTransform(orig);
    }

    @Override
    public void update() {
        double deltaX = Game.getMouseManager().getX() - ( x + getHeight() / 2);
        double deltaY = Game.getMouseManager().getY() - ( y + getWidth() / 2);
        theta = Math.atan2(deltaY, deltaX);
        theta += Math.PI / 2;
        
        if (Game.getKeyManager().isKeyDown(KeyEvent.VK_W)){
            setY(getY() - 3);
        }
        if (Game.getKeyManager().isKeyDown(KeyEvent.VK_S)){
            setY(getY() + 3);
        }
        if (Game.getKeyManager().isKeyDown(KeyEvent.VK_A)){
            setX(getX() - 3);
        }
        if (Game.getKeyManager().isKeyDown(KeyEvent.VK_D)){
            setX(getX() + 3);
        }
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
