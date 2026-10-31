package io.github.libzeal.zeal.values.core.boxed;

import io.github.libzeal.zeal.values.core.boxed.BoxedNumberExpressions.BoxedNumberOperations;
import io.github.libzeal.zeal.values.sdk.BaseValue;

/**
 * An expression used to evaluate {@link Float} instances.
 * <p>
 * Many of the predicates of this expression require unboxing, resulting in some performance loss.
 *
 * @author Justin Albano
 */
public class BoxedFloatValue extends BaseValue<Float, BoxedFloatValue>
        implements BoxedNumberValue<Float, BoxedFloatValue> {

    private final BoxedFloatOperations operations;

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public BoxedFloatValue(final Float subject) {
        super(subject, "Float value");
        this.operations = new BoxedFloatOperations();
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
    public BoxedFloatValue isEqualTo(final Float value, final Float delta) {
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
    public BoxedFloatValue isFinite() {
        return append(
            expression(Float::isFinite)
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
    public BoxedFloatValue isInfinite() {
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
    public BoxedFloatValue isNaN() {
        return append(
            expression(l -> l.isNaN())
                .name("isNaN")
                .expected("NaN")
                .build()
        );
    }

    @Override
    public BoxedFloatValue isLessThan(final Float value) {
        return append(
            BoxedNumberExpressions.isLessThan(value, operations)
        );
    }

    @Override
    public BoxedFloatValue isGreaterThan(final Float value) {
        return append(
            BoxedNumberExpressions.isGreaterThan(value, operations)
        );
    }

    @Override
    public BoxedFloatValue isLessThanOrEqualTo(final Float value) {
        return append(
            BoxedNumberExpressions.isLessThanOrEqualTo(value, operations)
        );
    }

    @Override
    public BoxedFloatValue isGreaterThanOrEqualTo(final Float value) {
        return append(
            BoxedNumberExpressions.isGreaterThanOrEqualTo(value, operations)
        );
    }

    @Override
    public BoxedFloatValue isMaxValue() {
        return append(
            BoxedNumberExpressions.isMaxValue(operations)
        );
    }

    @Override
    public BoxedFloatValue isMinValue() {
        return append(
            BoxedNumberExpressions.isMinValue(operations)
        );
    }

    @Override
    public BoxedFloatValue isZero() {
        return append(
            BoxedNumberExpressions.isZero(operations)
        );
    }

    @Override
    public BoxedFloatValue isNotZero() {
        return append(
            BoxedNumberExpressions.isNotZero(operations)
        );
    }

    @Override
    public BoxedFloatValue isPositive() {
        return append(
            BoxedNumberExpressions.isPositive(operations)
        );
    }

    @Override
    public BoxedFloatValue isNotPositive() {
        return append(
            BoxedNumberExpressions.isNotPositive(operations)
        );
    }

    @Override
    public BoxedFloatValue isNegative() {
        return append(
            BoxedNumberExpressions.isNegative(operations)
        );
    }

    @Override
    public BoxedFloatValue isNotNegative() {
        return append(
            BoxedNumberExpressions.isNotNegative(operations)
        );
    }

    private static final class BoxedFloatOperations implements BoxedNumberOperations<Float> {

        @Override
        public Float zero() {
            return 0.0f;
        }

        @Override
        public Float min() {
            return Float.MIN_VALUE;
        }

        @Override
        public Float max() {
            return Float.MAX_VALUE;
        }

        @Override
        public boolean eq(final Float a, final Float b) {
            return a.equals(b);
        }

        @Override
        public boolean lt(final Float a, final Float b) {
            return a < b;
        }

        @Override
        public boolean gt(final Float a, final Float b) {
            return a > b;
        }

        @Override
        public boolean gte(final Float a, final Float b) {
            return a >= b;
        }

        @Override
        public boolean lte(final Float a, final Float b) {
            return a <= b;
        }
    }
}
