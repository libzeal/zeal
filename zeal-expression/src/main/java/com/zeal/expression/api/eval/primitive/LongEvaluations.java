package com.zeal.expression.api.eval.primitive;

import com.zeal.expression.eval.primitive.LongEvaluation;

public final class LongEvaluations {

    private LongEvaluations() {}

    public static LongEvaluation equals(long value) {
        return l -> l == value;
    }

    public static LongEvaluation isLessThan(long value) {
        return l -> l < value;
    }

    /**
     * Same as {@link #isLessThan}
     */
    public static LongEvaluation lt(long value) {
        return isLessThan(value);
    }

    public static LongEvaluation isGreaterThan(long value) {
        return l -> l > value;
    }

    /**
     * Same as {@link #isGreaterThan}
     */
    public static LongEvaluation gt(long value) {
        return isGreaterThan(value);
    }

    public static LongEvaluation isLessThanOrEqualTo(long value) {
        return l -> l <= value;
    }

    /**
     * Same as {@link #isLessThanOrEqualTo}
     */
    public static LongEvaluation lte(long value) {
        return isLessThanOrEqualTo(value);
    }

    public static LongEvaluation isGreaterThanOrEqualTo(long value) {
        return l -> l >= value;
    }

    /**
     * Same as {@link #isGreaterThanOrEqualTo}
      */
    public static LongEvaluation gte(long value) {
        return isGreaterThanOrEqualTo(value);
    }

    public static LongEvaluation isMaxValue() {
        return l -> l == Long.MAX_VALUE;
    }

    public static LongEvaluation isMinValue() {
        return l -> l == Long.MIN_VALUE;
    }

    public static LongEvaluation isZero() {
        return l -> l == 0;
    }

    public static LongEvaluation isPositive() {
        return l -> l > 0;
    }

    public static LongEvaluation isNegative() {
        return l -> l < 0;
    }

    public static LongEvaluation isEven() {
        return l -> l % 2 == 0;
    }

    public static LongEvaluation isOdd() {
        return l -> l % 2 != 0;
    }

    public static LongEvaluation isNotNegative() {
        return l -> l >= 0;
    }

    public static LongEvaluation isNotPositive() {
        return l -> l <= 0;
    }
}
