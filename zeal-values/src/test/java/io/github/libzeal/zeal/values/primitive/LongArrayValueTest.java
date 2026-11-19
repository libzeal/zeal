package io.github.libzeal.zeal.values.primitive;

import io.github.libzeal.zeal.values.sdk.BaseValueTest;
import org.junit.jupiter.api.Test;

class LongArrayValueTest extends BaseValueTest<long[], LongArrayValue> {

    // Arrays use identity for equals, hashCode, and toString, so the same instances must be returned each time
    private static final long[] VALUE_A = {1};
    private static final long[] VALUE_B = {2};

    @Override
    protected LongArrayValue expression(final long[] value) {
        return new LongArrayValue(value);
    }

    @Override
    protected long[] nonNullValueA() {
        return VALUE_A;
    }

    @Override
    protected long[] nonNullValueB() {
        return VALUE_B;
    }

    @Test
    void givenIncluded_whenIncludes_thenTrue() {
        newTestCase()
                .subject(new long[]{1, 2})
                .expression(value -> value.includes(2L))
                .result(true)
                .name("includes[2]")
                .expected("includes[2]")
                .actual("includes[2]")
                .hint("'2' found at index 1")
                .run();
    }

    @Test
    void givenMatchingSum_whenSumIs_thenTrue() {
        newTestCase()
                .subject(new long[]{1, 2, 3})
                .expression(value -> value.sumIs(6))
                .result(true)
                .name("sumIs[6]")
                .expected("sum = 6")
                .actual("sum = 6")
                .run();
    }

    @Test
    void givenSumOverflowsLong_whenSumIs_thenFalse() {
        newTestCase()
                .subject(new long[]{Long.MAX_VALUE, 1})
                .expression(value -> value.sumIs(Long.MIN_VALUE))
                .result(false)
                .name("sumIs[-9223372036854775808]")
                .expected("sum = -9223372036854775808")
                .actual("sum overflows a long")
                .hint("The sum of the elements is outside the range of a long")
                .run();
    }

    @Test
    void givenSumUnderflowsLong_whenSumIs_thenFalse() {
        newTestCase()
                .subject(new long[]{Long.MIN_VALUE, -1})
                .expression(value -> value.sumIs(Long.MAX_VALUE))
                .result(false)
                .name("sumIs[9223372036854775807]")
                .expected("sum = 9223372036854775807")
                .actual("sum overflows a long")
                .run();
    }

    @Test
    void givenElementsBeyondIntRange_whenMinIs_thenTrue() {
        newTestCase()
                .subject(new long[]{Long.MAX_VALUE, Long.MIN_VALUE})
                .expression(value -> value.minimumIs(Long.MIN_VALUE))
                .result(true)
                .name("minIs[-9223372036854775808]")
                .expected("min = -9223372036854775808")
                .actual("min = -9223372036854775808")
                .run();
    }

    @Test
    void givenElementsBeyondIntRange_whenMaxIs_thenTrue() {
        newTestCase()
                .subject(new long[]{Long.MAX_VALUE, Long.MIN_VALUE})
                .expression(value -> value.maximumIs(Long.MAX_VALUE))
                .result(true)
                .name("maxIs[9223372036854775807]")
                .expected("max = 9223372036854775807")
                .actual("max = 9223372036854775807")
                .run();
    }

    @Test
    void givenAscending_whenIsAscending_thenTrue() {
        newTestCase()
                .subject(new long[]{Long.MIN_VALUE, 0, Long.MAX_VALUE})
                .expression(BaseNumericArrayValue::isAscending)
                .result(true)
                .name("isAscending")
                .expected("ascending")
                .actual("ascending")
                .run();
    }
}
