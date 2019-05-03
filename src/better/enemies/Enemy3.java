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
public class Enemy3 extends Enemy {
    private boolean shouldRenderBar;
    private int xSpeed;
    private int ySpeed;
    private boolean spawning;
    private int shootTimer;
    private int moveTimer;
    
    // Level Bullet List //
    private ArrayList<Bullet> bullets;
    
    public Enemy3(float width, float height, int health, Player player, ArrayList<Bullet> bullets, ArrayList<Light2D> lights) {
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
        
    }
    
    private void move(){
        // moves the way it is looking
        setX(getX() + ((float)(Math.cos(theta + Math.PI / 2)) * 5));
        setY(getY() + ((float)(Math.sin(theta + Math.PI / 2)) * 5));
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
            this.checkColision();
            this.move();
            
        }
        this.turn();
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
