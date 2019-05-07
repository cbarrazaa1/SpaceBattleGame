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
 * Boss that rotates and shoots a lot of proyectiles
 */
public class Singularity extends Enemy {
    private Timer shootTimer;
    private boolean hasSpawned;
    private UILabel lblName; 
    private Color lightColor;
    private Light2D light;
    // Level Bullets //
    private ArrayList<Bullet> bullets;
    private ArrayList<Enemy> enemies;
    
    private Timer spawnTimer;
    
    public Singularity(float width, float height, int score, int coins, int maxHealth, Player player, ArrayList<Bullet> bullets, ArrayList<Light2D> lights, ArrayList<Enemy> enemies) {
        super(0, 0, width, height, score, coins, maxHealth, player, lights);
        this.bullets = bullets;
        this.enemies = enemies;
        theta = 0;
        shootTimer = new Timer(1);
        hasSpawned = false;
        img = Assets.images.get("TheSingularity");
        healthBar = new StatusBar(10, 23, 6, 11, Assets.images.get("ArmorBar"), maxHealth, maxHealth, 0.40f);
        lblName = new UILabel(10, 4, "The Black Hole", Color.WHITE, UILabel.DEFAULT_FONT);
        lightColor = new Color(219, 219, 23);
        spawn();
        light = new Light2D(0, 0, 0.35f, 150, 233, 122, 32);
        spawnTimer = new Timer(5);
    }
    /**
     * render the healthbar
     * @param g 
     */
    @Override
    public void render(Graphics2D g) {
        light.render(g);
        super.render(g);
        
        lblName.render(g);
        healthBar.render(g);
        
    }
    
    private void spawn(){
        x = Game.getDisplay().getWidth()/2 - width/2;
        y = Game.getDisplay().getHeight()/2 - height/2;
    }
    
    /**
     * first shooting pattern
     */
    public void shoot(){

        float xMID = getX() + getWidth()/2;
        float yMID = getY() + getHeight()/2;
        
        if (shootTimer.isActivated()) {
            shootTimer.restart(0.1);
            bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta, 6, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, lightColor, lights));
            bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta + Math.PI, 6, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, lightColor, lights));
            bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta + Math.PI/2, 6, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, lightColor, lights));
            bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta - Math.PI/2, 6, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, lightColor, lights));
        }
        shootTimer.update();
        
    }
    
    /**
     * recond shooting pattern
     */
    public void shoot2(){

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
    /**
     * third shooting pattern
     */
    public void shoot3(){
        
        float xMID = getX() + getWidth()/2;
        float yMID = getY() + getHeight()/2;
        
        if (shootTimer.isActivated()) {
            shootTimer.restart(Util.randNumF(0.25f,1.5f));
            for (int i = 1; i <= 8; i++){
                bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta + i*Math.PI/8, 5, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, lightColor, lights));
            }
            for (int i = 0; i <= 8; i++){
                bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta - i*Math.PI/8, 5, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, lightColor, lights));
            }
            for (int i = 1; i <= 7; i++){
                bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta + i*Math.PI/7, 4, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, lightColor, lights));
            }
            for (int i = 0; i <= 7; i++){
                bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta - i*Math.PI/7, 4, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, lightColor, lights));
            }
        }
        shootTimer.update();
    }
    
    
    public void shoot4(){
        
        float xMID = getX() + getWidth()/2;
        float yMID = getY() + getHeight()/2;
        if (shootTimer.isActivated()){
            bullets.add(new Bullet(xMID, yMID, 10, 21, 10, -theta, 6, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, lightColor, lights));
            bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta, 6, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, lightColor, lights));
            bullets.add(new Bullet(xMID, yMID, 10, 21, 10, -theta + Math.PI, 6, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, lightColor, lights));
            bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta - Math.PI, 6, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, lightColor, lights));
            
            shootTimer.restart(0.05);
        }
        shootTimer.update();
    }
    
    /**
     * updates the boss
     */
    double spin = 320;
    boolean spinEnd = false;
    @Override
    public void update(){
        light.setX(x + width/2);
        light.setY(y + height/2);
        spawnTimer.update();
        /*
        if (spawnTimer.isActivated()){
            spawnTimer.restart(15);
            Enemy1 e = new Enemy1(64, 64, 100, 80, 40, player, bullets, lights);
            e.setX(x+64);
            e.setY(y+64);
            enemies.add(e);
        }
        */
        // shooting function
        if (health > 1300){
            //rotate
            theta += Math.PI/Util.randNumF(4, 16);
            //first shooting pattern
            this.shoot();
            
        }else if (!spinEnd){
            // rotate
            theta += Math.PI/spin;
            // second shooting pattern
            this.shoot2();
            // Movement function
            spin -= 0.5;
            if (spin <= 16){
                spin = 8;
                spinEnd = true;
            }
        }else if (health > 900){
            // third shooting pattern
            this.shoot3();
            //shoot4();
            theta += Math.PI/128;
        }else if (health > 600){
            //rotate
            theta += Math.PI/Util.randNumF(4, 32);
            //first shooting pattern
            this.shoot();
            
        }else if (health > 400){
            // third shooting pattern
            this.shoot3();
            //shoot4();
            theta += Math.PI/128;   
        }else{
            shoot4();
            theta += Math.PI/96;
        }

        // update healtbar
        healthBar.setValue(health);
        
        if(getHealth() <= 0) {
            Game.setCurrentScreen(LevelSelectScreen.getInstance());
        }
        
    }  
    /**
     * creates the hitbox
     * @return rect
     */
    @Override
    public Rectangle2D.Float getRect() {
        return new Rectangle2D.Float(x+64, y+64, 64, 64);
    }
}
