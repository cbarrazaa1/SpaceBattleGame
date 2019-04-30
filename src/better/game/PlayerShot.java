/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.game;

import better.assets.Assets;
import better.game.GameObject;
import better.scenes.LevelScreen;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 *
 * @author rogel
 */
public class PlayerShot extends GameObject {

    private double theta;
    private Light2D light;
    
    public PlayerShot(float x, float y, float width, float height, double theta) {
        super(x, y, width, height);
        this.theta = theta;
        this.light = new Light2D(x + width / 2, y + height / 2, 0.1f, 30, 80, 152, 70);
        LevelScreen.getInstance().lights.add(light);
    }

    @Override
    public void render(Graphics2D g) {
        AffineTransform orig = g.getTransform();
        g.translate(getX(), getY());
        g.rotate(theta, getWidth() / 2, getHeight() / 2);
        g.drawImage(Assets.images.get("BulletGreen"), 0, 0, (int)(getWidth()), (int)(getHeight()), null);
        g.setTransform(orig);
    }
    
    @Override
    public void update() {
        // The bullet moves depending on the rotation of the player
        setX(getX() + ((float)(Math.cos(theta-Math.PI/2))*5));
        setY(getY() + ((float)(Math.sin(theta-Math.PI/2))*5));
        
        // update light
        light.setX(x + width / 2);
        light.setY(y + height / 2);
    }
    
    public Light2D getLight() {
        return light;
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
