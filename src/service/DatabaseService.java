package service;

import database.DBConnection;
import model.Participant;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseService - Handles all database operations (CRUD)
 * CRUD = Create, Read, Update, Delete
 * 
 * @author Sindhuja
 */
public class DatabaseService {
    
    private Connection connection;
    
    // Constructor - gets database connection
    public DatabaseService() {
        this.connection = DBConnection.getConnection();
    }
    
    // ==================== CREATE (Add Participant) ====================
    
    /**
     * Add a new participant to the database
     * @param participant The participant object to add
     * @return true if added successfully, false otherwise
     */
    public boolean addParticipant(Participant participant) {
        String query = "INSERT INTO participants (name, department, score) VALUES (?, ?, ?)";
        
        try {
            // Prepare statement with auto-generated key for ID
            PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, participant.getName());
            pstmt.setString(2, participant.getDepartment());
            pstmt.setInt(3, participant.getScore());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Get the auto-generated ID
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    participant.setId(generatedKeys.getInt(1));
                }
                System.out.println("✅ Participant added successfully! ID: " + participant.getId());
                return true;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error adding participant: " + e.getMessage());
        }
        return false;
    }
    
    // ==================== READ (Get Participants) ====================
    
    /**
     * Get all participants from database
     * @return List of all participants
     */
    public List<Participant> getAllParticipants() {
        List<Participant> participants = new ArrayList<>();
        String query = "SELECT * FROM participants ORDER BY score DESC";
        
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                Participant p = new Participant();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setDepartment(rs.getString("department"));
                p.setScore(rs.getInt("score"));
                participants.add(p);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error getting participants: " + e.getMessage());
        }
        return participants;
    }
    
    /**
     * Get participant by ID
     * @param id The participant ID to search for
     * @return Participant object if found, null otherwise
     */
    public Participant getParticipantById(int id) {
        String query = "SELECT * FROM participants WHERE id = ?";
        
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Participant p = new Participant();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setDepartment(rs.getString("department"));
                p.setScore(rs.getInt("score"));
                return p;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error finding participant: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Search participants by name (partial match)
     * @param name The name to search for
     * @return List of matching participants
     */
    public List<Participant> searchParticipantsByName(String name) {
        List<Participant> participants = new ArrayList<>();
        String query = "SELECT * FROM participants WHERE name LIKE ? ORDER BY score DESC";
        
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, "%" + name + "%");  // % means any characters before/after
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Participant p = new Participant();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setDepartment(rs.getString("department"));
                p.setScore(rs.getInt("score"));
                participants.add(p);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error searching participants: " + e.getMessage());
        }
        return participants;
    }
    
    // ==================== UPDATE (Modify Participant) ====================
    
    /**
     * Update participant's score
     * @param id The participant ID
     * @param newScore The new score
     * @return true if updated successfully, false otherwise
     */
    public boolean updateScore(int id, int newScore) {
        String query = "UPDATE participants SET score = ? WHERE id = ?";
        
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, newScore);
            pstmt.setInt(2, id);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Score updated successfully for ID: " + id);
                return true;
            } else {
                System.out.println("❌ Participant with ID " + id + " not found");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error updating score: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Update participant's full details
     * @param participant Participant with updated details
     * @return true if updated successfully, false otherwise
     */
    public boolean updateParticipant(Participant participant) {
        String query = "UPDATE participants SET name = ?, department = ?, score = ? WHERE id = ?";
        
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, participant.getName());
            pstmt.setString(2, participant.getDepartment());
            pstmt.setInt(3, participant.getScore());
            pstmt.setInt(4, participant.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error updating participant: " + e.getMessage());
        }
        return false;
    }
    
    // ==================== DELETE (Remove Participant) ====================
    
    /**
     * Delete participant by ID
     * @param id The participant ID to delete
     * @return true if deleted successfully, false otherwise
     */
    public boolean deleteParticipant(int id) {
        String query = "DELETE FROM participants WHERE id = ?";
        
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Participant deleted successfully! ID: " + id);
                return true;
            } else {
                System.out.println("❌ Participant with ID " + id + " not found");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error deleting participant: " + e.getMessage());
        }
        return false;
    }
    
    // ==================== REPORT (Statistics) ====================
    
    /**
     * Get highest score
     * @return highest score, or 0 if no participants
     */
    public int getHighestScore() {
        String query = "SELECT MAX(score) as highest FROM participants";
        
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("highest");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error getting highest score: " + e.getMessage());
        }
        return 0;
    }
    
    /**
     * Get lowest score
     * @return lowest score, or 0 if no participants
     */
    public int getLowestScore() {
        String query = "SELECT MIN(score) as lowest FROM participants";
        
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("lowest");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error getting lowest score: " + e.getMessage());
        }
        return 0;
    }
    
    /**
     * Get average score
     * @return average score, or 0 if no participants
     */
    public double getAverageScore() {
        String query = "SELECT AVG(score) as average FROM participants";
        
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return rs.getDouble("average");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error getting average score: " + e.getMessage());
        }
        return 0;
    }
    
    /**
     * Get total number of participants
     * @return count of participants
     */
    public int getParticipantCount() {
        String query = "SELECT COUNT(*) as count FROM participants";
        
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error getting participant count: " + e.getMessage());
        }
        return 0;
    }
}
