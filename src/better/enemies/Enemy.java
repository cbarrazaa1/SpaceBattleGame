/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.enemies;

import better.assets.Assets;
import better.bullets.Bullet;
import better.game.GameObject;
import better.game.Light2D;
import better.game.Player;
import better.game.StatusBar;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Cesar Barraza
 */
public abstract class Enemy extends GameObject {
    protected int maxHealth; 
    protected int health;
    protected double theta;
    protected int score;
    protected int coins;
    protected StatusBar healthBar;
    protected Player player;
    protected BufferedImage img;
    protected HashSet<Bullet> invulnerableTo;
    
    // Lights Screen //
    protected ArrayList<Light2D> lights;
    
    public Enemy(float x, float y, float width, float height, int score, int coins, int maxHealth, Player player, ArrayList<Light2D> lights) {
        super(x, y, width, height);
        this.score = score;
        this.coins = coins;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.player = player;
        this.healthBar = new StatusBar(x, y, 6, 11, Assets.images.get("ArmorBar"), health, health, width / health);
        this.lights = lights;
        invulnerableTo = new HashSet<>();
    }
    /**
     * returns the enemy health
     * @return health
     */
    public int getHealth() {
        return health;
    }
    /**
     * setter for health
     * @param health 
     */
    public void setHealth(int health) {
        // checks that health does is not less than 0 or more than maxHealth
        if(health <= 0) {
            health = 0;
        }
        
        if(health >= maxHealth) {
            health = maxHealth;
        }
        
        this.health = health;
    }
    /**
     * Returns the amount of coins
     * @return coins
     */
    public int getCoins() {
        return coins;
    }
    /**
     * returns the object's tilt
     * @return theta
     */
    public double getTheta() {
        return theta;
    }
    
    /**
     * chanches the object's tilt
     * @param theta 
     */
    public void setTheta(double theta) {
        this.theta = theta;
    }
    /**
     * checks the health
     * @return if health is > than 0
     */
    public boolean isAlive() {
        return health > 0;
    }
    
    /**
     * returns the score given to the player
     * @return score
     */
    public int getScore() {
        return score;
    }
    /**
     * updates the object
     */
    @Override
    public void update() {
        super.update();
    }
    /**
     * renders the object
     * @param g 
     */
    @Override
    public void render(Graphics2D g) {
        AffineTransform orig = g.getTransform();
        g.translate(getX(), getY());
        g.rotate(theta, getWidth() / 2, getHeight() / 2);
        g.drawImage(img, 0, 0, (int)getWidth(), (int)getHeight(), null);
        g.setTransform(orig);
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
    
    /**
     * changeds vulnerability to ceirtain bullets
     * @param bullet 
     */
    public void addInvulnerableTo(Bullet bullet) {
        invulnerableTo.add(bullet);
    }
    /**
     * Getter for invulnerableTo
     * @return invlnerableTo
     */
    public HashSet<Bullet> getInvulnerableTo() {
        return invulnerableTo;
    }
}
