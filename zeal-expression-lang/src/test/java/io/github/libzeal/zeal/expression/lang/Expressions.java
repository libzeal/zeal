package io.github.libzeal.zeal.expression.lang;

import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.lang.evaluation.TerminalSkippedEvaluation;
import io.github.libzeal.zeal.expression.lang.unary.UnaryExpression;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class Expressions {

    private Expressions() {}

    public static UnaryExpression<Object> unaryExpression(final Result expectedResult) {
        return unaryExpression("test predicate", expectedResult);
    }

    @SuppressWarnings("unchecked")
    public static UnaryExpression<Object> unaryExpression(final String name, final Result expectedResult) {

        Evaluation evaluation = mock(Evaluation.class);
        doReturn(expectedResult).when(evaluation).result();
        doReturn(name).when(evaluation).name();

        UnaryExpression<Object> expression = mock(UnaryExpression.class);
        doReturn(evaluation).when(expression).evaluate();
        doReturn(name).when(expression).name();
        doReturn(new TerminalSkippedEvaluation(name)).when(expression).skip();

        return expression;
    }
}
