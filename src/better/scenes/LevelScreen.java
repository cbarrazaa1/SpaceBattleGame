
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.scenes;

import better.assets.Assets;
import better.core.Game;
import better.core.Timer;
import better.core.Util;
import better.enemies.Boss1;
import better.enemies.Enemy;
import better.enemies.Enemy1;
import better.game.Bullet;
import better.game.GameObject;
import better.game.Light2D;
import better.game.Player;
import better.game.Powerup;
import better.game.StatusBar;
import better.levels.Level;
import better.levels.Level1;
import better.ui.UIButton;
import better.levels.Level2;
import better.ui.UIControl;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import kuusisto.tinysound.Music;

/**
 *
 * @author cesar
 */
public class LevelScreen extends Screen {
    private static LevelScreen instance;
    public static LevelScreen getInstance() {
        if(instance == null) {
            instance = new LevelScreen();
        }
        return instance;
    }
    
    private Level level;
    private boolean gameOver;
    private Timer fadeTimer;
    private float fadeAlpha;
    
    @Override
    public void init() {
        level = new Level2();
        fadeTimer = new Timer(0.1d);
        fadeAlpha = 0;
        gameOver = false;
        
        // Game over buttons
        UIButton btnTryAgain = new UIButton(297, 237, 205, 56, Assets.images.get("GameOverTryAgain"));
        btnTryAgain.setOnClickListener(() -> {
            level = new Level1();
            gameOver = false;
            fadeAlpha = 0;
            fadeTimer = new Timer(0.1d);
        });

        UIButton btnLevelSelect = new UIButton(297, 306, 205, 56, Assets.images.get("GameOverLevelSelect"));
        btnLevelSelect.setOnClickListener(() -> {
            Game.setCurrentScreen(LevelSelectScreen.getInstance());
        });
        
        uiControls.put("btnTryAgain", btnTryAgain);
        uiControls.put("btnLevelSelect", btnLevelSelect);

    }

    @Override
    public void render(Graphics2D g) {
        level.render(g);
        
        if(gameOver) {
            Composite orig = g.getComposite();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fadeAlpha));
            g.drawImage(Assets.images.get("GameOverScreen"), 0, 0, Game.getDisplay().getWidth(), Game.getDisplay().getHeight(), null);
            uiControls.get("btnTryAgain").render(g);
            uiControls.get("btnLevelSelect").render(g);
            g.setComposite(orig);
        }
    }

    @Override
    public void update() {        
        level.update();
        
        if(gameOver) {
            if(fadeTimer.isActivated()) {            
                if(fadeAlpha < 0.6f) {
                    fadeAlpha += 0.04f;
                }
                fadeTimer.restart();
            }
            fadeTimer.update();
            
            uiControls.get("btnTryAgain").update();
            uiControls.get("btnLevelSelect").update();
        }
    } 
    
    public boolean isGameOver() {
        return gameOver;
    }
    
    public void setGameOver() {
        if(!this.gameOver) {
            this.gameOver = true;
            fadeAlpha = 0.0f;
        }
    }
}