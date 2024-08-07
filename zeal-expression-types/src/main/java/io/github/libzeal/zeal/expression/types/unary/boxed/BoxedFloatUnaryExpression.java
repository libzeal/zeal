package io.github.libzeal.zeal.expression.types.unary.boxed;

/**
 * An expression used to evaluate {@link Float} instances.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class BoxedFloatUnaryExpression extends BoxedNumericUnaryExpression<Float, BoxedFloatUnaryExpression> {

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
     *
     * @param value
     *     The value to compare to the subject.
     * @param delta
     *     The delta used to compare the expected value to the subject.
     *
     * @return This expression (fluent interface).
     *
     * @implSpec The formula used to evaluate the predicate is:
     *
     *     <pre><code>abs(subject - value) <= delta</code></pre>
     */
    public BoxedFloatUnaryExpression isEqualTo(final Float value, final Float delta) {
        return newPredicate(d -> Math.abs(d - value) <= delta)
            .name("isEqualTo[" + value + " +/- " + delta + "]")
            .expectedValue(value + " +/- " + delta)
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is finite.
     *
     * @return This expression (fluent interface).
     */
    public BoxedFloatUnaryExpression isFinite() {
        return newPredicate(Float::isFinite)
            .name("isFinite")
            .expectedValue("finite")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is infinite.
     *
     * @return This expression (fluent interface).
     */
    public BoxedFloatUnaryExpression isInfinite() {
        return newPredicate(l -> l.isInfinite())
            .name("isInfinite")
            .expectedValue("infinite")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not a number (NaN).
     *
     * @return This expression (fluent interface).
     */
    public BoxedFloatUnaryExpression isNaN() {
        return newPredicate(l -> l.isNaN())
            .name("isNaN")
            .expectedValue("NaN")
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
