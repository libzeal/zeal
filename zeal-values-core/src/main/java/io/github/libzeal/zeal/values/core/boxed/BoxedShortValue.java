package io.github.libzeal.zeal.values.core.boxed;

/**
 * An expression used to evaluate {@link Short} instances.
 * <p>
 * Note: Many of the predicates of this expression require unboxing, resulting in some performance loss.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class BoxedShortValue extends BoxedWholeNumberValue<Short, BoxedShortValue> {

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public BoxedShortValue(final Short subject) {
        super(subject, "Short expression");
    }

    @Override
    protected Short zero() {
        return 0;
    }

    @Override
    protected Short min() {
        return Short.MIN_VALUE;
    }

    @Override
    protected Short max() {
        return Short.MAX_VALUE;
    }

    @Override
    protected boolean eq(final Short a, final Short b) {
        return a.equals(b);
    }

    @Override
    protected boolean lt(final Short a, final Short b) {
        return a < b;
    }

    @Override
    protected boolean gt(final Short a, final Short b) {
        return a > b;
    }

    @Override
    protected boolean gte(final Short a, final Short b) {
        return a >= b;
    }

    @Override
    protected boolean lte(final Short a, final Short b) {
        return a <= b;
    }
}
