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

class DisjunctiveExpressionTest {

    private String name;
    private DisjunctiveExpression predicate;

    @BeforeEach
    void setUp() {

        name = "foo";

        predicate = new DisjunctiveExpression(name, new ArrayList<>());
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
    void givenValidName_whenName_thenNameIsCorrect() {
        assertEquals(name, predicate.name());
    }

    @Test
    void givenNoSubPredicate_whenEvaluate_thenEvaluationIsCorrect() {

        Evaluation evaluation = predicate.evaluate();

        assertEquals(Result.PASSED, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertTrue(evaluation.children().isEmpty());
    }

    @Test
    void givenOnePassingCriterionAppended_whenEvaluate_thenEvaluationIsCorrect() {

        UnaryExpression<Object> subPredicate = Expressions.unaryExpression(Result.PASSED);

        predicate.append(subPredicate);

        Evaluation evaluation = predicate.evaluate();

        assertEquals(Result.PASSED, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertEquals(1, evaluation.children().size());
    }

    @Test
    void givenOneFailingCriterionAppended_whenEvaluate_thenEvaluationIsCorrect() {

        UnaryExpression<Object> subPredicate = Expressions.unaryExpression(Result.FAILED);

        predicate.append(subPredicate);

        Evaluation evaluation = predicate.evaluate();

        assertEquals(Result.FAILED, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertEquals(1, evaluation.children().size());
    }

    @Test
    void givenOneSkippedCriterionAppended_whenEvaluate_thenEvaluationIsCorrect() {

        UnaryExpression<Object> subPredicate = Expressions.unaryExpression(Result.SKIPPED);

        predicate.append(subPredicate);

        Evaluation evaluation = predicate.evaluate();

        assertEquals(Result.SKIPPED, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertEquals(1, evaluation.children().size());
    }

    @Test
    void givenOneFailingPredicatePrependedBeforePassingPredicate_whenEvaluate_thenEvaluationIsCorrect() {

        UnaryExpression<Object> passingSubPredicate = Expressions.unaryExpression(Result.PASSED);
        UnaryExpression<Object> failingSubPredicate = Expressions.unaryExpression(Result.FAILED);

        predicate.append(passingSubPredicate);
        predicate.prepend(failingSubPredicate);

        Evaluation evaluation = predicate.evaluate();

        assertEquals(Result.PASSED, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertEquals(2, evaluation.children().size());
        assertIsNotSkipped(failingSubPredicate);
    }

    private static void assertIsSkipped(UnaryExpression<Object> predicate) {
        verify(predicate, times(1)).skip();
        verify(predicate, never()).evaluate();
    }

    private static void assertIsNotSkipped(UnaryExpression<Object> predicate) {
        verify(predicate, never()).skip();
        verify(predicate, times(1)).evaluate();
    }

    @Test
    void givenOnePassingPredicatePrependedBeforeFailingPredicate_whenEvaluate_thenEvaluationIsCorrect() {

        UnaryExpression<Object> passingSubPredicate = Expressions.unaryExpression(Result.PASSED);
        UnaryExpression<Object> failingSubPredicate = Expressions.unaryExpression(Result.FAILED);

        predicate.append(failingSubPredicate);
        predicate.prepend(passingSubPredicate);

        Evaluation evaluation = predicate.evaluate();

        assertEquals(Result.PASSED, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertEquals(2, evaluation.children().size());
        assertIsSkipped(failingSubPredicate);
    }

    @Test
    void givenNoChildren_whenSkip_thenEvaluationIsCorrect() {

        Evaluation skippedEvaluation = predicate.skip();

        assertEquals(predicate.name(), skippedEvaluation.name());
        assertEquals(Result.SKIPPED, skippedEvaluation.result());
        assertEquals(SimpleRationale.skipped(), skippedEvaluation.rationale());
        assertTrue(skippedEvaluation.children().isEmpty());
    }

    @Test
    void givenOneChild_whenSkip_thenEvaluationIsCorrect() {

        final String subPredicateName = "predicate 1";
        UnaryExpression<Object> subPredicate = Expressions.unaryExpression(subPredicateName, Result.PASSED);

        predicate.append(subPredicate);

        Evaluation skippedEvaluation = predicate.skip();

        assertEquals(predicate.name(), skippedEvaluation.name());
        assertEquals(Result.SKIPPED, skippedEvaluation.result());
        assertEquals(SimpleRationale.skipped(), skippedEvaluation.rationale());
        assertEquals(1, skippedEvaluation.children().size());
        ExpressionAssertions.assertHasChildWithName(skippedEvaluation, subPredicateName);
    }

    @Test
    void givenTwoChildren_whenSkip_thenEvaluationIsCorrect() {

        final String subPredicateName1 = "predicate 1";
        final String subPredicateName2 = "predicate 2";
        UnaryExpression<Object> subPredicate1 = Expressions.unaryExpression(subPredicateName1, Result.FAILED);
        UnaryExpression<Object> subPredicate2 = Expressions.unaryExpression(subPredicateName2, Result.FAILED);

        predicate.append(subPredicate1);
        predicate.append(subPredicate2);

        Evaluation skippedEvaluation = predicate.skip();

        assertEquals(predicate.name(), skippedEvaluation.name());
        assertEquals(Result.SKIPPED, skippedEvaluation.result());
        assertEquals(SimpleRationale.skipped(), skippedEvaluation.rationale());
        assertEquals(2, skippedEvaluation.children().size());
        ExpressionAssertions.assertHasChildWithName(skippedEvaluation, subPredicateName1);
        ExpressionAssertions.assertHasChildWithName(skippedEvaluation, subPredicateName2);
    }
}
