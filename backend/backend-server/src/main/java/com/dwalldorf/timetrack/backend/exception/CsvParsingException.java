package com.dwalldorf.timetrack.backend.exception;

public class CsvParsingException extends RuntimeException {

    public CsvParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}