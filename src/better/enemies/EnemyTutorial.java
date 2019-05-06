/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.enemies;

import better.assets.Assets;
import better.bullets.Bullet;
import better.core.Game;
import better.core.Util;
import better.game.Light2D;
import better.game.Player;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author cesar
 */
public class EnemyTutorial extends Enemy {
    public static int STATE_ENEMY1 = 0;
    public static int STATE_ENEMY1_FINISHED = 1;
    public static int STATE_ENEMY1_FIGHT = 2;
    public static int STATE_ENEMY2 = 3;
    public static int STATE_ENEMY2_FINISHED = 4;
    public static int STATE_ENEMY2_FIGHT = 5;
    public static int STATE_ENEMY2_END = 6;
    
    private ArrayList<Bullet> bullets;
    private int state;
    private int shootTimer;
    private int moveTimer;
    private int xSpeed;
    private int ySpeed;
    private boolean canAct;
    private boolean shouldRenderBar;
    
    public EnemyTutorial(float x, float y, float width, float height, int score, int coins, int maxHealth, Player player, ArrayList<Bullet> bullets, ArrayList<Light2D> lights, int state) {
        super(x, y, width, height, score, coins, maxHealth, player, lights);
        this.bullets = bullets;
        img = Assets.images.get("EnemyShip1");
        this.state = state;
        canAct = true;
    }
    
    private void checkColision(){
        //check for out of bounds collision
        if(getX() >= Game.getDisplay().getWidth() - width){
            xSpeed *= -1;
            setX(Game.getDisplay().getWidth() - width - 1);
        } else if(getX() <= 0) {
            xSpeed *= -1;
            setX(1);
        }
        
        if(getY() >= Game.getDisplay().getHeight() - height){
            ySpeed *= -1;
            setY(Game.getDisplay().getHeight() - height - 1);
        } else if(getY() <= 0){
            ySpeed *= -1;
            setY(1);
        }
    }
    
    @Override
    public void render(Graphics2D g) { 
        super.render(g);
        
        if(shouldRenderBar) {
            healthBar.render(g);
        }
    }
    
    @Override
    public void update() {
        if(!canAct) {
            return;
        }
        
        super.update();
        if(state == STATE_ENEMY1) {
            y++;
            
            if(y >= 20) {
                state = STATE_ENEMY1_FINISHED;
            }
        } else if(state == STATE_ENEMY1_FIGHT) {
            int WIDTH = Game.getDisplay().getWidth();
            int HEIGHT = Game.getDisplay().getHeight();
            
            checkColision();
             // checks when its time for the next shot
             if (shootTimer <= 0) {
                 bullets.add(new Bullet(getX() + getWidth() / 2, getY() + getHeight() / 2, 8, 17, 5,
                             theta, 6, Assets.images.get("BulletEnemyRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
                 shootTimer = (int)(Math.random() * 170);
             }
             shootTimer--;

             // checks when its time for the next change in movement
             if (moveTimer <= 0){
                 xSpeed = (int) Util.randNumF(-3, 3);
                 ySpeed = (int)Util.randNumF(-3, 3);
                 moveTimer = (int)(Math.random() * 500);
             }
             moveTimer--;

             // moves the object
             setX(getX() + xSpeed);
             setY(getY() + ySpeed);

            // update healthbar
            healthBar.setValue(health);
            healthBar.setX(x);
            healthBar.setY(y - 10);

            // theta is calculated
            theta = this.getThetaTo(player);
            theta -= Math.PI / 2;
        } else if(state == STATE_ENEMY2) {
            y++;
            
            if(y >= 20) {
                state = STATE_ENEMY2_FINISHED;
            }
        } else if(state == STATE_ENEMY2_FIGHT) {
            if(shootTimer <= 0) {
                bullets.add(new Bullet(getX() + getWidth() / 2, getY() + getHeight() / 2, 8, 17, 120,
                            theta, 7, Assets.images.get("BulletEnemyRed"), Bullet.BULLET_TYPE_ENEMY, Color.RED, lights));
                shootTimer = 8;
            }
            shootTimer--;
            
            x += 3;
            if(x >= Game.getDisplay().getWidth() + 20) {
                state = STATE_ENEMY2_END;
            }
        }
    }
    
    public int getState() {
        return state;
    }
    
    public void setState(int state) {
        this.state = state;
    }
    
    public boolean canAct() {
        return canAct;
    }
    
    public void setAct(boolean canAct) {
        this.canAct = canAct;
    }
    
    @Override
    public void mouseEnter() {
        shouldRenderBar = true;
    }
    
    @Override
    public void mouseLeave() {
        shouldRenderBar = false;
    }
}
