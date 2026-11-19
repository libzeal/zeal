package io.github.libzeal.zeal.values.primitive;

import io.github.libzeal.zeal.values.sdk.BaseValueTest;
import org.junit.jupiter.api.Test;

class ByteArrayValueTest extends BaseValueTest<byte[], ByteArrayValue> {

    // Arrays use identity for equals, hashCode, and toString, so the same instances must be returned each time
    private static final byte[] VALUE_A = {1};
    private static final byte[] VALUE_B = {2};

    @Override
    protected ByteArrayValue expression(final byte[] value) {
        return new ByteArrayValue(value);
    }

    @Override
    protected byte[] nonNullValueA() {
        return VALUE_A;
    }

    @Override
    protected byte[] nonNullValueB() {
        return VALUE_B;
    }

    @Test
    void givenIncluded_whenIncludes_thenTrue() {
        newTestCase()
                .subject(new byte[]{1, 2})
                .expression(value -> value.includes((byte) 2))
                .result(true)
                .name("includes[2]")
                .expected("includes[2]")
                .actual("includes[2]")
                .hint("'2' found at index 1")
                .run();
    }

    @Test
    void givenSumBeyondByteRange_whenSumIs_thenSumComputedAsLong() {
        newTestCase()
                .subject(new byte[]{Byte.MAX_VALUE, Byte.MAX_VALUE})
                .expression(value -> value.sumIs(254))
                .result(true)
                .name("sumIs[254]")
                .expected("sum = 254")
                .actual("sum = 254")
                .run();
    }

    @Test
    void givenMatchingMin_whenMinIs_thenTrue() {
        newTestCase()
                .subject(new byte[]{1, -3, 2})
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
                .subject(new byte[]{1, -3, 2})
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
                .subject(new byte[]{2, 1})
                .expression(BaseNumericArrayValue::isAscending)
                .result(false)
                .name("isAscending")
                .expected("ascending")
                .actual("not ascending")
                .hint("Element 2 at index 0 is greater than element 1 at index 1")
                .run();
    }
}
