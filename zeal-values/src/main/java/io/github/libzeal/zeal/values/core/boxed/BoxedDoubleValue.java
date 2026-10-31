package io.github.libzeal.zeal.values.core.boxed;

import io.github.libzeal.zeal.values.core.boxed.BoxedNumberExpressions.BoxedNumberOperations;
import io.github.libzeal.zeal.values.sdk.BaseValue;

/**
 * An expression used to evaluate {@link Double} instances.
 * <p>
 * Note: Many of the predicates of this expression require unboxing, resulting in some performance loss.
 *
 * @author Justin Albano
 */
public class BoxedDoubleValue extends BaseValue<Double, BoxedDoubleValue>
        implements BoxedNumberValue<Double, BoxedDoubleValue> {

    private final BoxedDoubleOperations operations;

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public BoxedDoubleValue(final Double subject) {
        super(subject, "Double value");
        this.operations = new BoxedDoubleOperations();
    }

    /**
     * Adds a condition to the expression that checks if the subject is equal within the supplied delta.
     * <p>
     * Note: The formula used to evaluate the predicate is:
     * <pre><code>abs(subject - value) &lt;= delta</code></pre>
     *
     * @param value
     *     The value to compare to the subject.
     * @param delta
     *     The delta used to compare the expected value to the subject.
     *
     * @return This expression (fluent interface).
     */
    public BoxedDoubleValue isEqualTo(final Double value, final Double delta) {
        return append(
            expression(d -> Math.abs(d - value) <= delta)
                .name("isEqualTo[" + value + " +/- " + delta + "]")
                .expected(value + " +/- " + delta)
                .build()
        );
    }

    /**
     * Adds a condition to the expression that checks if the subject is finite.
     *
     * @return This expression (fluent interface).
     */
    public BoxedDoubleValue isFinite() {
        return append(
            expression(Double::isFinite)
                .name("isFinite")
                .expected("finite")
                .build()
        );
    }

    /**
     * Adds a condition to the expression that checks if the subject is infinite.
     *
     * @return This expression (fluent interface).
     */
    public BoxedDoubleValue isInfinite() {
        return append(
            expression(l -> l.isInfinite())
                .name("isInfinite")
                .expected("infinite")
                .build()
        );
    }

    /**
     * Adds a condition to the expression that checks if the subject is not a number (NaN).
     *
     * @return This expression (fluent interface).
     */
    public BoxedDoubleValue isNaN() {
        return append(
            expression(l -> l.isNaN())
                .name("isNaN")
                .expected("NaN")
                .build()
        );
    }

    @Override
    public BoxedDoubleValue isLessThan(final Double value) {
        return append(
            BoxedNumberExpressions.isLessThan(value, operations)
        );
    }

    @Override
    public BoxedDoubleValue isGreaterThan(final Double value) {
        return append(
            BoxedNumberExpressions.isGreaterThan(value, operations)
        );
    }

    @Override
    public BoxedDoubleValue isLessThanOrEqualTo(final Double value) {
        return append(
            BoxedNumberExpressions.isLessThanOrEqualTo(value, operations)
        );
    }

    @Override
    public BoxedDoubleValue isGreaterThanOrEqualTo(final Double value) {
        return append(
            BoxedNumberExpressions.isGreaterThanOrEqualTo(value, operations)
        );
    }

    @Override
    public BoxedDoubleValue isMaxValue() {
        return append(
            BoxedNumberExpressions.isMaxValue(operations)
        );
    }

    @Override
    public BoxedDoubleValue isMinValue() {
        return append(
            BoxedNumberExpressions.isMinValue(operations)
        );
    }

    @Override
    public BoxedDoubleValue isZero() {
        return append(
            BoxedNumberExpressions.isZero(operations)
        );
    }

    @Override
    public BoxedDoubleValue isNotZero() {
        return append(
            BoxedNumberExpressions.isNotZero(operations)
        );
    }

    @Override
    public BoxedDoubleValue isPositive() {
        return append(
            BoxedNumberExpressions.isPositive(operations)
        );
    }

    @Override
    public BoxedDoubleValue isNotPositive() {
        return append(
            BoxedNumberExpressions.isNotPositive(operations)
        );
    }

    @Override
    public BoxedDoubleValue isNegative() {
        return append(
            BoxedNumberExpressions.isNegative(operations)
        );
    }

    @Override
    public BoxedDoubleValue isNotNegative() {
        return append(
            BoxedNumberExpressions.isNotNegative(operations)
        );
    }

    private static final class BoxedDoubleOperations implements BoxedNumberOperations<Double> {

        @Override
        public Double zero() {
            return 0.0;
        }

        @Override
        public Double min() {
            return Double.MIN_VALUE;
        }

        @Override
        public Double max() {
            return Double.MAX_VALUE;
        }

        @Override
        public boolean eq(final Double a, final Double b) {
            return a.equals(b);
        }

        @Override
        public boolean lt(final Double a, final Double b) {
            return a < b;
        }

        @Override
        public boolean gt(final Double a, final Double b) {
            return a > b;
        }

        @Override
        public boolean gte(final Double a, final Double b) {
            return a >= b;
        }

        @Override
        public boolean lte(final Double a, final Double b) {
            return a <= b;
        }
    }
}
