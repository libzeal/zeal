package io.github.libzeal.zeal.assertion.test;

import io.github.libzeal.zeal.logic.AndExpression;
import io.github.libzeal.zeal.logic.CompoundExpression;
import io.github.libzeal.zeal.logic.Contradiction;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.Tautology;
import io.github.libzeal.zeal.logic.TerminalExpression;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.TerminalEvaluation;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import io.github.libzeal.zeal.logic.unary.CompoundUnaryExpression;
import io.github.libzeal.zeal.logic.unary.TerminalUnaryExpression;
import io.github.libzeal.zeal.logic.unary.UnaryExpression;

import java.time.Duration;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class Expressions {

    @SuppressWarnings("unchecked")
    public static UnaryExpression<Object> expression() {
        return mock(TerminalUnaryExpression.class);
    }

    public static UnaryExpression<Object> expressionWithNullEvaluation() {

        final UnaryExpression<Object> expression = expression();

        doReturn(null).when(expression).evaluate();

        return expression;
    }

    public static Expression nonUnaryExpressionWithNullEvaluation() {

        final Expression expression = mock(TerminalExpression.class);

        doReturn(null).when(expression).evaluate();

        return expression;
    }

    public static Expression terminalExpression(final boolean result) {

        final Expression expression = mock(TerminalExpression.class);
        final Evaluation evaluation = new TerminalEvaluation(result, "foo", new SimpleRationale("foo", "bar", "baz"),
            Duration.ZERO);

        doReturn(evaluation).when(expression).evaluate();

        return expression;
    }

    public static Expression compoundExpression(final boolean result) {
        return compoundExpression(result ? new Tautology() : new Contradiction());
    }

    // A real compound evaluation records its elapsed time, which appears in the formatted message. The evaluation is
    // fixed so that repeated calls to evaluate produce the same message.
    public static Expression compoundExpression(final Expression... children) {

        final Expression expression = mock(CompoundExpression.class);

        doReturn(AndExpression.unnamed(children).evaluate()).when(expression).evaluate();

        return expression;
    }

    @SuppressWarnings("unchecked")
    public static UnaryExpression<Object> compoundUnaryExpression(final boolean result, final Object subject) {

        final UnaryExpression<Object> expression = mock(CompoundUnaryExpression.class);
        final Expression child = result ? new Tautology() : new Contradiction();

        doReturn(AndExpression.unnamed(child).evaluate()).when(expression).evaluate();
        doReturn(subject).when(expression).subject();

        return expression;
    }

    public static UnaryExpression<Object> expression(final boolean result, final Object subject) {

        final UnaryExpression<Object> expression = expression();
        final Evaluation evaluation = new TerminalEvaluation(result, "foo", new SimpleRationale("foo", "bar", "baz"),
            Duration.ZERO);

        doReturn(evaluation).when(expression).evaluate();
        doReturn(subject).when(expression).subject();

        return expression;
    }
}
