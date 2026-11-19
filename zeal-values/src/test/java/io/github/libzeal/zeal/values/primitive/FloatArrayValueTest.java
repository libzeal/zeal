package io.github.libzeal.zeal.values.primitive;

import io.github.libzeal.zeal.values.sdk.BaseValueTest;
import org.junit.jupiter.api.Test;

class FloatArrayValueTest extends BaseValueTest<float[], FloatArrayValue> {

    // Arrays use identity for equals, hashCode, and toString, so the same instances must be returned each time
    private static final float[] VALUE_A = {1};
    private static final float[] VALUE_B = {2};

    @Override
    protected FloatArrayValue expression(final float[] value) {
        return new FloatArrayValue(value);
    }

    @Override
    protected float[] nonNullValueA() {
        return VALUE_A;
    }

    @Override
    protected float[] nonNullValueB() {
        return VALUE_B;
    }

    @Test
    void givenIncluded_whenIncludes_thenTrue() {
        newTestCase()
                .subject(new float[]{1, 2})
                .expression(value -> value.includes(2.0f))
                .result(true)
                .name("includes[2.0]")
                .expected("includes[2.0]")
                .actual("includes[2.0]")
                .hint("'2.0' found at index 1")
                .run();
    }

    @Test
    void givenExactSum_whenSumIs_thenTrue() {
        newTestCase()
                .subject(new float[]{0.5f, 0.25f})
                .expression(value -> value.sumIs(0.75, 0.0))
                .result(true)
                .name("sumIs[expected=0.75, tolerance=0.0]")
                .expected("sum within 0.0 of 0.75")
                .actual("sum = 0.75")
                .run();
    }

    // 0.1f is not exactly 0.1, so this only passes because the expected value is compared as a float
    @Test
    void givenInexactFloatElement_whenMinIs_thenTrue() {
        newTestCase()
                .subject(new float[]{0.5f, 0.1f})
                .expression(value -> value.minimumIs(0.1f))
                .result(true)
                .name("minIs[0.1]")
                .expected("min = 0.1")
                .actual("min = 0.1")
                .run();
    }

    @Test
    void givenMatchingMax_whenMaxIs_thenTrue() {
        newTestCase()
                .subject(new float[]{0.5f, 0.1f})
                .expression(value -> value.maximumIs(0.5f))
                .result(true)
                .name("maxIs[0.5]")
                .expected("max = 0.5")
                .actual("max = 0.5")
                .run();
    }

    @Test
    void givenNaNLast_whenIsAscending_thenTrue() {
        newTestCase()
                .subject(new float[]{1, 2, Float.NaN})
                .expression(BaseNumericArrayValue::isAscending)
                .result(true)
                .name("isAscending")
                .expected("ascending")
                .actual("ascending")
                .run();
    }

    @Test
    void givenOutOfOrder_whenIsDescending_thenFalse() {
        newTestCase()
                .subject(new float[]{1, 2})
                .expression(BaseNumericArrayValue::isDescending)
                .result(false)
                .name("isDescending")
                .expected("descending")
                .actual("not descending")
                .hint("Element 1.0 at index 0 is less than element 2.0 at index 1")
                .run();
    }
}
