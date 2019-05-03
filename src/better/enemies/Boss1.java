/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.enemies;

import better.assets.Assets;
import better.core.Game;
import better.core.Timer;
import better.game.Bullet;
import better.game.Light2D;
import better.game.Player;
import better.game.StatusBar;
import better.scenes.LevelScreen;
import better.scenes.LevelSelectScreen;
import better.ui.UILabel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Cesar Barraza
 */
public class Boss1 extends Enemy {
    private Timer moveTimer;
    private Timer shootTimer;
    private boolean hasSpawned;
    private UILabel lblName; 
    
    // Level Bullets //
    private ArrayList<Bullet> bullets;
    
    public Boss1(float x, float y, float width, float height, int maxHealth, Player player, ArrayList<Bullet> bullets, ArrayList<Light2D> lights) {
        super(x, y, width, height, maxHealth, player, lights);
        this.bullets = bullets;
        theta = 0;
        moveTimer = new Timer(0);
        shootTimer = new Timer(1);
        hasSpawned = false;
        img = Assets.images.get("EnemyShip1");
        healthBar = new StatusBar(10, 23, 6, 11, Assets.images.get("ArmorBar"), maxHealth, maxHealth, 0.40f);
        lblName = new UILabel(10, 4, "Boss #1", Color.WHITE, UILabel.DEFAULT_FONT);
    }
    
    @Override
    public void render(Graphics2D g) {
        super.render(g);
        
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
                do {
                xPos = (int)(Math.random() * 600) - 300;
                yPos = (int)(Math.random() * 600) -300;
                xPos += getX();
                yPos += getY();
                } while(xPos < 0 || xPos > WIDTH - width || yPos < 0 || yPos > + HEIGHT - height);
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
                hasSpawned = true;
            }
        }
    }
    
    public void shoot(){
        if(!hasSpawned) {
            return;
        }
        
        float xMID = getX() + getWidth()/2;
        float yMID = getY() + getHeight()/2;
        
        if (shootTimer.isActivated()) {
            shootTimer.restart(health > 350 ? Math.random()*2 : (health > 100 ? 0.2d : 0.1d));
            float xF = (float)Math.cos(theta) * 30;
            float yF = (float)Math.sin(theta) * 30;
            bullets.add(new Bullet(xMID + xF, yMID + yF, 10, 10, 5, theta, 8, Assets.images.get("BulletRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
            bullets.add(new Bullet(xMID - xF, yMID - yF, 10, 10, 5, theta, 8, Assets.images.get("BulletRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
        } else {
            shootTimer.update();
        }
    }
    
    @Override
    public void update(){
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
        
        // Movement function
        move();
        
        // shooting function
        shoot();

        // update healtbar
        healthBar.setValue(health);
        
        if(getHealth() <= 0) {
            Game.setCurrentScreen(LevelSelectScreen.getInstance());
        }
        
    }  
  
    @Override
    public Rectangle2D.Float getRect() {
        return new Rectangle2D.Float(x + 32, y + 32, 64, 64);
    }
}
