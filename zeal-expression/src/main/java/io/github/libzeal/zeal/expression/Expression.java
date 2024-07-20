package io.github.libzeal.zeal.expression;

import io.github.libzeal.zeal.expression.evaluation.EvaluatedExpression;

/**
 * An expression that can be evaluated.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public interface Expression {

    /**
     * Evaluates the expression.
     *
     * @return The evaluated expression.
     */
    EvaluatedExpression evaluate();
}
