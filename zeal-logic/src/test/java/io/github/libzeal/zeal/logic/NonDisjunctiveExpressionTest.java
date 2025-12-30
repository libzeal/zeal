package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.evaluation.cause.CauseGenerator;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import io.github.libzeal.zeal.logic.test.Expressions;
import io.github.libzeal.zeal.logic.unary.UnaryExpression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.github.libzeal.zeal.logic.CompoundEvaluator.CompoundRationaleBuilder.format;
import static io.github.libzeal.zeal.logic.CompoundEvaluator.CompoundRationaleBuilder.formatFailed;
import static io.github.libzeal.zeal.logic.test.Arguments.listWithSingleNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NonDisjunctiveExpressionTest {

    private String name;
    private NonDisjunctiveExpression expression;

    @BeforeEach
    void setUp() {

        name = "foo";

        expression = new NonDisjunctiveExpression(name, new ArrayList<>());
    }

    @Test
    void givenNullName_whenConstruct_thenExceptionThrown() {

        final ArrayList<Expression> list = new ArrayList<>();

        assertThrows(
            NullPointerException.class,
            () -> new NonDisjunctiveExpression(null, list)
        );
    }

    @Test
    void givenNullPredicateList_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new NonDisjunctiveExpression(name, null)
        );
    }

    @Test
    void givenNullName_whenConstructWithNameOnly_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new NonDisjunctiveExpression(null)
        );
    }

    @Test
    void givenNullChildren_whenWithDefaultName_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> NonDisjunctiveExpression.withDefaultName(null)
        );
    }

    @Test
    void givenNullChildEntry_whenConstruct_thenExceptionThrown() {

        final List<Expression> children = listWithSingleNull();

        assertThrows(
            NullPointerException.class,
            () -> new NonDisjunctiveExpression("foo", children)
        );
    }

    @Test
    void givenNullChildEntry_whenWithDefaultName_thenExceptionThrown() {

        final List<Expression> children = listWithSingleNull();

        assertThrows(
            NullPointerException.class,
            () -> NonDisjunctiveExpression.withDefaultName(children)
        );
    }

    @Test
    void whenWithDefaultName_thenCorrectNameThrown() {

        final List<Expression> children = new ArrayList<>();

        final Expression defaultNameExpression = NonDisjunctiveExpression.withDefaultName(children);

        assertEquals(NonDisjunctiveExpression.DEFAULT_NAME, defaultNameExpression.name());
    }

    @Test
    void givenValidName_whenName_thenNameIsCorrect() {
        assertEquals(name, expression.name());
    }

    @Test
    void givenNoSubPredicate_whenEvaluate_thenEvaluationIsCorrect() {

        final Evaluation evaluation = expression.evaluate();
        final Rationale rationale = evaluation.rationale();

        assertEquals(Result.TRUE, evaluation.result());
        assertEquals(name, evaluation.name());
        assertEquals(formatFailed(0), rationale.expected());
        assertEquals(format(0, 0, 0), rationale.actual());
        assertFalse(rationale.hint().isPresent());
    }

    @Test
    void givenOnePassingExpressionAppended_whenEvaluate_thenEvaluationIsCorrect() {

        final Expression subPredicate = Expressions.expression(Result.TRUE);

        expression.append(subPredicate);

        final Evaluation evaluation = expression.evaluate();
        final Rationale rationale = evaluation.rationale();

        assertEquals(Result.FALSE, evaluation.result());
        assertEquals(name, evaluation.name());
        assertEquals(formatFailed(1), rationale.expected());
        assertEquals(format(1, 0, 0), rationale.actual());
        assertFalse(rationale.hint().isPresent());
    }

    @Test
    void givenOneFailingExpressionAppended_whenEvaluate_thenEvaluationIsCorrect() {

        final Expression subPredicate = Expressions.expression(Result.FALSE);

        expression.append(subPredicate);

        final Evaluation evaluation = expression.evaluate();
        final Rationale rationale = evaluation.rationale();

        assertEquals(Result.TRUE, evaluation.result());
        assertEquals(name, evaluation.name());
        assertEquals(formatFailed(1), rationale.expected());
        assertEquals(format(0, 1, 0), rationale.actual());
        assertFalse(rationale.hint().isPresent());
    }

    @Test
    void givenOneSkippedExpressionAppended_whenEvaluate_thenEvaluationIsCorrect() {

        final Expression subPredicate = Expressions.expression(Result.SKIPPED);

        expression.append(subPredicate);

        final Evaluation evaluation = expression.evaluate();
        final Rationale rationale = evaluation.rationale();

        assertEquals(Result.SKIPPED, evaluation.result());
        assertEquals(name, evaluation.name());
        assertEquals(SimpleRationale.skipped(), rationale);
    }

    @Test
    void givenOneFailingPredicatePrependedBeforePassingPredicate_whenEvaluate_thenEvaluationIsCorrect() {

        final Expression passingSubPredicate = Expressions.expression(Result.TRUE);
        final Expression failingSubPredicate = Expressions.expression(Result.FALSE);

        expression.append(passingSubPredicate);
        expression.prepend(failingSubPredicate);

        final Evaluation evaluation = expression.evaluate();
        final Rationale rationale = evaluation.rationale();

        assertEquals(Result.FALSE, evaluation.result());
        assertEquals(name, evaluation.name());
        assertEquals(formatFailed(2), rationale.expected());
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
    void givenOnePassingPredicatePrependedBeforeFailingPredicate_whenEvaluate_thenEvaluationIsCorrect() {

        final Expression passingSubPredicate = Expressions.expression(Result.TRUE);
        final Expression failingSubPredicate = Expressions.expression(Result.FALSE);

        expression.append(failingSubPredicate);
        expression.prepend(passingSubPredicate);

        final Evaluation evaluation = expression.evaluate();
        final Rationale rationale = evaluation.rationale();

        assertEquals(Result.FALSE, evaluation.result());
        assertEquals(name, evaluation.name());
        assertEquals(formatFailed(2), rationale.expected());
        assertEquals(format(1, 0, 1), rationale.actual());
        assertFalse(rationale.hint().isPresent());
        assertIsSkipped(failingSubPredicate);
    }

    private static Cause rootCause() {
        return mock(Cause.class);
    }

    @Test
    void givenSelfRootCause_whenEvaluate_thenRootCauseIsCorrect() {

        final Expression subExpression =
            Expressions.expressionWithRootCause(Result.TRUE, CauseGenerator.self());

        expression.append(subExpression);

        final Evaluation evaluation = expression.evaluate();

        assertEquals(subExpression.name(), evaluation.cause().rootCause().evaluation().name());
    }
}
