package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    private static final String URL = "jdbc:mysql://localhost:3306/quiz_system";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password123";
    
    private static Connection connection = null;
    
    private DBConnection() {
    }
    
    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("✅ Database connected successfully!");
            } catch (ClassNotFoundException e) {
                System.out.println("❌ MySQL JDBC Driver not found!");
                System.out.println("Error: " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("❌ Failed to connect to database!");
                System.out.println("Error: " + e.getMessage());
            }
        }
        return connection;
    }
    
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("✅ Database connection closed!");
            } catch (SQLException e) {
                System.out.println("❌ Error closing connection: " + e.getMessage());
            }
        }
    }
    
    public static boolean testConnection() {
        Connection testConn = getConnection();
        return testConn != null;
    }
}
