package io.github.libzeal.zeal.expression.types.boxed;

import io.github.libzeal.zeal.expression.types.ObjectExpression;

import java.util.function.Predicate;

public abstract class BoxedNumericExpression<T extends Number, E extends ObjectExpression<T, E>> extends ObjectExpression<T, E> {

    private static final String CANNOT_COMPARE_TO_NULL = "Always fail: cannot compare to subject to (null)";

    protected BoxedNumericExpression(final T subject) {
        super(subject);
    }

    protected BoxedNumericExpression(final T subject, final String name) {
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
            "isLessThan[" + stringify(value) + "]",
            "< " + value,
            t -> value != null && lt(t, value)
        );
    }

    private E nullProtectedValueExpression(final T value, final String name,
                                                             final String expectedValue,
                                                             final Predicate<T> longPredicate) {

        final PredicateBuilder builder = newPredicate(longPredicate)
            .name(name);

        if (value == null) {
            builder.expectedValue(CANNOT_COMPARE_TO_NULL);
        }
        else {
            builder.expectedValue(expectedValue);
        }

        return builder.append();
    }

    /**
     * Alias for {@link #isLessThan}
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
            "isGreaterThan[" + stringify(value) + "]",
            "> " + value,
            t -> value != null && gt(t, value)
        );
    }

    /**
     * Alias for {@link #isGreaterThan}
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
            "isLessThanOrEqualTo[" + stringify(value) + "]",
            "<= " + value,
            t -> value != null && lte(t, value)
        );
    }

    /**
     * Alias for {@link #isLessThanOrEqualTo}
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
            "isGreaterThanOrEqualTo[" + stringify(value) + "]",
            ">= " + value,
            t -> value != null && gte(t, value)
        );
    }

    /**
     * Alias for {@link #isGreaterThanOrEqualTo}
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
        return newPredicate(t -> eq(t, max()))
            .name("isMax")
            .expectedValue(String.valueOf(max()))
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is equal to the minimum {@link T} value.
     *
     * @return This expression (fluent interface).
     */
    public E isMinValue() {
        return newPredicate(t -> eq(t, min()))
            .name("isMin")
            .expectedValue(String.valueOf(min()))
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is equal to 0.
     *
     * @return This expression (fluent interface).
     */
    public E isZero() {
        return newPredicate(t -> eq(t, zero()))
            .name("isZero")
            .expectedValue(String.valueOf(zero()))
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not equal to 0.
     *
     * @return This expression (fluent interface).
     */
    public E isNotZero() {
        return newPredicate(t -> !eq(t, zero()))
            .name("isNotZero")
            .expectedValue("not[" + zero() + "]")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is positive (greater than 0).
     *
     * @return This expression (fluent interface).
     */
    public E isPositive() {
        return newPredicate(t -> gt(t, zero()))
            .name("isPositive")
            .expectedValue("> " + zero())
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not positive (less than or equal to 0).
     *
     * @return This expression (fluent interface).
     */
    public E isNotPositive() {
        return newPredicate(t -> lte(t, zero()))
            .name("isNotPositive")
            .expectedValue("<= " + zero())
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is negative (less than 0).
     *
     * @return This expression (fluent interface).
     */
    public E isNegative() {
        return newPredicate(t -> lt(t, zero()))
            .name("isNegative")
            .expectedValue("< " + zero())
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not negative (greater than or equal to 0).
     *
     * @return This expression (fluent interface).
     */
    public E isNotNegative() {
        return newPredicate(t -> gte(t, zero()))
            .name("isNotNegative")
            .expectedValue(">= " + zero())
            .append();
    }

    protected abstract T zero();
    protected abstract T min();
    protected abstract T max();
    protected abstract boolean eq(T a, T b);
    protected abstract boolean lt(T a, T b);
    protected abstract boolean gt(T a, T b);
    protected abstract boolean gte(T a, T b);
    protected abstract boolean lte(T a, T b);

}
