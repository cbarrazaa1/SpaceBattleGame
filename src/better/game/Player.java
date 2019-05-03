/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.game;

import better.game.GameObject;
import java.awt.Graphics2D;
import better.assets.Assets;
import better.core.Game;
import better.core.Timer;
import better.scenes.LevelScreen;
import java.awt.Color;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_SPACE;
import static java.awt.event.KeyEvent.VK_UP;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;
/**
 *
 * @author rogel
 */

public class Player extends GameObject {
    private double theta;
    private boolean shooting; // to check if the player has just shot
    private boolean dashing;
    private Timer shotTimer; 
    private Timer dashTimer;
    private ArrayList<Bullet> bullets; // list for the player shots
    private ArrayList<Light2D> lights;
    private int speed;
    private int energy; // energy for dashes or abilities
    private int health; // health of the player
    
    private Timer energyTimer; // timer for energy regeneration
    
    public Player(float x, float y, float width, float height, ArrayList<Bullet> bullets, ArrayList<Light2D> lights) {
        super(x, y, width, height);
        //theta = 0;
        this.shooting = false;
        this.speed = 3;
        this.dashing = false;
        this.health = 100;
        this.energy = 50;
        energyTimer = new Timer(0.1);
        shotTimer = new Timer(0.2);
        dashTimer = new Timer(0.1);   
        this.bullets = bullets;
        this.lights = lights;
    }

    @Override
    public void render(Graphics2D g) {
        AffineTransform orig = g.getTransform();
        g.translate(getX(), getY());
        g.rotate(theta, getWidth() / 2, getHeight() / 2);
        if (!isDashing()){
            g.drawImage(Assets.images.get("PlayerDefault"), 0, 0, (int)(getWidth()), (int)(getHeight()), null);
        }else{
            // TEST LOL // Should be a dashing image
            g.drawImage(Assets.images.get("PlayerDefault"), 0, 0, (int)(getWidth()), (int)(getHeight()), null);
        }
        g.setTransform(orig);    
    }
    
    @Override
    public void update() {
        if(getHealth() <= 0) {
            return;
        }
        
        // delta X and Y are calculated
        double deltaX = Game.getMouseManager().getX() - ( x + getHeight() / 2);
        double deltaY = Game.getMouseManager().getY() - ( y + getWidth() / 2);
        
        // theta is calculated
        theta = Math.atan2(deltaY, deltaX);
        theta += Math.PI / 2;
        
        // this controls the movement of the player
        if (Game.getKeyManager().isKeyDown(KeyEvent.VK_W)){
            setY(getY() - getSpeed());
        }
        if (Game.getKeyManager().isKeyDown(KeyEvent.VK_S)){
            setY(getY() + getSpeed());
        }
        if (Game.getKeyManager().isKeyDown(KeyEvent.VK_A)){
            setX(getX() - getSpeed());
        }
        if (Game.getKeyManager().isKeyDown(KeyEvent.VK_D)){
            setX(getX() + getSpeed());
        }
        
        // this controls the shots of the player
        if (Game.getMouseManager().isButtonDown(MouseEvent.BUTTON1)&& !isShooting()){
            bullets.add(new Bullet(getX() + getWidth() / 2, getY() + getHeight() / 2, 10, 10, 10,
                        theta - Math.PI, 10, Assets.images.get("BulletGreen"), Bullet.BULLET_TYPE_PLAYER, Color.GREEN, lights));      
            Assets.playerShoot.play();
            setShooting(true);
            shotTimer.restart();
        }
        // checks if the player can shoot again
        if (shotTimer.isActivated()){
            setShooting(false);
        }
        
        // dash mecanic version 1
        if (Game.getKeyManager().isKeyPressed(VK_SPACE) && !dashing && getEnergy() >= 15){
            speed = 25;
            dashTimer.restart();
            setDashing(true);
            setEnergy(getEnergy() - 20);
        }
        if(dashTimer.isActivated()){
            speed = 3;
            setDashing(false);
            if (energyTimer.isActivated()){
                // adding energy when not dashing
                setEnergy(getEnergy() + 1);
                energyTimer.restart();
                if (getEnergy() >= 50){
                    setEnergy(50);
                }
            }
            energyTimer.update();
        }
        
        // actualizes shot and dash timer
        shotTimer.update();
        dashTimer.update();
        
        checkColision();
    }
    
    private void checkColision(){
        //check for out of bounds collision
        if (getX() >= Game.getDisplay().getWidth() - width){
            setX(Game.getDisplay().getWidth() - width);
        }
        else if (getX() <= 0){
            setX(0);
        }
        if (getY() >= Game.getDisplay().getHeight() - height){
            setY(Game.getDisplay().getHeight() - height);
        }
        else if (getY() <= 0){
            setY(0);
        }
    }
    /**
     * Returns the player speed
     * @return speed
     */
    public int getSpeed(){
        return speed;
    }
    /**
     * sets the player speed
     * @param speed 
     */
    public void setSpeed(int speed){
        this.speed = speed;
    }
    
    /**
     * checks if dashing
     * @return dashing
     */
    public boolean isDashing(){
        return dashing;
    }
    /**
     * set if dashing
     * @param dashing 
     */
    public void setDashing(boolean dashing){
        this.dashing = dashing;
    }
    
    /**
     * 
     * @return shooting
     */
    public boolean isShooting(){
        return shooting;
    }
    /**
     * setter for shooting
     * @param shooting 
     */
    public void setShooting(boolean shooting){
        this.shooting = shooting;
    }
    
    /**
     * sets player health
     * @param health 
     */
    public void setHealth(int health){
        if(health <= 0) {
            health = 0;
        }
        if(health >= 100) {
            health = 100;
        }
        this.health = health;
    }
    
    /**
     * returns player health
     * @return health
     */
    public int getHealth(){
        return health;
    }
    
    /**
     * sets player energy
     * @param energy 
     */
    public void setEnergy(int energy){
        if(energy <= 0) {
            energy = 0;
        }
        this.energy = energy;
    }
    
    /**
     * returns the player energy
     * @return energy
     */
    public int getEnergy(){
        return energy;
    }
    
    @Override
    public Rectangle2D.Float getRect() {
        return new Rectangle2D.Float(x + 16, y + 16, 32, 32);
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
