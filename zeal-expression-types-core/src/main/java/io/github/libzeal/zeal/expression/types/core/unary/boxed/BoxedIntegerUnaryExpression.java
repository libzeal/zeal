package io.github.libzeal.zeal.expression.types.core.unary.boxed;

/**
 * An expression used to evaluate {@link Integer} instances.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class BoxedIntegerUnaryExpression extends BoxedNumericUnaryExpression<Integer, BoxedIntegerUnaryExpression> {

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public BoxedIntegerUnaryExpression(final Integer subject) {
        super(subject, "Integer expression");
    }

    /**
     * Adds a predicate to the expression that checks if the subject is even.
     *
     * @return This expression (fluent interface).
     */
    public BoxedIntegerUnaryExpression isEven() {
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
    public BoxedIntegerUnaryExpression isOdd() {
        return newPredicate(l -> l % 2 != 0)
            .name("isOdd")
            .expectedValue("% 2 != 0")
            .append();
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
