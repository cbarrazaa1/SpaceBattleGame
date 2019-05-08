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
        mainMenu = TinySound.loadMusic("MainMenu.ogg");
//        victory = TinySound.loadMusic("Victory.ogg");
//        gameOver = TinySound.loadMusic("GameOver.ogg");
//        level1 = TinySound.loadMusic("Level1.ogg", true);
//        level2 = TinySound.loadMusic("Level2.ogg", true);
//        level3 = TinySound.loadMusic("Level3.ogg", true);
//        level4 = TinySound.loadMusic("Level4.ogg", true);
//        level5 = TinySound.loadMusic("Level5.ogg", true);
//        level6 = level2;
//        level7 = TinySound.loadMusic("Level7.ogg", true);
//        level8 = TinySound.loadMusic("Level8.ogg", true);
//        level9 = TinySound.loadMusic("Level9.ogg", true);
    }
    
    public static void playMusic(Music music) {
        if(music == currMusic) {
            return;
        }
        
        if(currMusic != null) {
            currMusic.stop();
        }
        currMusic = music;
        currMusic.play(true);
    }
}