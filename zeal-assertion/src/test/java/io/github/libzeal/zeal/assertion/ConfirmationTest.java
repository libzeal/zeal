package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.assertion.error.AssertionFailedException;
import io.github.libzeal.zeal.assertion.error.PostconditionFailedException;
import io.github.libzeal.zeal.expression.lang.UnaryExpression;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.libzeal.zeal.assertion.AssertionTestExpectations.message;
import static io.github.libzeal.zeal.assertion.Expressions.expression;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

class ConfirmationTest {

    private Confirmation confirmation;

    @BeforeEach
    void setUp() {
        confirmation = Confirmation.create();
    }

    @Test
    void givenNullExpression_whenConfirm_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> confirmation.confirm(null)
        );
    }

    @Test
    void givenNullEvaluation_whenConfirm_thenExceptionThrown() {

        final UnaryExpression<Object> expression = expression();
        doReturn(null).when(expression).evaluate();

        assertThrows(
            NullPointerException.class,
            () -> confirmation.confirm(expression)
        );
    }

    @Test
    void givenNullEvaluationResult_whenConfirm_thenExceptionThrown() {

        final UnaryExpression<Object> expression = expression(null, null);

        assertThrows(
            NullPointerException.class,
            () -> confirmation.confirm(expression)
        );
    }

    @Test
    void givenPassingEvaluationAndNullSubject_whenConfirm_thenSubjectReturned() {

        final Result passed = Result.PASSED;
        final UnaryExpression<Object> expression = expression(passed, null);

        Object result = confirmation.confirm(expression);

        assertNull(result);
    }

    @Test
    void givenSkippedEvaluationAndNullSubject_whenConfirm_thenSubjectReturned() {

        final Result result = Result.SKIPPED;
        final UnaryExpression<Object> expression = expression(result, null);

        Object returnedSubject = confirmation.confirm(expression);

        assertNull(returnedSubject);
    }

    @Test
    void givenSkippedEvaluationAndValidSubject_whenConfirm_thenSubjectReturned() {

        final Result result = Result.SKIPPED;
        final Object subject = new Object();
        final UnaryExpression<Object> expression = expression(result, subject);

        Object returnedSubject = confirmation.confirm(expression);

        assertEquals(subject, returnedSubject);
    }

    @Test
    void givenFailedEvaluationAndNullSubject_whenConfirm_thenExceptionThrown() {

        final Result result = Result.FAILED;
        final UnaryExpression<Object> expression = expression(result, null);

        RuntimeException exception = assertThrows(
            AssertionFailedException.class,
            () -> confirmation.confirm(expression)
        );

        assertEquals(message(expression, Confirmation.DEFAULT_MESSAGE), exception.getMessage());
    }

    @Test
    void givenFailedEvaluationAndValidSubject_whenConfirm_thenExceptionThrown() {

        final Result result = Result.FAILED;
        final UnaryExpression<Object> expression = expression(result, new Object());

        RuntimeException exception = assertThrows(
            AssertionFailedException.class,
            () -> confirmation.confirm(expression)
        );

        assertEquals(message(expression, Confirmation.DEFAULT_MESSAGE), exception.getMessage());
    }

    @Test
    void givenNullExpression_whenConfirmWithMessage_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> confirmation.confirm(null, "foo")
        );
    }

    @Test
    void givenNullEvaluation_whenConfirmWithMessage_thenExceptionThrown() {

        final UnaryExpression<Object> expression = expression();
        doReturn(null).when(expression).evaluate();

        assertThrows(
            NullPointerException.class,
            () -> confirmation.confirm(expression, "foo")
        );
    }

    @Test
    void givenNullEvaluationResult_whenConfirmWithMessage_thenExceptionThrown() {

        final UnaryExpression<Object> expression = expression(null, null);

        assertThrows(
            NullPointerException.class,
            () -> confirmation.confirm(expression, "foo")
        );
    }

    @Test
    void givenPassingEvaluationAndNullSubject_whenConfirmWithMessage_thenSubjectReturned() {

        final Result passed = Result.PASSED;
        final UnaryExpression<Object> expression = expression(passed, null);

        Object result = confirmation.confirm(expression, "foo");

        assertNull(result);
    }

    @Test
    void givenSkippedEvaluationAndNullSubject_whenConfirmWithMessage_thenSubjectReturned() {

        final Result result = Result.SKIPPED;
        final UnaryExpression<Object> expression = expression(result, null);

        Object returnedSubject = confirmation.confirm(expression, "foo");

        assertNull(returnedSubject);
    }

    @Test
    void givenSkippedEvaluationAndValidSubject_whenConfirmWithMessage_thenSubjectReturned() {

        final Result result = Result.SKIPPED;
        final Object subject = new Object();
        final UnaryExpression<Object> expression = expression(result, subject);

        Object returnedSubject = confirmation.confirm(expression, "foo");

        assertEquals(subject, returnedSubject);
    }

    @Test
    void givenFailedEvaluationAndNullSubject_whenConfirmWithMessage_thenExceptionThrown() {

        final String message = "foo";
        final Result result = Result.FAILED;
        final UnaryExpression<Object> expression = expression(result, null);

        RuntimeException exception = assertThrows(
            AssertionFailedException.class,
            () -> confirmation.confirm(expression, message)
        );

        assertEquals(message(expression, message), exception.getMessage());
    }

    @Test
    void givenFailedEvaluationAndValidSubject_whenConfirmWithMessage_thenExceptionThrown() {

        final String message = "foo";
        final Result result = Result.FAILED;
        final UnaryExpression<Object> expression = expression(result, new Object());

        RuntimeException exception = assertThrows(
            AssertionFailedException.class,
            () -> confirmation.confirm(expression, message)
        );

        assertEquals(message(expression, message), exception.getMessage());
    }

    @Test
    void givenFailedEvaluationAndNullSubjectAndNullMessage_whenConfirmWithMessage_thenExceptionThrown() {

        final Result result = Result.FAILED;
        final UnaryExpression<Object> expression = expression(result, null);

        RuntimeException exception = assertThrows(
            AssertionFailedException.class,
            () -> confirmation.confirm(expression, null)
        );

        assertEquals(message(expression, ""), exception.getMessage());
    }

    @Test
    void givenFailedEvaluationAndValidSubjectAndNullMessage_whenConfirmWithMessage_thenExceptionThrown() {

        final Result result = Result.FAILED;
        final UnaryExpression<Object> expression = expression(result, new Object());

        RuntimeException exception = assertThrows(
            AssertionFailedException.class,
            () -> confirmation.confirm(expression, null)
        );

        assertEquals(message(expression, ""), exception.getMessage());
    }
}
