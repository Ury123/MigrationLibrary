package com.innowise.exeptions;

public class ReadingFilesException extends RuntimeException{
    public ReadingFilesException(String message){
        super(message);
    }

    public ReadingFilesException(String message, Throwable cause){
        super(message, cause);
    }
}
