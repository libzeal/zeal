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
    void givenNullResult_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new TerminalEvaluation(null, NAME, RATIONALE, ELAPSED_TIME, Cause.self())
        );
    }

    @Test
    void givenNullName_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new TerminalEvaluation(Result.TRUE, null, RATIONALE, ELAPSED_TIME, Cause.self())
        );
    }

    @Test
    void givenNullRationale_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new TerminalEvaluation(Result.TRUE, NAME, null, ELAPSED_TIME, Cause.self())
        );
    }

    @Test
    void givenNullElapsedTime_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new TerminalEvaluation(Result.TRUE, NAME, RATIONALE, null, Cause.self())
        );
    }

    @Test
    void givenNullCause_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new TerminalEvaluation(Result.TRUE, NAME, RATIONALE, ELAPSED_TIME, null)
        );
    }

    @Test
    void givenTrueResult_whenOf_thenDataIsCorrect() {

        final TerminalEvaluation evaluation = TerminalEvaluation.of(Result.TRUE, NAME, RATIONALE, ELAPSED_TIME);

        assertEquals(Result.TRUE, evaluation.result());
        assertEquals(NAME, evaluation.name());
        assertEquals(RATIONALE, evaluation.rationale());
        assertEquals(ELAPSED_TIME, evaluation.elapsedTime());
        assertEquals(Cause.self(), evaluation.cause());
    }

    @Test
    void givenFalseResult_whenOf_thenDataIsCorrect() {

        final TerminalEvaluation evaluation = TerminalEvaluation.of(Result.FALSE, NAME, RATIONALE, ELAPSED_TIME);

        assertEquals(Result.FALSE, evaluation.result());
        assertEquals(Cause.self(), evaluation.cause());
    }

    @Test
    void givenDecider_whenSkipped_thenDataIsCorrect() {

        final TerminalEvaluation decider = TerminalEvaluation.of(Result.FALSE, "decider", RATIONALE, ELAPSED_TIME);

        final TerminalEvaluation evaluation = TerminalEvaluation.skipped(NAME, decider);

        assertEquals(Result.SKIPPED, evaluation.result());
        assertEquals(NAME, evaluation.name());
        assertEquals(SimpleRationale.skipped(), evaluation.rationale());
        assertEquals(Duration.ZERO, evaluation.elapsedTime());
        assertEquals(Cause.of(decider), evaluation.cause());
    }

    @Test
    void givenNullDecider_whenSkipped_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> TerminalEvaluation.skipped(NAME, null)
        );
    }

    @Test
    void givenMatchingEvaluations_whenEquals_thenEqual() {

        final TerminalEvaluation evaluation1 = TerminalEvaluation.of(Result.TRUE, NAME, RATIONALE, ELAPSED_TIME);
        final TerminalEvaluation evaluation2 = TerminalEvaluation.of(Result.TRUE, NAME, RATIONALE, ELAPSED_TIME);

        assertEquals(evaluation1, evaluation2);
        assertEquals(evaluation1.hashCode(), evaluation2.hashCode());
    }

    @Test
    void givenDifferentResults_whenEquals_thenNotEqual() {

        final TerminalEvaluation evaluation1 = TerminalEvaluation.of(Result.TRUE, NAME, RATIONALE, ELAPSED_TIME);
        final TerminalEvaluation evaluation2 = TerminalEvaluation.of(Result.FALSE, NAME, RATIONALE, ELAPSED_TIME);

        assertNotEquals(evaluation1, evaluation2);
    }
}
