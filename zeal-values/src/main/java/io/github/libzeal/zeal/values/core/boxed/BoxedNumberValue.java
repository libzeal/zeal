package io.github.libzeal.zeal.values.core.boxed;

import io.github.libzeal.zeal.values.core.ObjectValue;
import org.jspecify.annotations.Nullable;

/**
 * A value representing a boxed {@link Number}.
 *
 * @param <T>
 *         The {@link Number} type.
 * @param <V>
 *         The value type (the implementation type of this interface).
 *
 * @author Justin Albano
 */
public interface BoxedNumberValue<T extends Number, V extends BoxedNumberValue<T, V>>
        extends ObjectValue<T, V> {

    /**
     * Checks if the subject is less than the supplied value.
     *
     * @param value
     *         The value to compare. This predicate will fail if the supplied
     *         value is {@code null}.
     *
     * @return This value (fluent interface).
     */
    V isLessThan(@Nullable T value);

    /**
     * Alias for {@link #isLessThan}
     *
     * @param value
     *         The value to compare. This predicate will fail if the supplied
     *         value is {@code null}.
     *
     * @return This value (fluent interface).
     *
     * @see #isLessThan
     */
    default V lt(final @Nullable T value) {
        return isLessThan(value);
    }

    /**
     * Checks if the subject is greater than the supplied value.
     *
     * @param value
     *         The value to compare. This predicate will fail if the supplied
     *         value is {@code null}.
     *
     * @return This value (fluent interface).
     */
    V isGreaterThan(@Nullable T value);

    /**
     * Alias for {@link #isGreaterThan}
     *
     * @param value
     *         The value to compare. This predicate will fail if the supplied
     *         value is {@code null}.
     *
     * @return This value (fluent interface).
     *
     * @see #isGreaterThan
     */
    default V gt(final @Nullable T value) {
        return isGreaterThan(value);
    }

    /**
     * Checks if the subject is less than or equal to the supplied value.
     *
     * @param value
     *         The value to compare. This predicate will fail if the supplied
     *         value is {@code null}.
     *
     * @return This value (fluent interface).
     */
    V isLessThanOrEqualTo(@Nullable T value);

    /**
     * Alias for {@link #isLessThanOrEqualTo}
     *
     * @param value
     *         The value to compare. This predicate will fail if the supplied
     *         value is {@code null}.
     *
     * @return This value (fluent interface).
     *
     * @see #isLessThanOrEqualTo
     */
    default V lte(final @Nullable T value) {
        return isLessThanOrEqualTo(value);
    }

    /**
     * Checks if the subject is greater than or equal to the supplied value.
     *
     * @param value
     *         The value to compare. This predicate will fail if the supplied
     *         value is {@code null}.
     *
     * @return This value (fluent interface).
     */
    V isGreaterThanOrEqualTo(@Nullable T value);

    /**
     * Alias for {@link #isGreaterThanOrEqualTo}
     *
     * @param value
     *         The value to compare. This predicate will fail if the supplied
     *         value is {@code null}.
     *
     * @return This value (fluent interface).
     *
     * @see #isGreaterThanOrEqualTo
     */
    default V gte(final @Nullable T value) {
        return isGreaterThanOrEqualTo(value);
    }

    /**
     * Checks if the subject is equal to the maximum {@link T} value.
     *
     * @return This value (fluent interface).
     */
    V isMaxValue();

    /**
     * Checks if the subject is equal to the minimum {@link T} value.
     *
     * @return This value (fluent interface).
     */
    V isMinValue();

    /**
     * Checks if the subject is equal to 0.
     *
     * @return This value (fluent interface).
     */
    V isZero();

    /**
     * Checks if the subject is not equal to 0.
     *
     * @return This value (fluent interface).
     */
    V isNotZero();

    /**
     * Checks if the subject is positive (greater than 0).
     *
     * @return This value (fluent interface).
     */
    V isPositive();

    /**
     * Checks if the subject is not positive (less than or equal to 0).
     *
     * @return This value (fluent interface).
     */
    V isNotPositive();

    /**
     * Checks if the subject is negative (less than 0).
     *
     * @return This value (fluent interface).
     */
    V isNegative();

    /**
     * Checks if the subject is not negative (greater than or equal to 0).
     *
     * @return This value (fluent interface).
     */
    V isNotNegative();
}
