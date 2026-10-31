package io.github.libzeal.zeal.values.core.boxed;

import io.github.libzeal.zeal.values.core.boxed.BoxedNumberExpressions.BoxedNumberOperations;
import io.github.libzeal.zeal.values.sdk.BaseValue;

/**
 * An expression used to evaluate {@link Short} instances.
 * <p>
 * Note: Many of the predicates of this expression require unboxing, resulting in some performance loss.
 *
 * @author Justin Albano
 */
public class BoxedShortValue extends BaseValue<Short, BoxedShortValue>
        implements BoxedWholeNumberValue<Short, BoxedShortValue> {

    private final BoxedShortOperations operations;

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public BoxedShortValue(final Short subject) {
        super(subject, "Short value");
        this.operations = new BoxedShortOperations();
    }

    @Override
    public BoxedShortValue isEven() {
        return append(
            BoxedWholeNumberExpressions.isEven()
        );
    }

    @Override
    public BoxedShortValue isOdd() {
        return append(
            BoxedWholeNumberExpressions.isOdd()
        );
    }

    @Override
    public BoxedShortValue isLessThan(final Short value) {
        return append(
            BoxedNumberExpressions.isLessThan(value, operations)
        );
    }

    @Override
    public BoxedShortValue isGreaterThan(final Short value) {
        return append(
            BoxedNumberExpressions.isGreaterThan(value, operations)
        );
    }

    @Override
    public BoxedShortValue isLessThanOrEqualTo(final Short value) {
        return append(
            BoxedNumberExpressions.isLessThanOrEqualTo(value, operations)
        );
    }

    @Override
    public BoxedShortValue isGreaterThanOrEqualTo(final Short value) {
        return append(
            BoxedNumberExpressions.isGreaterThanOrEqualTo(value, operations)
        );
    }

    @Override
    public BoxedShortValue isMaxValue() {
        return append(
            BoxedNumberExpressions.isMaxValue(operations)
        );
    }

    @Override
    public BoxedShortValue isMinValue() {
        return append(
            BoxedNumberExpressions.isMinValue(operations)
        );
    }

    @Override
    public BoxedShortValue isZero() {
        return append(
            BoxedNumberExpressions.isZero(operations)
        );
    }

    @Override
    public BoxedShortValue isNotZero() {
        return append(
            BoxedNumberExpressions.isNotZero(operations)
        );
    }

    @Override
    public BoxedShortValue isPositive() {
        return append(
            BoxedNumberExpressions.isPositive(operations)
        );
    }

    @Override
    public BoxedShortValue isNotPositive() {
        return append(
            BoxedNumberExpressions.isNotPositive(operations)
        );
    }

    @Override
    public BoxedShortValue isNegative() {
        return append(
            BoxedNumberExpressions.isNegative(operations)
        );
    }

    @Override
    public BoxedShortValue isNotNegative() {
        return append(
            BoxedNumberExpressions.isNotNegative(operations)
        );
    }

    private static final class BoxedShortOperations implements BoxedNumberOperations<Short> {

        @Override
        public Short zero() {
            return 0;
        }

        @Override
        public Short min() {
            return Short.MIN_VALUE;
        }

        @Override
        public Short max() {
            return Short.MAX_VALUE;
        }

        @Override
        public boolean eq(final Short a, final Short b) {
            return a.equals(b);
        }

        @Override
        public boolean lt(final Short a, final Short b) {
            return a < b;
        }

        @Override
        public boolean gt(final Short a, final Short b) {
            return a > b;
        }

        @Override
        public boolean gte(final Short a, final Short b) {
            return a >= b;
        }

        @Override
        public boolean lte(final Short a, final Short b) {
            return a <= b;
        }
    }
}
