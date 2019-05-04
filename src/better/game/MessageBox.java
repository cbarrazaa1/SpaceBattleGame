/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.game;

import better.assets.Assets;
import better.core.Game;
import better.ui.UIButton;
import better.ui.UILabel;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author cesar
 */
public class MessageBox extends GameObject {
    public static int MSG_TYPE_OK = 0;
    public static int MSG_TYPE_YESNO = 1;
    
    private int msgType;
    private boolean visible;
    private OnClickListener yesAction;
    private UILabel lblMsg;
    private UIButton btnOK;
    private UIButton btnAccept;
    private UIButton btnCancel;
    
    public MessageBox(String msg, int msgType, OnClickListener action) {
        super(Game.getDisplay().getWidth() / 2 - 460 / 2, Game.getDisplay().getHeight() / 2 - 187 / 2, 460, 187);
        visible = true;
        this.msgType = msgType;
        lblMsg = new UILabel(x, y + 60, msg, Color.WHITE, UILabel.DEFAULT_FONT);
        if(msgType == MSG_TYPE_OK) {
            btnOK = new UIButton(x + 127, y + 120, 205, 56, Assets.images.get("MessageOkay"));
            btnOK.setOnClickListener(() -> {
                visible = false;
            });
        } else {
            btnAccept = new UIButton(x + 12, y + 120, 205, 56, Assets.images.get("MessageAccept"));
            btnAccept.setOnClickListener(action);
            btnCancel = new UIButton(x + 243, y + 120, 205, 56, Assets.images.get("MessageCancel"));
            btnCancel.setOnClickListener(() -> {
                visible = false;
            });
        }
    }
    
    public void hide() {
        visible = false;
    }
    
    public boolean isVisible() {
        return visible;
    }
    
    @Override
    public void render(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 0.5f));
        g.fillRect(0, 0, Game.getDisplay().getWidth(), Game.getDisplay().getHeight());
        g.drawImage(Assets.images.get("MessageBox"), (int)x, (int)y, (int)width, (int)height, null);
        
        // draw text
        lblMsg.render(g);
        lblMsg.calculateDimensions(g);
        lblMsg.setX(Game.getDisplay().getWidth() / 2 - lblMsg.getWidth() / 2);
        
        // draw buttons
        if(msgType == MSG_TYPE_OK) {
            btnOK.render(g);
        } else {
            btnAccept.render(g);
            btnCancel.render(g);
        }
    }
    
    @Override
    public void update() {
        super.update();
        
        if(msgType == MSG_TYPE_OK) {
            btnOK.update();
        } else {
            btnAccept.update();
            btnCancel.update();
        }
    }

    @Override
    public void onClick() {
        
    }

    @Override
    public void mouseEnter() {
        
    }

    @Override
    public void mouseLeave() {
        
    }

    @Override
    public void mouseDown() {
        
    }

    @Override
    public void mouseUp() {
        
    }
}
