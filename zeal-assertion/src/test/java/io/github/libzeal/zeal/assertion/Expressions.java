package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.expression.lang.UnaryExpression;
import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class Expressions {

    @SuppressWarnings("unchecked")
    public static UnaryExpression<Object> expression() {
        return mock(UnaryExpression.class);
    }

    public static UnaryExpression<Object> expression(final Result passed, final Object subject) {

        final UnaryExpression<Object> expression = expression();
        final Evaluation evaluation = mock(Evaluation.class);

        doReturn(evaluation).when(expression).evaluate();
        doReturn(passed).when(evaluation).result();
        doReturn(subject).when(expression).subject();

        return expression;
    }
}
