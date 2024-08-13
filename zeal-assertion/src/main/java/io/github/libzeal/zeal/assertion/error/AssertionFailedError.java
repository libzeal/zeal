package io.github.libzeal.zeal.assertion.error;

/**
 * Exception thrown when an assertion fails.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class AssertionFailedError extends AssertionError {

    /**
     * Creates a new exception.
     *
     * @param message
     *     The message for the exception.
     */
    public AssertionFailedError(final String message) {
        super(message);
    }
}
