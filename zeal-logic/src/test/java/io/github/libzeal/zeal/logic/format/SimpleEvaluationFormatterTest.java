package io.github.libzeal.zeal.logic.format;

import io.github.libzeal.zeal.logic.AndExpression;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.*;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Evaluated;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Skipped;
import io.github.libzeal.zeal.logic.unary.Condition;
import io.github.libzeal.zeal.logic.unary.ConditionBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static io.github.libzeal.zeal.logic.test.NamedExpressions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SimpleEvaluationFormatterTest {

    private static final String EVALUATION_HEADING = "Evaluation:\n";
    private static final String ROOT_CAUSES_HEADING = "Root causes:\n";
    private SimpleEvaluationFormatter formatter;

    @BeforeEach
    void setUp() {
        formatter = new SimpleEvaluationFormatter();
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
                "[T] " + name + " (" + elapsedTime.toMillis() + "ms)  <-- Decided by";

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
                        "- Expression  : " + name + "\n" +
                        "  Expected    : " + rationale.expected() + "\n" +
                        "  Actual      : " + rationale.actual() + "\n" +
                        "  Hint        : " + rationale.hint().orElse("") + "\n" +
                        "  Cause trace : " + name + "\n" +
                        "\n" +
                        EVALUATION_HEADING +
                        "[F] " + name + " (" + elapsedTime.toMillis() + "ms)  <-- Root cause";

        final String result = formatter.format(evaluation);

        assertEquals(expected, result);
    }

    @Test
    void givenOneFailingNestedEvaluation_whenFormat_thenResultIsCorrect() {

        final Duration childElapsedTime = Duration.ofMillis(10);
        final Rationale childRationale = new SimpleRationale("someExpectedChild", "someActualChild", "someHintChild");
        final Evaluation childEvaluation = new TerminalEvaluation(
                false, "child", childRationale,
                childElapsedTime
        );

        final Duration parentElapsedTime = Duration.ofMillis(20);
        final Evaluation parentEvaluation = parent(parentElapsedTime, DirectCause.of(childEvaluation), childEvaluation);

        final String expected =
                ROOT_CAUSES_HEADING +
                        "- Expression  : " + childEvaluation.name() + "\n" +
                        "  Expected    : " + childRationale.expected() + "\n" +
                        "  Actual      : " + childRationale.actual() + "\n" +
                        "  Hint        : " + childRationale.hint().orElse("") + "\n" +
                        "  Cause trace : " + parentEvaluation.name() + " -> " + childEvaluation.name() + "\n" +
                        "\n" +
                        EVALUATION_HEADING +
                        "[F] " + parentEvaluation.name() + " (" + parentElapsedTime.toMillis() + "ms)\n" +
                        "    [F] " + childEvaluation.name() + " (" + childElapsedTime.toMillis() + "ms)  <-- Root cause";

        final String result = formatter.format(parentEvaluation);

        assertEquals(expected, result);
    }

    @Test
    void givenOneFailingWithTrueNestedEvaluation_whenFormat_thenNegatedExpectedAndHintFormatted() {

        final Duration childElapsedTime = Duration.ofMillis(10);
        final Rationale childRationale = new SimpleRationale("someExpectedChild", "someActualChild", "someHintChild");
        final Evaluation childEvaluation = new TerminalEvaluation(
                true, "child", childRationale,
                childElapsedTime
        );

        final Duration parentElapsedTime = Duration.ofMillis(20);
        final Evaluation parentEvaluation = parent(parentElapsedTime, DirectCause.of(childEvaluation), childEvaluation);

        final String expected =
                ROOT_CAUSES_HEADING +
                        "- Expression  : " + childEvaluation.name() + "\n" +
                        "  Expected    : " + childRationale.negatedExpected() + "\n" +
                        "  Actual      : " + childRationale.actual() + "\n" +
                        "  Hint        : " + childRationale.negatedHint().orElse("") + "\n" +
                        "  Cause trace : " + parentEvaluation.name() + " -> " + childEvaluation.name() + "\n" +
                        "\n" +
                        EVALUATION_HEADING +
                        "[F] " + parentEvaluation.name() + " (" + parentElapsedTime.toMillis() + "ms)\n" +
                        "    [T] " + childEvaluation.name() + " (" + childElapsedTime.toMillis() + "ms)  <-- Root cause";

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
        final Evaluation parentEvaluation = parent(
                parentElapsedTime, DirectCause.of(List.of(first, second)), first,
                second
        );

        final String expected =
                ROOT_CAUSES_HEADING +
                        "- Expression  : first\n" +
                        "  Expected    : firstExpected\n" +
                        "  Actual      : firstActual\n" +
                        "  Cause trace : parent -> first\n" +
                        "- Expression  : second\n" +
                        "  Expected    : secondExpected\n" +
                        "  Actual      : secondActual\n" +
                        "  Hint        : secondHint\n" +
                        "  Cause trace : parent -> second\n" +
                        "\n" +
                        EVALUATION_HEADING +
                        "[F] parent (20ms)\n" +
                        "    [F] first (10ms)  <-- Root cause\n" +
                        "    [F] second (10ms)  <-- Root cause";

        final String result = formatter.format(parentEvaluation);

        assertEquals(expected, result);
    }

    @Test
    void givenFailingEvaluationWithSkippedChild_whenFormat_thenOnlyDeciderIsRootCause() {

        final Duration childElapsedTime = Duration.ofMillis(10);
        final Rationale rationale = new SimpleRationale("someExpected", "someActual");
        final Evaluation decider = new TerminalEvaluation(false, "decider", rationale, childElapsedTime);
        final Skipped skipped = new Skipped(t("next"), DirectCause.of(decider));

        final Duration parentElapsedTime = Duration.ofMillis(20);
        final Evaluation parentEvaluation = parentOf(
                parentElapsedTime, DirectCause.of(decider), new Evaluated(decider),
                skipped
        );

        final String expected =
                ROOT_CAUSES_HEADING +
                        "- Expression  : decider\n" +
                        "  Expected    : someExpected\n" +
                        "  Actual      : someActual\n" +
                        "  Cause trace : parent -> decider\n" +
                        "\n" +
                        EVALUATION_HEADING +
                        "[F] parent (20ms)\n" +
                        "    [F] decider (10ms)  <-- Root cause\n" +
                        "    [-] next (skipped)";

        final String result = formatter.format(parentEvaluation);

        assertEquals(expected, result);
    }

    @Test
    void givenFailingEvaluationWithSkippedCompoundChild_whenFormat_thenSkippedSubtreeIsShown() {

        final Rationale rationale = new SimpleRationale("someExpected", "someActual");
        final Evaluation decider = new TerminalEvaluation(false, "decider", rationale, Duration.ofMillis(10));
        final Skipped skipped = new Skipped(or("OR", t("t1"), not("NOT", t("t2"))), DirectCause.of(decider));
        final Evaluation parentEvaluation = parentOf(
                Duration.ofMillis(20), DirectCause.of(decider),
                new Evaluated(decider), skipped
        );

        final String expected =
                ROOT_CAUSES_HEADING +
                        "- Expression  : decider\n" +
                        "  Expected    : someExpected\n" +
                        "  Actual      : someActual\n" +
                        "  Cause trace : parent -> decider\n" +
                        "\n" +
                        EVALUATION_HEADING +
                        "[F] parent (20ms)\n" +
                        "    [F] decider (10ms)  <-- Root cause\n" +
                        "    [-] OR (skipped)\n" +
                        "        [-] t1 (skipped)\n" +
                        "        [-] NOT (skipped)\n" +
                        "            [-] t2 (skipped)";

        final String result = formatter.format(parentEvaluation);

        assertEquals(expected, result);
    }

    @Test
    void givenDoubleNegation_whenFormat_thenRootCauseNotNegated() {

        final Evaluation evaluation = not("NOT1", not("NOT2", f("f1"))).evaluate();

        final String expected = ROOT_CAUSES_HEADING +
                "- Expression  : f1\n" +
                "  Expected    : true\n" +
                "  Actual      : false\n" +
                "  Cause trace : NOT1 -> NOT2 -> f1\n";

        assertEquals(expected, rootCausesOf(formatter.format(evaluation)));
    }

    @Test
    void givenTripleNegation_whenFormat_thenRootCauseNegated() {

        final Evaluation evaluation = not("NOT1", not("NOT2", not("NOT3", t("t1")))).evaluate();

        final String expected = ROOT_CAUSES_HEADING +
                "- Expression  : t1\n" +
                "  Expected    : not[true]\n" +
                "  Actual      : true\n" +
                "  Cause trace : NOT1 -> NOT2 -> NOT3 -> t1\n";

        assertEquals(expected, rootCausesOf(formatter.format(evaluation)));
    }

    @Test
    void givenNegatedPassingNor_whenFormat_thenNorIsNegatedRootCause() {

        final Evaluation evaluation = not("NOT", nor("NOR", f("f1"), f("f2"))).evaluate();

        final String expected = ROOT_CAUSES_HEADING +
                "- Expression  : NOR\n" +
                "  Expected    : not[true = 0]\n" +
                "  Actual      : True: 0, False: 2, Skipped: 0\n" +
                "  Cause trace : NOT -> NOR\n";

        assertEquals(expected, rootCausesOf(formatter.format(evaluation)));
    }

    @Test
    void givenNegatedConditionWithNegatedFields_whenFormat_thenNegatedFieldsUsed() {

        final Condition<String> condition = ConditionBuilder.<String>of(s -> s.contains("eve"))
                .name("includes[eve]")
                .expected("includes[eve]")
                .actual("includes[eve]")
                .hint("'eve' found")
                .negatedExpected("excludes[eve]")
                .negatedHint("'eve' should not be included")
                .build();

        final Evaluation evaluation = not("NOT", condition.bind("alice, eve")).evaluate();

        final String expected = ROOT_CAUSES_HEADING +
                "- Expression  : includes[eve]\n" +
                "  Expected    : excludes[eve]\n" +
                "  Actual      : includes[eve]\n" +
                "  Hint        : 'eve' should not be included\n" +
                "  Cause trace : NOT -> includes[eve]\n";

        assertEquals(expected, rootCausesOf(formatter.format(evaluation)));
    }

    @Test
    void givenDuplicateSiblings_whenFormat_thenSiblingsNumbered() {

        final Evaluation evaluation = or("OR", f("a"), f("a")).evaluate();

        final String expected =
                "[F] OR  <-- Root cause\n" +
                "    [F] a #1\n" +
                "    [F] a #2";

        assertEquals(expected, treeOf(formatter.format(evaluation)));
    }

    @Test
    void givenDuplicateSkippedSibling_whenFormat_thenSkippedSiblingNumbered() {

        final Evaluation evaluation = and("AND", f("a"), t("a")).evaluate();

        final String expected =
                "[F] AND\n" +
                "    [F] a #1  <-- Root cause\n" +
                "    [-] a #2 (skipped)";

        assertEquals(expected, treeOf(formatter.format(evaluation)));
    }

    @Test
    void givenDuplicatesInSkippedCompound_whenFormat_thenChildrenNumbered() {

        final Evaluation evaluation = and("AND", f("f1"), or("OR", t("b"), t("b"))).evaluate();

        final String expected =
                "[F] AND\n" +
                "    [F] f1  <-- Root cause\n" +
                "    [-] OR (skipped)\n" +
                "        [-] b #1 (skipped)\n" +
                "        [-] b #2 (skipped)";

        assertEquals(expected, treeOf(formatter.format(evaluation)));
    }

    @Test
    void givenUniqueSiblings_whenFormat_thenNamesUnchanged() {

        final Evaluation evaluation = or("OR", f("a"), f("b")).evaluate();

        final String expected =
                "[F] OR  <-- Root cause\n" +
                "    [F] a\n" +
                "    [F] b";

        assertEquals(expected, treeOf(formatter.format(evaluation)));
    }

    @Test
    void givenSameNameAtDifferentLevels_whenFormat_thenNotNumbered() {

        final Evaluation evaluation = or("OR", and("AND", f("a")), f("a")).evaluate();

        final String expected =
                "[F] OR  <-- Root cause\n" +
                "    [F] AND\n" +
                "        [F] a\n" +
                "    [F] a";

        assertEquals(expected, treeOf(formatter.format(evaluation)));
    }

    @Test
    void givenDuplicateRootCause_whenFormat_thenExpressionAndTraceNumbered() {

        final Evaluation evaluation = nor("NOR", f("a"), t("a")).evaluate();

        final String expected = ROOT_CAUSES_HEADING +
                "- Expression  : a #2\n" +
                "  Expected    : not[true]\n" +
                "  Actual      : true\n" +
                "  Cause trace : NOR -> a #2\n";

        assertEquals(expected, rootCausesOf(formatter.format(evaluation)));
    }

    @Test
    void givenSameExpressionInstanceTwice_whenFormat_thenBothNumbered() {

        final Expression a = f("a");
        final Evaluation evaluation = or("OR", a, a).evaluate();

        final String expected =
                "[F] OR  <-- Root cause\n" +
                "    [F] a #1\n" +
                "    [F] a #2";

        assertEquals(expected, treeOf(formatter.format(evaluation)));
    }

    @Test
    void givenSameExpressionInstanceTwiceAsRootCause_whenFormat_thenFirstInstanceNumbered() {

        final Expression a = f("a");
        final Evaluation evaluation = and("AND", a, a).evaluate();

        final String expected = ROOT_CAUSES_HEADING +
                "- Expression  : a #1\n" +
                "  Expected    : true\n" +
                "  Actual      : false\n" +
                "  Cause trace : AND -> a #1\n";

        assertEquals(expected, rootCausesOf(formatter.format(evaluation)));
    }

    // The evaluation tree, without the elapsed times (which vary between runs)
    private static String treeOf(final String formatted) {
        return formatted.substring(formatted.indexOf(EVALUATION_HEADING) + EVALUATION_HEADING.length())
                .replaceAll(" \\(\\d[^)]*\\)", "");
    }

    // Only the root causes are compared, since the evaluation tree includes elapsed times that vary between runs
    private static String rootCausesOf(final String formatted) {
        return formatted.substring(0, formatted.indexOf("\n" + EVALUATION_HEADING));
    }

    private static Evaluation parent(final Duration elapsedTime, final DirectCause cause, final Evaluation... children) {
        return parentOf(
                elapsedTime, cause, Arrays.stream(children)
                        .map(Evaluated::new)
                        .toArray(SkippableEvaluation[]::new)
        );
    }

    private static Evaluation parentOf(
            final Duration elapsedTime, final DirectCause cause,
            final SkippableEvaluation... children
    ) {

        final Rationale rationale = new SimpleRationale("someExpectedParent", "someActualParent", "someHintParent");
        final List<Expression> expressions = Arrays.stream(children)
                .map(child -> t(child.name()))
                .toList();

        return CompoundEvaluation.of(
                new AndExpression("parent", expressions), false, rationale, elapsedTime,
                cause, List.of(children)
        );
    }
}
