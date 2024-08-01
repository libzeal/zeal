package io.github.libzeal.zeal.expression.types.boxed;

import io.github.libzeal.zeal.expression.types.ObjectExpression;

import java.util.function.Predicate;

/**
 * An expression used to evaluate {@link Integer} instances.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class BoxedIntegerExpression extends ObjectExpression<Integer, BoxedIntegerExpression> {

    private static final String CANNOT_COMPARE_TO_NULL = "Always fail: cannot compare to subject to (null)";

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public BoxedIntegerExpression(final Integer subject) {
        super(subject, "Integer expression");
    }

    /**
     * Adds a predicate to the expression that checks if the subject is less than the supplied value.
     *
     * @param value
     *     The value to compare. This predicate will fail if the supplied value is {@code null}.
     *
     * @return This expression (fluent interface).
     */
    public BoxedIntegerExpression isLessThan(final Integer value) {
        return nullProtectedValueExpression(
            value,
            "isLessThan[" + stringify(value) + "]",
            "< " + value,
            l -> value != null && l < value
        );
    }

    private BoxedIntegerExpression nullProtectedValueExpression(final Integer value, final String name,
                                                                final String expectedValue,
                                                                final Predicate<Integer> integerPredicate) {

        final PredicateBuilder builder = newPredicate(integerPredicate)
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
     * Alias for {@link #isLessThan(Integer)}
     *
     * @see #isLessThan(Integer)
     */
    public BoxedIntegerExpression lt(final Integer value) {
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
    public BoxedIntegerExpression isGreaterThan(final Integer value) {
        return nullProtectedValueExpression(
            value,
            "isGreaterThan[" + stringify(value) + "]",
            "> " + value,
            l -> value != null && l > value
        );
    }

    /**
     * Alias for {@link #isGreaterThan(Integer)}
     *
     * @see #isGreaterThan(Integer)
     */
    public BoxedIntegerExpression gt(final Integer value) {
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
    public BoxedIntegerExpression isLessThanOrEqualTo(final Integer value) {
        return nullProtectedValueExpression(
            value,
            "isLessThanOrEqualTo[" + stringify(value) + "]",
            "<= " + value,
            l -> value != null && l <= value
        );
    }

    /**
     * Alias for {@link #isLessThanOrEqualTo(Integer)}
     *
     * @see #isLessThanOrEqualTo(Integer)
     */
    public BoxedIntegerExpression lte(final Integer value) {
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
    public BoxedIntegerExpression isGreaterThanOrEqualTo(final Integer value) {
        return nullProtectedValueExpression(
            value,
            "isGreaterThanOrEqualTo[" + stringify(value) + "]",
            ">= " + value,
            l -> value != null && l >= value
        );
    }

    /**
     * Alias for {@link #isGreaterThanOrEqualTo(Integer)}
     *
     * @see #isGreaterThanOrEqualTo(Integer)
     */
    public BoxedIntegerExpression gte(final Integer value) {
        return isGreaterThanOrEqualTo(value);
    }

    /**
     * Adds a predicate to the expression that checks if the subject is equal to the maximum {@link Integer} value.
     *
     * @return This expression (fluent interface).
     *
     * @see Integer#MAX_VALUE
     */
    public BoxedIntegerExpression isMaxValue() {
        return newPredicate(l -> l == Integer.MAX_VALUE)
            .name("isMax")
            .expectedValue(String.valueOf(Integer.MAX_VALUE))
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is equal to the minimum {@link Integer} value.
     *
     * @return This expression (fluent interface).
     *
     * @see Integer#MIN_VALUE
     */
    public BoxedIntegerExpression isMinValue() {
        return newPredicate(l -> l == Integer.MIN_VALUE)
            .name("isMin")
            .expectedValue(String.valueOf(Integer.MIN_VALUE))
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is equal to 0.
     *
     * @return This expression (fluent interface).
     */
    public BoxedIntegerExpression isZero() {
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
    public BoxedIntegerExpression isPositive() {
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
    public BoxedIntegerExpression isNotPositive() {
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
    public BoxedIntegerExpression isNegative() {
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
    public BoxedIntegerExpression isNotNegative() {
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
    public BoxedIntegerExpression isEven() {
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
    public BoxedIntegerExpression isOdd() {
        return newPredicate(l -> l % 2 != 0)
            .name("isOdd")
            .expectedValue("% 2 != 0")
            .append();
    }
}
