/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.game;

import better.assets.Assets;
import better.scenes.MainMenuScreen;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

/**
 *
 * @author rogel
 */
public class EnemyOne extends GameObject{
    private double theta;
    private int shootTimer;
    private ArrayList<EnemyShot> shot;
    
    public EnemyOne(float x, float y, float width, float height) {
        super(x, y, width, height);
        theta = -Math.PI/2;
        shootTimer = (int)(Math.random()*200);
        shot = new ArrayList<EnemyShot>();
    }

    @Override
    public void render(Graphics2D g) {
        for (int i = 0; i < shot.size(); i++){
            shot.get(i).render(g);
        }
        
        AffineTransform orig = g.getTransform();
        g.translate(getX(), getY());
        g.rotate(theta, getWidth() / 2, getHeight() / 2);
        g.drawImage(Assets.images.get("Enemy1v1"), 0, 0, (int)(getWidth()), (int)(getHeight()), null);
        g.setTransform(orig);
    }

    @Override
    public void update(){
        if (shootTimer <= 0){
            shot.add(new EnemyShot(getX()+getWidth()/2, getY()+getHeight()/2, 10, 10, theta));
            shootTimer = (int)(Math.random()*200);
        }
        shootTimer--;
        
        for (int i = 0; i < shot.size(); i++){
            shot.get(i).update();
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
