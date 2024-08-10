package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.assertion.error.PreconditionFailedException;
import io.github.libzeal.zeal.expression.lang.UnaryExpression;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.libzeal.zeal.assertion.AssertionTestExpectations.message;
import static io.github.libzeal.zeal.assertion.Expressions.expression;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

class RequirementTest {

    private Requirement requirement;

    @BeforeEach
    void setUp() {
        requirement = Requirement.create();
    }

    @Test
    void givenNullExpression_whenRequire_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> requirement.require(null)
        );
    }

    @Test
    void givenNullEvaluation_whenRequire_thenExceptionThrown() {

        final UnaryExpression<Object> expression = expression();
        doReturn(null).when(expression).evaluate();

        assertThrows(
            NullPointerException.class,
            () -> requirement.require(expression)
        );
    }

    @Test
    void givenNullEvaluationResult_whenRequire_thenExceptionThrown() {

        final UnaryExpression<Object> expression = expression(null, null);

        assertThrows(
            NullPointerException.class,
            () -> requirement.require(expression)
        );
    }

    @Test
    void givenPassingEvaluationAndNullSubject_whenRequire_thenSubjectReturned() {

        final Result passed = Result.PASSED;
        final UnaryExpression<Object> expression = expression(passed, null);

        Object result = requirement.require(expression);

        assertNull(result);
    }

    @Test
    void givenSkippedEvaluationAndNullSubject_whenRequire_thenSubjectReturned() {

        final Result result = Result.SKIPPED;
        final UnaryExpression<Object> expression = expression(result, null);

        Object returnedSubject = requirement.require(expression);

        assertNull(returnedSubject);
    }

    @Test
    void givenSkippedEvaluationAndValidSubject_whenRequire_thenSubjectReturned() {

        final Result result = Result.SKIPPED;
        final Object subject = new Object();
        final UnaryExpression<Object> expression = expression(result, subject);

        Object returnedSubject = requirement.require(expression);

        assertEquals(subject, returnedSubject);
    }

    @Test
    void givenFailedEvaluationAndNullSubject_whenRequire_thenExceptionThrown() {

        final Result result = Result.FAILED;
        final UnaryExpression<Object> expression = expression(result, null);

        RuntimeException exception = assertThrows(
            NullPointerException.class,
            () -> requirement.require(expression)
        );

        assertEquals(message(expression, Requirement.DEFAULT_MESSAGE), exception.getMessage());
    }

    @Test
    void givenFailedEvaluationAndValidSubject_whenRequire_thenExceptionThrown() {

        final Result result = Result.FAILED;
        final UnaryExpression<Object> expression = expression(result, new Object());

        RuntimeException exception = assertThrows(
            PreconditionFailedException.class,
            () -> requirement.require(expression)
        );

        assertEquals(message(expression, Requirement.DEFAULT_MESSAGE), exception.getMessage());
    }

    @Test
    void givenNullExpression_whenRequireWithMessage_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> requirement.require(null, "foo")
        );
    }

    @Test
    void givenNullEvaluation_whenRequireWithMessage_thenExceptionThrown() {

        final UnaryExpression<Object> expression = expression();
        doReturn(null).when(expression).evaluate();

        assertThrows(
            NullPointerException.class,
            () -> requirement.require(expression, "foo")
        );
    }

    @Test
    void givenNullEvaluationResult_whenRequireWithMessage_thenExceptionThrown() {

        final UnaryExpression<Object> expression = expression(null, null);

        assertThrows(
            NullPointerException.class,
            () -> requirement.require(expression, "foo")
        );
    }

    @Test
    void givenPassingEvaluationAndNullSubject_whenRequireWithMessage_thenSubjectReturned() {

        final Result passed = Result.PASSED;
        final UnaryExpression<Object> expression = expression(passed, null);

        Object result = requirement.require(expression, "foo");

        assertNull(result);
    }

    @Test
    void givenSkippedEvaluationAndNullSubject_whenRequireWithMessage_thenSubjectReturned() {

        final Result result = Result.SKIPPED;
        final UnaryExpression<Object> expression = expression(result, null);

        Object returnedSubject = requirement.require(expression, "foo");

        assertNull(returnedSubject);
    }

    @Test
    void givenSkippedEvaluationAndValidSubject_whenRequireWithMessage_thenSubjectReturned() {

        final Result result = Result.SKIPPED;
        final Object subject = new Object();
        final UnaryExpression<Object> expression = expression(result, subject);

        Object returnedSubject = requirement.require(expression, "foo");

        assertEquals(subject, returnedSubject);
    }

    @Test
    void givenFailedEvaluationAndNullSubject_whenRequireWithMessage_thenExceptionThrown() {

        final String message = "foo";
        final Result result = Result.FAILED;
        final UnaryExpression<Object> expression = expression(result, null);

        RuntimeException exception = assertThrows(
            NullPointerException.class,
            () -> requirement.require(expression, message)
        );

        assertEquals(message(expression, message), exception.getMessage());
    }

    @Test
    void givenFailedEvaluationAndValidSubject_whenRequireWithMessage_thenExceptionThrown() {

        final String message = "foo";
        final Result result = Result.FAILED;
        final UnaryExpression<Object> expression = expression(result, new Object());

        RuntimeException exception = assertThrows(
            PreconditionFailedException.class,
            () -> requirement.require(expression, message)
        );

        assertEquals(message(expression, message), exception.getMessage());
    }

    @Test
    void givenFailedEvaluationAndNullSubjectAndNullMessage_whenRequireWithMessage_thenExceptionThrown() {

        final Result result = Result.FAILED;
        final UnaryExpression<Object> expression = expression(result, null);

        RuntimeException exception = assertThrows(
            NullPointerException.class,
            () -> requirement.require(expression, null)
        );

        assertEquals(message(expression, ""), exception.getMessage());
    }

    @Test
    void givenFailedEvaluationAndValidSubjectAndNullMessage_whenRequireWithMessage_thenExceptionThrown() {

        final Result result = Result.FAILED;
        final UnaryExpression<Object> expression = expression(result, new Object());

        RuntimeException exception = assertThrows(
            PreconditionFailedException.class,
            () -> requirement.require(expression, null)
        );

        assertEquals(message(expression, ""), exception.getMessage());
    }
}
