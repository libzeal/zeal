package com.zeal.expression.api.eval.primitive;

import com.zeal.expression.eval.primitive.DoubleEvaluation;

public final class DoubleEvaluations {

    private DoubleEvaluations() {
    }

    public static DoubleEvaluation exactlyEquals(double value) {
        return d -> d == value;
    }

    // NOTE: This method is exclusive of delta
    public static DoubleEvaluation equalsWithinDelta(double value, double delta) {
        return d -> Math.abs(d - value) < delta;
    }

    public static DoubleEvaluation isLessThan(double value) {
        return d -> d < value;
    }

    /**
     * Same as {@link #isLessThan}
     */
    public static DoubleEvaluation lt(double value) {
        return isLessThan(value);
    }

    public static DoubleEvaluation isGreaterThan(double value) {
        return d -> d > value;
    }

    /**
     * Same as {@link #isGreaterThan}
     */
    public static DoubleEvaluation gt(double value) {
        return isGreaterThan(value);
    }

    public static DoubleEvaluation isLessThanOrEqualTo(double value) {
        return d -> d <= value;
    }

    /**
     * Same as {@link #isLessThanOrEqualTo}
     */
    public static DoubleEvaluation lte(double value) {
        return isLessThanOrEqualTo(value);
    }

    public static DoubleEvaluation isGreaterThanOrEqualTo(double value) {
        return d -> d >= value;
    }

    /**
     * Same as {@link #isGreaterThanOrEqualTo}
     */
    public static DoubleEvaluation gte(double value) {
        return isGreaterThanOrEqualTo(value);
    }

    public static DoubleEvaluation isMaxValue() {
        return d -> d == Double.MAX_VALUE;
    }

    public static DoubleEvaluation isMinValue() {
        return d -> d == Double.MIN_VALUE;
    }

    public static DoubleEvaluation isZero() {
        return d -> d == 0;
    }

    public static DoubleEvaluation isPositive() {
        return d -> d > 0;
    }

    public static DoubleEvaluation isNegative() {
        return d -> d < 0;
    }

    public static DoubleEvaluation isNotNegative() {
        return d -> d >= 0;
    }

    public static DoubleEvaluation isNotPositive() {
        return d -> d <= 0;
    }

    public static DoubleEvaluation isFinite() {
        return Double::isFinite;
    }

    public static DoubleEvaluation isInfinite() {
        return Double::isInfinite;
    }

    public static DoubleEvaluation isNan() {
        return Double::isNaN;
    }
}
