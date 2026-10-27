package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import io.github.libzeal.zeal.logic.test.TestExpressions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EvaluationTest {

    @Test
    void givenSelfCause_whenCauseTraces_thenTraceContainsOnlyEvaluation() {

        final Evaluation evaluation = terminal("a");

        assertEquals(List.of(Cause.Trace.of(evaluation)), evaluation.causeTraces());
    }

    @Test
    void givenSelfCause_whenRootCauses_thenEvaluationReturned() {

        final Evaluation evaluation = terminal("a");

        assertSame(evaluation, evaluation.rootCauses().getFirst());
        assertEquals(1, evaluation.rootCauses().size());
    }

    @Test
    void givenSingleDelegatedCause_whenCauseTraces_thenTraceEndsAtCause() {

        final Evaluation child = terminal("child");
        final Evaluation parent = compound("parent", Cause.of(child), child);

        assertEquals(List.of(new Cause.Trace(List.of(parent, child))), parent.causeTraces());
        assertSame(child, parent.rootCauses().getFirst());
    }

    @Test
    void givenMultipleDelegatedCauses_whenCauseTraces_thenOneTracePerCauseInOrder() {

        final Evaluation first = terminal("first");
        final Evaluation second = terminal("second");
        final Evaluation parent = compound("parent", Cause.of(List.of(first, second)), first, second);

        assertEquals(
            List.of(
                new Cause.Trace(List.of(parent, first)),
                new Cause.Trace(List.of(parent, second))
            ),
            parent.causeTraces()
        );
        assertEquals(List.of(first, second), parent.rootCauses());
    }

    @Test
    void givenNestedDelegatedCauses_whenCauseTraces_thenTracesBranchAtEachLevel() {

        final Evaluation a = terminal("a");
        final Evaluation b = terminal("b");
        final Evaluation c = terminal("c");
        final Evaluation inner = compound("inner", Cause.of(List.of(a, b)), a, b);
        final Evaluation outer = compound("outer", Cause.of(List.of(inner, c)), inner, c);

        final List<String> traces = outer.causeTraces()
            .stream()
            .map(Cause.Trace::toString)
            .toList();

        assertEquals(List.of("outer -> inner -> a", "outer -> inner -> b", "outer -> c"), traces);
        assertEquals(List.of(a, b, c), outer.rootCauses());
    }

    private static Evaluation terminal(final String name) {
        return new TerminalEvaluation(false, name, new SimpleRationale("expected", "actual"), Duration.ZERO);
    }

    private static Evaluation compound(final String name, final Cause cause, final Evaluation... children) {
        return TestExpressions.compound(false, name, cause, children);
    }
}
