package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.unary.UnaryExpression;

/**
 * A logical expression that can be evaluated.
 *
 * @author Justin Albano
 * @since 0.4.0
 */
public sealed interface Expression permits TerminalExpression, CompoundExpression, UnaryExpression {

    /**
     * The default name for all unnamed expressions.
     */
    String DEFAULT_NAME = "(unnamed)";

    /**
     * Obtains the name of the expression.
     *
     * @return The name of the expression.
     */
    default String name() {
        return DEFAULT_NAME;
    }

    /**
     * Evaluates the expression.
     *
     * @return The evaluated expression. This evaluation must not be
     * {@code null}.
     */
    Evaluation evaluate();
}
