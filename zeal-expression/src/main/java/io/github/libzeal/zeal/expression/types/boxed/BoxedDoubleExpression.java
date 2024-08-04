package io.github.libzeal.zeal.expression.types.boxed;

import io.github.libzeal.zeal.expression.types.ObjectExpression;

import java.util.function.Predicate;

public class BoxedDoubleExpression extends ObjectExpression<Double, BoxedDoubleExpression> {

    private static final String CANNOT_COMPARE_TO_NULL = "Always fail: cannot compare to subject to (null)";
    public static final String ZERO = stringify(0.0);

    public BoxedDoubleExpression(final Double subject) {
        super(subject, "Double expression");
    }

    public BoxedDoubleExpression isWithinDelta(final Double value, final Double delta) {
        return newPredicate(d ->  Math.abs(d - value) < delta)
            .expectedValue(value + " +/- " + delta)
            .append();
    }

    public BoxedDoubleExpression isLessThan(final Double value) {
        return nullProtectedValueExpression(
            value,
            "isLessThan[" + stringify(value) + "]",
            "< " + value,
            l -> value != null && l < value
        );
    }

    private BoxedDoubleExpression nullProtectedValueExpression(final Double value, final String name,
                                                             final String expectedValue,
                                                             final Predicate<Double> longPredicate) {

        final PredicateBuilder builder = newPredicate(longPredicate)
            .name(name);

        if (value == null) {
            builder.expectedValue(CANNOT_COMPARE_TO_NULL);
        }
        else {
            builder.expectedValue(expectedValue);
        }

        return builder.append();
    }

    public BoxedDoubleExpression lt(final Double value) {
        return isLessThan(value);
    }

    public BoxedDoubleExpression isGreaterThan(final Double value) {
        return nullProtectedValueExpression(
            value,
            "isGreaterThan[" + stringify(value) + "]",
            "> " + value,
            l -> value != null && l > value
        );
    }

    public BoxedDoubleExpression gt(final Double value) {
        return isGreaterThan(value);
    }

    public BoxedDoubleExpression isLessThanOrEqualTo(final Double value) {
        return nullProtectedValueExpression(
            value,
            "isLessThanOrEqualTo[" + stringify(value) + "]",
            "<= " + value,
            l -> value != null && l <= value
        );
    }

    public BoxedDoubleExpression lte(final Double value) {
        return isLessThanOrEqualTo(value);
    }

    public BoxedDoubleExpression isGreaterThanOrEqualTo(final Double value) {
        return nullProtectedValueExpression(
            value,
            "isGreaterThanOrEqualTo[" + stringify(value) + "]",
            ">= " + value,
            l -> value != null && l >= value
        );
    }

    public BoxedDoubleExpression gte(final Double value) {
        return isGreaterThanOrEqualTo(value);
    }

    public BoxedDoubleExpression isMaxValue() {
        return newPredicate(l -> l == Double.MAX_VALUE)
            .name("isMax")
            .expectedValue(String.valueOf(Double.MAX_VALUE))
            .append();
    }

    public BoxedDoubleExpression isMinValue() {
        return newPredicate(l -> l == Double.MIN_VALUE)
            .name("isMin")
            .expectedValue(String.valueOf(Double.MIN_VALUE))
            .append();
    }

    public BoxedDoubleExpression isZero() {
        return newPredicate(l -> l == 0)
            .name("isZero")
            .expectedValue(ZERO)
            .append();
    }

    public BoxedDoubleExpression isPositive() {
        return newPredicate(l -> l > 0)
            .name("isPositive")
            .expectedValue("> " + ZERO)
            .append();
    }

    public BoxedDoubleExpression isNotPositive() {
        return newPredicate(l -> l <= 0)
            .name("isNotPositive")
            .expectedValue("<= " + ZERO)
            .append();
    }

    public BoxedDoubleExpression isNegative() {
        return newPredicate(l -> l < 0)
            .name("isNegative")
            .expectedValue("< " + ZERO)
            .append();
    }

    public BoxedDoubleExpression isNotNegative() {
        return newPredicate(l -> l >= 0)
            .name("isNotNegative")
            .expectedValue(">= " + ZERO)
            .append();
    }

    public BoxedDoubleExpression isFinite() {
        return newPredicate(Double::isFinite)
            .name("isFinite")
            .expectedValue("finite")
            .append();
    }

    public BoxedDoubleExpression isInfinite() {
        return newPredicate(l -> l.isInfinite())
            .name("isInfinite")
            .expectedValue("infinite")
            .append();
    }

    public BoxedDoubleExpression isNaN() {
        return newPredicate(l -> l.isNaN())
            .name("isNaN")
            .expectedValue("NaN")
            .append();
    }
}
