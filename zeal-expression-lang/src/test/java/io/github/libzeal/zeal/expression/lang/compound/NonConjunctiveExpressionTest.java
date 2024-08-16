package io.github.libzeal.zeal.expression.lang.compound;

import io.github.libzeal.zeal.expression.lang.Expression;
import io.github.libzeal.zeal.expression.lang.ExpressionAssertions;
import io.github.libzeal.zeal.expression.lang.Expressions;
import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.lang.rationale.SimpleRationale;
import io.github.libzeal.zeal.expression.lang.unary.UnaryExpression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
    void whenWithDefaultName_thenCorrectNameThrown() {

        final List<Expression> children = new ArrayList<>();

        final Expression expression = NonConjunctiveExpression.withDefaultName(children);

        assertEquals(NonConjunctiveExpression.DEFAULT_NAME, expression.name());
    }

    @Test
    void givenValidName_whenName_thenNameIsCorrect() {
        assertEquals(name, expression.name());
    }

    @Test
    void givenNoSubExpression_whenEvaluate_thenEvaluationIsCorrect() {

        Evaluation evaluation = expression.evaluate();

        assertEquals(Result.TRUE, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertTrue(evaluation.children().isEmpty());
    }

    @Test
    void givenOnePassingExpressionAppended_whenEvaluate_thenEvaluationIsCorrect() {

        UnaryExpression<Object> subExpression = Expressions.unaryExpression(Result.TRUE);

        expression.append(subExpression);

        Evaluation evaluation = expression.evaluate();

        assertEquals(Result.FALSE, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertEquals(1, evaluation.children().size());
    }

    @Test
    void givenOneFailingExpressionAppended_whenEvaluate_thenEvaluationIsCorrect() {

        UnaryExpression<Object> subExpression = Expressions.unaryExpression(Result.FALSE);

        expression.append(subExpression);

        Evaluation evaluation = expression.evaluate();

        assertEquals(Result.TRUE, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertEquals(1, evaluation.children().size());
    }

    @Test
    void givenOneSkippedExpressionAppended_whenEvaluate_thenEvaluationIsCorrect() {

        UnaryExpression<Object> subExpression = Expressions.unaryExpression(Result.SKIPPED);

        expression.append(subExpression);

        Evaluation evaluation = expression.evaluate();

        assertEquals(Result.SKIPPED, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertEquals(1, evaluation.children().size());
    }

    @Test
    void givenOneFailingExpressionPrependedBeforePassingExpression_whenEvaluate_thenEvaluationIsCorrect() {

        UnaryExpression<Object> passingSubExpression = Expressions.unaryExpression(Result.TRUE);
        UnaryExpression<Object> failingSubExpression = Expressions.unaryExpression(Result.FALSE);

        expression.append(passingSubExpression);
        expression.prepend(failingSubExpression);

        Evaluation evaluation = expression.evaluate();

        assertEquals(Result.TRUE, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertEquals(2, evaluation.children().size());
        assertIsSkipped(passingSubExpression);
    }

    private static void assertIsSkipped(UnaryExpression<Object> expression) {
        verify(expression, times(1)).skip();
        verify(expression, never()).evaluate();
    }

    private static void assertIsNotSkipped(UnaryExpression<Object> expression) {
        verify(expression, never()).skip();
        verify(expression, times(1)).evaluate();
    }

    @Test
    void givenOnePassingExpressionPrependedBeforeFailingExpression_whenEvaluate_thenEvaluationIsCorrect() {

        UnaryExpression<Object> passingSubExpression = Expressions.unaryExpression(Result.TRUE);
        UnaryExpression<Object> failingSubExpression = Expressions.unaryExpression(Result.FALSE);

        expression.append(failingSubExpression);
        expression.prepend(passingSubExpression);

        Evaluation evaluation = expression.evaluate();

        assertEquals(Result.TRUE, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertEquals(2, evaluation.children().size());
        assertIsNotSkipped(failingSubExpression);
    }
    
    @Test
    void givenNoChildren_whenSkip_thenEvaluationIsCorrect() {
        
        Evaluation skippedEvaluation = expression.skip();

        assertEquals(expression.name(), skippedEvaluation.name());
        assertEquals(Result.SKIPPED, skippedEvaluation.result());
        assertEquals(SimpleRationale.skipped(), skippedEvaluation.rationale());
        assertTrue(skippedEvaluation.children().isEmpty());
    }

    @Test
    void givenOneChild_whenSkip_thenEvaluationIsCorrect() {

        final String subExpressionName = "expression 1";
        UnaryExpression<Object> subExpression = Expressions.unaryExpression(subExpressionName, Result.TRUE);

        expression.append(subExpression);

        Evaluation skippedEvaluation = expression.skip();

        assertEquals(expression.name(), skippedEvaluation.name());
        assertEquals(Result.SKIPPED, skippedEvaluation.result());
        assertEquals(SimpleRationale.skipped(), skippedEvaluation.rationale());
        assertEquals(1, skippedEvaluation.children().size());
        ExpressionAssertions.assertHasChildWithName(skippedEvaluation, subExpressionName);
    }

    @Test
    void givenTwoChildren_whenSkip_thenEvaluationIsCorrect() {

        final String subExpressionName1 = "expression 1";
        final String subExpressionName2 = "expression 2";
        UnaryExpression<Object> subExpression1 = Expressions.unaryExpression(subExpressionName1, Result.TRUE);
        UnaryExpression<Object> subExpression2 = Expressions.unaryExpression(subExpressionName2, Result.TRUE);

        expression.append(subExpression1);
        expression.append(subExpression2);

        Evaluation skippedEvaluation = expression.skip();

        assertEquals(expression.name(), skippedEvaluation.name());
        assertEquals(Result.SKIPPED, skippedEvaluation.result());
        assertEquals(SimpleRationale.skipped(), skippedEvaluation.rationale());
        assertEquals(2, skippedEvaluation.children().size());
        ExpressionAssertions.assertHasChildWithName(skippedEvaluation, subExpressionName1);
        ExpressionAssertions.assertHasChildWithName(skippedEvaluation, subExpressionName2);
    }
}
