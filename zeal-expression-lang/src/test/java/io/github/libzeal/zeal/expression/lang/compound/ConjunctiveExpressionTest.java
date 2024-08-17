package io.github.libzeal.zeal.expression.lang.compound;

import io.github.libzeal.zeal.expression.lang.Expression;
import io.github.libzeal.zeal.expression.lang.Expressions;
import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.lang.evaluation.RootCause;
import io.github.libzeal.zeal.expression.lang.rationale.SimpleRationale;
import io.github.libzeal.zeal.expression.lang.unary.UnaryExpression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

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

        final List<Expression> list = new ArrayList<>();

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
    void givenNullChildren_whenWithDefaultName_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> ConjunctiveExpression.withDefaultName(null)
        );
    }

    @Test
    void whenWithDefaultName_thenCorrectNameThrown() {

        final List<Expression> children = new ArrayList<>();

        final Expression expression = ConjunctiveExpression.withDefaultName(children);

        assertEquals(ConjunctiveExpression.DEFAULT_NAME, expression.name());
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
    }

    @Test
    void givenOnePassingExpressionAppended_whenEvaluate_thenEvaluationIsCorrect() {

        UnaryExpression<Object> subExpression = Expressions.unaryExpression(Result.TRUE);

        expression.append(subExpression);

        Evaluation evaluation = expression.evaluate();

        assertEquals(Result.TRUE, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
    }

    @Test
    void givenOneFailingExpressionAppended_whenEvaluate_thenEvaluationIsCorrect() {

        UnaryExpression<Object> subExpression = Expressions.unaryExpression(Result.FALSE);

        expression.append(subExpression);

        Evaluation evaluation = expression.evaluate();

        assertEquals(Result.FALSE, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
    }

    @Test
    void givenOneSkippedExpressionAppended_whenEvaluate_thenEvaluationIsCorrect() {

        UnaryExpression<Object> subExpression = Expressions.unaryExpression(Result.SKIPPED);

        expression.append(subExpression);

        Evaluation evaluation = expression.evaluate();

        assertEquals(Result.SKIPPED, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
    }

    @Test
    void givenOneFailingExpressionPrependedBeforePassingExpression_whenEvaluate_thenEvaluationIsCorrect() {

        UnaryExpression<Object> passingSubExpression = Expressions.unaryExpression(Result.TRUE);
        UnaryExpression<Object> failingSubExpression = Expressions.unaryExpression(Result.FALSE);

        expression.append(passingSubExpression);
        expression.prepend(failingSubExpression);

        Evaluation evaluation = expression.evaluate();

        assertEquals(Result.FALSE, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertIsSkipped(passingSubExpression);
    }

    private static void assertIsSkipped(UnaryExpression<Object> expression) {
        verify(expression, times(1)).skip(any(RootCause.class));
        verify(expression, never()).evaluate();
    }

    private static void assertIsNotSkipped(UnaryExpression<Object> expression) {
        verify(expression, never()).skip(any(RootCause.class));
        verify(expression, times(1)).evaluate();
    }

    @Test
    void givenOnePassingExpressionPrependedBeforeFailingExpression_whenEvaluate_thenEvaluationIsCorrect() {

        UnaryExpression<Object> passingSubExpression = Expressions.unaryExpression(Result.TRUE);
        UnaryExpression<Object> failingSubExpression = Expressions.unaryExpression(Result.FALSE);

        expression.append(failingSubExpression);
        expression.prepend(passingSubExpression);

        Evaluation evaluation = expression.evaluate();

        assertEquals(Result.FALSE, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertIsNotSkipped(failingSubExpression);
    }
    
    @Test
    void givenNoChildren_whenSkip_thenEvaluationIsCorrect() {
        
        Evaluation skippedEvaluation = expression.skip(rootCause());

        assertEquals(expression.name(), skippedEvaluation.name());
        assertEquals(Result.SKIPPED, skippedEvaluation.result());
        assertEquals(SimpleRationale.skipped(), skippedEvaluation.rationale());
    }

    private static RootCause rootCause() {
        return mock(RootCause.class);
    }

    @Test
    void givenOneChild_whenSkip_thenEvaluationIsCorrect() {

        final String subExpressionName = "expression 1";
        UnaryExpression<Object> subExpression = Expressions.unaryExpression(subExpressionName, Result.TRUE);

        expression.append(subExpression);

        Evaluation skippedEvaluation = expression.skip(rootCause());

        assertEquals(expression.name(), skippedEvaluation.name());
        assertEquals(Result.SKIPPED, skippedEvaluation.result());
        assertEquals(SimpleRationale.skipped(), skippedEvaluation.rationale());
    }

    @Test
    void givenTwoChildren_whenSkip_thenEvaluationIsCorrect() {

        final String subExpressionName1 = "expression 1";
        final String subExpressionName2 = "expression 2";
        UnaryExpression<Object> subExpression1 = Expressions.unaryExpression(subExpressionName1, Result.TRUE);
        UnaryExpression<Object> subExpression2 = Expressions.unaryExpression(subExpressionName2, Result.TRUE);

        expression.append(subExpression1);
        expression.append(subExpression2);

        Evaluation skippedEvaluation = expression.skip(rootCause());

        assertEquals(expression.name(), skippedEvaluation.name());
        assertEquals(Result.SKIPPED, skippedEvaluation.result());
        assertEquals(SimpleRationale.skipped(), skippedEvaluation.rationale());
    }
}
