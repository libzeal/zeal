package io.github.libzeal.zeal.logic.unary;

import io.github.libzeal.zeal.logic.Expression;

@FunctionalInterface
public interface ComputableExpression<T> {
    Expression compute(T subject);
}
