package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.AndExpression;
import io.github.libzeal.zeal.logic.BooleanExpression;
import io.github.libzeal.zeal.logic.CompoundExpression;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Evaluated;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Skipped;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompoundEvaluationTest {

    private static final String NAME = "foo";
    private static final Rationale RATIONALE = new SimpleRationale("expected", "actual");
    private static final Duration ELAPSED_TIME = Duration.ofMillis(7);
    private static final Expression CHILD_EXPRESSION = new BooleanExpression("child", () -> false);
    private static final Expression SIBLING_EXPRESSION = new BooleanExpression("sibling", () -> true);
    private static final CompoundExpression EXPRESSION = new AndExpression(NAME, List.of(CHILD_EXPRESSION));
    private static final CompoundExpression TWO_CHILD_EXPRESSION = new AndExpression(NAME,
        List.of(CHILD_EXPRESSION, SIBLING_EXPRESSION));
    private static final Evaluation CHILD = new TerminalEvaluation(false, "child", RATIONALE, ELAPSED_TIME);
    private static final List<SkippableEvaluation> CHILDREN = List.of(new Evaluated(CHILD));

    @Test
    void givenNullExpression_whenOf_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> CompoundEvaluation.of(null, true, RATIONALE, ELAPSED_TIME, Cause.self(), CHILDREN)
        );
    }

    @Test
    void givenNullRationale_whenOf_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> CompoundEvaluation.of(EXPRESSION, true, null, ELAPSED_TIME, Cause.self(), CHILDREN)
        );
    }

    @Test
    void givenNullElapsedTime_whenOf_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> CompoundEvaluation.of(EXPRESSION, true, RATIONALE, null, Cause.self(), CHILDREN)
        );
    }

    @Test
    void givenNullCause_whenOf_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> CompoundEvaluation.of(EXPRESSION, true, RATIONALE, ELAPSED_TIME, null, CHILDREN)
        );
    }

    @Test
    void givenNullChildren_whenOf_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> CompoundEvaluation.of(EXPRESSION, true, RATIONALE, ELAPSED_TIME, Cause.self(), null)
        );
    }

    @Test
    void givenNullChild_whenOf_thenExceptionThrown() {

        final List<SkippableEvaluation> children = Arrays.asList(new Evaluated(CHILD), null);

        assertThrows(
            NullPointerException.class,
            () -> CompoundEvaluation.of(TWO_CHILD_EXPRESSION, true, RATIONALE, ELAPSED_TIME, Cause.self(), children)
        );
    }

    @Test
    void givenValidData_whenOf_thenDataIsCorrect() {

        final Cause cause = Cause.of(CHILD);

        final CompoundEvaluation evaluation = CompoundEvaluation.of(EXPRESSION, false, RATIONALE, ELAPSED_TIME,
            cause, CHILDREN);

        assertFalse(evaluation.result());
        assertEquals(NAME, evaluation.name());
        assertEquals(RATIONALE, evaluation.rationale());
        assertEquals(ELAPSED_TIME, evaluation.elapsedTime());
        assertEquals(cause, evaluation.cause());
        assertEquals(CHILDREN, evaluation.children());
        assertEquals(List.of(CHILD), evaluation.evaluated());
    }

    @Test
    void givenMutableChildren_whenOf_thenChildrenAreCopied() {

        final List<SkippableEvaluation> children = new ArrayList<>(CHILDREN);

        final CompoundEvaluation evaluation = CompoundEvaluation.of(EXPRESSION, false, RATIONALE, ELAPSED_TIME,
            Cause.self(), children);

        children.clear();

        assertEquals(CHILDREN, evaluation.children());
        assertThrows(UnsupportedOperationException.class, () -> evaluation.children().clear());
    }

    @Test
    void givenFewerChildrenThanExpressionChildren_whenOf_thenExceptionThrown() {
        assertThrows(
            IllegalArgumentException.class,
            () -> CompoundEvaluation.of(TWO_CHILD_EXPRESSION, false, RATIONALE, ELAPSED_TIME, Cause.self(),
                CHILDREN)
        );
    }

    @Test
    void givenSkippedChildCausedBySibling_whenOf_thenEvaluatedExcludesSkippedChild() {

        final List<SkippableEvaluation> children = List.of(
            new Evaluated(CHILD),
            new Skipped(SIBLING_EXPRESSION, Cause.of(CHILD))
        );

        final CompoundEvaluation evaluation = CompoundEvaluation.of(TWO_CHILD_EXPRESSION, false, RATIONALE,
            ELAPSED_TIME, Cause.of(CHILD), children);

        assertEquals(children, evaluation.children());
        assertEquals(List.of(CHILD), evaluation.evaluated());
    }

    @Test
    void givenSkippedChildCausedByNonSibling_whenOf_thenExceptionThrown() {

        final Evaluation stranger = new TerminalEvaluation(false, "child", RATIONALE, ELAPSED_TIME);
        final List<SkippableEvaluation> children = List.of(
            new Evaluated(CHILD),
            new Skipped(SIBLING_EXPRESSION, Cause.of(stranger))
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> CompoundEvaluation.of(TWO_CHILD_EXPRESSION, false, RATIONALE, ELAPSED_TIME, Cause.of(CHILD),
                children)
        );
    }

    @Test
    void givenMatchingEvaluations_whenEquals_thenEqual() {

        final CompoundEvaluation evaluation1 = CompoundEvaluation.of(EXPRESSION, false, RATIONALE, ELAPSED_TIME,
            Cause.of(CHILD), CHILDREN);
        final CompoundEvaluation evaluation2 = CompoundEvaluation.of(EXPRESSION, false, RATIONALE, ELAPSED_TIME,
            Cause.of(CHILD), CHILDREN);

        assertEquals(evaluation1, evaluation2);
        assertEquals(evaluation1.hashCode(), evaluation2.hashCode());
    }

    @Test
    void givenDifferentResults_whenEquals_thenNotEqual() {

        final CompoundEvaluation evaluation1 = CompoundEvaluation.of(EXPRESSION, false, RATIONALE, ELAPSED_TIME,
            Cause.of(CHILD), CHILDREN);
        final CompoundEvaluation evaluation2 = CompoundEvaluation.of(EXPRESSION, true, RATIONALE, ELAPSED_TIME,
            Cause.of(CHILD), CHILDREN);

        assertNotEquals(evaluation1, evaluation2);
    }
}
