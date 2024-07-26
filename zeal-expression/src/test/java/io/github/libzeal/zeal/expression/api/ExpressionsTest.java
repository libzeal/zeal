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
}
