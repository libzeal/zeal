package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompoundEvaluationTest {

    private static final String NAME = "foo";
    private static final Rationale RATIONALE = new SimpleRationale("expected", "actual");
    private static final Duration ELAPSED_TIME = Duration.ofMillis(7);
    private static final Evaluation CHILD = TerminalEvaluation.of(Result.FALSE, "child", RATIONALE, ELAPSED_TIME);
    private static final List<Evaluation> CHILDREN = List.of(CHILD);

    @Test
    void givenNullResult_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new CompoundEvaluation(null, NAME, RATIONALE, ELAPSED_TIME, Cause.self(), CHILDREN)
        );
    }

    @Test
    void givenNullName_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new CompoundEvaluation(Result.TRUE, null, RATIONALE, ELAPSED_TIME, Cause.self(), CHILDREN)
        );
    }

    @Test
    void givenNullRationale_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new CompoundEvaluation(Result.TRUE, NAME, null, ELAPSED_TIME, Cause.self(), CHILDREN)
        );
    }

    @Test
    void givenNullElapsedTime_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new CompoundEvaluation(Result.TRUE, NAME, RATIONALE, null, Cause.self(), CHILDREN)
        );
    }

    @Test
    void givenNullCause_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new CompoundEvaluation(Result.TRUE, NAME, RATIONALE, ELAPSED_TIME, null, CHILDREN)
        );
    }

    @Test
    void givenNullChildren_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new CompoundEvaluation(Result.TRUE, NAME, RATIONALE, ELAPSED_TIME, Cause.self(), null)
        );
    }

    @Test
    void givenNullChild_whenConstruct_thenExceptionThrown() {

        final List<Evaluation> children = Arrays.asList(CHILD, null);

        assertThrows(
            NullPointerException.class,
            () -> new CompoundEvaluation(Result.TRUE, NAME, RATIONALE, ELAPSED_TIME, Cause.self(), children)
        );
    }

    @Test
    void givenValidData_whenConstruct_thenDataIsCorrect() {

        final Cause cause = Cause.of(CHILD);

        final CompoundEvaluation evaluation = new CompoundEvaluation(Result.FALSE, NAME, RATIONALE, ELAPSED_TIME,
            cause, CHILDREN);

        assertEquals(Result.FALSE, evaluation.result());
        assertEquals(NAME, evaluation.name());
        assertEquals(RATIONALE, evaluation.rationale());
        assertEquals(ELAPSED_TIME, evaluation.elapsedTime());
        assertEquals(cause, evaluation.cause());
        assertEquals(CHILDREN, evaluation.children());
    }

    @Test
    void givenMutableChildren_whenConstruct_thenChildrenAreCopied() {

        final List<Evaluation> children = new ArrayList<>(CHILDREN);

        final CompoundEvaluation evaluation = new CompoundEvaluation(Result.FALSE, NAME, RATIONALE, ELAPSED_TIME,
            Cause.self(), children);

        children.clear();

        assertEquals(CHILDREN, evaluation.children());
        assertThrows(UnsupportedOperationException.class, () -> evaluation.children().clear());
    }

    @Test
    void givenDecider_whenSkipped_thenDataIsCorrect() {

        final Evaluation decider = TerminalEvaluation.of(Result.FALSE, "decider", RATIONALE, ELAPSED_TIME);

        final CompoundEvaluation evaluation = CompoundEvaluation.skipped(NAME, decider, CHILDREN);

        assertEquals(Result.SKIPPED, evaluation.result());
        assertEquals(NAME, evaluation.name());
        assertEquals(SimpleRationale.skipped(), evaluation.rationale());
        assertEquals(Duration.ZERO, evaluation.elapsedTime());
        assertEquals(Cause.of(decider), evaluation.cause());
        assertEquals(CHILDREN, evaluation.children());
    }
}
