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
import better.game.Player;
import better.game.StatusBar;
import better.ui.UIControl;
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
    
    @Override
    public void init() {
        Player player = new Player(Game.getDisplay().getWidth() / 2, Game.getDisplay().getHeight() / 2, 75, 75);
        objects.put("player", player);
        /*for(int i = 0; i < 3; i++){
            objects.put("enemyOne#" + i, new EnemyOne(700, (Game.getDisplay().getHeight() * i) / 3 + 50, 50, 50, player));
        }*/
        
        StatusBar armorBar = new StatusBar(59, 571, 6, 11, Assets.images.get("ArmorBar"), 100, 100, 0.67f);
        StatusBar energyBar = new StatusBar(59, 581, 6, 11, Assets.images.get("EnergyBar"), 50, 50, 1f); 
        
        objects.put("armorBar", armorBar);
        objects.put("energyBar", energyBar);
    }

    @Override
    public void render(Graphics2D g) {
        // render background
        g.drawImage(Assets.images.get("LevelBackground"), 0, 0, Game.getDisplay().getWidth(), Game.getDisplay().getHeight(), null);
        
        // render current bullet (temp)
        g.drawImage(Assets.images.get("BulletGoodv1"), 28, 561, 16, 16, null);
        
        for(Map.Entry<String, GameObject> entry : objects.entrySet()) {
            entry.getValue().render(g);
        }
    }
    //// TEST
    int n = 0;
    int timer = 0;
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
        for(Map.Entry<String, GameObject> entry : objects.entrySet()) {
            /*GameObject go = entry.getValue();
            if (go instanceof EnemyOne){
                EnemyOne enemy = (EnemyOne)go;
                if (!enemy.isAlive()){
                    
                }
            }*/
            
            entry.getValue().update();
        }
        /// TEST for erasing enemys
        for (int i = 0; i < n; i++){
            if (objects.containsKey("enemyOne#" + i)){
                GameObject gO = objects.get("enemyOne#"+ i);
                EnemyOne enemy = (EnemyOne)gO;
                if (!enemy.isAlive()){
                    objects.remove("enemyOne#"+i);
                    //System.out.println("dead "+i);
                }
            }
        }
        ////
    }
    
}
