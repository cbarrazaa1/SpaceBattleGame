/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.levels;

import better.assets.Assets;
import better.core.Game;
import better.core.Timer;
import better.enemies.Enemy;
import better.enemies.EnemyTutorial;
import better.game.Player;
import better.ui.UIButton;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author cesar
 */

/*
    Things that have to be done in the tutorial:
    0. Explain UI (health, energy and bullet)
    1. Explain basic movement with WASD.
    2. Explain aiming and shooting.
    3. Explain killing an enemy (and that they drop coins)
    4. Explain using the dash.
    5. Explain how the dash can be used to become invincible against an enemy attack.
    6. Let player kill 10 enemies to end level.
*/
public class LevelTutorial extends Level {
    public enum TutorialState {
        Dummy,
        Intro,
        UI,
        Armor,
        Energy,
        Bullet,
        Movement,
        Aiming,
        FirstEnemy,
        Coins,
        Dashing,
        SecondEnemy,
        End,
        Victory
    };
    
    public static TutorialState State;
    private UIButton btnNext;
    private BufferedImage img;
    private boolean shouldShow;
    private float fadeAlpha = 0.9f;
    
    // Timers //
    private Timer tmrMovement;
    private boolean startMovementTimer;
    private Timer tmrAiming;
    private boolean startAimingTimer;
    private Timer tmrDashing;
    private boolean startDashingTimer;
    
    // Enemy References //
    private EnemyTutorial enemy1;
    
    public LevelTutorial(Player player) {
        super(player);
        eventListener = this;
        shouldShow = true;
        player.setAct(false);
        
        btnNext = new UIButton(154 + 314, 0, 160, 46, Assets.images.get("TutorialNext"));
        btnNext.setOnClickListener(() -> {
            if(State == TutorialState.Movement || State == TutorialState.Aiming || State == TutorialState.FirstEnemy || State == TutorialState.Dashing) {
                nextState(false);
            } else {
                nextState(true);
            }
        });
        nextState(true);
    }
    
    @Override
    public void update() {
        super.update();
        
        if(shouldShow) {
            btnNext.update();
        }
        
        // Movement Timer //
        if(tmrMovement != null) {
            if(startMovementTimer) {
                if(tmrMovement.isActivated()) {
                    player.setAct(false);
                    shouldShow = true;
                    nextState(true);
                    tmrMovement = null;
                }
                if(tmrMovement != null) {
                    tmrMovement.update();
                }
            } else {
                if(Game.getKeyManager().isKeyDown(KeyEvent.VK_W) || Game.getKeyManager().isKeyDown(KeyEvent.VK_A) ||
                   Game.getKeyManager().isKeyDown(KeyEvent.VK_D) || Game.getKeyManager().isKeyDown(KeyEvent.VK_S)) {
                    startMovementTimer = true;
                }
            }
        }
        
        // Aiming Timer //
        if(tmrAiming != null) {
            if(startAimingTimer) {
                if(tmrAiming.isActivated()) {
                    player.setAct(false);
                    tmrAiming = null;
                    enemy1 = new EnemyTutorial(368, -100, 64, 64, 0, 10, 100, player, bullets, lights, EnemyTutorial.STATE_ENEMY1);
                    enemies.add(enemy1);
                }
                if(tmrAiming != null) {
                    tmrAiming.update();
                }
            } else {
                if(Game.getMouseManager().isButtonDown(MouseEvent.BUTTON1)) {
                    startAimingTimer = true;
                }
            }
        }
        
        // Enemy1 Timer //
        if(enemy1 != null) {
            if(enemy1.getState() == EnemyTutorial.STATE_ENEMY1_FINISHED) {
                enemy1.setState(EnemyTutorial.STATE_ENEMY1_FIGHT);
                shouldShow = true;
                nextState(true);     
                enemy1.setAct(false);
            } else if(enemy1.getState() == EnemyTutorial.STATE_ENEMY1_FIGHT) {
                if(!enemy1.isAlive()) {
                    enemy1 = null;
                    shouldShow = true;
                    nextState(true);
                    player.setAct(false);
                }
            }
        }
        
        // Dashing Timer // 
        if(tmrDashing != null) {
            if(startDashingTimer) {
                if(tmrDashing.isActivated()) {
                    player.setAct(false);
                    shouldShow = true;
                    nextState(true);
                    tmrDashing = null;
                }
                if(tmrDashing != null) {
                    tmrDashing.update();
                }
            } else {
                if(Game.getKeyManager().isKeyDown(KeyEvent.VK_SPACE)) {
                    startDashingTimer = true;
                }
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
        
        // render window
        if(shouldShow) {
            Composite orig = g.getComposite();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fadeAlpha));
            g.drawImage(img, Game.getDisplay().getWidth() / 2 - img.getWidth() / 2, 100, img.getWidth(), img.getHeight(), null);
            btnNext.render(g);
            
            // pal
            float palX = Game.getDisplay().getWidth() / 2 - img.getWidth() / 2;
            float palY = 100;
            BufferedImage palImg = null;
            switch(player.getSelectedPal()) {
                case 0:
                    palX += 25;
                    palY += 13;
                    palImg = Assets.images.get("LevelSelectYoth");
                    break;
                case 1:
                    palX += 20;
                    palY += 20;
                    palImg = Assets.images.get("LevelSelectLak");
                    break;
                case 2:
                    palX += 20;
                    palY += 29;
                    palImg = Assets.images.get("LevelSelectAda");
                    break;
            }
            g.drawImage(palImg, (int)palX, (int)palY, palImg.getWidth(), palImg.getHeight(), null);
            g.setComposite(orig);
        }
    }
    
    @Override
    public void onEnemyDead(Enemy enemy) {
        
    }
    
    private void nextState(boolean change) {
        if(!change) {
            shouldShow = false;
            player.setAct(true);
            if(State == TutorialState.Movement) {
                tmrMovement = new Timer(3d);
                startMovementTimer = false;   
            } else if(State == TutorialState.Aiming) {
                tmrAiming = new Timer(3d);
                startAimingTimer = false;
            } else if(State == TutorialState.FirstEnemy) {
                enemy1.setAct(true);
            } else if(State == TutorialState.Dashing) {
                tmrDashing = new Timer(3d);
                startDashingTimer = false;
            }
            return;
        }
        
        if(State != TutorialState.Victory) {
            State = TutorialState.values()[State.ordinal() + 1];
        } else {
            
        }
        
        int index = State.ordinal();
        img = Assets.images.get("Tut" + index);
        
        float imgY = 100;
        
        switch(State) {
            case Intro:
                btnNext.setY(imgY + 121);
                break;
            case UI:
                btnNext.setY(imgY + 131);
                break;
            case Armor:
                btnNext.setY(imgY + 123);
                break;
            case Energy:
                btnNext.setY(imgY + 123);
                break;
            case Bullet:
                btnNext.setY(imgY + 123);
                break;
            case Movement:
                btnNext.setY(imgY + 152);
                break;
            case Aiming:
                btnNext.setY(imgY + 142);
                break;
            case FirstEnemy:
                btnNext.setY(imgY + 122);
                break;
            case Coins:
                btnNext.setY(imgY + 122);
                break;
            case Dashing:
                btnNext.setY(imgY + 122);
                break;
            case SecondEnemy:
                btnNext.setY(imgY + 122);
                break;
            case End:
                btnNext.setY(imgY + 122);
                break;
            case Victory:
                btnNext.setY(imgY + 122);
                break;
        }
    }
}
