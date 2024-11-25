package com.innowise.exeptions;

public class MigrationsFileException extends RuntimeException{
    public MigrationsFileException(String message) {
        super(message);
    }

    public MigrationsFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
