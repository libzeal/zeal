package io.github.libzeal.zeal.assertions;

public class PostconditionFailedException extends IllegalStateException {

    /**
     * Creates a new exception.
     *
     * @param message
     *     The message for the exception.
     */
    public PostconditionFailedException(final String message) {
        super(message);
    }
}
