package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;

/**
 * An expression that can be evaluated.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
@FunctionalInterface
public interface Expression {

    /**
     * Obtains the name of the expression.
     *
     * @return The name of the expression.
     *
     * @since 0.2.1
     */
    default String name() {
        return "(unnamed)";
    }

    /**
     * Evaluates the expression.
     *
     * @return The evaluated expression. This evaluation must not be {@code null}.
     */
    Evaluation evaluate();

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
