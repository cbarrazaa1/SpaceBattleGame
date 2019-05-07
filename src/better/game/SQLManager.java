/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.game;

import better.scenes.LevelSelectScreen;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anaisabelcruz
 */
public class SQLManager {
    
    /**+
     * Heroku Credentials.
     */
    private final String url = "jdbc:postgresql://ec2-54-225-116-36.compute-1.amazonaws.com:5432/dbg7b89dpc9ssc";
    private final String user = "qnsswjmuzcxgri";
    private final String password = "45fbc7fe0c89842304bd511360a7fc22ee5f9c2ee140d8cf431d6bf3bc83cd31";
        
    /**
     * Connect to PostgreSQL database
     * @return a Connection object
     * @throws java.sql.SQLException
     */
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
    /**
     * Insert statistics elements into table stats
     * @param stat
     * @return stats id
     */
    public int insertStat(Stats stat) {
        int id = 0;
        String SQL = "INSERT INTO stats(statsID, deaths, coinsCollected, enemiesKilled, bulletsShot, playTimeMin, playTimeSec)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, stat.getId());
            pstmt.setInt(2, stat.getDeaths());
            pstmt.setInt(3, stat.getCoins());
            pstmt.setInt(4, stat.getEnemiesKilled());
            pstmt.setInt(5, stat.getShots());
            pstmt.setInt(6, stat.getTimeMinutes());
            pstmt.setInt(7, stat.getTimeSeconds());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try(ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;       
    }
    /**
     * Insert elements of player into table players
     * @param player
     * @return player id
     */
    public int insertPlayer(Player player) {
        int playerID = 0;
        String SQL = "INSERT INTO players(userID, statsID, username, level, armorLvl, energyLvl, coins, skin, currBullet, palID, bulletTypes) "
                + "VALUES(?, (SELECT statsID FROM stats WHERE stats.statsID = ?), ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = connect();
               PreparedStatement pstmt = conn.prepareStatement(SQL,
               Statement.RETURN_GENERATED_KEYS)) {      
            pstmt.setInt(1, player.getId());
            pstmt.setInt(2, player.getStats().getId());
            pstmt.setString(3, player.getName());
            pstmt.setInt(4, player.getLevel());
            pstmt.setInt(6, player.getArmor());
            pstmt.setInt(7, player.getEnergyLvl());
            pstmt.setInt(8, player.getCoins());
            pstmt.setInt(9, player.getSkin());
            pstmt.setInt(10, player.getCurrBullet());
            pstmt.setInt(11, player.getSelectedPal());
            pstmt.setString(12, player.getBulletTypes());
            
            int affectedRows = pstmt.executeUpdate();
            
            // check affected rows
            if (affectedRows > 0) {
                // get ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    // moves one row from the current position
                    if(rs.next()) {
                        playerID = rs.getInt(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }    
        return playerID;
    }
    /**
     * Insert level elements into table level
     * @param planet
     * @return level id
     */
    public int insertLevel() {
        int id = 0;
        String SQL = "INSERT INTO levels"
                + "VALUES (?, ?)";
        
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS)) {
            
            for (int i = 1; i <= 9; i++) {
                pstmt.setInt(1, i);
                pstmt.setString(2, LevelSelectScreen.getInstance().planets[i - 1]);
            }
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
                } catch (SQLException ex) {
                      System.out.println(ex.getMessage());
                }
            }       
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
        return id;
    }
    /**
     * Insert highscore elements in table highsScores
     * @param levelID
     * @param player
     * @return highScore id
     */
    public int insertHighScore(int levelID, Player player) {
        int id = 0;
        String SQL = "INSERT INTO highScores"
               + "VALUES (?, ?, ?, ?)";
        
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, levelID);
            pstmt.setInt(2, player.getId());
            pstmt.setInt(3, 0); // personal best
            pstmt.setInt(4, 0); // times played
         
            int affectedRows = 0;
            
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }      
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }
    /**
     * Update statistics elements.
     * @param stat
     * @return affected rows
     */
    public int updateStats(Stats stat) {
        String SQL = "UPDATE stats "
                + "SET "
                + "deaths = ?"
                + "coinsCollected = ?"
                + "enemiesKilled = ?"
                + "bulletsShot = ?"
                + "playTimeMin = ?"
                + "playTimeSec = ?"
                + "WHERE statsID = ?";
        
        int affectedRows = 0;
        
        try (Connection conn = connect();
               PreparedStatement pstmt = conn.prepareStatement(SQL,
               Statement.RETURN_GENERATED_KEYS)) {      
            pstmt.setInt(1, stat.getDeaths());
            pstmt.setInt(2, stat.getCoins());
            pstmt.setInt(3, stat.getEnemiesKilled());
            pstmt.setInt(4, stat.getShots());
            pstmt.setInt(5, stat.getTimeMinutes());
            pstmt.setInt(6, stat.getTimeSeconds());
            pstmt.setInt(7, stat.getId());
            
            affectedRows = pstmt.executeUpdate();
                    
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedRows;    
    }
    /**
     * Updates elements of player.
     * @param player
     * @return affected rows after update
     */
    public int updatePlayer(Player player) {
        String SQL = "UPDATE players "
                + "SET "
                + "level = ?"
                + "armorLvl = ?"     
                + "energyLvl = ?"
                + "coins = ?"
                + "skin = ?"
                + "currBullet = ?"
                + "palID = ?"
                + "bulletTypes = ?"
                + "WHERE userID = ?";
        
        int affectedRows = 0;
        
        try (Connection conn = connect();
               PreparedStatement pstmt = conn.prepareStatement(SQL,
               Statement.RETURN_GENERATED_KEYS)) {      
            
            pstmt.setInt(1, player.getLevel());
            pstmt.setInt(2, player.getArmor());
            pstmt.setInt(3, player.getEnergyLvl());
            pstmt.setInt(4, player.getCoins());
            pstmt.setInt(5, player.getSkin());
            pstmt.setInt(6, player.getCurrBullet());
            pstmt.setInt(7, player.getSelectedPal());
            pstmt.setString(8, player.getBulletTypes());
            pstmt.setInt(9, player.getId());
            
            affectedRows = pstmt.executeUpdate();
                    
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedRows;
    }
    /**
     * Update high-score elements.
     * @param player
     * @return affected rows
     */
    public int updateHighScore(Player player) {
        String SQL = "UPDATE highScores "
                + "SET "
                + "personalBest = ?"
                + "timesPlayed = ?"
                + "WHERE levelID = ?"
                + "WHERE userID = ?";
        
        int affectedRows = 0;
        
        try (Connection conn = connect();
               PreparedStatement pstmt = conn.prepareStatement(SQL,
               Statement.RETURN_GENERATED_KEYS)) {      
            
            pstmt.setInt(1, 0);
            pstmt.setInt(2, 0);
            pstmt.setInt(3, player.getLevel());
            pstmt.setInt(4, player.getId());
            
            affectedRows = pstmt.executeUpdate();
                    
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedRows;
    }
}
