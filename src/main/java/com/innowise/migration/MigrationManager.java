package com.innowise.migration;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * Управляет выполнением миграций базы данных.
 * Реализует создание таблицы истории миграций, проверку применённых версий и выполнение новых миграций.
 */
@Slf4j
public class MigrationManager {
    private final Connection connection;

    private static final String CREATE_MIGRATION_HISTORY = """
            CREATE TABLE IF NOT EXISTS migration_history (
               id SERIAL PRIMARY KEY,
               version VARCHAR(5) NOT NULL,
               applied_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
            );
            """;

    private static final String SELECT_HISTORY_TABLE_FOR_UPDATE = "SELECT * FROM migration_history FOR UPDATE";

    /**
     * Конструктор класса `MigrationManager`.
     *
     * @param connection активное соединение с базой данных.
     */
    public MigrationManager(Connection connection) {
        this.connection = connection;
    }

    /**
     * Создаёт таблицу истории миграций, если она ещё не существует.
     *
     * @throws SQLException если возникает ошибка при выполнении SQL-запроса.
     */
    public void createMigrationHistory() throws SQLException {
        log.info("Создание таблицы истории миграций");
        try (PreparedStatement createMigrationHistory = connection.prepareStatement(CREATE_MIGRATION_HISTORY)) {
            createMigrationHistory.execute();
        };
    }

    /**
     * Проверяет, была ли уже применена миграция с указанной версией.
     *
     * @param version версия миграции, которую нужно проверить.
     * @return true, если миграция уже применена; false в противном случае.
     * @throws SQLException если возникает ошибка при выполнении SQL-запроса.
     */
    public boolean isMigrationApplied(String version) throws SQLException {
        log.info("Проверка, была ли применена {} миграция", version);
        String query = "SELECT COUNT(*) FROM migration_history WHERE version = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, version);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1) > 0; //
        }
    }

    /**
     * Применяет новую миграцию, состоящую из списка SQL-запросов.
     *
     * @param version версия миграции.
     * @param sql     список SQL-запросов, которые нужно выполнить.
     * @throws SQLException если возникает ошибка при выполнении SQL-запросов или фиксации транзакции.
     */
    public void applyMigration(String version, List<String> sql) throws SQLException {
        connection.setAutoCommit(false);

        try {
            lockSchemaHistoryTable();

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
            log.error("Ошибка при применении миграции, откат всех изменений");
            throw new SQLException("Ошибка при применении миграции", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    /**
     * Захватывает блокировку на таблице истории миграций.
     * Это предотвращает одновременное выполнение миграций несколькими процессами.
     *
     * @throws SQLException если возникает ошибка при выполнении SQL-запроса.
     */
    private void lockSchemaHistoryTable() throws SQLException {
        log.info("Попытка захватить блокировку таблицы истории миграций...");
        try (PreparedStatement statement = connection.prepareStatement(SELECT_HISTORY_TABLE_FOR_UPDATE)) {
            statement.executeQuery();
            log.info("Блокировка таблицы истории миграций успешно получена.");
        } catch (SQLException e) {
            log.error("Ошибка при захвате блокировки таблицы истории миграций", e);
            throw new SQLException("Ошибка при захвате блокировки таблицы истории миграций", e);
        }
    }
}
