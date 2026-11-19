package io.github.libzeal.zeal.values.primitive;

import io.github.libzeal.zeal.values.sdk.BaseValueTest;
import org.junit.jupiter.api.Test;

class ShortArrayValueTest extends BaseValueTest<short[], ShortArrayValue> {

    // Arrays use identity for equals, hashCode, and toString, so the same instances must be returned each time
    private static final short[] VALUE_A = {1};
    private static final short[] VALUE_B = {2};

    @Override
    protected ShortArrayValue expression(final short[] value) {
        return new ShortArrayValue(value);
    }

    @Override
    protected short[] nonNullValueA() {
        return VALUE_A;
    }

    @Override
    protected short[] nonNullValueB() {
        return VALUE_B;
    }

    @Test
    void givenIncluded_whenIncludes_thenTrue() {
        newTestCase()
                .subject(new short[]{1, 2})
                .expression(value -> value.includes((short) 2))
                .result(true)
                .name("includes[2]")
                .expected("includes[2]")
                .actual("includes[2]")
                .hint("'2' found at index 1")
                .run();
    }

    @Test
    void givenSumBeyondShortRange_whenSumIs_thenSumComputedAsLong() {
        newTestCase()
                .subject(new short[]{Short.MAX_VALUE, Short.MAX_VALUE})
                .expression(value -> value.sumIs(65534))
                .result(true)
                .name("sumIs[65534]")
                .expected("sum = 65534")
                .actual("sum = 65534")
                .run();
    }

    @Test
    void givenMatchingMin_whenMinIs_thenTrue() {
        newTestCase()
                .subject(new short[]{1, -3, 2})
                .expression(value -> value.minimumIs(-3))
                .result(true)
                .name("minIs[-3]")
                .expected("min = -3")
                .actual("min = -3")
                .run();
    }

    @Test
    void givenMatchingMax_whenMaxIs_thenTrue() {
        newTestCase()
                .subject(new short[]{1, -3, 2})
                .expression(value -> value.maximumIs(2))
                .result(true)
                .name("maxIs[2]")
                .expected("max = 2")
                .actual("max = 2")
                .run();
    }

    @Test
    void givenOutOfOrder_whenIsAscending_thenFalse() {
        newTestCase()
                .subject(new short[]{2, 1})
                .expression(BaseNumericArrayValue::isAscending)
                .result(false)
                .name("isAscending")
                .expected("ascending")
                .actual("not ascending")
                .hint("Element 2 at index 0 is greater than element 1 at index 1")
                .run();
    }
}
