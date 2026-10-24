package io.github.libzeal.zeal.values.core.boxed;

import io.github.libzeal.zeal.values.core.boxed.BoxedNumberExpressions.BoxedNumberOperations;
import io.github.libzeal.zeal.values.sdk.BaseValue;

/**
 * An expression used to evaluate {@link Long} instances.
 * <p>
 * Note: Many of the predicates of this expression require unboxing, resulting in some performance loss.
 *
 * @author Justin Albano
 */
public class BoxedLongValue extends BaseValue<Long, BoxedLongValue>
        implements BoxedWholeNumberValue<Long, BoxedLongValue> {

    private final BoxedLongOperations operations;

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public BoxedLongValue(final Long subject) {
        super(subject, "Long value");
        this.operations = new BoxedLongOperations();
    }

    @Override
    public BoxedLongValue isEven() {
        return append(
            BoxedWholeNumberExpressions.isEven()
        );
    }

    @Override
    public BoxedLongValue isOdd() {
        return append(
            BoxedWholeNumberExpressions.isOdd()
        );
    }

    @Override
    public BoxedLongValue isLessThan(final Long value) {
        return append(
            BoxedNumberExpressions.isLessThan(value, operations)
        );
    }

    @Override
    public BoxedLongValue isGreaterThan(final Long value) {
        return append(
            BoxedNumberExpressions.isGreaterThan(value, operations)
        );
    }

    @Override
    public BoxedLongValue isLessThanOrEqualTo(final Long value) {
        return append(
            BoxedNumberExpressions.isLessThanOrEqualTo(value, operations)
        );
    }

    @Override
    public BoxedLongValue isGreaterThanOrEqualTo(final Long value) {
        return append(
            BoxedNumberExpressions.isGreaterThanOrEqualTo(value, operations)
        );
    }

    @Override
    public BoxedLongValue isMaxValue() {
        return append(
            BoxedNumberExpressions.isMaxValue(operations)
        );
    }

    @Override
    public BoxedLongValue isMinValue() {
        return append(
            BoxedNumberExpressions.isMinValue(operations)
        );
    }

    @Override
    public BoxedLongValue isZero() {
        return append(
            BoxedNumberExpressions.isZero(operations)
        );
    }

    @Override
    public BoxedLongValue isNotZero() {
        return append(
            BoxedNumberExpressions.isNotZero(operations)
        );
    }

    @Override
    public BoxedLongValue isPositive() {
        return append(
            BoxedNumberExpressions.isPositive(operations)
        );
    }

    @Override
    public BoxedLongValue isNotPositive() {
        return append(
            BoxedNumberExpressions.isNotPositive(operations)
        );
    }

    @Override
    public BoxedLongValue isNegative() {
        return append(
            BoxedNumberExpressions.isNegative(operations)
        );
    }

    @Override
    public BoxedLongValue isNotNegative() {
        return append(
            BoxedNumberExpressions.isNotNegative(operations)
        );
    }

    private static final class BoxedLongOperations implements BoxedNumberOperations<Long> {

        @Override
        public Long zero() {
            return 0L;
        }

        @Override
        public Long min() {
            return Long.MIN_VALUE;
        }

        @Override
        public Long max() {
            return Long.MAX_VALUE;
        }

        @Override
        public boolean eq(final Long a, final Long b) {
            return a.equals(b);
        }

        @Override
        public boolean lt(final Long a, final Long b) {
            return a < b;
        }

        @Override
        public boolean gt(final Long a, final Long b) {
            return a > b;
        }

        @Override
        public boolean gte(final Long a, final Long b) {
            return a >= b;
        }

        @Override
        public boolean lte(final Long a, final Long b) {
            return a <= b;
        }
    }
}
