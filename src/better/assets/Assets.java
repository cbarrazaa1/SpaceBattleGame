package better.assets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import kuusisto.tinysound.Music;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

/**
 * Assets
 * 
 * Helper class to manage all the assets that the game will use.
 * @author César Barraza A01176786
 * @author Isabel Cruz A01138741
 * Date 09/March/2019
 * @version 1.0
 */
public class Assets {
    public enum MusicType {
        MainMenu,
        Victory,
        GameOver,
        Level1,
        Level2,
        Level3,
        Level4,
        Level5,
        Level6,
        Level7,
        Level8,
        Level9
    }
    /**
     * Images that will be used by the game.
     */
    public static HashMap<String, BufferedImage> images;
    
    /**
     * Music 
     */
    public static Music mainMenu;
    public static Music victory;
    public static Music gameOver;
    public static Music level1;
    public static Music level2;
    public static Music level3;
    public static Music level4;
    public static Music level5;
    public static Music level6;
    public static Music level7;
    public static Music level8;
    public static Music level9;
    public static Music currMusic;
    
    /**
     * Sounds
     */
    public static Sound playerShoot;
    public static Sound enemyShoot;
    public static Sound damage;
    public static Sound enemyDie;
    public static Sound powerup;
    public static Sound dash;
    public static Sound buy;
    
    /**
     * Loads all the assets that the game needs.
     * @throws java.net.URISyntaxException
     */
    public static void init() {
        images = new HashMap<>();
        
        try {
            File dir = new File("./images/");
            File[] files = dir.listFiles();
            if(files != null) {
                for(File f : files) {
                    String name = f.getName().substring(0, f.getName().indexOf("."));
                    images.put(name, ImageIO.read(f));
                }
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        
        // Sounds
        playerShoot = TinySound.loadSound("playerShoot.wav");
        enemyShoot = TinySound.loadSound("enemyShoot.wav");
        damage = TinySound.loadSound("damage.wav");
        enemyDie = TinySound.loadSound("enemyDie.wav");
        powerup = TinySound.loadSound("powerup.wav");
        dash = TinySound.loadSound("dash.wav");
        buy = TinySound.loadSound("buy.wav");
    }
    
    public static void loadMusic(MusicType music) {
        if(music == MusicType.MainMenu) {
            currMusic = TinySound.loadMusic("MainMenu.ogg");
        } else if(music == MusicType.Victory) {
            currMusic = TinySound.loadMusic("Victory.ogg");
        } else if(music == MusicType.GameOver) {
            currMusic = TinySound.loadMusic("GameOver.ogg");
        } else if(music == MusicType.Level1) {
            currMusic = TinySound.loadMusic("Level1.ogg");
        } else if(music == MusicType.Level2) {
            currMusic = TinySound.loadMusic("Level2.ogg");
        } else if(music == MusicType.Level3) {
            currMusic = TinySound.loadMusic("Level3.ogg");
        } else if(music == MusicType.Level4) {
            currMusic = TinySound.loadMusic("Level4.ogg");
        } else if(music == MusicType.Level5) {
            currMusic = TinySound.loadMusic("Level5.ogg");
        } else if(music == MusicType.Level6) {
            currMusic = TinySound.loadMusic("Level6.ogg");
        } else if(music == MusicType.Level7) {
            currMusic = TinySound.loadMusic("Level7.ogg");
        } else if(music == MusicType.Level8) {
            currMusic = TinySound.loadMusic("Level8.ogg");
        } else if(music == MusicType.Level9) {
            currMusic = TinySound.loadMusic("Level9.ogg");
        }
        currMusic.play(true);
    }
    
    public static void unloadMusic() {
        if(currMusic != null) {
            currMusic.stop();
        }
    }
}