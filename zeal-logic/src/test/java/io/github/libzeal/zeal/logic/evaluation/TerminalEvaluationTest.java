package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.cause.CauseGenerator;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TerminalEvaluationTest {

    @Test
    void givenNullName_whenOfTrue_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> TerminalEvaluation.ofTrue(null, mock(Rationale.class))
        );
    }

    @Test
    void givenNullRationale_whenOfTrue_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> TerminalEvaluation.ofTrue("foo", null)
        );
    }

    @Test
    void givenNullName_whenOfFalse_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> TerminalEvaluation.ofFalse(null, mock(Rationale.class))
        );
    }

    @Test
    void givenNullRationale_whenOfFalse_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> TerminalEvaluation.ofFalse("foo", null)
        );
    }

    @Test
    void givenNullName_whenOfSkipped_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> TerminalEvaluation.ofSkipped(null, mock(CauseGenerator.class))
        );
    }

    @Test
    void givenNullCauseGenerator_whenOfSkipped_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> TerminalEvaluation.ofSkipped("foo", null)
        );
    }

    @Test
    void givenTrueEvaluation_thenDataIsCorrect() {

        final String name = "foo";
        final Rationale rationale = mock(Rationale.class);

        final TerminalEvaluation evaluation = TerminalEvaluation.ofTrue(name, rationale);

        assertEquals(Result.TRUE, evaluation.result());
        assertEquals(name, evaluation.name());
        assertEquals(rationale, evaluation.rationale());
        assertEquals(evaluation, evaluation.cause().rootCause().evaluation());
    }

    @Test
    void givenFalseEvaluation_thenDataIsCorrect() {

        final String name = "foo";
        final Rationale rationale = mock(Rationale.class);

        final TerminalEvaluation evaluation = TerminalEvaluation.ofFalse(name, rationale);

        assertEquals(Result.FALSE, evaluation.result());
        assertEquals(name, evaluation.name());
        assertEquals(rationale, evaluation.rationale());
        assertEquals(evaluation, evaluation.cause().rootCause().evaluation());
    }

    @Test
    void givenSkippedEvaluation_thenDataIsCorrect() {

        final String name = "foo";
        final Cause cause = mock(Cause.class);
        final CauseGenerator generator = generator(cause);

        final TerminalEvaluation evaluation = TerminalEvaluation.ofSkipped(name, generator);

        assertEquals(Result.SKIPPED, evaluation.result());
        assertEquals(name, evaluation.name());
        assertEquals(SimpleRationale.skipped(), evaluation.rationale());
        assertEquals(cause, evaluation.cause());
    }

    private static CauseGenerator generator(final Cause cause) {

        final CauseGenerator generator = mock(CauseGenerator.class);

        doReturn(cause).when(generator).generate(any(Evaluation.class));

        return generator;
    }

    @Test
    void givenNullTraverser_whenTraverseDepthFirst_thenNoTraversalPerformed() {

        final Rationale rationale = mock(Rationale.class);

        final TerminalEvaluation evaluation = TerminalEvaluation.ofTrue("foo", rationale);

        assertDoesNotThrow(
            () -> evaluation.traverseDepthFirst(null)
        );
    }

    @Test
    void givenValidTraverser_whenTraverseDepthFirst_thenTraversalPerformed() {

        final Traverser traverser = mock(Traverser.class);
        final Rationale rationale = mock(Rationale.class);

        final TerminalEvaluation evaluation = TerminalEvaluation.ofTrue("foo", rationale);

        evaluation.traverseDepthFirst(traverser);

        verify(traverser, times(1)).on(eq(evaluation), any(TraversalContext.class));
    }

    @Test
    void givenMatchingTrueEvaluations_whenEquals_thenNeverEqual() {

        final String name = "foo";
        final Rationale rationale = mock(Rationale.class);

        final TerminalEvaluation evaluation1 = TerminalEvaluation.ofTrue(name, rationale);
        final TerminalEvaluation evaluation2 = TerminalEvaluation.ofTrue(name, rationale);

        assertNotEquals(evaluation1, evaluation2);
    }

    @Test
    void givenMatchingFalseEvaluations_whenEquals_thenNotEqual() {

        final String name = "foo";
        final Rationale rationale = mock(Rationale.class);

        final TerminalEvaluation evaluation1 = TerminalEvaluation.ofFalse(name, rationale);
        final TerminalEvaluation evaluation2 = TerminalEvaluation.ofFalse(name, rationale);

        assertNotEquals(evaluation1, evaluation2);
    }

    @Test
    void givenMatchingSkippedEvaluations_whenEquals_thenEqual() {

        final String name = "foo";
        final Cause cause = mock(Cause.class);
        final CauseGenerator generator = generator(cause);

        final TerminalEvaluation evaluation1 = TerminalEvaluation.ofSkipped(name, generator);
        final TerminalEvaluation evaluation2 = TerminalEvaluation.ofSkipped(name, generator);

        assertEquals(evaluation1, evaluation2);
    }

    @Test
    void givenNonMatchingNamesSkippedEvaluations_whenEquals_thenNotEqual() {

        final Cause cause = mock(Cause.class);
        final CauseGenerator generator = generator(cause);

        final TerminalEvaluation evaluation1 = TerminalEvaluation.ofSkipped("foo", generator);
        final TerminalEvaluation evaluation2 = TerminalEvaluation.ofSkipped("bar", generator);

        assertNotEquals(evaluation1, evaluation2);
    }

    @Test
    void givenNonMatchingGeneratorSkippedEvaluations_whenEquals_thenNotEqual() {

        final String name = "foo";
        final Cause cause1 = mock(Cause.class);
        final Cause cause2 = mock(Cause.class);
        final CauseGenerator generator1 = generator(cause1);
        final CauseGenerator generator2 = generator(cause2);

        doReturn(cause1).when(generator1).generate(any(Evaluation.class));
        doReturn(cause2).when(generator2).generate(any(Evaluation.class));

        final TerminalEvaluation evaluation1 = TerminalEvaluation.ofSkipped(name, generator1);
        final TerminalEvaluation evaluation2 = TerminalEvaluation.ofSkipped(name, generator2);

        assertNotEquals(evaluation1, evaluation2);
    }

    @Test
    void givenDifferentResultEvaluations_whenEquals_thenNotEqual() {

        final String name = "foo";
        final Cause cause = mock(Cause.class);
        final CauseGenerator generator = generator(cause);

        final TerminalEvaluation evaluation1 = TerminalEvaluation.ofTrue(name, mock(Rationale.class));
        final TerminalEvaluation evaluation2 = TerminalEvaluation.ofSkipped(name, generator);

        assertNotEquals(evaluation1, evaluation2);
    }

    @Test
    void givenDifferentObjects_whenEquals_thenNotEqual() {

        final String name = "foo";

        final TerminalEvaluation evaluation1 = TerminalEvaluation.ofTrue(name, mock(Rationale.class));

        assertNotEquals(evaluation1, new Object());
    }

    @Test
    void givenDifferentResult_whenHashCode_thenHashCodesNotEqual() {

        final String name = "foo";
        final Cause cause = mock(Cause.class);
        final CauseGenerator generator = generator(cause);

        final TerminalEvaluation evaluation1 = TerminalEvaluation.ofTrue(name, mock(Rationale.class));
        final TerminalEvaluation evaluation2 = TerminalEvaluation.ofSkipped(name, generator);

        assertNotEquals(evaluation1.hashCode(), evaluation2.hashCode());
    }

    @Test
    void givenDifferentNames_whenHashCode_thenHashCodesNotEqual() {

        final Rationale rationale = mock(Rationale.class);

        final TerminalEvaluation evaluation1 = TerminalEvaluation.ofTrue("foo", rationale);
        final TerminalEvaluation evaluation2 = TerminalEvaluation.ofTrue("bar", rationale);

        assertNotEquals(evaluation1.hashCode(), evaluation2.hashCode());
    }

    @Test
    void givenDifferentRationale_whenHashCode_thenHashCodesNotEqual() {

        final String name = "foo";

        final TerminalEvaluation evaluation1 = TerminalEvaluation.ofTrue(name, mock(Rationale.class));
        final TerminalEvaluation evaluation2 = TerminalEvaluation.ofTrue(name, mock(Rationale.class));

        assertNotEquals(evaluation1.hashCode(), evaluation2.hashCode());
    }

    @Test
    void givenDifferentGenerators_whenHashCode_thenHashCodesNotEqual() {

        final String name = "foo";
        final Cause cause1 = mock(Cause.class);
        final Cause cause2 = mock(Cause.class);

        final TerminalEvaluation evaluation1 = TerminalEvaluation.ofSkipped(name, generator(cause1));
        final TerminalEvaluation evaluation2 = TerminalEvaluation.ofSkipped(name, generator(cause2));

        assertNotEquals(evaluation1.hashCode(), evaluation2.hashCode());
    }

    @Test
    void givenMatching_whenHashCode_thenHashCodesEqual() {

        final String name = "foo";
        final Cause cause = mock(Cause.class);
        final CauseGenerator generator = generator(cause);

        final TerminalEvaluation evaluation1 = TerminalEvaluation.ofSkipped(name, generator);
        final TerminalEvaluation evaluation2 = TerminalEvaluation.ofSkipped(name, generator);

        assertEquals(evaluation1.hashCode(), evaluation2.hashCode());
    }
}
