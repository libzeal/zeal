package io.github.libzeal.zeal.expression.lang.evaluation;

/**
 * The result of an evaluation.
 */
public enum Result {

    /**
     * An evaluation that passed.
     */
    TRUE,

    /**
     * An evaluation that failed.
     */
    FALSE,

    /**
     * An evaluation that was skipped (never evaluated).
     */
    SKIPPED;

    public boolean isTrue() {
        return equals(TRUE);
    }

    public boolean isFalse() {
        return equals(FALSE);
    }

    public boolean isSkipped() {
        return equals(SKIPPED);
    }
}
