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
    private static final CompoundExpression TWO_CHILD_EXPRESSION = new AndExpression(
            NAME,
            List.of(CHILD_EXPRESSION, SIBLING_EXPRESSION)
    );
    private static final Evaluation CHILD = new TerminalEvaluation(false, "child", RATIONALE, ELAPSED_TIME);
    private static final List<SkippableEvaluation> CHILDREN = List.of(new Evaluated(CHILD));

    @Test
    void givenNullName_whenConstruct_thenExceptionThrown() {
        assertThrows(
                NullPointerException.class,
                () -> new CompoundEvaluation(true, null, RATIONALE, ELAPSED_TIME, DirectCause.self(), CHILDREN)
        );
    }

    @Test
    void givenNullRationale_whenConstruct_thenExceptionThrown() {
        assertThrows(
                NullPointerException.class,
                () -> new CompoundEvaluation(true, NAME, null, ELAPSED_TIME, DirectCause.self(), CHILDREN)
        );
    }

    @Test
    void givenNullDuration_whenConstruct_thenExceptionThrown() {
        assertThrows(
                NullPointerException.class,
                () -> new CompoundEvaluation(true, NAME, RATIONALE, null, DirectCause.self(), CHILDREN)
        );
    }

    @Test
    void givenNullCause_whenConstruct_thenExceptionThrown() {
        assertThrows(
                NullPointerException.class,
                () -> new CompoundEvaluation(true, NAME, RATIONALE, ELAPSED_TIME, null, CHILDREN)
        );
    }

    @Test
    void givenNullChildren_whenConstruct_thenExceptionThrown() {
        assertThrows(
                NullPointerException.class,
                () -> new CompoundEvaluation(true, NAME, RATIONALE, ELAPSED_TIME, DirectCause.self(), null)
        );
    }

    @Test
    void givenNullChild_whenConstruct_thenExceptionThrown() {

        final List<SkippableEvaluation> children = Arrays.asList(new Evaluated(CHILD), null);

        assertThrows(
                NullPointerException.class,
                () -> new CompoundEvaluation(true, NAME, RATIONALE, ELAPSED_TIME, DirectCause.self(), children)
        );
    }

    @Test
    void givenValidData_whenConstruct_thenDataIsCorrect() {

        final DirectCause cause = DirectCause.of(CHILD);

        final CompoundEvaluation evaluation = new CompoundEvaluation(
                false, NAME, RATIONALE, ELAPSED_TIME,
                cause, CHILDREN
        );

        assertFalse(evaluation.isTrue());
        assertEquals(NAME, evaluation.name());
        assertEquals(RATIONALE, evaluation.rationale());
        assertEquals(ELAPSED_TIME, evaluation.duration());
        assertEquals(cause, evaluation.cause().direct());
        assertEquals(CHILDREN, evaluation.children());
        assertEquals(List.of(CHILD), evaluation.evaluated());
    }

    @Test
    void givenNoChildren_whenConstruct_thenNoExceptionThrown() {

        final CompoundEvaluation evaluation = new CompoundEvaluation(
                true, NAME, RATIONALE, ELAPSED_TIME,
                DirectCause.self(), List.of()
        );

        assertEquals(List.of(), evaluation.children());
        assertEquals(List.of(), evaluation.evaluated());
    }

    @Test
    void givenSkippedChildCausedBySibling_whenConstruct_thenNoExceptionThrown() {

        final List<SkippableEvaluation> children = List.of(
                new Evaluated(CHILD),
                new Skipped(SIBLING_EXPRESSION, DirectCause.of(CHILD))
        );

        final CompoundEvaluation evaluation = new CompoundEvaluation(
                false, NAME, RATIONALE, ELAPSED_TIME,
                DirectCause.of(CHILD), children
        );

        assertEquals(children, evaluation.children());
        assertEquals(List.of(CHILD), evaluation.evaluated());
    }

    @Test
    void givenSkippedChildCausedByNonSibling_whenConstruct_thenExceptionThrown() {

        final Evaluation stranger = new TerminalEvaluation(false, "child", RATIONALE, ELAPSED_TIME);
        final List<SkippableEvaluation> children = List.of(
                new Evaluated(CHILD),
                new Skipped(SIBLING_EXPRESSION, DirectCause.of(stranger))
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new CompoundEvaluation(false, NAME, RATIONALE, ELAPSED_TIME, DirectCause.of(CHILD), children)
        );
    }

    @Test
    void givenSkippedChildCausedBySiblingAndNonSibling_whenConstruct_thenExceptionThrown() {

        final Evaluation stranger = new TerminalEvaluation(false, "child", RATIONALE, ELAPSED_TIME);
        final List<SkippableEvaluation> children = List.of(
                new Evaluated(CHILD),
                new Skipped(SIBLING_EXPRESSION, DirectCause.of(List.of(CHILD, stranger)))
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new CompoundEvaluation(false, NAME, RATIONALE, ELAPSED_TIME, DirectCause.of(CHILD), children)
        );
    }

    @Test
    void givenSkippedChildWithoutEvaluatedSiblings_whenConstruct_thenExceptionThrown() {

        final List<SkippableEvaluation> children = List.of(
                new Skipped(SIBLING_EXPRESSION, DirectCause.of(CHILD))
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new CompoundEvaluation(false, NAME, RATIONALE, ELAPSED_TIME, DirectCause.self(), children)
        );
    }

    @Test
    void givenNullExpression_whenOf_thenExceptionThrown() {
        assertThrows(
                NullPointerException.class,
                () -> CompoundEvaluation.of(null, true, RATIONALE, ELAPSED_TIME, DirectCause.self(), CHILDREN)
        );
    }

    @Test
    void givenNullRationale_whenOf_thenExceptionThrown() {
        assertThrows(
                NullPointerException.class,
                () -> CompoundEvaluation.of(EXPRESSION, true, null, ELAPSED_TIME, DirectCause.self(), CHILDREN)
        );
    }

    @Test
    void givenNullDuration_whenOf_thenExceptionThrown() {
        assertThrows(
                NullPointerException.class,
                () -> CompoundEvaluation.of(EXPRESSION, true, RATIONALE, null, DirectCause.self(), CHILDREN)
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
                () -> CompoundEvaluation.of(EXPRESSION, true, RATIONALE, ELAPSED_TIME, DirectCause.self(), null)
        );
    }

    @Test
    void givenNullChild_whenOf_thenExceptionThrown() {

        final List<SkippableEvaluation> children = Arrays.asList(new Evaluated(CHILD), null);

        assertThrows(
                NullPointerException.class,
                () -> CompoundEvaluation.of(TWO_CHILD_EXPRESSION, true, RATIONALE, ELAPSED_TIME, DirectCause.self(), children)
        );
    }

    @Test
    void givenValidData_whenOf_thenDataIsCorrect() {

        final DirectCause cause = DirectCause.of(CHILD);

        final CompoundEvaluation evaluation = CompoundEvaluation.of(
                EXPRESSION, false, RATIONALE, ELAPSED_TIME,
                cause, CHILDREN
        );

        assertFalse(evaluation.isTrue());
        assertTrue(evaluation.isFalse());
        assertEquals(NAME, evaluation.name());
        assertEquals(RATIONALE, evaluation.rationale());
        assertEquals(ELAPSED_TIME, evaluation.duration());
        assertEquals(cause, evaluation.cause().direct());
        assertEquals(CHILDREN, evaluation.children());
        assertEquals(List.of(CHILD), evaluation.evaluated());
    }

    @Test
    void givenTrueResult_whenOf_thenIsTrueAndNotFalse() {

        final CompoundEvaluation evaluation = CompoundEvaluation.of(
                EXPRESSION, true, RATIONALE, ELAPSED_TIME,
                DirectCause.self(), CHILDREN
        );

        assertTrue(evaluation.isTrue());
        assertFalse(evaluation.isFalse());
    }

    @Test
    void givenMutableChildren_whenOf_thenChildrenAreCopied() {

        final List<SkippableEvaluation> children = new ArrayList<>(CHILDREN);

        final CompoundEvaluation evaluation = CompoundEvaluation.of(
                EXPRESSION, false, RATIONALE, ELAPSED_TIME,
                DirectCause.self(), children
        );

        children.clear();

        assertEquals(CHILDREN, evaluation.children());
        assertThrows(UnsupportedOperationException.class, () -> evaluation.children().clear());
    }

    @Test
    void givenFewerChildrenThanExpressionChildren_whenOf_thenExceptionThrown() {
        assertThrows(
                IllegalArgumentException.class,
                () -> CompoundEvaluation.of(
                        TWO_CHILD_EXPRESSION, false, RATIONALE, ELAPSED_TIME, DirectCause.self(),
                        CHILDREN
                )
        );
    }

    @Test
    void givenMoreChildrenThanExpressionChildren_whenOf_thenExceptionThrown() {

        final Evaluation sibling = new TerminalEvaluation(true, "sibling", RATIONALE, ELAPSED_TIME);
        final List<SkippableEvaluation> children = List.of(new Evaluated(CHILD), new Evaluated(sibling));

        assertThrows(
                IllegalArgumentException.class,
                () -> CompoundEvaluation.of(
                        EXPRESSION, false, RATIONALE, ELAPSED_TIME, DirectCause.self(),
                        children
                )
        );
    }

    @Test
    void givenSkippedChildCausedBySibling_whenOf_thenEvaluatedExcludesSkippedChild() {

        final List<SkippableEvaluation> children = List.of(
                new Evaluated(CHILD),
                new Skipped(SIBLING_EXPRESSION, DirectCause.of(CHILD))
        );

        final CompoundEvaluation evaluation = CompoundEvaluation.of(
                TWO_CHILD_EXPRESSION, false, RATIONALE,
                ELAPSED_TIME, DirectCause.of(CHILD), children
        );

        assertEquals(children, evaluation.children());
        assertEquals(List.of(CHILD), evaluation.evaluated());
    }

    @Test
    void givenSkippedChildCausedByNonSibling_whenOf_thenExceptionThrown() {

        final Evaluation stranger = new TerminalEvaluation(false, "child", RATIONALE, ELAPSED_TIME);
        final List<SkippableEvaluation> children = List.of(
                new Evaluated(CHILD),
                new Skipped(SIBLING_EXPRESSION, DirectCause.of(stranger))
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> CompoundEvaluation.of(
                        TWO_CHILD_EXPRESSION, false, RATIONALE, ELAPSED_TIME, DirectCause.of(CHILD),
                        children
                )
        );
    }

    @Test
    void givenMatchingEvaluations_whenEquals_thenEqual() {

        final CompoundEvaluation evaluation1 = CompoundEvaluation.of(
                EXPRESSION, false, RATIONALE, ELAPSED_TIME,
                DirectCause.of(CHILD), CHILDREN
        );
        final CompoundEvaluation evaluation2 = CompoundEvaluation.of(
                EXPRESSION, false, RATIONALE, ELAPSED_TIME,
                DirectCause.of(CHILD), CHILDREN
        );

        assertEquals(evaluation1, evaluation2);
        assertEquals(evaluation1.hashCode(), evaluation2.hashCode());
    }

    @Test
    void givenDifferentResults_whenEquals_thenNotEqual() {

        final CompoundEvaluation evaluation1 = CompoundEvaluation.of(
                EXPRESSION, false, RATIONALE, ELAPSED_TIME,
                DirectCause.of(CHILD), CHILDREN
        );
        final CompoundEvaluation evaluation2 = CompoundEvaluation.of(
                EXPRESSION, true, RATIONALE, ELAPSED_TIME,
                DirectCause.of(CHILD), CHILDREN
        );

        assertNotEquals(evaluation1, evaluation2);
    }
}
