package io.github.libzeal.zeal.logic.test;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.evaluation.TerminalEvaluation;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import io.github.libzeal.zeal.logic.unary.UnaryExpression;

import java.time.Duration;

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

        final Evaluation evaluation = evaluation(name, expectedResult);
        final Expression expression = mock(UnaryExpression.class);

        doReturn(evaluation).when(expression).evaluate();
        doReturn(name).when(expression).name();

        return expression;
    }

    public static Evaluation evaluation(final String name, final Result result) {

        if (result.isSkipped()) {
            return TerminalEvaluation.skipped(name, evaluation("decider", Result.FALSE));
        }

        return TerminalEvaluation.of(result, name, new SimpleRationale("expected", "actual"), Duration.ZERO);
    }
}
