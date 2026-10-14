package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.unary.UnaryExpression;
import io.github.libzeal.zeal.logic.unary.future.ComputedExpression;

/**
 * An expression that can be evaluated.
 *
 * @author Justin Albano
 */
public sealed interface Expression
        permits BooleanExpression, Tautology, Contradiction, NotExpression,
            CompoundExpression, UnaryExpression, ComputedExpression {

    String DEFAULT_NAME = "(unnamed)";

    /**
     * Obtains the name of the expression.
     *
     * @return The name of the expression.
     *
     * @since 0.2.1
     */
    default String name() {
        return DEFAULT_NAME;
    }

    /**
     * Evaluates the expression.
     *
     * @return The evaluated expression. This evaluation must not be {@code null}.
     */
    Evaluation evaluate();
}
