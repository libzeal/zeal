package io.github.libzeal.zeal.expression.types.boxed;

public class BoxedDoubleExpression extends BoxedNumericExpression<Double, BoxedDoubleExpression> {

    public BoxedDoubleExpression(final Double subject) {
        super(subject, "Double expression");
    }

    public BoxedDoubleExpression isWithinDelta(final Double value, final Double delta) {
        return newPredicate(d ->  Math.abs(d - value) < delta)
            .expectedValue(value + " +/- " + delta)
            .append();
    }

    public BoxedDoubleExpression isFinite() {
        return newPredicate(Double::isFinite)
            .name("isFinite")
            .expectedValue("finite")
            .append();
    }

    public BoxedDoubleExpression isInfinite() {
        return newPredicate(l -> l.isInfinite())
            .name("isInfinite")
            .expectedValue("infinite")
            .append();
    }

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
