package de.javamaps.exceptions;

public class DataParsingException extends ApplicationInitializationException {
    public DataParsingException(Exception e) {
        super(e);
    }

    public DataParsingException(String message) {
        super(message);
    }
}
