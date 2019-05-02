
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.scenes;

import better.assets.Assets;
import better.core.Game;
import better.core.Timer;
import better.core.Util;
import better.enemies.Boss1;
import better.enemies.BossOne;
import better.enemies.Enemy;
import better.enemies.Enemy1;
import better.game.Bullet;
import better.game.EnemyShot;
import better.game.GameObject;
import better.game.Light2D;
import better.game.Player;
import better.game.Powerup;
import better.game.StatusBar;
import better.ui.UIControl;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author cesar
 */
public class LevelScreen extends Screen {
    private static LevelScreen instance;
    public static LevelScreen getInstance() {
        if(instance == null) {
            instance = new LevelScreen();
        }
        return instance;
    }
    
    public ArrayList<Light2D> lights;
    public ArrayList<Light2D> lightsToRemove;
    private ArrayList<Enemy> enemies;
    private ArrayList<Powerup> powerups;
    private ArrayList<Bullet> bullets;
    private Player player;
    private StatusBar armorBar;
    private StatusBar energyBar;
    
    // wave data
    private static final int TO_DEFEAT = 1;
    private int defeated;
    private Timer spawnTimer;
    
    @Override
    public void init() {
        lights = new ArrayList<>();
        lightsToRemove = new ArrayList<>();
        enemies = new ArrayList<>();
        powerups = new ArrayList<>();
        bullets = new ArrayList();
        player = new Player(Game.getDisplay().getWidth() / 2, Game.getDisplay().getHeight() / 2, 64, 64, bullets);
        
        armorBar = new StatusBar(59, 571, 6, 11, Assets.images.get("ArmorBar"), 100, 100, 0.67f);
        energyBar = new StatusBar(59, 581, 6, 11, Assets.images.get("EnergyBar"), 50, 50, 1f);       
           
        defeated = 0;
        spawnTimer = new Timer(1f);
    }

    @Override
    public void render(Graphics2D g) {
        // render background
        g.drawImage(Assets.images.get("LevelBackground"), 0, 0, Game.getDisplay().getWidth(), Game.getDisplay().getHeight(), null);
        
        // render current bullet (temp)
        g.drawImage(Assets.images.get("BulletGreen"), 28, 561, 16, 16, null);
         
        // render bullets
        for(Bullet bullet : bullets) {
            bullet.render(g);
        }
        
        // render enemies
        for(Enemy e : enemies) {
            e.render(g);
        }
        
        // render player
        player.render(g);
        
        // render powerups
        for(Powerup powerup : powerups) {
            powerup.render(g);
        }
        
        // render lights
        Composite orig = g.getComposite();
        g.setColor(Color.BLACK);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g.fillRect(0, 0, Game.getDisplay().getWidth(), Game.getDisplay().getHeight());      
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP));
        for(Light2D light : lights) {
            light.render(g);
        }
        g.setComposite(orig);

        // render bars
        armorBar.render(g);
        energyBar.render(g);
    }

    @Override
    public void update() {
        if(spawnTimer.isActivated()) {
            enemies.add(new Enemy1(64, 64, 40, player, bullets));
            if(defeated < TO_DEFEAT) {
                spawnTimer.restart(Util.randNumF(1.6f, 2.5f));
            } else {
                spawnTimer.restart(Util.randNumF(4f, 7f));
            }
        }
        spawnTimer.update();
        
        // update player
        player.update();
        armorBar.setValue(player.getHealth());
        energyBar.setValue(player.getEnergy());
        if(player.getHealth() <= 0) {
            Game.setCurrentScreen(LevelSelectScreen.getInstance());
        }   
        
        // update bullets
        for(int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.update();
            
            if(bullet.getType() == Bullet.BULLET_TYPE_ENEMY) {
                if(bullet.intersects(player) && !player.isDashing()) {
                    player.setHealth(player.getHealth() - bullet.getDamage());
                    lightsToRemove.add(bullet.getLight());
                    Assets.damage.play();
                    bullets.remove(i);
                }
            }
        }
         
        // update enemies
        for(int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            enemy.update();
            
            // check bullets collision
            for(int j = 0; j < bullets.size(); j++) {
                Bullet bullet = bullets.get(j);
                if(bullet.getType() == Bullet.BULLET_TYPE_PLAYER) {
                    if(bullet.intersects(enemy)) {
                        enemy.setHealth(enemy.getHealth() - bullet.getDamage());
                        lightsToRemove.add(bullet.getLight());
                        Assets.damage.play();
                        bullets.remove(j);
                    }
                }
            }
            
            if(!enemy.isAlive()) {
                defeated++;
                if(defeated == TO_DEFEAT) {
                    enemies.add(new Boss1(Game.getDisplay().getWidth() / 2 - 75, -300, 128, 128, 750, player, bullets));
                }
                
                // spawn powerup
                int p = Util.randNum(1, 11);
                if(p == 2) {
                    float x = enemy.getX();
                    float y = enemy.getY();
                    powerups.add(new Powerup(x, y, 48, 30, Powerup.TYPE_HEALTH, player));
                }
                Assets.enemyDie.play();
                enemies.remove(enemy);
            }
        }
        
        // check powerups
        for(int i = 0; i < powerups.size(); i++) {
            Powerup powerup = powerups.get(i);
            powerup.update();
            if(powerup.intersects(player)) {
                switch(powerup.getType()) {
                    case Powerup.TYPE_HEALTH:
                        player.setHealth(player.getHealth() + 15);
                        break;
                }
                Assets.powerup.play();
                powerups.remove(i);
            }
        }
        
        // check if lights out of bounds
        for(Light2D light : lights) {
            if(light.isOutOfBounds(0)) {
                lightsToRemove.add(light);
            }
        }
        
        // remove lights
        for(Light2D light : lightsToRemove) {
            lights.remove(light);
        }
        lightsToRemove.clear();
    } 
}