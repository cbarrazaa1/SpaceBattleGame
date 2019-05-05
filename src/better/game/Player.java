/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.game;

import better.bullets.Bullet;
import better.game.GameObject;
import java.awt.Graphics2D;
import better.assets.Assets;
import better.core.Game;
import better.core.Timer;
import java.awt.Color;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_SPACE;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
/**
 *
 * @author rogel
 */

public class Player extends GameObject {    
    // Database Data //
    private int id;
    private String name;
    private int armorLvl;
    private int energyLvl;
    private int coins;
    private int level;
    private int skin;
    private int currBullet;
    private int selectedPal;
    private String bulletTypes;
    
    // In-Game Data //
    private double theta;
    private boolean shooting; // to check if the player has just shot
    private boolean dashing;
    private Timer shotTimer; 
    private Timer dashTimer;
    private ArrayList<Bullet> bullets; // list for the player shots
    private ArrayList<Light2D> lights;
    private int speed;
    private int armor;
    private int maxArmor;
    private int energy;
    private int maxEnergy;
    private int currLevel;
    private int selectedBullet;
    
    private Timer energyTimer; // timer for energy regeneration
    
    public Player(float x, float y, float width, float height, int armorLvl, int energyLvl) {
        super(x, y, width, height);
        //theta = 0;
        this.shooting = false;
        this.speed = 3;
        this.dashing = false;
        
        this.armorLvl = armorLvl;
        calculateMaxArmor();
        
        this.energyLvl = energyLvl;
        calculateMaxEnergy();
        
        energyTimer = new Timer(0.1);
        shotTimer = new Timer(0.2);
        dashTimer = new Timer(0.1);   
        coins = 15000;
        level = 5;
        currLevel = 0;
        skin = 0;
        currBullet = -1;
        selectedPal = 0;
        bulletTypes = "";
        selectedBullet = -1;
    }

    @Override
    public void render(Graphics2D g) {
        AffineTransform orig = g.getTransform();
        g.translate(getX(), getY());
        g.rotate(theta, getWidth() / 2, getHeight() / 2);
        if (!isDashing()){
            g.drawImage(getSkinImg(skin), 0, 0, (int)(getWidth()), (int)(getHeight()), null);
        }else{
            // Should be a dashing image
            g.drawImage(getSkinImg(skin), 0, 0, (int)(getWidth()), (int)(getHeight()), null);
        }
        g.setTransform(orig);    
    }
    
