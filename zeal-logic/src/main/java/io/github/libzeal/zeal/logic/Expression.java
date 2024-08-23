package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.Cause;
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
     * @return An evaluation where the execution of the evaluation has been skipped.
     *
     * @since 0.2.1
     */
    Evaluation skip(Cause cause);

    /**
     * An expression that is always true (a tautology).
     *
     * @since 0.2.1
     */
    Expression TRUE = new Tautology();

    /**
     * An expression that is always false (a contradiction).
     *
     * @since 0.2.1
     */
    Expression FALSE = new Contradiction();
}
