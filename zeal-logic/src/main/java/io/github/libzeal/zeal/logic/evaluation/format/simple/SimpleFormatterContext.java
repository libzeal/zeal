package io.github.libzeal.zeal.logic.evaluation.format.simple;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.cause.Cause;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * The context used when formatting components. This context contains information about the specific component being
 * evaluated, such as its depth within the evaluation tree.
 *
 * @author Justin Albano
 * @since 0.4.0
 */
class SimpleFormatterContext {

    private final Cause cause;
    private final int depth;

    /**
     * Creates a next context.
     *
     * @param cause
     *     The cause of the evaluation.
     * @param depth
     *     The current depth within the evaluation tree.
     *
     * @throws NullPointerException
     *     The supplied root cause is {@code null}.
     */
    public SimpleFormatterContext(final Cause cause, final int depth) {
        this.cause = requireNonNull(cause);

        if (depth < 0) {
            throw new IllegalArgumentException("Depth cannot be negative");
        }

        this.depth = depth;
    }

    /**
     * Checks if the supplied evaluation is the root cause.
     *
     * @param evaluation
     *     The evaluation to check.
     *
     * @return True if the supplied evaluation is the root cause; false otherwise.
     */
    public boolean isRootCause(final Evaluation evaluation) {

        if (evaluation == null) {
            return false;
        }

        return cause.rootCause().is(evaluation);
    }

    /**
     * Obtains the current depth within the evaluation tree.
     *
     * @return The current depth within the evaluation tree.
     */
    public int depth() {
        return depth;
    }
}
