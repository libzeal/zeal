package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import org.junit.jupiter.api.Test;

import static io.github.libzeal.zeal.logic.NegatedExpression.*;
import static io.github.libzeal.zeal.logic.test.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class NegatedExpressionTest {

    @Test
    void givenNullWrappedExpression_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new NegatedExpression(null)
        );
    }

    @Test
    void givenTrueExpression_whenEvaluate_thenEvaluationIsCorrect() {

        final Expression trueExpression = Expression.tautology();
        final NegatedExpression expression = new NegatedExpression(trueExpression);
        final Evaluation evaluation = expression.evaluate();
        final Rationale rationale = evaluation.rationale();

        assertEquals(Result.FALSE, evaluation.result());
        assertEquals(NegatedExpression.DEFAULT_NAME, evaluation.name());
        assertRationaleEquals(rationale, ACTUAL_VALUE_WRAPPED_FALSE, ACTUAL_VALUE_WRAPPED_TRUE);
        assertEquals(trueExpression.evaluate(), evaluation.cause().rootCause().evaluation());
        assertDepthFirstTraversalIsTerminal(evaluation);
    }

    @Test
    void givenFalseExpression_whenEvaluate_thenEvaluationIsCorrect() {

        final Expression falseExpression = Expression.contradiction();
        final NegatedExpression expression = new NegatedExpression(falseExpression);
        final Evaluation evaluation = expression.evaluate();
        final Rationale rationale = evaluation.rationale();

        assertEquals(Result.TRUE, evaluation.result());
        assertEquals(NegatedExpression.DEFAULT_NAME, evaluation.name());
        assertRationaleEquals(rationale, ACTUAL_VALUE_WRAPPED_FALSE, ACTUAL_VALUE_WRAPPED_FALSE);
        assertEquals(falseExpression.evaluate(), evaluation.cause().rootCause().evaluation());
        assertDepthFirstTraversalIsTerminal(evaluation);
    }

    @Test
    void givenTrueExpression_whenSkip_thenSkippedEvaluationIsCorrect() {

        final Cause cause = mock(Cause.class);

        final NegatedExpression expression = new NegatedExpression(Expression.tautology());
        final Evaluation skippedEvaluation = expression.skip(cause);
        final Rationale rationale = skippedEvaluation.rationale();

        assertEquals(NegatedExpression.DEFAULT_NAME, skippedEvaluation.name());
        assertEquals(cause, skippedEvaluation.cause().rootCause());
        assertRationaleIsSkipped(rationale);
    }

    @Test
    void givenFalseExpression_whenSkip_thenSkippedEvaluationIsCorrect() {

        final Cause cause = mock(Cause.class);

        final NegatedExpression expression = new NegatedExpression(Expression.contradiction());
        final Evaluation skippedEvaluation = expression.skip(cause);
        final Rationale rationale = skippedEvaluation.rationale();

        assertEquals(NegatedExpression.DEFAULT_NAME, skippedEvaluation.name());
        assertEquals(cause, skippedEvaluation.cause().rootCause());
        assertRationaleIsSkipped(rationale);
    }

    @Test
    void givenSkippedExpression_whenEvaluate_thenEvaluationIsCorrect() {

        final Expression skippedExpression = skippedEvaluation();
        final NegatedExpression expression = new NegatedExpression(skippedExpression);
        final Evaluation evaluation = expression.evaluate();
        final Rationale rationale = evaluation.rationale();

        assertEquals(Result.FALSE, evaluation.result());
        assertEquals(NegatedExpression.DEFAULT_NAME, evaluation.name());
        assertRationaleEquals(rationale, ACTUAL_VALUE_WRAPPED_FALSE, ACTUAL_VALUE_WRAPPED_SKIPPED);
        assertNull(evaluation.cause().rootCause().evaluation());
        assertDepthFirstTraversalIsTerminal(evaluation);
    }

    private static Expression skippedEvaluation() {

        final Cause cause = mock(Cause.class);
        final Expression skippedExpression = mock(Expression.class);

        doReturn(Expression.tautology().skip(cause)).when(skippedExpression).evaluate();

        return skippedExpression;
    }

    @Test
    void givenSkippedExpression_whenSkip_thenSkippedEvaluationIsCorrect() {

        final Cause cause = mock(Cause.class);

        final Expression skippedExpression = skippedEvaluation();
        final NegatedExpression expression = new NegatedExpression(skippedExpression);
        final Evaluation skippedEvaluation = expression.skip(cause);
        final Rationale rationale = skippedEvaluation.rationale();

        assertEquals(NegatedExpression.DEFAULT_NAME, skippedEvaluation.name());
        assertEquals(cause, skippedEvaluation.cause().rootCause());
        assertRationaleIsSkipped(rationale);
    }
}
