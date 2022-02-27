package de.javamaps.exceptions;

public class ApplicationInitializationException extends Exception{
    public ApplicationInitializationException(Exception e) {
        super(e);
    }

    public ApplicationInitializationException(String message) {
        super(message);
    }
}
