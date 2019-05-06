/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.game;

/**
 *
 * @author Cesar Barraza
 */
public class Stats {
    private int id;
    private int timeSeconds;
    private int timeMinutes;
    private int deaths;
    private int shots;
    private int enemiesKilled;
    private int coins;
    
    public Stats(int id, int timeSeconds, int timeMinutes, int deaths, int shots, int enemiesKilled, int coins) {
        this.id = id;
        this.timeSeconds = timeSeconds;
        this.timeMinutes = timeMinutes;
        this.deaths = deaths;
        this.shots = shots;
        this.enemiesKilled = enemiesKilled;
        this.coins = coins;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimeSeconds() {
        return timeSeconds;
    }

    public void setTimeSeconds(int timeSeconds) {
        this.timeSeconds = timeSeconds;
        if(this.timeSeconds == 60) {
            timeMinutes++;
            this.timeSeconds = 0;
        }
        
    }

    public int getTimeMinutes() {
        return timeMinutes;
    }

    public void setTimeMinutes(int timeMinutes) {
        this.timeMinutes = timeMinutes;
    }

    public String getTimePlayed() {
        String res = "";
        
        if(timeMinutes > 9) {
            res += String.valueOf(timeMinutes);
        } else {
            res += "0" + timeMinutes;
        }
        
        res += ":";
        
        if(timeSeconds > 9) {
            res += String.valueOf(timeSeconds);
        } else {
            res += "0" + timeSeconds;
        }
        
        return res;
    }
    
    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getShots() {
        return shots;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }

    public int getEnemiesKilled() {
        return enemiesKilled;
    }

    public void setEnemiesKilled(int enemiesKilled) {
        this.enemiesKilled = enemiesKilled;
    }
    
    public int getCoins() {
        return coins;
    }
    
    public void setCoins(int coins) {
        this.coins = coins;
    }
    
}
