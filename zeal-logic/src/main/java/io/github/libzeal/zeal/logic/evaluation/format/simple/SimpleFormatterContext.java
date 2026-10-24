package io.github.libzeal.zeal.logic.evaluation.format.simple;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * The context used when formatting components. This context contains information about the specific component being
 * evaluated, such as its depth within the evaluation tree.
 *
 * @author Justin Albano
 */
class SimpleFormatterContext {

    private final List<Evaluation> rootCauses;
    private final int depth;

    /**
     * Creates a next context.
     *
     * @param rootCauses
     *     The root causes of the evaluation being formatted.
     * @param depth
     *     The current depth within the evaluation tree.
     *
     * @throws NullPointerException
     *     The supplied root causes are {@code null}.
     */
    public SimpleFormatterContext(final List<Evaluation> rootCauses, final int depth) {
        this.rootCauses = requireNonNull(rootCauses);

        if (depth < 0) {
            throw new IllegalArgumentException("Depth cannot be negative");
        }

        this.depth = depth;
    }

    /**
     * Checks if the supplied evaluation is a root cause. Evaluations are compared by identity, so an evaluation that is
     * equal to, but not the same instance as, a root cause is not considered a root cause.
     *
     * @param evaluation
     *     The evaluation to check.
     *
     * @return True if the supplied evaluation is a root cause; false otherwise.
     */
    public boolean isRootCause(final Evaluation evaluation) {
        return rootCauses.stream().anyMatch(rootCause -> rootCause == evaluation);
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
