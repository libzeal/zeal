package io.github.libzeal.zeal.expression.types.boxed;

/**
 * An expression used to evaluate {@link Long} instances.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class BoxedLongExpression extends BoxedNumericExpression<Long, BoxedLongExpression> {

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public BoxedLongExpression(final Long subject) {
        super(subject, "Long expression");
    }

    /**
     * Adds a predicate to the expression that checks if the subject is even.
     *
     * @return This expression (fluent interface).
     */
    public BoxedLongExpression isEven() {
        return newPredicate(l -> l % 2 == 0)
            .name("isEven")
            .expectedValue("% 2 := 0")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is odd.
     *
     * @return This expression (fluent interface).
     */
    public BoxedLongExpression isOdd() {
        return newPredicate(l -> l % 2 != 0)
            .name("isOdd")
            .expectedValue("% 2 != 0")
            .append();
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
