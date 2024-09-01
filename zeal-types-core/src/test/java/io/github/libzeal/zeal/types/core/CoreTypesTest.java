package io.github.libzeal.zeal.types.core;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CoreTypesTest {

    @Test
    void givenNullValue_whenValueGeneralObject_thenExpressionReturned() {

        Expression expression = CoreTypes.value((Object) null);

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueGeneralObject_thenExpressionReturned() {

        Expression expression = CoreTypes.value(new Object());

        assertNotNull(expression);
    }

    @Test
    void givenNullValue_whenValueString_thenExpressionReturned() {

        Expression expression = CoreTypes.value((String) null);

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueString_thenExpressionReturned() {

        Expression expression = CoreTypes.value("foo");

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueEnum_thenExpressionReturned() {

        Expression expression = CoreTypes.value(Result.TRUE);

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueBoxedInteger_thenExpressionReturned() {

        Expression expression = CoreTypes.value(Integer.valueOf(1));

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueBoxedLong_thenExpressionReturned() {

        Expression expression = CoreTypes.value(Long.valueOf(1));

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueBoxedDouble_thenExpressionReturned() {

        Expression expression = CoreTypes.value(Double.valueOf(1));

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueBoxedFloat_thenExpressionReturned() {

        Expression expression = CoreTypes.value(Float.valueOf(1));

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueBoxedBoolean_thenExpressionReturned() {

        Expression expression = CoreTypes.value(Boolean.TRUE);

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueBoxedByte_thenExpressionReturned() {

        Expression expression = CoreTypes.value(Byte.valueOf((byte) 0x01));

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueBoxedCharacter_thenExpressionReturned() {

        Expression expression = CoreTypes.value(Character.valueOf('c'));

        assertNotNull(expression);
    }
}
