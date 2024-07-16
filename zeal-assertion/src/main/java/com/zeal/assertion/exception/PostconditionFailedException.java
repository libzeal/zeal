package com.zeal.assertion.exception;

public class PostconditionFailedException extends AssertionFailedException {

    public PostconditionFailedException() {
    }

    public PostconditionFailedException(String s) {
        super(s);
    }

    public PostconditionFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PostconditionFailedException(Throwable cause) {
        super(cause);
    }
}
