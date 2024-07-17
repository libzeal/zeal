package com.zeal.expression.eval.primitive;

public final class IntEvaluations {

    private IntEvaluations() {}

    public static IntEvaluation equals(int value) {
        return IntEvaluation.of(i -> i == value);
    }

    public static IntEvaluation isLessThan(int value) {
        return IntEvaluation.of(i -> i < value);
    }

    /**
     * Same as {@link #isLessThan}
     */
    public static IntEvaluation lt(int value) {
        return isLessThan(value);
    }

    public static IntEvaluation isGreaterThan(int value) {
        return IntEvaluation.of(i -> i > value);
    }

    /**
     * Same as {@link #isGreaterThan}
     */
    public static IntEvaluation gt(int value) {
        return isGreaterThan(value);
    }

    public static IntEvaluation isLessThanOrEqualTo(int value) {
        return IntEvaluation.of(i -> i <= value);
    }

    /**
     * Same as {@link #isLessThanOrEqualTo}
     */
    public static IntEvaluation lte(int value) {
        return isLessThanOrEqualTo(value);
    }

    public static IntEvaluation isGreaterThanOrEqualTo(int value) {
        return IntEvaluation.of(i -> i >= value);
    }

    /**
     * Same as {@link #isGreaterThanOrEqualTo}
      */
    public static IntEvaluation gte(int value) {
        return isGreaterThanOrEqualTo(value);
    }

    public static IntEvaluation isMaxValue() {
        return IntEvaluation.of(i -> i == Integer.MAX_VALUE);
    }

    public static IntEvaluation isMinValue() {
        return IntEvaluation.of(i -> i == Integer.MIN_VALUE);
    }

    public static IntEvaluation isZero() {
        return IntEvaluation.of(i -> i == 0);
    }

    public static IntEvaluation isPositive() {
        return IntEvaluation.of(i -> i > 0);
    }

    public static IntEvaluation isNegative() {
        return IntEvaluation.of(i -> i < 0);
    }

    public static IntEvaluation isEven() {
        return IntEvaluation.of(i -> i % 2 == 0);
    }

    public static IntEvaluation isOdd() {
        return IntEvaluation.of(i -> i % 2 != 0);
    }

    public static IntEvaluation isNotNegative() {
        return IntEvaluation.of(i -> i >= 0);
    }

    public static IntEvaluation isNotPositive() {
        return IntEvaluation.of(i -> i <= 0);
    }
}
