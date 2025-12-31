package io.github.libzeal.zeal.values.core.boxed;

/**
 * An expression used to evaluate {@link Byte} instances.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class BoxedByteValue extends BoxedWholeNumberValue<Byte, BoxedByteValue> {

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public BoxedByteValue(final Byte subject) {
        super(subject, "Byte value");
    }

    @Override
    protected Byte zero() {
        return 0;
    }

    @Override
    protected Byte min() {
        return Byte.MIN_VALUE;
    }

    @Override
    protected Byte max() {
        return Byte.MAX_VALUE;
    }

    @Override
    protected boolean eq(final Byte a, final Byte b) {
        return a.equals(b);
    }

    @Override
    protected boolean lt(final Byte a, final Byte b) {
        return a < b;
    }

    @Override
    protected boolean gt(final Byte a, final Byte b) {
        return a > b;
    }

    @Override
    protected boolean gte(final Byte a, final Byte b) {
        return a >= b;
    }

    @Override
    protected boolean lte(final Byte a, final Byte b) {
        return a <= b;
    }
}
