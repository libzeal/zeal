package io.github.libzeal.zeal.logic.test;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.cause.CauseGenerator;

import static org.mockito.Mockito.*;

public class Expressions {

    private Expressions() {}

    public static Expression expression(final Result expectedResult) {
        return expression("test predicate", expectedResult);
    }

    public static Expression expression(final String name, final Result expectedResult) {
        return expressionWithRootCause(name, expectedResult, Cause::new);
    }

    public static Expression expressionWithRootCause(final Result expectedResult,
                                                     CauseGenerator rootCauseSupplier) {
        return expressionWithRootCause("test predicate", expectedResult, rootCauseSupplier);
    }

    @SuppressWarnings("unchecked")
    public static Expression expressionWithRootCause(final String name, final Result expectedResult,
                                                     final CauseGenerator rootCauseSupplier) {

        final Evaluation evaluation = mock(Evaluation.class);

        doReturn(expectedResult).when(evaluation).result();
        doReturn(name).when(evaluation).name();
        doReturn(rootCauseSupplier.generate(evaluation)).when(evaluation).cause();

        final Expression expression = mock(Expression.class);
        doReturn(evaluation).when(expression).evaluate();
        doReturn(name).when(expression).name();

        return expression;
    }
}
