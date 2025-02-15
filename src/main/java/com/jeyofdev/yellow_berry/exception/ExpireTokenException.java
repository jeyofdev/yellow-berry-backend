package com.jeyofdev.yellow_berry.exception;

public class ExpireTokenException extends RuntimeException {
    public ExpireTokenException(String message) {
        super(message);
    }

    public ExpireTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpireTokenException(Throwable cause) {
        super(cause);
    }
}
