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
 * @author Cesar Barraza and Rogelio Martinez
 * Enemy that moves slowly and shoots guided bullets
 */
public class EnemyTurret1 extends Enemy {
    private boolean shouldRenderBar;
    private int xSpeed;
    private int ySpeed;
    private Timer shootTimer;
    private boolean shouldShoot;

    
    // Level Bullet List //
    private ArrayList<Bullet> bullets;
    
    public EnemyTurret1(float width, float height, int score, int coins, int health, Player player, ArrayList<Bullet> bullets, ArrayList<Light2D> lights) {
        super(0, 0, width, height, score, coins, health, player, lights);
        this.bullets = bullets;
        shouldRenderBar = false;
        theta = 0;
        img = Assets.images.get("GunTurret_Red");
        spawnEnemy();
        shootTimer = new Timer(0.6);
        shouldShoot = true;
    }

    private void spawnEnemy(){
        x = Game.getDisplay().getWidth() + width;
        y = Util.randNum(0, Game.getDisplay().getHeight() - (int)height);
    }
    
    private void checkColision(){
        if (x < 0){
            shouldShoot = false;
        }
    }
    
    private void shoot(){
        if(shootTimer.isActivated()){
            shootTimer.restart();
            bullets.add(new GuidedBullet(getX() + getWidth() / 2, getY() + getHeight() / 2, 10, 10, 5,
                        theta, 6, Assets.images.get("BulletEnemyRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights, player));
        }
        shootTimer.update();
     }
    
    private void turn(){
        // theta is calculated
        theta = this.getThetaTo(player);
        theta -= Math.PI / 2;
    }
    
    private void move(){
        x -= 1.5;
    }
    
    @Override
    public void update(){
        super.update();
        //int WIDTH = Game.getDisplay().getWidth();
        //int HEIGHT = Game.getDisplay().getHeight();

        checkColision();
        move();
        if (shouldShoot){
            shoot();
            turn();
        }

        // update healthbar
        healthBar.setValue(health);
        healthBar.setX(x);
        healthBar.setY(y - 10);

        
        
    }
    
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
    
    @Override
    public Rectangle2D.Float getRect() {
        return new Rectangle2D.Float(x + 16, y + 16, 32, 32);
    }
    
    @Override
    public void mouseEnter() {
        shouldRenderBar = true;
    }
    
    @Override
    public void mouseLeave() {
        shouldRenderBar = false;
    }
}
