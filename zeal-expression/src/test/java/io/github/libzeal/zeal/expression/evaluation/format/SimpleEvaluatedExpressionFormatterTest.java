package io.github.libzeal.zeal.expression.evaluation.format;

import io.github.libzeal.zeal.expression.evaluation.CompoundEvaluatedExpression;
import io.github.libzeal.zeal.expression.evaluation.EvaluatedExpression;
import io.github.libzeal.zeal.expression.evaluation.Rationale;
import io.github.libzeal.zeal.expression.evaluation.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import static org.junit.jupiter.api.Assertions.assertThrows;

class SimpleEvaluatedExpressionFormatterTest {

    private SimpleEvaluatedExpressionFormatter formatter;

    @BeforeEach
    void setUp() {
        formatter = new SimpleEvaluatedExpressionFormatter();
    }

    @Test
    void givenNullEvaluation_whenFormat_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> formatter.format(null)
        );
    }

    @Test
    void givenPassingSingleEvaluation_whenFormat_thenCorrectStringReturned() {

        final Result result = Result.PASSED;
        final String name = "foo";
        final EvaluatedExpression expression = expression(result, name);

        assertEquals(
            expectedPassingOutput(name, 0),
            formatter.format(expression)
        );
    }

    private static EvaluatedExpression expression(Result result, String name) {

        final EvaluatedExpression expression = mock(EvaluatedExpression.class);

        doReturn(result).when(expression).result();
        doReturn(name).when(expression).name();

        return expression;
    }

    private static String expectedPassingOutput(final String name, final int indent) {
        return indent(indent) +
            "[PASS] " +
            name;
    }

    private static String indent(int indent) {

        final StringBuilder builder = new StringBuilder();

        for (int i = 0; i < indent; i++) {
            builder.append(SimpleEvaluatedExpressionFormatter.INDENT);
        }

        return builder.toString();
    }

    @Test
    void givenSkippedSingleEvaluation_whenFormat_thenCorrectStringReturned() {

        final Result result = Result.SKIPPED;
        final String name = "foo";
        final EvaluatedExpression expression = expression(result, name);

        assertEquals(
            expectedSkippedOutput(name, 0),
            formatter.format(expression)
        );
    }

    private static String expectedSkippedOutput(final String name, final int indent) {
        return indent(indent) +
            "[    ] " +
            name;
    }

    @Test
    void givenFailingSingleEvaluation_whenFormat_thenCorrectStringReturned() {

        final Result result = Result.FAILED;
        final String name = "foo";
        final Rationale rationale = new Rationale("expected", "actual", "hint");
        final EvaluatedExpression expression = expression(result, name, rationale);

        assertEquals(
            expectedRootCauseOutput(name, rationale) + "\n\n" +
                expectedFailingOutput(name, rationale, 0),
            formatter.format(expression)
        );
    }

    private static EvaluatedExpression expression(Result result, String name, Rationale rationale) {

        final EvaluatedExpression expression = expression(result, name);

        doReturn(rationale).when(expression).rationale();

        return expression;
    }

    private static String expectedRootCauseOutput(String name, Rationale rationale) {
        return "Root Cause: " +
            name +
            "\n" +
            format(rationale, 0);
    }

    private static String format(Rationale rationale, int indent) {

        StringBuilder builder = new StringBuilder();

        builder.append(indent(indent))
            .append("- Expected : ")
            .append(rationale.expected())
            .append("\n")
            .append(indent(indent))
            .append("- Actual   : ")
            .append(rationale.actual());

        rationale.hint().ifPresent(h -> {
            builder.append("\n")
                .append(indent(indent))
                .append("- Hint     : ")
                .append(h);
        });

        return builder.toString();
    }

    private static String expectedFailingOutput(String name, Rationale rationale, int indent) {
        return indent(indent) +
            "[FAIL] " +
            name +
            "\n" +
            format(rationale, indent + 1);
    }

    @Test
    void givenPassingThenFailingSingleEvaluation_whenFormat_thenCorrectStringReturned() {

        final String compoundExpressionName = "Compound Name";

        final Result passingResult = Result.PASSED;
        final String passingName = "foo1";
        final Rationale passingRationale = new Rationale("expected1", "actual1", "hint1");
        final EvaluatedExpression passingExpression = expression(passingResult, passingName, passingRationale);

        final Result failingResult = Result.FAILED;
        final String failingName = "foo2";
        final Rationale failingRationale = new Rationale("expected2", "actual2", "hint2");
        final EvaluatedExpression failingExpression = expression(failingResult, failingName, failingRationale);

        final CompoundEvaluatedExpression compound = new CompoundEvaluatedExpression(
            compoundExpressionName,
            Arrays.asList(passingExpression, failingExpression)
        );

        assertEquals(
            expectedRootCauseOutput(failingName, failingRationale) + "\n\n" +
                "[FAIL] " + compoundExpressionName + "\n" +
                expectedPassingOutput(passingName, 1) + "\n" +
                expectedFailingOutput(failingName, failingRationale, 1),
            formatter.format(compound)
        );
    }

    @Test
    void givenFailingNestedEvaluation_whenFormat_thenEvaluationAfterFailingEvaluationIsSkipped() {

        final String compoundExpressionName = "Compound Name";

        final Result passingResult = Result.PASSED;
        final String passingName = "foo1";
        final Rationale passingRationale = new Rationale("expected1", "actual1", "hint1");
        final EvaluatedExpression passingExpression = expression(passingResult, passingName, passingRationale);

        final Result failingResult = Result.FAILED;
        final String failingName = "foo2";
        final Rationale failingRationale = new Rationale("expected2", "actual2", "hint2");
        final EvaluatedExpression failingExpression = expression(failingResult, failingName, failingRationale);

        final Result skippedResult = Result.SKIPPED;
        final String skippedName = "foo3";
        final Rationale skippedRationale = Rationale.empty();
        final EvaluatedExpression skippedExpression = expression(skippedResult, skippedName, skippedRationale);

        final CompoundEvaluatedExpression compound = new CompoundEvaluatedExpression(
            compoundExpressionName,
            Arrays.asList(passingExpression, failingExpression, skippedExpression)
        );

        assertEquals(
            expectedRootCauseOutput(failingName, failingRationale) + "\n\n" +
                "[FAIL] " + compoundExpressionName + "\n" +
                expectedPassingOutput(passingName, 1) + "\n" +
                expectedFailingOutput(failingName, failingRationale, 1) + "\n" +
                expectedSkippedOutput(skippedName,1),
            formatter.format(compound)
        );
    }
}
