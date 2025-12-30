package io.github.libzeal.zeal.assertions;

public class PreconditionFailedException extends IllegalArgumentException {

    /**
     * Creates a new exception.
     *
     * @param message
     *     The message for the exception.
     */
    public PreconditionFailedException(final String message) {
        super(message);
    }
}
