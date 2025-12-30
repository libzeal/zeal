package io.github.libzeal.zeal.assertions;

import io.github.libzeal.zeal.values.UnaryExpression;

public class Assertions {

    private Assertions() {
    }

    public static <T> T require(final UnaryExpression<T> expression) {
        return new Requirement().require(expression);
    }

    public static <T> T require(final UnaryExpression<T> expression, final String message) {
        return new Requirement().require(expression, message);
    }
}
