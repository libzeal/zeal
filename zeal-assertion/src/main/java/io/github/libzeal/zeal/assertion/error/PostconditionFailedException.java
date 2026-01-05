package io.github.libzeal.zeal.assertion.error;

/**
 * Exception thrown when a postcondition fails.
 *
 * @author Justin Albano
 */
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
