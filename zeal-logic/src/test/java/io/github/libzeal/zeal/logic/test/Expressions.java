package io.github.libzeal.zeal.logic.test;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.TerminalEvaluation;
import io.github.libzeal.zeal.logic.unary.UnaryExpression;

import java.util.function.Function;

import static org.mockito.Mockito.*;

public class Expressions {

    private Expressions() {}

    public static UnaryExpression<Object> unaryExpression(final Result expectedResult) {
        return unaryExpression("test predicate", expectedResult);
    }

    public static UnaryExpression<Object> unaryExpression(final String name, final Result expectedResult) {
        return unaryExpressionWithRootCause(name, expectedResult, Cause::new);
    }

    public static UnaryExpression<Object> unaryExpressionWithRootCause(final Result expectedResult, final Function<Evaluation, Cause> rootCauseSupplier) {
        return unaryExpressionWithRootCause("test predicate", expectedResult, rootCauseSupplier);
    }

    @SuppressWarnings("unchecked")
    public static UnaryExpression<Object> unaryExpressionWithRootCause(final String name, final Result expectedResult,
                                                                  final Function<Evaluation, Cause> rootCauseSupplier) {

        final Evaluation evaluation = mock(Evaluation.class);

        doReturn(expectedResult).when(evaluation).result();
        doReturn(name).when(evaluation).name();
        doReturn(rootCauseSupplier.apply(evaluation)).when(evaluation).cause();

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
