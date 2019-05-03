/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.scenes;

import better.assets.Assets;
import better.core.Game;
import better.core.Util;
import better.game.GameObject;
import better.game.Player;
import better.game.StatusBar;
import better.scenes.SelectablePlanet.PlanetState;
import better.ui.UIButton;
import better.ui.UIControl;
import better.ui.UILabel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;

class SelectablePlanet extends GameObject {
    enum PlanetState {
        NORMAL,
        LOCKED,
        SELECTED
    };
    
    private BufferedImage normalImg;
    private BufferedImage lockedImg;
    private BufferedImage selectedImg;
    private PlanetState state;
    
    public SelectablePlanet(float x, float y, float width, float height, BufferedImage img) {
        super(x, y, width, height);
        state = PlanetState.LOCKED;
        normalImg = img.getSubimage(0, 0, (int)width, (int)height);
        lockedImg = img.getSubimage((int)width, 0, (int)width, (int)height);
        selectedImg = img.getSubimage((int)(width * 2), 0, (int)width, (int)height);
    }

    public void setState(PlanetState state) {
        this.state = state;
    }
    
    @Override
    public void render(Graphics2D g) { 
        BufferedImage toRender = normalImg;
        switch(state) {
            case LOCKED:
                toRender = lockedImg;
                break;
            case SELECTED:
                toRender = selectedImg;
                break;
        }
        
        g.drawImage(toRender, (int)getX(), (int)getY(), (int)getWidth(), (int)getHeight(), null);
    }

    @Override
    public void onClick() { }

    @Override
    public void mouseEnter() { }

    @Override
    public void mouseLeave() { }

    @Override
    public void mouseDown() { }

