package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import io.github.libzeal.zeal.logic.test.TestExpressions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CauseTest {

    private static final Evaluation FIRST = evaluation("first");
    private static final Evaluation SECOND = evaluation("second");
    private static final Evaluation OWNER = evaluation("owner");

    @Test
    void givenNullEvaluation_whenConstruct_thenExceptionThrown() {

        final DirectCause direct = DirectCause.self();

        assertThrows(
            NullPointerException.class,
            () -> new Cause(null, direct)
        );
    }

    @Test
    void givenNullDirect_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new Cause(FIRST, null)
        );
    }

    @Test
    void givenSelf_whenTraces_thenTraceAndRootCauseAreEvaluation() {

        final Cause cause = new Cause(FIRST, DirectCause.self());

        assertEquals(DirectCause.self(), cause.direct());
        assertEquals(List.of(Cause.Trace.of(FIRST)), cause.traces());
        assertEquals(List.of(FIRST), cause.rootCauses());
    }

    @Test
    void givenDelegatedToTerminals_whenTraces_thenOneTracePerEvaluationStartingAtOwner() {

        final DirectCause.Delegated direct = DirectCause.of(List.of(FIRST, SECOND));
        final Cause cause = new Cause(OWNER, direct);

        assertEquals(direct, cause.direct());
        assertEquals(
            List.of(
                new Cause.Trace(List.of(OWNER, FIRST)),
                new Cause.Trace(List.of(OWNER, SECOND))
            ),
            cause.traces()
        );
        assertEquals(List.of(FIRST, SECOND), cause.rootCauses());
    }

    @Test
    void givenDelegatedToCompound_whenTraces_thenTracesFollowCompoundCause() {

        final Evaluation compound = TestExpressions.compound(false, "compound", DirectCause.of(FIRST), FIRST);
        final Cause cause = new Cause(OWNER, DirectCause.of(compound));

        assertEquals(List.of(new Cause.Trace(List.of(OWNER, compound, FIRST))), cause.traces());
        assertEquals(List.of(FIRST), cause.rootCauses());
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
        return new TerminalEvaluation(false, name, new SimpleRationale("expected", "actual"), Duration.ZERO);
    }
}
