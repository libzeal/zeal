package io.github.libzeal.zeal.assertion.test;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.TerminalEvaluation;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import io.github.libzeal.zeal.logic.unary.TerminalUnaryExpression;
import io.github.libzeal.zeal.logic.unary.UnaryExpression;

import java.time.Duration;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class Expressions {

    @SuppressWarnings("unchecked")
    public static UnaryExpression<Object> expression() {
        return mock(TerminalUnaryExpression.class);
    }

    public static UnaryExpression<Object> expressionWithNullEvaluation() {

        final UnaryExpression<Object> expression = expression();

        doReturn(null).when(expression).evaluate();

        return expression;
    }

    public static UnaryExpression<Object> expression(final boolean result, final Object subject) {

        final UnaryExpression<Object> expression = expression();
        final Evaluation evaluation = new TerminalEvaluation(result, "foo", new SimpleRationale("foo", "bar", "baz"),
            Duration.ZERO);

        doReturn(evaluation).when(expression).evaluate();
        doReturn(subject).when(expression).subject();

        return expression;
    }
}
