/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.scenes;

import better.assets.Assets;
import better.core.Game;
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
public class ChoosePalScreen extends Screen {
    private static ChoosePalScreen instance;
    public static ChoosePalScreen getInstance() {
        if(instance == null) {
            instance = new ChoosePalScreen();
        }
        
        return instance;
    }
    
    @Override
    public void init() {
        int w = Game.getDisplay().getWidth();
        int h = Game.getDisplay().getHeight();
        UILabel lblTitle = new UILabel(0, 30, "CHOOSE YOUR PAL", Color.WHITE, UILabel.DEFAULT_FONT);
        lblTitle.setFontSize(60);
        
        UIButton btnNext = new UIButton((w - 300) - 20, (h - 70) - 20, 300, 70, Color.BLACK, "Confirm");
        btnNext.setOnClickListener(() -> {
            Game.setCurrentScreen(LevelSelectScreen.getInstance());
        });
        UIButton btnBack = new UIButton(20, (h - 70) - 20, 300, 70, Color.BLACK, "Go Back");
        btnBack.setOnClickListener(() -> {
            Game.setCurrentScreen(MainMenuScreen.getInstance());
        });
        uiControls.put("lblTitle", lblTitle);
        uiControls.put("btnNext", btnNext);
        uiControls.put("btnBack", btnBack);
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
