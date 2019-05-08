/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.scenes;

import better.assets.Assets;
import better.core.Game;
import better.game.MessageBox;
import better.game.Player;
import better.game.SQLManager;
import better.game.StarBackground;
import better.ui.UIButton;
import better.ui.UIControl;
import better.ui.UILabel;
import better.ui.UITextbox;
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
    private MessageBox msgBox;
    private UITextbox txtName;
    private UIButton btnConfirm;
    private boolean showLoad;
    
    /**
     * initializes the screen and its objects
     */
    @Override
    public void init() {
        UIButton btnNewGame = new UIButton(302, 160, 205, 56, Assets.images.get("NewGameButton"));
        btnNewGame.setOnClickListener(() -> {
            Game.setCurrentScreen(ChoosePalScreen.getInstance());
        });
        UIButton btnLoadGame = new UIButton(302, 232, 205, 56, Assets.images.get("LoadGameButton"));
        btnLoadGame.setOnClickListener(() -> {
            showLoad = true;
        });
        
        UIButton btnExit = new UIButton(302, 386, 205, 56, Assets.images.get("ExitButton"));
        btnExit.setOnClickListener(() -> {
            System.exit(0);
        });
        
        showLoad = false;
        msgBox = null;
        
        uiControls.put("btnNewGame", btnNewGame);
        uiControls.put("btnLoadGame", btnLoadGame);
        uiControls.put("btnExitGame", btnExit);
        
        txtName = new UITextbox(318, 250);
        btnConfirm = new UIButton(299, 300, 205, 56, Assets.images.get("PalScreenConfirm"));
        btnConfirm.setOnClickListener(() -> {
            int id = SQLManager.playerExists(txtName.getText());
            if(id != -1) {
                Player player = SQLManager.selectPlayer(id);
                player.setId(id);
                Game.setCurrentScreen(LevelSelectScreen.getInstance());
                LevelSelectScreen.getInstance().setPlayer(player);
                LevelSelectScreen.getInstance().init(); 
            } else {
                showLoad = false;
                msgBox = new MessageBox("That player does not exist.", MessageBox.MSG_TYPE_OK, null, null);
            }
        });
        
        Assets.playMusic(Assets.mainMenu);
        sb = new StarBackground(0, 0.6f);
    }
    /**
     * renders the screen and its objects
     * @param g 
     */
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
        
        if(showLoad) {
            g.setColor(new Color(0, 0, 0, 0.6f));
            g.fillRect(0, 0, Game.getDisplay().getWidth(), Game.getDisplay().getHeight());
            g.drawImage(Assets.images.get("LoadMessage"), 400 - 460/2, 300 - 187/2, 460, 187, null);
            txtName.render(g);
            btnConfirm.render(g);
        }
        
        if(msgBox != null && msgBox.isVisible()) {
            msgBox.render(g);
        }
    }
    /**
     * updates the screen
     */
    @Override
    public void update() {
        if(!showLoad) {
            for(Map.Entry<String, UIControl> entry : uiControls.entrySet()) {
                entry.getValue().update();
            }
        }
        
        sb.update();
        
        if(showLoad) {
            txtName.update();
            btnConfirm.update();
        }
        
        if(msgBox != null && msgBox.isVisible()) {
            msgBox.update();
        }
    }

}