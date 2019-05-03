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
import better.game.Coin;
import better.game.Player;
import better.game.Powerup;
import better.scenes.LevelScreen;
import better.ui.UILabel;

/**
 *
 * @author Cesar Barraza
 */
public class Level1 extends Level {
    private static final int TO_DEFEAT = 15;
    private int defeated;
    private Timer spawnTimer;
    
    public Level1(Player player) {
        super(player);
        defeated = 0;
        spawnTimer = new Timer(1f);
        eventListener = this;
    }
    
    @Override
    public void update() {
        super.update();
        if(spawnTimer.isActivated()) {
            enemies.add(new Enemy1(64, 64, 25, 10, 40, player, bullets, lights));
            if(defeated < TO_DEFEAT) {
                spawnTimer.restart(Util.randNumF(2.3f, 3.3f));
            } else {
                spawnTimer.restart(Util.randNumF(4.5f, 7f));
            }
        }
        spawnTimer.update();
    }
    
    @Override
    public void onEnemyDead(Enemy enemy) {
        if(enemy instanceof Boss1) {
            player.setCoins(player.getCoins() + 45);
            LevelScreen.getInstance().setVictory();
            UILabel lblScore = (UILabel)LevelScreen.getInstance().getUIControl("lblVictoryScore");
            lblScore.setText(String.valueOf(score));
            
            UILabel lblCoins = (UILabel)LevelScreen.getInstance().getUIControl("lblCoins");
            lblCoins.setText(String.valueOf(player.getCoins()));
        }
        
        defeated++;
        if(defeated == TO_DEFEAT) {
            enemies.add(new Boss1(Game.getDisplay().getWidth() / 2 - 75, -300, 128, 128, 150, 0, 750, player, bullets, lights));
        }

        // spawn powerup
        int p = Util.randNum(1, 9);
        if(p == 2) {
            float x = enemy.getX();
            float y = enemy.getY();
            powerups.add(new Powerup(x, y, 48, 30, Powerup.TYPE_HEALTH, player));
        }
    }
}