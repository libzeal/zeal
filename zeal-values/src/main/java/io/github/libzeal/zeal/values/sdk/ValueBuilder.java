package io.github.libzeal.zeal.values.sdk;

import io.github.libzeal.zeal.logic.unary.future.ComputableExpression;

public interface ValueBuilder<T> {
    ComputableExpression<T> build();
}
