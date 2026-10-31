package io.github.libzeal.zeal.values.core.boxed;

import io.github.libzeal.zeal.values.core.boxed.BoxedNumberExpressions.BoxedNumberOperations;
import io.github.libzeal.zeal.values.sdk.BaseValue;

/**
 * An expression used to evaluate {@link Byte} instances.
 *
 * @author Justin Albano
 */
public class BoxedByteValue extends BaseValue<Byte, BoxedByteValue> implements BoxedWholeNumberValue<Byte, BoxedByteValue> {

    private final BoxedByteOperations operations;

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public BoxedByteValue(final Byte subject) {
        super(subject, "Byte value");
        this.operations = new BoxedByteOperations();
    }

    @Override
    public BoxedByteValue isEven() {
        return append(
            BoxedWholeNumberExpressions.isEven()
        );
    }

    @Override
    public BoxedByteValue isOdd() {
        return append(
            BoxedWholeNumberExpressions.isOdd()
        );
    }

    @Override
    public BoxedByteValue isLessThan(final Byte value) {
        return append(
            BoxedNumberExpressions.isLessThan(value, operations)
        );
    }

    @Override
    public BoxedByteValue isGreaterThan(final Byte value) {
        return append(
            BoxedNumberExpressions.isGreaterThan(value, operations)
        );
    }

    @Override
    public BoxedByteValue isLessThanOrEqualTo(final Byte value) {
        return append(
            BoxedNumberExpressions.isLessThanOrEqualTo(value, operations)
        );
    }

    @Override
    public BoxedByteValue isGreaterThanOrEqualTo(final Byte value) {
        return append(
            BoxedNumberExpressions.isGreaterThanOrEqualTo(value, operations)
        );
    }

    @Override
    public BoxedByteValue isMaxValue() {
        return append(
            BoxedNumberExpressions.isMaxValue(operations)
        );
    }

    @Override
    public BoxedByteValue isMinValue() {
        return append(
            BoxedNumberExpressions.isMinValue(operations)
        );
    }

    @Override
    public BoxedByteValue isZero() {
        return append(
            BoxedNumberExpressions.isZero(operations)
        );
    }

    @Override
    public BoxedByteValue isNotZero() {
        return append(
            BoxedNumberExpressions.isNotZero(operations)
        );
    }

    @Override
    public BoxedByteValue isPositive() {
        return append(
            BoxedNumberExpressions.isPositive(operations)
        );
    }

    @Override
    public BoxedByteValue isNotPositive() {
        return append(
            BoxedNumberExpressions.isNotPositive(operations)
        );
    }

    @Override
    public BoxedByteValue isNegative() {
        return append(
            BoxedNumberExpressions.isNegative(operations)
        );
    }

    @Override
    public BoxedByteValue isNotNegative() {
        return append(
            BoxedNumberExpressions.isNotNegative(operations)
        );
    }
    private static final class BoxedByteOperations implements BoxedNumberOperations<Byte> {

        @Override
        public Byte zero() {
            return 0;
        }

        @Override
        public Byte min() {
            return Byte.MIN_VALUE;
        }

        @Override
        public Byte max() {
            return Byte.MAX_VALUE;
        }

        @Override
        public boolean eq(final Byte a, final Byte b) {
            return a.equals(b);
        }

        @Override
        public boolean lt(final Byte a, final Byte b) {
            return a < b;
        }

        @Override
        public boolean gt(final Byte a, final Byte b) {
            return a > b;
        }

        @Override
        public boolean gte(final Byte a, final Byte b) {
            return a >= b;
        }

        @Override
        public boolean lte(final Byte a, final Byte b) {
            return a <= b;
        }
    }
}
