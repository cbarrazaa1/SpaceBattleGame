package better.assets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * ImageLoader
 * 
 * Simplifies loading local assets for the game.
 * @author César Barraza A01176786
 * Date 30/Jan/2019
 * @version 1.0
 */
public class ImageLoader {
    /**
     * Attempts to load the image specified at the path.
     * @param path the relative location of the image
     * @return the loaded image
     */
    public static BufferedImage loadImage(String path) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(ImageLoader.class.getResource(path));
        } catch(IOException e) {
            System.out.println("Error loading image " + path + e.toString());
            System.exit(1);
        }
        return img;
    }
}
