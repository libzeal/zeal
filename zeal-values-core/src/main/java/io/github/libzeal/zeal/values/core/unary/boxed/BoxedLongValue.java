package io.github.libzeal.zeal.values.core.unary.boxed;

/**
 * An expression used to evaluate {@link Long} instances.
 * <p>
 * Note: Many of the predicates of this expression require unboxing, resulting in some performance loss.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class BoxedLongValue extends BoxedWholeNumberValue<Long, BoxedLongValue> {

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public BoxedLongValue(final Long subject) {
        super(subject, "Long expression");
    }

    @Override
    protected Long zero() {
        return 0L;
    }

    @Override
    protected Long min() {
        return Long.MIN_VALUE;
    }

    @Override
    protected Long max() {
        return Long.MAX_VALUE;
    }

    @Override
    protected boolean eq(final Long a, final Long b) {
        return a.equals(b);
    }

    @Override
    protected boolean lt(final Long a, final Long b) {
        return a < b;
    }

    @Override
    protected boolean gt(final Long a, final Long b) {
        return a > b;
    }

    @Override
    protected boolean gte(final Long a, final Long b) {
        return a >= b;
    }

    @Override
    protected boolean lte(final Long a, final Long b) {
        return a <= b;
    }
}
