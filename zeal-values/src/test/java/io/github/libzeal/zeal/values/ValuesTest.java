package io.github.libzeal.zeal.values;

import io.github.libzeal.zeal.logic.unary.UnaryExpression;
import io.github.libzeal.zeal.values.collection.MapValue;
import io.github.libzeal.zeal.values.core.boxed.BoxedShortValue;
import io.github.libzeal.zeal.values.primitive.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
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

        final UnaryExpression<?> expression = Values.value(true);

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueBoxedInteger_thenExpressionReturned() {

        final UnaryExpression<?> expression = Values.value(1);

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

        final UnaryExpression<?> expression = Values.value((byte) 0x01);

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueBoxedShort_thenBoxedShortValueReturned() {

        final UnaryExpression<?> expression = Values.value((short) 1);

        assertInstanceOf(BoxedShortValue.class, expression);
    }

    @Test
    void givenNullValue_whenValueBoxedShort_thenBoxedShortValueReturned() {

        final UnaryExpression<?> expression = Values.value((Short) null);

        assertInstanceOf(BoxedShortValue.class, expression);
    }

    @Test
    void givenValidValue_whenValueBoxedCharacter_thenExpressionReturned() {

        final UnaryExpression<?> expression = Values.value('c');

        assertNotNull(expression);
    }

    @Test
    void givenValidValue_whenValueMap_thenMapValueReturned() {

        final UnaryExpression<?> expression = Values.value(Map.of("a", 1));

        assertInstanceOf(MapValue.class, expression);
    }

    @Test
    void givenNullValue_whenValueMap_thenMapValueReturned() {

        final UnaryExpression<?> expression = Values.value((Map<String, Integer>) null);

        assertInstanceOf(MapValue.class, expression);
    }

    @Test
    void givenValidValue_whenValueBooleanArray_thenBooleanArrayValueReturned() {

        final UnaryExpression<?> expression = Values.value(new boolean[]{true});

        assertInstanceOf(BooleanArrayValue.class, expression);
    }

    @Test
    void givenNullValue_whenValueBooleanArray_thenBooleanArrayValueReturned() {

        final UnaryExpression<?> expression = Values.value((boolean[]) null);

        assertInstanceOf(BooleanArrayValue.class, expression);
    }

    @Test
    void givenValidValue_whenValueCharArray_thenCharArrayValueReturned() {

        final UnaryExpression<?> expression = Values.value(new char[]{'a'});

        assertInstanceOf(CharArrayValue.class, expression);
    }

    @Test
    void givenNullValue_whenValueCharArray_thenCharArrayValueReturned() {

        final UnaryExpression<?> expression = Values.value((char[]) null);

        assertInstanceOf(CharArrayValue.class, expression);
    }

    @Test
    void givenValidValue_whenValueByteArray_thenByteArrayValueReturned() {

        final UnaryExpression<?> expression = Values.value(new byte[]{1});

        assertInstanceOf(ByteArrayValue.class, expression);
    }

    @Test
    void givenNullValue_whenValueByteArray_thenByteArrayValueReturned() {

        final UnaryExpression<?> expression = Values.value((byte[]) null);

        assertInstanceOf(ByteArrayValue.class, expression);
    }

    @Test
    void givenValidValue_whenValueShortArray_thenShortArrayValueReturned() {

        final UnaryExpression<?> expression = Values.value(new short[]{1});

        assertInstanceOf(ShortArrayValue.class, expression);
    }

    @Test
    void givenNullValue_whenValueShortArray_thenShortArrayValueReturned() {

        final UnaryExpression<?> expression = Values.value((short[]) null);

        assertInstanceOf(ShortArrayValue.class, expression);
    }

    @Test
    void givenValidValue_whenValueIntArray_thenIntArrayValueReturned() {

        final UnaryExpression<?> expression = Values.value(new int[]{1});

        assertInstanceOf(IntArrayValue.class, expression);
    }

    @Test
    void givenNullValue_whenValueIntArray_thenIntArrayValueReturned() {

        final UnaryExpression<?> expression = Values.value((int[]) null);

        assertInstanceOf(IntArrayValue.class, expression);
    }

    @Test
    void givenValidValue_whenValueLongArray_thenLongArrayValueReturned() {

        final UnaryExpression<?> expression = Values.value(new long[]{1});

        assertInstanceOf(LongArrayValue.class, expression);
    }

    @Test
    void givenNullValue_whenValueLongArray_thenLongArrayValueReturned() {

        final UnaryExpression<?> expression = Values.value((long[]) null);

        assertInstanceOf(LongArrayValue.class, expression);
    }

    @Test
    void givenValidValue_whenValueFloatArray_thenFloatArrayValueReturned() {

        final UnaryExpression<?> expression = Values.value(new float[]{1});

        assertInstanceOf(FloatArrayValue.class, expression);
    }

    @Test
    void givenNullValue_whenValueFloatArray_thenFloatArrayValueReturned() {

        final UnaryExpression<?> expression = Values.value((float[]) null);

        assertInstanceOf(FloatArrayValue.class, expression);
    }

    @Test
    void givenValidValue_whenValueDoubleArray_thenDoubleArrayValueReturned() {

        final UnaryExpression<?> expression = Values.value(new double[]{1});

        assertInstanceOf(DoubleArrayValue.class, expression);
    }

    @Test
    void givenNullValue_whenValueDoubleArray_thenDoubleArrayValueReturned() {

        final UnaryExpression<?> expression = Values.value((double[]) null);

        assertInstanceOf(DoubleArrayValue.class, expression);
    }
}
