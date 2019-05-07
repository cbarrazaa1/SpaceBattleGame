package better.core;

import better.assets.Assets;
import better.input.MouseManager;
import better.input.KeyManager;
import better.scenes.MainMenuScreen;
import better.scenes.Screen;
import better.game.OnClickListener;
import better.game.Player;
import better.ui.UIButton;
import better.ui.UILabel;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import kuusisto.tinysound.TinySound;

/**
 * Game Handles everything that the game needs to function correctly.
 *
 * @author César Barraza A01176786 
 * @author Isabel Cruz A01138741
 * Date 09/March/2019
 * @version 1.0
 */
public class Game implements Runnable {
    private static Display display; // the game render window
    private static KeyManager keyManager; // the keyboard manager
    private static MouseManager mouseManager; // the mouse manager
    private static Screen currentScreen; // the current screen of the game
    
    private static Thread thread; // the main game running thread
    private static boolean isRunning; // denotes whether the game is running or not
    
    private int fps;
    private int maxFps = 0;
    private int minFps = 60;
    private int fpsCounter = 0;
    private static Timer fpsTimer; // timer for fps
    private Timer screenTimer; // timer for screen fading
    private static boolean isChangingScreen; // flag to determine if screen is fading or not
    private static Screen nextScreen; // determines screen that will appear after fading
    private float fadeAlpha; // determines how much of the fade is visible
    private static float fadeDelta; // determines how fast the screen fades

    /**
     * Initializes the game object with the desired display properties.
     *
     * @param title
     * @param width
     * @param height
     */
    public Game(String title, int width, int height) {
        this.isRunning = false;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
        init(title, width, height);
        fpsTimer = new Timer(1d);
    }
    /**
     * @return the keyManager
     */
    public static KeyManager getKeyManager() {
        return keyManager;
    }

    /**
     * @return the mouseManager
     */
    public static MouseManager getMouseManager() {
        return mouseManager;
    }
    
    /**
     * @return the display
     */
    public static Display getDisplay() {
        return display;
    }

    public static Screen getCurrentScreen() {
        return currentScreen;
    }
    
    public static void setCurrentScreen(Screen screen) {
        isChangingScreen = true;
        nextScreen = screen;
        fadeDelta = 0.1f;
    }
    
    /**
     * Starts all initializations needed to start the game.
     */
    private void init(String title, int width, int height) {
        // create the display
        display = new Display(title, width, height);
        display.getWindow().addKeyListener(getKeyManager());
        display.getWindow().addMouseListener(getMouseManager());
        display.getWindow().addMouseMotionListener(getMouseManager());
        display.getCanvas().addMouseListener(getMouseManager());
        display.getCanvas().addMouseMotionListener(getMouseManager());

        // initialize the game's assets
        TinySound.init();
        Assets.init();

        // set initial screen
        currentScreen = MainMenuScreen.getInstance();
        getCurrentScreen().init();
        screenTimer = new Timer(0.03d);
        isChangingScreen = false;
        fadeAlpha = 0.0f;
}

    /**
     * Starts the main game thread.
     */
    public synchronized void start() {
        if (!isRunning) {
            isRunning = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * Stops the game thread execution.
     */
    public static synchronized void stop() {
        if (isRunning) {
            isRunning = false;
            try {
                thread.join();
                TinySound.shutdown();
                System.out.println("Closed");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
     
    /**
     * Updates the game every frame.
     */
    private void update() {
        // update current screen
        getCurrentScreen().update();
        
        // update input
        getKeyManager().update();
        getMouseManager().update();
    }

    /**
     * Renders the game every frame.
     */
    private void render() {
        BufferStrategy bs = getDisplay().getCanvas().getBufferStrategy();

        // if there's no buffer strategy yet, create it
        if (bs == null) {
            getDisplay().getCanvas().createBufferStrategy(3);
        } else {
            // clear screen
            Graphics2D g = (Graphics2D)bs.getDrawGraphics();
            getDisplay().clear(g, new Color(13f / 255.0f, 0f, 83f / 255f, 1.0f));
            
            // render current screen
            getCurrentScreen().render(g);
            
            // render fading if we are changing screen
            if(isChangingScreen) {
                screenTimer.update();
                if(screenTimer.isActivated()) {
                    fadeAlpha += fadeDelta;
                    
                    // if scren is completely black, change screen
                    if(fadeAlpha >= 1.0f) {
                        fadeAlpha = 1.0f;
                        nextScreen.init();
                        currentScreen = nextScreen;
                        fadeDelta /= -2;
                    }
                    
                    // if screen is completely clear after fading, we are no longer fading
                    if(fadeAlpha < 0.0f) {
                        fadeAlpha = 0.0f;
                        isChangingScreen = false;
                    }
                    screenTimer.restart();
                }
                
                Composite orig = g.getComposite();
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fadeAlpha));
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getDisplay().getWidth(), getDisplay().getHeight());
                g.setComposite(orig);
            }
            
            // render pointer
            g.drawImage(Assets.images.get("pointer"), (int)getMouseManager().getX(), (int)getMouseManager().getY(), 17, 22, null);
            
            // actually render the whole scene
            bs.show();
            g.dispose();
            
            fps++;
            if(fpsTimer.isActivated()) {
                if(fps > maxFps) {
                    maxFps = fps;
                }
                if(fps < minFps) {
                    minFps = fps;
                }
                System.out.println("[" + fpsCounter++ + "] FPS: " + fps + ", Max: " + maxFps + ", Min: " + minFps);
                fps = 0;
                fpsTimer.restart();
            }
            fpsTimer.update();
        }
    }
    
    /**
     * The main game loop.
     */
    @Override
    public void run() {
        // set up timing constants
        final int maxFPS = 60;
        final double timeTick = 1000000000 / maxFPS;

        // set up timing variables
        double delta = 0.0d;
        long now = 0;
        long lastTime = System.nanoTime();

        // start game loop
        while (isRunning) {
            // calculate delta
            now = System.nanoTime();
            delta += (now - lastTime) / timeTick;
            lastTime = now;

            // make sure game always plays at desired fps
            if (delta >= 1.0d) {
                update();
                render();
                delta--;
            }
        }
    }
}
