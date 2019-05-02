/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.game;

import better.assets.Assets;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author Cesar Barraza
 */
public class Bullet extends GameObject {
    public static final int BULLET_TYPE_PLAYER = 0;
    public static final int BULLET_TYPE_ENEMY = 1;
    
    protected int damage;
    protected double theta;
    protected double speed;
    protected Light2D light;
    protected BufferedImage img;
    protected int bulletType;
    
    public Bullet(float x, float y, float width, float height, int damage, double theta, double speed, BufferedImage img, int bulletType, Color color) {
        super(x, y, width, height);
        this.damage = damage;
        this.theta = theta;
        this.speed = speed;
        this.light = new Light2D(x + width / 2, y + height / 2, 0.1f, 30, color.getRed(), color.getGreen(), color.getBlue());
        this.img = img;
        this.bulletType = bulletType;
    }
    
    public int getDamage() {
        return damage;
    }
    
    public Light2D getLight() {
        return light;
    }
    
    public int getType() {
        return bulletType;
    }
    
    @Override
    public void update() {
        // The bullet moves depending on the rotation of the player
        setX(getX() + ((float)(Math.cos(theta + Math.PI / 2)) * 5));
        setY(getY() + ((float)(Math.sin(theta + Math.PI / 2)) * 5));

        // update light
        light.setX(x + width / 2);
        light.setY(y + height / 2);
    }
    
    @Override
    public void render(Graphics2D g) {
        AffineTransform orig = g.getTransform();
        g.translate(getX(), getY());
        g.rotate(theta, getWidth() / 2, getHeight() / 2);
        g.drawImage(img, 0, 0, (int)(getWidth()), (int)(getHeight()), null);
        g.setTransform(orig);
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
