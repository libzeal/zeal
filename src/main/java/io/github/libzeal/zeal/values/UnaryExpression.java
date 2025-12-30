package io.github.libzeal.zeal.values;

import io.github.libzeal.zeal.logic.Expression;

public interface UnaryExpression<T> extends Value<T> {

    Expression expression();
}
