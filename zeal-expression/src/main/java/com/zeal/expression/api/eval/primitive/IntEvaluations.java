package com.zeal.expression.api.eval.primitive;

import com.zeal.expression.eval.primitive.IntEvaluation;

public final class IntEvaluations {

    private IntEvaluations() {}

    public static IntEvaluation equals(int value) {
        return i -> i == value;
    }

    public static IntEvaluation isLessThan(int value) {
        return i -> i < value;
    }

    /**
     * Same as {@link #isLessThan}
     */
    public static IntEvaluation lt(int value) {
        return isLessThan(value);
    }

    public static IntEvaluation isGreaterThan(int value) {
        return i -> i > value;
    }

    /**
     * Same as {@link #isGreaterThan}
     */
    public static IntEvaluation gt(int value) {
        return isGreaterThan(value);
    }

    public static IntEvaluation isLessThanOrEqualTo(int value) {
        return i -> i <= value;
    }

    /**
     * Same as {@link #isLessThanOrEqualTo}
     */
    public static IntEvaluation lte(int value) {
        return isLessThanOrEqualTo(value);
    }

    public static IntEvaluation isGreaterThanOrEqualTo(int value) {
        return i -> i >= value;
    }

    /**
     * Same as {@link #isGreaterThanOrEqualTo}
      */
    public static IntEvaluation gte(int value) {
        return isGreaterThanOrEqualTo(value);
    }

    public static IntEvaluation isMaxValue() {
        return i -> i == Integer.MAX_VALUE;
    }

    public static IntEvaluation isMinValue() {
        return i -> i == Integer.MIN_VALUE;
    }

    public static IntEvaluation isZero() {
        return i -> i == 0;
    }

    public static IntEvaluation isPositive() {
        return i -> i > 0;
    }

    public static IntEvaluation isNegative() {
        return i -> i < 0;
    }

    public static IntEvaluation isEven() {
        return i -> i % 2 == 0;
    }

    public static IntEvaluation isOdd() {
        return i -> i % 2 != 0;
    }

    public static IntEvaluation isNotNegative() {
        return i -> i >= 0;
    }

    public static IntEvaluation isNotPositive() {
        return i -> i <= 0;
    }
}
