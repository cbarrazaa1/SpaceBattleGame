/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.game;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author rogel
 */
public class GuidedBullet extends Bullet{
    private Player player;
    
    public GuidedBullet(float x, float y, float width, float height, int damage, double theta, double speed, BufferedImage img, int bulletType, Color color, ArrayList<Light2D> lights, Player player) {
        super(x, y, width, height, damage, theta, speed, img, bulletType, color, lights);
        this.player = player;
    }

    private void turn(){
        
        // delta X and Y are calculated
        double deltaX = (player.getX()+player.getWidth()/2) - ( x + getHeight() / 2);
        double deltaY = (player.getY()+player.getHeight()/2) - ( y + getWidth() / 2);
        
        // for when the diference in angles is more than 90 degrees stop turning
        if (theta + Math.PI/2 - Math.atan2(deltaY, deltaX) > Math.PI/2){
            return;
        }
        if (theta + Math.PI/2 - Math.atan2(deltaY, deltaX) < -Math.PI/2){
            return;
        }     
        // boss follows the player
        if (theta + Math.PI/2 < Math.atan2(deltaY, deltaX)-.01){
            theta += Math.PI/240;
        }if (theta + Math.PI/2 > Math.atan2(deltaY, deltaX)+.01){
            theta -= Math.PI/240;
        }
    
    }
    
    private void move(){
        // moves the way it is looking
        setX(getX() + ((float)(Math.cos(theta + Math.PI / 2)) * (float)speed));
        setY(getY() + ((float)(Math.sin(theta + Math.PI / 2)) * (float)speed));
    }
    
    @Override
    public void update() {
        this.turn();
        this.move();

        // update light
        light.setX(x + width / 2);
        light.setY(y + height / 2);
    }
    
}
