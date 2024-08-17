package io.github.libzeal.zeal.expression.lang;

import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.lang.evaluation.RootCause;
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
            RootCause rootCause = i.getArgument(0, RootCause.class);
            return TerminalEvaluation.ofSkipped(name, rootCause);
        })
            .when(expression).skip(any(RootCause.class));

        return expression;
    }
}
