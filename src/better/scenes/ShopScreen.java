/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.scenes;

import better.assets.Assets;
import better.bullets.Bullet;
import better.core.Game;
import better.game.MessageBox;
import better.game.Player;
import better.input.MouseManager;
import better.ui.UIButton;
import better.ui.UILabel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashSet;

/**
 *
 * @author cesar
 */
public class ShopScreen extends Screen {
    private static ShopScreen instance;
    public static ShopScreen getInstance() {
        if(instance == null) {
            instance = new ShopScreen();
        }
        return instance;
    }
    
    private Player player;
    private int armorCost;
    private int energyCost;
    private BufferedImage descImg;
    private MessageBox msgBox;
    private HashSet<Integer> bullets;
    
    // Shop Rectangles
    private Rectangle2D.Float rect1;
    private Rectangle2D.Float rect2;
    private Rectangle2D.Float rect3;
    private Rectangle2D.Float rect4;
    private Rectangle2D.Float rect5;
    private Rectangle2D.Float rect6;
    /**
     * initializes screen and its objects
     */
    @Override
    public void init() {
        calculateCosts();
        bullets = player.bulletsToHashSet();
        
        UIButton btnGoBack = new UIButton(573, 515, 205, 56, Assets.images.get("ShopGoBack"));
        btnGoBack.setOnClickListener(() -> {
            Game.setCurrentScreen(LevelSelectScreen.getInstance());
        });
        
        UILabel lblArmorCost = new UILabel(269, 451, String.valueOf(armorCost), Color.WHITE, UILabel.DEFAULT_FONT);
        lblArmorCost.setFontSize(11);
        
        UILabel lblEnergyCost = new UILabel(452, 451, String.valueOf(energyCost), Color.WHITE, UILabel.DEFAULT_FONT);
        lblEnergyCost.setFontSize(11);
        
        UILabel lblCoins = new UILabel(0, 532, String.valueOf(player.getCoins()), Color.WHITE, UILabel.DEFAULT_FONT);
        UILabel lblArmorLvl = new UILabel(128, 520, "Armor Level: " + player.getArmorLvl(), Color.WHITE, UILabel.DEFAULT_FONT);
        lblArmorLvl.setFontSize(13);
        
        UILabel lblEnergyLvl = new UILabel(128, 546, "Energy Level: " + player.getEnergyLvl(), Color.WHITE, UILabel.DEFAULT_FONT);
        lblEnergyLvl.setFontSize(13);
        
        UILabel lblDoubleShot = new UILabel(156, 239, "1000", Color.WHITE, UILabel.DEFAULT_FONT);
        lblDoubleShot.setFontSize(11);
        
        UILabel lblHeavyShot = new UILabel(297, 239, "1500", Color.WHITE, UILabel.DEFAULT_FONT);
        lblHeavyShot.setFontSize(11);
        
        UILabel lblProtonShot = new UILabel(437, 239, "2200", Color.WHITE, UILabel.DEFAULT_FONT);
        lblProtonShot.setFontSize(11);
        
        UILabel lblTripleShot = new UILabel(578, 239, "3000", Color.WHITE, UILabel.DEFAULT_FONT);
        lblTripleShot.setFontSize(11);
        
        uiControls.put("btnGoBack", btnGoBack);
        uiControls.put("lblArmorCost", lblArmorCost);
        uiControls.put("lblEnergyCost", lblEnergyCost);
        uiControls.put("lblCoins", lblCoins);
        uiControls.put("lblArmorLvl", lblArmorLvl);
        uiControls.put("lblEnergyLvl", lblEnergyLvl);
        uiControls.put("lblDoubleShot", lblDoubleShot);
        uiControls.put("lblHeavyShot", lblHeavyShot);
        uiControls.put("lblProtonShot", lblProtonShot);
        uiControls.put("lblTripleShot", lblTripleShot);
        
        // initialize rectangles
        rect1 = new Rectangle2D.Float(157, 170, 64, 64);
        rect2 = new Rectangle2D.Float(298, 170, 64, 64);
        rect3 = new Rectangle2D.Float(438, 170, 64, 64);
        rect4 = new Rectangle2D.Float(579, 170, 64, 64);
        rect5 = new Rectangle2D.Float(269, 365, 80, 80);
        rect6 = new Rectangle2D.Float(452, 365, 80, 80);
    }
    /**
     * render screen and its objects
     * @param g 
     */
    @Override
    public void render(Graphics2D g) {
        BufferedImage coin = Assets.images.get("coin");
        int coinW = coin.getWidth();
        int coinH = coin.getHeight();
        
        // background
        g.drawImage(Assets.images.get("ShopScreen"), 0, 0, Game.getDisplay().getWidth(), Game.getDisplay().getHeight(), null);
        
        // draw go back button
        uiControls.get("btnGoBack").render(g);
        
        // draw armor cost
        UILabel lblArmorCost = (UILabel)uiControls.get("lblArmorCost");
        lblArmorCost.render(g);
        lblArmorCost.calculateDimensions(g);
        
        int coinX = 269 + (40 - (coinW / 2 + (int)lblArmorCost.getWidth() / 2)) - coinW + 2;
        g.drawImage(coin, coinX, 450, coinW, coinH, null);
        lblArmorCost.setX(coinX + coinW + 10);
        
        // draw energy cost
        UILabel lblEnergyCost = (UILabel)uiControls.get("lblEnergyCost");
        lblEnergyCost.render(g);
        lblEnergyCost.calculateDimensions(g);
        
        coinX = 452 + (40 - (coinW / 2 + (int)lblEnergyCost.getWidth() / 2)) - coinW + 2;
        g.drawImage(coin, coinX, 450, coinW, coinH, null);
        lblEnergyCost.setX(coinX + coinW + 10);
        
        // draw coins
        UILabel lblCoins = (UILabel)uiControls.get("lblCoins");
        lblCoins.render(g);
        lblCoins.calculateDimensions(g);
        
        coinX = 0 + (58 - (coinW / 2 + (int)lblCoins.getWidth() / 2)) - coinW + 2;
        g.drawImage(coin, coinX, 536, coinW, coinH, null);
        lblCoins.setX(coinX + coinW + 10);
        
        // draw armor lvl
        UILabel lblArmorLvl = (UILabel)uiControls.get("lblArmorLvl");
        lblArmorLvl.render(g);
        lblArmorLvl.calculateDimensions(g);
        lblArmorLvl.setX(121 + (68 - lblArmorLvl.getWidth() / 2));
        
        // draw energy lvl
        UILabel lblEnergyLvl = (UILabel)uiControls.get("lblEnergyLvl");
        lblEnergyLvl.render(g);
        lblEnergyLvl.calculateDimensions(g);
        lblEnergyLvl.setX(121 + (68 - lblEnergyLvl.getWidth() / 2));
           
        // render bought states
        if(bullets.contains(Bullet.BULLET_DOUBLE_SHOT)) {
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect((int)rect1.x, (int)rect1.y, (int)rect1.width, (int)rect1.height);
        } else {
            UILabel lblDoubleShot = (UILabel)uiControls.get("lblDoubleShot");
            lblDoubleShot.render(g);
            lblDoubleShot.calculateDimensions(g);

            coinX = 158 + (32 - (coinW / 2 + (int)lblDoubleShot.getWidth() / 2)) - coinW + 2;
            g.drawImage(coin, coinX, 238, coinW, coinH, null);
            lblDoubleShot.setX(coinX + coinW + 10);
        }
        
        if(bullets.contains(Bullet.BULLET_HEAVY_SHOT)) {
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect((int)rect2.x, (int)rect2.y, (int)rect2.width, (int)rect2.height);
        } else {
            UILabel lblHeavyShot = (UILabel)uiControls.get("lblHeavyShot");
            lblHeavyShot.render(g);
            lblHeavyShot.calculateDimensions(g);

            coinX = 299 + (32 - (coinW / 2 + (int)lblHeavyShot.getWidth() / 2)) - coinW + 2;
            g.drawImage(coin, coinX, 238, coinW, coinH, null);
            lblHeavyShot.setX(coinX + coinW + 10);
        }
        
        if(bullets.contains(Bullet.BULLET_PROTON_SHOT)) {
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect((int)rect3.x, (int)rect3.y, (int)rect3.width, (int)rect3.height);
        } else {
            UILabel lblProtonShot = (UILabel)uiControls.get("lblProtonShot");
            lblProtonShot.render(g);
            lblProtonShot.calculateDimensions(g);

            coinX = 439 + (32 - (coinW / 2 + (int)lblProtonShot.getWidth() / 2)) - coinW + 2;
            g.drawImage(coin, coinX, 238, coinW, coinH, null);
            lblProtonShot.setX(coinX + coinW + 10);
        }
        
        if(bullets.contains(Bullet.BULLET_TRIPLE_SHOT)) {
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect((int)rect4.x, (int)rect4.y, (int)rect4.width, (int)rect4.height);
        } else {
            UILabel lblTripleShot = (UILabel)uiControls.get("lblTripleShot");
            lblTripleShot.render(g);
            lblTripleShot.calculateDimensions(g);

            coinX = 580 + (32 - (coinW / 2 + (int)lblTripleShot.getWidth() / 2)) - coinW + 2;
            g.drawImage(coin, coinX, 238, coinW, coinH, null);
            lblTripleShot.setX(coinX + coinW + 10);
        }
     
        // render desc img
        if(descImg != null) {
            g.drawImage(descImg, (int)Game.getMouseManager().getX() + 12, (int)Game.getMouseManager().getY() - 4, descImg.getWidth(), descImg.getHeight(), null);
        }        
        
        // render msgbox
        if(msgBox != null && msgBox.isVisible()) {
            msgBox.render(g);
        }
    }
    /**
     * updates screen and its objects
     */
    @Override
    public void update() {
        int coins = player.getCoins();
        
        if(msgBox != null && msgBox.isVisible()) {
            msgBox.update();
        } else {
            uiControls.get("btnGoBack").update();

            // check for rectangle collisions
            if(Game.getMouseManager().intersects(rect1)) {             
                if(!bullets.contains(Bullet.BULLET_DOUBLE_SHOT)) { 
                    descImg = Assets.images.get("ShopDoubleShotDesc");
                    if(Game.getMouseManager().isButtonPressed(MouseEvent.BUTTON1)) {
                        descImg = null;
                        if(coins >= 1000) {
                            msgBox = new MessageBox("Do you want to buy the double shot?", MessageBox.MSG_TYPE_YESNO, () -> {
                                msgBox.hide();
                                player.setCoins(player.getCoins() - 1000);
                                bullets.add(Bullet.BULLET_DOUBLE_SHOT);
                                player.hashSetToBullets(bullets);
                                updateLabels();
                            }, null);
                        } else {
                            msgBox = new MessageBox("You do not have enough coins!", MessageBox.MSG_TYPE_OK, null, null);
                        }
                    }
                }
            } else if(Game.getMouseManager().intersects(rect2)) {            
                if(!bullets.contains(Bullet.BULLET_HEAVY_SHOT)) {
                    descImg = Assets.images.get("ShopHeavyShotDesc");
                    if(Game.getMouseManager().isButtonPressed(MouseEvent.BUTTON1)) {
                        descImg = null;
                        if(coins >= 1500) {
                            msgBox = new MessageBox("Do you want to buy the heavy shot?", MessageBox.MSG_TYPE_YESNO, () -> {
                                msgBox.hide();
                                player.setCoins(player.getCoins() - 1500);
                                bullets.add(Bullet.BULLET_HEAVY_SHOT);
                                player.hashSetToBullets(bullets);
                                updateLabels();
                            }, null);
                        } else {
                            msgBox = new MessageBox("You do not have enough coins!", MessageBox.MSG_TYPE_OK, null, null);
                        }
                    }                    
                }
            } else if(Game.getMouseManager().intersects(rect3)) {              
                if(!bullets.contains(Bullet.BULLET_PROTON_SHOT)) {
                    descImg = Assets.images.get("ShopProtonShotDesc");
                    if(Game.getMouseManager().isButtonPressed(MouseEvent.BUTTON1)) {
                        descImg = null;
                        if(coins >= 2200) {
                            msgBox = new MessageBox("Do you want to buy the proton shot?", MessageBox.MSG_TYPE_YESNO, () -> {
                                msgBox.hide();
                                player.setCoins(player.getCoins() - 2200);
                                bullets.add(Bullet.BULLET_PROTON_SHOT);
                                player.hashSetToBullets(bullets);
                                updateLabels();
                            }, null);
                        } else {
                            msgBox = new MessageBox("You do not have enough coins!", MessageBox.MSG_TYPE_OK, null, null);
                        }
                    }
                }             
            } else if(Game.getMouseManager().intersects(rect4)) {                
                if(!bullets.contains(Bullet.BULLET_TRIPLE_SHOT)) {
                    descImg = Assets.images.get("ShopTripleShotDesc");
                    if(Game.getMouseManager().isButtonPressed(MouseEvent.BUTTON1)) {
                        descImg = null;
                        if(coins >= 3000) {
                            msgBox = new MessageBox("Do you want to buy the double shot?", MessageBox.MSG_TYPE_YESNO, () -> {
                                msgBox.hide();
                                player.setCoins(player.getCoins() - 3000);
                                bullets.add(Bullet.BULLET_TRIPLE_SHOT);
                                player.hashSetToBullets(bullets);
                                updateLabels();
                            }, null);
                        } else {
                            msgBox = new MessageBox("You do not have enough coins!", MessageBox.MSG_TYPE_OK, null, null);
                        }
                    }                  
                }
            } else if(Game.getMouseManager().intersects(rect5)) {
                descImg = Assets.images.get("ShopArmorDesc");
                if(Game.getMouseManager().isButtonPressed(MouseEvent.BUTTON1)) {
                    descImg = null;
                    if(coins >= armorCost) {
                        msgBox = new MessageBox("Do you want to buy an armor upgrade?", MessageBox.MSG_TYPE_YESNO, () -> {
                            msgBox.hide();
                            player.setCoins(player.getCoins() - armorCost);
                            player.setArmorLvl(player.getArmorLvl() + 1);
                            calculateCosts();
                            updateLabels();
                        }, null);
                    } else {
                        msgBox = new MessageBox("You do not have enough coins!", MessageBox.MSG_TYPE_OK, null, null);
                    }
                }
            } else if(Game.getMouseManager().intersects(rect6)) {
                descImg = Assets.images.get("ShopEnergyDesc");
                if(Game.getMouseManager().isButtonPressed(MouseEvent.BUTTON1)) {
                    descImg = null;
                    if(coins >= energyCost) {
                        msgBox = new MessageBox("Do you want to buy an energy upgrade?", MessageBox.MSG_TYPE_YESNO, () -> {
                            msgBox.hide();
                            player.setCoins(player.getCoins() - energyCost);
                            player.setEnergyLvl(player.getEnergyLvl() + 1);
                            calculateCosts();
                            updateLabels();
                        }, null);
                    } else {
                        msgBox = new MessageBox("You do not have enough coins!", MessageBox.MSG_TYPE_OK, null, null);
                    }
                }
            } else {
                descImg = null;
            }        
        }
    }
    /**
     * new player
     * @param player 
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
    /**
     * set cost for armor and energy levels
     */
    private void calculateCosts() {
        armorCost = 350 + (player.getArmorLvl() * 150);
        energyCost = 350 + (player.getEnergyLvl() * 150);
    }
    /**
     * update costs, levels and coins
     */
    private void updateLabels() {
        UILabel lblArmorCost = (UILabel)uiControls.get("lblArmorCost");
        lblArmorCost.setText(String.valueOf(armorCost));
        lblArmorCost.setDimensions(false);
        
        UILabel lblEnergyCost = (UILabel)uiControls.get("lblEnergyCost");
        lblEnergyCost.setText(String.valueOf(energyCost));
        lblEnergyCost.setDimensions(false);
        
        UILabel lblCoins = (UILabel)uiControls.get("lblCoins");
        lblCoins.setText(String.valueOf(player.getCoins()));
        lblCoins.setDimensions(false);
        
        UILabel lblArmorLvl = (UILabel)uiControls.get("lblArmorLvl");
        lblArmorLvl.setText("Armor Level: " + player.getArmorLvl());
        lblArmorLvl.setDimensions(false);
        
        UILabel lblEnergyLvl = (UILabel)uiControls.get("lblEnergyLvl");
        lblEnergyLvl.setText("Energy Level: " + player.getEnergyLvl());
        lblEnergyLvl.setDimensions(false);
    }
}
