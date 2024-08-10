package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.assertion.error.PostconditionFailedException;
import io.github.libzeal.zeal.expression.lang.UnaryExpression;
import io.github.libzeal.zeal.expression.lang.evaluation.format.SimpleEvaluationFormatter;

public class Assurance {

    static final String DEFAULT_MESSAGE = "Postcondition failed";
    private final AssertionExpressionEvaluator evaluator;

    public Assurance() {
        this.evaluator = new AssertionExpressionEvaluator(
            new SimpleEvaluationFormatter(),
            PostconditionFailedException::new,
            PostconditionFailedException::new
        );
    }

    public static Assurance create() {
        return new Assurance();
    }

    public <T> T ensure(final UnaryExpression<T> expression) {
        return ensure(expression, DEFAULT_MESSAGE);
    }

    public <T> T ensure(final UnaryExpression<T> expression, final String message) {
        return evaluator.evaluate(expression, message);
    }
}
