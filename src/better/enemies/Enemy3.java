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
 * Enemy that follows the player and shoots
 */
public class Enemy3 extends Enemy {
    private boolean shouldRenderBar;
    private int xSpeed;
    private int ySpeed;
    private boolean spawning;
    private int moveTimer;
    private Timer shotTimer;
    private Timer shotTimer2;
    
    // Level Bullet List //
    private ArrayList<Bullet> bullets;
    
    public Enemy3(float width, float height, int score, int coins, int health, Player player, ArrayList<Bullet> bullets, ArrayList<Light2D> lights) {
        super(0, 0, width, height, score, coins, health, player, lights);
        this.bullets = bullets;
        shouldRenderBar = false;
        theta = -Math.PI/2;
        moveTimer = 0;
        spawning = true;
        img = Assets.images.get("EnemyShip1");
        shotTimer = new Timer(1);
        shotTimer2 = new Timer(0.2);
        spawnEnemy();
    }
    /**
     * spawns the object
     */
    private void spawnEnemy(){
        float pX = player.getX();
        float pY = player.getY();
        
        // width and heignt of the game
        int WIDTH = Game.getDisplay().getWidth();
        int HEIGHT = Game.getDisplay().getHeight();
        
        int cuadrant = 1;
        if (pX < WIDTH / 2 && pY > HEIGHT / 2) cuadrant = 3;
        if (pX > WIDTH / 2 && pY > HEIGHT / 2) cuadrant = 2;
        if (pX > WIDTH / 2 && pY < HEIGHT / 2) cuadrant = 1;
        if (pX < WIDTH / 2 && pY < HEIGHT / 2) cuadrant = 4;
        
        // get random variables for random spawn position
        int rX = (int)(Math.random() * (WIDTH - 100)) + 50;
        int rY = (int)(Math.random() * (HEIGHT - 100)) + 50;
        int rNum = (int)(Math.random() * 2);
        
        switch(cuadrant){
            case 1:
                if(rNum == 0){
                    setX(-100);
                    setY(rY);
                } else {
                    setX(rX);
                    setY(HEIGHT + 100);
                }
                break;
            case 2:
                if(rNum == 0){
                    setX(rX);
                    setY(-100);
                } else {
                    setX(-100);
                    setY(rY);
                }
                break;
            case 3:
                if(rNum == 0){
                    setX(WIDTH + 100);
                    setY(rY);
                } else {
                    setX(rX);
                    setY(-100);
                }
                break;
            case 4:   
                if(rNum == 0){
                    setX(rX);
                    setY(HEIGHT + 100);
                } else {
                    setX(WIDTH + 100);
                    setY(rY);
                }
                break;      
        }
    }
    /**
     * checks if collided with outer limits
     */
    private void checkColision(){
        //check for out of bounds collision
        if(getX() >= Game.getDisplay().getWidth() - width){
            setX(Game.getDisplay().getWidth() - width);
        } else if(getX() <= 0) {
            setX(0);
        }
        
        if(getY() >= Game.getDisplay().getHeight() - height){
            setY(Game.getDisplay().getHeight() - height);
        } else if(getY() <= 0){
            setY(0);
        }
    }
    /**
     * turns the enemy to the player's position
     */
    private void turn(){
        // delta X and Y are calculated
        double deltaX = (player.getX()+player.getWidth()/2) - ( x + getHeight() / 2);
        double deltaY = (player.getY()+player.getHeight()/2) - ( y + getWidth() / 2);
        
        // boss follows the player
        if (theta + Math.PI/2 < Math.atan2(deltaY, deltaX)-.01){
            theta += Math.PI/100;
        }if (theta + Math.PI/2 > Math.atan2(deltaY, deltaX)+.01){
            theta -= Math.PI/100;
        }
        
        // for when the diference in angles is more the 180 degrees
        if (theta + Math.PI/2 - Math.atan2(deltaY, deltaX) > Math.PI){
            theta -= 2*Math.PI;
        }
        if (theta + Math.PI/2 - Math.atan2(deltaY, deltaX) < -Math.PI){
            theta += 2*Math.PI;
        }
        
        if(theta + Math.PI/2 < Math.atan2(deltaY, deltaX) + 0.001 && theta + Math.PI/2 > Math.atan2(deltaY, deltaX) - 0.001 ){
            if (shotTimer2.isActivated()){
                shoot();
                shotTimer2.restart();
            }
        }
        shotTimer2.update();
        
    }
    /**
     * moves to where the enemy is looking
     */
    private void move(){
        // moves the way it is looking
        setX(getX() + ((float)(Math.cos(theta + Math.PI / 2)) * 4));
        setY(getY() + ((float)(Math.sin(theta + Math.PI / 2)) * 4));
    }
    
    /**
     * shoots when facing the player
     */
    private void shoot(){
            float xMID = getX() + getWidth()/2;
            float yMID = getY() + getHeight()/2;
            float xF = (float)Math.cos(theta) * 30;
            float yF = (float)Math.sin(theta) * 30;
            bullets.add(new Bullet(xMID + xF, yMID + yF, 8, 17, 10, theta, 8, Assets.images.get("BulletEnemyRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
            bullets.add(new Bullet(xMID - xF, yMID - yF, 8, 17, 10, theta, 8, Assets.images.get("BulletEnemyRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
    }
    /**
     * updates the object
     */
    @Override
    public void update(){
        super.update();
        int WIDTH = Game.getDisplay().getWidth();
        int HEIGHT = Game.getDisplay().getHeight();
        
        if(spawning) {
            if(getX() > WIDTH - 100) {
                setX(getX() - 2);
            }
            else if(getY() > HEIGHT - 100) {
                setY(getY() - 2);
            }
            else if(getX() < 100) {
                setX(getX() + 2);
            }
            else if(getY() < 100){
                setY(getY() + 2);
            } else {
                spawning = false;
            }
        } else {
            this.checkColision();
            this.move();
            if (shotTimer.isActivated()){
                this.shoot();
                shotTimer.restart(Util.randNumF(0.5f, 2f));
            }
            shotTimer.update();
        }
        this.turn();
        // update healthbar
        healthBar.setValue(health);
        healthBar.setX(x);
        healthBar.setY(y - 10);
        
    }
    /**
     * renders the healthbar
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
     * creates hitbox
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
