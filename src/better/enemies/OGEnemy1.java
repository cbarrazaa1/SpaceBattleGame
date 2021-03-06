/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.enemies;

import better.assets.Assets;
import better.core.Game;
import better.core.Util;
import better.bullets.Bullet;
import better.game.Light2D;
import better.game.Player;
import better.scenes.LevelScreen;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Cesar Barraza
 * @author Rogelio Martinez
 */
public class OGEnemy1 extends Enemy {
    private boolean shouldRenderBar;
    private int xSpeed;
    private int ySpeed;

    
    // Level Bullet List //
    private ArrayList<Bullet> bullets;
    
    public OGEnemy1(float width, float height, int score, int coins, int health, Player player, ArrayList<Bullet> bullets, ArrayList<Light2D> lights) {
        super(0, 0, width, height, score, coins, health, player, lights);
        this.bullets = bullets;
        shouldRenderBar = false;
        theta = 0;
        img = Assets.images.get("EnemyShip1Blue");
        spawnEnemy();
        ySpeed = 4;
        xSpeed = 0;
    }
    /**
     * spawns the enemy
     */
    private void spawnEnemy(){
        int WIDTH = Game.getDisplay().getWidth();
        int HEIGHT = Game.getDisplay().getHeight();
        x = Util.randNum(0,(int)(WIDTH-width));
        y = (int)(0 - height);
    }
    
    /**
     * updates the enemy
     */
    @Override
    public void update(){
        super.update();
 
        // moves the object
        setX(getX() + xSpeed);
        setY(getY() + ySpeed);
        
        // update healthbar
        healthBar.setValue(health);
        healthBar.setX(x);
        healthBar.setY(y - 10);

    }
    /**
     * renders the enemy
     * @param g 
     */
    @Override
    public void render(Graphics2D g) { 
        super.render(g);
        
        if(shouldRenderBar) {
            healthBar.render(g);
        }
    }
    /**
     * creates the hitbox
     * @return 
     */
    @Override
    public Rectangle2D.Float getRect() {
        return new Rectangle2D.Float(x + 16, y + 16, 32, 32);
    }
    /**
     * checks if mouse is on top of the object to render healthbar
     */
    @Override
    public void mouseEnter() {
        shouldRenderBar = true;
    }
    /**
     * checks if mouse is no longer on top of the object
     */
    @Override
    public void mouseLeave() {
        shouldRenderBar = false;
    }
}
