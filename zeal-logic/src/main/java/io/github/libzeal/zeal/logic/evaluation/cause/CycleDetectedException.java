package io.github.libzeal.zeal.logic.evaluation.cause;

<<<<<<< HEAD
public class CycleDetectedException extends RuntimeException {

    public CycleDetectedException(final Cause cause, final int index) {
        super("Cycle detected: " + cause + " already exists in chain at index " + index);
=======
/**
 * An exception denoting that a cycle in a chain has been detected.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
public class CycleDetectedException extends RuntimeException {

    /**
     * Creates a new exception with the cause that would have created the cycle and the index at which the cause is
     * already present in the chain.
     *
     * @param cause
     *     The cause that would have created the cycle.
     * @param existingIndex
     *     The index of the existing cause.
     */
    public CycleDetectedException(final Cause cause, final int existingIndex) {
        super("Cycle detected: " + cause + " already exists in chain at index " + existingIndex);
>>>>>>> c66d1f7 (Added documentation for cause classes (#10).)
    }
}
