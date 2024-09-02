package io.github.libzeal.zeal.logic.evaluation.cause;

<<<<<<< HEAD
public class MaximumDepthExceededException extends RuntimeException{
=======
/**
 * An exception denoting that the maximum depth of the chain has been exceeded.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
public class MaximumDepthExceededException extends RuntimeException {

    /**
     * Creates a new exception with the supplied maximum depth.
     *
     * @param maximumDepth
     *     The maximum depth that was exceeded.
     */
    public MaximumDepthExceededException(final int maximumDepth) {
        super("Maximum depth exceeded: " + maximumDepth);
    }
>>>>>>> c66d1f7 (Added documentation for cause classes (#10).)
}
