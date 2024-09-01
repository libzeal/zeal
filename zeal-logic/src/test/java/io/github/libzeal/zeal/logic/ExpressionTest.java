package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import org.junit.jupiter.api.Test;

import static io.github.libzeal.zeal.logic.test.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class ExpressionTest {

    private static final String TRUE = "true";
    private static final String FALSE = "false";

    @Test
    void givenContradiction_whenName_thenNameIsCorrect() {
        assertEquals(Contradiction.NAME, Expression.contradiction().name());
    }

    @Test
    void givenContradiction_whenEvaluate_thenEvaluationIsCorrect() {

        final Expression expression = Expression.contradiction();
        final Evaluation evaluation = expression.evaluate();
        final Rationale rationale = evaluation.rationale();

        assertEquals(Result.FALSE, evaluation.result());
        assertEquals(Contradiction.NAME, evaluation.name());
        assertRationaleEquals(rationale, FALSE, FALSE);
        assertEquals(evaluation, evaluation.cause().evaluation());
        assertDepthFirstTraversalIsTerminal(evaluation);
    }

    @Test
    void givenContradiction_whenSkip_thenSkippedEvaluationIsCorrect() {

        final Cause cause = mock(Cause.class);

        final Expression expression = Expression.contradiction();
        final Evaluation skippedEvaluation = expression.skip(cause);
        final Rationale rationale = skippedEvaluation.rationale();

        assertEquals(Contradiction.NAME, skippedEvaluation.name());
        assertEquals(cause, skippedEvaluation.cause().rootCause());
        assertRationaleIsSkipped(rationale);
    }

    @Test
    void givenTautology_whenName_thenNameIsCorrect() {
        assertEquals(Tautology.NAME, Expression.tautology().name());
    }

    @Test
    void givenTautology_whenEvaluate_thenEvaluationIsCorrect() {

        final Expression expression = Expression.tautology();
        final Evaluation evaluation = expression.evaluate();
        final Rationale rationale = evaluation.rationale();

        assertEquals(Result.TRUE, evaluation.result());
        assertEquals(Tautology.NAME, evaluation.name());
        assertRationaleEquals(rationale, TRUE, TRUE);
        assertEquals(evaluation, evaluation.cause().evaluation());
        assertDepthFirstTraversalIsTerminal(evaluation);
    }

    @Test
    void givenTautology_whenSkip_thenSkippedEvaluationIsCorrect() {

        final Cause cause = mock(Cause.class);

        final Expression expression = Expression.tautology();
        final Evaluation skippedEvaluation = expression.skip(cause);
        final Rationale rationale = skippedEvaluation.rationale();

        assertEquals(Tautology.NAME, skippedEvaluation.name());
        assertEquals(cause, skippedEvaluation.cause().rootCause());
        assertRationaleIsSkipped(rationale);
    }
}
