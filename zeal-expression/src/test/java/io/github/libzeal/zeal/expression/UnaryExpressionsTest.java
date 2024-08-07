package io.github.libzeal.zeal.expression;

import io.github.libzeal.zeal.expression.UnaryExpressions;
import io.github.libzeal.zeal.expression.lang.Expression;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UnaryExpressionsTest {

    @Test
    void givenNullValue_whenThatGeneralObject_thenExpressionReturned() {

        Expression expression = UnaryExpressions.value((Object) null);

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenThatGeneralObject_thenExpressionReturned() {

        Expression expression = UnaryExpressions.value(new Object());

        assertNotNull(expression);
    }

    @Test
    void givenNullValue_whenThatString_thenExpressionReturned() {

        Expression expression = UnaryExpressions.value((String) null);

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenThatString_thenExpressionReturned() {

        Expression expression = UnaryExpressions.value("foo");

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenThatEnum_thenExpressionReturned() {

        Expression expression = UnaryExpressions.value(Result.PASSED);

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenThatBoxedInteger_thenExpressionReturned() {

        Expression expression = UnaryExpressions.value(Integer.valueOf(1));

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenThatBoxedLong_thenExpressionReturned() {

        Expression expression = UnaryExpressions.value(Long.valueOf(1));

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenThatBoxedDouble_thenExpressionReturned() {

        Expression expression = UnaryExpressions.value(Double.valueOf(1));

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenThatBoxedFloat_thenExpressionReturned() {

        Expression expression = UnaryExpressions.value(Float.valueOf(1));

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenThatBoxedBoolean_thenExpressionReturned() {

        Expression expression = UnaryExpressions.value(Boolean.TRUE);

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenThatBoxedByte_thenExpressionReturned() {

        Expression expression = UnaryExpressions.value((byte) 1);

        assertNotNull(expression);
    }
}
