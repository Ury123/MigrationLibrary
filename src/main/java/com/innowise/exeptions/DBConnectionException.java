package com.innowise.exeptions;


/**
 * Исключение, возникающее при ошибках подключения к базе данных.
 * Этот класс используется для обработки ошибок, связанных с установлением соединения
 * с базой данных или выполнением операций, требующих подключения к БД.
 */
public class DBConnectionException extends RuntimeException {
    public DBConnectionException(String message) {
        super(message);
    }

    public DBConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
