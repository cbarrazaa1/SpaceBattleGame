/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package better.game;

import better.scenes.LevelSelectScreen;
import java.sql.*;

/**
 *
 * @author anaisabelcruz
 */
public class SQLManager {
    
    /**+
     * Heroku Credentials.
     */
    private static final String url = "jdbc:postgresql://ec2-54-225-116-36.compute-1.amazonaws.com:5432/dbg7b89dpc9ssc";
    private static final String user = "qnsswjmuzcxgri";
    private static final String password = "45fbc7fe0c89842304bd511360a7fc22ee5f9c2ee140d8cf431d6bf3bc83cd31";
        
    /**
     * Connect to PostgreSQL database
     * @return a Connection object
     * @throws java.sql.SQLException
     */
    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
    /**
     * 
     * @param statsID
     * @return 
     */
    public static Stats selectStats(int statsID) {
        String SQL = "SELECT * FROM stats WHERE statsID = " + statsID;
        Stats stats = null;
        int deaths = 0;
        int coinsCollected = 0;
        int enemiesKilled = 0;
        int bulletsShot = 0;
        int playTimeMin = 0;
        int playTimeSec = 0;
            
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
                        
            ResultSet rs = stmt.executeQuery(SQL);
            
            while (rs.next()) {
                deaths = rs.getInt(1);
                coinsCollected = rs.getInt(2);
                enemiesKilled = rs.getInt(3);
                bulletsShot = rs.getInt(4);
                playTimeMin = rs.getInt(5);
                playTimeSec = rs.getInt(6);
            }
            
            rs.close();
            stmt.close();
            
            stats = new Stats(statsID, 0, 0, 0, 0, 0, 0);
            stats.setDeaths(deaths);
            stats.setCoins(coinsCollected);
            stats.setEnemiesKilled(enemiesKilled);
            stats.setShots(bulletsShot);
            stats.setTimeMinutes(playTimeMin);
            stats.setTimeSeconds(playTimeSec);
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return stats;
    }
    /**
     * 
     * @param userID
     * @return 
     */
    public static Player selectPlayer(int userID){
        String SQL = "SELECT * FROM players WHERE userID = " + userID;
        Player player = null;
                
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
                        
            ResultSet rs = stmt.executeQuery(SQL);
            
            int statsID = 0;
            String username = "";
            int lvl = 0;
            int armorLvl = 0;
            int energyLvl = 0;
            int coins = 0;
            int skin = 0;
            int currBullet = 0;
            int palID = 0;
            String bulletType = "";
            
            while (rs.next()) {
                statsID = rs.getInt(1);
                username = rs.getString(2);
                lvl = rs.getInt(3);
                armorLvl = rs.getInt(4);
                energyLvl = rs.getInt(5);
                coins = rs.getInt(6);
                skin = rs.getInt(7);
                currBullet = rs.getInt(8);
                palID = rs.getInt(9);
                bulletType = rs.getString(10);
            }
            
            rs.close();
            stmt.close();
            
            player = new Player (0, 0, 64, 64, armorLvl, energyLvl);
            player.setStats(selectStats(statsID));
            player.setName(username);
            player.setCoins(coins);
            player.setLevel(lvl);
            player.setSkin(skin);
            player.setCurrBullet(currBullet);
            player.setSelectedPal(palID);
            player.setBulletTypes(bulletType);
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return player;
    }
    /**
     * Insert statistics elements into table stats
     * @param stat
     * @return player id
     */
    public static int insertStats(Player player) {
        int id = 0;
        Stats stat = player.getStats();
        String SQL = "INSERT INTO stats(deaths, coinsCollected, enemiesKilled, bulletsShot, playTimeMin, playTimeSec)"
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, stat.getDeaths());
            pstmt.setInt(2, stat.getCoins());
            pstmt.setInt(3, stat.getEnemiesKilled());
            pstmt.setInt(4, stat.getShots());
            pstmt.setInt(5, stat.getTimeMinutes());
            pstmt.setInt(6, stat.getTimeSeconds());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try(ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                        stat.setId(id);
                        player.setStats(stat);
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
    public static int insertPlayer(Player player) {
        int playerID = 0;
        String SQL = "INSERT INTO players(statsID, username, lvl, armorLvl, energyLvl, coins, skin, currBullet, palID, bulletTypes) "
                   + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        insertStats(player);
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {      
            pstmt.setInt(1, player.getStats().getId());
            pstmt.setString(2, player.getName());
            pstmt.setInt(3, player.getLevel());
            pstmt.setInt(4, player.getArmorLvl());
            pstmt.setInt(5, player.getEnergyLvl());
            pstmt.setInt(6, player.getCoins());
            pstmt.setInt(7, player.getSkin());
            pstmt.setInt(8, player.getCurrBullet());
            pstmt.setInt(9, player.getSelectedPal());
            pstmt.setString(10, player.getBulletTypes());
            
            int affectedRows = pstmt.executeUpdate();
            
            // check affected rows
            if (affectedRows > 0) {
                // get ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    // moves one row from the current position
                    if(rs.next()) {
                        playerID = rs.getInt(1);
                        player.setId(playerID);
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
     * @param levelID
     * @return level id
     */
    public static int insertLevels(int levelID) {
        int id = 0;
        String SQL = "INSERT INTO levels(levelID, planetName)"
                + "VALUES(?, ?)";
        
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, levelID);
            pstmt.setString(2, LevelSelectScreen.getInstance().planets[levelID - 1]);
            
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
    public static int insertHighScore(Player player) {
        int id = 0;
        int levelID = player.getLevel();
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
     * @param player
     * @return affected rows
     */
    public static int updateStats(Player player) {
        Stats stat = player.getStats();
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
    public static int updatePlayer(Player player) {
        String SQL = "UPDATE players "
                + "SET "
                + "lvl = ?,"
                + "armorLvl = ?,"     
                + "energyLvl = ?,"
                + "coins = ?,"
                + "skin = ?,"
                + "currBullet = ?,"
                + "palID = ?,"
                + "bulletTypes = ? "
                + "WHERE userID = ?";
        
        int affectedRows = 0;
        
        try (Connection conn = connect();
               PreparedStatement pstmt = conn.prepareStatement(SQL,
               Statement.RETURN_GENERATED_KEYS)) {      
            
            pstmt.setInt(1, player.getLevel());
            pstmt.setInt(2, player.getArmorLvl());
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
    public static int updateHighScore(Player player) {
        String SQL = "UPDATE highScores "
                + "SET "
                + "personalBest = ?"
                + "timesPlayed = ?"
                + "WHERE levelID = ?"
                + "WHERE userID = ?";
        
        int affectedRows = 0;
        int levelID = player.getLevel();
        
        try (Connection conn = connect();
               PreparedStatement pstmt = conn.prepareStatement(SQL,
               Statement.RETURN_GENERATED_KEYS)) {      
            
            pstmt.setInt(1, player.getHighscoreData().get(levelID).getPersonalBest());
            pstmt.setInt(2, player.getHighscoreData().get(levelID).getTimesPlayed());
            pstmt.setInt(3, player.getLevel());
            pstmt.setInt(4, player.getId());
            
            affectedRows = pstmt.executeUpdate();
                    
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedRows;
    }
}
