package io.github.libzeal.zeal.logic;

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

class OrExpressionTest {

    private String name;
    private OrExpression expression;

    @BeforeEach
    void setUp() {

        name = "foo";

        expression = new OrExpression(name, List.of());
    }

    @Test
    void givenNullName_whenConstruct_thenExceptionThrown() {

        final List<Expression> list = List.of();

        assertThrows(
            NullPointerException.class,
            () -> new OrExpression(null, list)
        );
    }

    @Test
    void givenNullPredicateList_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new OrExpression(name, null)
        );
    }

    @Test
    void givenNullListChildren_whenWithDefaultName_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> OrExpression.unnamed((List<Expression>) null)
        );
    }

    @Test
    void givenNullVarargsChildren_whenWithDefaultName_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> OrExpression.unnamed((Expression[]) null)
        );
    }

    @Test
    void givenNullChildEntry_whenConstruct_thenExceptionThrown() {

        final List<Expression> children = listWithSingleNull();

        assertThrows(
            NullPointerException.class,
            () -> new OrExpression("foo", children)
        );
    }

    @Test
    void givenNullChildEntry_whenWithDefaultName_thenExceptionThrown() {

        final List<Expression> children = listWithSingleNull();

        assertThrows(
            NullPointerException.class,
            () -> OrExpression.unnamed(children)
        );
    }

    @Test
    void whenWithDefaultName_thenCorrectNameThrown() {

        final List<Expression> children = List.of();

        final Expression defaultNameExpression = OrExpression.unnamed(children);

        assertEquals(OrExpression.DEFAULT_NAME, defaultNameExpression.name());
    }

    @Test
    void givenValidName_whenName_thenNameIsCorrect() {
        assertEquals(name, expression.name());
    }

    @Test
    void givenNoSubPredicate_whenEvaluate_thenEvaluationIsCorrect() {

        final Evaluation evaluation = expression.evaluate();
        final Rationale rationale = evaluation.rationale();

        assertFalse(evaluation.isTrue());
        assertEquals(name, evaluation.name());
        assertEquals(OrExpression.EXPECTED, rationale.expected());
        assertEquals(format(0, 0, 0), rationale.actual());
        assertFalse(rationale.hint().isPresent());
    }

    @Test
    void givenOnePassingExpression_whenEvaluate_thenEvaluationIsCorrect() {

        final Expression subPredicate = TestExpressions.tautology();

        final Evaluation evaluation = new OrExpression(name, List.of(subPredicate)).evaluate();
        final Rationale rationale = evaluation.rationale();

        assertTrue(evaluation.isTrue());
        assertEquals(name, evaluation.name());
        assertEquals(OrExpression.EXPECTED, rationale.expected());
        assertEquals(format(1, 0, 0), rationale.actual());
        assertFalse(rationale.hint().isPresent());
    }

    @Test
    void givenOneFailingExpression_whenEvaluate_thenEvaluationIsCorrect() {

        final Expression subPredicate = TestExpressions.expression(false);

        final Evaluation evaluation = new OrExpression(name, List.of(subPredicate)).evaluate();
        final Rationale rationale = evaluation.rationale();

        assertFalse(evaluation.isTrue());
        assertEquals(name, evaluation.name());
        assertEquals(OrExpression.EXPECTED, rationale.expected());
        assertEquals(format(0, 1, 0), rationale.actual());
        assertFalse(rationale.hint().isPresent());
    }

    @Test
    void givenOneFailingPredicateBeforePassingPredicate_whenEvaluate_thenEvaluationIsCorrect() {

        final Expression passingSubPredicate = TestExpressions.expression(true);
        final Expression failingSubPredicate = TestExpressions.expression(false);

        final List<Expression> children = List.of(failingSubPredicate, passingSubPredicate);
        final Evaluation evaluation = new OrExpression(name, children).evaluate();
        final Rationale rationale = evaluation.rationale();

        assertTrue(evaluation.isTrue());
        assertEquals(name, evaluation.name());
        assertEquals(OrExpression.EXPECTED, rationale.expected());
        assertEquals(format(1, 1, 0), rationale.actual());
        assertFalse(rationale.hint().isPresent());
        assertIsNotSkipped(failingSubPredicate);
    }

    private static void assertIsSkipped(Expression expression) {
        verify(expression, never()).evaluate();
    }

    private static void assertIsNotSkipped(Expression expression) {
        verify(expression, times(1)).evaluate();
    }

    @Test
    void givenOnePassingPredicateBeforeFailingPredicate_whenEvaluate_thenEvaluationIsCorrect() {

        final Expression passingSubPredicate = TestExpressions.expression(true);
        final Expression failingSubPredicate = TestExpressions.expression(false);

        final List<Expression> children = List.of(passingSubPredicate, failingSubPredicate);
        final Evaluation evaluation = new OrExpression(name, children).evaluate();
        final Rationale rationale = evaluation.rationale();

        assertTrue(evaluation.isTrue());
        assertEquals(name, evaluation.name());
        assertEquals(OrExpression.EXPECTED, rationale.expected());
        assertEquals(format(1, 0, 1), rationale.actual());
        assertFalse(rationale.hint().isPresent());
        assertIsSkipped(failingSubPredicate);
    }

    @Test
    void givenMutableChildren_whenConstruct_thenChildrenAreCopied() {

        final Expression child = TestExpressions.tautology();
        final List<Expression> children = new ArrayList<>(List.of(child));

        final OrExpression copied = new OrExpression(name, children);

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
    void givenEmptyOr_whenEvaluate_thenCauseIsSelf() {

        final Evaluation evaluation = or("OR").evaluate();

        assertFalse(evaluation.isTrue());
        assertCauseIsSelf(evaluation);
        assertTraces(evaluation, "OR");
    }

    @Test
    void givenOrWithAllFalse_whenEvaluate_thenCauseIsAllChildren() {

        final Evaluation evaluation = or("OR", f("f1"), f("f2")).evaluate();

        assertFalse(evaluation.isTrue());
        assertCause(evaluation, child(evaluation, 0), child(evaluation, 1));
        assertTraces(evaluation, "OR -> f1", "OR -> f2");
        assertRootCauses(evaluation, child(evaluation, 0), child(evaluation, 1));
    }

    @Test
    void givenOrWithTrueInMiddle_whenEvaluate_thenCauseIsFirstTrueAndRemainingSkipped() {

        final Evaluation evaluation = or("OR", f("f1"), t("t2"), f("f3")).evaluate();
        final Evaluation t2 = child(evaluation, 1);
        final Skipped f3 = skipped(evaluation, 2);

        assertTrue(evaluation.isTrue());
        assertCause(evaluation, t2);
        assertTraces(evaluation, "OR -> t2");

        assertEquals("f3", f3.name());
        assertCause(f3, t2);
        assertTraces(f3, "t2");
    }

    @Test
    void givenOrWithTrueLast_whenEvaluate_thenCauseIsTrueChildOnly() {

        final Evaluation evaluation = or("OR", f("f1"), t("t2")).evaluate();

        assertTrue(evaluation.isTrue());
        assertCause(evaluation, child(evaluation, 1));
        assertTraces(evaluation, "OR -> t2");
    }

    @Test
    void givenOrOfFalseAndThenTrueAnd_whenEvaluate_thenTracesFollowEveryChildOfTrueAnd() {

        final Evaluation evaluation = or(
            "OR",
            and("AND1", t("t1"), f("f2")),
            and("AND2", t("t3"), t("t4"))
        ).evaluate();

        assertTrue(evaluation.isTrue());
        assertTraces(evaluation, "OR -> AND2 -> t3", "OR -> AND2 -> t4");
    }

    @Test
    void givenOrOfTrueAnd_whenEvaluate_thenSkippedSiblingTracesFollowTrueAnd() {

        final Evaluation evaluation = or("OR", and("AND", t("t1"), t("t2")), f("f3")).evaluate();
        final Skipped f3 = skipped(evaluation, 1);

        assertTrue(evaluation.isTrue());
        assertTraces(evaluation, "OR -> AND -> t1", "OR -> AND -> t2");

        assertCause(f3, child(evaluation, 0));
        assertTraces(f3, "AND -> t1", "AND -> t2");
    }
}
