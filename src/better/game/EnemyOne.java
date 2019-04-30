/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.game;

import better.assets.Assets;
import better.core.Game;
import better.scenes.MainMenuScreen;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

/**
 *
 * @author rogel
 */
public class EnemyOne extends GameObject{
    private double theta;
    private int shootTimer;
    private int moveTimer;
    private ArrayList<EnemyShot> shot;
    private Player player;
    private int xSpeed;
    private int ySpeed;
    private boolean spawning;
    private int health;
    private boolean alive;
    
    public EnemyOne(float width, float height, Player player) {
        super(0, 0, width, height);
        this.player = player;
        theta = -Math.PI/2;
        shootTimer = (int)(Math.random()*200);
        moveTimer = 0;
        shot = new ArrayList<EnemyShot>();
        xSpeed = 0;
        ySpeed = 0;
        spawning = true;
        spawnEnemy();
        health = 20;
        alive = true;
    }

    @Override
    public void render(Graphics2D g) {
        for (int i = 0; i < shot.size(); i++){
            shot.get(i).render(g);
        }
        
        AffineTransform orig = g.getTransform();
        g.translate(getX(), getY());
        g.rotate(theta, getWidth() / 2, getHeight() / 2);
        g.drawImage(Assets.images.get("EnemyShip1"), 0, 0, (int)(getWidth()), (int)(getHeight()), null);
        g.setTransform(orig);
    }
    
    private void spawnEnemy(){
        float pX = player.getX();
        float pY = player.getY();
        
        // width and heignt of the game
        int WIDTH = Game.getDisplay().getWidth();
        int HEIGHT = Game.getDisplay().getHeight();
        
        int cuadrant = 1;
        if (pX < WIDTH/2 && pY > HEIGHT/2) cuadrant = 3;
        if (pX > WIDTH/2 && pY > HEIGHT/2) cuadrant = 2;
        if (pX > WIDTH/2 && pY < HEIGHT/2) cuadrant = 1;
        if (pX < WIDTH/2 && pY < HEIGHT/2) cuadrant = 4;
        
        // get random variables for random spawn position
        int rX = (int)(Math.random()*(WIDTH-100))+50;
        int rY = (int)(Math.random()*(HEIGHT-100))+50;
        int rNum = (int)(Math.random()*2);
        
        switch(cuadrant){
            case 1:
                if (rNum == 0){
                    setX(-100);
                    setY(rY);
                }else{
                    setX(rX);
                    setY(HEIGHT + 100);
                }
                break;
                
            case 2:
                if (rNum == 0){
                    setX(rX);
                    setY(-100);
                }else{
                    setX(-100);
                    setY(rY);
                }
                break;
                
            case 3:
                if (rNum == 0){
                    setX(WIDTH + 100);
                    setY(rY);
                }else{
                    setX(rX);
                    setY(-100);
                }
                break;
                
            case 4:   
                if (rNum == 0){
                    setX(rX);
                    setY(HEIGHT + 100);
                }else{
                    setX(WIDTH + 100);
                    setY(rY);
                }
                break;
                
        }
        
        //System.out.println("X: " + getX() + " Y: " + getY() + "cuadrant: " + cuadrant);
        
    }
    
    private void checkColision(){
        //check for out of bounds collision
        if (getX() >= Game.getDisplay().getWidth() - width){
            xSpeed *= -1;
        }
        else if (getX() <= 0){
            xSpeed *= -1;
        }
        if (getY() >= Game.getDisplay().getHeight() - height){
            ySpeed *= -1;
        }
        else if (getY() <= 0){
            ySpeed *= -1;
        }
    }
    
    @Override
    public void update(){
        int WIDTH = Game.getDisplay().getWidth();
        int HEIGHT = Game.getDisplay().getHeight();
        
        if (spawning){
            if (getX() > WIDTH-100){
                setX(getX()-2);
            }
            else if (getY() > HEIGHT-100){
                setY(getY()-2);
            }
            else if (getX() < 100){
                setX(getX()+2);
            }
            else if (getY() < 100){
                setY(getY()+2);
            }else{
                spawning = false;
            }
        }else{
           checkColision();
            // checks when its time for the next shot
            if (shootTimer <= 0){
                shot.add(new EnemyShot(getX()+getWidth()/2, getY()+getHeight()/2, 10, 10, theta));
                shootTimer = (int)(Math.random()*200);
            }
            shootTimer--;

            // checks when its time for the next change in movement
            if (moveTimer <= 0){
                xSpeed = (int)(Math.random()*7)-3;
                ySpeed = (int)(Math.random()*7)-3;
                moveTimer = (int)(Math.random()*500);
            }
            moveTimer--;
            // moves the object
            setX(getX()+xSpeed);
            setY(getY()+ySpeed);

            // checks if the shot intersected the player
            for (int i = 0; i < shot.size(); i++){
                shot.get(i).update();
                if(shot.get(i).intersects(player) && !player.isDashing()) {
                    shot.remove(i);
                }
            }
            

            // checks if the player shot intersected the enemy
            for (int i = 0; i < player.getShot().size(); i++){
                if(player.getShot().get(i).intersects(this)) {
                    player.getShot().remove(i);
                    setHealth(getHealth()-10); // reduce enemy health when shot
                }
            }
        }
        // checks if enemy has lost all of its health
        if (getHealth() <= 0){ 
            setAlive(false);
        }
        
        // delta X and Y are calculated
        double deltaX = (player.getX()+player.getWidth()/2) - ( x + getHeight() / 2);
        double deltaY = (player.getY()+player.getHeight()/2) - ( y + getWidth() / 2);

        // theta is calculated
        theta = Math.atan2(deltaY, deltaX);
        theta += Math.PI / 2;
        
            
    }
    
    public int getHealth(){
        return health;
    }
    
    public void setHealth(int health){
        this.health = health;
    }
    
    public boolean isAlive(){
        return alive;
    }
    
    public void setAlive(boolean alive){
        this.alive = alive;
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
