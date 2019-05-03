/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.levels;

import better.core.Game;
import better.core.Timer;
import better.core.Util;
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
    private static final int TO_DEFEAT = 15;
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
            enemies.add(new Enemy3(64, 64, 40, player, bullets, lights));
            if(defeated < TO_DEFEAT) {
                spawnTimer.restart(Util.randNumF(1.6f, 2.5f));
            } else {
                spawnTimer.restart(Util.randNumF(4f, 7f));
            }
        }
        spawnTimer.update();
    }
    
    @Override
    public void onEnemyDead(Enemy enemy) {
        defeated++;
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