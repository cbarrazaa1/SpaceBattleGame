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
import better.enemies.Boss2;
import better.enemies.Boss3;
import better.enemies.Enemy;
import better.enemies.Enemy1;
import better.enemies.Enemy2;
import better.enemies.Enemy3;
import better.game.Player;
import better.enemies.Enemy4;
import better.game.Powerup;
import better.scenes.LevelScreen;
import better.ui.UILabel;

/**
 *
 * @author Cesar Barraza
 * @author Rogelio Martinez
 */
public class Level5 extends Level {
    private static final int TO_DEFEAT = 15;
    private int defeated;
    private Timer spawnTimer;
    private Timer spawnTimer2;
    
    public Level5(Player player) {
        super(player);
        defeated = 0;
        spawnTimer = new Timer(1f);
        spawnTimer2 = new Timer(3);
        eventListener = this;
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
        if(spawnTimer.isActivated()) {
            //enemies.add(new Asteroid1(0, 0, 128, 128, 100, 0, 10, player, bullets, lights));
            enemies.add(new Enemy3(64, 64, 70, 100, 30, player, bullets, lights));
            if(defeated < TO_DEFEAT) {
                spawnTimer.restart(Util.randNumF(2.5f, 4.5f));
            } else {
                spawnTimer.restart(Util.randNumF(55f, 60f));
            }
        }
        spawnTimer.update();
       
        if(spawnTimer2.isActivated()) {
            //enemies.add(new Asteroid1(0, 0, 128, 128, 100, 0, 10, player, bullets, lights));
            enemies.add(new Enemy2(64, 64, 50, 50, 30, player, bullets, lights));
            if(defeated < TO_DEFEAT) {
                spawnTimer2.restart(Util.randNumF(3f, 4.5f));
            }else{
                spawnTimer2.restart(Util.randNumF(35f, 45f));
            }
        }
        spawnTimer2.update();

    }
    /**
     * on enemy death
     * @param enemy 
     */
    @Override
    public void onEnemyDead(Enemy enemy) {
        defeated++;
        
        if (enemy instanceof Boss3){
            collectedCoins += 500;
            LevelScreen.getInstance().setVictory();
            UILabel lblScore = (UILabel)LevelScreen.getInstance().getUIControl("lblVictoryScore");
            lblScore.setText(String.valueOf(score));
            
            UILabel lblCoins = (UILabel)LevelScreen.getInstance().getUIControl("lblCoins");
            lblCoins.setText(String.valueOf(collectedCoins));
            player.setCoins(player.getCoins() + collectedCoins);
        }
        if(defeated == TO_DEFEAT) {
            enemies.add(new Boss3(Game.getDisplay().getWidth() / 2 - 75, -300, 64, 64, 500, 0, 400, player, bullets, lights));
        }

        // spawn powerup
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