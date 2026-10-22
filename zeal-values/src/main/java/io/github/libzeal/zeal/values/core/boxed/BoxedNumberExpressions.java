package io.github.libzeal.zeal.values.core.boxed;

import io.github.libzeal.zeal.logic.unary.ComputableExpression;
import io.github.libzeal.zeal.logic.format.Formatter;
import io.github.libzeal.zeal.logic.unary.ExpressionBuilder;

import java.util.function.Predicate;

class BoxedNumberExpressions {

    private static final String CANNOT_COMPARE_TO_NULL = "Always fail: cannot compare to subject to (null)";

    private BoxedNumberExpressions() {}

    public static <T extends Number> ComputableExpression<T> isLessThan(
        final T value, final BoxedNumberOperations<T> operations) {

        return nullProtectedValueExpression(
            value,
            "isLessThan[" + Formatter.stringify(value) + "]",
            "< " + value,
            t -> value != null && operations.lt(t, value)
        );
    }

    static <T extends Number> ComputableExpression<T> nullProtectedValueExpression(
        final T value, final String name,
        final String expectedValue,
        final Predicate<T> longPredicate) {

        final ExpressionBuilder<T, T> builder = ExpressionBuilder.of(longPredicate)
            .name(name);

        if (value == null) {
            builder.expected(CANNOT_COMPARE_TO_NULL);
        }
        else {
            builder.expected(expectedValue);
        }

        return builder.build();
    }

    public static <T extends Number> ComputableExpression<T> isGreaterThan(
            final T value, final BoxedNumberOperations<T> operations) {
        return nullProtectedValueExpression(
            value,
            "isGreaterThan[" + Formatter.stringify(value) + "]",
            "> " + value,
            t -> value != null && operations.gt(t, value)
        );
    }

    public static <T extends Number> ComputableExpression<T> isLessThanOrEqualTo(
            final T value, final BoxedNumberOperations<T> operations) {
        return nullProtectedValueExpression(
            value,
            "isLessThanOrEqualTo[" + Formatter.stringify(value) + "]",
            "<= " + value,
            t -> value != null && operations.lte(t, value)
        );
    }

    public static <T extends Number> ComputableExpression<T> isGreaterThanOrEqualTo(
            final T value, final BoxedNumberOperations<T> operations) {
        return nullProtectedValueExpression(
            value,
            "isGreaterThanOrEqualTo[" + Formatter.stringify(value) + "]",
            ">= " + value,
            t -> value != null && operations.gte(t, value)
        );
    }

    public static <T extends Number> ComputableExpression<T> isMaxValue(
            final BoxedNumberOperations<T> operations) {

        final T max = operations.max();

        return notNullExpression((T t) -> operations.eq(t, max))
            .name("isMax")
            .expected(String.valueOf(max))
            .build();
    }

    static <T extends Number> ExpressionBuilder<T, T> notNullExpression(
        final Predicate<T> test) {
        return ExpressionBuilder.of(test);
    }

    public static <T extends Number> ComputableExpression<T> isMinValue(
        final BoxedNumberOperations<T> operations) {

        final T min = operations.min();

        return notNullExpression((T t) -> operations.eq(t, min))
            .name("isMin")
            .expected(String.valueOf(min))
            .build();
    }

    public static <T extends Number> ComputableExpression<T> isZero(
        final BoxedNumberOperations<T> operations) {

        final T zero = operations.zero();

        return notNullExpression((T t) -> operations.eq(t, zero))
            .name("isZero")
            .expected(String.valueOf(zero))
            .build();
    }

    public static <T extends Number> ComputableExpression<T> isNotZero(
        final BoxedNumberOperations<T> operations) {

        final T zero = operations.zero();

        return notNullExpression((T t) -> !operations.eq(t, zero))
            .name("isNotZero")
            .expected("not[" + zero + "]")
            .build();
    }

    public static <T extends Number> ComputableExpression<T> isPositive(
        final BoxedNumberOperations<T> operations) {

        final T zero = operations.zero();

        return notNullExpression((T t) -> operations.gt(t, zero))
            .name("isPositive")
            .expected("> " + zero)
            .build();
    }

    public static <T extends Number> ComputableExpression<T> isNotPositive(
        final BoxedNumberOperations<T> operations) {

        final T zero = operations.zero();

        return notNullExpression((T t) -> operations.lte(t, zero))
            .name("isNotPositive")
            .expected("<= " + zero)
            .build();
    }

    public static <T extends Number> ComputableExpression<T> isNegative(
        final BoxedNumberOperations<T> operations) {

        final T zero = operations.zero();

        return notNullExpression((T t) -> operations.lt(t, zero))
            .name("isNegative")
            .expected("< " + zero)
            .build();
    }

    public static <T extends Number> ComputableExpression<T> isNotNegative(
        final BoxedNumberOperations<T> operations) {

        final T zero = operations.zero();

        return notNullExpression((T t) -> operations.gte(t, zero))
            .name("isNotNegative")
            .expected(">= " + zero)
            .build();
    }

    public interface BoxedNumberOperations<T extends Number> {

        /**
         * Obtains the zero value for the numeric subtype.
         *
         * @return The zero value for the numeric subtype.
         */
        T zero();

        /**
         * Obtains the minimum value for the numeric subtype.
         *
         * @return The minimum value for the numeric subtype.
         */
        T min();

        /**
         * Obtains the maximum value for the numeric subtype.
         *
         * @return The maximum value for the numeric subtype.
         */
        T max();

        /**
         * Checks if the supplied values are equal.
         *
         * @param a
         *     The first value.
         * @param b
         *     The second value.
         *
         * @return True if the supplied values are equal; false otherwise.
         */
        boolean eq(T a, T b);

        /**
         * Checks if the first supplied value is less than the second supplied value.
         *
         * @param a
         *     The first value.
         * @param b
         *     The second value.
         *
         * @return True if the first supplied value is less than the second supplied value; false otherwise.
         */
        boolean lt(T a, T b);

        /**
         * Checks if the first supplied value is greater than the second supplied value.
         *
         * @param a
         *     The first value.
         * @param b
         *     The second value.
         *
         * @return True if the first supplied value is greater than the second supplied value; false otherwise.
         */
        boolean gt(T a, T b);

        /**
         * Checks if the first supplied value is greater than or equal to the second supplied value.
         *
         * @param a
         *     The first value.
         * @param b
         *     The second value.
         *
         * @return True if the first supplied value is greater than or equal to the second supplied value; false otherwise.
         */
        boolean lte(T a, T b);

        /**
         * Checks if the first supplied value is less than or equal to the second supplied value.
         *
         * @param a
         *     The first value.
         * @param b
         *     The second value.
         *
         * @return True if the first supplied value is less than or equal to the second supplied value; false otherwise.
         */
        boolean gte(T a, T b);
    }
}
