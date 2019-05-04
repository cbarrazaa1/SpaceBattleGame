
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.scenes;

import better.assets.Assets;
import better.core.Game;
import better.core.Timer;
import better.game.Player;
import better.levels.Level;
import better.levels.Level1;
import better.ui.UIButton;
import better.levels.Level2;
import better.levels.Level3;
import better.ui.UIControl;
import better.ui.UILabel;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

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
    private int selectedLevel;
    private boolean gameOver;
    private boolean victory;
    private boolean paused;
    private Timer fadeTimer;
    private float fadeAlpha;
    private Player player;
    
    @Override
    public void init() {
        player.setX(Game.getDisplay().getWidth() / 2 - player.getWidth() / 2);
        player.setY(Game.getDisplay().getWidth() / 2 - player.getHeight() / 2);
        
        chooseLevel();
        fadeTimer = new Timer(0.1d);
        fadeAlpha = 0;
        gameOver = false;
        victory = false;
        paused = false;
        
        // Game over buttons
        UIButton btnTryAgain = new UIButton(297, 237, 205, 56, Assets.images.get("GameOverTryAgain"));
        btnTryAgain.setOnClickListener(() -> {
            chooseLevel();
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
        
        // Victory UI
        UIButton btnPlayAgain = new UIButton(429, 252, 205, 56, Assets.images.get("VictoryPlayAgain"));
        btnPlayAgain.setOnClickListener(() -> {
            chooseLevel();
            victory = false;
            fadeAlpha = 0;
            fadeTimer = new Timer(0.1d);
        });
        
        UIButton btnContinue = new UIButton(429, 321, 205, 56, Assets.images.get("VictoryContinue"));
        btnContinue.setOnClickListener(() -> {
            Game.setCurrentScreen(LevelSelectScreen.getInstance());
        });
        
        UILabel lblVictoryScore = new UILabel(236, 214, "0", Color.WHITE, UILabel.DEFAULT_FONT);
        lblVictoryScore.setFontSize(24);
        
        UILabel lblHighscore = new UILabel(437, 198, "Highscore: 0 by NULL", Color.WHITE, UILabel.DEFAULT_FONT);
        lblHighscore.setFontSize(16);
        
        UILabel lblPB = new UILabel(437, 220, "Your Personal Best: 0", Color.WHITE, UILabel.DEFAULT_FONT);
        lblPB.setFontSize(16);
        
        UILabel lblCoins = new UILabel(139, 285, "2000", Color.WHITE, UILabel.DEFAULT_FONT);
        lblCoins.setFontSize(16);
        
        UILabel lblNewSkin = new UILabel(260, 344, "New Ship Skin!", Color.WHITE, UILabel.DEFAULT_FONT);
        lblNewSkin.setFontSize(16);
        
        uiControls.put("btnPlayAgain", btnPlayAgain);
        uiControls.put("btnContinue", btnContinue);
        uiControls.put("lblVictoryScore", lblVictoryScore);
        uiControls.put("lblHighscore", lblHighscore);
        uiControls.put("lblPB", lblPB);
        uiControls.put("lblCoins", lblCoins);
        uiControls.put("lblNewSkin", lblNewSkin);
        
        // Pause UI
        UIButton btnResume = new UIButton(297, 215, 205, 56, Assets.images.get("PauseResume"));
        btnResume.setOnClickListener(() -> {
            paused = false;
        });
        
        UIButton btnRestart = new UIButton(297, 284, 205, 56, Assets.images.get("PauseRestart"));
        btnRestart.setOnClickListener(() -> {
            chooseLevel();
            paused = false;
        });
        
        UIButton btnExitLevel = new UIButton(297, 353, 205, 56, Assets.images.get("PauseExit"));
        btnExitLevel.setOnClickListener(() -> {
            Game.setCurrentScreen(LevelSelectScreen.getInstance());
        });
        
        uiControls.put("btnResume", btnResume);
        uiControls.put("btnRestart", btnRestart);
        uiControls.put("btnExitLevel", btnExitLevel);
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
        
        if(victory) {
            Composite orig = g.getComposite();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fadeAlpha));
            g.drawImage(Assets.images.get("VictoryScreen"), 0, 0, Game.getDisplay().getWidth(), Game.getDisplay().getHeight(), null);
            uiControls.get("btnPlayAgain").render(g);
            uiControls.get("btnContinue").render(g);
            
            // update victory score label
            UILabel lblVictoryScore = (UILabel)uiControls.get("lblVictoryScore");
            lblVictoryScore.render(g);
            lblVictoryScore.calculateDimensions(g);
            lblVictoryScore.setX(139 + (129 - lblVictoryScore.getWidth() / 2));
            
            // update highscore label
            UILabel lblHighscore = (UILabel)uiControls.get("lblHighscore");
            lblHighscore.render(g);
            lblHighscore.calculateDimensions(g);
            lblHighscore.setX(404 + (128 - lblHighscore.getWidth() / 2));
            
            // update PB label
            UILabel lblPB = (UILabel)uiControls.get("lblPB");
            lblPB.render(g);
            lblPB.calculateDimensions(g);
            lblPB.setX(404 + (128 - lblPB.getWidth() / 2));
            
            // render coin image and coins
            UILabel lblCoins = (UILabel)uiControls.get("lblCoins");
            lblCoins.render(g);
            lblCoins.calculateDimensions(g);
            
            int w = Assets.images.get("coin").getWidth();
            int h = Assets.images.get("coin").getHeight();
            int coinX = 139 + (129 - (w / 2 + (int)lblCoins.getWidth() / 2)) - w + 2;
            g.drawImage(Assets.images.get("coin"), coinX, 288, w, h, null);
            lblCoins.setX(coinX + w + 10);
            
            // render skin reward
            g.drawImage(Assets.images.get("PlayerBlue"), 185, 320, 64, 64, null);
            uiControls.get("lblNewSkin").render(g);
            
            // set back composite to original
            g.setComposite(orig);
        }
        
        if(paused) {
            g.drawImage(Assets.images.get("PauseScreen"), 0, 0, Game.getDisplay().getWidth(), Game.getDisplay().getHeight(), null);
            uiControls.get("btnResume").render(g);
            uiControls.get("btnRestart").render(g);
            uiControls.get("btnExitLevel").render(g);
        }
    }

    @Override
    public void update() {        
        level.update();
        
        if(gameOver || victory) {
            if(fadeTimer.isActivated()) {            
                if(fadeAlpha < 0.7f) {
                    fadeAlpha += 0.04f;
                }
                fadeTimer.restart();
            }
            fadeTimer.update();
            
            if(gameOver) {
                uiControls.get("btnTryAgain").update();
                uiControls.get("btnLevelSelect").update();   
            }
            
            if(victory) {
                uiControls.get("btnPlayAgain").update();
                uiControls.get("btnContinue").update();
            }
        }
        
        if(paused) {
            uiControls.get("btnResume").update();
            uiControls.get("btnRestart").update();
            uiControls.get("btnExitLevel").update();
        }
        
        if(Game.getKeyManager().isKeyPressed(KeyEvent.VK_ESCAPE)) {
            paused = !paused;
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
    
    public boolean hasVictory() {
        return victory;
    }
    
    public void setVictory() {
        if(!this.victory) {
            this.victory = true;
            fadeAlpha = 0.0f;
        }
    }
    
    public boolean isPaused() {
        return paused;
    }
    
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public void setSelectedLevel(int selectedLevel) {
        this.selectedLevel = selectedLevel;
    }
    
    public void chooseLevel() {
        switch(selectedLevel) {
            case 1:
                level = new Level1(player);
                break;
            case 2:
                level = new Level2(player);
                break;
            case 3:
                level = new Level3(player);
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
        }
    }
}