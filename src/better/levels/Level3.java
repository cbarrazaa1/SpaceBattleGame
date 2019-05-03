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
import better.enemies.OGEnemy1;
import better.enemies.OGEnemy2;
import better.game.Powerup;

/**
 *
 * @author Cesar Barraza
 */
public class Level3 extends Level {
    private static final int TO_DEFEAT = 100;
    private int defeated;
    private Timer spawnTimer;
    
    public Level3(Player player) {
        super(player);
        defeated = 0;
        spawnTimer = new Timer(1f);
        eventListener = this;
        player.setLevel(3);
    }
    
    @Override
    public void update() {
        super.update();
        if(spawnTimer.isActivated()) {
            //enemies.add(new Asteroid1(0, 0, 128, 128, 100, 0, 10, player, bullets, lights));
            enemies.add(new OGEnemy1(64, 64, 100, 10, 40, player, bullets, lights));
            enemies.add(new OGEnemy2(64, 64, 100, 10, 40, player, bullets, lights));
            if(defeated < TO_DEFEAT) {
                spawnTimer.restart(Util.randNumF(1.5f, 2.5f));
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