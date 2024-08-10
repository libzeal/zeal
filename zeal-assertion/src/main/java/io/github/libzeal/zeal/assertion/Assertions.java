package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.expression.lang.UnaryExpression;

/**
 * Basic assertions that verify expressions.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class Assertions {

    private Assertions() {
    }

    public static <T> T require(final UnaryExpression<T> expression) {
        return Requirement.create().require(expression);
    }

    public static <T> T require(final UnaryExpression<T> expression, final String message) {
        return Requirement.create().require(expression, message);
    }

    public static <T> T confirm(final UnaryExpression<T> expression) {
        return Confirmation.create().confirm(expression);
    }

    public static <T> T confirm(final UnaryExpression<T> expression, final String message) {
        return Confirmation.create().confirm(expression, message);
    }

    public static <T> T ensure(final UnaryExpression<T> expression) {
        return Assurance.create().ensure(expression);
    }

    public static <T> T ensure(final UnaryExpression<T> expression, final String message) {
        return Assurance.create().ensure(expression, message);
    }
}
