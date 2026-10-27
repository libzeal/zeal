package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.BooleanExpression;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Evaluated;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Skipped;
import io.github.libzeal.zeal.logic.test.TestExpressions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SkippableEvaluationTest {

    private static final Evaluation DECIDER = TestExpressions.evaluation("decider", false);
    private static final Expression EXPRESSION = new BooleanExpression("skipped", () -> true);

    @Test
    void givenNullEvaluation_whenEvaluated_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new Evaluated(null)
        );
    }

    @Test
    void givenEvaluation_whenEvaluated_thenDelegatesToEvaluation() {

        final Evaluated evaluated = new Evaluated(DECIDER);

        assertEquals(DECIDER.name(), evaluated.name());
        assertEquals(DECIDER.cause(), evaluated.cause());
        assertEquals(DECIDER.causeTraces(), evaluated.causeTraces());
        assertEquals(DECIDER.rootCauses(), evaluated.rootCauses());
    }

    @Test
    void givenNullExpression_whenSkipped_thenExceptionThrown() {

        final Cause.Delegated cause = Cause.of(DECIDER);

        assertThrows(
            NullPointerException.class,
            () -> new Skipped(null, cause)
        );
    }

    @Test
    void givenNullCause_whenSkipped_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new Skipped(EXPRESSION, null)
        );
    }

    @Test
    void givenTerminalDecider_whenSkipped_thenTracesStartAtDecider() {

        final Skipped skipped = new Skipped(EXPRESSION, Cause.of(DECIDER));

        assertEquals("skipped", skipped.name());
        assertEquals(Cause.of(DECIDER), skipped.cause());
        assertEquals(List.of(Cause.Trace.of(DECIDER)), skipped.causeTraces());
        assertEquals(List.of(DECIDER), skipped.rootCauses());
    }

    @Test
    void givenCompoundDecider_whenSkipped_thenTracesReachDeciderRootCause() {

        final Evaluation rootCause = TestExpressions.evaluation("root", false);
        final Evaluation decider = TestExpressions.compound(false, "decider", Cause.of(rootCause), rootCause);
        final Skipped skipped = new Skipped(EXPRESSION, Cause.of(decider));

        assertEquals(List.of(new Cause.Trace(List.of(decider, rootCause))), skipped.causeTraces());
        assertEquals(List.of(rootCause), skipped.rootCauses());
    }
}
