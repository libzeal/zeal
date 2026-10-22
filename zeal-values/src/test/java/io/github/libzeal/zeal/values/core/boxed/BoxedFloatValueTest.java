package io.github.libzeal.zeal.values.core.boxed;

import io.github.libzeal.zeal.logic.format.Formatter;
import org.junit.jupiter.api.Test;

import static io.github.libzeal.zeal.logic.evaluation.Result.FALSE;
import static io.github.libzeal.zeal.logic.evaluation.Result.TRUE;

class BoxedFloatValueTest extends BoxedNumberValueTest<Float, BoxedFloatValue> {

    @Override
    protected BoxedFloatValue expression(final Float value) {
        return new BoxedFloatValue(value);
    }

    @Override
    protected Float exampleNegativeValue() {
        return -1.0f;
    }

    @Override
    protected Float exampleZeroValue() {
        return 0.0f;
    }

    @Override
    protected Float examplePositiveValue() {
        return 1.0f;
    }

    @Override
    protected Float exampleMaximumValue() {
        return Float.MAX_VALUE;
    }

    @Override
    protected Float exampleMinimumValue() {
        return Float.MIN_VALUE;
    }

    // ---- isEqualTo (with tolerance) ----

    @Test
    void givenBelowRange_whenIsEqualToWithTolerance_thenFalse() {
        newTestCase()
            .subject(-0.1f)
            .expression(value -> value.isEqualTo(1.0f, 1.0f))
            .result(FALSE)
            .name("isEqualTo[1.0 +/- 1.0]")
            .expected("1.0 +/- 1.0")
            .actual(Formatter.stringify(-0.1f))
            .run();
    }

    @Test
    void givenLowerBound_whenIsEqualToWithTolerance_thenTrue() {
        newTestCase()
            .subject(0.0f)
            .expression(value -> value.isEqualTo(1.0f, 1.0f))
            .result(TRUE)
            .name("isEqualTo[1.0 +/- 1.0]")
            .expected("1.0 +/- 1.0")
            .actual(Formatter.stringify(0.0f))
            .run();
    }

    @Test
    void givenNearLowerBound_whenIsEqualToWithTolerance_thenTrue() {
        newTestCase()
            .subject(0.1f)
            .expression(value -> value.isEqualTo(1.0f, 1.0f))
            .result(TRUE)
            .name("isEqualTo[1.0 +/- 1.0]")
            .expected("1.0 +/- 1.0")
            .actual(Formatter.stringify(0.1f))
            .run();
    }

    @Test
    void givenNearUpperBound_whenIsEqualToWithTolerance_thenTrue() {
        newTestCase()
            .subject(1.9f)
            .expression(value -> value.isEqualTo(1.0f, 1.0f))
            .result(TRUE)
            .name("isEqualTo[1.0 +/- 1.0]")
            .expected("1.0 +/- 1.0")
            .actual(Formatter.stringify(1.9f))
            .run();
    }

    @Test
    void givenUpperBound_whenIsEqualToWithTolerance_thenTrue() {
        newTestCase()
            .subject(2.0f)
            .expression(value -> value.isEqualTo(1.0f, 1.0f))
            .result(TRUE)
            .name("isEqualTo[1.0 +/- 1.0]")
            .expected("1.0 +/- 1.0")
            .actual(Formatter.stringify(2.0f))
            .run();
    }

    @Test
    void givenAboveRange_whenIsEqualToWithTolerance_thenFalse() {
        newTestCase()
            .subject(2.1f)
            .expression(value -> value.isEqualTo(1.0f, 1.0f))
            .result(FALSE)
            .name("isEqualTo[1.0 +/- 1.0]")
            .expected("1.0 +/- 1.0")
            .actual(Formatter.stringify(2.1f))
            .run();
    }

    // ---- isFinite ----

    @Test
    void givenPositiveInfinity_whenIsFinite_thenFalse() {
        newTestCase()
            .subject(Float.POSITIVE_INFINITY)
            .expression(BoxedFloatValue::isFinite)
            .result(FALSE)
            .name("isFinite")
            .expected("finite")
            .actual(Formatter.stringify(Float.POSITIVE_INFINITY))
            .run();
    }

    @Test
    void givenNegativeInfinity_whenIsFinite_thenFalse() {
        newTestCase()
            .subject(Float.NEGATIVE_INFINITY)
            .expression(BoxedFloatValue::isFinite)
            .result(FALSE)
            .name("isFinite")
            .expected("finite")
            .actual(Formatter.stringify(Float.NEGATIVE_INFINITY))
            .run();
    }

    @Test
    void givenZero_whenIsFinite_thenTrue() {
        newTestCase()
            .subject(exampleZeroValue())
            .expression(BoxedFloatValue::isFinite)
            .result(TRUE)
            .name("isFinite")
            .expected("finite")
            .actual(Formatter.stringify(exampleZeroValue()))
            .run();
    }

    // ---- isInfinite ----

    @Test
    void givenPositiveInfinity_whenIsInfinite_thenTrue() {
        newTestCase()
            .subject(Float.POSITIVE_INFINITY)
            .expression(BoxedFloatValue::isInfinite)
            .result(TRUE)
            .name("isInfinite")
            .expected("infinite")
            .actual(Formatter.stringify(Float.POSITIVE_INFINITY))
            .run();
    }

    @Test
    void givenNegativeInfinity_whenIsInfinite_thenTrue() {
        newTestCase()
            .subject(Float.NEGATIVE_INFINITY)
            .expression(BoxedFloatValue::isInfinite)
            .result(TRUE)
            .name("isInfinite")
            .expected("infinite")
            .actual(Formatter.stringify(Float.NEGATIVE_INFINITY))
            .run();
    }

    @Test
    void givenZero_whenIsInfinite_thenFalse() {
        newTestCase()
            .subject(exampleZeroValue())
            .expression(BoxedFloatValue::isInfinite)
            .result(FALSE)
            .name("isInfinite")
            .expected("infinite")
            .actual(Formatter.stringify(exampleZeroValue()))
            .run();
    }

    // ---- isNaN ----

    @Test
    void givenNaN_whenIsNaN_thenTrue() {
        newTestCase()
            .subject(Float.NaN)
            .expression(BoxedFloatValue::isNaN)
            .result(TRUE)
            .name("isNaN")
            .expected("NaN")
            .actual(Formatter.stringify(Float.NaN))
            .run();
    }

    @Test
    void givenZero_whenIsNaN_thenFalse() {
        newTestCase()
            .subject(exampleZeroValue())
            .expression(BoxedFloatValue::isNaN)
            .result(FALSE)
            .name("isNaN")
            .expected("NaN")
            .actual(Formatter.stringify(exampleZeroValue()))
            .run();
    }
}
