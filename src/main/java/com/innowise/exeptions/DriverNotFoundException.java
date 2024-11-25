package com.innowise.exeptions;


/**
 * Исключение, возникающее, если драйвер базы данных не найден.
 * Это исключение используется для обработки ситуаций, когда необходимый драйвер
 * базы данных отсутствует или не может быть загружен.
 */
public class DriverNotFoundException extends RuntimeException {
    public DriverNotFoundException(String message) {
        super(message);
    }

    public DriverNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
