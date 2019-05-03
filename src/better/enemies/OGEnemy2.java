/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.enemies;

import better.assets.Assets;
import better.core.Game;
import better.core.Timer;
import better.core.Util;
import better.game.Bullet;
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
 */
public class OGEnemy2 extends Enemy {
    private boolean shouldRenderBar;
    private int xSpeed;
    private int ySpeed;
    private boolean spawning;
    private Timer moveTimer;
    private Timer shootTimer;
    
    // Level Bullet List //
    private ArrayList<Bullet> bullets;
    
    public OGEnemy2(float width, float height, int score, int coins, int health, Player player, ArrayList<Bullet> bullets, ArrayList<Light2D> lights) {
        super(0, 0, width, height, score, coins, health, player, lights);
        this.bullets = bullets;
        shouldRenderBar = false;
        theta = 0;
        img = Assets.images.get("EnemyShip1");
        spawnEnemy();
        spawning = true;
        ySpeed = 0;
        xSpeed = -3;
        moveTimer = new Timer(0);
        shootTimer = new Timer(1);
    }

    private void spawnEnemy(){
        int WIDTH = Game.getDisplay().getWidth();
        int HEIGHT = Game.getDisplay().getHeight();
        x = WIDTH + width;
        y = 10;
    }
    
    private void shoot(){
        bullets.add(new Bullet(getX() + getWidth() / 2, getY() + getHeight() / 2, 10, 10, 5,
                    theta, 6, Assets.images.get("BulletRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
    }
    
    private void move(){
        ySpeed = Util.randNum(-3, 3);
        
    }
    private void checkCollision(){
        if (y > Game.getDisplay().getHeight()/2-height){
            ySpeed *= -1;
            y = Game.getDisplay().getHeight()/2-height;
        }
        if (y < 10){
            ySpeed *= -1;
            y = 10;
        }
        if (x < 0){
            xSpeed *= -1;
            x = 0;
        }
        if (x > Game.getDisplay().getWidth() - width){
            xSpeed*= -1;
            x= Game.getDisplay().getWidth() - width;
        }
    }
    @Override
    public void update(){
        super.update();
        if (spawning){
            setX(getX() - 3);
            if (x <= Game.getDisplay().getWidth() - width){
                spawning = false;
            }
        }else{
            // moves the object
            setX(getX() + xSpeed);
            setY(getY() + ySpeed);
            
            if (moveTimer.isActivated()){
                moveTimer.restart(3);
                move();
            }
            
            if (shootTimer.isActivated()){
                shootTimer.restart();
                shoot();
            }
            this.checkCollision();
            
            moveTimer.update();
            shootTimer.update();
        }  
        
        // update healthbar
        healthBar.setValue(health);
        healthBar.setX(x);
        healthBar.setY(y - 10);
        

    }
    
    @Override
    public void render(Graphics2D g) { 
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
