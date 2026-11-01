package io.github.libzeal.zeal.values.core.boxed;

import io.github.libzeal.zeal.logic.format.ObjectFormatter;
import io.github.libzeal.zeal.logic.unary.Condition;
import io.github.libzeal.zeal.logic.unary.ConditionBuilder;
import io.github.libzeal.zeal.values.sdk.BaseValue;

import java.util.function.Predicate;

abstract class BaseBoxedNumberValue<N extends Number, V extends BaseBoxedNumberValue<N, V>>
        extends BaseValue<N, V>
        implements BoxedNumberValue<N, V> {

    private static final String CANNOT_COMPARE_TO_NULL = "Always fail: cannot compare to subject to (null)";

    private final BoxedNumberOperations<N> operations;

    BaseBoxedNumberValue(final N subject, final String name, final BoxedNumberOperations<N> operations) {
        super(subject, name);
        this.operations = operations;
    }

    @Override
    public V isLessThan(final N value) {
        return append(
                nullProtectedValueExpression(
                        value,
                        "isLessThan[" + ObjectFormatter.stringify(value) + "]",
                        "< " + value,
                        n -> value != null && operations.lt(n, value)
                )
        );
    }

    @Override
    public V isGreaterThan(final N value) {
        return append(
                nullProtectedValueExpression(
                        value,
                        "isGreaterThan[" + ObjectFormatter.stringify(value) + "]",
                        "> " + value,
                        n -> value != null && operations.gt(n, value)
                )
        );
    }

    @Override
    public V isLessThanOrEqualTo(final N value) {
        return append(
                nullProtectedValueExpression(
                        value,
                        "isLessThanOrEqualTo[" + ObjectFormatter.stringify(value) + "]",
                        "<= " + value,
                        n -> value != null && operations.lte(n, value)
                )
        );
    }

    @Override
    public V isGreaterThanOrEqualTo(final N value) {
        return append(
                nullProtectedValueExpression(
                        value,
                        "isGreaterThanOrEqualTo[" + ObjectFormatter.stringify(value) + "]",
                        ">= " + value,
                        n -> value != null && operations.gte(n, value)
                )
        );
    }

    private Condition<N> nullProtectedValueExpression(
            final N value,
            final String name,
            final String expectedValue,
            final Predicate<N> test
    ) {

        final ConditionBuilder<N, N> builder = expression(test)
                .name(name);

        if (value == null) {
            builder.expected(CANNOT_COMPARE_TO_NULL);
        }
        else {
            builder.expected(expectedValue);
        }

        return builder.build();
    }

    @Override
    public V isMaxValue() {

        final N max = operations.max();

        return append(
                expression(n -> operations.eq(n, max))
                        .name("isMax")
                        .expected(String.valueOf(max))
                        .build()
        );
    }

    @Override
    public V isMinValue() {

        final N min = operations.min();

        return append(
                expression(n -> operations.eq(n, min))
                        .name("isMin")
                        .expected(String.valueOf(min))
                        .build()
        );
    }

    @Override
    public V isZero() {

        final N zero = operations.zero();

        return append(
                expression(n -> operations.eq(n, zero))
                        .name("isZero")
                        .expected(String.valueOf(zero))
                        .build()
        );
    }

    @Override
    public V isNotZero() {

        final N zero = operations.zero();

        return append(
                expression(n -> !operations.eq(n, zero))
                        .name("isNotZero")
                        .expected("not[" + zero + "]")
                        .build()
        );
    }

    @Override
    public V isPositive() {

        final N zero = operations.zero();

        return append(
                expression(n -> operations.gt(n, zero))
                        .name("isPositive")
                        .expected("> " + zero)
                        .build()
        );
    }

    @Override
    public V isNotPositive() {

        final N zero = operations.zero();

        return append(
                expression(n -> operations.lte(n, zero))
                        .name("isNotPositive")
                        .expected("<= " + zero)
                        .build()
        );
    }

    @Override
    public V isNegative() {

        final N zero = operations.zero();

        return append(
                expression(n -> operations.lt(n, zero))
                        .name("isNegative")
                        .expected("< " + zero)
                        .build()
        );
    }

    @Override
    public V isNotNegative() {

        final N zero = operations.zero();

        return append(
                expression(n -> operations.gte(n, zero))
                        .name("isNotNegative")
                        .expected(">= " + zero)
                        .build()
        );
    }

    /**
     * A set of operations common to all boxed numbers.
     *
     * @param <N>
     *         The boxed number type.
     *
     * @author Justin Albano
     */
    interface BoxedNumberOperations<N extends Number> {

        /**
         * Obtains the zero value for the numeric subtype.
         *
         * @return The zero value for the numeric subtype.
         */
        N zero();

        /**
         * Obtains the minimum value for the numeric subtype.
         *
         * @return The minimum value for the numeric subtype.
         */
        N min();

        /**
         * Obtains the maximum value for the numeric subtype.
         *
         * @return The maximum value for the numeric subtype.
         */
        N max();

        /**
         * Checks if the supplied values are equal.
         *
         * @param a
         *         The first value.
         * @param b
         *         The second value.
         *
         * @return True if the supplied values are equal; false otherwise.
         */
        boolean eq(N a, N b);

        /**
         * Checks if the first supplied value is less than the second supplied
         * value.
         *
         * @param a
         *         The first value.
         * @param b
         *         The second value.
         *
         * @return True if the first supplied value is less than the second
         * supplied value; false otherwise.
         */
        boolean lt(N a, N b);

        /**
         * Checks if the first supplied value is greater than the second
         * supplied value.
         *
         * @param a
         *         The first value.
         * @param b
         *         The second value.
         *
         * @return True if the first supplied value is greater than the second
         * supplied value; false otherwise.
         */
        boolean gt(N a, N b);

        /**
         * Checks if the first supplied value is greater than or equal to the
         * second supplied value.
         *
         * @param a
         *         The first value.
         * @param b
         *         The second value.
         *
         * @return True if the first supplied value is greater than or equal to
         * the second supplied value; false otherwise.
         */
        boolean lte(N a, N b);

        /**
         * Checks if the first supplied value is less than or equal to the
         * second supplied value.
         *
         * @param a
         *         The first value.
         * @param b
         *         The second value.
         *
         * @return True if the first supplied value is less than or equal to the
         * second supplied value; false otherwise.
         */
        boolean gte(N a, N b);
    }
}
