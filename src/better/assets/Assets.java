package better.assets;

import java.awt.image.BufferedImage;

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
    /**
     * Images that will be used by the game.
     */
    public static BufferedImage defaultUIButton;
    public static BufferedImage UIButtonGradient;
    
    /**
     * Audio that will be used by the game.
     */

    
    /**
     * Loads all the assets that the game needs.
     */
    public static void init() {
        defaultUIButton = ImageLoader.loadImage("/images/uibutton.png");
        UIButtonGradient = ImageLoader.loadImage("/images/uigradient.png");
    }
}