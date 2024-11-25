package com.innowise.exeptions;

/**
 * Исключение, возникающее при работе с файлами миграций.
 * Это исключение используется для обработки ошибок, связанных с чтением,
 * парсингом или доступом к файлам миграций.
 */
public class MigrationsFileException extends RuntimeException{
    public MigrationsFileException(String message) {
        super(message);
    }

    public MigrationsFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
