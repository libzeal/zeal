package io.github.libzeal.zeal.expression.types.boxed;

import io.github.libzeal.zeal.expression.types.ObjectExpression;

import java.util.function.Predicate;

/**
 * An expression used to evaluate {@link Long} instances.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class BoxedLongExpression extends ObjectExpression<Long, BoxedLongExpression> {

    private static final String CANNOT_COMPARE_TO_NULL = "Always fail: cannot compare to subject to (null)";

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public BoxedLongExpression(final Long subject) {
        super(subject, "Long expression");
    }

    /**
     * Adds a predicate to the expression that checks if the subject is less than the supplied value.
     *
     * @param value
     *     The value to compare. This predicate will fail if the supplied value is {@code null}.
     *
     * @return This expression (fluent interface).
     */
    public BoxedLongExpression isLessThan(final Long value) {
        return nullProtectedValueExpression(
            value,
            "isLessThan[" + stringify(value) + "]",
            "< " + value,
            l -> value != null && l < value
        );
    }

    private BoxedLongExpression nullProtectedValueExpression(final Long value, final String name,
                                                             final String expectedValue,
                                                             final Predicate<Long> longPredicate) {

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
     * Alias for {@link #isLessThan(Long)}
     *
     * @see #isLessThan(Long)
     */
    public BoxedLongExpression lt(final Long value) {
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
    public BoxedLongExpression isGreaterThan(final Long value) {
        return nullProtectedValueExpression(
            value,
            "isGreaterThan[" + stringify(value) + "]",
            "> " + value,
            l -> value != null && l > value
        );
    }

    /**
     * Alias for {@link #isGreaterThan(Long)}
     *
     * @see #isGreaterThan(Long)
     */
    public BoxedLongExpression gt(final Long value) {
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
    public BoxedLongExpression isLessThanOrEqualTo(final Long value) {
        return nullProtectedValueExpression(
            value,
            "isLessThanOrEqualTo[" + stringify(value) + "]",
            "<= " + value,
            l -> value != null && l <= value
        );
    }

    /**
     * Alias for {@link #isLessThanOrEqualTo(Long)}
     *
     * @see #isLessThanOrEqualTo(Long)
     */
    public BoxedLongExpression lte(final Long value) {
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
    public BoxedLongExpression isGreaterThanOrEqualTo(final Long value) {
        return nullProtectedValueExpression(
            value,
            "isGreaterThanOrEqualTo[" + stringify(value) + "]",
            ">= " + value,
            l -> value != null && l >= value
        );
    }

    /**
     * Alias for {@link #isGreaterThanOrEqualTo(Long)}
     *
     * @see #isGreaterThanOrEqualTo(Long)
     */
    public BoxedLongExpression gte(final Long value) {
        return isGreaterThanOrEqualTo(value);
    }

    /**
     * Adds a predicate to the expression that checks if the subject is equal to the maximum {@link Long} value.
     *
     * @return This expression (fluent interface).
     *
     * @see Long#MAX_VALUE
     */
    public BoxedLongExpression isMaxValue() {
        return newPredicate(l -> l == Long.MAX_VALUE)
            .name("isMax")
            .expectedValue(String.valueOf(Long.MAX_VALUE))
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is equal to the minimum {@link Long} value.
     *
     * @return This expression (fluent interface).
     *
     * @see Long#MIN_VALUE
     */
    public BoxedLongExpression isMinValue() {
        return newPredicate(l -> l == Long.MIN_VALUE)
            .name("isMin")
            .expectedValue(String.valueOf(Long.MIN_VALUE))
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is equal to 0.
     *
     * @return This expression (fluent interface).
     */
    public BoxedLongExpression isZero() {
        return newPredicate(l -> l == 0)
            .name("isZero")
            .expectedValue("0")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is positive (greater than 0).
     *
     * @return This expression (fluent interface).
     */
    public BoxedLongExpression isPositive() {
        return newPredicate(l -> l > 0)
            .name("isPositive")
            .expectedValue("> 0")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not positive (less than or equal to 0).
     *
     * @return This expression (fluent interface).
     */
    public BoxedLongExpression isNotPositive() {
        return newPredicate(l -> l <= 0)
            .name("isNotPositive")
            .expectedValue("<= 0")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is negative (less than 0).
     *
     * @return This expression (fluent interface).
     */
    public BoxedLongExpression isNegative() {
        return newPredicate(l -> l < 0)
            .name("isNegative")
            .expectedValue("< 0")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not negative (greater than or equal to 0).
     *
     * @return This expression (fluent interface).
     */
    public BoxedLongExpression isNotNegative() {
        return newPredicate(l -> l >= 0)
            .name("isNotNegative")
            .expectedValue(">= 0")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is even.
     *
     * @return This expression (fluent interface).
     */
    public BoxedLongExpression isEven() {
        return newPredicate(l -> l % 2 == 0)
            .name("isEven")
            .expectedValue("% 2 := 0")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is odd.
     *
     * @return This expression (fluent interface).
     */
    public BoxedLongExpression isOdd() {
        return newPredicate(l -> l % 2 != 0)
            .name("isOdd")
            .expectedValue("% 2 != 0")
            .append();
    }
}
