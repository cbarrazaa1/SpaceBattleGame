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
import better.game.BossOne;
import better.game.EnemyOne;
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
    private ArrayList<EnemyOne> enemies;
    private ArrayList<Powerup> powerups;
    private Player player;
    
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
        objects = new HashMap<>();
        player = new Player(Game.getDisplay().getWidth() / 2, Game.getDisplay().getHeight() / 2, 64, 64);
        objects.put("player", player);
        
        StatusBar armorBar = new StatusBar(59, 571, 6, 11, Assets.images.get("ArmorBar"), 100, 100, 0.67f);
        StatusBar energyBar = new StatusBar(59, 581, 6, 11, Assets.images.get("EnergyBar"), 50, 50, 1f);       
        
        objects.put("armorBar", armorBar);
        objects.put("energyBar", energyBar);
           
        defeated = 0;
        spawnTimer = new Timer(1f);
    }

    @Override
    public void render(Graphics2D g) {
        // render background
        g.drawImage(Assets.images.get("LevelBackground"), 0, 0, Game.getDisplay().getWidth(), Game.getDisplay().getHeight(), null);
        
        // render current bullet (temp)
        g.drawImage(Assets.images.get("BulletGreen"), 28, 561, 16, 16, null);
        
        for(Map.Entry<String, GameObject> entry : objects.entrySet()) {
            entry.getValue().render(g);
        }
        
        for(EnemyOne e : enemies) {
            e.render(g);
        }
        
        for(Powerup powerup : powerups) {
            powerup.render(g);
        }
        
        Composite orig = g.getComposite();
        g.setColor(Color.BLACK);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g.fillRect(0, 0, Game.getDisplay().getWidth(), Game.getDisplay().getHeight());
        
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP));
        for(Light2D light : lights) {
            light.render(g);
        }
        g.setComposite(orig);

    }

    @Override
    public void update() {
        if(spawnTimer.isActivated()) {
            enemies.add(new EnemyOne(64, 64, player));
            if(defeated < TO_DEFEAT) {
                spawnTimer.restart(Util.randNumF(1.6f, 2.5f));
            } else {
                spawnTimer.restart(Util.randNumF(4f, 7f));
            }
        }
        spawnTimer.update();
        
        StatusBar healthBar = (StatusBar)objects.get("armorBar");
        healthBar.setValue(player.getHealth());
        StatusBar energyBar = (StatusBar)objects.get("energyBar");
        energyBar.setValue(player.getEnergy());
        
        if(player.getHealth() <= 0) {
            Game.setCurrentScreen(LevelSelectScreen.getInstance());
        }
        
        for(Map.Entry<String, GameObject> entry : objects.entrySet()) {
            entry.getValue().update();
        }
        
        // for erasing dead enemys from the objects map
        for(int i = 0; i < enemies.size(); i++) {
            EnemyOne enemy = enemies.get(i);
            enemy.update();
            if(!enemy.isAlive()) {
                for(EnemyShot shot : enemies.get(i).getShot()) { 
                    lightsToRemove.add(shot.getLight());
                }
                defeated++;
                if(defeated == TO_DEFEAT) {
                    BossOne bossOne = new BossOne(Game.getDisplay().getWidth() / 2 - 75, -300, 128, 128, player);
                    objects.put("bossOne", bossOne);
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