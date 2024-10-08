package io.github.libzeal.zeal.assertion.test;

import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.unary.UnaryExpression;

import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class Expressions {

    @SuppressWarnings("unchecked")
    public static UnaryExpression<Object> expression() {
        return mock(UnaryExpression.class);
    }

    public static UnaryExpression<Object> expressionWithNullEvaluation() {

        final UnaryExpression<Object> expression = expression();

        doReturn(null).when(expression).evaluate();

        return expression;
    }

    public static UnaryExpression<Object> expressionWithNullResult() {

        final UnaryExpression<Object> expression = expression();
        final Evaluation evaluation = mock(Evaluation.class);

        doReturn(evaluation).when(expression).evaluate();
        doReturn(null).when(evaluation).result();

        return expression;
    }

    public static UnaryExpression<Object> expression(final Result result, final Object subject) {

        final UnaryExpression<Object> expression = expression();
        final Evaluation evaluation = mock(Evaluation.class);
        final Rationale rationale = mock(Rationale.class);

        doReturn("foo").when(rationale).expected();
        doReturn("bar").when(rationale).actual();
        doReturn(Optional.of("baz")).when(rationale).hint();

        doReturn(rationale).when(evaluation).rationale();
        doReturn(new Cause(evaluation)).when(evaluation).cause();

        doReturn(evaluation).when(expression).evaluate();
        doReturn(result).when(evaluation).result();
        doReturn(subject).when(expression).subject();

        return expression;
    }
}
