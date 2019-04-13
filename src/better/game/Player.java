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
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
/**
 *
 * @author rogel
 */

public class Player extends GameObject {
    private double theta;
    private boolean shooting; // to check if the player has just shot
    private int shotTimer; 
    private ArrayList<PlayerShot> shots; // list for the player shots
    
    public Player(float x, float y, float width, float height) {
        super(x, y, width, height);
        //theta = 0;
        shots = new ArrayList<PlayerShot>();
        this.shooting = false;
        this.shotTimer = 0;
    }

    @Override
    public void render(Graphics2D g) {
        // render every shot
        for (int i = 0; i < shots.size(); i++){
            shots.get(i).render(g);
        }
        
        AffineTransform orig = g.getTransform();
        g.translate(getX(), getY());
        g.rotate(theta, getWidth() / 2, getHeight() / 2);
        g.drawImage(Assets.images.get("SpaceShipv1"), 0, 0, (int)(getWidth()), (int)(getHeight()), null);
        g.setTransform(orig);        
    }

    @Override
    public void update() {
        // delta X and Y are calculated
        double deltaX = Game.getMouseManager().getX() - ( x + getHeight() / 2);
        double deltaY = Game.getMouseManager().getY() - ( y + getWidth() / 2);
        
        // theta is calculated
        theta = Math.atan2(deltaY, deltaX);
        theta += Math.PI / 2;
        
        // this controls the movement of the player
        if (Game.getKeyManager().isKeyDown(KeyEvent.VK_W)){
            setY(getY() - 3);
        }
        if (Game.getKeyManager().isKeyDown(KeyEvent.VK_S)){
            setY(getY() + 3);
        }
        if (Game.getKeyManager().isKeyDown(KeyEvent.VK_A)){
            setX(getX() - 3);
        }
        if (Game.getKeyManager().isKeyDown(KeyEvent.VK_D)){
            setX(getX() + 3);
        }
        
        // this controls the shots of the player
        if (Game.getMouseManager().isButtonDown(MouseEvent.BUTTON1)&& !isShooting()){
            shots.add(new PlayerShot(getX()+getWidth()/2, getY()+getHeight()/2, 10, 10, theta));
            setShooting(true);
            setShotTimer(20);
        }
        // checks if the player can shoot again
        if (readyToShoot()){
            setShooting(false);
        }
        // update every shot
        for (int i = 0; i < shots.size(); i++){
            shots.get(i).update();
        }
        // actualizes shot timer
        actShotTimer();
    }
    /**
     * 
     * @return if the shot timer is less than 1 it returns true, else, false
     */
    public boolean readyToShoot(){
        return shotTimer <= 0;
    }
    /**
     * actualizes the timer
     */
    public void actShotTimer(){
        shotTimer--;
    }
    /**
     * Setter for the shotTimer
     * @param shot 
     */
    public void setShotTimer(int shot){
        shotTimer = shot;
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