package io.github.libzeal.zeal.expression.lang.condition;

import io.github.libzeal.zeal.expression.lang.Expression;

@FunctionalInterface
public interface Condition<T> {

    Expression create(T subject);
}
