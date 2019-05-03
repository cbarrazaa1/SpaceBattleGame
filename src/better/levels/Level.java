/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.levels;

import better.assets.Assets;
import better.core.Game;
import better.core.Util;
import better.enemies.Boss1;
import better.enemies.Enemy;
import better.enemies.Enemy1;
import better.game.Bullet;
import better.game.Light2D;
import better.game.Player;
import better.game.Powerup;
import better.game.StatusBar;
import better.scenes.LevelScreen;
import better.scenes.LevelSelectScreen;
import better.ui.UILabel;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author Cesar Barraza
 */
public abstract class Level implements LevelEventListener {
    public ArrayList<Light2D> lights;
    public ArrayList<Light2D> lightsToRemove;
    protected ArrayList<Enemy> enemies;
    protected ArrayList<Powerup> powerups;
    protected ArrayList<Bullet> bullets;
    protected Player player;
    protected int score;
    protected StatusBar armorBar;
    protected StatusBar energyBar;
    protected LevelEventListener eventListener;
    private UILabel lblScore;
    
    public Level() {
        lights = new ArrayList<>();
        lightsToRemove = new ArrayList<>();
        enemies = new ArrayList<>();
        powerups = new ArrayList<>();
        bullets = new ArrayList();
        score = 0;
        
        player = new Player(Game.getDisplay().getWidth() / 2, Game.getDisplay().getHeight() / 2, 64, 64, bullets, lights);
        armorBar = new StatusBar(59, 571, 6, 11, Assets.images.get("ArmorBar"), 100, 100, 0.67f);
        energyBar = new StatusBar(59, 581, 6, 11, Assets.images.get("EnergyBar"), 50, 50, 1f);   
        
        // UI
        lblScore = new UILabel(15, 544, "Score: 0", Color.WHITE, UILabel.DEFAULT_FONT);
    }
    
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
        lblScore.render(g);
    }
    
    public void update() {
        // update player
        player.update();
        armorBar.setValue(player.getHealth());
        energyBar.setValue(player.getEnergy());
        if(player.getHealth() <= 0) {
            LevelScreen.getInstance().setGameOver();
        }   
        
        // update bullets
        for(int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.update();
            
            if(bullet.getType() == Bullet.BULLET_TYPE_ENEMY) {
                if(bullet.intersects(player) && !player.isDashing()) {
                    player.setHealth(player.getHealth() - bullet.getDamage());
                    score -= 5;
                    if(score <= 0) {
                        score = 0;
                    }
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
                eventListener.onEnemyDead(enemy);
                Assets.enemyDie.play();
                score += enemy.getScore();
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
        
        // update score
        lblScore.setText("Score: " + score);
    }
}
