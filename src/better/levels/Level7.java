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
import better.enemies.BossTurret1;
import better.enemies.Enemy;
import better.enemies.Enemy1;
import better.enemies.Enemy2;
import better.enemies.Enemy3;
import better.game.Player;
import better.enemies.Enemy4;
import better.enemies.EnemyTurret1;
import better.enemies.EnemyTurret2;
import better.enemies.TwinBoss1;
import better.enemies.TwinBoss2;
import better.game.Powerup;
import better.scenes.LevelScreen;
import better.ui.UILabel;

/**
 *
 * @author Cesar Barraza
 * @author Rogelio Martinez
 */
public class Level7 extends Level {
    private static final int TO_DEFEAT = 1;
    private int defeated;
    private Timer spawnTimer;
    private Timer spawnTimer2;
    
    
    public Level7(Player player) {
        super(player);
        defeated = 0;
        spawnTimer = new Timer(1f);
        spawnTimer2 = new Timer(3);
        eventListener = this;
    }
    
    @Override
    public void update() {
        super.update();
        if(spawnTimer.isActivated() && defeated <= TO_DEFEAT) {
            
            enemies.add(new Enemy1(32, 32, 100, 10, 30, player, bullets, lights));
            
            spawnTimer.restart(Util.randNumF(3f, 3.5f));
            
        }
        spawnTimer.update();
        /*
        if(spawnTimer2.isActivated() && defeated <= TO_DEFEAT) {
            
            enemies.add(new EnemyTurret2(32, 32, 100, 10, 30, player, bullets, lights));
            
            spawnTimer2.restart(Util.randNumF(1.5f, 3.5f));
            
        }
        spawnTimer2.update();
*/
    }
    
    @Override
    public void onEnemyDead(Enemy enemy) {
        defeated++;
        
        if (enemy instanceof BossTurret1){
            collectedCoins += 100;
            LevelScreen.getInstance().setVictory();
            UILabel lblScore = (UILabel)LevelScreen.getInstance().getUIControl("lblVictoryScore");
            lblScore.setText(String.valueOf(score));
            
            UILabel lblCoins = (UILabel)LevelScreen.getInstance().getUIControl("lblCoins");
            lblCoins.setText(String.valueOf(collectedCoins));
            player.setCoins(player.getCoins() + collectedCoins);
        }
        
        if(defeated == TO_DEFEAT) {
            enemies.add(new TwinBoss1(Game.getDisplay().getWidth() / 4 - 75, -300, 128, 128, 150, 0, 750, player, bullets, lights));
            enemies.add(new TwinBoss2(Game.getDisplay().getWidth() * 3 / 4 - 75, -300, 128, 128, 150, 0, 750, player, bullets, lights));

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