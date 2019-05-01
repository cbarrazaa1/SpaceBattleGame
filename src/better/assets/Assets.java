package better.assets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

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
    public static HashMap<String, BufferedImage> images;
    
    /**
     * Audio that will be used by the game.
     */

    
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
    }
}