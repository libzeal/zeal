package com.zeal.assertion.exception;

public class PreconditionFailedException extends AssertionFailedException {

    public PreconditionFailedException() {
    }

    public PreconditionFailedException(String s) {
        super(s);
    }

    public PreconditionFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PreconditionFailedException(Throwable cause) {
        super(cause);
    }
}
