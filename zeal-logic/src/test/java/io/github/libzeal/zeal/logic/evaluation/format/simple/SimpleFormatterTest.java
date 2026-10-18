package io.github.libzeal.zeal.logic.evaluation.format.simple;

import io.github.libzeal.zeal.logic.evaluation.EvaluatedCompoundEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.SkippedTerminalEvaluation;
import io.github.libzeal.zeal.logic.evaluation.EvaluatedTerminalEvaluation;
import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.cause.CauseGenerator;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class SimpleFormatterTest {

    private static final String EVALUATION_HEADING = "Evaluation:\n";
    private static final String ROOT_CAUSE_HEADING = "Root Cause:\n";
    private SimpleFormatter formatter;

    @BeforeEach
    void setUp() {
        formatter = new SimpleFormatter();
    }

    @Test
    void givenNullEvaluation_whenFormat_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> formatter.format(null)
        );
    }

    @Test
    void givenOnePassingEvaluation_whenFormat_thenResultIsCorrect() {

        final String name = "foo";
        final Duration elapsedTime = Duration.ofMillis(10);
        final Rationale rationale = new SimpleRationale("someExpected", "someActual", "someHint");
        final Evaluation evaluation = EvaluatedTerminalEvaluation.ofTrue(name, rationale, elapsedTime);

        final String expected = EVALUATION_HEADING +
            "[T] " + name + " (" + elapsedTime.toMillis() + "ms)  <---[ Root Cause ]";

        final String result = formatter.format(evaluation);

        assertEquals(expected, result);
    }

    @Test
    void givenOneSkippedEvaluation_whenFormat_thenResultIsCorrect() {

        final String name = "foo";
        final Evaluation evaluation = new SkippedTerminalEvaluation(name, CauseGenerator.self());

        final String expected = EVALUATION_HEADING +
            "\\[ \\] " + name + " \\(.+\\)  <---\\[ Root Cause \\]";

        final String result = formatter.format(evaluation);

        assertTrue(result.matches(expected), "Was: " + result);
    }

    @Test
    void givenOneFailingEvaluation_whenFormat_thenResultIsCorrect() {

        final String name = "foo";
        final Duration elapsedTime = Duration.ofMillis(10);
        final Rationale rationale = new SimpleRationale("someExpected", "someActual", "someHint");
        final Evaluation evaluation = EvaluatedTerminalEvaluation.ofFalse(name, rationale, elapsedTime);

        final String expected =
            ROOT_CAUSE_HEADING +
            "Expression : " + name + "\n" +
            "Expected   : " + rationale.expected() + "\n" +
            "Actual     : " + rationale.actual() + "\n" +
            "Hint       : " + rationale.hint().orElse("") + "\n" +
            "Chain      : " + name + "\n" +
            "\n" +
                EVALUATION_HEADING +
            "[F] " + name + " (" + elapsedTime.toMillis() + "ms)  <---[ Root Cause ]";

        final String result = formatter.format(evaluation);

        assertEquals(expected, result);
    }

    @Test
    void givenOneFailingNestedEvaluation_whenFormat_thenResultIsCorrect() {

        final Duration childElapsedTime = Duration.ofMillis(10);
        final Rationale childRationale = new SimpleRationale("someExpectedChild", "someActualChild", "someHintChild");
        final Evaluation childEvaluation = EvaluatedTerminalEvaluation.ofFalse("child", childRationale, childElapsedTime);

        final Duration parentElapsedTime = Duration.ofMillis(20);
        final Rationale parentRationale = new SimpleRationale("someExpectedParent", "someActualParent",
            "someHintParent");
        final Evaluation parentEvaluation = EvaluatedCompoundEvaluation.ofFalse(
            "parent",
            parentRationale,
            parentElapsedTime,
            CauseGenerator.withUnderlyingCause(new Cause(childEvaluation)),
            Collections.singletonList(childEvaluation)
        );

        final String expected =
            ROOT_CAUSE_HEADING +
                "Expression : " + childEvaluation.name() + "\n" +
                "Expected   : " + childRationale.expected() + "\n" +
                "Actual     : " + childRationale.actual() + "\n" +
                "Hint       : " + childRationale.hint().orElse("") + "\n" +
                "Chain      : " + parentEvaluation.name() + " -> " + childEvaluation.name() + "\n" +
                "\n" +
                EVALUATION_HEADING +
                "[F] " + parentEvaluation.name() + " (" + parentElapsedTime.toMillis() + "ms)\n" +
                "    [F] " + childEvaluation.name() + " (" + childElapsedTime.toMillis() + "ms)  <---[ Root Cause ]";

        final String result = formatter.format(parentEvaluation);

        assertEquals(expected, result);
    }

    @Test
    void givenOneFailingWithTrueNestedEvaluation_whenFormat_thenResultIsCorrect() {

        final Duration childElapsedTime = Duration.ofMillis(10);
        final Rationale childRationale = new SimpleRationale("someExpectedChild", "someActualChild", "someHintChild");
        final Evaluation childEvaluation = EvaluatedTerminalEvaluation.ofTrue("child", childRationale, childElapsedTime);

        final Duration parentElapsedTime = Duration.ofMillis(20);
        final Rationale parentRationale = new SimpleRationale("someExpectedParent", "someActualParent",
            "someHintParent");
        final Evaluation parentEvaluation = EvaluatedCompoundEvaluation.ofFalse(
            "parent",
            parentRationale,
            parentElapsedTime,
            CauseGenerator.withUnderlyingCause(new Cause(childEvaluation)),
            Collections.singletonList(childEvaluation)
        );

        final String expected =
            ROOT_CAUSE_HEADING +
                "Expression : " + childEvaluation.name() + "\n" +
                "Expected   : " + childRationale.expected() + "\n" +
                "Actual     : " + childRationale.actual() + "\n" +
                "Hint       : " + childRationale.hint().orElse("") + "\n" +
                "Chain      : " + parentEvaluation.name() + " -> " + childEvaluation.name() + "\n" +
                "Notes      : " + SimpleRootCauseFormatter.TRUE_ROOT_CAUSE_NOTE + "\n" +
                "\n" +
                EVALUATION_HEADING +
                "[F] " + parentEvaluation.name() + " (" + parentElapsedTime.toMillis() + "ms)\n" +
                "    [T] " + childEvaluation.name() + " (" + childElapsedTime.toMillis() + "ms)  <---[ Root Cause ]";

        final String result = formatter.format(parentEvaluation);

        assertEquals(expected, result);
    }
}
