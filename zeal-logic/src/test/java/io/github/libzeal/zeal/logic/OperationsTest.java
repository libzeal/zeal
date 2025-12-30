package io.github.libzeal.zeal.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class OperationsTest {

    @Test
    void givenNullWrappedExpression_whenNot_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Operations.not((Expression) null)
        );
    }

    @Test
    void givenValidWrappedExpression_whenNot_thenCorrectExpressionReturned() {

        final Expression wrappedExpression = mock(Expression.class);
        final NotExpression expression = Operations.not(wrappedExpression);

        assertNotNull(expression);
        assertInstanceOf(NotExpression.class, expression);
    }

    @Test
    void givenNullExpression_whenAnd_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Operations.and((Expression[]) null)
        );
    }

    @Test
    void givenValidWrappedExpressions_whenAnd_thenCorrectExpressionReturned() {

        final Expression wrappedExpression = mock(Expression.class);
        final AndExpression expression = Operations.and(wrappedExpression);

        assertNotNull(expression);
        assertInstanceOf(AndExpression.class, expression);
    }

    @Test
    void givenNullExpression_whenOr_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Operations.or((Expression[]) null)
        );
    }

    @Test
    void givenValidWrappedExpressions_whenOr_thenCorrectExpressionReturned() {

        final Expression wrappedExpression = mock(Expression.class);
        final OrExpression expression = Operations.or(wrappedExpression);

        assertNotNull(expression);
        assertInstanceOf(OrExpression.class, expression);
    }

    @Test
    void givenNullExpression_whenNand_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Operations.nand((Expression[]) null)
        );
    }

    @Test
    void givenValidWrappedExpressions_whenNand_thenCorrectExpressionReturned() {

        final Expression wrappedExpression = mock(Expression.class);
        final Expression expression = Operations.nand(wrappedExpression);

        assertNotNull(expression);
        assertInstanceOf(NotExpression.class, expression);
    }

    @Test
    void givenNullExpression_whenNor_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Operations.nor((Expression[]) null)
        );
    }

    @Test
    void givenValidWrappedExpressions_whenNor_thenCorrectExpressionReturned() {

        final Expression wrappedExpression = mock(Expression.class);
        final Expression expression = Operations.nor(wrappedExpression);

        assertNotNull(expression);
        assertInstanceOf(NotExpression.class, expression);
    }
}
