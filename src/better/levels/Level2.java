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
import better.game.Powerup;

/**
 *
 * @author Cesar Barraza
 */
public class Level2 extends Level {
    private static final int TO_DEFEAT = 100;
    private int defeated;
    private Timer spawnTimer;
    
    public Level2() {
        super();
        defeated = 0;
        spawnTimer = new Timer(1f);
        eventListener = this;
    }
    
    @Override
    public void update() {
        super.update();
        if(spawnTimer.isActivated()) {
            //enemies.add(new Asteroid1(0, 0, 128, 128, 100, 0, 10, player, bullets, lights));
            enemies.add(new Enemy2(64, 64, 100, 10, 40, player, bullets, lights));
            if(defeated < TO_DEFEAT) {
                spawnTimer.restart(Util.randNumF(1.5f, 2.5f));
            } else {
                spawnTimer.restart(Util.randNumF(4f, 7f));
            }
        }
        spawnTimer.update();
    }
    
    //TEST ASTEROID///CODE FOR ASTEROID DEATH, USE FOR LEVELS WITH ASTEROIDS
    private void asteroidDeath(Enemy enemy){
        if (enemy instanceof Asteroid1){
            Enemy e = enemy;
            Asteroid1 a = (Asteroid1)e;
            a.explode();
            if (a.getWidth() > 32){
                enemies.add(new Asteroid1(a.getX(), a.getY(), a.getWidth()/2, a.getHeight()/2, 100, 0, 10, player, bullets, lights));
                enemies.add(new Asteroid1(a.getX(), a.getY(), a.getWidth()/2, a.getHeight()/2, 100, 0, 10, player, bullets, lights));
            }
        }
    }
    
    @Override
    public void onEnemyDead(Enemy enemy) {
        defeated++;
        
        asteroidDeath(enemy);
        //if(defeated == TO_DEFEAT) {
        //    enemies.add(new Boss1(Game.getDisplay().getWidth() / 2 - 75, -300, 128, 128, 750, player, bullets, lights));
        //}

        // spawn powerup
        int p = Util.randNum(1, 9);
        if(p == 2) {
            float x = enemy.getX();
            float y = enemy.getY();
            powerups.add(new Powerup(x, y, 48, 30, Powerup.TYPE_HEALTH, player));
        }
    }
}