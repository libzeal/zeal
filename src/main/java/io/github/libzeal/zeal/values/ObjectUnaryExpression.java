package io.github.libzeal.zeal.values;

import io.github.libzeal.zeal.logic.AndExpression;
import io.github.libzeal.zeal.logic.Expression;

public abstract class ObjectUnaryExpression<T, E extends ObjectUnaryExpression<T, E>>
    implements UnaryExpression<T> {

    private final T value;
    private final AndExpression expression;

    protected ObjectUnaryExpression(final T value) {
        this.value = value;
        this.expression = AndExpression.empty("Object[" + value + "]");
    }

    @Override
    public Expression expression() {
        return null;
    }

    @Override
    public T value() {
        return value;
    }
}
