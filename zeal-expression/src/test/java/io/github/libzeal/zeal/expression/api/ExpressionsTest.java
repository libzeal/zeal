package io.github.libzeal.zeal.expression.api;

import io.github.libzeal.zeal.expression.Expression;
import io.github.libzeal.zeal.expression.evaluation.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ExpressionsTest {

    @Test
    void givenNullValue_whenThatGeneralObject_thenExpressionReturned() {

        Expression expression = Expressions.that((Object) null);

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenThatGeneralObject_thenExpressionReturned() {

        Expression expression = Expressions.that(new Object());

        assertNotNull(expression);
    }

    @Test
    void givenNullValue_whenThatString_thenExpressionReturned() {

        Expression expression = Expressions.that((String) null);

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenThatString_thenExpressionReturned() {

        Expression expression = Expressions.that("foo");

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenThatEnum_thenExpressionReturned() {

        Expression expression = Expressions.that(Result.PASSED);

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenThatBoxedInteger_thenExpressionReturned() {

        Expression expression = Expressions.that(Integer.valueOf(1));

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenThatBoxedLong_thenExpressionReturned() {

        Expression expression = Expressions.that(Long.valueOf(1));

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenThatBoxedDouble_thenExpressionReturned() {

        Expression expression = Expressions.that(Double.valueOf(1));

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenThatBoxedFloat_thenExpressionReturned() {

        Expression expression = Expressions.that(Float.valueOf(1));

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenThatBoxedBoolean_thenExpressionReturned() {

        Expression expression = Expressions.that(Boolean.TRUE);

        assertNotNull(expression);
    }
}
