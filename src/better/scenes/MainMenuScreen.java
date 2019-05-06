/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.scenes;

import better.assets.Assets;
import better.core.Game;
import better.game.Player;
import better.game.StarBackground;
import better.ui.UIButton;
import better.ui.UIControl;
import better.ui.UILabel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import kuusisto.tinysound.Music;
import kuusisto.tinysound.TinySound;

/**
 *
 * @author Cesar Barraza
 */
public class MainMenuScreen extends Screen {
    private static MainMenuScreen instance;
    
    public static MainMenuScreen getInstance() {
        if(instance == null) {
            instance = new MainMenuScreen();
        }
        
        return instance;
    }
    
    private StarBackground sb;
    
    @Override
    public void init() {
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
        
        Assets.playMusic(Assets.mainMenu);
        sb = new StarBackground(0, 0.6f);
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.images.get("ExpBackground"), 0, 0, Game.getDisplay().getWidth(), Game.getDisplay().getHeight(), null);
        sb.render(g);
        g.drawImage(Assets.images.get("logo"), 0, 0, Game.getDisplay().getWidth(), Game.getDisplay().getHeight(), null);
        g.drawImage(Assets.images.get("backgroundBorder"), 0, 575, 800, 25, null);
        for(Map.Entry<String, UIControl> entry : uiControls.entrySet()) {
            String key = entry.getKey();
            UIControl val = entry.getValue();
            
            val.render(g);
        }
    }

    @Override
    public void update() {
        for(Map.Entry<String, UIControl> entry : uiControls.entrySet()) {
            entry.getValue().update();
        }
        
        sb.update();
    }

}