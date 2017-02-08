package com.dwalldorf.timetrack.repository.exception;

public class CsvParsingException extends RuntimeException {

    public CsvParsingException() {
    }

    public CsvParsingException(String message) {
        super(message);
    }

    public CsvParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public CsvParsingException(Throwable cause) {
        super(cause);
    }

    public CsvParsingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}