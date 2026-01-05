package io.github.libzeal.zeal.values.api;

import io.github.libzeal.zeal.logic.unary.future.ComputableExpression;

public interface ValueBuilder<T> {
    ComputableExpression<T> build();
}
