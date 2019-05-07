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
import better.enemies.Singularity;
import better.enemies.TrueSingularity;
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
public class Level8 extends Level {
    private static final int TO_DEFEAT = 25;
    private int defeated;
    private Timer spawnTimer;
    private Timer spawnTimer2;
    private Timer spawnTimer3;
    
    
    public Level8(Player player) {
        super(player);
        defeated = 0;
        spawnTimer = new Timer(1f);
        spawnTimer2 = new Timer(3);
        spawnTimer3 = new Timer(5);
        eventListener = this;
        enemies.add(new Singularity(140, 140, 150, 0, 1500, player, bullets, lights, enemies));
    }
    /**
     * updates the level
     */
    @Override
    public void update() {
        if(LevelScreen.getInstance().isPaused()) {
            return;
        }
        
        super.update();
    }
    
    @Override
    public void onEnemyDead(Enemy enemy) {
        if (enemy instanceof Singularity){
            enemies.add(new TrueSingularity(100, 100, 150, 0, 1000, player, bullets, lights));
        }
        if (enemy instanceof TrueSingularity){
            collectedCoins += 100;
            LevelScreen.getInstance().setVictory();
            UILabel lblScore = (UILabel)LevelScreen.getInstance().getUIControl("lblVictoryScore");
            lblScore.setText(String.valueOf(score));
            
            UILabel lblCoins = (UILabel)LevelScreen.getInstance().getUIControl("lblCoins");
            lblCoins.setText(String.valueOf(collectedCoins));
            player.setCoins(player.getCoins() + collectedCoins);
        }

    }

    @Override
    public void onGameOver() {
    }
}