package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;
import io.github.libzeal.zeal.logic.evaluation.DirectCause;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Rationale;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Evaluated;
import io.github.libzeal.zeal.logic.test.TestExpressions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.libzeal.zeal.logic.NotExpression.ACTUAL_VALUE_WRAPPED_FALSE;
import static io.github.libzeal.zeal.logic.NotExpression.ACTUAL_VALUE_WRAPPED_TRUE;
import static io.github.libzeal.zeal.logic.test.Assertions.assertRationaleEquals;
import static io.github.libzeal.zeal.logic.test.CauseAssertions.*;
import static io.github.libzeal.zeal.logic.test.NamedExpressions.*;
import static org.junit.jupiter.api.Assertions.*;

class NotExpressionTest {

    @Test
    void givenNullWrappedExpression_whenConstruct_thenExceptionThrown() {
        assertThrows(
                NullPointerException.class,
                () -> NotExpression.unnamed(null)
        );
    }

    @Test
    void givenTrueExpression_whenEvaluate_thenEvaluationIsCorrect() {

        final Expression trueExpression = TestExpressions.tautology();
        final NotExpression expression = NotExpression.unnamed(trueExpression);
        final Evaluation evaluation = expression.evaluate();
        final Rationale rationale = evaluation.rationale();
        final Evaluation wrappedEvaluation = trueExpression.evaluate();

        assertFalse(evaluation.isTrue());
        assertEquals(NotExpression.DEFAULT_NAME, evaluation.name());
        assertRationaleEquals(rationale, ACTUAL_VALUE_WRAPPED_FALSE, ACTUAL_VALUE_WRAPPED_TRUE);
        assertEquals(DirectCause.of(wrappedEvaluation), evaluation.cause().direct());
        assertEquals(List.of(new Evaluated(wrappedEvaluation)), ((CompoundEvaluation) evaluation).children());
    }

    @Test
    void givenFalseExpression_whenEvaluate_thenEvaluationIsCorrect() {

        final Expression falseExpression = TestExpressions.contradiction();
        final NotExpression expression = NotExpression.unnamed(falseExpression);
        final Evaluation evaluation = expression.evaluate();
        final Rationale rationale = evaluation.rationale();
        final Evaluation wrappedEvaluation = falseExpression.evaluate();

        assertTrue(evaluation.isTrue());
        assertEquals(NotExpression.DEFAULT_NAME, evaluation.name());
        assertRationaleEquals(rationale, ACTUAL_VALUE_WRAPPED_FALSE, ACTUAL_VALUE_WRAPPED_FALSE);
        assertEquals(DirectCause.of(wrappedEvaluation), evaluation.cause().direct());
        assertEquals(List.of(new Evaluated(wrappedEvaluation)), ((CompoundEvaluation) evaluation).children());
    }

    @Test
    void givenNotOfTrue_whenEvaluate_thenCauseIsWrapped() {

        final Evaluation evaluation = not("NOT", t("t")).evaluate();

        assertFalse(evaluation.isTrue());
        assertCause(evaluation, child(evaluation, 0));
        assertTraces(evaluation, "NOT -> t");
    }

    @Test
    void givenNotOfFalse_whenEvaluate_thenCauseIsWrapped() {

        final Evaluation evaluation = not("NOT", f("f")).evaluate();

        assertTrue(evaluation.isTrue());
        assertCause(evaluation, child(evaluation, 0));
        assertTraces(evaluation, "NOT -> f");
    }

    @Test
    void givenDoubleNegation_whenEvaluate_thenTraceFollowsBothNots() {

        final Evaluation evaluation = not("NOT1", not("NOT2", f("f"))).evaluate();

        assertFalse(evaluation.isTrue());
        assertTraces(evaluation, "NOT1 -> NOT2 -> f");
        assertRootCauses(evaluation, child(evaluation, 0, 0));
    }

    @Test
    void givenNotOfTrueAnd_whenEvaluate_thenTraceEndsAtAnd() {

        final Evaluation evaluation = not("NOT", and("AND", t("t1"), t("t2"))).evaluate();

        assertFalse(evaluation.isTrue());
        assertTraces(evaluation, "NOT -> AND");
    }

    @Test
    void givenNotOfFalseAnd_whenEvaluate_thenTraceFollowsFirstFalse() {

        final Evaluation evaluation = not("NOT", and("AND", t("t1"), f("f2"))).evaluate();

        assertTrue(evaluation.isTrue());
        assertTraces(evaluation, "NOT -> AND -> f2");
    }

    @Test
    void givenNotOfTrueOr_whenEvaluate_thenTraceFollowsFirstTrue() {

        final Evaluation evaluation = not("NOT", or("OR", f("f1"), t("t2"))).evaluate();

        assertFalse(evaluation.isTrue());
        assertTraces(evaluation, "NOT -> OR -> t2");
    }

    @Test
    void givenNotOfFalseOr_whenEvaluate_thenTraceEndsAtOr() {

        final Evaluation evaluation = not("NOT", or("OR", f("f1"), f("f2"))).evaluate();

        assertTrue(evaluation.isTrue());
        assertTraces(evaluation, "NOT -> OR");
    }

    @Test
    void givenNameAndWrapped_whenNamed_thenNameAndWrappedUsed() {

        final BooleanExpression wrapped = new BooleanExpression(() -> true);

        final NotExpression expression = NotExpression.named("foo", wrapped);

        assertEquals("foo", expression.name());
        assertEquals(List.of(wrapped), expression.children());
    }

    @Test
    void givenNullName_whenNamed_thenExceptionThrown() {
        assertThrows(NullPointerException.class, () -> NotExpression.named(null, new BooleanExpression(() -> true)));
    }
}
