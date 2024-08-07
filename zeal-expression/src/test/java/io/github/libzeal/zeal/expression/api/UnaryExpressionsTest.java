package io.github.libzeal.zeal.expression.api;

import io.github.libzeal.zeal.expression.UnaryExpressions;
import io.github.libzeal.zeal.expression.lang.Expression;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UnaryExpressionsTest {

    @Test
    void givenNullValue_whenThatGeneralObject_thenExpressionReturned() {

        Expression expression = UnaryExpressions.that((Object) null);

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenThatGeneralObject_thenExpressionReturned() {

        Expression expression = UnaryExpressions.that(new Object());

        assertNotNull(expression);
    }

    @Test
    void givenNullValue_whenThatString_thenExpressionReturned() {

        Expression expression = UnaryExpressions.that((String) null);

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenThatString_thenExpressionReturned() {

        Expression expression = UnaryExpressions.that("foo");

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenThatEnum_thenExpressionReturned() {

        Expression expression = UnaryExpressions.that(Result.PASSED);

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenThatBoxedInteger_thenExpressionReturned() {

        Expression expression = UnaryExpressions.that(Integer.valueOf(1));

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenThatBoxedLong_thenExpressionReturned() {

        Expression expression = UnaryExpressions.that(Long.valueOf(1));

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenThatBoxedDouble_thenExpressionReturned() {

        Expression expression = UnaryExpressions.that(Double.valueOf(1));

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenThatBoxedFloat_thenExpressionReturned() {

        Expression expression = UnaryExpressions.that(Float.valueOf(1));

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenThatBoxedBoolean_thenExpressionReturned() {

        Expression expression = UnaryExpressions.that(Boolean.TRUE);

        assertNotNull(expression);
    }
}
