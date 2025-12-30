package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.cause.CauseGenerator;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TerminalEvaluationTest {

    @Test
    void givenNullName_whenOfTrue_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> EvaluatedTerminalEvaluation.ofTrue(null, mock(Rationale.class), Duration.ZERO)
        );
    }

    @Test
    void givenNullRationale_whenOfTrue_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> EvaluatedTerminalEvaluation.ofTrue("foo", null, Duration.ZERO)
        );
    }

    @Test
    void givenNullElapsedTime_whenOfTrue_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> EvaluatedTerminalEvaluation.ofTrue("foo", mock(Rationale.class), null)
        );
    }

    @Test
    void givenNullName_whenOfFalse_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> EvaluatedTerminalEvaluation.ofFalse(null, mock(Rationale.class), Duration.ZERO)
        );
    }

    @Test
    void givenNullRationale_whenOfFalse_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> EvaluatedTerminalEvaluation.ofFalse("foo", null, Duration.ZERO)
        );
    }

    @Test
    void givenNullElapsedTime_whenOfFalse_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> EvaluatedTerminalEvaluation.ofFalse("foo", mock(Rationale.class), null)
        );
    }

    @Test
    void givenTrueEvaluation_thenDataIsCorrect() {

        final String name = "foo";
        final Rationale rationale = mock(Rationale.class);
        final long elapsedTimeMs = 7;

        final EvaluatedTerminalEvaluation evaluation = EvaluatedTerminalEvaluation.ofTrue(name, rationale, Duration.ofMillis(elapsedTimeMs));

        assertEquals(Result.TRUE, evaluation.result());
        assertEquals(name, evaluation.name());
        assertEquals(rationale, evaluation.rationale());
        assertEquals(evaluation, evaluation.cause().rootCause().evaluation());
        assertEquals(Duration.ofMillis(elapsedTimeMs), evaluation.elapsedTime());
    }

    @Test
    void givenFalseEvaluation_thenDataIsCorrect() {

        final String name = "foo";
        final Rationale rationale = mock(Rationale.class);
        final long elapsedTimeMs = 7;

        final EvaluatedTerminalEvaluation evaluation = EvaluatedTerminalEvaluation.ofFalse(name, rationale, Duration.ofMillis(elapsedTimeMs));

        assertEquals(Result.FALSE, evaluation.result());
        assertEquals(name, evaluation.name());
        assertEquals(rationale, evaluation.rationale());
        assertEquals(evaluation, evaluation.cause().rootCause().evaluation());
        assertEquals(Duration.ofMillis(elapsedTimeMs), evaluation.elapsedTime());
    }

    private static CauseGenerator generator(final Cause cause) {

        final CauseGenerator generator = mock(CauseGenerator.class);

        doReturn(cause).when(generator).generate(any(Evaluation.class));

        return generator;
    }

    @Test
    void givenMatchingTrueEvaluations_whenEquals_thenNeverEqual() {

        final String name = "foo";
        final Rationale rationale = mock(Rationale.class);

        final EvaluatedTerminalEvaluation evaluation1 = EvaluatedTerminalEvaluation.ofTrue(name, rationale, Duration.ofMillis(7));
        final EvaluatedTerminalEvaluation evaluation2 = EvaluatedTerminalEvaluation.ofTrue(name, rationale, Duration.ofMillis(7));

        assertNotEquals(evaluation1, evaluation2);
    }

    @Test
    void givenMatchingFalseEvaluations_whenEquals_thenNotEqual() {

        final String name = "foo";
        final Rationale rationale = mock(Rationale.class);

        final EvaluatedTerminalEvaluation evaluation1 = EvaluatedTerminalEvaluation.ofFalse(name, rationale, Duration.ofMillis(7));
        final EvaluatedTerminalEvaluation evaluation2 = EvaluatedTerminalEvaluation.ofFalse(name, rationale, Duration.ofMillis(7));

        assertNotEquals(evaluation1, evaluation2);
    }

    @Test
    void givenDifferentObjects_whenEquals_thenNotEqual() {

        final String name = "foo";

        final EvaluatedTerminalEvaluation evaluation1 = EvaluatedTerminalEvaluation.ofTrue(name, mock(Rationale.class), Duration.ofMillis(7));

        assertNotEquals(evaluation1, new Object());
    }

    @Test
    void givenDifferentResult_whenHashCode_thenHashCodesNotEqual() {

        final String name = "foo";
        final Cause cause = mock(Cause.class);
        final CauseGenerator generator = generator(cause);

        final EvaluatedTerminalEvaluation evaluation1 = EvaluatedTerminalEvaluation.ofTrue(name, mock(Rationale.class), Duration.ofMillis(7));
        final EvaluatedTerminalEvaluation evaluation2 = EvaluatedTerminalEvaluation.ofFalse(name, mock(Rationale.class), Duration.ofMillis(7));

        assertNotEquals(evaluation1.hashCode(), evaluation2.hashCode());
    }

    @Test
    void givenDifferentNames_whenHashCode_thenHashCodesNotEqual() {

        final Rationale rationale = mock(Rationale.class);

        final EvaluatedTerminalEvaluation evaluation1 = EvaluatedTerminalEvaluation.ofTrue("foo", rationale, Duration.ofMillis(7));
        final EvaluatedTerminalEvaluation evaluation2 = EvaluatedTerminalEvaluation.ofTrue("bar", rationale, Duration.ofMillis(7));

        assertNotEquals(evaluation1.hashCode(), evaluation2.hashCode());
    }

    @Test
    void givenDifferentRationale_whenHashCode_thenHashCodesNotEqual() {

        final String name = "foo";

        final EvaluatedTerminalEvaluation evaluation1 = EvaluatedTerminalEvaluation.ofTrue(name, mock(Rationale.class), Duration.ofMillis(7));
        final EvaluatedTerminalEvaluation evaluation2 = EvaluatedTerminalEvaluation.ofTrue(name, mock(Rationale.class), Duration.ofMillis(7));

        assertNotEquals(evaluation1.hashCode(), evaluation2.hashCode());
    }

    @Test
    void givenDifferentGenerators_whenHashCode_thenHashCodesNotEqual() {

        final String name = "foo";
        final Cause cause1 = mock(Cause.class);
        final Cause cause2 = mock(Cause.class);

        final EvaluatedTerminalEvaluation evaluation1 = new EvaluatedTerminalEvaluation(Result.TRUE, name, mock(Rationale.class),
            Duration.ofMillis(7), generator(cause1));
        final EvaluatedTerminalEvaluation evaluation2 = new EvaluatedTerminalEvaluation(Result.TRUE, name, mock(Rationale.class),
            Duration.ofMillis(7), generator(cause2));

        assertNotEquals(evaluation1.hashCode(), evaluation2.hashCode());
    }

    @Test
    void givenMatching_whenHashCode_thenHashCodesEqual() {

        final String name = "foo";
        final Rationale rationale = mock(Rationale.class);
        final Duration duration = Duration.ofSeconds(1);
        final CauseGenerator causeGenerator = mock(CauseGenerator.class);

        final EvaluatedTerminalEvaluation evaluation1 = new EvaluatedTerminalEvaluation(Result.TRUE, name, rationale, duration, causeGenerator);
        final EvaluatedTerminalEvaluation evaluation2 = new EvaluatedTerminalEvaluation(Result.TRUE, name, rationale, duration, causeGenerator);

        assertEquals(evaluation1.hashCode(), evaluation2.hashCode());
    }
}
