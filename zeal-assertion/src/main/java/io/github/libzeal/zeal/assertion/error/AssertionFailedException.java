package io.github.libzeal.zeal.assertion.error;

/**
 * Exception thrown when an assertion fails.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class AssertionFailedException extends IllegalStateException {

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
