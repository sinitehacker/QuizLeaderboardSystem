package main;

import database.DBConnection;

public class TestMain {
    public static void main(String[] args) {
        System.out.println("=== Testing Database Connection ===\n");
        
        boolean connected = DBConnection.testConnection();
        
        if (connected) {
            System.out.println("\n✅ SUCCESS: Database connection working!");
        } else {
            System.out.println("\n❌ FAILED: Database connection not working!");
            System.out.println("Please check:");
            System.out.println("1. MySQL is running: sudo systemctl status mysql");
            System.out.println("2. Password in DBConnection.java is correct");
            System.out.println("3. Database 'quiz_system' exists");
        }
        
        DBConnection.closeConnection();
    }
}
