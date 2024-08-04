package io.github.libzeal.zeal.expression.types.boxed;

public class BoxedFloatExpression extends BoxedNumericExpression<Float, BoxedFloatExpression> {

    public BoxedFloatExpression(final Float subject) {
        super(subject, "Float expression");
    }

    public BoxedFloatExpression isWithinDelta(final Float value, final Float delta) {
        return newPredicate(d ->  Math.abs(d - value) < delta)
            .expectedValue(value + " +/- " + delta)
            .append();
    }

    public BoxedFloatExpression isFinite() {
        return newPredicate(Float::isFinite)
            .name("isFinite")
            .expectedValue("finite")
            .append();
    }

    public BoxedFloatExpression isInfinite() {
        return newPredicate(l -> l.isInfinite())
            .name("isInfinite")
            .expectedValue("infinite")
            .append();
    }

    public BoxedFloatExpression isNaN() {
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
