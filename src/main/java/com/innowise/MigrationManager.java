package com.innowise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MigrationManager {
    private final Connection connection;

    private static final String CREATE_MIGRATION_HISTORY = "CREATE TABLE IF NOT EXISTS migration_history (" +
            "    id SERIAL PRIMARY KEY," +
            "    version VARCHAR(255) NOT NULL," +
            "    applied_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP" +
            ");";

    public MigrationManager(Connection connection) {
        this.connection = connection;
    }

    public void createMigrationHistory() throws SQLException {
        try (PreparedStatement createMigrationHistory = connection.prepareStatement(CREATE_MIGRATION_HISTORY)) {
            createMigrationHistory.execute();
        };
    }

    public boolean isMigrationApplied(String version) throws SQLException {
        String query = "SELECT COUNT(*) FROM migration_history WHERE version = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, version);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1) > 0;
        }
    }


    public void applyMigration(String version, List<String> sql) throws SQLException {
        connection.setAutoCommit(false);

        try {
            for (String query : sql) {
                MigrationExecutor.executeSql(connection, query);
            }

            String insertHistoryQuery = "INSERT INTO migration_history (version) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertHistoryQuery)) {
                preparedStatement.setString(1, version);
                preparedStatement.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new SQLException("Ошибка при применении миграции", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
