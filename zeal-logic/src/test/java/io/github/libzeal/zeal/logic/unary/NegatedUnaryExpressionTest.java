package io.github.libzeal.zeal.logic.unary;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.NegatedExpression;
import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.evaluation.TerminalEvaluation;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.libzeal.zeal.logic.NegatedExpression.*;
import static io.github.libzeal.zeal.logic.test.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NegatedUnaryExpressionTest {

    private UnaryExpression<Object> wrapped;
    private NegatedUnaryExpression<Object> expression;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() {

        wrapped = mock(UnaryExpression.class);

        doReturn("foo").when(wrapped).name();
        doReturn(new Object()).when(wrapped).subject();
        doReturn(TerminalEvaluation.ofTrue("foo", SimpleRationale.empty())).when(wrapped).evaluate();

        expression = new NegatedUnaryExpression<>(wrapped);
    }

    @Test
    void givenNullWrappedExpression_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new NegatedUnaryExpression<>(null)
        );
    }

    @Test
    void whenName_thenMatchesWrapped() {
        assertEquals(NegatedExpression.DEFAULT_NAME, expression.name());
    }

    @Test
    void givenValidWrappedExpression_whenSubject_thenMatchesWrapped() {
        assertEquals(wrapped.subject(), expression.subject());
    }

    @Test
    void givenTrueExpression_whenEvaluate_thenEvaluationIsCorrect() {

        final UnaryExpression<Object> trueExpression = trueExpression();
        final NegatedUnaryExpression<Object> negatedExpression = new NegatedUnaryExpression<>(trueExpression);
        final Evaluation evaluation = negatedExpression.evaluate();
        final Rationale rationale = evaluation.rationale();

        assertEquals(Result.FALSE, evaluation.result());
        assertEquals(NegatedExpression.DEFAULT_NAME, evaluation.name());
        assertRationaleEquals(rationale, VALUE_WRAPPED_FALSE, VALUE_WRAPPED_TRUE);
        assertEquals(trueExpression.evaluate(), evaluation.cause().evaluation());
        assertDepthFirstTraversalIsTerminal(evaluation);
    }

    private static UnaryExpression<Object> trueExpression() {
        return expression(Expression.TRUE);
    }

    @SuppressWarnings("unchecked")
    private static UnaryExpression<Object> expression(final Expression baseExpression) {

        final Object subject = new Object();
        final UnaryExpression<Object> expression = mock(UnaryExpression.class);

        doReturn(subject).when(expression).subject();
        doReturn("foo").when(expression).name();
        doAnswer(i -> baseExpression.evaluate()).when(expression).evaluate();
        doAnswer(i -> baseExpression.skip(i.getArgument(0, Cause.class))).when(expression).skip(any(Cause.class));

        return expression;
    }

    @Test
    void givenFalseExpression_whenEvaluate_thenEvaluationIsCorrect() {

        final UnaryExpression<Object> falseExpression = falseExpression();
        final NegatedUnaryExpression<Object> negatedExpression = new NegatedUnaryExpression<>(falseExpression);
        final Evaluation evaluation = negatedExpression.evaluate();
        final Rationale rationale = evaluation.rationale();

        assertEquals(Result.TRUE, evaluation.result());
        assertEquals(NegatedExpression.DEFAULT_NAME, evaluation.name());
        assertRationaleEquals(rationale, VALUE_WRAPPED_FALSE, VALUE_WRAPPED_FALSE);
        assertEquals(falseExpression.evaluate(), evaluation.cause().evaluation());
        assertDepthFirstTraversalIsTerminal(evaluation);
    }

    private static UnaryExpression<Object> falseExpression() {
        return expression(Expression.FALSE);
    }

    @Test
    void givenTrueExpression_whenSkip_thenSkippedEvaluationIsCorrect() {

        final Cause cause = mock(Cause.class);

        final NegatedUnaryExpression<Object> negatedExpression = new NegatedUnaryExpression<>(trueExpression());
        final Evaluation skippedEvaluation = negatedExpression.skip(cause);
        final Rationale rationale = skippedEvaluation.rationale();

        assertEquals(NegatedExpression.DEFAULT_NAME, skippedEvaluation.name());
        assertEquals(cause, skippedEvaluation.cause());
        assertRationaleIsSkipped(rationale);
    }

    @Test
    void givenFalseExpression_whenSkip_thenSkippedEvaluationIsCorrect() {

        final Cause cause = mock(Cause.class);

        final NegatedUnaryExpression<Object> negatedExpression = new NegatedUnaryExpression<>(falseExpression());
        final Evaluation skippedEvaluation = negatedExpression.skip(cause);
        final Rationale rationale = skippedEvaluation.rationale();

        assertEquals(NegatedExpression.DEFAULT_NAME, skippedEvaluation.name());
        assertEquals(cause, skippedEvaluation.cause());
        assertRationaleIsSkipped(rationale);
    }

    @Test
    void givenSkippedExpression_whenEvaluate_thenEvaluationIsCorrect() {

        final UnaryExpression<Object> skippedExpression = skippedEvaluation();
        final NegatedUnaryExpression<Object> negatedSkippedExpression = new NegatedUnaryExpression<>(skippedExpression);
        final Evaluation skippedEvaluation = negatedSkippedExpression.evaluate();
        final Rationale rationale = skippedEvaluation.rationale();

        assertEquals(Result.FALSE, skippedEvaluation.result());
        assertEquals(NegatedExpression.DEFAULT_NAME, skippedEvaluation.name());
        assertRationaleEquals(rationale, VALUE_WRAPPED_FALSE, VALUE_WRAPPED_SKIPPED);
        assertNull(skippedEvaluation.cause().evaluation());
        assertDepthFirstTraversalIsTerminal(skippedEvaluation);
    }

    @SuppressWarnings("unchecked")
    private static UnaryExpression<Object> skippedEvaluation() {

        final Cause cause = mock(Cause.class);
        final UnaryExpression<Object> skippedExpression = mock(UnaryExpression.class);

        doReturn(Expression.TRUE.skip(cause)).when(skippedExpression).evaluate();

        return skippedExpression;
    }

    @Test
    void givenSkippedExpression_whenSkip_thenSkippedEvaluationIsCorrect() {

        final Cause cause = mock(Cause.class);

        final UnaryExpression<Object> skippedExpression = skippedEvaluation();
        final NegatedUnaryExpression<Object> negatedSkippedExpression = new NegatedUnaryExpression<>(skippedExpression);
        final Evaluation skippedEvaluation = negatedSkippedExpression.skip(cause);
        final Rationale rationale = skippedEvaluation.rationale();

        assertEquals(NegatedExpression.DEFAULT_NAME, skippedEvaluation.name());
        assertEquals(cause, skippedEvaluation.cause());
        assertRationaleIsSkipped(rationale);
    }
}
