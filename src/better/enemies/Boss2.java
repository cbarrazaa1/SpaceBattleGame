/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.enemies;

import better.assets.Assets;
import better.core.Game;
import better.core.Timer;
import better.core.Util;
import better.bullets.Bullet;
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
 */
public class Boss2 extends Enemy {
    private Timer moveTimer;
    private Timer shootTimer;
    private boolean hasSpawned;
    private UILabel lblName; 
    private Color lightColor;
    
    // Level Bullets //
    private ArrayList<Bullet> bullets;
    
    public Boss2(float x, float y, float width, float height, int score, int coins, int maxHealth, Player player, ArrayList<Bullet> bullets, ArrayList<Light2D> lights) {
        super(x, y, width, height, score, coins, maxHealth, player, lights);
        this.bullets = bullets;
        theta = 0;
        moveTimer = new Timer(0);
        shootTimer = new Timer(1);
        hasSpawned = false;
        img = Assets.images.get("Rotor");
        healthBar = new StatusBar(10, 23, 6, 11, Assets.images.get("ArmorBar"), maxHealth, maxHealth, 0.40f);
        lblName = new UILabel(10, 4, "Pinweel", Color.WHITE, UILabel.DEFAULT_FONT);
        lightColor = new Color(219, 219, 23);
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
        if (!hasSpawned) return;
        
        int WIDTH =  Game.getDisplay().getWidth();
        int HEIGHT = Game.getDisplay().getHeight();
        
        if(moveTimer.isActivated()){
            do {
            xPos = (int)(Math.random() * 800) - 400;
            yPos = (int)(Math.random() * 800) - 400;
            xPos += getX();
            yPos += getY();
            } while(xPos < 0 || xPos > WIDTH - width || yPos < 0 || yPos > + HEIGHT - height);
            moveTimer.restart(4);
            movTheta = Math.atan2(getY()-yPos, getX()-xPos);
        }
        if((getX() < xPos - 10 || getX() > xPos + 10) && (getY() < yPos - 10 || getY() > yPos + 10)){
            float speed = (health > 500 ? 3 :5.5f);
            setX(getX() + ((float)(Math.cos(movTheta+Math.PI))*speed));
            setY(getY() + ((float)(Math.sin(movTheta+Math.PI))*speed));
        }
        moveTimer.update();
        
                    
    }
    
    public void moveToCenter(){
        int WIDTH =  Game.getDisplay().getWidth();
        int HEIGHT = Game.getDisplay().getHeight();
        if (x < WIDTH/2 - width/2){
            x++;
        }
        else if (x > WIDTH/2 - width/2){
            x--;
        }
        if (y < HEIGHT/2 - height/2){
            y++;
        }
        else if (y > HEIGHT/2 - height/2){
            y--;
        }
    }
    
    public void shoot(){
        if(!hasSpawned) {
            return;
        }
        
        float xMID = getX() + getWidth()/2;
        float yMID = getY() + getHeight()/2;
        
        if (shootTimer.isActivated()) {
            shootTimer.restart(0.3);
            bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta, 6, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, lightColor, lights));
            bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta + Math.PI, 6, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, lightColor, lights));
            bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta + Math.PI/2, 6, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, lightColor, lights));
            bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta - Math.PI/2, 6, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, lightColor, lights));
        }
        shootTimer.update();
        
    }
    
    public void shoot2(){
        if(!hasSpawned) {
            return;
        }
        
        float xMID = getX() + getWidth()/2;
        float yMID = getY() + getHeight()/2;
        
        if (shootTimer.isActivated()) {
            shootTimer.restart(0.1);
            bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta, 8, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, lightColor, lights));
            bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta + Math.PI, 8, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, lightColor, lights));
            bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta + Math.PI/2, 8, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, lightColor, lights));
            bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta - Math.PI/2, 8, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, lightColor, lights));
        }
        shootTimer.update();
        
    }
    
    public void shoot3(){
        if(!hasSpawned) {
            return;
        }
        
        float xMID = getX() + getWidth()/2;
        float yMID = getY() + getHeight()/2;
        
        if (shootTimer.isActivated()) {
            shootTimer.restart(Util.randNumF(0.25f,1.5f));
            for (int i = 1; i <= 12; i++){
                bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta + Math.PI*i/12, 5, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, lightColor, lights));
            }
            for (int i = 0; i <= 12; i++){
                bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta - Math.PI*i/12, 5, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, lightColor, lights));
            }
        }
        shootTimer.update();
        
    }
    
    @Override
    public void update(){
        
        
        if (!hasSpawned){
            setY(getY() + 3);
            if(getY() >= 0) {
                hasSpawned = true;
            }
        }
        // shooting function
        else if (health > 600){
            //rotate
            theta += Math.PI/64;
            //first shooting pattern
            this.shoot();
            // Movement function
            this.move();
        }else if (health > 300){
            // rotate
            theta += Math.PI/320;
            // second shooting pattern
            this.shoot2();
            // Movement function
            this.move();
        }else{
            //rotate
            theta += Math.PI/32;
            this.moveToCenter();
            // third shooting pattern
            this.shoot3();
        }

        // update healtbar
        healthBar.setValue(health);
        
        if(getHealth() <= 0) {
            Game.setCurrentScreen(LevelSelectScreen.getInstance());
        }
        
    }  
  
    @Override
    public Rectangle2D.Float getRect() {
        return new Rectangle2D.Float(x+64, y+64, 64, 64);
    }
}
