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
import better.bullets.GuidedBullet;
import better.core.Timer;
import better.game.Light2D;
import better.game.Player;
import better.scenes.LevelScreen;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Cesar Barraza
 * @author Rogelio Martinez
 * Enemy turret that moves only one direction and shoots a burst of bullets
 */
public class EnemyTurret2 extends Enemy {
    private boolean shouldRenderBar;
    
    private Timer shootTimer;
    private Timer noShootTimer;
    private Timer burstTimer;
    private boolean shouldShoot;

    
    // Level Bullet List //
    private ArrayList<Bullet> bullets;
    
    public EnemyTurret2(float width, float height, int score, int coins, int health, Player player, ArrayList<Bullet> bullets, ArrayList<Light2D> lights) {
        super(0, 0, width, height, score, coins, health, player, lights);
        this.bullets = bullets;
        shouldRenderBar = false;
        theta = 0;
        img = Assets.images.get("GunTurret_Red");
        spawnEnemy();
        shootTimer = new Timer(0);
        noShootTimer = new Timer(0);
        burstTimer = new Timer(0.2);
        shouldShoot = true;
    }
    /**
     * spawns the enemy
     */
    private void spawnEnemy(){
        x = Game.getDisplay().getWidth() + width;
        y = Util.randNum(0, Game.getDisplay().getHeight() - (int)height);
    }
    /**
     * checks if out of bounds
     */
    private void checkColision(){
        if (x < 0){
            shouldShoot = false;
        }
    }
    /**
     * shoots a burst of bullets
     */
    private void shoot(){
        if(burstTimer.isActivated()){
            burstTimer.restart();
            bullets.add(new Bullet(getX() + getWidth() / 2, getY() + getHeight() / 2, 10, 10, 5,
                        theta, 6, Assets.images.get("BulletEnemyRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
        }
        burstTimer.update();
     }
    
    /**
     * turns towards the player
     */
    private void turn(){
        // theta is calculated
        theta = this.getThetaTo(player);
        theta -= Math.PI / 2;
    }
    /**
     * moves to the left
     */
    private void move(){
        x -= 1.5;
    }
    /**
     * updates the object
     */
    @Override
    public void update(){
        super.update();
        //int WIDTH = Game.getDisplay().getWidth();
        //int HEIGHT = Game.getDisplay().getHeight();

        checkColision();
        move();
        if (shouldShoot){
            if(shootTimer.isActivated()){
                shoot();
                noShootTimer.update();
            }
            if(noShootTimer.isActivated()){
                shootTimer.restart(Util.randNumF(1f, 1.5f));
                noShootTimer.restart(Util.randNumF(1f, 3f));
            }
            shootTimer.update();
            turn();
        }

        // update healthbar
        healthBar.setValue(health);
        healthBar.setX(x);
        healthBar.setY(y - 10);

        
        
    }
    /**
     * renders the base and the healthbar
     * @param g 
     */
    @Override
    public void render(Graphics2D g) { 
        //GunTurretMount
        AffineTransform orig = g.getTransform();
        g.translate(getX()-16, getY()-16);
        g.drawImage(Assets.images.get("GunTurretMount"), 0, 0, (int)getWidth()+32, (int)getHeight()+32, null);
        g.setTransform(orig);
        super.render(g);
        
        if(shouldRenderBar) {
            healthBar.render(g);
        }
    }
    /**
     * creates the hitbox
     * @return rect
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