    @Override
    public void update() {
        if(getArmor() <= 0) {
            return;
        }
        
        // delta X and Y are calculated
        double deltaX = Game.getMouseManager().getX() - ( x + getHeight() / 2);
        double deltaY = Game.getMouseManager().getY() - ( y + getWidth() / 2);
        
        // theta is calculated
        theta = Math.atan2(deltaY, deltaX);
        theta += Math.PI / 2;
        
        // this controls the movement of the player
        if (Game.getKeyManager().isKeyDown(KeyEvent.VK_W)){
            setY(getY() - getSpeed());
        }
        if (Game.getKeyManager().isKeyDown(KeyEvent.VK_S)){
            setY(getY() + getSpeed());
        }
        if (Game.getKeyManager().isKeyDown(KeyEvent.VK_A)){
            setX(getX() - getSpeed());
        }
        if (Game.getKeyManager().isKeyDown(KeyEvent.VK_D)){
            setX(getX() + getSpeed());
        }
        
        // this controls the shots of the player
        if (Game.getMouseManager().isButtonDown(MouseEvent.BUTTON1)&& !isShooting()) {
            boolean didShoot = true;
            boolean autoRestart = true;
            
            if(selectedBullet == -1) {
                bullets.add(new Bullet(getX() + getWidth() / 2, getY() + getHeight() / 2, 7, 19, 10,
                        theta - Math.PI, 10, Assets.images.get("BulletNormal"), Bullet.BULLET_TYPE_PLAYER, new Color(215, 234, 209), lights)); 
            } else if(currBullet == Bullet.BULLET_DOUBLE_SHOT) {
                if(energy > 2) {
                    float xF = (float)Math.cos(theta) * 10;
                    float yF = (float)Math.sin(theta) * 10;

                    bullets.add(new Bullet(getX() + getWidth() / 2 + xF, getY() + getHeight() / 2 + yF, 7, 19, 10,
                            theta - Math.PI, 10, Assets.images.get("DoubleShot"), Bullet.BULLET_TYPE_PLAYER, Color.GREEN, lights)); 
                    bullets.add(new Bullet(getX() + getWidth() / 2 - xF, getY() + getHeight() / 2 - yF, 7, 19, 10,
                            theta - Math.PI, 10, Assets.images.get("DoubleShot"), Bullet.BULLET_TYPE_PLAYER, Color.GREEN, lights));
                    energy -= 2;   
                } else {
                    didShoot = false;
                }
            } else if(selectedBullet == Bullet.BULLET_HEAVY_SHOT) {
                bullets.add(new Bullet(getX() + getWidth() / 2, getY() + getHeight() / 2, 7, 19, 25 + (energy / 3),
                        theta - Math.PI, 5, Assets.images.get("HeavyShot"), Bullet.BULLET_TYPE_PLAYER, new Color(170, 67, 10), lights)); 
                autoRestart = false;
                shotTimer.restart(0.7);
            } else if(selectedBullet == Bullet.BULLET_PROTON_SHOT) {
                if(energy > 10) {
                    bullets.add(new Bullet(getX() + getWidth() / 2, getY() + getHeight() / 2, 14, 13, 20,
                            theta - Math.PI, 11, Assets.images.get("ProtonShot"), Bullet.BULLET_TYPE_PLAYER_PROTON, new Color(39, 171, 205), lights)); 
                    autoRestart = false;
                    shotTimer.restart(0.5);
                    energy -= 10; 
                } else {
                    didShoot = false;
                }
            } else if(selectedBullet == Bullet.BULLET_TRIPLE_SHOT) {
                if(energy > 4) {
                    float xF = (float)Math.cos(theta) * 13;
                    float yF = (float)Math.sin(theta) * 13;

                    bullets.add(new Bullet(getX() + getWidth() / 2 + xF, getY() + getHeight() / 2 + yF, 7, 19, 10,
                            theta - Math.PI, 10, Assets.images.get("TripleShot"), Bullet.BULLET_TYPE_PLAYER, new Color(167, 20, 173), lights)); 
                    bullets.add(new Bullet(getX() + getWidth() / 2, getY() + getHeight() / 2, 7, 19, 10,
                            theta - Math.PI, 10, Assets.images.get("TripleShot"), Bullet.BULLET_TYPE_PLAYER, new Color(167, 20, 173), lights)); 
                    bullets.add(new Bullet(getX() + getWidth() / 2 - xF, getY() + getHeight() / 2 - yF, 7, 19, 10,
                            theta - Math.PI, 10, Assets.images.get("TripleShot"), Bullet.BULLET_TYPE_PLAYER, new Color(167, 20, 173), lights));
                    energy -= 4;   
                } else {
                    didShoot = false;
                }
            }
     
            if(didShoot) {
                Assets.playerShoot.play();
                setShooting(true);   
            }
            if(autoRestart) {
                shotTimer.restart(0.2);
            }
        }
        // checks if the player can shoot again
        if (shotTimer.isActivated()){
            setShooting(false);
        }
        
        // dash mecanic version 1
        if (Game.getKeyManager().isKeyPressed(VK_SPACE) && !dashing && getEnergy() >= 15){
            speed = 25;
            dashTimer.restart();
            setDashing(true);
            setEnergy(getEnergy() - (19 + energyLvl));
        }
        if(dashTimer.isActivated()){
            speed = 3;
            setDashing(false);
            if (energyTimer.isActivated()){
                if(!(isShooting() && (selectedBullet == Bullet.BULLET_DOUBLE_SHOT || selectedBullet == Bullet.BULLET_TRIPLE_SHOT || selectedBullet == Bullet.BULLET_PROTON_SHOT))) {
                    int regenRate = 1 + energyLvl / 4;
                    
                    // adding energy when not dashing
                    setEnergy(getEnergy() + regenRate);                    
                }

                energyTimer.restart(0.15);
                if (getEnergy() >= maxEnergy){
                    setEnergy(maxEnergy);
                }
            }
            energyTimer.update();
        }
        
        // actualizes shot and dash timer
        shotTimer.update();
        dashTimer.update();
        
        checkColision();
    }
    
    private void checkColision(){
        //check for out of bounds collision
        if (getX() >= Game.getDisplay().getWidth() - width){
            setX(Game.getDisplay().getWidth() - width);
        }
        else if (getX() <= 0){
            setX(0);
        }
        if (getY() >= Game.getDisplay().getHeight() - height){
            setY(Game.getDisplay().getHeight() - height);
        }
        else if (getY() <= 0){
            setY(0);
        }
        
        if(currLevel == 3){
            y = Game.getDisplay().getHeight() - height - 10;
        }
    }
    
   public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxArmor() {
        return maxArmor;
    }
    
    public int getArmorLvl() {
        return armorLvl;
    }

    public void setArmorLvl(int armorLvl) {
        this.armorLvl = armorLvl;
        calculateMaxArmor();
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }
    
    public int getEnergyLvl() {
        return energyLvl;
    }

