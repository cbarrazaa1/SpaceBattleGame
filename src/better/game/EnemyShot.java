/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.game;

import better.assets.Assets;
import better.game.GameObject;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 *
 * @author rogel
 */
public class EnemyShot extends GameObject {

    private double theta;
    
    public EnemyShot(float x, float y, float width, float height, double theta) {
        super(x, y, width, height);
        this.theta = theta;
    }

    @Override
    public void render(Graphics2D g) {
        AffineTransform orig = g.getTransform();
        g.translate(getX(), getY());
        g.rotate(theta, getWidth() / 2, getHeight() / 2);
        g.drawImage(Assets.images.get("BulletRed"), 0, 0, (int)(getWidth()), (int)(getHeight()), null);
        g.setTransform(orig);
    }
    
    @Override
    public void update() {
        // The bullet moves depending on the rotation of the player
        setX(getX() + ((float)(Math.cos(theta-Math.PI/2))*5));
        setY(getY() + ((float)(Math.sin(theta-Math.PI/2))*5));
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
