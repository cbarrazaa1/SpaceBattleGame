/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.scenes;

import better.assets.Assets;
import better.core.Game;
import better.game.GameObject;
import better.ui.UIButton;
import better.ui.UIControl;
import better.ui.UILabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;

class SelectablePal extends GameObject {
    private boolean selected;
    private BufferedImage img;
    
    public SelectablePal(float x, float y, float width, float height, BufferedImage img) {
        super(x, y, width, height);
        selected = false;
        this.img = img;
    }
    
    public boolean isSelected() {
        return selected;
    }
    
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    @Override
    public void render(Graphics2D g) {
        if(isSelected()) {
            g.drawImage(img, (int)getX(), (int)getY(), (int)getWidth(), (int)getHeight(), null);
        }
    }

    @Override
    public void onClick() {}
    
    @Override
    public void mouseEnter() { }

    @Override
    public void mouseLeave() { }

    @Override
    public void mouseDown() { }

    @Override
    public void mouseUp() { }
}
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
        // UI
        UIButton btnGoBack = new UIButton(17, 503, 205, 56, Assets.images.get("PalScreenGoBack"));
        btnGoBack.setOnClickListener(() -> {
            Game.setCurrentScreen(MainMenuScreen.getInstance());
        });
        
        UIButton btnConfirm = new UIButton(578, 503, 205, 56, Assets.images.get("PalScreenConfirm"));
        btnConfirm.setEnabled(false);
        btnConfirm.setOnClickListener(() -> {
            Game.setCurrentScreen(LevelSelectScreen.getInstance());
        });
        
        UILabel lblSelectedPal = new UILabel(0, 504, "", Color.WHITE, UILabel.DEFAULT_FONT);
        lblSelectedPal.setFontSize(40);
        lblSelectedPal.setFontStyle(Font.BOLD);
        
        uiControls.put("btnGoBack", btnGoBack);
        uiControls.put("btnConfirm", btnConfirm);
        uiControls.put("lblSelectedPal", lblSelectedPal);
        
        // Objects
        SelectablePal yothPal = new SelectablePal(90, 147, 107, 185, Assets.images.get("YothSelected"));
        yothPal.setOnClickListener(() -> {
            yothPal.setSelected(true);
            ((SelectablePal)objects.get("lakPal")).setSelected(false);
            ((SelectablePal)objects.get("adaPal")).setSelected(false);
            UILabel lbl = (UILabel)uiControls.get("lblSelectedPal");
            lbl.setText("YOTH");
            lbl.setColor(Color.RED);
            btnConfirm.setEnabled(true);
        });
        
        SelectablePal lakPal = new SelectablePal(332, 167, 135, 162, Assets.images.get("LakSelected"));
        lakPal.setOnClickListener(() -> {
            lakPal.setSelected(true);
            ((SelectablePal)objects.get("yothPal")).setSelected(false);
            ((SelectablePal)objects.get("adaPal")).setSelected(false);
            UILabel lbl = (UILabel)uiControls.get("lblSelectedPal");
            lbl.setText("LAK");
            lbl.setColor(new Color(145f / 255f, 237f / 255f, 108f / 255f, 1f));
            btnConfirm.setEnabled(true);
        });
        
        SelectablePal adaPal = new SelectablePal(583, 192, 126, 137, Assets.images.get("AdaSelected"));
        adaPal.setOnClickListener(() -> {
            adaPal.setSelected(true);
            ((SelectablePal)objects.get("lakPal")).setSelected(false);
            ((SelectablePal)objects.get("yothPal")).setSelected(false);
            UILabel lbl = (UILabel)uiControls.get("lblSelectedPal");
            lbl.setText("ADA");
            lbl.setColor(Color.WHITE);
            btnConfirm.setEnabled(true);
        });
        
        objects.put("yothPal", yothPal);
        objects.put("lakPal", lakPal);
        objects.put("adaPal", adaPal);
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.images.get("ChoosePalBackground"), 0, 0, Game.getDisplay().getWidth(), Game.getDisplay().getHeight(), null);
        
        for(Map.Entry<String, UIControl> entry : uiControls.entrySet()) {
            String key = entry.getKey();
            UIControl val = entry.getValue();
            int w = Game.getDisplay().getWidth();
            
            val.render(g); 
            
            if(key.equals("lblSelectedPal")) {
                UILabel lbl = (UILabel)val;
                lbl.calculateDimensions(g);
                lbl.setX(w / 2 - lbl.getWidth() / 2);
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
