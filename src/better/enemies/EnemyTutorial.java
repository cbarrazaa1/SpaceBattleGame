/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.enemies;

import better.game.Light2D;
import better.game.Player;
import java.util.ArrayList;

/**
 *
 * @author cesar
 */
public class EnemyTutorial extends Enemy {
    
    public EnemyTutorial(float x, float y, float width, float height, int score, int coins, int maxHealth, Player player, ArrayList<Light2D> lights) {
        super(x, y, width, height, score, coins, maxHealth, player, lights);
    }
    
    @Override
    public void update() {
        
    }
}
