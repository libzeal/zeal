package io.github.libzeal.zeal.logic.test;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.cause.CauseGenerator;
import io.github.libzeal.zeal.logic.unary.UnaryExpression;

import static org.mockito.Mockito.*;

public class TestExpressions {

    private TestExpressions() {}

    public static Expression expression(final Result expectedResult) {
        return expression("test predicate", expectedResult);
    }

    public static Expression tautology() {
        return expression("test predicate", Result.TRUE);
    }

    public static Expression contradiction() {
        return expression("test predicate", Result.FALSE);
    }

    public static Expression expression(final String name, final Result expectedResult) {
        return expressionWithRootCause(name, expectedResult, Cause::new);
    }

    public static Expression expressionWithRootCause(final Result expectedResult,
                                                     CauseGenerator rootCauseSupplier) {
        return expressionWithRootCause("test predicate", expectedResult, rootCauseSupplier);
    }

    public static Expression expressionWithRootCause(final String name, final Result expectedResult,
                                                     final CauseGenerator rootCauseSupplier) {

        final Evaluation evaluation = mock(Evaluation.class);

        doReturn(expectedResult).when(evaluation).result();
        doReturn(name).when(evaluation).name();
        doReturn(rootCauseSupplier.generate(evaluation)).when(evaluation).cause();

        final Expression expression = mock(UnaryExpression.class);
        doReturn(evaluation).when(expression).evaluate();
        doReturn(name).when(expression).name();

        return expression;
    }
}
