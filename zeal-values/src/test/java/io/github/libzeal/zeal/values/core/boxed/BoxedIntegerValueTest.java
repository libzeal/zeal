package io.github.libzeal.zeal.values.core.boxed;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BoxedIntegerValueTest extends BoxedWholeNumberValueTest<Integer, BoxedIntegerValue> {

    @Override
    protected BoxedIntegerValue expression(final Integer value) {
        return new BoxedIntegerValue(value);
    }

    @Override
    protected Integer exampleNegativeValue() {
        return -1;
    }

    @Override
    protected Integer exampleZeroValue() {
        return 0;
    }

    @Override
    protected Integer examplePositiveValue() {
        return 1;
    }

    @Override
    protected Integer exampleMaximumValue() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected Integer exampleMinimumValue() {
        return Integer.MIN_VALUE;
    }

    @Override
    protected Integer add(final Integer a, final short b) {
        return a + b;
    }

    @Override
    protected Integer subtract(final Integer a, final short b) {
        return a - b;
    }

    // ---- predicates and comparators of a supertype ----

    @Test
    void givenPredicatesOfSupertype_whenSatisfiesAndDoesNotSatisfy_thenAccepted() {

        final Predicate<Object> nonNull = Objects::nonNull;
        final Predicate<Number> negative = n -> n.doubleValue() < 0;

        final BoxedIntegerValue value = new BoxedIntegerValue(1)
            .satisfies(nonNull)
            .doesNotSatisfy(negative);

        assertTrue(value.evaluate().isTrue());
    }

    @Test
    void givenComparatorOfSupertype_whenIsEqualToAndIsNotEqualTo_thenAccepted() {

        final Comparator<Number> byValue = Comparator.comparingDouble(Number::doubleValue);

        final BoxedIntegerValue value = new BoxedIntegerValue(1)
            .isEqualTo(1, byValue)
            .isNotEqualTo(2, byValue);

        assertTrue(value.evaluate().isTrue());
    }
}
