package com.webapp.users.exception;

public class TestNotFoundException extends Exception {

    public TestNotFoundException() {
    }

    public TestNotFoundException(String message) {
        super(message);
    }

    public TestNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TestNotFoundException(Throwable cause) {
        super(cause);
    }

    public TestNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
