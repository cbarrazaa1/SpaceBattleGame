/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.levels;

import better.enemies.Enemy;

/**
 *
 * @author Cesar Barraza
 */
public interface LevelEventListener {
    public void onEnemyDead(Enemy enemy);
}
