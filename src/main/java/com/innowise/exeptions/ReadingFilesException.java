package com.innowise.exeptions;

/**
 * Исключение, возникающее при ошибках чтения файлов.
 * Это исключение используется для обработки ситуаций, когда файл не может быть прочитан,
 * отсутствует, поврежден или содержит недопустимые данные.
 */
public class ReadingFilesException extends RuntimeException{
    public ReadingFilesException(String message){
        super(message);
    }

    public ReadingFilesException(String message, Throwable cause){
        super(message, cause);
    }
}
