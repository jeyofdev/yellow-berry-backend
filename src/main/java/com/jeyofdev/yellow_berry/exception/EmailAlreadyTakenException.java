package com.jeyofdev.yellow_berry.exception;

public class EmailAlreadyTakenException extends RuntimeException {
    public EmailAlreadyTakenException(String message) {
        super(message);
    }

    public EmailAlreadyTakenException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailAlreadyTakenException(Throwable cause) {
        super(cause);
    }
}