package com.zeal.expression.eval.primitive;

public final class FloatEvaluations {

    private FloatEvaluations() {}

    public static FloatEvaluation exactlyEquals(float value) {
        return FloatEvaluation.of(f -> f == value);
    }

    // NOTE: This method is exclusive of delta
    public static FloatEvaluation equalsWithinDelta(float value, float delta) {
        return FloatEvaluation.of(d -> Math.abs(d - value) < delta);
    }

    public static FloatEvaluation isLessThan(float value) {
        return FloatEvaluation.of(f -> f < value);
    }

    /**
     * Same as {@link #isLessThan}
     */
    public static FloatEvaluation lt(float value) {
        return isLessThan(value);
    }

    public static FloatEvaluation isGreaterThan(float value) {
        return FloatEvaluation.of(f -> f > value);
    }

    /**
     * Same as {@link #isGreaterThan}
     */
    public static FloatEvaluation gt(float value) {
        return isGreaterThan(value);
    }

    public static FloatEvaluation isLessThanOrEqualTo(float value) {
        return FloatEvaluation.of(f -> f <= value);
    }

    /**
     * Same as {@link #isLessThanOrEqualTo}
     */
    public static FloatEvaluation lte(float value) {
        return isLessThanOrEqualTo(value);
    }

    public static FloatEvaluation isGreaterThanOrEqualTo(float value) {
        return FloatEvaluation.of(f -> f >= value);
    }

    /**
     * Same as {@link #isGreaterThanOrEqualTo}
      */
    public static FloatEvaluation gte(float value) {
        return isGreaterThanOrEqualTo(value);
    }

    public static FloatEvaluation isMaxValue() {
        return FloatEvaluation.of(f -> f == Float.MAX_VALUE);
    }

    public static FloatEvaluation isMinValue() {
        return FloatEvaluation.of(f -> f == Float.MIN_VALUE);
    }

    public static FloatEvaluation isZero() {
        return FloatEvaluation.of(f -> f == 0);
    }

    public static FloatEvaluation isPositive() {
        return FloatEvaluation.of(f -> f > 0);
    }

    public static FloatEvaluation isNegative() {
        return FloatEvaluation.of(f -> f < 0);
    }

    public static FloatEvaluation isNotNegative() {
        return FloatEvaluation.of(f -> f >= 0);
    }

    public static FloatEvaluation isNotPositive() {
        return FloatEvaluation.of(f -> f <= 0);
    }

    public static FloatEvaluation isFinite() {
        return FloatEvaluation.of(Float::isFinite);
    }

    public static FloatEvaluation isInfinite() {
        return FloatEvaluation.of(Float::isInfinite);
    }

    public static FloatEvaluation isNan() {
        return FloatEvaluation.of(Float::isNaN);
    }
}
