/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.enemies;

import better.assets.Assets;
import better.core.Game;
import better.core.Timer;
import better.bullets.Bullet;
import better.bullets.GuidedBullet;
import better.core.Util;
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
import static java.lang.Math.abs;
import java.util.ArrayList;

/**
 *
 * @author Cesar Barraza
 * @author Rogelio Martinez
 */
public class Boss3 extends Enemy {
    private Timer moveTimer;
    private Timer shootTimer;
    private Timer shootTimer2;
    private Timer endDash;
    private Timer startDash;
    private boolean hasSpawned;
    private UILabel lblName;
    float xSpeed;
    float ySpeed;
    
    // Level Bullets //
    private ArrayList<Bullet> bullets;
    
    public Boss3(float x, float y, float width, float height, int score, int coins, int maxHealth, Player player, ArrayList<Bullet> bullets, ArrayList<Light2D> lights) {
        super(x, y, width, height, score, coins, maxHealth, player, lights);
        this.bullets = bullets;
        theta = 0;
        moveTimer = new Timer(0);
        shootTimer = new Timer(1);
        shootTimer2 = new Timer(1);
        endDash = new Timer(4);
        startDash = new Timer(8);
        hasSpawned = false;
        img = Assets.images.get("EnemyShip1");
        healthBar = new StatusBar(10, 23, 6, 11, Assets.images.get("ArmorBar"), maxHealth, maxHealth, 0.40f);
        lblName = new UILabel(10, 4, "Boss #1", Color.WHITE, UILabel.DEFAULT_FONT);
        xSpeed = 0;
        ySpeed = 0;
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
        
        
        if(moveTimer.isActivated()){
            do {
            xPos = (int)(Math.random() * 1000) - 500;
            yPos = (int)(Math.random() * 1000) - 500;
            xPos += getX();
            yPos += getY();
            } while(xPos < 0 || xPos > WIDTH - width || yPos < 0 || yPos > + HEIGHT - height);
            moveTimer.restart(1);
            movTheta = Math.atan2(getY()-yPos, getX()-xPos);
        }

        if((getX() < xPos - 10 || getX() > xPos + 10) && (getY() < yPos - 10 || getY() > yPos + 10)){
            float speed = (health > 300 ? 5 : (health > 100 ? 6f : 7f));
            setX(getX() + ((float)(Math.cos(movTheta+Math.PI))*speed));
            setY(getY() + ((float)(Math.sin(movTheta+Math.PI))*speed));
        }
        moveTimer.update();
        
    }
    
    public void shoot(){
        if(!hasSpawned) {
            return;
        }
        
        float xMID = getX() + getWidth()/2;
        float yMID = getY() + getHeight()/2;
        
        if (shootTimer.isActivated()) {
            shootTimer.restart(health > 300 ? Util.randNumF(0.2f, 2) : (health > 100 ? Util.randNumF(0.2f, 1) : Util.randNumF(0.1f, 0.5f)));
            float xF = (float)Math.cos(theta) * 30;
            float yF = (float)Math.sin(theta) * 30;
            bullets.add(new Bullet(xMID + xF, yMID + yF, 8, 17, 10, theta, 12, Assets.images.get("BulletEnemyRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
            bullets.add(new Bullet(xMID - xF, yMID - yF, 8, 17, 10, theta, 12, Assets.images.get("BulletEnemyRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
        } else {
            shootTimer.update();
        }
    }
    
    private void turn(){
        // delta X and Y are calculated
        double deltaX = (player.getX()+player.getWidth()/2) - ( x + getHeight() / 2);
        double deltaY = (player.getY()+player.getHeight()/2) - ( y + getWidth() / 2);
        
        // boss follows the player
        if (theta + Math.PI/2 < Math.atan2(deltaY, deltaX)-.01){
            theta += Math.PI/20;
        }if (theta + Math.PI/2 > Math.atan2(deltaY, deltaX)+.01){
            theta -= Math.PI/20;
        }
        
        // for when the diference in angles is more the 180 degrees
        if (theta + Math.PI/2 - Math.atan2(deltaY, deltaX) > Math.PI){
            theta -= 2*Math.PI;
        }
        if (theta + Math.PI/2 - Math.atan2(deltaY, deltaX) < -Math.PI){
            theta += 2*Math.PI;
        }
    }
    
    private void dash(){
        if (moveTimer.isActivated()){
            moveTimer.restart(Util.randNumF(0.5f, 1f));
            do{
            xSpeed = Util.randNumF(-8, 8);
            ySpeed = Util.randNumF(-8, 8);
            }while(abs(xSpeed) < 6 && abs(ySpeed) < 6);
        }
        moveTimer.update();
        x += xSpeed;
        y += ySpeed;
        // boss follows the direction it is going
        if (theta + Math.PI/2 < Math.atan2(ySpeed, xSpeed)-.01){
            theta += Math.PI/10;
        }if (theta + Math.PI/2 > Math.atan2(ySpeed, xSpeed)+.01){
            theta -= Math.PI/10;
        }
        // for when the diference in angles is more the 180 degrees
        if (theta + Math.PI/2 - Math.atan2(ySpeed, xSpeed) > Math.PI){
            theta -= 2*Math.PI;
        }
        if (theta + Math.PI/2 - Math.atan2(ySpeed, xSpeed) < -Math.PI){
            theta += 2*Math.PI;
        }
        
        // delta X and Y of the player are calculated
        double deltaX = (player.getX()+player.getWidth()/2) - ( x + getHeight() / 2);
        double deltaY = (player.getY()+player.getHeight()/2) - ( y + getWidth() / 2);
        double playerTheta = Math.atan2(deltaY, deltaX) - Math.PI/2;
        if (shootTimer2.isActivated()){
            shootTimer2.restart(Util.randNumF(0.2f, 0.4f));
            bullets.add(new GuidedBullet(getX() + getWidth() / 2, getY() + getHeight() / 2, 12, 12, 8,
                            playerTheta, 5, Assets.images.get("ProtonShot"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights, player));
                
        }
        shootTimer2.update();
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
    
    private void bounceOnColision(){
        //check for out of bounds collision
        if(getX() >= Game.getDisplay().getWidth() - width){
            setX(Game.getDisplay().getWidth() - width);
            xSpeed *= -1;
        } else if(getX() <= 0) {
            setX(0);
            xSpeed *= -1;
        }
        
        if(getY() >= Game.getDisplay().getHeight() - height){
            setY(Game.getDisplay().getHeight() - height);
            ySpeed *= -1;
        } else if(getY() <= 0){
            setY(0);
            ySpeed *= -1;
        }
    }
    @Override
    public void update(){
        if (!hasSpawned){
            y += 8;
            if (y > 0){
                hasSpawned = true;
            }
        }
        else if (!endDash.isActivated()){
           startDash.restart();
           this.dash();
           this.bounceOnColision();
           endDash.update();
        }else{
            shoot();
            turn();
            move();
            startDash.update();
            this.checkColision();
        }
        
        if (startDash.isActivated()){
            endDash.restart();
        }
        
        // update healtbar
        healthBar.setValue(health);
        
        if(getHealth() <= 0) {
            Game.setCurrentScreen(LevelSelectScreen.getInstance());
        }
        
    }  
  
    @Override
    public Rectangle2D.Float getRect() {
        return new Rectangle2D.Float(x + 12, y + 12, 36, 36);
    }
}
