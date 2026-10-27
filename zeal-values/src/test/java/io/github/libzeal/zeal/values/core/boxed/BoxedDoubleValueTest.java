package io.github.libzeal.zeal.values.core.boxed;

import io.github.libzeal.zeal.logic.format.Formatter;
import org.junit.jupiter.api.Test;


class BoxedDoubleValueTest extends BoxedNumberValueTest<Double, BoxedDoubleValue> {

    @Override
    protected BoxedDoubleValue expression(final Double value) {
        return new BoxedDoubleValue(value);
    }

    @Override
    protected Double exampleNegativeValue() {
        return -1.0;
    }

    @Override
    protected Double exampleZeroValue() {
        return 0.0;
    }

    @Override
    protected Double examplePositiveValue() {
        return 1.0;
    }

    @Override
    protected Double exampleMaximumValue() {
        return Double.MAX_VALUE;
    }

    @Override
    protected Double exampleMinimumValue() {
        return Double.MIN_VALUE;
    }

    // ---- isEqualTo (with tolerance) ----

    @Test
    void givenBelowRange_whenIsEqualToWithTolerance_thenFalse() {
        newTestCase()
            .subject(-0.1)
            .expression(value -> value.isEqualTo(1.0, 1.0))
            .result(false)
            .name("isEqualTo[1.0 +/- 1.0]")
            .expected("1.0 +/- 1.0")
            .actual(Formatter.stringify(-0.1))
            .run();
    }

    @Test
    void givenLowerBound_whenIsEqualToWithTolerance_thenTrue() {
        newTestCase()
            .subject(0.0)
            .expression(value -> value.isEqualTo(1.0, 1.0))
            .result(true)
            .name("isEqualTo[1.0 +/- 1.0]")
            .expected("1.0 +/- 1.0")
            .actual(Formatter.stringify(0.0))
            .run();
    }

    @Test
    void givenNearLowerBound_whenIsEqualToWithTolerance_thenTrue() {
        newTestCase()
            .subject(0.1)
            .expression(value -> value.isEqualTo(1.0, 1.0))
            .result(true)
            .name("isEqualTo[1.0 +/- 1.0]")
            .expected("1.0 +/- 1.0")
            .actual(Formatter.stringify(0.1))
            .run();
    }

    @Test
    void givenNearUpperBound_whenIsEqualToWithTolerance_thenTrue() {
        newTestCase()
            .subject(1.9)
            .expression(value -> value.isEqualTo(1.0, 1.0))
            .result(true)
            .name("isEqualTo[1.0 +/- 1.0]")
            .expected("1.0 +/- 1.0")
            .actual(Formatter.stringify(1.9))
            .run();
    }

    @Test
    void givenUpperBound_whenIsEqualToWithTolerance_thenTrue() {
        newTestCase()
            .subject(2.0)
            .expression(value -> value.isEqualTo(1.0, 1.0))
            .result(true)
            .name("isEqualTo[1.0 +/- 1.0]")
            .expected("1.0 +/- 1.0")
            .actual(Formatter.stringify(2.0))
            .run();
    }

    @Test
    void givenAboveRange_whenIsEqualToWithTolerance_thenFalse() {
        newTestCase()
            .subject(2.1)
            .expression(value -> value.isEqualTo(1.0, 1.0))
            .result(false)
            .name("isEqualTo[1.0 +/- 1.0]")
            .expected("1.0 +/- 1.0")
            .actual(Formatter.stringify(2.1))
            .run();
    }

    // ---- isFinite ----

    @Test
    void givenPositiveInfinity_whenIsFinite_thenFalse() {
        newTestCase()
            .subject(Double.POSITIVE_INFINITY)
            .expression(BoxedDoubleValue::isFinite)
            .result(false)
            .name("isFinite")
            .expected("finite")
            .actual(Formatter.stringify(Double.POSITIVE_INFINITY))
            .run();
    }

    @Test
    void givenNegativeInfinity_whenIsFinite_thenFalse() {
        newTestCase()
            .subject(Double.NEGATIVE_INFINITY)
            .expression(BoxedDoubleValue::isFinite)
            .result(false)
            .name("isFinite")
            .expected("finite")
            .actual(Formatter.stringify(Double.NEGATIVE_INFINITY))
            .run();
    }

    @Test
    void givenZero_whenIsFinite_thenTrue() {
        newTestCase()
            .subject(exampleZeroValue())
            .expression(BoxedDoubleValue::isFinite)
            .result(true)
            .name("isFinite")
            .expected("finite")
            .actual(Formatter.stringify(exampleZeroValue()))
            .run();
    }

    // ---- isInfinite ----

    @Test
    void givenPositiveInfinity_whenIsInfinite_thenTrue() {
        newTestCase()
            .subject(Double.POSITIVE_INFINITY)
            .expression(BoxedDoubleValue::isInfinite)
            .result(true)
            .name("isInfinite")
            .expected("infinite")
            .actual(Formatter.stringify(Double.POSITIVE_INFINITY))
            .run();
    }

    @Test
    void givenNegativeInfinity_whenIsInfinite_thenTrue() {
        newTestCase()
            .subject(Double.NEGATIVE_INFINITY)
            .expression(BoxedDoubleValue::isInfinite)
            .result(true)
            .name("isInfinite")
            .expected("infinite")
            .actual(Formatter.stringify(Double.NEGATIVE_INFINITY))
            .run();
    }

    @Test
    void givenZero_whenIsInfinite_thenFalse() {
        newTestCase()
            .subject(exampleZeroValue())
            .expression(BoxedDoubleValue::isInfinite)
            .result(false)
            .name("isInfinite")
            .expected("infinite")
            .actual(Formatter.stringify(exampleZeroValue()))
            .run();
    }

    // ---- isNaN ----

    @Test
    void givenNaN_whenIsNaN_thenTrue() {
        newTestCase()
            .subject(Double.NaN)
            .expression(BoxedDoubleValue::isNaN)
            .result(true)
            .name("isNaN")
            .expected("NaN")
            .actual(Formatter.stringify(Double.NaN))
            .run();
    }

    @Test
    void givenZero_whenIsNaN_thenFalse() {
        newTestCase()
            .subject(exampleZeroValue())
            .expression(BoxedDoubleValue::isNaN)
            .result(false)
            .name("isNaN")
            .expected("NaN")
            .actual(Formatter.stringify(exampleZeroValue()))
            .run();
    }
}
