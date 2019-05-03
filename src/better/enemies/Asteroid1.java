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
import better.game.Bullet;
import better.game.Light2D;
import better.game.Player;
import better.scenes.LevelScreen;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Cesar Barraza
 */
public class Asteroid1 extends Enemy {
    private int xSpeed;
    private float ySpeed;
    private Timer deathTimer;
    
    // Level Bullet List //
    private ArrayList<Bullet> bullets;
    
    public Asteroid1(float x, float y, float width, float height, int score, int health, Player player, ArrayList<Bullet> bullets, ArrayList<Light2D> lights) {
        super(x, y, width, height, score, health, player, lights);
        this.bullets = bullets;
        theta = Util.randNumF(0.0f, (float)Math.PI*2);
        img = Assets.images.get("BulletRed");
        if (width == 128){
            spawn();
        }
        xSpeed = 3;
        ySpeed = Util.randNumF(-2f, 2f);
        deathTimer = new Timer(Util.randNum(1,5));
    }
    
    private void spawn(){
        int WIDTH = Game.getDisplay().getWidth();
        int HEIGHT = Game.getDisplay().getHeight();
        
        x = Util.randNum(WIDTH, WIDTH+100);
        y = Util.randNum((int)getHeight(), (int)(HEIGHT-getHeight()));
    }
    
    public void explode(){
        if (width < 64) return;
        for (int i = 0; i < 3; i++){
        bullets.add(new Bullet(getX() + getWidth() / 2, getY() + getHeight() / 2, 32, 32, 5,
                    theta*i/2, 10, Assets.images.get("BulletRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
        }
    }
    
    public void die(){
        if (width < 64) return;
        health = 0;
        for (int i = 0; i < 6; i++){
        bullets.add(new Bullet(getX() + getWidth() / 2, getY() + getHeight() / 2, 32, 32, 5,
                    theta*i/2, 10, Assets.images.get("BulletRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
        }
    }
    
    @Override
    public void update(){
        super.update();
        
        theta += Math.PI/500;
        setX(x-xSpeed);
        setY(y+ySpeed);
        deathTimer.update();
        if (deathTimer.isActivated()){
            this.die();
        }
    }
    
    @Override
    public void render(Graphics2D g) { 
        super.render(g);

    }
    
    @Override
    public Rectangle2D.Float getRect() {
        return new Rectangle2D.Float(x + 16, y + 16, 32, 32);
    }
    
    @Override
    public void mouseEnter() {
       
    }
    
    @Override
    public void mouseLeave() {
        
    }
}
