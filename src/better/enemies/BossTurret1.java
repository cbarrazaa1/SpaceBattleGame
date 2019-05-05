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
import better.game.StatusBar;
import better.scenes.LevelScreen;
import better.ui.UILabel;
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
public class BossTurret1 extends Enemy {
    
    
    private Timer shootTimer;
    private Timer shootTimer2;
    private Timer shootTimer3;
    private boolean shouldShoot;
    private UILabel lblName;

    
    // Level Bullet List //
    private ArrayList<Bullet> bullets;
    
    public BossTurret1(float width, float height, int score, int coins, int health, Player player, ArrayList<Bullet> bullets, ArrayList<Light2D> lights) {
        super(0, 0, width, height, score, coins, health, player, lights);
        this.bullets = bullets;
        
        theta = 0;
        img = Assets.images.get("GunTurret_Teal");
        spawnEnemy();
        shootTimer = new Timer(0.6);
        shootTimer2 = new Timer(0.6);
        shootTimer3 = new Timer(0.6);
        shouldShoot = false;
        healthBar = new StatusBar(10, 23, 6, 11, Assets.images.get("ArmorBar"), maxHealth, maxHealth, 0.40f);
        lblName = new UILabel(10, 4, "Need new Sprite lol", Color.WHITE, UILabel.DEFAULT_FONT);
    }

    private void spawnEnemy(){
        x = Game.getDisplay().getWidth() + width;
        y = Game.getDisplay().getHeight()/2 - height/2;
    }
    
    private void checkColision(){
        if (x < 0){
            shouldShoot = false;
        }
    }
    
    private void shoot(){
        float xMID = getX() + getWidth()/2;
        float yMID = getY() + getHeight()/2;
        
        if (shootTimer.isActivated()) {
            // for shooting in bursts
            float restart;
            restart = Util.randNum(1, 4);
            if (restart == 1){
                restart = 2;
            }else{
                restart = 0.2f;
            }
            shootTimer.restart(restart);
            float xF = (float)Math.cos(theta) * 15;
            float yF = (float)Math.sin(theta) * 15;
            bullets.add(new Bullet(xMID + xF, yMID + yF, 14, 30, 10, theta + Math.PI/4, 7, Assets.images.get("BulletEnemyRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
            bullets.add(new Bullet(xMID - xF, yMID - yF, 14, 30, 10, theta - Math.PI/4, 7, Assets.images.get("BulletEnemyRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
            bullets.add(new Bullet(xMID + xF, yMID + yF, 14, 30, 10, theta + Math.PI/16, 7, Assets.images.get("BulletEnemyRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
            bullets.add(new Bullet(xMID - xF, yMID - yF, 14, 30, 10, theta - Math.PI/16, 7, Assets.images.get("BulletEnemyRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
            bullets.add(new Bullet(xMID + xF, yMID + yF, 14, 30, 10, theta + Math.PI/32, 7, Assets.images.get("BulletEnemyRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
            bullets.add(new Bullet(xMID - xF, yMID - yF, 14, 30, 10, theta - Math.PI/32, 7, Assets.images.get("BulletEnemyRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
            
        }
        shootTimer.update();
        
    }
    
    private void shoot2(){

        // for shooting in bursts
        float restart;
        restart = Util.randNum(1, 2);
        if (restart == 1){
            restart = 2;
        }else{
            restart = 0.3f;
        }
        if (shootTimer2.isActivated()){
            shootTimer2.restart(restart);
            bullets.add(new GuidedBullet(getX() + getWidth() / 2, getY() + getHeight() / 2, 20, 25, 8,
                        theta + Math.PI/6, 8, Assets.images.get("BulletEnemyBlue"), Bullet.BULLET_TYPE_ENEMY, Color.BLUE, lights, player));
            bullets.add(new GuidedBullet(getX() + getWidth() / 2, getY() + getHeight() / 2, 20, 25, 8,
                        theta - Math.PI/6, 8, Assets.images.get("BulletEnemyBlue"), Bullet.BULLET_TYPE_ENEMY, Color.BLUE, lights, player));    
            bullets.add(new GuidedBullet(getX() + getWidth() / 2, getY() + getHeight() / 2, 20, 25, 8,
                        theta + Math.PI/4, 8, Assets.images.get("BulletEnemyBlue"), Bullet.BULLET_TYPE_ENEMY, Color.BLUE, lights, player));
            bullets.add(new GuidedBullet(getX() + getWidth() / 2, getY() + getHeight() / 2, 20, 25, 8,
                        theta - Math.PI/4, 8, Assets.images.get("BulletEnemyBlue"), Bullet.BULLET_TYPE_ENEMY, Color.BLUE, lights, player));    
        
        }
        shootTimer2.update();
    }
    
    private void shoot3(){
        float xMID = getX() + getWidth()/2;
        float yMID = getY() + getHeight()/2;

            if (shootTimer3.isActivated()){
            bullets.add(new Bullet(xMID-14, yMID-14, 24, 64, 49, theta, 25, Assets.images.get("BulletEnemyRed"), Bullet.BULLET_TYPE_ENEMY, Color.WHITE, lights));
            shootTimer3.restart(Util.randNum(1, 5));
            }
            shootTimer3.update();
        
        shootTimer3.update();
    }
    
    private void turn(){
        // theta is calculated
        theta = this.getThetaTo(player);
        theta -= Math.PI / 2;
    }
    
    private void move(){
        if (x > Game.getDisplay().getWidth() - width - 20){
            x -= 1.5;
        }else{
            shouldShoot = true;
        }
    }
    
    @Override
    public void update(){
        super.update();
        //int WIDTH = Game.getDisplay().getWidth();
        //int HEIGHT = Game.getDisplay().getHeight();

        checkColision();
        move();
        if (shouldShoot){
            if (health > 1200){
                shoot();
                shoot2();
                
            }else if (health > 800){
                shoot3();
                shoot2();
            }else{
                shoot();
                shoot2();
                shoot3();
            }
            
        }
        turn();
        // update healtbar
        healthBar.setValue(health);

        
        
    }
    
    @Override
    public void render(Graphics2D g) { 
        //GunTurretMount
        AffineTransform orig = g.getTransform();
        g.translate(getX()-32, getY()-32);
        g.drawImage(Assets.images.get("GunTurretMount"), 0, 0, (int)getWidth()+64, (int)getHeight()+64, null);
        g.setTransform(orig);
        super.render(g);
        
        lblName.render(g);
        healthBar.render(g);
        
    }
    
    @Override
    public Rectangle2D.Float getRect() {
        return new Rectangle2D.Float(x + 16, y + 16, 112, 112);
    }
    
    @Override
    public void mouseEnter() {
        
    }
    
    @Override
    public void mouseLeave() {
        
    }
}
