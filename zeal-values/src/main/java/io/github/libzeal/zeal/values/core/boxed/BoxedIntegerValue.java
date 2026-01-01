package io.github.libzeal.zeal.values.core.boxed;

/**
 * An expression used to evaluate {@link Integer} instances.
 * <p>
 * Note: Many of the predicates of this expression require unboxing, resulting in some performance loss.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class BoxedIntegerValue extends BoxedWholeNumberValue<Integer, BoxedIntegerValue> {

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public BoxedIntegerValue(final Integer subject) {
        super(subject, "Integer value");
    }

    @Override
    protected Integer zero() {
        return 0;
    }

    @Override
    protected Integer min() {
        return Integer.MIN_VALUE;
    }

    @Override
    protected Integer max() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected boolean eq(final Integer a, final Integer b) {
        return a.equals(b);
    }

    @Override
    protected boolean lt(final Integer a, final Integer b) {
        return a < b;
    }

    @Override
    protected boolean gt(final Integer a, final Integer b) {
        return a > b;
    }

    @Override
    protected boolean gte(final Integer a, final Integer b) {
        return a >= b;
    }

    @Override
    protected boolean lte(final Integer a, final Integer b) {
        return a <= b;
    }
}
