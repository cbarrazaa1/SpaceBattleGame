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
    
    public EnemyOne(float x, float y, float width, float height, Player player) {
        super(x, y, width, height);
        this.player = player;
        theta = -Math.PI/2;
        shootTimer = (int)(Math.random()*200);
        moveTimer = 0;
        shot = new ArrayList<EnemyShot>();
        xSpeed = 0;
        ySpeed = 0;
    }

    @Override
    public void render(Graphics2D g) {
        for (int i = 0; i < shot.size(); i++){
            shot.get(i).render(g);
        }
        
        AffineTransform orig = g.getTransform();
        g.translate(getX(), getY());
        g.rotate(theta, getWidth() / 2, getHeight() / 2);
        g.drawImage(Assets.images.get("Enemy1v1"), 0, 0, (int)(getWidth()), (int)(getHeight()), null);
        g.setTransform(orig);
    }

    @Override
    public void update(){
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
            if(shot.get(i).intersects(player)) {
                shot.remove(i);
            }
        }
        
        // checks if the shot intersected the player
        for (int i = 0; i < player.getShot().size(); i++){
            if(player.getShot().get(i).intersects(this)) {
                player.getShot().remove(i);
            }
        }
        
        // delta X and Y are calculated
        double deltaX = (player.getX()+player.getWidth()/2) - ( x + getHeight() / 2);
        double deltaY = (player.getY()+player.getHeight()/2) - ( y + getWidth() / 2);
        
        // theta is calculated
        theta = Math.atan2(deltaY, deltaX);
        theta += Math.PI / 2;
        
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
