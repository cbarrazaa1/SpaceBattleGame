/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.scenes;

import better.assets.Assets;
import better.bullets.Bullet;
import better.core.Game;
import better.core.Util;
import better.game.GameObject;
import better.game.HighscoreStruct;
import better.game.Player;
import better.game.SQLManager;
import better.game.StarBackground;
import better.game.StatusBar;
import better.scenes.SelectablePlanet.PlanetState;
import better.ui.UIButton;
import better.ui.UIControl;
import better.ui.UILabel;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
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
    
    private String name;
    private BufferedImage normalImg;
    private BufferedImage lockedImg;
    private BufferedImage selectedImg;
    private PlanetState state;
    
    public SelectablePlanet(float x, float y, float width, float height, BufferedImage img, String name) {
        super(x, y, width, height);
        state = PlanetState.LOCKED;
        normalImg = img.getSubimage(0, 0, (int)width, (int)height);
        lockedImg = img.getSubimage((int)width, 0, (int)width, (int)height);
        selectedImg = img.getSubimage((int)(width * 2), 0, (int)width, (int)height);
        this.name = name;
    }
    /**
     * returns the planet name
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * new planet name
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * new state
     * @param state 
     */
    public void setState(PlanetState state) {
        this.state = state;
    }
    /**
     * returns planet state
     * @return state
     */
    public PlanetState getState() {
        return state;
    }
    /**
     * renders the planet depending on its state
     * @param g 
     */
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
    private Player player;
    private int selectedPlanet;
    private String funFactPlanet;
    private int funFactIndex;
    public final String[] planets = { "Sun", "Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"};
    private boolean showStats;
    private ArrayList<UIControl> statsControls;
    private StarBackground sb;
    private boolean showHighscores;
    private ArrayList<UIControl> hsControls;
    
    /**
     * initializes screen and its assets
     */
    @Override
    public void init() {
        sb = new StarBackground(0, 0.5f);
        int w = Game.getDisplay().getWidth();
        
        // UI
        UIButton btnPlayLevel = new UIButton(48, 90, 205, 56, Assets.images.get("LevelSelectPlay"));
        btnPlayLevel.setOnClickListener(() -> {
            Game.setCurrentScreen(LevelScreen.getInstance());
            player.setCurrLevel(selectedPlanet);
            LevelScreen.getInstance().setPlayer(player);
            LevelScreen.getInstance().setSelectedLevel(selectedPlanet);
            LevelScreen.getInstance().init();
        });
        
        
         // Highscore Window
        UILabel lblHSPlanet = new UILabel(382, 110, "LEADERBOARDS", Color.WHITE, UILabel.DEFAULT_FONT);
        lblHSPlanet.setFontSize(10);
        UILabel lblPB = new UILabel(470, 156, String.valueOf(Util.randNum(50, 100)), Color.WHITE, UILabel.DEFAULT_FONT);
        UILabel lblTimesPlayed = new UILabel(468, 204, "1", Color.WHITE, UILabel.DEFAULT_FONT);
        UILabel lblName1 = new UILabel(262, 262, "", Color.WHITE, UILabel.DEFAULT_FONT);
        UILabel lblScore1 = new UILabel(422, 262, "", Color.WHITE, UILabel.DEFAULT_FONT);
        UILabel lblName2 = new UILabel(262, 291, "", Color.WHITE, UILabel.DEFAULT_FONT);
        UILabel lblScore2 = new UILabel(422, 291, "", Color.WHITE, UILabel.DEFAULT_FONT);
        UILabel lblName3 = new UILabel(262, 318, "", Color.WHITE, UILabel.DEFAULT_FONT);
        UILabel lblScore3 = new UILabel(422, 318, "", Color.WHITE, UILabel.DEFAULT_FONT);
        UILabel lblName4 = new UILabel(262, 345, "", Color.WHITE, UILabel.DEFAULT_FONT);
        UILabel lblScore4 = new UILabel(422, 345, "", Color.WHITE, UILabel.DEFAULT_FONT);
        UILabel lblName5 = new UILabel(262, 372, "", Color.WHITE, UILabel.DEFAULT_FONT);
        UILabel lblScore5 = new UILabel(422, 372, "", Color.WHITE, UILabel.DEFAULT_FONT);
                
        
        UIButton btnCloseHS = new UIButton(326, 402, 188, 51, Assets.images.get("HighscoreClose"));
        btnCloseHS.setOnClickListener(() -> {
            showHighscores = false;
        });
        
        hsControls = new ArrayList<>();
        hsControls.add(lblHSPlanet);
        hsControls.add(lblPB);
        hsControls.add(lblTimesPlayed);
        hsControls.add(lblName1);
        hsControls.add(lblScore1);
        hsControls.add(lblName2);
        hsControls.add(lblScore2);
        hsControls.add(lblName3);
        hsControls.add(lblScore3);
        hsControls.add(lblName4);
        hsControls.add(lblScore4);
        hsControls.add(lblName5);
        hsControls.add(lblScore5);
        hsControls.add(btnCloseHS);
        
        UIButton btnHighscores = new UIButton(48, 154, 205, 56, Assets.images.get("LevelSelectHighscores"));
        btnHighscores.setOnClickListener(() -> {
            lblName1.setText("");
            lblScore1.setText("");
            lblName2.setText("");
            lblScore2.setText("");
            lblName3.setText("");
            lblScore3.setText("");
            lblName4.setText("");
            lblScore4.setText("");
            lblName5.setText("");
            lblScore5.setText("");
            showHighscores = true;
            ArrayList<HighscoreStruct> hs = SQLManager.getLevelHighscores(selectedPlanet);
            
            for(int i = 0; i < hs.size(); i++) {
                HighscoreStruct h = hs.get(i);
                if(i == 0) {
                    lblName1.setText(h.name);
                    lblScore1.setText(String.valueOf(h.hs));
                } else if(i == 1) {
                    lblName2.setText(h.name);
                    lblScore2.setText(String.valueOf(h.hs));
                } else if(i == 2) {
                     lblName3.setText(h.name);
                    lblScore3.setText(String.valueOf(h.hs));
                } else if(i == 3) {
                    lblName4.setText(h.name);
                    lblScore4.setText(String.valueOf(h.hs));
                } else if(i == 4) {
                    lblName5.setText(h.name);
                    lblScore5.setText(String.valueOf(h.hs));
                } else {
                    break;
                }
                System.out.println(hs.get(i).name + ": " + hs.get(i).hs);
            }
        });
        
        UIButton btnCustomize = new UIButton(132, 294, 160, 46, Assets.images.get("LevelSelectCustomize"));
        btnCustomize.setOnClickListener(() -> {
            Game.setCurrentScreen(CustomizeScreen.getInstance());
            CustomizeScreen.getInstance().setPlayer(player);
            CustomizeScreen.getInstance().init();
        });
        
        UIButton btnShop = new UIButton(132, 349, 160, 46, Assets.images.get("LevelSelectShop"));
        btnShop.setOnClickListener(() -> {
            Game.setCurrentScreen(ShopScreen.getInstance());
            ShopScreen.getInstance().setPlayer(player);
            ShopScreen.getInstance().init();
        });
        
        UIButton btnSave = new UIButton(58, 478, 188, 51, Assets.images.get("LevelSelectSave"));
        btnSave.setOnClickListener(() -> {
            showStats = true;
        });
        
        UIButton btnMainMenu = new UIButton(58, 539, 188, 51, Assets.images.get("LevelSelectMainMenu"));
        btnMainMenu.setOnClickListener(() -> {
            MainMenuScreen.getInstance().init();
            Game.setCurrentScreen(MainMenuScreen.getInstance());
        });
        
        UILabel lblSelectedPlanet = new UILabel(0, 45, "NEPTUNE", new Color(255f / 255f, 237f / 255f, 211f / 255f, 1f), UILabel.DEFAULT_FONT);
        lblSelectedPlanet.setFontSize(29);
        
        UILabel lblLevel = new UILabel(3, 410, "Lv. " + (player.getArmorLvl() + player.getEnergyLvl() - 1), Color.WHITE, UILabel.DEFAULT_FONT);
        lblLevel.setFontSize(11);
        UILabel lblCoins = new UILabel(75, 410, String.valueOf(player.getCoins()), Color.WHITE, UILabel.DEFAULT_FONT);
        lblCoins.setFontSize(11);
        UILabel lblArmor = new UILabel(0, 418, "Armor Level: " + player.getArmorLvl(), Color.WHITE, UILabel.DEFAULT_FONT);
        lblArmor.setFontSize(13);
        UILabel lblEnergy = new UILabel(0, 440, "Energy Level: " + player.getEnergyLvl(), Color.WHITE, UILabel.DEFAULT_FONT);
        lblEnergy.setFontSize(13);
        
        UILabel lblName = new UILabel(0, 384, player.getName(), Color.WHITE, UILabel.DEFAULT_FONT);
        lblName.setFontSize(11);
        
        uiControls.put("btnPlayLevel", btnPlayLevel);
        uiControls.put("btnHighscores", btnHighscores);
        uiControls.put("btnCustomize", btnCustomize);
        uiControls.put("btnShop", btnShop);
        uiControls.put("btnSave", btnSave);
        uiControls.put("btnMainMenu", btnMainMenu);
        uiControls.put("lblSelectedPlanet", lblSelectedPlanet);
        uiControls.put("lblLevel", lblLevel);
        uiControls.put("lblCoins", lblCoins);
        uiControls.put("lblArmor", lblArmor);
        uiControls.put("lblEnergy", lblEnergy);
        uiControls.put("lblName", lblName);
        
        // Objects
        selectablePlanets = new ArrayList<>();
        SelectablePlanet sun = new SelectablePlanet(503, 18, 94, 89, Assets.images.get("sun"), "Sun");
        sun.setOnClickListener(() -> {
            if(sun.getState() == PlanetState.NORMAL) {
                selectPlanet("Sun");
            }
        });
        
        SelectablePlanet mercury = new SelectablePlanet(650, 110, 31, 29, Assets.images.get("mercury"), "Mercury");
        mercury.setOnClickListener(() -> {
            if(mercury.getState() == PlanetState.NORMAL) {
                selectPlanet("Mercury");
            }
        });
        
        SelectablePlanet venus = new SelectablePlanet(413, 126, 44, 42, Assets.images.get("venus"), "Venus");
        venus.setOnClickListener(() -> {
            if(venus.getState() == PlanetState.NORMAL) {
                selectPlanet("Venus");
            }
        });
        
        SelectablePlanet earth = new SelectablePlanet(551, 193, 53, 48, Assets.images.get("earth"), "Earth");
        earth.setOnClickListener(() -> {
            if(earth.getState() == PlanetState.NORMAL) {
                selectPlanet("Earth");
            }
        });
        
        SelectablePlanet mars = new SelectablePlanet(735, 229, 41, 39, Assets.images.get("mars"), "Mars");
        mars.setOnClickListener(() -> {
            if(mars.getState() == PlanetState.NORMAL) {
                selectPlanet("Mars");
            }
        });
        
        SelectablePlanet jupiter = new SelectablePlanet(339, 269, 72, 71, Assets.images.get("jupiter"), "Jupiter");
        jupiter.setOnClickListener(() -> {
            if(jupiter.getState() == PlanetState.NORMAL) {
                selectPlanet("Jupiter");
            }
        });
        
        SelectablePlanet saturn = new SelectablePlanet(506, 331, 66, 69, Assets.images.get("saturn"), "Saturn");
        saturn.setOnClickListener(() -> {
            if(saturn.getState() == PlanetState.NORMAL) {
                selectPlanet("Saturn");
            }
        });
        
        SelectablePlanet uranus = new SelectablePlanet(713, 378, 47, 47, Assets.images.get("uranus"), "Uranus");
        uranus.setOnClickListener(() -> {
            if(uranus.getState() == PlanetState.NORMAL) {
                selectPlanet("Uranus");
            }
        });
        
        SelectablePlanet neptune = new SelectablePlanet(397, 433, 52, 49, Assets.images.get("neptune"), "Neptune");
        neptune.setOnClickListener(() -> {
            if(neptune.getState() == PlanetState.NORMAL) {
                selectPlanet("Neptune");
            }
        });              
        
        selectablePlanets.add(sun);
        selectablePlanets.add(mercury);
        selectablePlanets.add(venus);
        selectablePlanets.add(earth);
        selectablePlanets.add(mars);
        selectablePlanets.add(jupiter);
        selectablePlanets.add(saturn);
        selectablePlanets.add(uranus);
        selectablePlanets.add(neptune);
        funFactIndex = Util.randNum(1, 3);
        
        // get unlocked planets
        for(int i = 0; i < player.getLevel(); i++) {
            if (i >= 9) break;
            selectablePlanets.get(8 - i).setState(PlanetState.NORMAL);
        }
        if (player.getLevel() >= 9){
            selectPlanet("Sun");
        }else{
            selectPlanet(selectablePlanets.get(8 - player.getLevel() + 1).getName());
        }
        // music
        Assets.playMusic(Assets.mainMenu);
        
        // Stats Window
        UIButton btnClose = new UIButton(306, 368, 188, 51, Assets.images.get("GameStatsClose"));
        btnClose.setOnClickListener(() -> {
            showStats = false;
        });
        
        UILabel lblPlayTime = new UILabel(449, 219, player.getStats().getTimePlayed(), Color.WHITE, UILabel.DEFAULT_FONT);
        UILabel lblDeaths = new UILabel(449, 247, String.valueOf(player.getStats().getDeaths()), Color.WHITE, UILabel.DEFAULT_FONT);
        UILabel lblEnemiesKilled = new UILabel(449, 276, String.valueOf(player.getStats().getEnemiesKilled()), Color.WHITE, UILabel.DEFAULT_FONT);
        UILabel lblBulletsShot = new UILabel(449, 304, String.valueOf(player.getStats().getShots()), Color.WHITE, UILabel.DEFAULT_FONT);
        UILabel lblCoinsCollected = new UILabel(449, 332, String.valueOf(player.getStats().getCoins()), Color.WHITE, UILabel.DEFAULT_FONT);
        
        statsControls = new ArrayList<>();
        statsControls.add(btnClose);
        statsControls.add(lblPlayTime);
        statsControls.add(lblDeaths);
        statsControls.add(lblEnemiesKilled);
        statsControls.add(lblBulletsShot);
        statsControls.add(lblCoinsCollected);
        
    }
    /**
     * render screen and its objects
     * @param g 
     */
    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.images.get("ExpBackground"), 0, 0, Game.getDisplay().getWidth(), Game.getDisplay().getHeight(), null);
        sb.render(g);
        g.drawImage(Assets.images.get("LevelSelectBackground"), 0, 0, Game.getDisplay().getWidth(), Game.getDisplay().getHeight(), null);
        
        // render pal
        BufferedImage palImg = null;
        int selectedPal = player.getSelectedPal();
        int offsetX = 0, offsetY = 0;
        switch(selectedPal) {
            case 0:
                palImg = Assets.images.get("LevelSelectYoth");
                offsetX = 0;
                break;
            case 1:
                palImg = Assets.images.get("LevelSelectLak");
                offsetX = -4;
                break;
            case 2:
                palImg = Assets.images.get("LevelSelectAda");
                offsetX = -3;
                offsetY = 9;
                break;
        }
        g.drawImage(palImg, 740 + offsetX, 510 + offsetY, palImg.getWidth(), palImg.getHeight(), null);
        
        // render speech tip
        String s = getPlanetString();
        g.drawImage(Assets.images.get(s + "Fact" + funFactIndex), 336, 524, 346, 61, null);
        
        // render ship 
        g.drawImage(player.getSkinImg(player.getSkin()), 29, 306, 64, 64, null);
        
        // render coin icon
        g.drawImage(Assets.images.get("coin"), 64, 411, 7, 7, null);
        
        // render first bullet
        g.drawImage(Assets.images.get("BulletNormal"), 24, 435, 7, 19, null);
        
        // render second bullet
        switch(player.getCurrBullet()) {
            case Bullet.BULLET_DOUBLE_SHOT:
                g.drawImage(Assets.images.get("DoubleShot"), 78, 432, 8, 24, null);
                g.drawImage(Assets.images.get("DoubleShot"), 93, 432, 8, 24, null);
                break;
            case Bullet.BULLET_HEAVY_SHOT:
                g.drawImage(Assets.images.get("HeavyShot"), 85, 433, 9, 28, null);
                break;
            case Bullet.BULLET_PROTON_SHOT:
                g.drawImage(Assets.images.get("ProtonShot"), 82, 439, 14, 13, null);
                break;
            case Bullet.BULLET_TRIPLE_SHOT:
                g.drawImage(Assets.images.get("TripleShot"), 68, 432, 9, 26, null);
                g.drawImage(Assets.images.get("TripleShot"), 83, 432, 9, 26, null);
                g.drawImage(Assets.images.get("TripleShot"), 98, 432, 9, 26, null);
                break;
        }  
        
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
            
            if(key.equals("lblName")) {
                UILabel lbl = (UILabel)val;
                lbl.calculateDimensions(g);
                lbl.setX(60 - lbl.getWidth() / 2);
            }
        } 
        
        // render objects
        for(SelectablePlanet planet : selectablePlanets) {
            planet.render(g);
        }
        
        // render stats
        if(showStats) {
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect(0, 0, Game.getDisplay().getWidth(), Game.getDisplay().getHeight());
            
            Composite orig = g.getComposite();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            g.drawImage(Assets.images.get("GameStatsWindow"), 220, 165, 360, 270, null);
            for(UIControl control : statsControls) {
                control.render(g);
            }
            
            g.setComposite(orig);
        }
        
        // render hs
        if(showHighscores) {
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect(0, 0, Game.getDisplay().getWidth(), Game.getDisplay().getHeight());
            
            Composite orig = g.getComposite();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            g.drawImage(Assets.images.get("HighscoreWindow"), 228, 85, 384, 384, null);
            
            BufferedImage p = Assets.images.get("trophu");
            g.drawImage(p, 253, 111, p.getWidth(), p.getHeight(), null);
            
            for(UIControl control : hsControls) {
                if(control instanceof UILabel) {
                    UILabel lbl = (UILabel)control;
                    lbl.calculateDimensions(g);
                    
                }
                control.render(g);
            }
            g.setComposite(orig);
        }
    }
    /**
     * updates screen and its objects
     */
    @Override
    public void update() {
        for(Map.Entry<String, UIControl> entry : uiControls.entrySet()) {
            entry.getValue().update();
        }
        
        // update objects
        for(SelectablePlanet planet : selectablePlanets) {
            planet.update();
        }
        
        // update stat controls
        if(showStats) {
            for(UIControl control : statsControls) {
                control.update();
            }
        }
        
        // update hs controls
        if(showHighscores) {
            for(UIControl control : hsControls) {
                control.update();
            }
        }
        
        sb.update();
    }
    /**
     * new player
     * @param player 
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
    /**
     * returns the planet string
     * @return selected planet string
     */
    private String getPlanetString() {
        return planets[8 - selectedPlanet + 1];
    }
    /**
     * sets planet as selected
     * @param planet 
     */
    private void selectPlanet(String planet) {
        for(int i = 0; i < selectablePlanets.size(); i++) {
            SelectablePlanet p = selectablePlanets.get(i);
            if(p.getName().equals(planet)) {
                p.setState(PlanetState.SELECTED);
                selectedPlanet = 8 - i + 1;
                UILabel lblSelectedPlanet = (UILabel)uiControls.get("lblSelectedPlanet");
                lblSelectedPlanet.setText(planet.toUpperCase());
                lblSelectedPlanet.setDimensions(false);
            } else {
                if(p.getState() != PlanetState.LOCKED) {
                    p.setState(PlanetState.NORMAL);
                }
            }
        }
    }
}