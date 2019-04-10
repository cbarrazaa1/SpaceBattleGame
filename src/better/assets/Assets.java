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
    public static BufferedImage mainMenuBackground;
    public static BufferedImage background2;
    public static BufferedImage mercury;
    public static BufferedImage venus;
    public static BufferedImage earth;
    public static BufferedImage mars;
    public static BufferedImage jupiter;
    public static BufferedImage saturn;
    public static BufferedImage uranus;
    public static BufferedImage neptune;
    public static BufferedImage sun;
    public static BufferedImage mercuryBlack;
    public static BufferedImage venusBlack;
    public static BufferedImage earthBlack;
    public static BufferedImage marsBlack;
    public static BufferedImage jupiterBlack;
    public static BufferedImage saturnBlack;
    public static BufferedImage uranusBlack;
    public static BufferedImage neptuneBlack;
    public static BufferedImage sunBlack;
    public static BufferedImage mainPlayer;
    
    /**
     * Audio that will be used by the game.
     */

    
    /**
     * Loads all the assets that the game needs.
     */
    public static void init() {
        defaultUIButton = ImageLoader.loadImage("/images/uibutton.png");
        UIButtonGradient = ImageLoader.loadImage("/images/uigradient.png");
        mainMenuBackground = ImageLoader.loadImage("/images/mainMenuBackground.png");
        background2 = ImageLoader.loadImage("/images/background2.png");
        mercury = ImageLoader.loadImage("/images/mercury.png");
        venus = ImageLoader.loadImage("/images/venus.png");
        earth = ImageLoader.loadImage("/images/earth.png");
        mars = ImageLoader.loadImage("/images/mars.png");
        jupiter = ImageLoader.loadImage("/images/jupiter.png");
        saturn = ImageLoader.loadImage("/images/saturn.png");
        uranus = ImageLoader.loadImage("/images/uranus.png");
        neptune = ImageLoader.loadImage("/images/neptune.png");
        sun = ImageLoader.loadImage("/images/sun.png");
        mercuryBlack = ImageLoader.loadImage("/images/mercuryblack.png");
        venusBlack = ImageLoader.loadImage("/images/venusblack.png");
        earthBlack = ImageLoader.loadImage("/images/earthblack.png");
        marsBlack = ImageLoader.loadImage("/images/marsblack.png");
        jupiterBlack = ImageLoader.loadImage("/images/jupiterblack.png");
        saturnBlack = ImageLoader.loadImage("/images/saturnblack.png");
        uranusBlack = ImageLoader.loadImage("/images/uranusblack.png");
        neptuneBlack = ImageLoader.loadImage("/images/neptuneblack.png");
        sunBlack = ImageLoader.loadImage("/images/sunblack.png");
        mainPlayer = ImageLoader.loadImage("/images/SpaceShipv1.png");
    }
}