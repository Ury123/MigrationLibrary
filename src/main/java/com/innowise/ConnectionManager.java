package com.innowise;

import com.innowise.utils.PropertiesUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    public static Connection getConnection() {
        try {
            Class.forName(PropertiesUtils.getProperty("db.driver"));
            return DriverManager.getConnection(PropertiesUtils.getProperty("db.url"),
                    PropertiesUtils.getProperty("db.username"), PropertiesUtils.getProperty("db.password"));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error connecting to the database");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
