package io.github.libzeal.zeal.assertions;

public class AssertionFailedException extends AssertionError {

    /**
     * Creates a new exception.
     *
     * @param message
     *     The message for the exception.
     */
    public AssertionFailedException(final String message) {
        super(message);
    }
}
