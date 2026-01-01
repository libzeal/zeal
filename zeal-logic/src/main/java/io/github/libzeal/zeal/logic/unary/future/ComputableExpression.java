package io.github.libzeal.zeal.logic.unary.future;

import io.github.libzeal.zeal.logic.Expression;

public interface ComputableExpression<T> {
    Expression compute(T subject);
}