    @Override
    public void mouseUp() {
        if(state == PlanetState.LOCKED) {
            return;
        }
    } 
}

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
    
    // Game objects
    private ArrayList<SelectablePlanet> selectablePlanets;
    private StatusBar armorBar;
    private StatusBar energyBar;
    private int selectedPlanet;
    private String funFactPlanet;
    private int funFactIndex;
    
    @Override
    public void init() {
        int w = Game.getDisplay().getWidth();
        
        // UI
        UIButton btnPlayLevel = new UIButton(48, 90, 205, 56, Assets.images.get("LevelSelectPlay"));
        btnPlayLevel.setOnClickListener(() -> {
            Game.setCurrentScreen(LevelScreen.getInstance());
        });
        
        UIButton btnHighscores = new UIButton(48, 154, 205, 56, Assets.images.get("LevelSelectHighscores"));
        UIButton btnCustomize = new UIButton(132, 294, 160, 46, Assets.images.get("LevelSelectCustomize"));
        UIButton btnShop = new UIButton(132, 349, 160, 46, Assets.images.get("LevelSelectShop"));
        UIButton btnSave = new UIButton(58, 478, 188, 51, Assets.images.get("LevelSelectSave"));
        UIButton btnMainMenu = new UIButton(58, 539, 188, 51, Assets.images.get("LevelSelectMainMenu"));
        
        UILabel lblSelectedPlanet = new UILabel(0, 45, "NEPTUNE", new Color(255f / 255f, 237f / 255f, 211f / 255f, 1f), UILabel.DEFAULT_FONT);
        lblSelectedPlanet.setFontSize(29);
        
        UILabel lblLevel = new UILabel(3, 410, "Lv. 1", Color.WHITE, UILabel.DEFAULT_FONT);
        lblLevel.setFontSize(11);
        UILabel lblCoins = new UILabel(75, 410, "0", Color.WHITE, UILabel.DEFAULT_FONT);
        lblCoins.setFontSize(11);
        UILabel lblBullet1 = new UILabel(0, 452, "Basic", Color.WHITE, UILabel.DEFAULT_FONT);
        lblBullet1.setFontSize(11);
        UILabel lblBullet2 = new UILabel(0, 452, "No Bullet", Color.WHITE, UILabel.DEFAULT_FONT);
        lblBullet2.setFontSize(11);
        UILabel lblArmor = new UILabel(0, 410, "Armor: 1", Color.WHITE, UILabel.DEFAULT_FONT);
        lblArmor.setFontSize(11);
        UILabel lblEnergy = new UILabel(0, 440, "Energy: 1", Color.WHITE, UILabel.DEFAULT_FONT);
        lblEnergy.setFontSize(11);
        
        uiControls.put("btnPlayLevel", btnPlayLevel);
        uiControls.put("btnHighscores", btnHighscores);
        uiControls.put("btnCustomize", btnCustomize);
        uiControls.put("btnShop", btnShop);
        uiControls.put("btnSave", btnSave);
        uiControls.put("btnMainMenu", btnMainMenu);
        uiControls.put("lblSelectedPlanet", lblSelectedPlanet);
        uiControls.put("lblLevel", lblLevel);
        uiControls.put("lblCoins", lblCoins);
        uiControls.put("lblBullet1", lblBullet1);
        uiControls.put("lblBullet2", lblBullet2);
        uiControls.put("lblArmor", lblArmor);
        uiControls.put("lblEnergy", lblEnergy);
        
        // Objects
        selectablePlanets = new ArrayList<>();
        SelectablePlanet sun = new SelectablePlanet(503, 18, 94, 89, Assets.images.get("sun"));
        SelectablePlanet mercury = new SelectablePlanet(650, 110, 31, 29, Assets.images.get("mercury"));
        SelectablePlanet venus = new SelectablePlanet(413, 126, 44, 42, Assets.images.get("venus"));
        SelectablePlanet earth = new SelectablePlanet(551, 193, 53, 48, Assets.images.get("earth"));
        SelectablePlanet mars = new SelectablePlanet(735, 229, 41, 39, Assets.images.get("mars"));
        SelectablePlanet jupiter = new SelectablePlanet(339, 269, 72, 71, Assets.images.get("jupiter"));
        SelectablePlanet saturn = new SelectablePlanet(506, 331, 66, 69, Assets.images.get("saturn"));
        SelectablePlanet uranus = new SelectablePlanet(713, 378, 47, 47, Assets.images.get("uranus"));
        SelectablePlanet neptune = new SelectablePlanet(397, 433, 52, 49, Assets.images.get("neptune"));
        neptune.setState(PlanetState.SELECTED);
        
        armorBar = new StatusBar(175, 421, 6, 11, Assets.images.get("ArmorBar"), 100, 100, 0.67f);
        energyBar = new StatusBar(175, 453, 6, 11, Assets.images.get("EnergyBar"), 50, 50, 1f);
        armorBar.setX(120 + (92 - armorBar.getWidth() / 2));
        energyBar.setX(120 + (92 - energyBar.getWidth() / 2));
        
        selectablePlanets.add(sun);
        selectablePlanets.add(mercury);
        selectablePlanets.add(venus);
        selectablePlanets.add(earth);
        selectablePlanets.add(mars);
        selectablePlanets.add(jupiter);
        selectablePlanets.add(saturn);
        selectablePlanets.add(uranus);
        selectablePlanets.add(neptune);
        selectedPlanet = 1;
        funFactIndex = Util.randNum(1, 3);
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.images.get("LevelSelectBackground"), 0, 0, Game.getDisplay().getWidth(), Game.getDisplay().getHeight(), null);
        
        // render pal
        BufferedImage palImg = Assets.images.get("LevelSelectYoth");
        g.drawImage(palImg, 740, 510, palImg.getWidth(), palImg.getHeight(), null);
        
        // render speech tip
        String s = getPlanetString();
        g.drawImage(Assets.images.get(s + "Fact" + funFactIndex), 336, 524, 346, 61, null);
        
        // render ship (temp)
        g.drawImage(Assets.images.get("PlayerShip"), 16, 308, 87, 91, null);
        
        // render coin icon
        g.drawImage(Assets.images.get("coin"), 64, 411, 7, 7, null);
        
        // render first bullet (temp)
        g.drawImage(Assets.images.get("BulletGreen"), 22, 431, 13, 19, null);
        
        for(Map.Entry<String, UIControl> entry : uiControls.entrySet()) {
            String key = entry.getKey();
            UIControl val = entry.getValue();
            int w = Game.getDisplay().getWidth();
            
            val.render(g);
            
            // center selected planet
            if(key.equals("lblSelectedPlanet")) {
                UILabel lbl = (UILabel)val;
                lbl.calculateDimensions(g);
                lbl.setX(153 - lbl.getWidth() / 2);
            }
            
            // center bullet1
            if(key.equals("lblBullet1")) {
                UILabel lbl = (UILabel)val;
                lbl.calculateDimensions(g);
                lbl.setX(30 - lbl.getWidth() / 2);
            }
            
            // center bullet2 
            if(key.equals("lblBullet2")) {
                UILabel lbl = (UILabel)val;
                lbl.calculateDimensions(g);
                lbl.setX(59 + (30 - lbl.getWidth() / 2));
            }
            
            // center bar labels
            if(key.equals("lblArmor") || key.equals("lblEnergy")) {
                UILabel lbl = (UILabel)val;
                lbl.calculateDimensions(g);
                lbl.setX(120 + (92 - lbl.getWidth() / 2));
            }   
        } 
        
        // render objects
        for(SelectablePlanet planet : selectablePlanets) {
            planet.render(g);
        }
        
        armorBar.render(g);
        energyBar.render(g);
    }

    @Override
    public void update() {
        for(Map.Entry<String, UIControl> entry : uiControls.entrySet()) {
            entry.getValue().update();
        }
        
        // update objects
        for(SelectablePlanet planet : selectablePlanets) {
            planet.update();
        }
        
        armorBar.update();
        energyBar.update();
    }
    
    private String getPlanetString() {
        String res = "";
        switch(selectedPlanet) {
            case 1:
                res = "Neptune";
                break;
            case 2:
                res = "Uranus";
                break;
            case 3:
                res = "Saturn";
                break;
            case 4:
                res = "Jupiter";
                break;
            case 5:
                res = "Mars";
                break;
            case 6:
                res = "Earth";
                break;
            case 7:
                res = "Venus";
                break;
            case 8:
                res = "Mercury";
                break;
            case 9:
                res = "Sun";
                break;
        }
        
        return res;
    }
}