package io.github.libzeal.zeal.expression.types.boxed;

/**
 * An expression used to evaluate {@link Short} instances.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class BoxedShortExpression extends BoxedNumericExpression<Short, BoxedShortExpression> {

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public BoxedShortExpression(final Short subject) {
        super(subject, "Short expression");
    }

    /**
     * Adds a predicate to the expression that checks if the subject is even.
     *
     * @return This expression (fluent interface).
     */
    public BoxedShortExpression isEven() {
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
    public BoxedShortExpression isOdd() {
        return newPredicate(l -> l % 2 != 0)
            .name("isOdd")
            .expectedValue("% 2 != 0")
            .append();
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
