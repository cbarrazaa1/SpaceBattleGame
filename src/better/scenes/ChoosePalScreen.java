/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.scenes;

import better.assets.Assets;
import better.core.Game;
import better.game.GameObject;
import better.game.MessageBox;
import better.game.Player;
import better.game.StarBackground;
import better.ui.UIButton;
import better.ui.UIControl;
import better.ui.UILabel;
import better.ui.UITextbox;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
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
    /**
     * returns if pal is selected
     * @return selected
     */
    public boolean isSelected() {
        return selected;
    }
    /**
     * sets if selected
     * @param selected 
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    /**
     * renders the pal
     * @param g 
     */
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
        
    // Game objects
    private SelectablePal yothPal;
    private SelectablePal lakPal;
    private SelectablePal adaPal;
    
    private StarBackground sb;
    private UITextbox txtBox;
    private MessageBox msgBox;
    /**
     * initializes the screen
     */
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
            if(txtBox.getText().length() < 4) {
                msgBox = new MessageBox("Your name must be at least 4 characters long!", MessageBox.MSG_TYPE_OK, null, null);
            } else {
                Player player = new Player(Game.getDisplay().getWidth() / 2, Game.getDisplay().getHeight() / 2, 64, 64, 1, 1);
                player.setName(txtBox.getText());
                int selectedPal = 0;
                if(yothPal.isSelected()) {
                    selectedPal = 0;
                }

                if(lakPal.isSelected()) {
                    selectedPal = 1;
                }

                if(adaPal.isSelected()) {
                    selectedPal = 2;
                }
                player.setSelectedPal(selectedPal);
                Game.setCurrentScreen(LevelSelectScreen.getInstance());
                LevelSelectScreen.getInstance().setPlayer(player);
                LevelSelectScreen.getInstance().init(); 
            }
        });
        
        UILabel lblSelectedPal = new UILabel(0, 504, "", Color.WHITE, UILabel.DEFAULT_FONT);
        lblSelectedPal.setFontSize(40);
        lblSelectedPal.setFontStyle(Font.BOLD);
        
        uiControls.put("btnGoBack", btnGoBack);
        uiControls.put("btnConfirm", btnConfirm);
        uiControls.put("lblSelectedPal", lblSelectedPal);
        
        // Objects
        yothPal = new SelectablePal(90, 147, 107, 185, Assets.images.get("YothSelected"));
        yothPal.setOnClickListener(() -> {
            yothPal.setSelected(true);
            lakPal.setSelected(false);
            adaPal.setSelected(false);
            UILabel lbl = (UILabel)uiControls.get("lblSelectedPal");
            lbl.setText("YOTH");
            lbl.setDimensions(false);
            lbl.setColor(Color.RED);
            btnConfirm.setEnabled(true);
        });
        
        lakPal = new SelectablePal(332, 167, 135, 162, Assets.images.get("LakSelected"));
        lakPal.setOnClickListener(() -> {
            lakPal.setSelected(true);
            yothPal.setSelected(false);
            adaPal.setSelected(false);
            UILabel lbl = (UILabel)uiControls.get("lblSelectedPal");
            lbl.setText("LAK");
            lbl.setDimensions(false);
            lbl.setColor(new Color(145f / 255f, 237f / 255f, 108f / 255f, 1f));
            btnConfirm.setEnabled(true);
        });
        
        adaPal = new SelectablePal(583, 192, 126, 137, Assets.images.get("AdaSelected"));
        adaPal.setOnClickListener(() -> {
            adaPal.setSelected(true);
            lakPal.setSelected(false);
            yothPal.setSelected(false);
            UILabel lbl = (UILabel)uiControls.get("lblSelectedPal");
            lbl.setText("ADA");
            lbl.setDimensions(false);
            lbl.setColor(Color.WHITE);
            btnConfirm.setEnabled(true);
        });
        
        sb = new StarBackground(0, 0.5f);
        txtBox = new UITextbox(316, 451);
    }
    /**
     * renders the screen and assets
     * @param g 
     */
    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.images.get("ExpBackground"), 0, 0, Game.getDisplay().getWidth(), Game.getDisplay().getHeight(), null);
        sb.render(g);
        
        g.drawImage(Assets.images.get("ChoosePalBackground"), 0, 0, Game.getDisplay().getWidth(), Game.getDisplay().getHeight(), null);
        g.drawImage(Assets.images.get("ChoosePalTitle"), 0, 0, Game.getDisplay().getWidth(), Game.getDisplay().getHeight(), null);
        g.drawImage(Assets.images.get("backgroundBorder"), 0, 575, 800, 25, null);
        
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
        
        // render objects
        yothPal.render(g);
        lakPal.render(g);
        adaPal.render(g);
        
        txtBox.render(g);
        
        if(msgBox != null && msgBox.isVisible()) {
            msgBox.render(g);
        }
    }
    /**
     * updates the screen and its assets
     */
    @Override
    public void update() {
        if(msgBox != null && msgBox.isVisible()) {
            msgBox.update();
        } else {
            for(Map.Entry<String, UIControl> entry : uiControls.entrySet()) {
                entry.getValue().update();
            }

            // update objects
            yothPal.update();
            lakPal.update();
            adaPal.update();

            sb.update();
            txtBox.update();  
        }
    }
    
}