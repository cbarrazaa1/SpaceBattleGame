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
public abstract class Enemy extends GameObject {
    private int health;
    private double theta;
    private StatusBar healthBar;
    private Player player;
    private BufferedImage img;
    
    public Enemy(float x, float y, float width, float height, int health, Player player, BufferedImage img) {
        super(x, y, width, height);
        this.health = health;
        this.player = player;
        this.healthBar = new StatusBar(x, y, 6, 11, Assets.images.get("ArmorBar"), health, health, width / health);
    }

    @Override
    public abstract void update();
    
    @Override
    public void render(Graphics2D g) {
        AffineTransform orig = g.getTransform();
        g.translate(getX(), getY());
        g.rotate(theta, getWidth() / 2, getHeight() / 2);
        g.drawImage(img, 0, 0, (int)(getWidth()), (int)(getHeight()), null);
        g.setTransform(orig);
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
