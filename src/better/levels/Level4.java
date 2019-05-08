/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.levels;

import better.core.Game;
import better.core.Timer;
import better.core.Util;
import better.enemies.Asteroid1;
import better.enemies.Boss1;
import better.enemies.Enemy;
import better.enemies.Enemy1;
import better.enemies.Enemy2;
import better.enemies.Enemy3;
import better.game.Player;
import better.enemies.Enemy4;
import better.enemies.OGBoss;
import better.enemies.OGEnemy1;
import better.enemies.OGEnemy2;
import better.game.Powerup;
import better.scenes.LevelScreen;
import better.ui.UILabel;

/**
 *
 * @author Cesar Barraza
 * @author Rogelio Martinez
 */
public class Level4 extends Level {
    private static final int TO_DEFEAT = 10;
    private int defeated;
    private Timer spawnTimer;
    private Timer spawnTimer2;
    private Timer asteroidTimer;
    private Timer endTimer;
    private int enemyCounter;
    
    public Level4(Player player) {
        super(player);
        defeated = 0;
        spawnTimer = new Timer(1f);
        spawnTimer2 = new Timer(1f);
        asteroidTimer = new Timer(2);
        endTimer = new Timer(5);
        eventListener = this;
        enemyCounter = 0;
    }
    /**
     * update the level
     */
    @Override
    public void update() {
        if(LevelScreen.getInstance().isPaused()) {
            return;
        }
        
        super.update();
        if(spawnTimer.isActivated() && enemyCounter < 10) {
            //enemies.add(new Asteroid1(0, 0, 128, 128, 100, 0, 10, player, bullets, lights));
            enemies.add(new Enemy1(64, 64, 35, 80, 50, player, bullets, lights));
            enemyCounter++;
            
            spawnTimer.restart(Util.randNumF(0.5f, 2f));
            
        }
        spawnTimer.update();
        if(spawnTimer2.isActivated() && enemyCounter < 10) {
            //enemies.add(new Asteroid1(0, 0, 128, 128, 100, 0, 10, player, bullets, lights));
            enemies.add(new Enemy2(64, 64, 50, 80, 40, player, bullets, lights));
            enemyCounter++;
            
            spawnTimer2.restart(Util.randNumF(1.5f, 3f));
            
        }
        spawnTimer2.update();
        if(defeated >= TO_DEFEAT && enemyCounter < 50){
            if(asteroidTimer.isActivated()) {
                enemies.add(new Asteroid1(0, 0, 128, 128, 10, 0, 20, player, bullets, lights));
                
                enemyCounter++;

                asteroidTimer.restart(Util.randNumF(0.5f, 2.5f));

            }
            asteroidTimer.update();
        }
        if (enemyCounter >= 50){
            endTimer.update();
        }
        
    }
    /**
     * on asteroid death
     * @param enemy 
     */
    private void asteroidDeath(Enemy enemy){
        if (enemy instanceof Asteroid1){
            Enemy e = enemy;
            Asteroid1 a = (Asteroid1)e;
            a.explode();
            if (a.getWidth() > 32){
                enemies.add(new Asteroid1(a.getX(), a.getY(), a.getWidth()/2, a.getHeight()/2, 0, 0, 10, player, bullets, lights));
                enemies.add(new Asteroid1(a.getX(), a.getY(), a.getWidth()/2, a.getHeight()/2, 0, 0, 10, player, bullets, lights));
            }
        }
    }
    /**
     * on enemy death
     * @param enemy 
     */
    @Override
    public void onEnemyDead(Enemy enemy) {
        defeated++;
        
        asteroidDeath(enemy);
        //if(defeated == TO_DEFEAT) {
        //    enemies.add(new OGBoss(128, 128, 100, 10, 200, player, bullets, lights));
        //}

        // spawn powerup
        if (endTimer.isActivated()){
            collectedCoins += 750;
            LevelScreen.getInstance().setVictory();
            UILabel lblScore = (UILabel)LevelScreen.getInstance().getUIControl("lblVictoryScore");
            lblScore.setText(String.valueOf(score));
            
            UILabel lblCoins = (UILabel)LevelScreen.getInstance().getUIControl("lblCoins");
            lblCoins.setText(String.valueOf(collectedCoins));
            player.setCoins(player.getCoins() + collectedCoins);
        }
        
        if (enemy instanceof Asteroid1){
            return;
        }
        int p = Util.randNum(1, 9);
        if(p == 2) {
            float x = enemy.getX();
            float y = enemy.getY();
            powerups.add(new Powerup(x, y, 48, 30, Powerup.TYPE_HEALTH, player));
        }
    }
    
    @Override
    public void onGameOver() {
    }
}