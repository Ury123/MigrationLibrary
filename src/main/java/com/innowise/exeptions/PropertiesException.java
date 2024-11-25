package com.innowise.exeptions;

/**
 * Исключение, возникающее при работе с файлом свойств.
 * Это исключение используется для обработки ошибок, связанных с загрузкой,
 * чтением или доступом к свойствам конфигурации.
 */
public class PropertiesException extends RuntimeException {
    public PropertiesException(String message) {
        super(message);
    }

    public PropertiesException(String message, Throwable cause) {
        super(message, cause);
    }
}
