package io.github.libzeal.zeal.expression.api;

import io.github.libzeal.zeal.expression.types.GeneralObjectExpression;

public class Expressions {

    private Expressions() {}

    public static <T> GeneralObjectExpression<T> that(T value) {
        return new GeneralObjectExpression<>(value);
    }
}
