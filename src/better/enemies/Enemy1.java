/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.enemies;

import better.assets.Assets;
import better.core.Game;
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
public class Enemy1 extends Enemy {
    private boolean shouldRenderBar;
    private int xSpeed;
    private int ySpeed;
    private boolean spawning;
    private int shootTimer;
    private int moveTimer;
    
    // Level Bullet List //
    private ArrayList<Bullet> bullets;
    
    public Enemy1(float width, float height, int health, Player player, ArrayList<Bullet> bullets, ArrayList<Light2D> lights) {
        super(0, 0, width, height, health, player, lights);
        this.bullets = bullets;
        shouldRenderBar = false;
        theta = -Math.PI/2;
        shootTimer = (int)(Math.random()*200);
        moveTimer = 0;
        spawning = true;
        img = Assets.images.get("EnemyShip1");
        spawnEnemy();
    }

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
    
    private void checkColision(){
        //check for out of bounds collision
        if(getX() >= Game.getDisplay().getWidth() - width){
            xSpeed *= -1;
            setX(Game.getDisplay().getWidth() - width - 1);
        } else if(getX() <= 0) {
            xSpeed *= -1;
            setX(1);
        }
        
        if(getY() >= Game.getDisplay().getHeight() - height){
            ySpeed *= -1;
            setY(Game.getDisplay().getHeight() - height - 1);
        } else if(getY() <= 0){
            ySpeed *= -1;
            setY(1);
        }
    }
    
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
           checkColision();
            // checks when its time for the next shot
            if (shootTimer <= 0){
                bullets.add(new Bullet(getX() + getWidth() / 2, getY() + getHeight() / 2, 10, 10, 5,
                            theta, 6, Assets.images.get("BulletRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
                shootTimer = (int)(Math.random() * 170);
            }
            shootTimer--;

            // checks when its time for the next change in movement
            if (moveTimer <= 0){
                xSpeed = (int) Util.randNumF(-3, 3);
                ySpeed = (int)Util.randNumF(-3, 3);
                moveTimer = (int)(Math.random() * 500);
            }
            moveTimer--;
            
            // moves the object
            setX(getX() + xSpeed);
            setY(getY() + ySpeed);
        }
        
        // update healthbar
        healthBar.setValue(health);
        healthBar.setX(x);
        healthBar.setY(y - 10);

        // theta is calculated
        theta = this.getThetaTo(player);
        theta -= Math.PI / 2;
        
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
