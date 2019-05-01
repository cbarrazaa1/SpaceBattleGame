/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.game;

import better.assets.Assets;
import better.core.Game;
import better.core.Timer;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import static java.lang.Math.abs;
import java.util.ArrayList;

/**
 *
 * @author rogel
 */
public class BossOne extends GameObject{
    
    private double theta;
    private ArrayList<EnemyShot> shot;
    private Player player;
    private Timer moveTimer;
    private Timer shootTimer;
    
    public BossOne(float x, float y, float width, float height, Player player) {
        super(x, y, width, height);
        this.player = player;
        shot = new ArrayList<EnemyShot>();
        theta = 0;
        moveTimer = new Timer(0);
        shootTimer = new Timer(1);
    }
    
    @Override
    public void render(Graphics2D g) {
        // RENDER SHOT HERE FOR NOW
        for (int i = 0; i < shot.size(); i++){
            shot.get(i).render(g);
        }
        
        AffineTransform orig = g.getTransform();
        g.translate(getX(), getY());
        g.rotate(theta - Math.PI/2, getWidth() / 2, getHeight() / 2);
        g.drawImage(Assets.images.get("EnemyShip1"), 0, 0, (int)(getWidth()), (int)(getHeight()), null);
        g.setTransform(orig);
        
    }
    
    int xPos = 0, yPos = 0; 
    double movTheta = 0;
    public void move(){
        
        int WIDTH =  Game.getDisplay().getWidth();
        int HEIGHT = Game.getDisplay().getHeight();
        
        if(moveTimer.isActivated()){
            do{
            xPos = (int)(Math.random() * 600)-300;
            yPos = (int)(Math.random() * 600)-300;
            xPos += getX();
            yPos += getY();
            }while(xPos < 0 || xPos > WIDTH - width || yPos < 0 || yPos > + HEIGHT - height);
            moveTimer.restart(2);
            movTheta = Math.atan2(getY()-yPos, getX()-xPos);
        }
        
        //System.out.println("x: " + (getY()-yPos) + " y: " + (getX()-xPos));
        if((getX() < xPos - 10 || getX() > xPos + 10) && (getY() < yPos - 10 || getY() > yPos + 10)){
            setX(getX() + ((float)(Math.cos(movTheta+Math.PI))*2));
            setY(getY() + ((float)(Math.sin(movTheta+Math.PI))*2));
        }
        moveTimer.update();
    }
    
    public void shoot(){
        float xMID = getX() + getWidth()/2;
        float yMID = getY() + getHeight()/2;
        
        if (shootTimer.isActivated()){
            shootTimer.restart();
            float xF = (float)Math.cos(theta + Math.PI/2)*30;
            float yF = (float)Math.sin(theta + Math.PI/2)*30;
            shot.add(new EnemyShot(xMID + xF, yMID + yF, 10, 10, theta - Math.PI/2));
            shot.add(new EnemyShot(xMID - xF, yMID - yF, 10, 10, theta - Math.PI/2));
        }
        shootTimer.update();
    }
    
    @Override
    public void update(){
        // delta X and Y are calculated
        double deltaX = (player.getX()+player.getWidth()/2) - ( x + getHeight() / 2);
        double deltaY = (player.getY()+player.getHeight()/2) - ( y + getWidth() / 2);
        
        // boss follows the player
        if (theta < Math.atan2(deltaY, deltaX)-.01){
            theta += Math.PI/100;
        }if (theta > Math.atan2(deltaY, deltaX)+.01){
            theta -= Math.PI/100;
        }
        
        // for when the diference in angles is more the 180 degrees
        if (theta - Math.atan2(deltaY, deltaX) > Math.PI){
            theta -= 2*Math.PI;
        }
        if (theta - Math.atan2(deltaY, deltaX) < -Math.PI){
            theta += 2*Math.PI;
        }
        
        // Movement function
        move();
        
        // shooting function
        shoot();
        // UPDATE SHOT HERE FOR NOW
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
