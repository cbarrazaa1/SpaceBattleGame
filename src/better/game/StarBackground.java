/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.game;

import better.assets.Assets;
import better.core.Game;
import better.core.Util;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

class Star {
    public BufferedImage img;
    public float x;
    public float y;
    public int type;
    
    public Star(int type, float x, float y) {
        if(type == 0) {
            img = Assets.images.get("starSmall" + Util.randNum(1, 2));
        } else {
            img = Assets.images.get("starBig");
        }
        this.x = x;
        this.y = y;
        this.type = type;
    }
}

/**
 *
 * @author cesar
 */
public class StarBackground extends GameObject {
    private static final int NUM_STARS = 150;
    
    private float velX;
    private float velY;
    private ArrayList<Star> stars;
    
    public StarBackground(float velX, float velY) {
        super(0, 0, Game.getDisplay().getWidth(), Game.getDisplay().getHeight());
        this.velX = velX;
        this.velY = velY;
        stars = new ArrayList<>();
        for(int i = 0; i < NUM_STARS; i++) {
            int r = Util.randNum(0, 100);
            if(r >= 0 && r <= 90) {
                stars.add(new Star(0, (int)Util.randNum(0, (int)width), (int)Util.randNum(0, (int)height)));
            } else {
                stars.add(new Star(1, (int)Util.randNum(0, (int)width), (int)Util.randNum(0, (int)height)));
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        for(Star star : stars) {
            AffineTransform orig = g.getTransform();
            g.translate(star.x, star.y);
            g.drawImage(star.img, 0, 0, star.img.getWidth(), star.img.getHeight(), null);
            g.setTransform(orig);
        }
    }

    @Override
    public void update() {
        for(Star star : stars) {
            float dx = (star.type == 0 ? velX : velX * 1.3f);
            float dy = (star.type == 0 ? velY : velY * 1.3f);
            star.x += dx;
            star.y += dy;
            
            if(velX < 0 && star.x + star.img.getWidth() <= 0) {
                star.x = Game.getDisplay().getWidth();
                star.y = Util.randNum(0, Game.getDisplay().getHeight());
            }
            
            if(velX > 0 && star.x >= Game.getDisplay().getWidth()) {
                star.x = -star.img.getWidth();
                star.y = Util.randNum(0, Game.getDisplay().getHeight());
            }
            
            if(velY < 0 && star.y + star.img.getHeight() <= 0) {
                star.y = Game.getDisplay().getHeight();
                star.x = Util.randNum(0, Game.getDisplay().getWidth());
            }
            
            if(velY > 0 && star.y >= Game.getDisplay().getHeight()) {
                star.y = -star.img.getHeight();
                star.x = Util.randNum(0, Game.getDisplay().getWidth());
            }
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
