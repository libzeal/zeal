package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;

/**
 * An expression that can be evaluated.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public interface Expression {

    /**
     * Obtains the name of the expression.
     *
     * @return The name of the expression.
     *
     * @since 0.2.1
     */
    String name();

    /**
     * Evaluates the expression.
     *
     * @return The evaluated expression. This evaluation must not be {@code null}.
     */
    Evaluation evaluate();

    /**
     * Performs a skipped evaluation.
     *
     * @param cause
     *     The cause of the skipped evaluation.
     *
     * @return An evaluation where the execution of the evaluation has been skipped.
     *
     * @since 0.2.1
     */
    Evaluation skip(Cause cause);

    /**
     * Creates a tautology.
     *
     * @return A tautology.
     *
     * @since 0.2.1
     */
    static Expression tautology() {
        return new Tautology();
    }

    /**
     * Creates a contradiction.
     *
     * @return A contradiction.
     *
     * @since 0.2.1
     */
    static Expression contradiction() {
        return new Contradiction();
    }
}
