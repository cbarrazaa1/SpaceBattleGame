/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.scenes;

import better.core.Game;
import better.ui.UIButton;
import better.ui.UIControl;
import better.ui.UILabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Map;

/**
 *
 * @author Cesar Barraza
 */
public class TestScreen3 extends Screen {
    private static TestScreen3 instance;
    public static TestScreen3 getInstance() {
        if(instance == null) {
            instance = new TestScreen3();
        }
        return instance;
    }
    
    public TestScreen3() {
        super();
    }
    
    @Override
    public void init() {
        int w = Game.getDisplay().getWidth();
        
        UILabel lblTest = new UILabel(50, 50, "This is TestScreen #3.", Color.WHITE, UILabel.DEFAULT_FONT);
        lblTest.setFontSize(40);
        lblTest.setFontStyle(Font.BOLD);
        
        UIButton btnBack = new UIButton((w / 2) - (250 / 2), 200, 250, 55, Color.RED, "Previous Screen");
        btnBack.setOnClickListener(() -> {
            Game.setCurrentScreen(TestScreen2.getInstance());
        });
        uiControls.put("lblTest", lblTest);
        uiControls.put("btnBack", btnBack);
    }

    @Override
    public void render(Graphics2D g) {
        for(Map.Entry<String, UIControl> entry : uiControls.entrySet()) {
            String key = entry.getKey();
            UIControl val = entry.getValue();
            int w = Game.getDisplay().getWidth();
            
            val.render(g);
            if(key.equals("lblTest")) {
                UILabel lblTest = (UILabel)val;
                lblTest.setX((w / 2) - (lblTest.getWidth() / 2));
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
    
