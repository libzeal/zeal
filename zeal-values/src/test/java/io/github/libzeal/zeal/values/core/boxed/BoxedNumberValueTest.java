package io.github.libzeal.zeal.values.core.boxed;

import io.github.libzeal.zeal.logic.format.Formatter;
import io.github.libzeal.zeal.values.sdk.BaseValueTest;
import org.junit.jupiter.api.Test;


public abstract class BoxedNumberValueTest<T extends Number, E extends BoxedNumberValue<T, E>>
    extends BaseValueTest<T, E> {

    // ---- isEqualTo ----

    @Test
    void givenEqualNumber_whenIsEqualTo_thenTrue() {
        final T a = nonNullValueA();
        newTestCase()
            .subject(a)
            .expression(value -> value.isEqualTo(a))
            .result(true)
            .name("isEqualTo[" + Formatter.stringify(a) + "]")
            .expected(Formatter.stringify(a))
            .actual(Formatter.stringify(a))
            .run();
    }

    @Test
    void givenDifferentNumber_whenIsEqualTo_thenFalse() {
        final T a = nonNullValueA();
        final T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.isEqualTo(a))
            .result(false)
            .name("isEqualTo[" + Formatter.stringify(a) + "]")
            .expected(Formatter.stringify(a))
            .actual(Formatter.stringify(b))
            .run();
    }

    // ---- isNotEqualTo ----

    @Test
    void givenEqualNumber_whenIsNotEqualTo_thenFalse() {
        final T a = nonNullValueA();
        newTestCase()
            .subject(a)
            .expression(value -> value.isNotEqualTo(a))
            .result(false)
            .name("isNotEqualTo[" + Formatter.stringify(a) + "]")
            .expected("not[" + Formatter.stringify(a) + "]")
            .actual(Formatter.stringify(a))
            .run();
    }

    @Test
    void givenDifferentNumber_whenIsNotEqualTo_thenTrue() {
        final T a = nonNullValueA();
        final T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.isNotEqualTo(a))
            .result(true)
            .name("isNotEqualTo[" + Formatter.stringify(a) + "]")
            .expected("not[" + Formatter.stringify(a) + "]")
            .actual(Formatter.stringify(b))
            .run();
    }

    // ---- isLessThan ----

    @Test
    void givenPositive_whenIsLessThanZero_thenFalse() {
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();
        newTestCase()
            .subject(positive)
            .expression(value -> value.isLessThan(zero))
            .result(false)
            .name("isLessThan[" + Formatter.stringify(zero) + "]")
            .expected("< " + Formatter.stringify(zero))
            .actual(Formatter.stringify(positive))
            .run();
    }

    @Test
    void givenZero_whenIsLessThanZero_thenFalse() {
        final T zero = exampleZeroValue();
        newTestCase()
            .subject(zero)
            .expression(value -> value.isLessThan(zero))
            .result(false)
            .name("isLessThan[" + Formatter.stringify(zero) + "]")
            .expected("< " + Formatter.stringify(zero))
            .actual(Formatter.stringify(zero))
            .run();
    }

    @Test
    void givenNegative_whenIsLessThanZero_thenTrue() {
        final T zero = exampleZeroValue();
        final T negative = exampleNegativeValue();
        newTestCase()
            .subject(negative)
            .expression(value -> value.isLessThan(zero))
            .result(true)
            .name("isLessThan[" + Formatter.stringify(zero) + "]")
            .expected("< " + Formatter.stringify(zero))
            .actual(Formatter.stringify(negative))
            .run();
    }

    // ---- lt ----

    @Test
    void givenPositive_whenLtZero_thenFalse() {
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();
        newTestCase()
            .subject(positive)
            .expression(value -> value.lt(zero))
            .result(false)
            .name("isLessThan[" + Formatter.stringify(zero) + "]")
            .expected("< " + Formatter.stringify(zero))
            .actual(Formatter.stringify(positive))
            .run();
    }

    @Test
    void givenZero_whenLtZero_thenFalse() {
        final T zero = exampleZeroValue();
        newTestCase()
            .subject(zero)
            .expression(value -> value.lt(zero))
            .result(false)
            .name("isLessThan[" + Formatter.stringify(zero) + "]")
            .expected("< " + Formatter.stringify(zero))
            .actual(Formatter.stringify(zero))
            .run();
    }

    @Test
    void givenNegative_whenLtZero_thenTrue() {
        final T zero = exampleZeroValue();
        final T negative = exampleNegativeValue();
        newTestCase()
            .subject(negative)
            .expression(value -> value.lt(zero))
            .result(true)
            .name("isLessThan[" + Formatter.stringify(zero) + "]")
            .expected("< " + Formatter.stringify(zero))
            .actual(Formatter.stringify(negative))
            .run();
    }

    // ---- isGreaterThan ----

    @Test
    void givenPositive_whenIsGreaterThanZero_thenTrue() {
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();
        newTestCase()
            .subject(positive)
            .expression(value -> value.isGreaterThan(zero))
            .result(true)
            .name("isGreaterThan[" + Formatter.stringify(zero) + "]")
            .expected("> " + Formatter.stringify(zero))
            .actual(Formatter.stringify(positive))
            .run();
    }

    @Test
    void givenZero_whenIsGreaterThanZero_thenFalse() {
        final T zero = exampleZeroValue();
        newTestCase()
            .subject(zero)
            .expression(value -> value.isGreaterThan(zero))
            .result(false)
            .name("isGreaterThan[" + Formatter.stringify(zero) + "]")
            .expected("> " + Formatter.stringify(zero))
            .actual(Formatter.stringify(zero))
            .run();
    }

    @Test
    void givenNegative_whenIsGreaterThanZero_thenFalse() {
        final T zero = exampleZeroValue();
        final T negative = exampleNegativeValue();
        newTestCase()
            .subject(negative)
            .expression(value -> value.isGreaterThan(zero))
            .result(false)
            .name("isGreaterThan[" + Formatter.stringify(zero) + "]")
            .expected("> " + Formatter.stringify(zero))
            .actual(Formatter.stringify(negative))
            .run();
    }

    // ---- gt ----

    @Test
    void givenPositive_whenGtZero_thenTrue() {
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();
        newTestCase()
            .subject(positive)
            .expression(value -> value.gt(zero))
            .result(true)
            .name("isGreaterThan[" + Formatter.stringify(zero) + "]")
            .expected("> " + Formatter.stringify(zero))
            .actual(Formatter.stringify(positive))
            .run();
    }

    @Test
    void givenZero_whenGtZero_thenFalse() {
        final T zero = exampleZeroValue();
        newTestCase()
            .subject(zero)
            .expression(value -> value.gt(zero))
            .result(false)
            .name("isGreaterThan[" + Formatter.stringify(zero) + "]")
            .expected("> " + Formatter.stringify(zero))
            .actual(Formatter.stringify(zero))
            .run();
    }

    @Test
    void givenNegative_whenGtZero_thenFalse() {
        final T zero = exampleZeroValue();
        final T negative = exampleNegativeValue();
        newTestCase()
            .subject(negative)
            .expression(value -> value.gt(zero))
            .result(false)
            .name("isGreaterThan[" + Formatter.stringify(zero) + "]")
            .expected("> " + Formatter.stringify(zero))
            .actual(Formatter.stringify(negative))
            .run();
    }

    // ---- isLessThanOrEqualTo ----

    @Test
    void givenPositive_whenIsLessThanOrEqualToZero_thenFalse() {
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();
        newTestCase()
            .subject(positive)
            .expression(value -> value.isLessThanOrEqualTo(zero))
            .result(false)
            .name("isLessThanOrEqualTo[" + Formatter.stringify(zero) + "]")
            .expected("<= " + Formatter.stringify(zero))
            .actual(Formatter.stringify(positive))
            .run();
    }

    @Test
    void givenZero_whenIsLessThanOrEqualToZero_thenTrue() {
        final T zero = exampleZeroValue();
        newTestCase()
            .subject(zero)
            .expression(value -> value.isLessThanOrEqualTo(zero))
            .result(true)
            .name("isLessThanOrEqualTo[" + Formatter.stringify(zero) + "]")
            .expected("<= " + Formatter.stringify(zero))
            .actual(Formatter.stringify(zero))
            .run();
    }

    @Test
    void givenNegative_whenIsLessThanOrEqualToZero_thenTrue() {
        final T zero = exampleZeroValue();
        final T negative = exampleNegativeValue();
        newTestCase()
            .subject(negative)
            .expression(value -> value.isLessThanOrEqualTo(zero))
            .result(true)
            .name("isLessThanOrEqualTo[" + Formatter.stringify(zero) + "]")
            .expected("<= " + Formatter.stringify(zero))
            .actual(Formatter.stringify(negative))
            .run();
    }

    // ---- lte ----

    @Test
    void givenPositive_whenLteZero_thenFalse() {
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();
        newTestCase()
            .subject(positive)
            .expression(value -> value.lte(zero))
            .result(false)
            .name("isLessThanOrEqualTo[" + Formatter.stringify(zero) + "]")
            .expected("<= " + Formatter.stringify(zero))
            .actual(Formatter.stringify(positive))
            .run();
    }

    @Test
    void givenZero_whenLteZero_thenTrue() {
        final T zero = exampleZeroValue();
        newTestCase()
            .subject(zero)
            .expression(value -> value.lte(zero))
            .result(true)
            .name("isLessThanOrEqualTo[" + Formatter.stringify(zero) + "]")
            .expected("<= " + Formatter.stringify(zero))
            .actual(Formatter.stringify(zero))
            .run();
    }

    @Test
    void givenNegative_whenLteZero_thenTrue() {
        final T zero = exampleZeroValue();
        final T negative = exampleNegativeValue();
        newTestCase()
            .subject(negative)
            .expression(value -> value.lte(zero))
            .result(true)
            .name("isLessThanOrEqualTo[" + Formatter.stringify(zero) + "]")
            .expected("<= " + Formatter.stringify(zero))
            .actual(Formatter.stringify(negative))
            .run();
    }

    // ---- isGreaterThanOrEqualTo ----

    @Test
    void givenPositive_whenIsGreaterThanOrEqualToZero_thenTrue() {
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();
        newTestCase()
            .subject(positive)
            .expression(value -> value.isGreaterThanOrEqualTo(zero))
            .result(true)
            .name("isGreaterThanOrEqualTo[" + Formatter.stringify(zero) + "]")
            .expected(">= " + Formatter.stringify(zero))
            .actual(Formatter.stringify(positive))
            .run();
    }

    @Test
    void givenZero_whenIsGreaterThanOrEqualToZero_thenTrue() {
        final T zero = exampleZeroValue();
        newTestCase()
            .subject(zero)
            .expression(value -> value.isGreaterThanOrEqualTo(zero))
            .result(true)
            .name("isGreaterThanOrEqualTo[" + Formatter.stringify(zero) + "]")
            .expected(">= " + Formatter.stringify(zero))
            .actual(Formatter.stringify(zero))
            .run();
    }

    @Test
    void givenNegative_whenIsGreaterThanOrEqualToZero_thenFalse() {
        final T zero = exampleZeroValue();
        final T negative = exampleNegativeValue();
        newTestCase()
            .subject(negative)
            .expression(value -> value.isGreaterThanOrEqualTo(zero))
            .result(false)
            .name("isGreaterThanOrEqualTo[" + Formatter.stringify(zero) + "]")
            .expected(">= " + Formatter.stringify(zero))
            .actual(Formatter.stringify(negative))
            .run();
    }

    // ---- gte ----

    @Test
    void givenPositive_whenGteZero_thenTrue() {
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();
        newTestCase()
            .subject(positive)
            .expression(value -> value.gte(zero))
            .result(true)
            .name("isGreaterThanOrEqualTo[" + Formatter.stringify(zero) + "]")
            .expected(">= " + Formatter.stringify(zero))
            .actual(Formatter.stringify(positive))
            .run();
    }

    @Test
    void givenZero_whenGteZero_thenTrue() {
        final T zero = exampleZeroValue();
        newTestCase()
            .subject(zero)
            .expression(value -> value.gte(zero))
            .result(true)
            .name("isGreaterThanOrEqualTo[" + Formatter.stringify(zero) + "]")
            .expected(">= " + Formatter.stringify(zero))
            .actual(Formatter.stringify(zero))
            .run();
    }

    @Test
    void givenNegative_whenGteZero_thenFalse() {
        final T zero = exampleZeroValue();
        final T negative = exampleNegativeValue();
        newTestCase()
            .subject(negative)
            .expression(value -> value.gte(zero))
            .result(false)
            .name("isGreaterThanOrEqualTo[" + Formatter.stringify(zero) + "]")
            .expected(">= " + Formatter.stringify(zero))
            .actual(Formatter.stringify(negative))
            .run();
    }

    // ---- isMaxValue ----

    @Test
    void givenMax_whenIsMaxValue_thenTrue() {
        final T max = exampleMaximumValue();
        newTestCase()
            .subject(max)
            .expression(BoxedNumberValue::isMaxValue)
            .result(true)
            .name("isMax")
            .expected(String.valueOf(max))
            .actual(String.valueOf(max))
            .run();
    }

    @Test
    void givenMin_whenIsMaxValue_thenFalse() {
        final T max = exampleMaximumValue();
        final T min = exampleMinimumValue();
        newTestCase()
            .subject(min)
            .expression(BoxedNumberValue::isMaxValue)
            .result(false)
            .name("isMax")
            .expected(String.valueOf(max))
            .actual(String.valueOf(min))
            .run();
    }

    // ---- isMinValue ----

    @Test
    void givenMax_whenIsMinValue_thenFalse() {
        final T max = exampleMaximumValue();
        final T min = exampleMinimumValue();
        newTestCase()
            .subject(max)
            .expression(BoxedNumberValue::isMinValue)
            .result(false)
            .name("isMin")
            .expected(String.valueOf(min))
            .actual(String.valueOf(max))
            .run();
    }

    @Test
    void givenMin_whenIsMinValue_thenTrue() {
        final T min = exampleMinimumValue();
        newTestCase()
            .subject(min)
            .expression(BoxedNumberValue::isMinValue)
            .result(true)
            .name("isMin")
            .expected(String.valueOf(min))
            .actual(String.valueOf(min))
            .run();
    }

    // ---- isZero ----

    @Test
    void givenPositive_whenIsZero_thenFalse() {
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();
        newTestCase()
            .subject(positive)
            .expression(BoxedNumberValue::isZero)
            .result(false)
            .name("isZero")
            .expected(Formatter.stringify(zero))
            .actual(Formatter.stringify(positive))
            .run();
    }

    @Test
    void givenZero_whenIsZero_thenTrue() {
        final T zero = exampleZeroValue();
        newTestCase()
            .subject(zero)
            .expression(BoxedNumberValue::isZero)
            .result(true)
            .name("isZero")
            .expected(Formatter.stringify(zero))
            .actual(Formatter.stringify(zero))
            .run();
    }

    @Test
    void givenNegative_whenIsZero_thenFalse() {
        final T zero = exampleZeroValue();
        final T negative = exampleNegativeValue();
        newTestCase()
            .subject(negative)
            .expression(BoxedNumberValue::isZero)
            .result(false)
            .name("isZero")
            .expected(Formatter.stringify(zero))
            .actual(Formatter.stringify(negative))
            .run();
    }

    // ---- isNotZero ----

    @Test
    void givenPositive_whenIsNotZero_thenTrue() {
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();
        newTestCase()
            .subject(positive)
            .expression(BoxedNumberValue::isNotZero)
            .result(true)
            .name("isNotZero")
            .expected("not[" + Formatter.stringify(zero) + "]")
            .actual(Formatter.stringify(positive))
            .run();
    }

    @Test
    void givenZero_whenIsNotZero_thenFalse() {
        final T zero = exampleZeroValue();
        newTestCase()
            .subject(zero)
            .expression(BoxedNumberValue::isNotZero)
            .result(false)
            .name("isNotZero")
            .expected("not[" + Formatter.stringify(zero) + "]")
            .actual(Formatter.stringify(zero))
            .run();
    }

    @Test
    void givenNegative_whenIsNotZero_thenTrue() {
        final T zero = exampleZeroValue();
        final T negative = exampleNegativeValue();
        newTestCase()
            .subject(negative)
            .expression(BoxedNumberValue::isNotZero)
            .result(true)
            .name("isNotZero")
            .expected("not[" + Formatter.stringify(zero) + "]")
            .actual(Formatter.stringify(negative))
            .run();
    }

    // ---- isPositive ----

    @Test
    void givenPositive_whenIsPositive_thenTrue() {
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();
        newTestCase()
            .subject(positive)
            .expression(BoxedNumberValue::isPositive)
            .result(true)
            .name("isPositive")
            .expected("> " + Formatter.stringify(zero))
            .actual(Formatter.stringify(positive))
            .run();
    }

    @Test
    void givenZero_whenIsPositive_thenFalse() {
        final T zero = exampleZeroValue();
        newTestCase()
            .subject(zero)
            .expression(BoxedNumberValue::isPositive)
            .result(false)
            .name("isPositive")
            .expected("> " + Formatter.stringify(zero))
            .actual(Formatter.stringify(zero))
            .run();
    }

    @Test
    void givenNegative_whenIsPositive_thenFalse() {
        final T zero = exampleZeroValue();
        final T negative = exampleNegativeValue();
        newTestCase()
            .subject(negative)
            .expression(BoxedNumberValue::isPositive)
            .result(false)
            .name("isPositive")
            .expected("> " + Formatter.stringify(zero))
            .actual(Formatter.stringify(negative))
            .run();
    }

    // ---- isNotPositive ----

    @Test
    void givenPositive_whenIsNotPositive_thenFalse() {
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();
        newTestCase()
            .subject(positive)
            .expression(BoxedNumberValue::isNotPositive)
            .result(false)
            .name("isNotPositive")
            .expected("<= " + Formatter.stringify(zero))
            .actual(Formatter.stringify(positive))
            .run();
    }

    @Test
    void givenZero_whenIsNotPositive_thenTrue() {
        final T zero = exampleZeroValue();
        newTestCase()
            .subject(zero)
            .expression(BoxedNumberValue::isNotPositive)
            .result(true)
            .name("isNotPositive")
            .expected("<= " + Formatter.stringify(zero))
            .actual(Formatter.stringify(zero))
            .run();
    }

    @Test
    void givenNegative_whenIsNotPositive_thenTrue() {
        final T zero = exampleZeroValue();
        final T negative = exampleNegativeValue();
        newTestCase()
            .subject(negative)
            .expression(BoxedNumberValue::isNotPositive)
            .result(true)
            .name("isNotPositive")
            .expected("<= " + Formatter.stringify(zero))
            .actual(Formatter.stringify(negative))
            .run();
    }

    // ---- isNegative ----

    @Test
    void givenPositive_whenIsNegative_thenFalse() {
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();
        newTestCase()
            .subject(positive)
            .expression(BoxedNumberValue::isNegative)
            .result(false)
            .name("isNegative")
            .expected("< " + Formatter.stringify(zero))
            .actual(Formatter.stringify(positive))
            .run();
    }

    @Test
    void givenZero_whenIsNegative_thenFalse() {
        final T zero = exampleZeroValue();
        newTestCase()
            .subject(zero)
            .expression(BoxedNumberValue::isNegative)
            .result(false)
            .name("isNegative")
            .expected("< " + Formatter.stringify(zero))
            .actual(Formatter.stringify(zero))
            .run();
    }

    @Test
    void givenNegative_whenIsNegative_thenTrue() {
        final T zero = exampleZeroValue();
        final T negative = exampleNegativeValue();
        newTestCase()
            .subject(negative)
            .expression(BoxedNumberValue::isNegative)
            .result(true)
            .name("isNegative")
            .expected("< " + Formatter.stringify(zero))
            .actual(Formatter.stringify(negative))
            .run();
    }

    // ---- isNotNegative ----

    @Test
    void givenPositive_whenIsNotNegative_thenTrue() {
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();
        newTestCase()
            .subject(positive)
            .expression(BoxedNumberValue::isNotNegative)
            .result(true)
            .name("isNotNegative")
            .expected(">= " + Formatter.stringify(zero))
            .actual(Formatter.stringify(positive))
            .run();
    }

    @Test
    void givenZero_whenIsNotNegative_thenTrue() {
        final T zero = exampleZeroValue();
        newTestCase()
            .subject(zero)
            .expression(BoxedNumberValue::isNotNegative)
            .result(true)
            .name("isNotNegative")
            .expected(">= " + Formatter.stringify(zero))
            .actual(Formatter.stringify(zero))
            .run();
    }

    @Test
    void givenNegative_whenIsNotNegative_thenFalse() {
        final T zero = exampleZeroValue();
        final T negative = exampleNegativeValue();
        newTestCase()
            .subject(negative)
            .expression(BoxedNumberValue::isNotNegative)
            .result(false)
            .name("isNotNegative")
            .expected(">= " + Formatter.stringify(zero))
            .actual(Formatter.stringify(negative))
            .run();
    }

    @Override
    protected T nonNullValueA() {
        return exampleZeroValue();
    }

    @Override
    protected T nonNullValueB() {
        return examplePositiveValue();
    }

    protected abstract T exampleNegativeValue();
    protected abstract T exampleZeroValue();
    protected abstract T examplePositiveValue();
    protected abstract T exampleMaximumValue();
    protected abstract T exampleMinimumValue();
}
