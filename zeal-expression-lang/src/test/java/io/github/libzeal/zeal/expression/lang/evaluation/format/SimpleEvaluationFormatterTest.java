package io.github.libzeal.zeal.expression.lang.evaluation.format;

import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.lang.evaluation.SimpleEvaluation;
import io.github.libzeal.zeal.expression.lang.rationale.Rationale;
import io.github.libzeal.zeal.expression.lang.rationale.SimpleRationale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class SimpleEvaluationFormatterTest {

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
    void givenPassingSingleEvaluation_whenFormat_thenCorrectStringReturned() {

        final Result result = Result.TRUE;
        final String name = "foo";
        final Evaluation expression = expression(result, name);

        assertEquals(
            expectedPassingOutput(name, 0),
            formatter.format(expression)
        );
    }

    private static Evaluation expression(Result result, String name) {

        final Evaluation expression = mock(Evaluation.class);

        doReturn(result).when(expression).result();
        doReturn(name).when(expression).name();

        return expression;
    }

    private static String expectedPassingOutput(final String name, final int indent) {
        return indent(indent) +
            "[T] " +
            name;
    }

    private static String indent(int indent) {

        final StringBuilder builder = new StringBuilder();

        for (int i = 0; i < indent; i++) {
            builder.append(SimpleEvaluationFormatter.INDENT);
        }

        return builder.toString();
    }

    @Test
    void givenSkippedSingleEvaluation_whenFormat_thenCorrectStringReturned() {

        final Result result = Result.SKIPPED;
        final String name = "foo";
        final Evaluation expression = expression(result, name);

        assertEquals(
            expectedSkippedOutput(name, 0),
            formatter.format(expression)
        );
    }

    private static String expectedSkippedOutput(final String name, final int indent) {
        return indent(indent) +
            "[ ] " +
            name;
    }

    @Test
    void givenFailingSingleEvaluation_whenFormat_thenCorrectStringReturned() {

        final Result result = Result.FALSE;
        final String name = "foo";
        final Rationale rationale = new SimpleRationale("expected", "actual", "hint");
        final Evaluation expression = expression(result, name, rationale);

        assertEquals(
            expectedRootCauseOutput(name, rationale) + "\n" +
                expectedFailingOutput(name, rationale, 0),
            formatter.format(expression)
        );
    }

    private static Evaluation expression(Result result, String name, Rationale rationale) {

        final Evaluation expression = expression(result, name);

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

        if (rationale == null) {
            return "";
        }

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
            "[F] " +
            name +
            "\n" +
            format(rationale, indent + 1);
    }

    @Test
    void givenFailingSingleEvaluationWithNullRationale_whenFormat_thenCorrectStringReturned() {

        final Result result = Result.FALSE;
        final String name = "foo";
        final Evaluation expression = expression(result, name, null);

        assertEquals(
            expectedRootCauseOutput(name, null) + "\n" +
                expectedFailingOutput(name, null, 0),
            formatter.format(expression)
        );
    }

    @Test
    void givenPassingThenFailingSingleEvaluation_whenFormat_thenCorrectStringReturned() {

        final String compoundExpressionName = "Compound Name";

        final Result passingResult = Result.TRUE;
        final String passingName = "foo1";
        final Rationale passingRationale = new SimpleRationale("expected1", "actual1","hint1");
        final Evaluation passingExpression = expression(passingResult, passingName, passingRationale);

        final Result failingResult = Result.FALSE;
        final String failingName = "foo2";
        final Rationale failingRationale = new SimpleRationale("expected2", "actual2", "hint2");
        final Evaluation failingExpression = expression(failingResult, failingName, failingRationale);

        final SimpleEvaluation compound = new SimpleEvaluation(
            compoundExpressionName, Result.FALSE,
            SimpleRationale.empty(),
            Arrays.asList(passingExpression, failingExpression)
        );

        assertEquals(
            expectedRootCauseOutput(failingName, failingRationale) + "\n" +
                "[F] " + compoundExpressionName + "\n" +
                expectedPassingOutput(passingName, 1) + "\n" +
                expectedFailingOutput(failingName, failingRationale, 1),
            formatter.format(compound)
        );
    }

    @Test
    void givenFailingNestedEvaluation_whenFormat_thenEvaluationAfterFailingEvaluationIsSkipped() {

        final String compoundExpressionName = "Compound Name";

        final Result passingResult = Result.TRUE;
        final String passingName = "foo1";
        final Rationale passingRationale = new SimpleRationale("expected1", "actual1", "hint1");
        final Evaluation passingExpression = expression(passingResult, passingName, passingRationale);

        final Result failingResult = Result.FALSE;
        final String failingName = "foo2";
        final Rationale failingRationale = new SimpleRationale("expected2", "actual2", "hint2");
        final Evaluation failingExpression = expression(failingResult, failingName, failingRationale);

        final Result skippedResult = Result.SKIPPED;
        final String skippedName = "foo3";
        final Rationale skippedRationale = SimpleRationale.empty();
        final Evaluation skippedExpression = expression(skippedResult, skippedName, skippedRationale);

        final SimpleEvaluation compound = new SimpleEvaluation(
            compoundExpressionName, Result.FALSE,
            SimpleRationale.empty(),
            Arrays.asList(passingExpression, failingExpression, skippedExpression)
        );

        assertEquals(
            expectedRootCauseOutput(failingName, failingRationale) + "\n" +
                "[F] " + compoundExpressionName + "\n" +
                expectedPassingOutput(passingName, 1) + "\n" +
                expectedFailingOutput(failingName, failingRationale, 1) + "\n" +
                expectedSkippedOutput(skippedName,1),
            formatter.format(compound)
        );
    }
}
