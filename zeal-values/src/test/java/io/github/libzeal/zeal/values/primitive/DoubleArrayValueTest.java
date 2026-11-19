package io.github.libzeal.zeal.values.primitive;

import io.github.libzeal.zeal.values.sdk.BaseValueTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DoubleArrayValueTest extends BaseValueTest<double[], DoubleArrayValue> {

    // Arrays use identity for equals, hashCode, and toString, so the same instances must be returned each time
    private static final double[] VALUE_A = {1};
    private static final double[] VALUE_B = {2};

    @Override
    protected DoubleArrayValue expression(final double[] value) {
        return new DoubleArrayValue(value);
    }

    @Override
    protected double[] nonNullValueA() {
        return VALUE_A;
    }

    @Override
    protected double[] nonNullValueB() {
        return VALUE_B;
    }

    @Test
    void givenIncluded_whenIncludes_thenTrue() {
        newTestCase()
                .subject(new double[]{1, 2})
                .expression(value -> value.includes(2.0))
                .result(true)
                .name("includes[2.0]")
                .expected("includes[2.0]")
                .actual("includes[2.0]")
                .hint("'2.0' found at index 1")
                .run();
    }

    // ---- sumIs ----

    @Test
    void givenExactSum_whenSumIsWithZeroTolerance_thenTrue() {
        newTestCase()
                .subject(new double[]{1.5, 2.5})
                .expression(value -> value.sumIs(4.0, 0.0))
                .result(true)
                .name("sumIs[expected=4.0, tolerance=0.0]")
                .expected("sum within 0.0 of 4.0")
                .actual("sum = 4.0")
                .hint("The sum of the elements is within 0.0 of 4.0")
                .run();
    }

    @Test
    void givenInexactSumWithinTolerance_whenSumIs_thenTrue() {
        newTestCase()
                .subject(new double[]{0.1, 0.2})
                .expression(value -> value.sumIs(0.3, 1e-9))
                .result(true)
                .name("sumIs[expected=0.3, tolerance=1.0E-9]")
                .expected("sum within 1.0E-9 of 0.3")
                .actual("sum = 0.30000000000000004")
                .run();
    }

    @Test
    void givenInexactSumOutsideTolerance_whenSumIs_thenFalse() {
        newTestCase()
                .subject(new double[]{0.1, 0.2})
                .expression(value -> value.sumIs(0.3, 0.0))
                .result(false)
                .name("sumIs[expected=0.3, tolerance=0.0]")
                .expected("sum within 0.0 of 0.3")
                .actual("sum = 0.30000000000000004")
                .hint("The sum of the elements differs from 0.3 by more than 0.0")
                .run();
    }

    @Test
    void givenNaNElement_whenSumIs_thenFalse() {
        newTestCase()
                .subject(new double[]{1, Double.NaN})
                .expression(value -> value.sumIs(1.0, 100.0))
                .result(false)
                .name("sumIs[expected=1.0, tolerance=100.0]")
                .expected("sum within 100.0 of 1.0")
                .actual("sum = NaN")
                .run();
    }

    @Test
    void givenEmpty_whenSumIsZero_thenTrue() {
        newTestCase()
                .subject(new double[]{})
                .expression(value -> value.sumIs(0.0, 0.0))
                .result(true)
                .name("sumIs[expected=0.0, tolerance=0.0]")
                .expected("sum within 0.0 of 0.0")
                .actual("sum = 0.0")
                .run();
    }

    @Test
    void givenNullSubject_whenSumIs_thenFalse() {
        newTestCase()
                .subject(null)
                .expression(value -> value.sumIs(0.0, 0.0))
                .result(false)
                .name("sumIs[expected=0.0, tolerance=0.0]")
                .expected("sum within 0.0 of 0.0")
                .actual("(null)")
                .run();
    }

    @Test
    void givenNegativeTolerance_whenSumIs_thenExceptionThrown() {

        final DoubleArrayValue value = new DoubleArrayValue(new double[]{1});

        assertThrows(IllegalArgumentException.class, () -> value.sumIs(1.0, -0.1));
    }

    @Test
    void givenNaNTolerance_whenSumIs_thenExceptionThrown() {

        final DoubleArrayValue value = new DoubleArrayValue(new double[]{1});

        assertThrows(IllegalArgumentException.class, () -> value.sumIs(1.0, Double.NaN));
    }

    // ---- minIs and maxIs ----

    @Test
    void givenMatchingMin_whenMinIs_thenTrue() {
        newTestCase()
                .subject(new double[]{2.5, -1.5})
                .expression(value -> value.minimumIs(-1.5))
                .result(true)
                .name("minIs[-1.5]")
                .expected("min = -1.5")
                .actual("min = -1.5")
                .hint("The minimum element is -1.5")
                .run();
    }

    @Test
    void givenNegativeZero_whenMinIsPositiveZero_thenFalse() {
        newTestCase()
                .subject(new double[]{0.0, -0.0})
                .expression(value -> value.minimumIs(0.0))
                .result(false)
                .name("minIs[0.0]")
                .expected("min = 0.0")
                .actual("min = -0.0")
                .hint("The minimum element should be 0.0")
                .run();
    }

    @Test
    void givenNaNElement_whenMinIs_thenNaNIgnored() {
        newTestCase()
                .subject(new double[]{Double.NaN, 1})
                .expression(value -> value.minimumIs(1.0))
                .result(true)
                .name("minIs[1.0]")
                .expected("min = 1.0")
                .actual("min = 1.0")
                .run();
    }

    @Test
    void givenNaNElement_whenMaxIsNaN_thenTrue() {
        newTestCase()
                .subject(new double[]{1, Double.NaN})
                .expression(value -> value.maximumIs(Double.NaN))
                .result(true)
                .name("maxIs[NaN]")
                .expected("max = NaN")
                .actual("max = NaN")
                .run();
    }

    @Test
    void givenEmpty_whenMaxIs_thenFalse() {
        newTestCase()
                .subject(new double[]{})
                .expression(value -> value.maximumIs(0.0))
                .result(false)
                .name("maxIs[0.0]")
                .expected("max = 0.0")
                .actual("empty")
                .hint("An empty array has no maximum element")
                .run();
    }

    // ---- isAscending and isDescending ----

    @Test
    void givenNaNLast_whenIsAscending_thenTrue() {
        newTestCase()
                .subject(new double[]{-0.0, 0.0, 1, Double.NaN})
                .expression(BaseNumericArrayValue::isAscending)
                .result(true)
                .name("isAscending")
                .expected("ascending")
                .actual("ascending")
                .run();
    }

    @Test
    void givenNaNFirst_whenIsAscending_thenFalse() {
        newTestCase()
                .subject(new double[]{Double.NaN, 1})
                .expression(BaseNumericArrayValue::isAscending)
                .result(false)
                .name("isAscending")
                .expected("ascending")
                .actual("not ascending")
                .hint("Element NaN at index 0 is greater than element 1.0 at index 1")
                .run();
    }

    @Test
    void givenDescending_whenIsDescending_thenTrue() {
        newTestCase()
                .subject(new double[]{2.5, 1.5, -1})
                .expression(BaseNumericArrayValue::isDescending)
                .result(true)
                .name("isDescending")
                .expected("descending")
                .actual("descending")
                .run();
    }
}
