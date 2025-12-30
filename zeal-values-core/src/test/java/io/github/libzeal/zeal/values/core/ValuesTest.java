package io.github.libzeal.zeal.values.core;

import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.unary.UnaryExpression;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ValuesTest {

    @Test
    void givenNullValue_whenValueGeneralObject_thenExpressionReturned() {

        final UnaryExpression<?> expression = Values.value((Object) null);

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueGeneralObject_thenExpressionReturned() {

        final UnaryExpression<?> expression = Values.value(new Object());

        assertNotNull(expression);
    }

    @Test
    void givenNullValue_whenValueString_thenExpressionReturned() {

        final UnaryExpression<?> expression = Values.value((String) null);

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueString_thenExpressionReturned() {

        final UnaryExpression<?> expression = Values.value("foo");

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueEnum_thenExpressionReturned() {

        final UnaryExpression<?> expression = Values.value(Result.TRUE);

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueBoxedInteger_thenExpressionReturned() {

        final UnaryExpression<?> expression = Values.value(Integer.valueOf(1));

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueBoxedLong_thenExpressionReturned() {

        final UnaryExpression<?> expression = Values.value(Long.valueOf(1));

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueBoxedDouble_thenExpressionReturned() {

        final UnaryExpression<?> expression = Values.value(Double.valueOf(1));

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueBoxedFloat_thenExpressionReturned() {

        final UnaryExpression<?> expression = Values.value(Float.valueOf(1));

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueBoxedBoolean_thenExpressionReturned() {

        final UnaryExpression<?> expression = Values.value(Boolean.TRUE);

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueBoxedByte_thenExpressionReturned() {

        final UnaryExpression<?> expression = Values.value(Byte.valueOf((byte) 0x01));

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueBoxedCharacter_thenExpressionReturned() {

        final UnaryExpression<?> expression = Values.value(Character.valueOf('c'));

        assertNotNull(expression);
    }
}
