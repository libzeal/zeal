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

class DisjunctiveExpressionTest {

    private String name;
    private DisjunctiveExpression expression;

    @BeforeEach
    void setUp() {

        name = "foo";

        expression = new DisjunctiveExpression(name, new ArrayList<>());
    }

    @Test
    void givenNullName_whenConstruct_thenExceptionThrown() {

        final ArrayList<Expression> list = new ArrayList<>();

        assertThrows(
            NullPointerException.class,
            () -> new DisjunctiveExpression(null, list)
        );
    }

    @Test
    void givenNullPredicateList_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new DisjunctiveExpression(name, null)
        );
    }

    @Test
    void givenNullName_whenConstructWithNameOnly_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new DisjunctiveExpression(null)
        );
    }

    @Test
    void givenNullChildren_whenWithDefaultName_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> DisjunctiveExpression.withDefaultName(null)
        );
    }

    @Test
    void whenWithDefaultName_thenCorrectNameThrown() {

        final List<Expression> children = new ArrayList<>();

        final Expression expression = DisjunctiveExpression.withDefaultName(children);

        assertEquals(DisjunctiveExpression.DEFAULT_NAME, expression.name());
    }

    @Test
    void givenValidName_whenName_thenNameIsCorrect() {
        assertEquals(name, expression.name());
    }

    @Test
    void givenNoSubPredicate_whenEvaluate_thenEvaluationIsCorrect() {

        Evaluation evaluation = expression.evaluate();

        assertEquals(Result.TRUE, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertTrue(evaluation.children().isEmpty());
    }

    @Test
    void givenOnePassingExpressionAppended_whenEvaluate_thenEvaluationIsCorrect() {

        UnaryExpression<Object> subPredicate = Expressions.unaryExpression(Result.TRUE);

        expression.append(subPredicate);

        Evaluation evaluation = expression.evaluate();

        assertEquals(Result.TRUE, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertEquals(1, evaluation.children().size());
    }

    @Test
    void givenOneFailingExpressionAppended_whenEvaluate_thenEvaluationIsCorrect() {

        UnaryExpression<Object> subPredicate = Expressions.unaryExpression(Result.FALSE);

        expression.append(subPredicate);

        Evaluation evaluation = expression.evaluate();

        assertEquals(Result.FALSE, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertEquals(1, evaluation.children().size());
    }

    @Test
    void givenOneSkippedExpressionAppended_whenEvaluate_thenEvaluationIsCorrect() {

        UnaryExpression<Object> subPredicate = Expressions.unaryExpression(Result.SKIPPED);

        expression.append(subPredicate);

        Evaluation evaluation = expression.evaluate();

        assertEquals(Result.SKIPPED, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertEquals(1, evaluation.children().size());
    }

    @Test
    void givenOneFailingPredicatePrependedBeforePassingPredicate_whenEvaluate_thenEvaluationIsCorrect() {

        UnaryExpression<Object> passingSubPredicate = Expressions.unaryExpression(Result.TRUE);
        UnaryExpression<Object> failingSubPredicate = Expressions.unaryExpression(Result.FALSE);

        expression.append(passingSubPredicate);
        expression.prepend(failingSubPredicate);

        Evaluation evaluation = expression.evaluate();

        assertEquals(Result.TRUE, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertEquals(2, evaluation.children().size());
        assertIsNotSkipped(failingSubPredicate);
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
    void givenOnePassingPredicatePrependedBeforeFailingPredicate_whenEvaluate_thenEvaluationIsCorrect() {

        UnaryExpression<Object> passingSubPredicate = Expressions.unaryExpression(Result.TRUE);
        UnaryExpression<Object> failingSubPredicate = Expressions.unaryExpression(Result.FALSE);

        expression.append(failingSubPredicate);
        expression.prepend(passingSubPredicate);

        Evaluation evaluation = expression.evaluate();

        assertEquals(Result.TRUE, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertEquals(2, evaluation.children().size());
        assertIsSkipped(failingSubPredicate);
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

        final String subPredicateName = "expression 1";
        UnaryExpression<Object> subPredicate = Expressions.unaryExpression(subPredicateName, Result.TRUE);

        expression.append(subPredicate);

        Evaluation skippedEvaluation = expression.skip();

        assertEquals(expression.name(), skippedEvaluation.name());
        assertEquals(Result.SKIPPED, skippedEvaluation.result());
        assertEquals(SimpleRationale.skipped(), skippedEvaluation.rationale());
        assertEquals(1, skippedEvaluation.children().size());
        ExpressionAssertions.assertHasChildWithName(skippedEvaluation, subPredicateName);
    }

    @Test
    void givenTwoChildren_whenSkip_thenEvaluationIsCorrect() {

        final String subPredicateName1 = "expression 1";
        final String subPredicateName2 = "expression 2";
        UnaryExpression<Object> subPredicate1 = Expressions.unaryExpression(subPredicateName1, Result.FALSE);
        UnaryExpression<Object> subPredicate2 = Expressions.unaryExpression(subPredicateName2, Result.FALSE);

        expression.append(subPredicate1);
        expression.append(subPredicate2);

        Evaluation skippedEvaluation = expression.skip();

        assertEquals(expression.name(), skippedEvaluation.name());
        assertEquals(Result.SKIPPED, skippedEvaluation.result());
        assertEquals(SimpleRationale.skipped(), skippedEvaluation.rationale());
        assertEquals(2, skippedEvaluation.children().size());
        ExpressionAssertions.assertHasChildWithName(skippedEvaluation, subPredicateName1);
        ExpressionAssertions.assertHasChildWithName(skippedEvaluation, subPredicateName2);
    }
}
