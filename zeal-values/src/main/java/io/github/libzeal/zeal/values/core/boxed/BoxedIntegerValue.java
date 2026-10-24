package io.github.libzeal.zeal.values.core.boxed;

import io.github.libzeal.zeal.values.core.boxed.BoxedNumberExpressions.BoxedNumberOperations;
import io.github.libzeal.zeal.values.sdk.BaseValue;

/**
 * An expression used to evaluate {@link Integer} instances.
 * <p>
 * Note: Many of the predicates of this expression require unboxing, resulting in some performance loss.
 *
 * @author Justin Albano
 */
public class BoxedIntegerValue extends BaseValue<Integer, BoxedIntegerValue>
        implements BoxedWholeNumberValue<Integer, BoxedIntegerValue> {

    private final BoxedIntegerOperations operations;

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public BoxedIntegerValue(final Integer subject) {
        super(subject, "Integer value");
        this.operations = new BoxedIntegerOperations();
    }

    @Override
    public BoxedIntegerValue isEven() {
        return append(
            BoxedWholeNumberExpressions.isEven()
        );
    }

    @Override
    public BoxedIntegerValue isOdd() {
        return append(
            BoxedWholeNumberExpressions.isOdd()
        );
    }

    @Override
    public BoxedIntegerValue isLessThan(final Integer value) {
        return append(
            BoxedNumberExpressions.isLessThan(value, operations)
        );
    }

    @Override
    public BoxedIntegerValue isGreaterThan(final Integer value) {
        return append(
            BoxedNumberExpressions.isGreaterThan(value, operations)
        );
    }

    @Override
    public BoxedIntegerValue isLessThanOrEqualTo(final Integer value) {
        return append(
            BoxedNumberExpressions.isLessThanOrEqualTo(value, operations)
        );
    }

    @Override
    public BoxedIntegerValue isGreaterThanOrEqualTo(final Integer value) {
        return append(
            BoxedNumberExpressions.isGreaterThanOrEqualTo(value, operations)
        );
    }

    @Override
    public BoxedIntegerValue isMaxValue() {
        return append(
            BoxedNumberExpressions.isMaxValue(operations)
        );
    }

    @Override
    public BoxedIntegerValue isMinValue() {
        return append(
            BoxedNumberExpressions.isMinValue(operations)
        );
    }

    @Override
    public BoxedIntegerValue isZero() {
        return append(
            BoxedNumberExpressions.isZero(operations)
        );
    }

    @Override
    public BoxedIntegerValue isNotZero() {
        return append(
            BoxedNumberExpressions.isNotZero(operations)
        );
    }

    @Override
    public BoxedIntegerValue isPositive() {
        return append(
            BoxedNumberExpressions.isPositive(operations)
        );
    }

    @Override
    public BoxedIntegerValue isNotPositive() {
        return append(
            BoxedNumberExpressions.isNotPositive(operations)
        );
    }

    @Override
    public BoxedIntegerValue isNegative() {
        return append(
            BoxedNumberExpressions.isNegative(operations)
        );
    }

    @Override
    public BoxedIntegerValue isNotNegative() {
        return append(
            BoxedNumberExpressions.isNotNegative(operations)
        );
    }

    private static final class BoxedIntegerOperations implements BoxedNumberOperations<Integer> {

        @Override
        public Integer zero() {
            return 0;
        }

        @Override
        public Integer min() {
            return Integer.MIN_VALUE;
        }

        @Override
        public Integer max() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean eq(final Integer a, final Integer b) {
            return a.equals(b);
        }

        @Override
        public boolean lt(final Integer a, final Integer b) {
            return a < b;
        }

        @Override
        public boolean gt(final Integer a, final Integer b) {
            return a > b;
        }

        @Override
        public boolean gte(final Integer a, final Integer b) {
            return a >= b;
        }

        @Override
        public boolean lte(final Integer a, final Integer b) {
            return a <= b;
        }
    }
}
