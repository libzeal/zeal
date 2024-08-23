package io.github.libzeal.zeal.logic.evaluation.format;

import io.github.libzeal.zeal.logic.evaluation.Cause;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * The context used when formatting components. This context contains information about the specific component being
 * evaluated, such as its depth within the evaluation tree.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
public class ComponentContext {

    private final Cause rootCause;
    private final int depth;

    /**
     * Creates a next context.
     *
     * @param rootCause
     *     The root cause of the evaluation tree.
     * @param depth
     *     The current depth within the evaluation tree.
     *
     * @throws NullPointerException
     *     The supplied root cause is {@code null}.
     */
    public ComponentContext(final Cause rootCause, final int depth) {
        this.rootCause = requireNonNull(rootCause);
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

        return Objects.equals(rootCause.evaluation(), evaluation);
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
