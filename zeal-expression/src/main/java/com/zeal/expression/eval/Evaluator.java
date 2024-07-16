package com.zeal.expression.eval;

import com.zeal.expression.BooleanExpression;

public interface Evaluator<S> extends BooleanExpression {

    S subject();

    static <T> Evaluator<T> not(Evaluator<T> expression) {
        return new Evaluator<T>() {

            @Override
            public boolean isTrue() {
                return expression.isFalse();
            }

            @Override
            public T subject() {
                return expression.subject();
            }
        };
    }
}
