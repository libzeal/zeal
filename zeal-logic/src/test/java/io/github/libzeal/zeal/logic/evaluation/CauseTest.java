package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CauseTest {

    private static final Evaluation FIRST = evaluation("first");
    private static final Evaluation SECOND = evaluation("second");

    @Test
    void whenSelf_thenSelfReturned() {
        assertInstanceOf(Cause.Self.class, Cause.self());
    }

    @Test
    void givenEvaluation_whenOf_thenDelegatedToEvaluation() {

        final Cause.Delegated cause = assertInstanceOf(Cause.Delegated.class, Cause.of(FIRST));

        assertEquals(List.of(FIRST), cause.evaluations());
    }

    @Test
    void givenEvaluations_whenOf_thenDelegatedToEvaluations() {

        final Cause.Delegated cause = assertInstanceOf(Cause.Delegated.class, Cause.of(List.of(FIRST, SECOND)));

        assertEquals(List.of(FIRST, SECOND), cause.evaluations());
    }

    @Test
    void givenNullEvaluation_whenOf_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Cause.of((Evaluation) null)
        );
    }

    @Test
    void givenNullEvaluations_whenConstructDelegated_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new Cause.Delegated(null)
        );
    }

    @Test
    void givenNullElement_whenConstructDelegated_thenExceptionThrown() {

        final List<Evaluation> evaluations = Arrays.asList(FIRST, null);

        assertThrows(
            NullPointerException.class,
            () -> new Cause.Delegated(evaluations)
        );
    }

    @Test
    void givenNoEvaluations_whenConstructDelegated_thenExceptionThrown() {

        final List<Evaluation> evaluations = List.of();

        assertThrows(
            IllegalArgumentException.class,
            () -> new Cause.Delegated(evaluations)
        );
    }

    @Test
    void givenMutableEvaluations_whenConstructDelegated_thenEvaluationsAreCopied() {

        final List<Evaluation> evaluations = new ArrayList<>(List.of(FIRST));

        final Cause.Delegated cause = new Cause.Delegated(evaluations);

        evaluations.add(SECOND);

        assertEquals(List.of(FIRST), cause.evaluations());
    }

    @Test
    void givenNoEvaluations_whenConstructTrace_thenExceptionThrown() {

        final List<Evaluation> evaluations = List.of();

        assertThrows(
            IllegalArgumentException.class,
            () -> new Cause.Trace(evaluations)
        );
    }

    @Test
    void givenNullEvaluations_whenConstructTrace_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new Cause.Trace(null)
        );
    }

    @Test
    void givenMutableEvaluations_whenConstructTrace_thenEvaluationsAreCopied() {

        final List<Evaluation> evaluations = new ArrayList<>(List.of(FIRST));

        final Cause.Trace trace = new Cause.Trace(evaluations);

        evaluations.add(SECOND);

        assertEquals(List.of(FIRST), trace.evaluations());
    }

    @Test
    void givenRootCause_whenOf_thenTraceContainsOnlyRootCause() {

        final Cause.Trace trace = Cause.Trace.of(FIRST);

        assertEquals(List.of(FIRST), trace.evaluations());
        assertSame(FIRST, trace.evaluation());
        assertSame(FIRST, trace.rootCause());
    }

    @Test
    void givenTrace_whenPrepend_thenEvaluationIsFirst() {

        final Cause.Trace trace = Cause.Trace.of(SECOND).prepend(FIRST);

        assertEquals(List.of(FIRST, SECOND), trace.evaluations());
        assertSame(FIRST, trace.evaluation());
        assertSame(SECOND, trace.rootCause());
    }

    @Test
    void givenSingleEvaluationTrace_whenToString_thenNameReturned() {
        assertEquals("first", Cause.Trace.of(FIRST).toString());
    }

    @Test
    void givenMultipleEvaluationTrace_whenToString_thenNamesJoined() {
        assertEquals("first -> second", new Cause.Trace(List.of(FIRST, SECOND)).toString());
    }

    private static Evaluation evaluation(final String name) {
        return TerminalEvaluation.of(Result.FALSE, name, new SimpleRationale("expected", "actual"), Duration.ZERO);
    }
}
