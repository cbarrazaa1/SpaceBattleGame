package better.assets;

import better.core.Timer;
import java.awt.image.BufferedImage;

/**
 * Animation
 * 
 * Helper class to manage animations of the game.
 * @author César Barraza A01176786
 * @author Isabel Cruz A01138741
 * Date 09/March/2019
 * @version 1.0
 */
public class Animation {
    private BufferedImage image;
    private int width;
    private int height;
    private Timer timer;
    private double speed;
    private int frame;
    private int maxFrames;
    
    /**
     * Class constructor
     * @param image
     * @param width
     * @param height
     * @param speed
     * @param maxFrames 
     */
    public Animation(BufferedImage image, int width, int height, double speed, int maxFrames) {
        this.image = image;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.frame = 0;
        this.timer = new Timer(speed);
        this.maxFrames = maxFrames;
    }
    /**
     * Getter of the animation's width
     * @return width
     */
    public int getWidth() {
        return width;
    }
    /**
     * Getter of the animation's height
     * @return height
     */
    public int getHeight() {
        return height;
    }
    /**
     * Getter of the animation's speed
     * @return speed
     */
    public double getSpeed() {
        return speed;
    }
    /**
     * Setter of animation's speed.
     * @param speed 
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    /**
     * Getter of the animation's frame
     * @return frame
     */
    public int getFrame() {
        return frame;
    }
    /**
     * Setter of the animation's frame
     * @param frame 
     */
    public void setFrame(int frame) {
        this.frame = frame;
    }
    /**
     * Updates the animation.
     */
    public void update() {
        timer.update();
        if(timer.isActivated()) {
            frame++;
            if(frame == maxFrames) {
                frame = 0;
            }
            timer.restart();
        }
    }
    /**
     *
     * @param column
     * @return Image of the frame.
     */
    public BufferedImage getImageFrame(int column) {
        return image.getSubimage(width * column, 0, width, height);
    }
    /**
     * Getter of the current image of the frame.
     * @return current image of the frame
     */
    public BufferedImage getCurrentImageFrame() {
        return getImageFrame(frame);
    }
}
