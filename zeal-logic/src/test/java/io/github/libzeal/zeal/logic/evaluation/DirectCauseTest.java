package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DirectCauseTest {

    private static final Evaluation FIRST = evaluation("first");
    private static final Evaluation SECOND = evaluation("second");

    @Test
    void whenSelf_thenSelfReturned() {
        assertInstanceOf(DirectCause.Self.class, DirectCause.self());
    }

    @Test
    void givenEvaluation_whenOf_thenDelegatedToEvaluation() {

        final DirectCause.Delegated cause = assertInstanceOf(DirectCause.Delegated.class, DirectCause.of(FIRST));

        assertEquals(List.of(FIRST), cause.evaluations());
    }

    @Test
    void givenEvaluations_whenOf_thenDelegatedToEvaluations() {

        final DirectCause.Delegated cause = assertInstanceOf(DirectCause.Delegated.class,
            DirectCause.of(List.of(FIRST, SECOND)));

        assertEquals(List.of(FIRST, SECOND), cause.evaluations());
    }

    @Test
    void givenNullEvaluation_whenOf_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> DirectCause.of((Evaluation) null)
        );
    }

    @Test
    void givenNullEvaluations_whenConstructDelegated_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new DirectCause.Delegated(null)
        );
    }

    @Test
    void givenNullElement_whenConstructDelegated_thenExceptionThrown() {

        final List<Evaluation> evaluations = Arrays.asList(FIRST, null);

        assertThrows(
            NullPointerException.class,
            () -> new DirectCause.Delegated(evaluations)
        );
    }

    @Test
    void givenNoEvaluations_whenConstructDelegated_thenExceptionThrown() {

        final List<Evaluation> evaluations = List.of();

        assertThrows(
            IllegalArgumentException.class,
            () -> new DirectCause.Delegated(evaluations)
        );
    }

    @Test
    void givenMutableEvaluations_whenConstructDelegated_thenEvaluationsAreCopied() {

        final List<Evaluation> evaluations = new ArrayList<>(List.of(FIRST));

        final DirectCause.Delegated cause = new DirectCause.Delegated(evaluations);

        evaluations.add(SECOND);

        assertEquals(List.of(FIRST), cause.evaluations());
    }

    private static Evaluation evaluation(final String name) {
        return new TerminalEvaluation(false, name, new SimpleRationale("expected", "actual"), Duration.ZERO);
    }
}
