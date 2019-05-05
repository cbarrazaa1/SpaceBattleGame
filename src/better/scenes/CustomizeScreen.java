/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.scenes;

import better.assets.Assets;
import better.bullets.Bullet;
import better.core.Game;
import better.game.GameObject;
import better.game.MessageBox;
import better.game.Player;
import better.ui.UIButton;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashSet;

class SelectableRectangle extends GameObject {
    public static final int RECT_STATE_NORMAL = 0;
    public static final int RECT_STATE_SELECTED = 1;
    public static final int RECT_STATE_LOCKED = 2;
    
    private Rectangle2D.Float rect;
    private int state;

    public SelectableRectangle(float x, float y, float width, float height, int state) {
        super(x, y, width, height);
        this.state = state;
    }

    @Override
    public void render(Graphics2D g) {
        Color color = new Color(255, 255, 255, 0);
        switch(state) {
            case RECT_STATE_SELECTED:
                color = new Color(255, 184, 76, 60);
                break;
            case RECT_STATE_LOCKED:
                color = new Color(0, 0, 0, 200);
                break;
        }
        g.setColor(color);
        g.fillRect((int)x, (int)y, (int)width, (int)height);
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
    public void mouseUp() { }
    
    public int getState() {
        return state;
    }
    
    public void setState(int state) {
        this.state = state;
    }
}
/**
 *
 * @author cesar
 */
public class CustomizeScreen extends Screen {
    private static CustomizeScreen instance;
    public static CustomizeScreen getInstance() {
        if(instance == null) {
            instance = new CustomizeScreen();
        }
        
        return instance;
    }
    
    private Player player;
    private int selectedShip;
    private int selectedBullet;
    private int origShip;
    private int origBullet;
    private MessageBox msgBox;
    private ArrayList<SelectableRectangle> shipRects;
    private ArrayList<SelectableRectangle> bulletRects;
    private HashSet<Integer> bullets;
    
    @Override
    public void init() {
        shipRects = new ArrayList<>();
        bulletRects = new ArrayList<>();
        selectedShip = player.getSkin();
        origShip = selectedShip;
        selectedBullet = player.getCurrBullet();
        origBullet = selectedBullet;
        bullets = player.bulletsToHashSet();
        
        // UI
        UIButton btnChange = new UIButton(605, 403, 160, 46, Assets.images.get("CustomChange"));
        btnChange.setOnClickListener(() -> {
            if(selectedShip == origShip && selectedBullet == origBullet) {
                msgBox = new MessageBox("You did not make any change.", MessageBox.MSG_TYPE_OK, null, null);
            } else {
                msgBox = new MessageBox("Are you sure you want to make these changes?", MessageBox.MSG_TYPE_YESNO, () -> {
                    msgBox.hide();
                    player.setSkin(selectedShip);
                    player.setCurrBullet(selectedBullet);
                }, () -> {
                    msgBox.hide();
                    changeShipSelection(origShip);
                    changeBulletSelection(origBullet);
                    selectedBullet = origBullet;
                });
            }
        });
        
        UIButton btnGoBack = new UIButton(564, 477, 205, 56, Assets.images.get("ShopGoBack"));
        btnGoBack.setOnClickListener(() -> {
            Game.setCurrentScreen(LevelSelectScreen.getInstance());
        });
        
        uiControls.put("btnChange", btnChange);
        uiControls.put("btnGoBack", btnGoBack);
        
        // Ship Rectangles
        shipRects.add(new SelectableRectangle(74, 136, 80, 80, SelectableRectangle.RECT_STATE_NORMAL));
        shipRects.get(0).setOnClickListener(() -> {
            changeShipSelection(0);
        });
        
        shipRects.add(new SelectableRectangle(174, 136, 80, 80, SelectableRectangle.RECT_STATE_LOCKED));
        shipRects.get(1).setOnClickListener(() -> {
            changeShipSelection(1);
        });
        
        shipRects.add(new SelectableRectangle(274, 136, 80, 80, SelectableRectangle.RECT_STATE_LOCKED));
        shipRects.get(2).setOnClickListener(() -> {
            changeShipSelection(2);
        });
        
        shipRects.add(new SelectableRectangle(374, 136, 80, 80, SelectableRectangle.RECT_STATE_LOCKED));
        shipRects.get(3).setOnClickListener(() -> {
            changeShipSelection(3);
        });
        
        shipRects.add(new SelectableRectangle(74, 236, 80, 80, SelectableRectangle.RECT_STATE_LOCKED));
        shipRects.get(4).setOnClickListener(() -> {
            changeShipSelection(4);
        });
        
        shipRects.add(new SelectableRectangle(174, 236, 80, 80, SelectableRectangle.RECT_STATE_LOCKED));
        shipRects.get(5).setOnClickListener(() -> {
            changeShipSelection(5);
        });
        
        shipRects.add(new SelectableRectangle(274, 236, 80, 80, SelectableRectangle.RECT_STATE_LOCKED));
        shipRects.get(6).setOnClickListener(() -> {
            changeShipSelection(6);
        });
        
        shipRects.add(new SelectableRectangle(374, 236, 80, 80, SelectableRectangle.RECT_STATE_LOCKED));
        shipRects.get(7).setOnClickListener(() -> {
            changeShipSelection(7);
        });
        
        shipRects.add(new SelectableRectangle(174, 336, 80, 80, SelectableRectangle.RECT_STATE_LOCKED));
        shipRects.get(8).setOnClickListener(() -> {
            changeShipSelection(8);
        });
        
        shipRects.add(new SelectableRectangle(274, 336, 80, 80, SelectableRectangle.RECT_STATE_LOCKED));
        shipRects.get(9).setOnClickListener(() -> {
            changeShipSelection(9);
        });    
       
        int curr = player.getLevel();
        for(int i = 1; i < curr; i++) {
            shipRects.get(i).setState(SelectableRectangle.RECT_STATE_NORMAL);
        }
        shipRects.get(player.getSkin()).setState(SelectableRectangle.RECT_STATE_SELECTED);
        
        // Bullet Rects
        bulletRects.add(new SelectableRectangle(217, 463, 64, 64, SelectableRectangle.RECT_STATE_LOCKED));
        bulletRects.get(0).setOnClickListener(() -> {
            changeBulletSelection(0);
        });
        
        bulletRects.add(new SelectableRectangle(291, 463, 64, 64, SelectableRectangle.RECT_STATE_LOCKED));
        bulletRects.get(1).setOnClickListener(() -> {
            changeBulletSelection(1);
        });
        
        bulletRects.add(new SelectableRectangle(365, 463, 64, 64, SelectableRectangle.RECT_STATE_LOCKED));
        bulletRects.get(2).setOnClickListener(() -> {
            changeBulletSelection(2);
        });
        
        bulletRects.add(new SelectableRectangle(439, 463, 64, 64, SelectableRectangle.RECT_STATE_LOCKED));
        bulletRects.get(3).setOnClickListener(() -> {
            changeBulletSelection(3);
        });
        
        
        for(int i = 0; i < bulletRects.size(); i++) {
            if(bullets.contains(i)) {
                bulletRects.get(i).setState(SelectableRectangle.RECT_STATE_NORMAL);
            }
        }
        
        if(selectedBullet != -1) {
            bulletRects.get(selectedBullet).setState(SelectableRectangle.RECT_STATE_SELECTED);
        }
    }

