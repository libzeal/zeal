package io.github.libzeal.zeal.expression.lang;

import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.lang.evaluation.Cause;
import io.github.libzeal.zeal.expression.lang.evaluation.TerminalEvaluation;
import io.github.libzeal.zeal.expression.lang.unary.UnaryExpression;

import static org.mockito.Mockito.*;

public class Expressions {

    private Expressions() {}

    public static UnaryExpression<Object> unaryExpression(final Result expectedResult) {
        return unaryExpression("test predicate", expectedResult);
    }

    @SuppressWarnings("unchecked")
    public static UnaryExpression<Object> unaryExpression(final String name, final Result expectedResult) {

        final Evaluation evaluation = mock(Evaluation.class);

        doReturn(expectedResult).when(evaluation).result();
        doReturn(name).when(evaluation).name();

        final UnaryExpression<Object> expression = mock(UnaryExpression.class);
        doReturn(evaluation).when(expression).evaluate();
        doReturn(name).when(expression).name();

        doAnswer(i -> {
            Cause cause = i.getArgument(0, Cause.class);
            return TerminalEvaluation.ofSkipped(name, cause);
        })
            .when(expression).skip(any(Cause.class));

        return expression;
    }
}
