package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class TerminalEvaluationTest {

    private static final String NAME = "foo";
    private static final Rationale RATIONALE = new SimpleRationale("expected", "actual");
    private static final Duration ELAPSED_TIME = Duration.ofMillis(7);

    @Test
    void givenNullName_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new TerminalEvaluation(true, null, RATIONALE, ELAPSED_TIME)
        );
    }

    @Test
    void givenNullRationale_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new TerminalEvaluation(true, NAME, null, ELAPSED_TIME)
        );
    }

    @Test
    void givenNullElapsedTime_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new TerminalEvaluation(true, NAME, RATIONALE, null)
        );
    }

    @Test
    void givenTrueResult_whenConstruct_thenDataIsCorrect() {

        final TerminalEvaluation evaluation = new TerminalEvaluation(true, NAME, RATIONALE, ELAPSED_TIME);

        assertTrue(evaluation.isTrue());
        assertFalse(evaluation.isFalse());
        assertEquals(NAME, evaluation.name());
        assertEquals(RATIONALE, evaluation.rationale());
        assertEquals(ELAPSED_TIME, evaluation.elapsedTime());
        assertEquals(Cause.self(), evaluation.cause());
    }

    @Test
    void givenFalseResult_whenConstruct_thenDataIsCorrect() {

        final TerminalEvaluation evaluation = new TerminalEvaluation(false, NAME, RATIONALE, ELAPSED_TIME);

        assertFalse(evaluation.isTrue());
        assertTrue(evaluation.isFalse());
        assertEquals(Cause.self(), evaluation.cause());
    }

    @Test
    void givenMatchingEvaluations_whenEquals_thenEqual() {

        final TerminalEvaluation evaluation1 = new TerminalEvaluation(true, NAME, RATIONALE, ELAPSED_TIME);
        final TerminalEvaluation evaluation2 = new TerminalEvaluation(true, NAME, RATIONALE, ELAPSED_TIME);

        assertEquals(evaluation1, evaluation2);
        assertEquals(evaluation1.hashCode(), evaluation2.hashCode());
    }

    @Test
    void givenDifferentResults_whenEquals_thenNotEqual() {

        final TerminalEvaluation evaluation1 = new TerminalEvaluation(true, NAME, RATIONALE, ELAPSED_TIME);
        final TerminalEvaluation evaluation2 = new TerminalEvaluation(false, NAME, RATIONALE, ELAPSED_TIME);

        assertNotEquals(evaluation1, evaluation2);
    }
}
