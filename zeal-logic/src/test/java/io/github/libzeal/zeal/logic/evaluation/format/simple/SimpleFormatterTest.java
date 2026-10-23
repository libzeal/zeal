package io.github.libzeal.zeal.logic.evaluation.format.simple;

import io.github.libzeal.zeal.logic.evaluation.Cause;
import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.evaluation.TerminalEvaluation;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleFormatterTest {

    private static final String EVALUATION_HEADING = "Evaluation:\n";
    private static final String ROOT_CAUSES_HEADING = "Root Causes:\n";
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
        final Evaluation evaluation = TerminalEvaluation.of(Result.TRUE, name, rationale, elapsedTime);

        final String expected = EVALUATION_HEADING +
            "[T] " + name + " (" + elapsedTime.toMillis() + "ms)  <---[ Root Cause ]";

        final String result = formatter.format(evaluation);

        assertEquals(expected, result);
    }

    @Test
    void givenOneSkippedEvaluation_whenFormat_thenResultIsCorrect() {

        final String name = "foo";
        final Evaluation decider = TerminalEvaluation.of(Result.FALSE, "decider", SimpleRationale.empty(),
            Duration.ZERO);
        final Evaluation evaluation = TerminalEvaluation.skipped(name, decider);

        final String expected = EVALUATION_HEADING +
            "[ ] " + name + " (0ns)";

        final String result = formatter.format(evaluation);

        assertEquals(expected, result);
    }

    @Test
    void givenOneFailingEvaluation_whenFormat_thenResultIsCorrect() {

        final String name = "foo";
        final Duration elapsedTime = Duration.ofMillis(10);
        final Rationale rationale = new SimpleRationale("someExpected", "someActual", "someHint");
        final Evaluation evaluation = TerminalEvaluation.of(Result.FALSE, name, rationale, elapsedTime);

        final String expected =
            ROOT_CAUSES_HEADING +
                "Expression  : " + name + "\n" +
                "Expected    : " + rationale.expected() + "\n" +
                "Actual      : " + rationale.actual() + "\n" +
                "Hint        : " + rationale.hint().orElse("") + "\n" +
                "Cause Trace : " + name + "\n" +
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
        final Evaluation childEvaluation = TerminalEvaluation.of(Result.FALSE, "child", childRationale,
            childElapsedTime);

        final Duration parentElapsedTime = Duration.ofMillis(20);
        final Evaluation parentEvaluation = parent(parentElapsedTime, Cause.of(childEvaluation), childEvaluation);

        final String expected =
            ROOT_CAUSES_HEADING +
                "Expression  : " + childEvaluation.name() + "\n" +
                "Expected    : " + childRationale.expected() + "\n" +
                "Actual      : " + childRationale.actual() + "\n" +
                "Hint        : " + childRationale.hint().orElse("") + "\n" +
                "Cause Trace : " + parentEvaluation.name() + " -> " + childEvaluation.name() + "\n" +
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
        final Evaluation childEvaluation = TerminalEvaluation.of(Result.TRUE, "child", childRationale,
            childElapsedTime);

        final Duration parentElapsedTime = Duration.ofMillis(20);
        final Evaluation parentEvaluation = parent(parentElapsedTime, Cause.of(childEvaluation), childEvaluation);

        final String expected =
            ROOT_CAUSES_HEADING +
                "Expression  : " + childEvaluation.name() + "\n" +
                "Expected    : " + childRationale.expected() + "\n" +
                "Actual      : " + childRationale.actual() + "\n" +
                "Hint        : " + childRationale.hint().orElse("") + "\n" +
                "Cause Trace : " + parentEvaluation.name() + " -> " + childEvaluation.name() + "\n" +
                "Notes       : " + SimpleRootCauseFormatter.TRUE_ROOT_CAUSE_NOTE + "\n" +
                "\n" +
                EVALUATION_HEADING +
                "[F] " + parentEvaluation.name() + " (" + parentElapsedTime.toMillis() + "ms)\n" +
                "    [T] " + childEvaluation.name() + " (" + childElapsedTime.toMillis() + "ms)  <---[ Root Cause ]";

        final String result = formatter.format(parentEvaluation);

        assertEquals(expected, result);
    }

    @Test
    void givenFailingEvaluationWithMultipleRootCauses_whenFormat_thenEachRootCauseFormatted() {

        final Duration childElapsedTime = Duration.ofMillis(10);
        final Rationale firstRationale = new SimpleRationale("firstExpected", "firstActual");
        final Rationale secondRationale = new SimpleRationale("secondExpected", "secondActual", "secondHint");
        final Evaluation first = TerminalEvaluation.of(Result.FALSE, "first", firstRationale, childElapsedTime);
        final Evaluation second = TerminalEvaluation.of(Result.FALSE, "second", secondRationale, childElapsedTime);

        final Duration parentElapsedTime = Duration.ofMillis(20);
        final Evaluation parentEvaluation = parent(parentElapsedTime, Cause.of(List.of(first, second)), first,
            second);

        final String expected =
            ROOT_CAUSES_HEADING +
                "Expression  : first\n" +
                "Expected    : firstExpected\n" +
                "Actual      : firstActual\n" +
                "Cause Trace : parent -> first\n" +
                "\n" +
                "Expression  : second\n" +
                "Expected    : secondExpected\n" +
                "Actual      : secondActual\n" +
                "Hint        : secondHint\n" +
                "Cause Trace : parent -> second\n" +
                "\n" +
                EVALUATION_HEADING +
                "[F] parent (20ms)\n" +
                "    [F] first (10ms)  <---[ Root Cause ]\n" +
                "    [F] second (10ms)  <---[ Root Cause ]";

        final String result = formatter.format(parentEvaluation);

        assertEquals(expected, result);
    }

    @Test
    void givenFailingEvaluationWithSkippedChild_whenFormat_thenOnlyDeciderIsRootCause() {

        final Duration childElapsedTime = Duration.ofMillis(10);
        final Rationale rationale = new SimpleRationale("someExpected", "someActual");
        final Evaluation decider = TerminalEvaluation.of(Result.FALSE, "decider", rationale, childElapsedTime);
        final Evaluation skipped = TerminalEvaluation.skipped("skipped", decider);

        final Duration parentElapsedTime = Duration.ofMillis(20);
        final Evaluation parentEvaluation = parent(parentElapsedTime, Cause.of(decider), decider, skipped);

        final String expected =
            ROOT_CAUSES_HEADING +
                "Expression  : decider\n" +
                "Expected    : someExpected\n" +
                "Actual      : someActual\n" +
                "Cause Trace : parent -> decider\n" +
                "\n" +
                EVALUATION_HEADING +
                "[F] parent (20ms)\n" +
                "    [F] decider (10ms)  <---[ Root Cause ]\n" +
                "    [ ] skipped (0ns)";

        final String result = formatter.format(parentEvaluation);

        assertEquals(expected, result);
    }

    private static Evaluation parent(final Duration elapsedTime, final Cause cause, final Evaluation... children) {

        final Rationale rationale = new SimpleRationale("someExpectedParent", "someActualParent", "someHintParent");

        return new CompoundEvaluation(Result.FALSE, "parent", rationale, elapsedTime, cause, List.of(children));
    }
}
