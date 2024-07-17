package com.zeal.expression;

import java.util.Optional;
import java.util.function.Supplier;

@FunctionalInterface
public interface BooleanExpression {

    BooleanResult result();

    static BooleanExpression of(boolean expression) {
        return () -> new BooleanResult(() -> expression);
    }

    static BooleanExpression of(Supplier<Boolean> supplier) {
        return () -> new BooleanResult(supplier::get);
    }
}
