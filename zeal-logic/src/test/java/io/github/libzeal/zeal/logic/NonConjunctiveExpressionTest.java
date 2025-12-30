package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.cause.CauseGenerator;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import io.github.libzeal.zeal.logic.test.Expressions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.github.libzeal.zeal.logic.CompoundEvaluator.CompoundRationaleBuilder.format;
import static io.github.libzeal.zeal.logic.CompoundEvaluator.CompoundRationaleBuilder.formatFailed;
import static io.github.libzeal.zeal.logic.test.Arguments.listWithSingleNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NonConjunctiveExpressionTest {

    private String name;
    private NonConjunctiveExpression expression;

    @BeforeEach
    void setUp() {

        name = "foo";

        expression = new NonConjunctiveExpression(name, new ArrayList<>());
    }

    @Test
    void givenNullName_whenConstruct_thenExceptionThrown() {

        final ArrayList<Expression> list = new ArrayList<>();

        assertThrows(
            NullPointerException.class,
            () -> new NonConjunctiveExpression(null, list)
        );
    }

    @Test
    void givenNullExpressionList_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new NonConjunctiveExpression(name, null)
        );
    }

    @Test
    void givenNullName_whenConstructWithNameOnly_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new NonConjunctiveExpression(null)
        );
    }

    @Test
    void givenNullChildren_whenWithDefaultName_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> NonConjunctiveExpression.withDefaultName(null)
        );
    }

    @Test
    void givenNullChildEntry_whenConstruct_thenExceptionThrown() {

        final List<Expression> children = listWithSingleNull();

        assertThrows(
            NullPointerException.class,
            () -> new NonConjunctiveExpression("foo", children)
        );
    }

    @Test
    void givenNullChildEntry_whenWithDefaultName_thenExceptionThrown() {

        final List<Expression> children = listWithSingleNull();

        assertThrows(
            NullPointerException.class,
            () -> NonConjunctiveExpression.withDefaultName(children)
        );
    }

    @Test
    void whenWithDefaultName_thenCorrectNameThrown() {

        final List<Expression> children = new ArrayList<>();

        final Expression defaultNameExpression = NonConjunctiveExpression.withDefaultName(children);

        assertEquals(NonConjunctiveExpression.DEFAULT_NAME, defaultNameExpression.name());
    }

    @Test
    void givenValidName_whenName_thenNameIsCorrect() {
        assertEquals(name, expression.name());
    }

    @Test
    void givenNoSubExpression_whenEvaluate_thenEvaluationIsCorrect() {

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

        final Expression subExpression = Expressions.expression(Result.TRUE);

        expression.append(subExpression);

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

        final Expression subExpression = Expressions.expression(Result.FALSE);

        expression.append(subExpression);

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

        final Expression subExpression = Expressions.expression(Result.SKIPPED);

        expression.append(subExpression);

        final Evaluation evaluation = expression.evaluate();
        final Rationale rationale = evaluation.rationale();

        assertEquals(Result.SKIPPED, evaluation.result());
        assertEquals(name, evaluation.name());
        assertEquals(SimpleRationale.skipped(), rationale);
    }

    @Test
    void givenOneFailingExpressionPrependedBeforePassingExpression_whenEvaluate_thenEvaluationIsCorrect() {

        final Expression passingSubExpression = Expressions.expression(Result.TRUE);
        final Expression failingSubExpression = Expressions.expression(Result.FALSE);

        expression.append(passingSubExpression);
        expression.prepend(failingSubExpression);

        final Evaluation evaluation = expression.evaluate();
        final Rationale rationale = evaluation.rationale();

        assertEquals(Result.TRUE, evaluation.result());
        assertEquals(name, evaluation.name());
        assertEquals(formatFailed(1), rationale.expected());
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
    void givenOnePassingExpressionPrependedBeforeFailingExpression_whenEvaluate_thenEvaluationIsCorrect() {

        final Expression passingSubExpression = Expressions.expression(Result.TRUE);
        final Expression failingSubExpression = Expressions.expression(Result.FALSE);

        expression.append(failingSubExpression);
        expression.prepend(passingSubExpression);

        final Evaluation evaluation = expression.evaluate();
        final Rationale rationale = evaluation.rationale();

        assertEquals(Result.TRUE, evaluation.result());
        assertEquals(name, evaluation.name());
        assertEquals(formatFailed(1), rationale.expected());
        assertEquals(format(1, 1, 0), rationale.actual());
        assertFalse(rationale.hint().isPresent());
        assertIsNotSkipped(failingSubExpression);
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
