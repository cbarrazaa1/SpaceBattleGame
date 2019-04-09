/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.scenes;

import better.assets.Assets;
import better.core.Game;
import better.core.GameObject;
import better.game.SelectablePlanet;
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
public class LevelSelectScreen extends Screen {
    private static LevelSelectScreen instance;
    public static LevelSelectScreen getInstance() {
        if(instance == null) {
            instance = new LevelSelectScreen();
        }
        
        return instance;
    }
    
    @Override
    public void init() {
        int w = Game.getDisplay().getWidth();
        int h = Game.getDisplay().getHeight();
        
        // Objects //
        SelectablePlanet sun = new SelectablePlanet(464, 30, 96, 96, Assets.sun, Assets.sunBlack);
        SelectablePlanet mercury = new SelectablePlanet(600, 70, 40, 40, Assets.mercury, Assets.mercuryBlack);
        SelectablePlanet venus = new SelectablePlanet(400, 170, 48, 48, Assets.venus, Assets.venusBlack);
        SelectablePlanet earth = new SelectablePlanet(682, 232, 56, 56, Assets.earth, Assets.earthBlack);
        SelectablePlanet mars = new SelectablePlanet(250, 272, 51, 51, Assets.mars, Assets.marsBlack);
        SelectablePlanet jupiter = new SelectablePlanet(380, 310, 85, 85, Assets.jupiter, Assets.jupiterBlack);
        SelectablePlanet saturn = new SelectablePlanet(570, 380, 76, 76, Assets.saturn, Assets.saturnBlack);
        SelectablePlanet uranus = new SelectablePlanet(300, 440, 70, 70, Assets.uranus, Assets.uranusBlack);
        SelectablePlanet neptune = new SelectablePlanet(530, 500, 80, 80, Assets.neptune, Assets.neptuneBlack);
        
        objects.put("sun", sun);
        objects.put("mercury", mercury);
        objects.put("venus", venus);
        objects.put("earth", earth);
        objects.put("mars", mars);
        objects.put("jupiter", jupiter);
        objects.put("saturn", saturn);
        objects.put("uranus", uranus);
        objects.put("neptune", neptune);
        
        // UI //
        UILabel lblTitle = new UILabel(0, 630, "", Color.WHITE, UILabel.DEFAULT_FONT);
        lblTitle.setFontSize(50);
        
        UIButton btnPlay = new UIButton((w - 200) - 30, (h - 45) - 30, 200, 45, Color.BLUE, "Play Level");
        uiControls.put("lblTitle", lblTitle);
        uiControls.put("btnPlay", btnPlay);
    }

    @Override
    public void render(Graphics2D g) {
        // render background
        g.drawImage(Assets.background2, 0, 0, Game.getDisplay().getWidth(), Game.getDisplay().getHeight(), null);
        
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
        
        for(Map.Entry<String, GameObject> entry : objects.entrySet()) {
            entry.getValue().render(g);
        }
    }

    @Override
    public void update() {
        for(Map.Entry<String, UIControl> entry : uiControls.entrySet()) {
            entry.getValue().update();
        }
        
        for(Map.Entry<String, GameObject> entry : objects.entrySet()) {
            entry.getValue().update();
        }
    }
    
}