    @Override
    public void render(Graphics2D g) {
        // draw screen
        g.drawImage(Assets.images.get("CustomizeScreen"), 0, 0, Game.getDisplay().getWidth(), Game.getDisplay().getHeight(), null);
        
        // draw buttons
        uiControls.get("btnChange").render(g);
        uiControls.get("btnGoBack").render(g);
        
        // render ship rectangles
        for(SelectableRectangle rect : shipRects) {
            rect.render(g);
        }
        
        // render bullet rectangles
        for(SelectableRectangle rect : bulletRects) {
            rect.render(g);
        }
        
        // render player ship
        g.drawImage(player.getSkinImg(selectedShip), 653, 229, 64, 64, null);
        
        // render current bullet (temp)
        switch(selectedBullet) {
            case Bullet.BULLET_DOUBLE_SHOT:
                g.drawImage(Assets.images.get("DoubleShot"), 676, 327, 8, 24, null);
                g.drawImage(Assets.images.get("DoubleShot"), 691, 327, 8, 24, null);
                break;
            case Bullet.BULLET_HEAVY_SHOT:
                g.drawImage(Assets.images.get("HeavyShot"), 682, 328, 9, 28, null);
                break;
            case Bullet.BULLET_PROTON_SHOT:
                g.drawImage(Assets.images.get("ProtonShot"), 680, 332, 14, 13, null);
                break;
            case Bullet.BULLET_TRIPLE_SHOT:
                g.drawImage(Assets.images.get("TripleShot"), 666, 326, 9, 26, null);
                g.drawImage(Assets.images.get("TripleShot"), 682, 326, 9, 26, null);
                g.drawImage(Assets.images.get("TripleShot"), 698, 326, 9, 26, null);
                break;
        }  
        
        // render msgbox
        if(msgBox != null && msgBox.isVisible()) {
            msgBox.render(g);
        }
    }

    @Override
    public void update() {
        if(msgBox != null && msgBox.isVisible()) {
            msgBox.update();
        } else {
            // update buttons
            uiControls.get("btnChange").update();
            uiControls.get("btnGoBack").update();

            // update ship rectangles
            for(SelectableRectangle rect : shipRects) {
                rect.update();
            }   
            
            // update bullet rectangles
            for(SelectableRectangle rect : bulletRects) {
                rect.update();
            }
        }
    } 
    
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    private void changeShipSelection(int selected) {
        if(shipRects.get(selected).getState() == SelectableRectangle.RECT_STATE_LOCKED) {
            msgBox = new MessageBox("You have not unlocked this skin!", MessageBox.MSG_TYPE_OK, null, null);
        } else {
            for(int i = 0; i < player.getLevel(); i++) {
                if(i == selected) {
                    shipRects.get(i).setState(SelectableRectangle.RECT_STATE_SELECTED);
                } else {
                    shipRects.get(i).setState(SelectableRectangle.RECT_STATE_NORMAL);
                }
            }

            selectedShip = selected;  
        }
    }
    
    private void changeBulletSelection(int selected) {
        if(selected == -1) {
            for(int i = 0; i < bulletRects.size(); i++) {
                if(bulletRects.get(i).getState() == SelectableRectangle.RECT_STATE_SELECTED) {
                    bulletRects.get(i).setState(SelectableRectangle.RECT_STATE_NORMAL);
                }
            }
            
            return;
        }
        
        if(bulletRects.get(selected).getState() == SelectableRectangle.RECT_STATE_LOCKED) {
             msgBox = new MessageBox("You have not unlocked this bullet type!", MessageBox.MSG_TYPE_OK, null, null);
        } else {
            for(int i = 0; i < bulletRects.size(); i++) {
                if(bullets.contains(i)) {
                    if(i == selected) {
                        bulletRects.get(i).setState(SelectableRectangle.RECT_STATE_SELECTED);
                    } else {
                        bulletRects.get(i).setState(SelectableRectangle.RECT_STATE_NORMAL);
                    }
                }
            }
            
            selectedBullet = selected;
        }
    }
}
