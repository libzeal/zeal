package io.github.libzeal.zeal.logic.condition;

import io.github.libzeal.zeal.logic.Expression;

@FunctionalInterface
public interface Condition<T> {

    Expression create(T subject);
}
