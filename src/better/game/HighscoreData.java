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
public class HighscoreData {
    private int personalBest;
    private int timesPlayed;
    
    public HighscoreData(int personalBest, int timesPlayed) {
        this.personalBest = personalBest;
        this.timesPlayed = timesPlayed;
    }
    
    public int getPersonalBest() {
        return personalBest;
    }
    
    public void setPersonalBest(int personalBest) {
        this.personalBest = personalBest;
    }
    
    public int getTimesPlayed() {
        return timesPlayed;
    }
    
    public void setTimesPlayed(int timesPlayed) {
        this.timesPlayed = timesPlayed;
    }
}
