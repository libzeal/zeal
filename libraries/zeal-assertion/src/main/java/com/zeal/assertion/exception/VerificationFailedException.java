package com.zeal.assertion.exception;

public class VerificationFailedException extends AssertionFailedException {

    public VerificationFailedException() {
    }

    public VerificationFailedException(String s) {
        super(s);
    }

    public VerificationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerificationFailedException(Throwable cause) {
        super(cause);
    }
}
