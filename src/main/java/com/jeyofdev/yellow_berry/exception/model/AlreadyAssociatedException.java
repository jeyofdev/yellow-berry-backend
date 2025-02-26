package com.jeyofdev.yellow_berry.exception.model;

public class AlreadyAssociatedException extends RuntimeException {
    public AlreadyAssociatedException(String message) {
        super(message);
    }

    public AlreadyAssociatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyAssociatedException(Throwable cause) {
        super(cause);
    }
}