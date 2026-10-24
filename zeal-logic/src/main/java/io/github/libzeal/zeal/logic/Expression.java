package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.unary.UnaryExpression;

/**
 * An expression that can be evaluated.
 *
 * @author Justin Albano
 */
public sealed interface Expression permits TerminalExpression, CompoundExpression, UnaryExpression {

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
