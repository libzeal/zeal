package io.github.libzeal.zeal.assertion.error;

/**
 * Exception thrown when a precondition fails.
 *
 * @author Justin Albano
 * @since 0.2.0
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
