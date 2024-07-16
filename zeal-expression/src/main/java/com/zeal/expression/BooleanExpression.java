package com.zeal.expression;

@FunctionalInterface
public interface BooleanExpression {

    boolean isTrue();

    default boolean isFalse() {
        return !isTrue();
    }
}
