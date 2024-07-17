package com.zeal.expression;

import java.util.function.Supplier;

@FunctionalInterface
public interface BooleanExpression {

    boolean isTrue();

    default boolean isFalse() {
        return !isTrue();
    }

    default boolean hasFailingNotNullCheck() {
        return false;
    }

    static BooleanExpression of(boolean expression) {
        return () -> expression;
    }

    static BooleanExpression of(Supplier<Boolean> supplier) {
        return supplier::get;
    }
}
