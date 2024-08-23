package io.github.libzeal.zeal.logic.compound;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import io.github.libzeal.zeal.logic.test.Expressions;
import io.github.libzeal.zeal.logic.unary.UnaryExpression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.github.libzeal.zeal.logic.compound.CompoundEvaluator.CompoundRationaleBuilder.format;
import static io.github.libzeal.zeal.logic.compound.CompoundEvaluator.CompoundRationaleBuilder.formatFailed;
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

        final UnaryExpression<Object> subExpression = Expressions.unaryExpression(Result.TRUE);

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

        final UnaryExpression<Object> subExpression = Expressions.unaryExpression(Result.FALSE);

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

        final UnaryExpression<Object> subExpression = Expressions.unaryExpression(Result.SKIPPED);

        expression.append(subExpression);

        final Evaluation evaluation = expression.evaluate();
        final Rationale rationale = evaluation.rationale();

        assertEquals(Result.SKIPPED, evaluation.result());
        assertEquals(name, evaluation.name());
        assertEquals(SimpleRationale.skipped(), rationale);
    }

    @Test
    void givenOneFailingExpressionPrependedBeforePassingExpression_whenEvaluate_thenEvaluationIsCorrect() {

        final UnaryExpression<Object> passingSubExpression = Expressions.unaryExpression(Result.TRUE);
        final UnaryExpression<Object> failingSubExpression = Expressions.unaryExpression(Result.FALSE);

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

    private static void assertIsSkipped(UnaryExpression<Object> expression) {
        verify(expression, times(1)).skip(any(Cause.class));
        verify(expression, never()).evaluate();
    }

    private static void assertIsNotSkipped(UnaryExpression<Object> expression) {
        verify(expression, never()).skip(any(Cause.class));
        verify(expression, times(1)).evaluate();
    }

    @Test
    void givenOnePassingExpressionPrependedBeforeFailingExpression_whenEvaluate_thenEvaluationIsCorrect() {

        final UnaryExpression<Object> passingSubExpression = Expressions.unaryExpression(Result.TRUE);
        final UnaryExpression<Object> failingSubExpression = Expressions.unaryExpression(Result.FALSE);

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
    
    @Test
    void givenNoChildren_whenSkip_thenEvaluationIsCorrect() {
        
        final Evaluation skippedEvaluation = expression.skip(rootCause());

        assertEquals(expression.name(), skippedEvaluation.name());
        assertEquals(Result.SKIPPED, skippedEvaluation.result());
        assertEquals(SimpleRationale.skipped(), skippedEvaluation.rationale());
    }

    private static Cause rootCause() {
        return mock(Cause.class);
    }

    @Test
    void givenOneChild_whenSkip_thenEvaluationIsCorrect() {

        final String subExpressionName = "expression 1";
        final UnaryExpression<Object> subExpression = Expressions.unaryExpression(subExpressionName, Result.TRUE);

        expression.append(subExpression);

        final Evaluation skippedEvaluation = expression.skip(rootCause());

        assertEquals(expression.name(), skippedEvaluation.name());
        assertEquals(Result.SKIPPED, skippedEvaluation.result());
        assertEquals(SimpleRationale.skipped(), skippedEvaluation.rationale());
    }

    @Test
    void givenTwoChildren_whenSkip_thenEvaluationIsCorrect() {

        final String subExpressionName1 = "expression 1";
        final String subExpressionName2 = "expression 2";
        final UnaryExpression<Object> subExpression1 = Expressions.unaryExpression(subExpressionName1, Result.TRUE);
        final UnaryExpression<Object> subExpression2 = Expressions.unaryExpression(subExpressionName2, Result.TRUE);

        expression.append(subExpression1);
        expression.append(subExpression2);

        final Evaluation skippedEvaluation = expression.skip(rootCause());

        assertEquals(expression.name(), skippedEvaluation.name());
        assertEquals(Result.SKIPPED, skippedEvaluation.result());
        assertEquals(SimpleRationale.skipped(), skippedEvaluation.rationale());
    }

    @Test
    void givenNullRootCause_whenEvaluate_thenRootCauseIsCorrect() {

        final UnaryExpression<Object> subExpression =
            Expressions.unaryExpressionWithRootCause(Result.TRUE, e -> null);

        expression.append(subExpression);

        final Evaluation evaluation = expression.evaluate();

        assertEquals(subExpression.name(), evaluation.cause().evaluation().name());
    }
}
