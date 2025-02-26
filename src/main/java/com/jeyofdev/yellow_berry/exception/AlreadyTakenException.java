package com.jeyofdev.yellow_berry.exception;

public class AlreadyTakenException extends RuntimeException {
    public AlreadyTakenException(String message) {
        super(message);
    }

    public AlreadyTakenException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyTakenException(Throwable cause) {
        super(cause);
    }
}