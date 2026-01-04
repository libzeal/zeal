package io.github.libzeal.zeal.values.core.boxed;

/**
 * An expression used to evaluate {@link Double} instances.
 * <p>
 * Note: Many of the predicates of this expression require unboxing, resulting in some performance loss.
 *
 * @author Justin Albano
 */
public class BoxedDoubleValue extends BoxedNumberValue<Double, BoxedDoubleValue> {

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public BoxedDoubleValue(final Double subject) {
        super(subject, "Double value");
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
    public BoxedDoubleValue isEqualTo(final Double value, final Double delta) {
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
    public BoxedDoubleValue isFinite() {
        return append(
            expression(Double::isFinite)
                .name("isFinite")
                .expected("finite")
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject is infinite.
     *
     * @return This expression (fluent interface).
     */
    public BoxedDoubleValue isInfinite() {
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
    public BoxedDoubleValue isNaN() {
        return append(
            expression(l -> l.isNaN())
                .name("isNaN")
                .expected("NaN")
        );
    }

    @Override
    protected Double zero() {
        return 0.0;
    }

    @Override
    protected Double min() {
        return Double.MIN_VALUE;
    }

    @Override
    protected Double max() {
        return Double.MAX_VALUE;
    }

    @Override
    protected boolean eq(final Double a, final Double b) {
        return a.equals(b);
    }

    @Override
    protected boolean lt(final Double a, final Double b) {
        return a < b;
    }

    @Override
    protected boolean gt(final Double a, final Double b) {
        return a > b;
    }

    @Override
    protected boolean gte(final Double a, final Double b) {
        return a >= b;
    }

    @Override
    protected boolean lte(final Double a, final Double b) {
        return a <= b;
    }
}
