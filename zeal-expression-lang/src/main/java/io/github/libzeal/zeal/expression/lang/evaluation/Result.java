package io.github.libzeal.zeal.expression.lang.evaluation;

/**
 * The result of an evaluation.
 */
public enum Result {

    /**
     * An evaluation that passed.
     */
    PASSED,

    /**
     * An evaluation that failed.
     */
    FAILED,

    /**
     * An evaluation that was skipped (never evaluated).
     */
    SKIPPED;

    public boolean isPassed() {
        return equals(PASSED);
    }

    public boolean isFailed() {
        return equals(FAILED);
    }

    public boolean isSkipped() {
        return equals(SKIPPED);
    }
}