    public void setEnergyLvl(int energyLvl) {
        this.energyLvl = energyLvl;
        calculateMaxEnergy();
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
    public int getCurrLevel() {
        return currLevel;
    }

    public void setCurrLevel(int currLevel) {
        this.currLevel = currLevel;
    }
    
    public int getSkin() {
        return skin;
    }

    public void setSkin(int skin) {
        this.skin = skin;
    }

    public int getCurrBullet() {
        return currBullet;
    }

    public void setCurrBullet(int currBullet) {
        this.currBullet = currBullet;
    }
    
    public int getSelectedBullet() {
        return selectedBullet;
    }
    
    public void setSelectedBullet(int selected) {
        this.selectedBullet = selected;
    }
    
    public void switchSelectedBullet() {
        if(currBullet == -1) {
            return;
        }
        
        if(selectedBullet == -1) {
            selectedBullet = currBullet;
        } else {
            selectedBullet = -1;
        }
    }

    public int getSelectedPal() {
        return selectedPal;
    }

    public void setSelectedPal(int selectedPal) {
        this.selectedPal = selectedPal;
    }

    public String getBulletTypes() {
        return bulletTypes;
    }

    public void setBulletTypes(String bulletTypes) {
        this.bulletTypes = bulletTypes;
    }
    
    public void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }
    
    public void setLights(ArrayList<Light2D> lights) {
        this.lights = lights;
    }
    
    /**
     * Returns the player speed
     * @return speed
     */
    public int getSpeed(){
        return speed;
    }
    /**
     * sets the player speed
     * @param speed 
     */
    public void setSpeed(int speed){
        this.speed = speed;
    }
    
    /**
     * checks if dashing
     * @return dashing
     */
    public boolean isDashing(){
        return dashing;
    }
    /**
     * set if dashing
     * @param dashing 
     */
    public void setDashing(boolean dashing){
        this.dashing = dashing;
    }
    
    /**
     * 
     * @return shooting
     */
    public boolean isShooting(){
        return shooting;
    }
    /**
     * setter for shooting
     * @param shooting 
     */
    public void setShooting(boolean shooting){
        this.shooting = shooting;
    }
    
    /**
     * sets player health
     * @param health 
     */
    public void setArmor(int armor){
        if(armor <= 0) {
            armor = 0;
        }
        if(armor >= maxArmor) {
            armor = maxArmor;
        }
        this.armor = armor;
    }
    
    /**
     * returns player health
     * @return health
     */
    public int getArmor(){
        return armor;
    }
    
    /**
     * sets player energy
     * @param energy 
     */
    public void setEnergy(int energy){
        if(energy <= 0) {
            energy = 0;
        }
        this.energy = energy;
    }
    
    /**
     * returns the player energy
     * @return energy
     */
    public int getEnergy(){
        return energy;
    }
    
    public void recoverStats() {
        armor = maxArmor;
        energy = maxEnergy;
    }
    
    public void calculateMaxArmor() {
        maxArmor = 80 + armorLvl * 20;
        armor = maxArmor;
    }
    
    public void calculateMaxEnergy() {
        maxEnergy = 30 + energyLvl * 20;
        energy = maxEnergy;
    }
    
    @Override
    public Rectangle2D.Float getRect() {
        return new Rectangle2D.Float(x + 16, y + 16, 32, 32);
    }
    
    public HashSet<Integer> bulletsToHashSet() {
        if(bulletTypes.length() == 0) {
            return new HashSet<>();
        }
        
        String[] s = bulletTypes.split(",");
        HashSet<Integer> res = new HashSet<>();
        for(int i = 0; i < s.length; i++) {
            res.add(Integer.parseInt(s[i]));
        }
        
        return res;
    }
    
    public void hashSetToBullets(HashSet<Integer> set) {
        bulletTypes = "";
        for(Integer i : set) {
            bulletTypes += String.valueOf(i) + ",";
        }
    }
    
    public BufferedImage getSkinImg(int skin) {
        if(skin == -1) {
            skin = this.skin;
        }
        
        BufferedImage img = Assets.images.get("PlayerDefault");
        switch(skin) {
            case 1:
                img = Assets.images.get("PlayerBlue");
                break;
            case 2:
                img = Assets.images.get("PlayerCyan");
                break;
            case 3:
                img = Assets.images.get("PlayerYellow");
                break;
            case 4:
                img = Assets.images.get("PlayerOrange");
                break;
            case 5:
                img = Assets.images.get("PlayerRed");
                break;
            case 6:
                img = Assets.images.get("PlayerGreen");
                break;
            case 7:
                img = Assets.images.get("PlayerPurple");
                break;
            case 8:
                img = Assets.images.get("PlayerBrown");
                break;
            case 9:
                img = Assets.images.get("PlayerPremium");
                break;
        }
        
        return img;
    }
    
    @Override
    public void onClick() { }

    @Override
    public void mouseEnter() { }

    @Override
    public void mouseLeave() { }

    @Override
    public void mouseDown() { }

    @Override
    public void mouseUp() { }
}
