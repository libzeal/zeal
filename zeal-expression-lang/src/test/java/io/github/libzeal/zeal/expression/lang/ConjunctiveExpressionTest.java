package io.github.libzeal.zeal.expression.lang;

import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.lang.rationale.SimpleRationale;
import io.github.libzeal.zeal.expression.lang.unary.UnaryExpression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConjunctiveExpressionTest {

    private String name;
    private ConjunctiveExpression expression;

    @BeforeEach
    void setUp() {

        name = "foo";

        expression = new ConjunctiveExpression(name, new ArrayList<>());
    }

    @Test
    void givenNullName_whenConstruct_thenExceptionThrown() {

        final ArrayList<Expression> list = new ArrayList<>();

        assertThrows(
            NullPointerException.class,
            () -> new ConjunctiveExpression(null, list)
        );
    }

    @Test
    void givenNullExpressionList_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new ConjunctiveExpression(name, null)
        );
    }

    @Test
    void givenNullName_whenConstructWithNameOnly_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new ConjunctiveExpression(null)
        );
    }

    @Test
    void givenValidName_whenName_thenNameIsCorrect() {
        assertEquals(name, expression.name());
    }

    @Test
    void givenNoSubExpression_whenEvaluate_thenEvaluationIsCorrect() {

        Evaluation evaluation = expression.evaluate();

        assertEquals(Result.PASSED, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertTrue(evaluation.children().isEmpty());
    }

    @Test
    void givenOnePassingCriterionAppended_whenEvaluate_thenEvaluationIsCorrect() {

        UnaryExpression<Object> subExpression = Expressions.unaryExpression(Result.PASSED);

        expression.append(subExpression);

        Evaluation evaluation = expression.evaluate();

        assertEquals(Result.PASSED, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertEquals(1, evaluation.children().size());
    }

    @Test
    void givenOneFailingCriterionAppended_whenEvaluate_thenEvaluationIsCorrect() {

        UnaryExpression<Object> subExpression = Expressions.unaryExpression(Result.FAILED);

        expression.append(subExpression);

        Evaluation evaluation = expression.evaluate();

        assertEquals(Result.FAILED, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertEquals(1, evaluation.children().size());
    }

    @Test
    void givenOneSkippedCriterionAppended_whenEvaluate_thenEvaluationIsCorrect() {

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

        UnaryExpression<Object> passingSubExpression = Expressions.unaryExpression(Result.PASSED);
        UnaryExpression<Object> failingSubExpression = Expressions.unaryExpression(Result.FAILED);

        expression.append(passingSubExpression);
        expression.prepend(failingSubExpression);

        Evaluation evaluation = expression.evaluate();

        assertEquals(Result.FAILED, evaluation.result());
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

        UnaryExpression<Object> passingSubExpression = Expressions.unaryExpression(Result.PASSED);
        UnaryExpression<Object> failingSubExpression = Expressions.unaryExpression(Result.FAILED);

        expression.append(failingSubExpression);
        expression.prepend(passingSubExpression);

        Evaluation evaluation = expression.evaluate();

        assertEquals(Result.FAILED, evaluation.result());
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
        UnaryExpression<Object> subExpression = Expressions.unaryExpression(subExpressionName, Result.PASSED);

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
        UnaryExpression<Object> subExpression1 = Expressions.unaryExpression(subExpressionName1, Result.PASSED);
        UnaryExpression<Object> subExpression2 = Expressions.unaryExpression(subExpressionName2, Result.PASSED);

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
