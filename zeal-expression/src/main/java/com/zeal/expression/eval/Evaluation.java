package com.zeal.expression.eval;

import com.zeal.expression.BooleanExpression;

@FunctionalInterface
public interface Evaluation<T> {

    BooleanExpression evaluate(T subject);

    static <T> Evaluation<T> of(BooleanFunction<T> func) {
        return o -> BooleanExpression.of(() -> func.apply(o));
    }

    @FunctionalInterface
    static interface BooleanFunction<T> {
        boolean apply(T o);
    }
}
