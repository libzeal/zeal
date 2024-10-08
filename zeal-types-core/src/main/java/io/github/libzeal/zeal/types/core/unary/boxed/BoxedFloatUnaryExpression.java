package io.github.libzeal.zeal.types.core.unary.boxed;

/**
 * An expression used to evaluate {@link Float} instances.
 * <p>
 * Many of the predicates of this expression require unboxing, resulting in some performance loss.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class BoxedFloatUnaryExpression extends BoxedNumberUnaryExpression<Float, BoxedFloatUnaryExpression> {

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public BoxedFloatUnaryExpression(final Float subject) {
        super(subject, "Float expression");
    }

    /**
     * Adds a predicate to the expression that checks if the subject is equal within the supplied delta.
     * <p>
     * Note: The formula used to evaluate the predicate is:
     * <pre><code>abs(subject - value) &lt;= delta</code></pre>
     *
     * @param value
     *     The value to compare to the subject.
     * @param delta
     *     The delta used to compare the expected value to the subject.
     *
     * @return This expression (fluent interface).
     */
    public BoxedFloatUnaryExpression isEqualTo(final Float value, final Float delta) {
        return newExpression(d -> Math.abs(d - value) <= delta)
            .name("isEqualTo[" + value + " +/- " + delta + "]")
            .expected(value + " +/- " + delta)
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is finite.
     *
     * @return This expression (fluent interface).
     */
    public BoxedFloatUnaryExpression isFinite() {
        return newExpression(Float::isFinite)
            .name("isFinite")
            .expected("finite")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is infinite.
     *
     * @return This expression (fluent interface).
     */
    public BoxedFloatUnaryExpression isInfinite() {
        return newExpression(l -> l.isInfinite())
            .name("isInfinite")
            .expected("infinite")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not a number (NaN).
     *
     * @return This expression (fluent interface).
     */
    public BoxedFloatUnaryExpression isNaN() {
        return newExpression(l -> l.isNaN())
            .name("isNaN")
            .expected("NaN")
            .append();
    }

    @Override
    protected Float zero() {
        return 0.0f;
    }

    @Override
    protected Float min() {
        return Float.MIN_VALUE;
    }

    @Override
    protected Float max() {
        return Float.MAX_VALUE;
    }

    @Override
    protected boolean eq(final Float a, final Float b) {
        return a.equals(b);
    }

    @Override
    protected boolean lt(final Float a, final Float b) {
        return a < b;
    }

    @Override
    protected boolean gt(final Float a, final Float b) {
        return a > b;
    }

    @Override
    protected boolean gte(final Float a, final Float b) {
        return a >= b;
    }

    @Override
    protected boolean lte(final Float a, final Float b) {
        return a <= b;
    }
}
