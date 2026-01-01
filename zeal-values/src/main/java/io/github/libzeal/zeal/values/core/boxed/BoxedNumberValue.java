package io.github.libzeal.zeal.values.core.boxed;

import io.github.libzeal.zeal.logic.util.Formatter;
import io.github.libzeal.zeal.values.api.ObjectValue;
import io.github.libzeal.zeal.values.core.ValueBuilder;

import java.util.function.Predicate;

/**
 * An expression used to evaluate {@link Number} instances.
 *
 * @param <T>
 *     The type of the subject.
 * @param <E>
 *     The type of the subclass.
 *
 * @author Justin Albano
 * @see ObjectValue
 * @since 0.2.0
 */
public abstract class BoxedNumberValue<T extends Number, E extends ObjectValue<T, E>> extends ObjectValue<T, E> {

    private static final String CANNOT_COMPARE_TO_NULL = "Always fail: cannot compare to subject to (null)";

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     * @param name
     *     The name of the expression.
     */
    protected BoxedNumberValue(final T subject, final String name) {
        super(subject, name);
    }

    /**
     * Adds a predicate to the expression that checks if the subject is less than the supplied value.
     *
     * @param value
     *     The value to compare. This predicate will fail if the supplied value is {@code null}.
     *
     * @return This expression (fluent interface).
     */
    public E isLessThan(final T value) {
        return nullProtectedValueExpression(
            value,
            "isLessThan[" + Formatter.stringify(value) + "]",
            "< " + value,
            t -> value != null && lt(t, value)
        );
    }

    private E nullProtectedValueExpression(final T value, final String name,
                                           final String expectedValue,
                                           final Predicate<T> longPredicate) {

        final ValueBuilder<T, E> builder = expression(longPredicate)
            .name(name);

        if (value == null) {
            builder.expected(CANNOT_COMPARE_TO_NULL);
        }
        else {
            builder.expected(expectedValue);
        }

        return append(builder);
    }

    /**
     * Alias for {@link #isLessThan}
     *
     * @param value
     *     The value to compare. This predicate will fail if the supplied value is {@code null}.
     *
     * @return This expression (fluent interface).
     *
     * @see #isLessThan
     */
    public E lt(final T value) {
        return isLessThan(value);
    }

    /**
     * Adds a predicate to the expression that checks if the subject is greater than the supplied value.
     *
     * @param value
     *     The value to compare. This predicate will fail if the supplied value is {@code null}.
     *
     * @return This expression (fluent interface).
     */
    public E isGreaterThan(final T value) {
        return nullProtectedValueExpression(
            value,
            "isGreaterThan[" + Formatter.stringify(value) + "]",
            "> " + value,
            t -> value != null && gt(t, value)
        );
    }

    /**
     * Alias for {@link #isGreaterThan}
     *
     * @param value
     *     The value to compare. This predicate will fail if the supplied value is {@code null}.
     *
     * @return This expression (fluent interface).
     *
     * @see #isGreaterThan
     */
    public E gt(final T value) {
        return isGreaterThan(value);
    }

    /**
     * Adds a predicate to the expression that checks if the subject is less than or equal to the supplied value.
     *
     * @param value
     *     The value to compare. This predicate will fail if the supplied value is {@code null}.
     *
     * @return This expression (fluent interface).
     */
    public E isLessThanOrEqualTo(final T value) {
        return nullProtectedValueExpression(
            value,
            "isLessThanOrEqualTo[" + Formatter.stringify(value) + "]",
            "<= " + value,
            t -> value != null && lte(t, value)
        );
    }

    /**
     * Alias for {@link #isLessThanOrEqualTo}
     *
     * @param value
     *     The value to compare. This predicate will fail if the supplied value is {@code null}.
     *
     * @return This expression (fluent interface).
     *
     * @see #isLessThanOrEqualTo
     */
    public E lte(final T value) {
        return isLessThanOrEqualTo(value);
    }

    /**
     * Adds a predicate to the expression that checks if the subject is greater than or equal to the supplied value.
     *
     * @param value
     *     The value to compare. This predicate will fail if the supplied value is {@code null}.
     *
     * @return This expression (fluent interface).
     */
    public E isGreaterThanOrEqualTo(final T value) {
        return nullProtectedValueExpression(
            value,
            "isGreaterThanOrEqualTo[" + Formatter.stringify(value) + "]",
            ">= " + value,
            t -> value != null && gte(t, value)
        );
    }

    /**
     * Alias for {@link #isGreaterThanOrEqualTo}
     *
     * @param value
     *     The value to compare. This predicate will fail if the supplied value is {@code null}.
     *
     * @return This expression (fluent interface).
     *
     * @see #isGreaterThanOrEqualTo
     */
    public E gte(final T value) {
        return isGreaterThanOrEqualTo(value);
    }

    /**
     * Adds a predicate to the expression that checks if the subject is equal to the maximum {@link T} value.
     *
     * @return This expression (fluent interface).
     */
    public E isMaxValue() {
        return append(
            expression(t -> eq(t, max()))
                .name("isMax")
                .expected(String.valueOf(max()))
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject is equal to the minimum {@link T} value.
     *
     * @return This expression (fluent interface).
     */
    public E isMinValue() {
        return append(
            expression(t -> eq(t, min()))
                .name("isMin")
                .expected(String.valueOf(min()))
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject is equal to 0.
     *
     * @return This expression (fluent interface).
     */
    public E isZero() {
        return append(
            expression(t -> eq(t, zero()))
                .name("isZero")
                .expected(String.valueOf(zero()))
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not equal to 0.
     *
     * @return This expression (fluent interface).
     */
    public E isNotZero() {
        return append(
            expression(t -> !eq(t, zero()))
                .name("isNotZero")
                .expected("not[" + zero() + "]")
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject is positive (greater than 0).
     *
     * @return This expression (fluent interface).
     */
    public E isPositive() {
        return append(
            expression(t -> gt(t, zero()))
                .name("isPositive")
                .expected("> " + zero())
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not positive (less than or equal to 0).
     *
     * @return This expression (fluent interface).
     */
    public E isNotPositive() {
        return append(
            expression(t -> lte(t, zero()))
                .name("isNotPositive")
                .expected("<= " + zero())
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject is negative (less than 0).
     *
     * @return This expression (fluent interface).
     */
    public E isNegative() {
        return append(
            expression(t -> lt(t, zero()))
                .name("isNegative")
                .expected("< " + zero())
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not negative (greater than or equal to 0).
     *
     * @return This expression (fluent interface).
     */
    public E isNotNegative() {
        return append(
            expression(t -> gte(t, zero()))
                .name("isNotNegative")
                .expected(">= " + zero())
        );
    }

    /**
     * Obtains the zero value for the numeric subtype.
     *
     * @return The zero value for the numeric subtype.
     */
    protected abstract T zero();

    /**
     * Obtains the minimum value for the numeric subtype.
     *
     * @return The minimum value for the numeric subtype.
     */
    protected abstract T min();

    /**
     * Obtains the maximum value for the numeric subtype.
     *
     * @return The maximum value for the numeric subtype.
     */
    protected abstract T max();

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
    protected abstract boolean eq(T a, T b);

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
    protected abstract boolean lt(T a, T b);

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
    protected abstract boolean gt(T a, T b);

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
    protected abstract boolean lte(T a, T b);

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
    protected abstract boolean gte(T a, T b);

}
