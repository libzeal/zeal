package io.github.libzeal.zeal.logic.evaluation.format;

import io.github.libzeal.zeal.logic.AndExpression;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.DirectCause;
import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Evaluated;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Skipped;
import io.github.libzeal.zeal.logic.evaluation.TerminalEvaluation;
import io.github.libzeal.zeal.logic.evaluation.format.SimpleFormatter;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static io.github.libzeal.zeal.logic.test.NamedExpressions.*;
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
        final Evaluation evaluation = new TerminalEvaluation(true, name, rationale, elapsedTime);

        final String expected = EVALUATION_HEADING +
            "[T] " + name + " (" + elapsedTime.toMillis() + "ms)  <---[ Root Cause ]";

        final String result = formatter.format(evaluation);

        assertEquals(expected, result);
    }

    @Test
    void givenOneFailingEvaluation_whenFormat_thenResultIsCorrect() {

        final String name = "foo";
        final Duration elapsedTime = Duration.ofMillis(10);
        final Rationale rationale = new SimpleRationale("someExpected", "someActual", "someHint");
        final Evaluation evaluation = new TerminalEvaluation(false, name, rationale, elapsedTime);

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
        final Evaluation childEvaluation = new TerminalEvaluation(false, "child", childRationale,
            childElapsedTime);

        final Duration parentElapsedTime = Duration.ofMillis(20);
        final Evaluation parentEvaluation = parent(parentElapsedTime, DirectCause.of(childEvaluation), childEvaluation);

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
        final Evaluation childEvaluation = new TerminalEvaluation(true, "child", childRationale,
            childElapsedTime);

        final Duration parentElapsedTime = Duration.ofMillis(20);
        final Evaluation parentEvaluation = parent(parentElapsedTime, DirectCause.of(childEvaluation), childEvaluation);

        final String expected =
            ROOT_CAUSES_HEADING +
                "Expression  : " + childEvaluation.name() + "\n" +
                "Expected    : " + childRationale.expected() + "\n" +
                "Actual      : " + childRationale.actual() + "\n" +
                "Hint        : " + childRationale.hint().orElse("") + "\n" +
                "Cause Trace : " + parentEvaluation.name() + " -> " + childEvaluation.name() + "\n" +
                "Notes       : " + SimpleFormatter.TRUE_ROOT_CAUSE_NOTE + "\n" +
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
        final Evaluation first = new TerminalEvaluation(false, "first", firstRationale, childElapsedTime);
        final Evaluation second = new TerminalEvaluation(false, "second", secondRationale, childElapsedTime);

        final Duration parentElapsedTime = Duration.ofMillis(20);
        final Evaluation parentEvaluation = parent(parentElapsedTime, DirectCause.of(List.of(first, second)), first,
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
        final Evaluation decider = new TerminalEvaluation(false, "decider", rationale, childElapsedTime);
        final Skipped skipped = new Skipped(t("skipped"), DirectCause.of(decider));

        final Duration parentElapsedTime = Duration.ofMillis(20);
        final Evaluation parentEvaluation = parentOf(parentElapsedTime, DirectCause.of(decider), new Evaluated(decider),
            skipped);

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
                "    [ ] skipped";

        final String result = formatter.format(parentEvaluation);

        assertEquals(expected, result);
    }

    @Test
    void givenFailingEvaluationWithSkippedCompoundChild_whenFormat_thenSkippedSubtreeIsShown() {

        final Rationale rationale = new SimpleRationale("someExpected", "someActual");
        final Evaluation decider = new TerminalEvaluation(false, "decider", rationale, Duration.ofMillis(10));
        final Skipped skipped = new Skipped(or("OR", t("t1"), not("NOT", t("t2"))), DirectCause.of(decider));
        final Evaluation parentEvaluation = parentOf(Duration.ofMillis(20), DirectCause.of(decider),
            new Evaluated(decider), skipped);

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
                "    [ ] OR\n" +
                "        [ ] t1\n" +
                "        [ ] NOT\n" +
                "            [ ] t2";

        final String result = formatter.format(parentEvaluation);

        assertEquals(expected, result);
    }

    private static Evaluation parent(final Duration elapsedTime, final DirectCause cause, final Evaluation... children) {
        return parentOf(elapsedTime, cause, Arrays.stream(children)
            .map(Evaluated::new)
            .toArray(SkippableEvaluation[]::new));
    }

    private static Evaluation parentOf(final Duration elapsedTime, final DirectCause cause,
                                       final SkippableEvaluation... children) {

        final Rationale rationale = new SimpleRationale("someExpectedParent", "someActualParent", "someHintParent");
        final List<Expression> expressions = Arrays.stream(children)
            .map(child -> t(child.name()))
            .toList();

        return CompoundEvaluation.of(new AndExpression("parent", expressions), false, rationale, elapsedTime,
            cause, List.of(children));
    }
}
