/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.scenes;

import better.assets.Assets;
import better.core.Game;
import better.game.EnemyOne;
import better.game.GameObject;
import better.game.Light2D;
import better.game.Player;
import better.game.StatusBar;
import better.ui.UIControl;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.util.ArrayList;
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
    
    private Light2D light;
    private Light2D light2;
    private Player player;
    
    @Override
    public void init() {
        player = new Player(Game.getDisplay().getWidth() / 2, Game.getDisplay().getHeight() / 2, 75, 75);
        objects.put("player", player);
        /*for(int i = 0; i < 3; i++){
            objects.put("enemyOne#" + i, new EnemyOne(700, (Game.getDisplay().getHeight() * i) / 3 + 50, 50, 50, player));
        }*/
        
        StatusBar armorBar = new StatusBar(59, 571, 6, 11, Assets.images.get("ArmorBar"), 100, 100, 0.67f);
        StatusBar energyBar = new StatusBar(59, 581, 6, 11, Assets.images.get("EnergyBar"), 50, 50, 1f); 
        
        objects.put("armorBar", armorBar);
        objects.put("energyBar", energyBar);
        
        light = new Light2D(100, 100, 0.1f, 100, 255, 255, 255);
        light2 = new Light2D(130, 100, 0.15f, 50, 244, 229, 66);
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
        
        Composite orig = g.getComposite();
        g.setColor(Color.BLACK);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g.fillRect(0, 0, Game.getDisplay().getWidth(), Game.getDisplay().getHeight());
        
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP));
        light.render(g);
        light2.render(g);
        g.setComposite(orig);

    }
    //// TEST
    int n = 0;
    int timer = 0;
    ////
    @Override
    public void update() {
        //// TEST
        if (timer >= 100){
            objects.put("enemyOne#" + n, new EnemyOne(50, 50, (Player)objects.get("player")));
            n++;
            timer = 0;
        }
        timer++;
        ////
        
        StatusBar energyBar = (StatusBar)objects.get("energyBar");
        Player player = (Player)objects.get("player");
        energyBar.setValue(player.getEnergy());
        
        for(Map.Entry<String, GameObject> entry : objects.entrySet()) {
            /*GameObject go = entry.getValue();
            if (go instanceof EnemyOne){
                EnemyOne enemy = (EnemyOne)go;
                if (!enemy.isAlive()){
                    
                }
            }*/
            
            
            entry.getValue().update();
        }
        // for erasing dead enemys from the objects map
        for (int i = 0; i < n; i++){
            if (objects.containsKey("enemyOne#" + i)){
                GameObject gO = objects.get("enemyOne#"+ i);
                EnemyOne enemy = (EnemyOne)gO;
                if (!enemy.isAlive()){
                    objects.remove("enemyOne#"+i);
                }
            }
        }
        
        light.setX(player.getX() + player.getWidth() / 2);
        light.setY(player.getY() + player.getHeight() / 2);
    }
    
}
