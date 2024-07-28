package io.github.libzeal.zeal.expression.evaluation.format;

import io.github.libzeal.zeal.expression.evaluation.EvaluatedExpression;

/**
 * A formatter that consumes an evaluated expression and provides a string-based illustration of the evaluation.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
@FunctionalInterface
public interface EvaluatedExpressionFormatter {

    /**
     * Formats the supplied evaluated expression.
     *
     * @param evaluatedExpression
     *     The evaluated expression to format.
     *
     * @return The formatted evaluated expression.
     */
    String format(EvaluatedExpression evaluatedExpression);
}
