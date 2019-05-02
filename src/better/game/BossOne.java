/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.game;

import better.assets.Assets;
import better.core.Game;
import better.core.Timer;
import better.scenes.LevelScreen;
import better.scenes.LevelSelectScreen;
import better.ui.UILabel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import static java.lang.Math.abs;
import java.util.ArrayList;

/**
 *
 * @author rogel
 */
public class BossOne extends GameObject{
    
    private double theta;
    private ArrayList<EnemyShot> shot;
    private Player player;
    private Timer moveTimer;
    private Timer shootTimer;
    private int health;
    private boolean alive;
    private boolean hasSpawned;
    private StatusBar healthBar;
    private UILabel lblName;
    
    public BossOne(float x, float y, float width, float height, Player player) {
        super(x, y, width, height);
        this.player = player;
        shot = new ArrayList<EnemyShot>();
        theta = 0;
        moveTimer = new Timer(0);
        shootTimer = new Timer(1);
        health = 750;
        alive = true;
        hasSpawned = false;
        healthBar = new StatusBar(10, 23, 6, 11, Assets.images.get("ArmorBar"), health, health, 0.40f);
        lblName = new UILabel(10, 4, "Boss #1", Color.WHITE, UILabel.DEFAULT_FONT);
    }
    
    @Override
    public void render(Graphics2D g) {
        // RENDER SHOT HERE FOR NOW
        for (int i = 0; i < shot.size(); i++){
            shot.get(i).render(g);
        }
        
        AffineTransform orig = g.getTransform();
        g.translate(getX(), getY());
        g.rotate(theta - Math.PI/2, getWidth() / 2, getHeight() / 2);
        g.drawImage(Assets.images.get("EnemyShip1"), 0, 0, (int)(getWidth()), (int)(getHeight()), null);
        g.setTransform(orig);
        
        lblName.render(g);
        healthBar.render(g);
    }
    
    int xPos = 0, yPos = 0; 
    double movTheta = 0;
    public void move(){
        
        int WIDTH =  Game.getDisplay().getWidth();
        int HEIGHT = Game.getDisplay().getHeight();
        
        if(hasSpawned) {
            if(moveTimer.isActivated()){
                do{
                xPos = (int)(Math.random() * 600)-300;
                yPos = (int)(Math.random() * 600)-300;
                xPos += getX();
                yPos += getY();
                }while(xPos < 0 || xPos > WIDTH - width || yPos < 0 || yPos > + HEIGHT - height);
                moveTimer.restart(2);
                movTheta = Math.atan2(getY()-yPos, getX()-xPos);
            }

            if((getX() < xPos - 10 || getX() > xPos + 10) && (getY() < yPos - 10 || getY() > yPos + 10)){
                float speed = (health > 350 ? 2 : (health > 100 ? 4.5f : 5.5f));
                setX(getX() + ((float)(Math.cos(movTheta+Math.PI))*speed));
                setY(getY() + ((float)(Math.sin(movTheta+Math.PI))*speed));
            }
            moveTimer.update();
        } else {
            setY(getY() + 1);
            if(getY() >= 0) {
                setSpawned(true);
            }
        }
    }
    
    public void shoot(){
        if(!hasSpawned) {
            return;
        }
        
        float xMID = getX() + getWidth()/2;
        float yMID = getY() + getHeight()/2;
        
        if (shootTimer.isActivated()){
            shootTimer.restart(health > 350 ? Math.random()*2 : (health > 100 ? 0.2d : 0.1d));
            float xF = (float)Math.cos(theta + Math.PI/2)*30;
            float yF = (float)Math.sin(theta + Math.PI/2)*30;
            shot.add(new EnemyShot(xMID + xF, yMID + yF, 10, 10, theta - Math.PI/2));
            shot.add(new EnemyShot(xMID - xF, yMID - yF, 10, 10, theta - Math.PI/2));
        }else{
            shootTimer.update();
        }
    }
    
    @Override
    public void update(){
        // delta X and Y are calculated
        double deltaX = (player.getX()+player.getWidth()/2) - ( x + getHeight() / 2);
        double deltaY = (player.getY()+player.getHeight()/2) - ( y + getWidth() / 2);
        
        // boss follows the player
        if (theta < Math.atan2(deltaY, deltaX)-.01){
            theta += Math.PI/100;
        }if (theta > Math.atan2(deltaY, deltaX)+.01){
            theta -= Math.PI/100;
        }
        
        // for when the diference in angles is more the 180 degrees
        if (theta - Math.atan2(deltaY, deltaX) > Math.PI){
            theta -= 2*Math.PI;
        }
        if (theta - Math.atan2(deltaY, deltaX) < -Math.PI){
            theta += 2*Math.PI;
        }
        
        // Movement function
        move();
        
        // shooting function
        shoot();

        // update healtbar
        healthBar.setValue(health);
        
        // checks if the shot intersected the player
        for (int i = 0; i < shot.size(); i++){
            shot.get(i).update();
            if(shot.get(i).intersects(player) && !player.isDashing()) {
                player.setHealth(player.getHealth() - 12);
                LevelScreen.getInstance().lightsToRemove.add(shot.get(i).getLight());
                shot.remove(i);
            }
        }

        // checks if the player shot intersected the enemy
        for (int i = 0; i < player.getShot().size(); i++){
            if(player.getShot().get(i).intersects(this)) {
                LevelScreen.getInstance().lightsToRemove.add(player.getShot().get(i).getLight());
                player.getShot().remove(i);
                
                if(health > 350) { 
                    setHealth(getHealth()-10); // reduce enemy health when shot                    
                } else if(health > 100) {
                    setHealth(getHealth() - 4);
                } else {
                    setHealth(getHealth() - 3);
                }
            }
        }
        
        if (getHealth() <= 0) {
            Game.setCurrentScreen(LevelSelectScreen.getInstance());
        }
        
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
    
    public ArrayList<EnemyShot> getShot() {
        return shot;
    }
    
    public void setSpawned(boolean hasSpawned) {
        this.hasSpawned = hasSpawned;
    }
    
    @Override
    public Rectangle2D.Float getRect() {
        return new Rectangle2D.Float(x + 32, y + 32, 64, 64);
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
