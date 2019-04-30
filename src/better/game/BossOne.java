/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.game;

import better.assets.Assets;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

/**
 *
 * @author rogel
 */
public class BossOne extends GameObject{
    
    private double theta;
    private ArrayList<BossOneShot> shots;
    private Player player;
    
    public BossOne(float x, float y, float width, float height, Player player) {
        super(x, y, width, height);
        this.player = player;
        shots = new ArrayList<BossOneShot>();
        theta = 0;
    }
    
    @Override
    public void render(Graphics2D g) {
        AffineTransform orig = g.getTransform();
        g.translate(getX(), getY());
        g.rotate(theta, getWidth() / 2, getHeight() / 2);
        g.drawImage(Assets.images.get("EnemyShip1"), 0, 0, (int)(getWidth()), (int)(getHeight()), null);
        g.setTransform(orig);
    }
    
    @Override
    public void update(){
        // delta X and Y are calculated
        double deltaX = (player.getX()+player.getWidth()/2) - ( x + getHeight() / 2);
        double deltaY = (player.getY()+player.getHeight()/2) - ( y + getWidth() / 2);

        
        /*
        theta = Math.atan2(deltaY, deltaX);
        theta -= Math.PI / 2;
        */
        System.out.println("theta"+theta);
        System.out.println("player"+Math.atan2(deltaY, deltaX));
        
        if (theta < Math.atan2(deltaY, deltaX)-.1){
            theta += Math.PI/400;
        }else if (theta > Math.atan2(deltaY, deltaX)+.1){
            theta -= Math.PI/400;
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
