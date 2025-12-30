package io.github.libzeal.zeal.types.core;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.unary.UnaryExpression;
import io.github.libzeal.zeal.types.core.unary.GeneralObjectUnaryExpression;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CoreTypesTest {

    @Test
    void givenNullValue_whenValueGeneralObject_thenExpressionReturned() {

        final UnaryExpression<?> expression = CoreTypes.value((Object) null);

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueGeneralObject_thenExpressionReturned() {

        final UnaryExpression<?> expression = CoreTypes.value(new Object());

        assertNotNull(expression);
    }

    @Test
    void givenNullValue_whenValueString_thenExpressionReturned() {

        final UnaryExpression<?> expression = CoreTypes.value((String) null);

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueString_thenExpressionReturned() {

        final UnaryExpression<?> expression = CoreTypes.value("foo");

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueEnum_thenExpressionReturned() {

        final UnaryExpression<?> expression = CoreTypes.value(Result.TRUE);

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueBoxedInteger_thenExpressionReturned() {

        final UnaryExpression<?> expression = CoreTypes.value(Integer.valueOf(1));

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueBoxedLong_thenExpressionReturned() {

        final UnaryExpression<?> expression = CoreTypes.value(Long.valueOf(1));

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueBoxedDouble_thenExpressionReturned() {

        final UnaryExpression<?> expression = CoreTypes.value(Double.valueOf(1));

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueBoxedFloat_thenExpressionReturned() {

        final UnaryExpression<?> expression = CoreTypes.value(Float.valueOf(1));

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueBoxedBoolean_thenExpressionReturned() {

        final UnaryExpression<?> expression = CoreTypes.value(Boolean.TRUE);

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueBoxedByte_thenExpressionReturned() {

        final UnaryExpression<?> expression = CoreTypes.value(Byte.valueOf((byte) 0x01));

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueBoxedCharacter_thenExpressionReturned() {

        final UnaryExpression<?> expression = CoreTypes.value(Character.valueOf('c'));

        assertNotNull(expression);
    }
}
