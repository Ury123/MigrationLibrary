package com.innowise.migration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Утилитарный класс для выполнения SQL-запросов в рамках миграции.
 */
public class MigrationExecutor {

    /**
     * Выполняет указанный SQL-запрос с использованием предоставленного соединения.
     *
     * @param connection активное соединение с базой данных, используемое для выполнения запроса.
     * @param sql        SQL-запрос, который необходимо выполнить.
     * @throws SQLException если возникает ошибка при выполнении SQL-запроса.
     */
    public static void executeSql(Connection connection, String sql) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
        }
    }
}
