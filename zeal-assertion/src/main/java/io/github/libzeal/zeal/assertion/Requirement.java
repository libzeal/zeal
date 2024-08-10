package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.assertion.error.PreconditionFailedException;
import io.github.libzeal.zeal.expression.lang.UnaryExpression;
import io.github.libzeal.zeal.expression.lang.evaluation.format.SimpleEvaluationFormatter;

public class Requirement {

    static final String DEFAULT_MESSAGE = "Precondition failed";
    private final AssertionExpressionEvaluator evaluator;

    public Requirement() {
        this.evaluator = new AssertionExpressionEvaluator(
            new SimpleEvaluationFormatter(),
            NullPointerException::new,
            PreconditionFailedException::new
        );
    }

    public static Requirement create() {
        return new Requirement();
    }

    public <T> T require(final UnaryExpression<T> expression) {
        return require(expression, DEFAULT_MESSAGE);
    }

    public <T> T require(final UnaryExpression<T> expression, final String message) {
        return evaluator.evaluate(expression, message);
    }
}
