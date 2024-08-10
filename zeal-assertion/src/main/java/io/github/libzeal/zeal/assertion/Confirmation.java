package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.assertion.error.AssertionFailedException;
import io.github.libzeal.zeal.expression.lang.UnaryExpression;
import io.github.libzeal.zeal.expression.lang.evaluation.format.SimpleEvaluationFormatter;

public class Confirmation {

    static final String DEFAULT_MESSAGE = "Assertion failed";
    private final AssertionExpressionEvaluator evaluator;

    public Confirmation() {
        this.evaluator = new AssertionExpressionEvaluator(
            new SimpleEvaluationFormatter(),
            AssertionFailedException::new,
            AssertionFailedException::new
        );
    }

    public static Confirmation create() {
        return new Confirmation();
    }

    public <T> T confirm(final UnaryExpression<T> expression) {
        return confirm(expression, DEFAULT_MESSAGE);
    }

    public <T> T confirm(final UnaryExpression<T> expression, final String message) {
        return evaluator.evaluate(expression, message);
    }
}
