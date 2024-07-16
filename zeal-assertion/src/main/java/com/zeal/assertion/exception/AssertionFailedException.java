package com.zeal.assertion.exception;

public class AssertionFailedException extends IllegalStateException {

    public AssertionFailedException() {
    }

    public AssertionFailedException(String s) {
        super(s);
    }

    public AssertionFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssertionFailedException(Throwable cause) {
        super(cause);
    }
}
