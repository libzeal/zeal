package io.github.libzeal.zeal.assertion.error;

/**
 * Exception thrown when a precondition fails.
 *
 * @author Justin Albano
 */
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
