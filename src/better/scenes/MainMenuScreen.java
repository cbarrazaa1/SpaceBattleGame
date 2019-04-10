/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.scenes;

import better.assets.Assets;
import better.core.Game;
import better.game.Player;
import better.ui.UIButton;
import better.ui.UIControl;
import better.ui.UILabel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Map;

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
    
    @Override
    public void init() {
        int w = Game.getDisplay().getWidth();
        UILabel lblTitle = new UILabel(0, 50, "SPACEMANIA", Color.WHITE, UILabel.DEFAULT_FONT);
        lblTitle.setFontSize(70);
        
        UIButton btnNewGame = new UIButton((w / 2) - (350 / 2), 160, 350, 70, Color.BLUE, "New Game");
        btnNewGame.setOnClickListener(() -> {
            Game.setCurrentScreen(ChoosePalScreen.getInstance());
        });
        UIButton btnLoadGame = new UIButton((w / 2) - (350 / 2), 260, 350, 70, Color.BLUE, "Load Game");
        UIButton btnOptions = new UIButton((w / 2) - (350 / 2), 360, 350, 70, Color.WHITE, "Options");
        UIButton btnExitGame = new UIButton((w / 2) - (350 / 2), 460, 350, 70, Color.RED, "Exit");
        btnExitGame.setOnClickListener(() -> {
            System.exit(0);
        });
        
        uiControls.put("lblTitle", lblTitle);
        uiControls.put("btnNewGame", btnNewGame);
        uiControls.put("btnLoadGame", btnLoadGame);
        uiControls.put("btnOptions", btnOptions);
        uiControls.put("btnExitGame", btnExitGame);
        
    }

    @Override
    public void render(Graphics2D g) {
        // render background
        g.drawImage(Assets.mainMenuBackground, 0, 0, Game.getDisplay().getWidth(), Game.getDisplay().getHeight(), null);

        for(Map.Entry<String, UIControl> entry : uiControls.entrySet()) {
            String key = entry.getKey();
            UIControl val = entry.getValue();
            int w = Game.getDisplay().getWidth();
            
            val.render(g);
            
            if(key.equals("lblTitle")) {
                UILabel lbl = (UILabel)val;
                lbl.setX((w / 2) - (lbl.getWidth() / 2));
            }
        }
        
    }

    @Override
    public void update() {
        for(Map.Entry<String, UIControl> entry : uiControls.entrySet()) {
            entry.getValue().update();
        }
    }
}
