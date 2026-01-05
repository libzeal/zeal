package io.github.libzeal.zeal.values.core.boxed;

/**
 * An expression used to evaluate {@link Float} instances.
 * <p>
 * Many of the predicates of this expression require unboxing, resulting in some performance loss.
 *
 * @author Justin Albano
 */
public class BoxedFloatValue extends BoxedNumberValue<Float, BoxedFloatValue> {

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public BoxedFloatValue(final Float subject) {
        super(subject, "Float value");
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
    public BoxedFloatValue isEqualTo(final Float value, final Float delta) {
        return append(
            expression(d -> Math.abs(d - value) <= delta)
                .name("isEqualTo[" + value + " +/- " + delta + "]")
                .expected(value + " +/- " + delta)
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject is finite.
     *
     * @return This expression (fluent interface).
     */
    public BoxedFloatValue isFinite() {
        return append(
            expression(Float::isFinite)
                .name("isFinite")
                .expected("finite")
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject is infinite.
     *
     * @return This expression (fluent interface).
     */
    public BoxedFloatValue isInfinite() {
        return append(
            expression(l -> l.isInfinite())
                .name("isInfinite")
                .expected("infinite")
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not a number (NaN).
     *
     * @return This expression (fluent interface).
     */
    public BoxedFloatValue isNaN() {
        return append(
            expression(l -> l.isNaN())
                .name("isNaN")
                .expected("NaN")
        );
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
