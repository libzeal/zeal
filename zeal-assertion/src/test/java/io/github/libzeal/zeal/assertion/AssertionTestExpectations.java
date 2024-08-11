package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.expression.lang.UnaryExpression;
import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.format.SimpleEvaluationFormatter;

public class AssertionTestExpectations {

    @Deprecated
    public static String message(final UnaryExpression<Object> expression, final String message) {
        return AssertionExpressionEvaluator.formatMessage(
            new SimpleEvaluationFormatter(),
            message,
            expression.evaluate()
        );
    }

    public static String message(final Evaluation evaluation, final String message) {
        return AssertionExpressionEvaluator.formatMessage(
            new SimpleEvaluationFormatter(),
            message,
            evaluation
        );
    }
}
