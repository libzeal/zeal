package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.assertion.error.PostconditionFailedException;
import io.github.libzeal.zeal.expression.lang.UnaryExpression;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.libzeal.zeal.assertion.AssertionTestExpectations.message;
import static io.github.libzeal.zeal.assertion.Expressions.expression;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

class AssuranceTest {

    private Assurance assurance;

    @BeforeEach
    void setUp() {
        assurance = Assurance.create();
    }

    @Test
    void givenNullExpression_whenEnsure_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> assurance.ensure(null)
        );
    }

    @Test
    void givenNullEvaluation_whenEnsure_thenExceptionThrown() {

        final UnaryExpression<Object> expression = expression();
        doReturn(null).when(expression).evaluate();

        assertThrows(
            NullPointerException.class,
            () -> assurance.ensure(expression)
        );
    }

    @Test
    void givenNullEvaluationResult_whenEnsure_thenExceptionThrown() {

        final UnaryExpression<Object> expression = expression(null, null);

        assertThrows(
            NullPointerException.class,
            () -> assurance.ensure(expression)
        );
    }

    @Test
    void givenPassingEvaluationAndNullSubject_whenEnsure_thenSubjectReturned() {

        final Result passed = Result.PASSED;
        final UnaryExpression<Object> expression = expression(passed, null);

        Object result = assurance.ensure(expression);

        assertNull(result);
    }

    @Test
    void givenSkippedEvaluationAndNullSubject_whenEnsure_thenSubjectReturned() {

        final Result result = Result.SKIPPED;
        final UnaryExpression<Object> expression = expression(result, null);

        Object returnedSubject = assurance.ensure(expression);

        assertNull(returnedSubject);
    }

    @Test
    void givenSkippedEvaluationAndValidSubject_whenEnsure_thenSubjectReturned() {

        final Result result = Result.SKIPPED;
        final Object subject = new Object();
        final UnaryExpression<Object> expression = expression(result, subject);

        Object returnedSubject = assurance.ensure(expression);

        assertEquals(subject, returnedSubject);
    }

    @Test
    void givenFailedEvaluationAndNullSubject_whenEnsure_thenExceptionThrown() {

        final Result result = Result.FAILED;
        final UnaryExpression<Object> expression = expression(result, null);

        RuntimeException exception = assertThrows(
            PostconditionFailedException.class,
            () -> assurance.ensure(expression)
        );

        assertEquals(message(expression, Assurance.DEFAULT_MESSAGE), exception.getMessage());
    }

    @Test
    void givenFailedEvaluationAndValidSubject_whenEnsure_thenExceptionThrown() {

        final Result result = Result.FAILED;
        final UnaryExpression<Object> expression = expression(result, new Object());

        RuntimeException exception = assertThrows(
            PostconditionFailedException.class,
            () -> assurance.ensure(expression)
        );

        assertEquals(message(expression, Assurance.DEFAULT_MESSAGE), exception.getMessage());
    }

    @Test
    void givenNullExpression_whenEnsureWithMessage_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> assurance.ensure(null, "foo")
        );
    }

    @Test
    void givenNullEvaluation_whenEnsureWithMessage_thenExceptionThrown() {

        final UnaryExpression<Object> expression = expression();
        doReturn(null).when(expression).evaluate();

        assertThrows(
            NullPointerException.class,
            () -> assurance.ensure(expression, "foo")
        );
    }

    @Test
    void givenNullEvaluationResult_whenEnsureWithMessage_thenExceptionThrown() {

        final UnaryExpression<Object> expression = expression(null, null);

        assertThrows(
            NullPointerException.class,
            () -> assurance.ensure(expression, "foo")
        );
    }

    @Test
    void givenPassingEvaluationAndNullSubject_whenEnsureWithMessage_thenSubjectReturned() {

        final Result passed = Result.PASSED;
        final UnaryExpression<Object> expression = expression(passed, null);

        Object result = assurance.ensure(expression, "foo");

        assertNull(result);
    }

    @Test
    void givenSkippedEvaluationAndNullSubject_whenEnsureWithMessage_thenSubjectReturned() {

        final Result result = Result.SKIPPED;
        final UnaryExpression<Object> expression = expression(result, null);

        Object returnedSubject = assurance.ensure(expression, "foo");

        assertNull(returnedSubject);
    }

    @Test
    void givenSkippedEvaluationAndValidSubject_whenEnsureWithMessage_thenSubjectReturned() {

        final Result result = Result.SKIPPED;
        final Object subject = new Object();
        final UnaryExpression<Object> expression = expression(result, subject);

        Object returnedSubject = assurance.ensure(expression, "foo");

        assertEquals(subject, returnedSubject);
    }

    @Test
    void givenFailedEvaluationAndNullSubject_whenEnsureWithMessage_thenExceptionThrown() {

        final String message = "foo";
        final Result result = Result.FAILED;
        final UnaryExpression<Object> expression = expression(result, null);

        RuntimeException exception = assertThrows(
            PostconditionFailedException.class,
            () -> assurance.ensure(expression, message)
        );

        assertEquals(message(expression, message), exception.getMessage());
    }

    @Test
    void givenFailedEvaluationAndValidSubject_whenEnsureWithMessage_thenExceptionThrown() {

        final String message = "foo";
        final Result result = Result.FAILED;
        final UnaryExpression<Object> expression = expression(result, new Object());

        RuntimeException exception = assertThrows(
            PostconditionFailedException.class,
            () -> assurance.ensure(expression, message)
        );

        assertEquals(message(expression, message), exception.getMessage());
    }

    @Test
    void givenFailedEvaluationAndNullSubjectAndNullMessage_whenEnsureWithMessage_thenExceptionThrown() {

        final Result result = Result.FAILED;
        final UnaryExpression<Object> expression = expression(result, null);

        RuntimeException exception = assertThrows(
            PostconditionFailedException.class,
            () -> assurance.ensure(expression, null)
        );

        assertEquals(message(expression, ""), exception.getMessage());
    }

    @Test
    void givenFailedEvaluationAndValidSubjectAndNullMessage_whenEnsureWithMessage_thenExceptionThrown() {

        final Result result = Result.FAILED;
        final UnaryExpression<Object> expression = expression(result, new Object());

        RuntimeException exception = assertThrows(
            PostconditionFailedException.class,
            () -> assurance.ensure(expression, null)
        );

        assertEquals(message(expression, ""), exception.getMessage());
    }
}
