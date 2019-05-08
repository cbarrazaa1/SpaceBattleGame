/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.game;

import better.assets.Assets;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author cesar
 */
public class Powerup extends GameObject {
    public static final int TYPE_HEALTH = 0;
    private int type;
    private float theta;
    private BufferedImage img;
    private Player player;
    
    public Powerup(float x, float y, float width, float height, int type, Player player) {
        super(x, y, width, height);
        this.type = type;
        this.player = player;
        switch(type) {
            case TYPE_HEALTH:
                img = Assets.images.get("PowerupHealth");
                break;
        }
    }
    /**
     * the powerup type
     * @return type
     */
    public int getType() {
        return type;
    }
    /**
     * new powerup type
     * @param type 
     */
    public void setType(int type) {
        this.type = type;
    }
    /**
     * render the powerup
     * @param g 
     */
    @Override
    public void render(Graphics2D g) {
        AffineTransform orig = g.getTransform();
        g.translate(getX(), getY());
        g.drawImage(img, 0, 0, (int)getWidth(), (int)getHeight(), null);
        g.setTransform(orig);
    }
    /**
     * update the powerup
     */
    @Override
    public void update() {
        float distX = (player.getX() + player.getWidth() / 2) - (x + getWidth() / 2);
        float distY = (player.getY() + player.getHeight() / 2) - (y + getHeight() / 2);
        float dist = (float)Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
        float speed = (dist > 200 ? 0f : 1f / (dist / 200));
        theta = (float)Math.atan2(distY, distX);
        
        setX(getX() + ((float)(Math.cos(theta)) * speed));
        setY(getY() + ((float)(Math.sin(theta)) * speed));
        
        if(player.getCurrLevel() == 4){
            setY(getY() + 2);
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
