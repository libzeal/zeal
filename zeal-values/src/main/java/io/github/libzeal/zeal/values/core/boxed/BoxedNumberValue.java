package io.github.libzeal.zeal.values.core.boxed;

import io.github.libzeal.zeal.values.core.ObjectValue;

public interface BoxedNumberValue<T extends Number, V extends BoxedNumberValue<T, V>>
        extends ObjectValue<T, V> {

    /**
     * Adds a predicate to the expression that checks if the subject is less
     * than the supplied value.
     *
     * @param value
     *     The value to compare. This predicate will fail if the supplied value
     *     is {@code null}.
     *
     * @return This expression (fluent interface).
     */
    V isLessThan(T value);

    /**
     * Alias for {@link #isLessThan}
     *
     * @param value
     *     The value to compare. This predicate will fail if the supplied value
     *     is {@code null}.
     *
     * @return This expression (fluent interface).
     *
     * @see #isLessThan
     */
    default V lt(final T value) {
        return isLessThan(value);
    }

    /**
     * Adds a predicate to the expression that checks if the subject is greater
     * than the supplied value.
     *
     * @param value
     *     The value to compare. This predicate will fail if the supplied value
     *     is {@code null}.
     *
     * @return This expression (fluent interface).
     */
    V isGreaterThan(T value);

    /**
     * Alias for {@link #isGreaterThan}
     *
     * @param value
     *     The value to compare. This predicate will fail if the supplied value
     *     is {@code null}.
     *
     * @return This expression (fluent interface).
     *
     * @see #isGreaterThan
     */
    default V gt(final T value) {
        return isGreaterThan(value);
    }

    /**
     * Adds a predicate to the expression that checks if the subject is less
     * than or equal to the supplied value.
     *
     * @param value
     *     The value to compare. This predicate will fail if the supplied value
     *     is {@code null}.
     *
     * @return This expression (fluent interface).
     */
    V isLessThanOrEqualTo(T value);

    /**
     * Alias for {@link #isLessThanOrEqualTo}
     *
     * @param value
     *     The value to compare. This predicate will fail if the supplied value
     *     is {@code null}.
     *
     * @return This expression (fluent interface).
     *
     * @see #isLessThanOrEqualTo
     */
    default V lte(final T value) {
        return isLessThanOrEqualTo(value);
    }

    /**
     * Adds a predicate to the expression that checks if the subject is greater
     * than or equal to the supplied value.
     *
     * @param value
     *     The value to compare. This predicate will fail if the supplied value
     *     is {@code null}.
     *
     * @return This expression (fluent interface).
     */
    V isGreaterThanOrEqualTo(T value);

    /**
     * Alias for {@link #isGreaterThanOrEqualTo}
     *
     * @param value
     *     The value to compare. This predicate will fail if the supplied value
     *     is {@code null}.
     *
     * @return This expression (fluent interface).
     *
     * @see #isGreaterThanOrEqualTo
     */
    default V gte(final T value) {
        return isGreaterThanOrEqualTo(value);
    }

    /**
     * Adds a predicate to the expression that checks if the subject is equal to
     * the maximum {@link T} value.
     *
     * @return This expression (fluent interface).
     */
    V isMaxValue();

    /**
     * Adds a predicate to the expression that checks if the subject is equal to
     * the minimum {@link T} value.
     *
     * @return This expression (fluent interface).
     */
    V isMinValue();

    /**
     * Adds a predicate to the expression that checks if the subject is equal to
     * 0.
     *
     * @return This expression (fluent interface).
     */
    V isZero();

    /**
     * Adds a predicate to the expression that checks if the subject is not
     * equal to 0.
     *
     * @return This expression (fluent interface).
     */
    V isNotZero();

    /**
     * Adds a predicate to the expression that checks if the subject is positive
     * (greater than 0).
     *
     * @return This expression (fluent interface).
     */
    V isPositive();

    /**
     * Adds a predicate to the expression that checks if the subject is not
     * positive (less than or equal to 0).
     *
     * @return This expression (fluent interface).
     */
    V isNotPositive();

    /**
     * Adds a predicate to the expression that checks if the subject is negative
     * (less than 0).
     *
     * @return This expression (fluent interface).
     */
    V isNegative();

    /**
     * Adds a predicate to the expression that checks if the subject is not
     * negative (greater than or equal to 0).
     *
     * @return This expression (fluent interface).
     */
    V isNotNegative();
}
