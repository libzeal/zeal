package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.test.TestExpressions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static io.github.libzeal.zeal.logic.test.NamedExpressions.*;
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

    @Test
    void givenSelfTrace_whenIsNegated_thenFalse() {
        assertFalse(Cause.Trace.of(FIRST).isNegated());
    }

    @Test
    void givenNotOfTrue_whenIsNegated_thenTrue() {
        assertTrue(onlyTraceOf(not("NOT", t("t1"))).isNegated());
    }

    @Test
    void givenNotOfFalse_whenIsNegated_thenTrue() {
        assertTrue(onlyTraceOf(not("NOT", f("f1"))).isNegated());
    }

    @Test
    void givenDoubleNegation_whenIsNegated_thenFalse() {
        assertFalse(onlyTraceOf(not("NOT1", not("NOT2", f("f1")))).isNegated());
    }

    @Test
    void givenTripleNegation_whenIsNegated_thenTrue() {
        assertTrue(onlyTraceOf(not("NOT1", not("NOT2", not("NOT3", t("t1"))))).isNegated());
    }

    @Test
    void givenAndOfFalse_whenIsNegated_thenFalse() {
        assertFalse(onlyTraceOf(and("AND", t("t1"), f("f2"))).isNegated());
    }

    @Test
    void givenNorWithTrueChild_whenIsNegated_thenTrue() {
        assertTrue(onlyTraceOf(nor("NOR", f("f1"), t("t2"))).isNegated());
    }

    @Test
    void givenNandOfTrueChildren_whenIsNegated_thenFalse() {
        assertFalse(onlyTraceOf(nand("NAND", t("t1"), t("t2"))).isNegated());
    }

    private static Cause.Trace onlyTraceOf(final Expression expression) {

        final List<Cause.Trace> traces = expression.evaluate().cause().traces();

        assertEquals(1, traces.size());

        return traces.getFirst();
    }

    private static Evaluation evaluation(final String name) {
        return new TerminalEvaluation(false, name, new SimpleRationale("expected", "actual"), Duration.ZERO);
    }
}
