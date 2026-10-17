package com.tap.util;

import java.sql.Connection;

public class TestConnection {
    
    public static void main(String[] args) {
        
        System.out.println("Attempting to connect to the database...");
        
        // Call our utility method
        Connection connection = DBConnection.getConnection();
        
        if (connection != null) {
            System.out.println("Success! The database connection is working perfectly.");
        } else {
            System.out.println("Failed! Could not connect to the database. Please check the console errors.");
        }
    }
}