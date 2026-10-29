package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Skipped;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.test.TestExpressions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.github.libzeal.zeal.logic.CompoundEvaluator.format;
import static io.github.libzeal.zeal.logic.test.Arguments.listWithSingleNull;
import static io.github.libzeal.zeal.logic.test.CauseAssertions.*;
import static io.github.libzeal.zeal.logic.test.NamedExpressions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AndExpressionTest {

    private String name;
    private AndExpression expression;

    @BeforeEach
    void setUp() {

        name = "foo";

        expression = new AndExpression(name, List.of());
    }

    @Test
    void givenNullName_whenConstruct_thenExceptionThrown() {

        final List<Expression> children = List.of();

        assertThrows(
            NullPointerException.class,
            () -> new AndExpression(null, children)
        );
    }

    @Test
    void givenNullExpressionList_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new AndExpression(name, null)
        );
    }

    @Test
    void givenNullListChildren_whenWithDefaultName_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> AndExpression.unnamed((List<Expression>) null)
        );
    }

    @Test
    void givenNullVarargsChildren_whenWithDefaultName_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> AndExpression.unnamed((Expression[]) null)
        );
    }

    @Test
    void givenNullChildEntry_whenConstruct_thenExceptionThrown() {

        final List<Expression> children = listWithSingleNull();

        assertThrows(
            NullPointerException.class,
            () -> new AndExpression("foo", children)
        );
    }

    @Test
    void givenNullChildEntry_whenWithDefaultName_thenExceptionThrown() {

        final List<Expression> children = listWithSingleNull();

        assertThrows(
            NullPointerException.class,
            () -> AndExpression.unnamed(children)
        );
    }

    @Test
    void whenWithDefaultName_thenCorrectNameThrown() {

        final List<Expression> children = List.of();

        final Expression defaultNameExpression = AndExpression.unnamed(children);

        assertEquals(AndExpression.DEFAULT_NAME, defaultNameExpression.name());
    }

    @Test
    void givenValidName_whenName_thenNameIsCorrect() {
        assertEquals(name, expression.name());
    }

    @Test
    void givenNoSubExpression_whenEvaluate_thenEvaluationIsCorrect() {

        final Evaluation evaluation = expression.evaluate();
        final Rationale rationale = evaluation.rationale();

        assertTrue(evaluation.isTrue());
        assertEquals(name, evaluation.name());
        assertEquals(AndExpression.EXPECTED, rationale.expected());
        assertEquals(format(0, 0, 0), rationale.actual());
        assertFalse(rationale.hint().isPresent());
    }

    @Test
    void givenOnePassingExpression_whenEvaluate_thenEvaluationIsCorrect() {

        final Expression subExpression = TestExpressions.expression(true);

        final Evaluation evaluation = new AndExpression(name, List.of(subExpression)).evaluate();
        final Rationale rationale = evaluation.rationale();

        assertTrue(evaluation.isTrue());
        assertEquals(name, evaluation.name());
        assertEquals(AndExpression.EXPECTED, rationale.expected());
        assertEquals(format(1, 0, 0), rationale.actual());
        assertFalse(rationale.hint().isPresent());
    }

    @Test
    void givenOneFailingExpression_whenEvaluate_thenEvaluationIsCorrect() {

        final Expression subExpression = TestExpressions.expression(false);

        final Evaluation evaluation = new AndExpression(name, List.of(subExpression)).evaluate();
        final Rationale rationale = evaluation.rationale();

        assertFalse(evaluation.isTrue());
        assertEquals(name, evaluation.name());
        assertEquals(AndExpression.EXPECTED, rationale.expected());
        assertEquals(format(0, 1, 0), rationale.actual());
        assertFalse(rationale.hint().isPresent());
    }

    @Test
    void givenOneFailingExpressionBeforePassingExpression_whenEvaluate_thenEvaluationIsCorrect() {

        final Expression passingSubExpression = TestExpressions.expression(true);
        final Expression failingSubExpression = TestExpressions.expression(false);

        final List<Expression> children = List.of(failingSubExpression, passingSubExpression);
        final Evaluation evaluation = new AndExpression(name, children).evaluate();
        final Rationale rationale = evaluation.rationale();

        assertFalse(evaluation.isTrue());
        assertEquals(name, evaluation.name());
        assertEquals(AndExpression.EXPECTED, rationale.expected());
        assertEquals(format(0, 1, 1), rationale.actual());
        assertFalse(rationale.hint().isPresent());
        assertIsSkipped(passingSubExpression);
    }

    private static void assertIsSkipped(Expression expression) {
        verify(expression, never()).evaluate();
    }

    private static void assertIsNotSkipped(Expression expression) {
        verify(expression, times(1)).evaluate();
    }

    @Test
    void givenOnePassingExpressionBeforeFailingExpression_whenEvaluate_thenEvaluationIsCorrect() {

        final Expression passingSubExpression = TestExpressions.expression(true);
        final Expression failingSubExpression = TestExpressions.expression(false);

        final List<Expression> children = List.of(passingSubExpression, failingSubExpression);
        final Evaluation evaluation = new AndExpression(name, children).evaluate();
        final Rationale rationale = evaluation.rationale();

        assertFalse(evaluation.isTrue());
        assertEquals(name, evaluation.name());
        assertEquals(AndExpression.EXPECTED, rationale.expected());
        assertEquals(format(1, 1, 0), rationale.actual());
        assertFalse(rationale.hint().isPresent());
        assertIsNotSkipped(failingSubExpression);
    }

    @Test
    void givenMutableChildren_whenConstruct_thenChildrenAreCopied() {

        final Expression child = TestExpressions.tautology();
        final List<Expression> children = new ArrayList<>(List.of(child));

        final AndExpression copied = new AndExpression(name, children);

        children.clear();

        assertEquals(List.of(child), copied.children());
    }

    @Test
    void givenExpression_whenModifyChildren_thenExceptionThrown() {

        final List<Expression> children = expression.children();
        final Expression child = TestExpressions.tautology();

        assertThrows(
            UnsupportedOperationException.class,
            () -> children.add(child)
        );
    }

    @Test
    void givenEmptyAnd_whenEvaluate_thenCauseIsSelf() {

        final Evaluation evaluation = and("AND").evaluate();

        assertTrue(evaluation.isTrue());
        assertCauseIsSelf(evaluation);
        assertTraces(evaluation, "AND");
        assertRootCauses(evaluation, evaluation);
    }

    @Test
    void givenAndWithAllTrue_whenEvaluate_thenCauseIsAllChildren() {

        final Evaluation evaluation = and("AND", t("t1"), t("t2")).evaluate();

        assertTrue(evaluation.isTrue());
        assertCause(evaluation, child(evaluation, 0), child(evaluation, 1));
        assertTraces(evaluation, "AND -> t1", "AND -> t2");
        assertRootCauses(evaluation, child(evaluation, 0), child(evaluation, 1));
    }

    @Test
    void givenAndWithFalseInMiddle_whenEvaluate_thenCauseIsFirstFalseAndRemainingSkipped() {

        final Evaluation evaluation = and("AND", t("t1"), f("f2"), t("t3")).evaluate();
        final Evaluation f2 = child(evaluation, 1);
        final Skipped t3 = skipped(evaluation, 2);

        assertFalse(evaluation.isTrue());
        assertCause(evaluation, f2);
        assertTraces(evaluation, "AND -> f2");
        assertRootCauses(evaluation, f2);

        assertEquals("t3", t3.name());
        assertCause(t3, f2);
        assertTraces(t3, "f2");
        assertRootCauses(t3, f2);
    }

    @Test
    void givenAndWithSingleFalse_whenEvaluate_thenCauseIsFalseChild() {

        final Evaluation evaluation = and("AND", f("f1")).evaluate();

        assertFalse(evaluation.isTrue());
        assertCause(evaluation, child(evaluation, 0));
        assertTraces(evaluation, "AND -> f1");
    }

    @Test
    void givenAndWithFalseLast_whenEvaluate_thenCauseIsFalseChildOnly() {

        final Evaluation evaluation = and("AND", t("t1"), f("f2")).evaluate();

        assertFalse(evaluation.isTrue());
        assertCause(evaluation, child(evaluation, 1));
        assertTraces(evaluation, "AND -> f2");
    }

    @Test
    void givenAndOfTrueOrAndFalse_whenEvaluate_thenTraceFollowsFalseChild() {

        final Evaluation evaluation = and("AND", or("OR", f("f1"), t("t2")), f("f3")).evaluate();

        assertFalse(evaluation.isTrue());
        assertTraces(evaluation, "AND -> f3");
        assertTraces(child(evaluation, 0), "OR -> t2");
    }

    @Test
    void givenAndOfFalseOr_whenEvaluate_thenTracesFollowEveryOrChild() {

        final Evaluation evaluation = and("AND", or("OR", f("f1"), f("f2")), t("t3")).evaluate();
        final Skipped t3 = skipped(evaluation, 1);

        assertFalse(evaluation.isTrue());
        assertTraces(evaluation, "AND -> OR -> f1", "AND -> OR -> f2");
        assertRootCauses(evaluation, child(evaluation, 0, 0), child(evaluation, 0, 1));

        assertCause(t3, child(evaluation, 0));
        assertTraces(t3, "OR -> f1", "OR -> f2");
        assertRootCauses(t3, child(evaluation, 0, 0), child(evaluation, 0, 1));
    }

    @Test
    void givenDeeplyNestedAnd_whenEvaluate_thenTraceReachesDeepestFalse() {

        final Evaluation evaluation = and(
            "AND1",
            and("AND2", t("t1"), and("AND3", t("t2"), f("f3"))),
            t("t4")
        ).evaluate();
        final Skipped t4 = skipped(evaluation, 1);

        assertFalse(evaluation.isTrue());
        assertTraces(evaluation, "AND1 -> AND2 -> AND3 -> f3");
        assertRootCauses(evaluation, child(evaluation, 0, 1, 1));
        assertTraces(t4, "AND2 -> AND3 -> f3");
    }

    @Test
    void givenAndOfNegatedTrueOr_whenEvaluate_thenTraceReachesTrueRootCause() {

        final Evaluation evaluation = and("AND", not("NOT", or("OR", f("f1"), t("t2"))), t("t3")).evaluate();
        final Skipped t3 = skipped(evaluation, 1);

        assertFalse(evaluation.isTrue());
        assertTraces(evaluation, "AND -> NOT -> OR -> t2");
        assertTrue(evaluation.rootCauses().getFirst().isTrue());
        assertTraces(t3, "NOT -> OR -> t2");
    }

    @Test
    void givenMixedTree_whenEvaluate_thenTracesFollowEveryDecidingBranch() {

        final Evaluation evaluation = and(
            "AND1",
            or("OR", f("f1"), and("AND2", t("t2"), f("f3"), t("t4"))),
            not("NOT", t("t5"))
        ).evaluate();
        final Evaluation or = child(evaluation, 0);
        final Evaluation f3 = child(evaluation, 0, 1, 1);
        final Skipped t4 = skipped(evaluation, 0, 1, 2);
        final Skipped not = skipped(evaluation, 1);
        final List<Skipped> descendants = skippedDescendants(evaluation);

        assertFalse(evaluation.isTrue());
        assertCause(evaluation, or);
        assertTraces(evaluation, "AND1 -> OR -> f1", "AND1 -> OR -> AND2 -> f3");
        assertRootCauses(evaluation, child(evaluation, 0, 0), f3);

        assertCause(t4, f3);
        assertTraces(t4, "f3");

        assertCause(not, or);
        assertTraces(not, "OR -> f1", "OR -> AND2 -> f3");

        assertEquals(List.of("t4", "NOT", "t5"), descendants.stream().map(Skipped::name).toList());
        assertCause(descendants.get(2), or);
        assertTraces(descendants.get(2), "OR -> f1", "OR -> AND2 -> f3");
    }

    @Test
    void givenSkippedCompound_whenEvaluate_thenEveryDescendantIsCausedByDecider() {

        final Evaluation evaluation = and(
            "AND1",
            f("f1"),
            or("OR", t("t2"), and("AND2", t("t3"), t("t4")))
        ).evaluate();
        final Evaluation f1 = child(evaluation, 0);
        final List<Skipped> descendants = skippedDescendants(evaluation);

        assertTraces(evaluation, "AND1 -> f1");
        assertEquals(List.of("OR", "t2", "AND2", "t3", "t4"), descendants.stream().map(Skipped::name).toList());

        for (final Skipped descendant : descendants) {
            assertCause(descendant, f1);
            assertTraces(descendant, "f1");
            assertRootCauses(descendant, f1);
        }
    }

    @Test
    void givenSkippedNot_whenEvaluate_thenNotAndWrappedAreCausedByDecider() {

        final Evaluation evaluation = and("AND", f("f1"), not("NOT", t("t2"))).evaluate();
        final Evaluation f1 = child(evaluation, 0);
        final List<Skipped> descendants = skippedDescendants(evaluation);

        assertEquals(List.of("NOT", "t2"), descendants.stream().map(Skipped::name).toList());
        assertCause(descendants.get(0), f1);
        assertCause(descendants.get(1), f1);
    }

    @Test
    void givenFalseBeforeRemainingChildren_whenEvaluate_thenRemainingChildrenAreSkippedInOrder() {

        final Evaluation evaluation = and("AND", t("t1"), f("f2"), t("t3"), f("f4")).evaluate();
        final CompoundEvaluation compound = assertInstanceOf(CompoundEvaluation.class, evaluation);

        assertEquals(List.of(child(evaluation, 0), child(evaluation, 1)), compound.evaluated());
        assertEquals("t3", skipped(evaluation, 2).name());
        assertEquals("f4", skipped(evaluation, 3).name());
    }
}
