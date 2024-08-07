package io.github.libzeal.zeal.expression.types.boxed;

/**
 * An expression used to evaluate {@link Double} instances.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class BoxedDoubleExpression extends BoxedNumericExpression<Double, BoxedDoubleExpression> {

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public BoxedDoubleExpression(final Double subject) {
        super(subject, "Double expression");
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
    public BoxedDoubleExpression isEqualTo(final Double value, final Double delta) {
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
    public BoxedDoubleExpression isFinite() {
        return newPredicate(Double::isFinite)
            .name("isFinite")
            .expectedValue("finite")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is infinite.
     *
     * @return This expression (fluent interface).
     */
    public BoxedDoubleExpression isInfinite() {
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
    public BoxedDoubleExpression isNaN() {
        return newPredicate(l -> l.isNaN())
            .name("isNaN")
            .expectedValue("NaN")
            .append();
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
