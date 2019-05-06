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
import java.util.ArrayList;

/**
 *
 * @author Cesar Barraza
 * @author Rogelio Martinez
 * Boss with twin that moves and shoot semirandomly
 */
public class TwinBoss2 extends Enemy {
    private Timer moveTimer;
    private Timer shootTimer;
    private Timer shootTimer2;
    private Timer shootTimer3;
    private boolean hasSpawned;
    private UILabel lblName; 
    private boolean angry;
    private boolean healthfull;
    
    // Level Bullets //
    private ArrayList<Bullet> bullets;
    
    public TwinBoss2(float x, float y, float width, float height, int score, int coins, int maxHealth, Player player, ArrayList<Bullet> bullets, ArrayList<Light2D> lights) {
        super(x, y, width, height, score, coins, maxHealth, player, lights);
        this.bullets = bullets;
        theta = 0;
        moveTimer = new Timer(0);
        shootTimer = new Timer(1);
        shootTimer2 = new Timer(2);
        shootTimer3 = new Timer(3);
        hasSpawned = false;
        img = Assets.images.get("EnemyShip1");
        healthBar = new StatusBar(10, 53, 6, 11, Assets.images.get("ArmorBar"), maxHealth, maxHealth, 0.40f);
        lblName = new UILabel(10, 34, "Luigi", Color.WHITE, UILabel.DEFAULT_FONT);
        angry = false;
        healthfull = false;
    }
    /**
     * render the healthbar
     * @param g 
     */
    @Override
    public void render(Graphics2D g) {
        super.render(g);
        
        lblName.render(g);
        healthBar.render(g);
    }
    /**
     * moves to a random position
     */
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
                float speed = (health > 350 ? 4 : (health > 100 ? 5.5f : 6.5f));
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
    /**
     * shoots normal bullets
     */
    public void shoot(){
        if(!hasSpawned) {
            return;
        }
        
        float xMID = getX() + getWidth()/2;
        float yMID = getY() + getHeight()/2;
        
        float xF = (float)Math.cos(theta) * 30;
        float yF = (float)Math.sin(theta) * 30;
        bullets.add(new Bullet(xMID + xF, yMID + yF, 8, 17, 10, theta, 8, Assets.images.get("BulletEnemyBlue"), Bullet.BULLET_TYPE_ENEMY, Color.BLUE, lights));
        bullets.add(new Bullet(xMID - xF, yMID - yF, 8, 17, 10, theta, 8, Assets.images.get("BulletEnemyBlue"), Bullet.BULLET_TYPE_ENEMY, Color.BLUE, lights));
    }
    /**
     * shoots guided bullets
     */
    public void shoot2(){
        if(!hasSpawned) {
            return;
        }
        
        float xMID = getX() + getWidth()/2;
        float yMID = getY() + getHeight()/2;
        
       
        
        float xF = (float)Math.cos(theta) * 30;
        float yF = (float)Math.sin(theta) * 30;
        bullets.add(new GuidedBullet(xMID + xF, yMID + yF, 8, 17, 10, theta, 8, Assets.images.get("BulletEnemyRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights, player));
        bullets.add(new GuidedBullet(xMID - xF, yMID - yF, 8, 17, 10, theta, 8, Assets.images.get("BulletEnemyRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights, player));
        
    }
    /**
     * turns towards the player
     */
    public void turn(){
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
    /**
     * gets angry if twin is killed, recuperates health and shoots in all directions
     */
    public void angry(){
        float xMID = getX() + getWidth()/2;
        float yMID = getY() + getHeight()/2;
        angry = true;
        maxHealth = 1000;
        healthBar = new StatusBar(10, 53, 6, 11, Assets.images.get("ArmorBar"), health, maxHealth, 0.40f);
        for (int i = 1; i <= 12; i++){
            bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta + Math.PI*i/12, 7, Assets.images.get("BulletEnemyBlue"), Bullet.BULLET_TYPE_ENEMY, Color.BLUE, lights));
        }
        for (int i = 0; i <= 12; i++){
            bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta - Math.PI*i/12, 7, Assets.images.get("BulletEnemyBlue"), Bullet.BULLET_TYPE_ENEMY, Color.BLUE, lights));
        }
        for (int i = 1; i <= 6; i++){
            bullets.add(new GuidedBullet(xMID, yMID, 10, 21, 10, theta + Math.PI*i/6, 5, Assets.images.get("BulletEnemyRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights, player));
        }
        for (int i = 0; i <= 6; i++){
            bullets.add(new GuidedBullet(xMID, yMID, 10, 21, 10, theta - Math.PI*i/6, 5, Assets.images.get("BulletEnemyRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights, player));
        }
    }
    /**
     * shoots in all direcions
     */
    private void shoot3(){
        float xMID = getX() + getWidth()/2;
        float yMID = getY() + getHeight()/2;
        
        for (int i = 1; i <= 12; i++){
            bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta + Math.PI*i/12, 7, Assets.images.get("BulletEnemyBlue"), Bullet.BULLET_TYPE_ENEMY, Color.BLUE, lights));
        }
        for (int i = 0; i <= 12; i++){
            bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta - Math.PI*i/12, 7, Assets.images.get("BulletEnemyBlue"), Bullet.BULLET_TYPE_ENEMY, Color.BLUE, lights));
        }
        for (int i = 1; i <= 6; i++){
            bullets.add(new GuidedBullet(xMID, yMID, 10, 21, 10, theta + Math.PI*i/6, 5, Assets.images.get("BulletEnemyRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights, player));
        }
        for (int i = 0; i <= 6; i++){
            bullets.add(new GuidedBullet(xMID, yMID, 10, 21, 10, theta - Math.PI*i/6, 5, Assets.images.get("BulletEnemyRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights, player));
        }
    }
    /**
     * updates the object
     */
    @Override
    public void update(){
        
        turn();
        // Movement function
        move();
        
        // shooting function
        if (!angry){
            if (shootTimer.isActivated()) {
                shootTimer.restart(health > 350 ? Util.randNumF(0.5f,2.5f) : (health > 100 ? Util.randNumF(0.5f,2) : Util.randNumF(0.5f, 1)));
                shoot2();
            }
            shootTimer.update();
        }else{
            if (shootTimer.isActivated()) {
                int num = Util.randNum(1, 5);
                if (num == 1) shootTimer.restart(2);
                else shootTimer.restart(0.3);
                shoot();
            }
            shootTimer.update();
            if (shootTimer2.isActivated()) {
                shootTimer2.restart(Util.randNumF(0.3f, 3));
                shoot2();
            }
            shootTimer2.update();
            if (shootTimer3.isActivated()){
                shootTimer3.restart(Util.randNumF(1, 7));
                shoot3();
            }
            shootTimer3.update();
        }
        
        if (angry && !healthfull){
            health += 2;
            if (health >= maxHealth){
                healthfull = true;
            }
        }
        
        // update healtbar
        healthBar.setValue(health);
        
        if(getHealth() <= 0) {
            Game.setCurrentScreen(LevelSelectScreen.getInstance());
        }
        
    }  
    /**
     * creates hitbox
     * @return rect
     */
    @Override
    public Rectangle2D.Float getRect() {
        return new Rectangle2D.Float(x + 32, y + 32, 64, 64);
    }
}
