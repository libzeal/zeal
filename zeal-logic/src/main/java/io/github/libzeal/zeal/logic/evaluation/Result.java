package io.github.libzeal.zeal.logic.evaluation;

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

    /**
     * Checks if the result is true.
     *
     * @return True if the result is true; false otherwise.
     */
    public boolean isTrue() {
        return equals(TRUE);
    }

    /**
     * Checks if the result is false.
     *
     * @return True if the result is false; false otherwise.
     */
    public boolean isFalse() {
        return equals(FALSE);
    }

    /**
     * Checks if the result is skip.
     *
     * @return True if the result is skip; false otherwise.
     */
    public boolean isSkipped() {
        return equals(SKIPPED);
    }
}
