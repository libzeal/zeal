package io.github.libzeal.zeal.logic.test;

import io.github.libzeal.zeal.logic.AndExpression;
import io.github.libzeal.zeal.logic.BooleanExpression;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.TerminalExpression;
import io.github.libzeal.zeal.logic.evaluation.DirectCause;
import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Evaluated;
import io.github.libzeal.zeal.logic.evaluation.TerminalEvaluation;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class TestExpressions {

    private TestExpressions() {}

    public static Expression expression(final boolean expectedResult) {
        return expression("test predicate", expectedResult);
    }

    public static Expression tautology() {
        return expression("test predicate", true);
    }

    public static Expression contradiction() {
        return expression("test predicate", false);
    }

    public static Expression expression(final String name, final boolean expectedResult) {

        final Evaluation evaluation = evaluation(name, expectedResult);
        final Expression expression = mock(TerminalExpression.class);

        doReturn(evaluation).when(expression).evaluate();
        doReturn(name).when(expression).name();

        return expression;
    }

    public static Evaluation evaluation(final String name, final boolean result) {
        return new TerminalEvaluation(result, name, new SimpleRationale("expected", "actual"), Duration.ZERO);
    }

    public static CompoundEvaluation compound(final boolean result, final String name, final DirectCause cause,
                                              final Evaluation... children) {
        return compoundOf(result, name, cause, Arrays.stream(children)
            .map(Evaluated::new)
            .toArray(SkippableEvaluation[]::new));
    }

    public static CompoundEvaluation compoundOf(final boolean result, final String name, final DirectCause cause,
                                                final SkippableEvaluation... children) {

        final List<Expression> expressions = Arrays.stream(children)
            .map(child -> (Expression) new BooleanExpression(child.name(), () -> true))
            .toList();

        return CompoundEvaluation.of(new AndExpression(name, expressions), result,
            new SimpleRationale("expected", "actual"), Duration.ZERO, cause, List.of(children));
    }
}
