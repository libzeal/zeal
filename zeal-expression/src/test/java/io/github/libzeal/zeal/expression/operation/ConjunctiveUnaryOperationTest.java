package io.github.libzeal.zeal.expression.operation;

import io.github.libzeal.zeal.expression.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.evaluation.Result;
import io.github.libzeal.zeal.expression.operation.unary.ConjunctiveUnaryOperation;
import io.github.libzeal.zeal.expression.operation.unary.UnaryOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static io.github.libzeal.zeal.expression.operation.TestOperations.unaryOperation;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConjunctiveUnaryOperationTest {

    private String name;
    private ConjunctiveUnaryOperation<Object> operation;

    @BeforeEach
    void setUp() {

        name = "foo";

        operation = new ConjunctiveUnaryOperation<>(name, new ArrayList<>());
    }

    @Test
    void givenNullName_whenConstruct_thenExceptionThrown() {

        final ArrayList<UnaryOperation<Object>> list = new ArrayList<>();

        assertThrows(
            NullPointerException.class,
            () -> new ConjunctiveUnaryOperation<>(null, list)
        );
    }

    @Test
    void givenNullOperationList_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new ConjunctiveUnaryOperation<>(name, null)
        );
    }

    @Test
    void givenNullName_whenConstructWithNameOnly_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new ConjunctiveUnaryOperation<>(null)
        );
    }

    @Test
    void givenValidName_whenName_thenNameIsCorrect() {
        assertEquals(name, operation.name());
    }

    @Test
    void givenNoChildren_whenChildren_thenChildrenIsCorrect() {
        assertTrue(operation.children().isEmpty());
    }

    @Test
    void givenOneChild_whenChildren_thenChildrenIsCorrect() {

        UnaryOperation<Object> subOperation = unaryOperation(Result.PASSED);

        operation.append(subOperation);

        assertEquals(1, operation.children().size());
        assertTrue(operation.children().contains(subOperation));
    }

    @Test
    void givenTwoChildren_whenChildren_thenChildrenIsCorrect() {

        UnaryOperation<Object> subOperation1 = unaryOperation(Result.PASSED);
        UnaryOperation<Object> subOperation2 = unaryOperation(Result.PASSED);

        operation.append(subOperation1);
        operation.append(subOperation2);

        assertEquals(2, operation.children().size());
        assertTrue(operation.children().contains(subOperation1));
        assertTrue(operation.children().contains(subOperation2));
    }

    @Test
    void givenNoSubOperation_whenEvaluate_thenEvaluationIsCorrect() {

        Object subject = new Object();

        Evaluation evaluation = operation.evaluate(subject);

        assertEquals(Result.PASSED, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertTrue(evaluation.children().isEmpty());
    }

    @Test
    void givenOnePassingCriterionAppended_whenEvaluate_thenEvaluationIsCorrect() {

        Object subject = new Object();
        UnaryOperation<Object> subOperation = unaryOperation(Result.PASSED);

        operation.append(subOperation);

        Evaluation evaluation = operation.evaluate(subject);

        assertEquals(Result.PASSED, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertEquals(1, evaluation.children().size());
    }

    @Test
    void givenOneFailingCriterionAppended_whenEvaluate_thenEvaluationIsCorrect() {

        Object subject = new Object();
        UnaryOperation<Object> subOperation = unaryOperation(Result.FAILED);

        operation.append(subOperation);

        Evaluation evaluation = operation.evaluate(subject);

        assertEquals(Result.FAILED, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertEquals(1, evaluation.children().size());
    }

    @Test
    void givenOneSkippedCriterionAppended_whenEvaluate_thenEvaluationIsCorrect() {

        Object subject = new Object();
        UnaryOperation<Object> subOperation = unaryOperation(Result.SKIPPED);

        operation.append(subOperation);

        Evaluation evaluation = operation.evaluate(subject);

        assertEquals(Result.SKIPPED, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertEquals(1, evaluation.children().size());
    }

    @Test
    void givenOneFailingOperationPrependedBeforePassingOperation_whenEvaluate_thenEvaluationIsCorrect() {

        Object subject = new Object();
        UnaryOperation<Object> passingSubOperation = unaryOperation(Result.PASSED);
        UnaryOperation<Object> failingSubOperation = unaryOperation(Result.FAILED);

        operation.append(passingSubOperation);
        operation.prepend(failingSubOperation);

        Evaluation evaluation = operation.evaluate(subject);

        assertEquals(Result.FAILED, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertEquals(2, evaluation.children().size());
        assertIsSkipped(passingSubOperation, subject);
    }

    private static void assertIsSkipped(UnaryOperation<Object> operation, Object subject) {
        verify(operation, times(1)).evaluateAsSkipped(subject);
        verify(operation, never()).evaluate(subject);
    }

    private static void assertIsNotSkipped(UnaryOperation<Object> operation, Object subject) {
        verify(operation, never()).evaluateAsSkipped(subject);
        verify(operation, times(1)).evaluate(subject);
    }

    @Test
    void givenOnePassingOperationPrependedBeforeFailingOperation_whenEvaluate_thenEvaluationIsCorrect() {

        Object subject = new Object();
        UnaryOperation<Object> passingSubOperation = unaryOperation(Result.PASSED);
        UnaryOperation<Object> failingSubOperation = unaryOperation(Result.FAILED);

        operation.append(failingSubOperation);
        operation.prepend(passingSubOperation);

        Evaluation evaluation = operation.evaluate(subject);

        assertEquals(Result.FAILED, evaluation.result());
        assertEquals(name, evaluation.name());
        assertNotNull(evaluation.rationale());
        assertEquals(2, evaluation.children().size());
        assertIsNotSkipped(failingSubOperation, subject);
    }
}
