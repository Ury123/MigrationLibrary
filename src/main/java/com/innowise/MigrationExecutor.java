package com.innowise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MigrationExecutor {
    public static void executeSql(Connection connection, String sql) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
        }
    }
}
