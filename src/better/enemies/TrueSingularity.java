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
 * Final Boss of the game
 */
public class TrueSingularity extends Enemy {
    private Timer moveTimer;
    private Timer shootTimer;
    private Timer shootTimer2;
    private Timer shootTimer3;
    private Timer explodeTimer;
    private boolean hasSpawned;
    private UILabel lblName;
    float xSpeed;
    float ySpeed;
    boolean isDying;
    boolean isSpawning;
    
    // Level Bullets //
    private ArrayList<Bullet> bullets;
    
    public TrueSingularity(float width, float height, int score, int coins, int maxHealth, Player player, ArrayList<Bullet> bullets, ArrayList<Light2D> lights) {
        super(0, 0, width, height, score, coins, maxHealth, player, lights);
        this.bullets = bullets;
        theta = 0;
        moveTimer = new Timer(1);
        shootTimer = new Timer(1);
        shootTimer2 = new Timer(1);
        shootTimer3 = new Timer(0.3);
        explodeTimer = new Timer(15);
        hasSpawned = false;
        img = Assets.images.get("TheHedgeShip");
        healthBar = new StatusBar(10, 23, 6, 11, Assets.images.get("ArmorBar"), maxHealth, maxHealth, 0.40f);
        lblName = new UILabel(10, 4, "The Singularity", Color.WHITE, UILabel.DEFAULT_FONT);
        xSpeed = 0;
        ySpeed = 0;
        spawn();
        isDying = false;
        isSpawning = true;
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
     * spawns the boss in the center of the display 
     */
    private void spawn(){
        x = Game.getDisplay().getWidth()/2 - width/2;
        y = Game.getDisplay().getHeight()/2 - height/2;
        
        float xMID = getX() + getWidth()/2;
        float yMID = getY() + getHeight()/2;
        
        this.health = 20;
        
        for (int i = 1; i <= 12; i++){
            bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta + i*Math.PI/8, 4, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
        }
        for (int i = 0; i <= 12; i++){
            bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta - i*Math.PI/8, 4, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
        }
        for (int i = 1; i <= 9; i++){
            bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta + i*Math.PI/7, 3, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
        }
        for (int i = 0; i <= 9; i++){
            bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta - i*Math.PI/7, 3, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
        }
        hasSpawned = true;
        
    }
    
    /**
     * moves to a random position
     */
    int xPos = (int)Game.getDisplay().getWidth()/2 - (int)width/2; 
    int yPos = (int)Game.getDisplay().getHeight()/2 - (int)height/2; 
    double movTheta = 0;
    public void move(){
        int WIDTH =  Game.getDisplay().getWidth();
        int HEIGHT = Game.getDisplay().getHeight();
        
        
        if(moveTimer.isActivated()){
            do {
            xPos = (int)(Math.random() * 600) - 300;
            yPos = (int)(Math.random() * 600) - 300;
            xPos += getX();
            yPos += getY();
            } while(xPos < 0 || xPos > WIDTH - width || yPos < 0 || yPos > + HEIGHT - height);
            moveTimer.restart(Util.randNumF(0.5f, 2.5f));
            movTheta = Math.atan2(getY()-yPos, getX()-xPos);
        }

        if((getX() < xPos - 10 || getX() > xPos + 10) && (getY() < yPos - 10 || getY() > yPos + 10)){
            float speed = (12);
            setX(getX() + ((float)(Math.cos(movTheta+Math.PI))*speed));
            setY(getY() + ((float)(Math.sin(movTheta+Math.PI))*speed));
        }
        moveTimer.update();
        
    }
    /**
     * shoots randomly fast bullets
     */
    public void shoot(){
        if(!hasSpawned) {
            return;
        }
        
        float xMID = getX() + getWidth()/2;
        float yMID = getY() + getHeight()/2;
        
        if (shootTimer.isActivated()) {
            int num = Util.randNum(1, 8);
            if (num == 1) shootTimer.restart(1.5);
            else shootTimer.restart(0.2);
            float xF = (float)Math.cos(theta) * 15;
            float yF = (float)Math.sin(theta) * 15;
            bullets.add(new Bullet(xMID + xF, yMID + yF, 8, 17, 10, theta-Math.PI, 8, Assets.images.get("BulletEnemyBlue"), Bullet.BULLET_TYPE_ENEMY, Color.BLUE, lights));
            bullets.add(new Bullet(xMID - xF, yMID - yF, 8, 17, 10, theta-Math.PI, 8, Assets.images.get("BulletEnemyBlue"), Bullet.BULLET_TYPE_ENEMY, Color.BLUE, lights));
        }
        shootTimer.update();
        
    }
    /**
     * shoots normal and guided bullets
     * moves slowly randomly
     */
    private void attackP1(){
        float xMID = getX() + getWidth()/2;
        float yMID = getY() + getHeight()/2;
        
        if (shootTimer.isActivated()) {
            
            shootTimer.restart(0.3);
            float xF = (float)Math.cos(theta) * 15;
            float yF = (float)Math.sin(theta) * 15;
            double num = Math.PI/Util.randNum(4, 32);
            
            bullets.add(new Bullet(xMID + xF, yMID + yF, 10, 20, 10, theta-Math.PI - num, 8, Assets.images.get("BulletEnemyBlue"), Bullet.BULLET_TYPE_ENEMY, Color.BLUE, lights));
            bullets.add(new Bullet(xMID - xF, yMID - yF, 10, 20, 10, theta-Math.PI + num, 8, Assets.images.get("BulletEnemyBlue"), Bullet.BULLET_TYPE_ENEMY, Color.BLUE, lights));
            
        }
        shootTimer.update();
        
        if (shootTimer2.isActivated()) {
            
            shootTimer2.restart(0.8);
            float xF = (float)Math.cos(theta) * 5;
            float yF = (float)Math.sin(theta) * 5;
            bullets.add(new GuidedBullet(xMID + xF, yMID + yF, 10, 20, 10, theta-Math.PI - Math.PI/6, 6, Assets.images.get("BulletEnemyRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights, player));
            bullets.add(new GuidedBullet(xMID - xF, yMID - yF, 10, 20, 10, theta-Math.PI + Math.PI/6, 6, Assets.images.get("BulletEnemyRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights, player));
        }
        shootTimer2.update();
        
        if (moveTimer.isActivated()){
            moveTimer.restart(4);
            
            xSpeed = Util.randNumF(-4f, 4f);
            ySpeed = Util.randNumF(-4f, 4f);
            
        }
        moveTimer.update();
    }
    /**
     * shoots all types of bullets
     * and does not move
     */
    private void attackP2(){
        float xMID = getX() + getWidth()/2;
        float yMID = getY() + getHeight()/2;
        
        if (shootTimer.isActivated()) {
            
            shootTimer.restart(0.15);
            float xF = (float)Math.cos(theta) * 15;
            float yF = (float)Math.sin(theta) * 15;
            double num = Math.PI/16;
            double num2 = Math.PI/4;
            bullets.add(new Bullet(xMID + xF, yMID + yF, 10, 20, 10, theta-Math.PI + num, 8, Assets.images.get("BulletEnemyBlue"), Bullet.BULLET_TYPE_ENEMY, Color.BLUE, lights));
            bullets.add(new Bullet(xMID - xF, yMID - yF, 10, 20, 10, theta-Math.PI - num, 8, Assets.images.get("BulletEnemyBlue"), Bullet.BULLET_TYPE_ENEMY, Color.BLUE, lights));
            bullets.add(new GuidedBullet(xMID + xF, yMID + yF, 10, 20, 10, theta-Math.PI + num2, 8, Assets.images.get("BulletEnemyRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights, player));
            bullets.add(new GuidedBullet(xMID - xF, yMID - yF, 10, 20, 10, theta-Math.PI - num2, 8, Assets.images.get("BulletEnemyRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights, player));
            
        }
        shootTimer.update();
        
        if (shootTimer3.isActivated()) {
            
            shootTimer3.restart(Util.randNum(2, 4));
            
            bullets.add(new Bullet(xMID - 7.5f, yMID - 20, 15, 40, 30, theta-Math.PI, 20, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, Color.YELLOW, lights));
            
        }
        shootTimer3.update();
        
    }
    /**
     * shoots bullets in all directions
     */
    private void attackP3(){
        float xMID = getX() + getWidth()/2;
        float yMID = getY() + getHeight()/2;
        if (shootTimer.isActivated()){
            shootTimer.restart(0.2);
            for (int i = 0; i <= 8; i++){
                bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta + i*Math.PI/8, 4, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
            }
            for (int i = 0; i < 8; i++){
                bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta - i*Math.PI/8, 4, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
            }
        }
        shootTimer.update();
    }
    /**
     * shoots a heavy bullet randomly
     */
    private void attackP4(){
        float xMID = getX() + getWidth()/2;
        float yMID = getY() + getHeight()/2;
        
        if (shootTimer3.isActivated()) {
            
            shootTimer3.restart(Util.randNumF(0.2f, 0.5f));
            
            bullets.add(new Bullet(xMID - 7.5f, yMID - 20, 15, 40, 30, theta-Math.PI, 20, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, Color.YELLOW, lights));
            
        }
        shootTimer3.update();
    }
    
    /**
     * shoots in all directions
     * bullets have different speeds
     */
    private void explode(){
        float xMID = getX() + getWidth()/2;
        float yMID = getY() + getHeight()/2;
        
        for (int i = 1; i <= 12; i++){
            bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta + i*Math.PI/8, 4, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
        }
        for (int i = 0; i <= 12; i++){
            bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta - i*Math.PI/8, 4, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
        }
        for (int i = 1; i <= 9; i++){
            bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta + i*Math.PI/7, 3, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
        }
        for (int i = 0; i <= 9; i++){
            bullets.add(new Bullet(xMID, yMID, 10, 21, 10, theta - i*Math.PI/7, 3, Assets.images.get("Boss2Shot"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
        }
    }
    /**
     * turns towards the player
     */
    private void turn(){
        // delta X and Y are calculated
        double deltaX = (player.getX()+player.getWidth()/2) - ( x + getHeight() / 2);
        double deltaY = (player.getY()+player.getHeight()/2) - ( y + getWidth() / 2);
        
        // boss follows the player
        if (theta - Math.PI/2 < Math.atan2(deltaY, deltaX)-.001){
            theta += Math.PI/64;
        }if (theta - Math.PI/2 > Math.atan2(deltaY, deltaX)+.001){
            theta -= Math.PI/64;
        }
        
        // for when the diference in angles is more the 180 degrees
        if (theta - Math.PI/2 - Math.atan2(deltaY, deltaX) > Math.PI){
            theta -= 2*Math.PI;
        }
        if (theta - Math.PI/2 - Math.atan2(deltaY, deltaX) < -Math.PI){
            theta += 2*Math.PI;
        }
    }
    
    /**
     * checks if collided with outer bounds
     */
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
    /**
     * bounce when collided with outer bounds
     */
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
    /**
     * update the boss
     */
    float rotation = 0;
    @Override
    public void update(){
        // regen health when the boss is spawning
        if (isSpawning){
            health += 4;
            if (health >= maxHealth){
                isSpawning = false;
            }
            // update healtbar
            healthBar.setValue(health);
            turn();
            return;
        }
        // shoot at health 1500 to 1300
        if(health > 1300){
            shoot();
            turn();
            move();
        }else if (health > 1000){ // attack pattern 1 at health 1300 to 1000
            x += xSpeed;
            y += ySpeed;
            attackP1();
            turn();
        }else if(health > 800){ // shoot at helath 1000 to 800
            shoot();
            turn();
            move();
        }else if (health > 500){ // attack pattern 2 al heath 800 to 500
            attackP2();
            turn();
        }else if (health > 200){ // attack pattern 3 at health 500 to 200
            attackP3();
            theta += Math.PI/160;
            isDying = true;
            moveTimer.restart(0.1);
            
        }else{ // attack pattern 4 at health 200 to 0
            attackP4();
            if (moveTimer.isActivated()){
                moveTimer.restart(Util.randNumF(0.5f, 1.5f));
                xSpeed = Util.randNumF(-2, 2);
                ySpeed = Util.randNumF(-2, 2);
                rotation = Util.randNumF((float)-Math.PI/32, (float)Math.PI/32);
                explode();
            }
            moveTimer.update();
            x += xSpeed;
            y += ySpeed;
            theta += rotation;
        }
        this.bounceOnColision();
        // shoot in all directions randomly
        if (explodeTimer.isActivated() && !isDying){
            explode();
            explodeTimer.restart(Util.randNum(8, 15));
        }
        explodeTimer.update();
        
        
        // update healtbar
        healthBar.setValue(health);
        
        if(getHealth() <= 0) {
            Game.setCurrentScreen(LevelSelectScreen.getInstance());
        }
        
    }  
    /**
     * create the hitbox
     * @return rect
     */
    @Override
    public Rectangle2D.Float getRect() {
        return new Rectangle2D.Float(x + 12, y + 12, 88, 88);
    }
}
