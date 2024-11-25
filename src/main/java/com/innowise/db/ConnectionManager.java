package com.innowise.db;

import com.innowise.exeptions.DBConnectionException;
import com.innowise.exeptions.DriverNotFoundException;
import com.innowise.utils.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Класс для управления подключениями к базе данных.
 * Обеспечивает создание и закрытие соединений с использованием конфигурации из файла свойств.
 */
@Slf4j
public class ConnectionManager {

    /**
     * Создаёт и возвращает подключение к базе данных.
     * Настройки подключения считываются из файла application.properties.
     *
     * @return объект {@link Connection}, представляющий соединение с базой данных.
     * @throws RuntimeException если возникает ошибка подключения или драйвер базы данных не найден.
     */
    public static Connection getConnection() {
        try {
            Class.forName(PropertiesUtils.getProperty("db.driver"));
            return DriverManager.getConnection(PropertiesUtils.getProperty("db.url"),
                    PropertiesUtils.getProperty("db.username"), PropertiesUtils.getProperty("db.password"));
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Ошибка подключения к базе данных");
            throw new DBConnectionException("Ошибка подключения к базе данных", e);
        } catch (ClassNotFoundException e) {
            log.error("Драйвер не найден");
            throw new DriverNotFoundException("Драйвер не найден", e);
        }
    }

    /**
     * Закрывает указанное соединение с базой данных.
     *
     * @param connection объект {@link Connection}, который необходимо закрыть. Может быть {@code null}.
     *                   Если соединение равно {@code null}, метод ничего не делает.
     * @throws RuntimeException если возникает ошибка при закрытии соединения.
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                log.info("Соединение успешно закрыто");
            } catch (SQLException e) {
                log.error("Ошибка при закрытии соединения");
                e.printStackTrace();
            }
        }
    }
}
