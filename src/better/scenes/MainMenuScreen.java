/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.scenes;

import better.assets.Assets;
import better.core.Game;
import better.game.EnemyOne;
import better.game.Player;
import better.ui.UIButton;
import better.ui.UIControl;
import better.ui.UILabel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Cesar Barraza
 */
public class MainMenuScreen extends Screen {
    private static MainMenuScreen instance;
    
    /// TEST /////
    private Player player;
    private ArrayList<EnemyOne> enemies;
    
    public static MainMenuScreen getInstance() {
        if(instance == null) {
            instance = new MainMenuScreen();
        }
        
        return instance;
    }
    
    @Override
    public void init() {
        //TEST ///// creating a player
        player = new Player(Game.getDisplay().getWidth()/2, Game.getDisplay().getHeight()/2, 100, 100);
        enemies = new ArrayList<EnemyOne>();
        
        for (int i = 0; i < 3; i++){
            enemies.add(new EnemyOne(700, (Game.getDisplay().getHeight()*i)/3+50, 50, 50));
        }
        ////////////
        UIButton btnNewGame = new UIButton(302, 160, 205, 56, Assets.images.get("NewGameButton"));
        btnNewGame.setOnClickListener(() -> {
            Game.setCurrentScreen(ChoosePalScreen.getInstance());
        });
        UIButton btnLoadGame = new UIButton(302, 232, 205, 56, Assets.images.get("LoadGameButton"));
        UIButton btnOptions = new UIButton(302, 304, 205, 56, Assets.images.get("OptionsButton"));
        UIButton btnExit = new UIButton(302, 386, 205, 56, Assets.images.get("ExitButton"));
        btnExit.setOnClickListener(() -> {
            System.exit(0);
        });

        uiControls.put("btnNewGame", btnNewGame);
        uiControls.put("btnLoadGame", btnLoadGame);
        uiControls.put("btnOptions", btnOptions);
        uiControls.put("btnExitGame", btnExit);
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.images.get("MenuBackground"), 0, 0, Game.getDisplay().getWidth(), Game.getDisplay().getHeight(), null);
        
        for(Map.Entry<String, UIControl> entry : uiControls.entrySet()) {
            String key = entry.getKey();
            UIControl val = entry.getValue();
            
            val.render(g);
        }
        //TEST// rendering player
        player.render(g);
        for (int i = 0; i < 3; i++){
            enemies.get(i).render(g);
        }
    }

    @Override
    public void update() {
        for(Map.Entry<String, UIControl> entry : uiControls.entrySet()) {
            entry.getValue().update();
        }
        //TEST// update player
        player.update();
        for(int i = 0; i < 3; i++){
            enemies.get(i).update();
        }
    }
}
