package com.rainbowacehardware.paintsalescompetitionmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConnection {

    public Connection getConnection() {
        String databaseName = "rainbowAceHardware";
        String databaseUser = null;
        String databasePassword = null;
        String url = "jdbc:mysql://localhost/" + databaseName;

        // Load the database credentials from the configuration file or environment variables
        try {
            // Load properties from the config.properties file
            Properties props = new Properties();
            props.load(getClass().getResourceAsStream("/config.properties"));

            // Load databaseUser and databasePassword from properties
            databaseUser = props.getProperty("databaseUser");
            databasePassword = props.getProperty("databasePassword");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

