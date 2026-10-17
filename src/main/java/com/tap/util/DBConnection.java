package com.tap.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    
    private static final String URL = "jdbc:mysql://localhost:3306/foodvex";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root"; // Change this to your actual MySQL password

    
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Load the MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish the connection
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            
        } catch (ClassNotFoundException e) {
            System.err.println();
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println();
            e.printStackTrace();
        }
        
        return connection;
    }
}