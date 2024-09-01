package io.github.libzeal.zeal.logic.evaluation.cause;

public class CycleDetectedException extends RuntimeException {

    public CycleDetectedException(final Cause cause, final int index) {
        super("Cycle detected: " + cause + " already exists in chain at index " + index);
    }
}
