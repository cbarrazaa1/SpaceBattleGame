/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.scenes;

import better.assets.Assets;
import better.core.Game;
import better.game.GameObject;
import better.game.Player;
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
    
    private String selectedPlanet = "Neptune";
    
    @Override
    public void init() {
        int w = Game.getDisplay().getWidth();
        int h = Game.getDisplay().getHeight();
        
        // Objects //
        SelectablePlanet sun = new SelectablePlanet(464, 30, 96, 96, Assets.sun, Assets.sunBlack);
        sun.setOnClickListener(() -> {
            if(sun.isLocked()) {
                return;
            }
            selectedPlanet = "Sun";
            updateSelectedPlanet();
        });
        SelectablePlanet mercury = new SelectablePlanet(600, 70, 40, 40, Assets.mercury, Assets.mercuryBlack);
        mercury.setOnClickListener(() -> {
            if(mercury.isLocked()) {
                return;
            }
            selectedPlanet = "Mercury";
            updateSelectedPlanet();
        });
        SelectablePlanet venus = new SelectablePlanet(400, 170, 48, 48, Assets.venus, Assets.venusBlack);
        venus.setOnClickListener(() -> {
            if(venus.isLocked()) {
                return;
            }
            selectedPlanet = "Venus";
            updateSelectedPlanet();
        });
        SelectablePlanet earth = new SelectablePlanet(682, 232, 56, 56, Assets.earth, Assets.earthBlack);
        earth.setOnClickListener(() -> {
            if(earth.isLocked()) {
                return;
            }
            selectedPlanet = "Earth";
            updateSelectedPlanet();
        });
        SelectablePlanet mars = new SelectablePlanet(250, 272, 51, 51, Assets.mars, Assets.marsBlack);
        mars.setOnClickListener(() -> {
            if(mars.isLocked()) {
                return;
            }
            selectedPlanet = "Mars";
            updateSelectedPlanet();
        });
        SelectablePlanet jupiter = new SelectablePlanet(380, 310, 85, 85, Assets.jupiter, Assets.jupiterBlack);
        jupiter.setOnClickListener(() -> {
            if(jupiter.isLocked()) {
                return;
            }
            selectedPlanet = "Jupiter";
            updateSelectedPlanet();
        });
        SelectablePlanet saturn = new SelectablePlanet(570, 380, 76, 76, Assets.saturn, Assets.saturnBlack);
        saturn.setOnClickListener(() -> {
            if(saturn.isLocked()) {
                return;
            }
            selectedPlanet = "Saturn";
            updateSelectedPlanet();
        });
        SelectablePlanet uranus = new SelectablePlanet(300, 440, 70, 70, Assets.uranus, Assets.uranusBlack);
        uranus.setOnClickListener(() -> {
            if(uranus.isLocked()) {
                return;
            }
            selectedPlanet = "Uranus";
            updateSelectedPlanet();
        });
        SelectablePlanet neptune = new SelectablePlanet(530, 500, 80, 80, Assets.neptune, Assets.neptuneBlack);
        neptune.setOnClickListener(() -> {
            if(neptune.isLocked()) {
                return;
            }
            selectedPlanet = "Neptune";
            updateSelectedPlanet();
        });
        neptune.setLocked(false);
        
        objects.put("sun", sun);
        objects.put("mercury", mercury);
        objects.put("venus", venus);
        objects.put("earth", earth);
        objects.put("mars", mars);
        objects.put("jupiter", jupiter);
        objects.put("saturn", saturn);
        objects.put("uranus", uranus);
        objects.put("neptune", neptune);
        objects.put("player", new Player(30, 30, 100, 100));
        
        // UI //
        UILabel lblTitle = new UILabel(0, 630, selectedPlanet, Color.WHITE, UILabel.DEFAULT_FONT);
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
    
    private void updateSelectedPlanet() {
        ((UILabel)uiControls.get("lblTitle")).setText(selectedPlanet);
    }
    
}
