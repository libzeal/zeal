package com.zeal.expression.api.eval.primitive;

import com.zeal.expression.eval.primitive.FloatEvaluation;

public final class FloatEvaluations {

    private FloatEvaluations() {}

    public static FloatEvaluation exactlyEquals(float value) {
        return f -> f == value;
    }

    // NOTE: This method is exclusive of delta
    public static FloatEvaluation equalsWithinDelta(float value, float delta) {
        return d -> Math.abs(d - value) < delta;
    }

    public static FloatEvaluation isLessThan(float value) {
        return f -> f < value;
    }

    /**
     * Same as {@link #isLessThan}
     */
    public static FloatEvaluation lt(float value) {
        return isLessThan(value);
    }

    public static FloatEvaluation isGreaterThan(float value) {
        return f -> f > value;
    }

    /**
     * Same as {@link #isGreaterThan}
     */
    public static FloatEvaluation gt(float value) {
        return isGreaterThan(value);
    }

    public static FloatEvaluation isLessThanOrEqualTo(float value) {
        return f -> f <= value;
    }

    /**
     * Same as {@link #isLessThanOrEqualTo}
     */
    public static FloatEvaluation lte(float value) {
        return isLessThanOrEqualTo(value);
    }

    public static FloatEvaluation isGreaterThanOrEqualTo(float value) {
        return f -> f >= value;
    }

    /**
     * Same as {@link #isGreaterThanOrEqualTo}
      */
    public static FloatEvaluation gte(float value) {
        return isGreaterThanOrEqualTo(value);
    }

    public static FloatEvaluation isMaxValue() {
        return f -> f == Float.MAX_VALUE;
    }

    public static FloatEvaluation isMinValue() {
        return f -> f == Float.MIN_VALUE;
    }

    public static FloatEvaluation isZero() {
        return f -> f == 0;
    }

    public static FloatEvaluation isPositive() {
        return f -> f > 0;
    }

    public static FloatEvaluation isNegative() {
        return f -> f < 0;
    }

    public static FloatEvaluation isNotNegative() {
        return f -> f >= 0;
    }

    public static FloatEvaluation isNotPositive() {
        return f -> f <= 0;
    }

    public static FloatEvaluation isFinite() {
        return Float::isFinite;
    }

    public static FloatEvaluation isInfinite() {
        return Float::isInfinite;
    }

    public static FloatEvaluation isNan() {
        return Float::isNaN;
    }
}
